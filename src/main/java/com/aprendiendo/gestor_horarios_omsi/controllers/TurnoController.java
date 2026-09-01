package com.aprendiendo.gestor_horarios_omsi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.services.ProcesadorTurnosService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/live")
    public String getMethodName() {
        return new String("Hola");
    }

    @PostMapping(value = "/generar-archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generarArchivoTurno(
            @RequestParam("nombre") String nombre,
            @RequestParam("garage") String garage,
            @RequestParam("jornada") int jornada,
            @RequestParam("archivo") MultipartFile archivo) {

        String nombreArchivo = archivo.getOriginalFilename();
        if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".csv")) {
            String mensajeError = "Error: El archivo subido no tiene formato .csv";
            return ResponseEntity
                    .badRequest()
                    .body(mensajeError.getBytes());
        }

        try {
            byte[] archivoGenerado = procesadorTurnosService.procesarArchivoYGenerarTurno(nombre, garage, jornada,
                    archivo);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            String nombreLimpio = nombre.replaceAll("\\s+", "_");
            String nombreArchivoDescarga = "Turno_" + nombreLimpio + ".txt";

            headers.setContentDispositionFormData("attachment", nombreArchivoDescarga);

            return new ResponseEntity<>(archivoGenerado, headers, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(("Error de validación: " + e.getMessage()).getBytes());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error interno del servidor: " + e.getMessage()).getBytes());
        }
    }

}
