package com.tromero.guia24.controller;

import com.tromero.guia24.model.Role;
import com.tromero.guia24.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/configuracion")
    public ResponseEntity<String> obtenerConfiguracion() {
        return ResponseEntity.ok("Configuración de administrador.");
    }
    @GetMapping("/lista_roles")
    public List<Role> roleList(){
        return roleService.rolelist();
    }
}
