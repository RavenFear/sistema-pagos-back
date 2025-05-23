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

            estudianteRepository.save(Estudiante.builder()
                    .id(UUID.randomUUID().toString()) //Genera un identificador único aleatorio para el estudiante
                    .nombre("Christian") //Nombre del estudiante
                    .apellido("Ramirez") //Apellido del estudiante
                    .codigo("1234") //Codigo del estudiante en la universidad
                    .programaId("LTA1") //Programa academico al que pertenece
                    .build());

            //Agrega otro estudiante llamado "Biaggio Ramirez" al mismo programa
            estudianteRepository.save(Estudiante.builder()
                    .id(UUID.randomUUID().toString())
                    .nombre("Biaggio")
                    .apellido("Ramirez")
                    .codigo("12354")
                    .programaId("LTA1")
                    .build());

            //Agrega un tercer estusiante llamado "Valentina Lopez" en otro programa ("LTA2")
            estudianteRepository.save(Estudiante.builder()
                    .id(UUID.randomUUID().toString())
                    .nombre("Valentina")
                    .apellido("Lopez")
                    .codigo("5678")
                    .programaId("LTA2")
                    .build());


            TypePago[] tiposPago = TypePago.values();

            Random random = new Random();

            estudianteRepository.findAll().forEach(estudiante -> {

                for (int i = 0; i < 10; i++) {
                    int index = random.nextInt(tiposPago.length);

                    Pago pago = Pago.builder()
                            .monto(1000 + (int) (Math.random() * 20000)) //Genera un monto entre 1000 y 21000
                            .type(tiposPago[index]) //Asigna un tipo de pago aleatorio
                            .status(StatusPago.CREADO) //Estado inicial del pago (CREADO)
                            .fecha(LocalDate.now()) //Fecha actual del Pago
                            .estudiante(estudiante) //Asigna el Pago al estudiante actual
                            .build();


                    pagoRepository.save(pago);
                }
            });
        };

    }
}
 