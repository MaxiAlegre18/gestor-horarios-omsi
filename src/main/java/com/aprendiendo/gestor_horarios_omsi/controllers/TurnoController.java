package com.aprendiendo.gestor_horarios_omsi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.services.ProcesadorTurnosService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
            @RequestParam("nombre") String nombre,
            @RequestParam("garage") String garage,
            @RequestParam("jornada") int jornada,
            @RequestParam("archivo") MultipartFile archivo) {

        if (formatoInvalido(archivo.getOriginalFilename())) {
            throw new IllegalArgumentException("El archivo subido no tiene formato csv");
        }

        byte[] archivoGenerado = procesadorTurnosService.procesarArchivoYGenerarTurno(nombre, garage, jornada,
                archivo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        String nombreLimpio = nombre.replaceAll("\\s+", "_");
        String nombreArchivoDescarga = "Turno_" + nombreLimpio + ".txt";

        headers.setContentDispositionFormData("attachment", nombreArchivoDescarga);

        return new ResponseEntity<>(archivoGenerado, headers, HttpStatus.OK);
    }

    private boolean formatoInvalido(String nombreArchivo) {
        return nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".csv");
    }

}
