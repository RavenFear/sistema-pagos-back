package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.StatusPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.PagoStatus;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.TypePago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.EstudianteRepository;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

       
    /**
     * @param file archivo PDF que se subira al servidor
     * @param cantidad monto del pago realizado
     * @param type tipo de pago (EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO)
     * @param date fecha en qu se realiza el pago
     * @param codigoEstudiante codigo del estudiante que realiza el pago
     * @return Objeto del pago guardado en la base de datos
     * @throws IOException lanzar si ocurre un error al manejar el archivo
     */

     public Pago savePago(MultipartFile file, double cantidad, TypePago type, LocalDate date, String codigoEstudiante)throws IOException{

        //Construir la ruta donde se guardara el archivo dentro del sistema

        Path folderPath = Paths.get(System.getProperty("user.home"),"enset-data", "pagos");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }

        //Generar un nombre unico para el archivo usando UUID(identificador unico universal)
        String fileName = UUID.randomUUID().toString();

        //Construir la ruta completa del archivo, añadiendo la extension .pdf
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-data", "pagos", fileName + ".pdf");

        //Guardar el archivo recibido en la ubicación especificada dentro del sistema de archivos
        Files.copy(file.getInputStream(), filePath);

        //Buscar en la base de datos de estudiante que realizo el pago usando el codigo unico de sistema UUID
        Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

        //Creamos un nuevo objeto utilizando el patron de diseño builder
        Pago pago = Pago.builder()

        .type(type)//tipo de pago EFECTIVO, CHEQUE, TRANFERENCIA, DEPOSITO
        .status(StatusPago.CREADO)//estado inicial del pago
        .fecha(date)//fecha en que se realiza el pago
        .estudiante(estudiante)
        .monto(cantidad)//monto del pago
        .file(filePath.toUri().toString())//ruta del archivo PDF almacenado
        .build();//construccion final del objeto pago

        return pagoRepository.save(pago);
    }

    public byte[] getArchivoPorId(String pagoId) throws IOException{
        Pago pago = pagoRepository.findById(pagoId).get();
        return Files.readAllBytes(Path.of(URI.create(pago.getFile())));
    }

    public Pago actualizarPagoPorStatus(StatusPago status, String id){

        //Buscar un objeto pago en la base de datos por su ID
        Pago pago = pagoRepository.findById(id).get();

        //Actualice el estado del pago con el nuevo estado recibido
        pago.setStatus(status);

        return pagoRepository.save(pago);

    }
    
}
