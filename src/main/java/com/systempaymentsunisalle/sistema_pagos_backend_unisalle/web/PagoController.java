package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.dtos.UpdateStatusPagoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.TypePago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.EstudianteRepository;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.PagoRepository;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.services.PagoService;

@RestController
public class PagoController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    //Metodo que devuelva un estudiante en especifico segun su codigo
    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listarEstudiantesPorCodigo(@PathVariable String codigo) {
        return estudianteRepository.findByCodigo(codigo);
    }

    //Metodo que lista estudiantes segun el programa academico
    @GetMapping("/estudiantesPorPrograma/{programaId}")
    public List<Estudiante> listarEstudiantesPorPrograma(@PathVariable String programaId) {
        if (programaId == null || programaId.isEmpty()) {
            return estudianteRepository.findAll();
        }
        return estudianteRepository.findByProgramaId(programaId);
    }


    //Metodo que devuelve una lista con todos los pagos registrados
    @GetMapping("/pagos")
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    //Metodo que devuelve un pago en especifico por su id
    @GetMapping("/pagos{id}")
    public Pago listarPagoPorId(@PathVariable String id) {
        return pagoRepository.findById(id).get();
    }

    //Metodo que lista los pagos hechos por un estudiante segun codigo
    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo) {
        return pagoRepository.findByEstudianteCodigo(codigo);
    }

    @GetMapping("/pagos/pagosPorStatus/{status}")
    public List<Pago> listarPagosPorStatus(@PathVariable StatusPago status) {
        return pagoRepository.findByStatus(status);
    }


    //Metodo que lista los pagos segun su tipo (EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO)
    @GetMapping("/pagos/porTipo/{tipo}")
    public List<Pago> listarPagosPorType(@PathVariable TypePago tipo) {
        return pagoRepository.findByType(tipo);
    }

    //Metodo para actualizar el estado de un pago
    @PutMapping("pagos/{pagoId}/actualizarPago")
    public Pago actualizarStatusPago(@RequestBody UpdateStatusPagoDto request, @PathVariable String pagoId) {
        return pagoService.actualizarPagoPorStatus(request.getValor(), pagoId);
    }


    //Metodo para registrar un pago con un archivo adjunto (comprobante de pago)
    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(
            @RequestParam("file") MultipartFile file, //archivo adjunto
            double monto, //monto del pago
            TypePago type,
            LocalDate date,
            String codigoEstudiante) throws IOException {
        return pagoService.savePago(file, monto, type, date, codigoEstudiante);//Guarda el pago en la base de datos
    }

    //Metodo para descargar un archivo de pago
    //Metodo que lista los pagos segun tipo (EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO)
    @GetMapping(value = "/pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] listarArchivoPorId(@PathVariable String pagoId) throws IOException {
        return pagoService.getArchivoPorId(pagoId);
    }
}


