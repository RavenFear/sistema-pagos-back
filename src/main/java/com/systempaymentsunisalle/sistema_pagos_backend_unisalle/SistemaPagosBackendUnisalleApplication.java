package com.systempaymentsunisalle.sistema_pagos_backend_unisalle;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.TypePago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.EstudianteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.PagoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SistemaPagosBackendUnisalleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaPagosBackendUnisalleApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRuuner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository) {
        return args -> {

        };

    }
}
 