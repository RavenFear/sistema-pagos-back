package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.web;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/crear-estudiante")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;

    @PostMapping
    public ResponseEntity<?> crearEstudiante(@RequestBody Estudiante request) {
        // Validar unicidad del código
        if (estudianteRepository.existsByCodigo(request.getCodigo())) {
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un estudiante con el código: " + request.getCodigo());
        }

        // Crear nuevo estudiante ignorando cualquier ID que venga en el request
        Estudiante estudiante = Estudiante.builder()
                .id(UUID.randomUUID().toString())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .codigo(request.getCodigo())
                .programaId(request.getProgramaId())
                .build();

        Estudiante guardado = estudianteRepository.save(estudiante);
        return ResponseEntity.ok(guardado);
    }
}
