package com.example.backend.controller;


import com.example.backend.exception.TokenRefreshException;
import com.example.backend.model.*;
import com.example.backend.payload.request.*;
import com.example.backend.payload.response.TokenRefreshResponse;
import com.example.backend.repository.*;
import com.example.backend.security.services.RefreshTokenService;
import com.example.backend.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.backend.payload.response.JwtResponse;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.services.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    RecurringExpenseRepository recurringExpenseRepository;

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserService userService;

    @GetMapping("/listOfUsers")
    public ResponseEntity<?> getUsersByRoleId() {
        List<User> users = userRepository.findByRoleId(1);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("No users found with the given role ID."));
        }
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // Check if the user with the given id exists
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User user = userData.get();

            // Check if the user has associated incomes
            List<Income> userIncomes = incomeRepository.findByUserId(user.getId());
            List<Investment> userInvestments = investmentRepository.findByUserId(user.getId());
            List<NormalExpense> userExpenses = expenseRepository.findByUserId(user.getId());
            List<RecurringExpense> userRecurringExpense = recurringExpenseRepository.findByUserId(user.getId());
            List<Goal> userGoal = goalRepository.findByUserId(user.getId());

            if(!userIncomes.isEmpty()) {
               incomeRepository.deleteAll(userIncomes);
            }

            if(!userInvestments.isEmpty()) {
                investmentRepository.deleteAll(userInvestments);
            }

            if(!userExpenses.isEmpty()) {
                expenseRepository.deleteAll(userExpenses);
            }

            if(!userRecurringExpense.isEmpty()) {
                recurringExpenseRepository.deleteAll(userRecurringExpense);
            }

            if(!userGoal.isEmpty()) {
                goalRepository.deleteAll(userGoal);
            }

            // Now, delete the user
            userRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found with the provided ID."));
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        String role = userDetails.getAuthorities().toString();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), userDetails.getFirstName(), userDetails.getLastName(), role));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {

        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(logoutRequest.getRefreshToken());

        refreshToken.ifPresent(token -> refreshTokenRepository.delete(token));

        // Return a response indicating whether the logout was successful or not
        return refreshToken.isPresent() ?
                ResponseEntity.ok(new MessageResponse("User logged out successfully")) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Refresh token is not in database!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                encoder.encode(signUpRequest.getPassword()));

        String strRole = signUpRequest.getRole();

        if (strRole == null) {
            Role role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(role);
            userRepository.save(user);
        } else {
            if (strRole.equals("admin")) {
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.setRole(adminRole);
                userRepository.save(user);
            } else {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.setRole(userRole);
                userRepository.save(user);
            }
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest userUpdates) {
        User updatedUser = userService.updateUser(id, userUpdates);
        if(updatedUser == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User updated unsuccessfully. Please check again!"));
        }
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }
}