package com.aprendiendo.gestor_horarios_omsi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.services.ProcesadorTurnosService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    private final ProcesadorTurnosService procesadorTurnosService;

    // Bean procesadorturnosservice inyectado por Spring
    public TurnoController(ProcesadorTurnosService procesadorTurnosService) {
        this.procesadorTurnosService = procesadorTurnosService;
    }

    @PostMapping(value = "/generar-archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generarArchivoTurno(
            @RequestParam("nombre") String nombreTurno,
            @RequestParam("garage") String garage,
            @RequestParam("jornada") int jornada,
            @RequestParam("archivo") MultipartFile archivo) {

        validarFormatoCsv(archivo);

        byte[] archivoGenerado = procesadorTurnosService.procesarArchivoYGenerarTurno(nombreTurno, garage, jornada,
                archivo);

        return new ResponseEntity<>(archivoGenerado, armarHttpHeaders(nombreTurno), HttpStatus.OK);
    }

    @PostMapping(value = "/generar-archivo-multiples", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generarArchivoConMultiplesTurnos(
            @RequestBody MultipartFile archivo) {

        validarFormatoCsv(archivo);

        byte[] archivoGenerado = procesadorTurnosService.procesarArchivoYGenerarMultiplesTurnos(archivo);

        return new ResponseEntity<>(archivoGenerado, armarHttpHeaders("jornada"), HttpStatus.OK);
    }

    private void validarFormatoCsv(MultipartFile archivo) {
        String nombreArchivo = archivo.getOriginalFilename();
        if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("El archivo subido no tiene formato .csv");
        }
    }

    private HttpHeaders armarHttpHeaders(String nombre) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        headers.setContentDispositionFormData("attachment", formatearNombreArchivo(nombre));

        return headers;
    }

    private String formatearNombreArchivo(String nombre) {
        String nombreLimpio = nombre.replaceAll("\\s+", "_");
        String nombreArchivoDescarga = "Turno_" + nombreLimpio + ".txt";

        return nombreArchivoDescarga;
    }

}
