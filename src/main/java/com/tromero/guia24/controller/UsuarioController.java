package com.tromero.guia24.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @GetMapping("/perfil")
    public ResponseEntity<String> obtenerPerfil() {
        return ResponseEntity.ok("Perfil de usuario registrado.");
    }
}