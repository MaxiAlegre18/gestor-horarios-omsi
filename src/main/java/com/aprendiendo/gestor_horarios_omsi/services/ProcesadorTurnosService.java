package com.aprendiendo.gestor_horarios_omsi.services;

import org.springframework.web.multipart.MultipartFile;

public interface ProcesadorTurnosService {

    byte[] procesarArchivoYGenerarTurno(String nombre, String garage, int jornada, MultipartFile archivo);

    byte[] procesarArchivoYGenerarMultiplesTurnos(MultipartFile archivo);
}
