package com.example.backend.security;

import com.example.backend.controller.AuthController;
import com.example.backend.model.ERole;
import com.example.backend.model.RefreshToken;
import com.example.backend.model.Role;
import com.example.backend.payload.request.*;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.services.RefreshTokenService;
import com.example.backend.security.services.UserDetailsImpl;
import com.example.backend.security.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserDetailsImpl userDetails;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private UserService userService;
    @Mock
    private JwtUtils jwtUtils;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testSignup_UsernameAlreadyTaken() {
        SignupRequest request = new SignupRequest();
        request.setUsername("existingUsername");
        request.setEmail("test@test.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        when(userRepository.existsByUsername(any(String.class))).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error: Username is already taken!", ((MessageResponse) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    void testSignup_EmailAlreadyTaken() {
        SignupRequest request = new SignupRequest();
        request.setUsername("newUsername");
        request.setEmail("existing@test.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error: Email is already in use!", ((MessageResponse) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    void testSignup_SuccessfulDefaultRole() {
        SignupRequest request = new SignupRequest();
        request.setUsername("newUsername");
        request.setEmail("newemail@test.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        // Mocking to indicate that username and email do not exist
        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

        // Mocking to return the default "USER" role when no role is provided in the request
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

        ResponseEntity<?> response = authController.registerUser(request);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals("User registered successfully!", ((MessageResponse) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    void testSignup_SuccessfulAdminRole() {
        SignupRequest request = new SignupRequest();
        request.setUsername("newAdmin");
        request.setEmail("newadmin@test.com");
        request.setFirstName("Admin");
        request.setLastName("User");
        request.setPassword("admin123");
        request.setRole("admin");

        // Mocking to indicate that username and email do not exist
        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

        // Mocking to return the "ADMIN" role when "admin" role is provided in the request
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(new Role(ERole.ROLE_ADMIN)));

        ResponseEntity<?> response = authController.registerUser(request);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals("User registered successfully!", ((MessageResponse) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    void testSignup_SuccessfulUserRole() {
        SignupRequest request = new SignupRequest();
        request.setUsername("newUser");
        request.setEmail("newuser@test.com");
        request.setFirstName("Regular");
        request.setLastName("User");
        request.setPassword("user123");
        request.setRole("user");

        // Mocking to indicate that username and email do not exist
        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

        // Mocking to return the "USER" role when "user" role is provided in the request
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

        ResponseEntity<?> response = authController.registerUser(request);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals("User registered successfully!", ((MessageResponse) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    public void authenticateUser_Success() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUsername");
        loginRequest.setPassword("testPassword");

        // Mock the authentication process
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        // Mock the JWT token generation
        when(jwtUtils.generateJwtToken(any())).thenReturn("testJwtToken");

        // Mock the Refresh token creation
        RefreshToken mockRefreshToken = new RefreshToken();
        mockRefreshToken.setToken("testRefreshToken");
        when(refreshTokenService.createRefreshToken(any(Long.class))).thenReturn(mockRefreshToken);

        // Act and Assert
        mockMvc.perform(post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("testJwtToken"))
                .andExpect(jsonPath("$.refreshToken").value("testRefreshToken"));
    }


    @Test
    void testGetUsersByRoleId_NoUsers() {
        when(userRepository.findByRoleId(1)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = authController.getUsersByRoleId();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }

    @Test
    void testLogout_Successful() {
        LogoutRequest request = new LogoutRequest();
        request.setRefreshToken("validRefreshToken");

        when(refreshTokenService.findByToken(any(String.class))).thenReturn(Optional.of(new RefreshToken()));

        ResponseEntity<?> response = authController.logoutUser(request);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void testUpdateUser() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("updateUsername");
        request.setEmail("updateemail@test.com");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userService.updateUser(any(Long.class), any())).thenReturn(null);

        ResponseEntity<?> response = authController.updateUser(1L, request);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.deleteUser(1L);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }
}
