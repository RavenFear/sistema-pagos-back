package com.systempaymentsunisalle.sistema_pagos_backend_unisalle.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Estudiante;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.entities.Pago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.StatusPago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.enums.TypePago;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.EstudianteRepository;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.repository.PagoRepository;
import com.systempaymentsunisalle.sistema_pagos_backend_unisalle.services.PagoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class PagoController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes(){
        return estudianteRepository.findAll();
    }

    //Metodo que devuelva un estudiante en especifico segun su codigo
    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listarEstudiantesPorCodigo(@PathVariable String codigo){
        return estudianteRepository.findByCodigo(codigo);
    }
    
    //Metodo que lista estudiantes segun el programa academico
    @GetMapping("/estudiantesPorPrograma/{programaId}")
    public List<Estudiante> listarEstudiantesPorPrograma(@RequestParam String programaId){
        return estudianteRepository.findByProgramaId(programaId);
    }

    //Metodo que devuelve una lista con todos los pagos registrados
    @GetMapping("/pagos")
    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    //Metodo que devuelve un pago en especifico por su id
    @GetMapping("/pagos{id}")
    public Pago listarPagoPorId(@PathVariable String id){
        return pagoRepository.findById(id).get();
    }

    //Metodo que lista los pagos hechos por un estudiante segun codigo
    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo){
        return pagoRepository.findByEstudianteCodigo(codigo);
    }

    //Metodo para listar los pagos segun su estado (CREADO, VALIDADO, RECHAZADO)
    @GetMapping("/pagosPorStatus")
    public List<Pago> listarPagosPorStatus(@RequestParam StatusPago status){
        return pagoRepository.findByStatus(status);
    }

    //Metodo que lista los pagos segun su tipo (EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO)
    @GetMapping("/pagos/porTipo")
    public List<Pago> listarPagosPorType(@RequestParam TypePago type){
        return pagoRepository.findByType(type);
    }

     //Metodo para actualizar el estado de un pago
     @PutMapping("pagos/{pagoId}/actualizarpago")
     public Pago actualizarStatusPago(@RequestParam StatusPago status, @PathVariable String  pagoId){
         return pagoService.actualizarPagoPorStatus(status, pagoId);
     }

     //Metodo para registrar un pago con un archivo adjunto (comprobante de pago)
     @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     public Pago guardarPago(
        @RequestParam("file") MultipartFile file, //archivo adjunto
        double monto, //monto del pago
        TypePago type,
        LocalDate date,
        String codigoEstudiante) throws IOException{
            return pagoService.savePago(file, monto, type, date, codigoEstudiante);//Guarda el pago en la base de datos
        }

    //Metodo para descargar un archivo de pago
    //Metodo que lista los pagos segun tipo (EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO)
    @GetMapping(value = "/pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte [] listarArchivoPorId(@PathVariable String pagoId) throws IOException{
        return pagoService.getArchivoPorId(pagoId);
    }
}


