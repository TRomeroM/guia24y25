package com.tromero.guia24.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/visitante")
public class VisitanteController {
    @GetMapping("/informacion")
    public ResponseEntity<String> obtenerInformacion() {
        return ResponseEntity.ok("Información para visitantes.");
    }
}