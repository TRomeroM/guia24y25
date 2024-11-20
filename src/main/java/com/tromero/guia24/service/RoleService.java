package com.tromero.guia24.service;

import com.tromero.guia24.model.Role;
import com.tromero.guia24.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role> rolelist(){
        return roleRepository.findAll();
    }
}
