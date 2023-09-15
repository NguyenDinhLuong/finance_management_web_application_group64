package com.financemanagementwebapp.backend.dao;

import com.financemanagementwebapp.backend.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
