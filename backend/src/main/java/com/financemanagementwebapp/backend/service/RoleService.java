package com.financemanagementwebapp.backend.service;

import com.financemanagementwebapp.backend.dao.RoleDao;
import com.financemanagementwebapp.backend.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;
    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }

}
