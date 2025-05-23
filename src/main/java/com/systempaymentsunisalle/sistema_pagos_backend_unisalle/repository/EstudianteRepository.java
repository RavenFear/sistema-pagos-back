package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Estudiante;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {

    // Método para buscar un estudiante por código
    Estudiante findByCodigo(String codigo);

    // Método para buscar estudiantes por programa
    List<Estudiante> findByProgramaId(String programaId);

    // Método para validar si ya existe un estudiante con ese código
    boolean existsByCodigo(String codigo);
}
