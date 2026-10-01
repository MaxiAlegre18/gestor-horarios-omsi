package com.aprendiendo.gestor_horarios_omsi.services;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.model.TipoJornada;
import com.aprendiendo.gestor_horarios_omsi.model.Turno;
import com.aprendiendo.gestor_horarios_omsi.model.Viaje;
import com.aprendiendo.gestor_horarios_omsi.utils.FormatearTurnoUtil;

@Service
public class ProcesadorTurnosServiceImpl implements ProcesadorTurnosService {

    private final LectorCsvService procesadorCsvService;

    public ProcesadorTurnosServiceImpl(LectorCsvService procesadorCsvService) {
        this.procesadorCsvService = procesadorCsvService;
    }

    @Override
    public byte[] procesarArchivoYGenerarTurno(String nombre, String garage, int jornada, MultipartFile archivo) {
        Turno turno = new Turno(nombre, garage, TipoJornada.desdeInt(jornada));

        List<Viaje> viajesLeidos = procesadorCsvService.extraerViajesDelCsv(archivo);

        for (Viaje viaje : viajesLeidos) {
            turno.agregarViaje(viaje);
        }

        return FormatearTurnoUtil.turnoToBytes(turno);
    }

    @Override
    public byte[] procesarArchivoYGenerarMultiplesTurnos(MultipartFile archivo) {
        List<Turno> listaDeTurnos = procesadorCsvService.extraerTurnosDelCsv(archivo);
        StringBuilder sb = new StringBuilder();

        for (Turno turno : listaDeTurnos) {
            sb.append(FormatearTurnoUtil.turnoToString(turno));
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

}
