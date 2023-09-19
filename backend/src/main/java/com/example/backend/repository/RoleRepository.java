package com.example.backend.repository;

import com.example.backend.model.ERole;
import com.example.backend.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
