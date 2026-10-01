package com.aprendiendo.gestor_horarios_omsi.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.model.Turno;
import com.aprendiendo.gestor_horarios_omsi.model.Viaje;

public interface LectorCsvService {

    public List<Viaje> extraerViajesDelCsv(MultipartFile archivo);

    public List<Turno> extraerTurnosDelCsv(MultipartFile archivo);

}
