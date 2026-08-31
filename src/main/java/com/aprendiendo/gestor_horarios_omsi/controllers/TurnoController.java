package com.aprendiendo.gestor_horarios_omsi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.services.ProcesadorTurnosService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TurnoController {

    private final ProcesadorTurnosService procesadorTurnosService;

    // Bean procesadorturnosservice inyectado por Spring
    public TurnoController(ProcesadorTurnosService procesadorTurnosService) {
        this.procesadorTurnosService = procesadorTurnosService;
    }

    @GetMapping("/live")
    public String getMethodName() {
        return new String("Hola");
    }

    @PostMapping("/generar-archivo")
    public ResponseEntity<byte[]> generarArchivoTurno(
            @RequestParam("nombre") String nombre,
            @RequestParam("garage") String garage,
            @RequestParam("jornada") int jornada,
            @RequestParam("archivo") MultipartFile archivo) {

        String nombreArchivo = archivo.getOriginalFilename();

        if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".csv")) {
            String mensajeError = "Error: El archivo subido no tiene formato .csv";
            return ResponseEntity.badRequest().body(mensajeError.getBytes());
        }
        try {
            byte[] archivoGenerado = procesadorTurnosService.procesarArchivoYGenerarTurno(nombre, garage, jornada,
                    archivo);

            return ResponseEntity.ok().body(archivoGenerado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(("Error al procesar :" + e.getMessage()).getBytes());
        }
    }

}
