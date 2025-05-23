package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities;
import java.time.LocalDate;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.TypePago;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {


    @Id
    //Genere automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;  //Identificador único

    private LocalDate fecha;
    private double monto;
    private TypePago type;
    private StatusPago status;
    private String file;



    @ManyToOne //Muchos pagos pueden estar asociados a un estudiante
    private Estudiante estudiante;
}
   
    