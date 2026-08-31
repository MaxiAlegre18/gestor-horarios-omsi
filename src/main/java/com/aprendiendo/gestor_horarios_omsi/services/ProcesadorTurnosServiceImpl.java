package com.aprendiendo.gestor_horarios_omsi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.model.TipoJornada;
import com.aprendiendo.gestor_horarios_omsi.model.Turno;
import com.aprendiendo.gestor_horarios_omsi.model.Viaje;
import com.aprendiendo.gestor_horarios_omsi.utils.FormatearTurnoUtil;

@Service
public class ProcesadorTurnosServiceImpl implements ProcesadorTurnosService {

    @Override
    public byte[] procesarArchivoYGenerarTurno(String nombre, String garage, int jornada, MultipartFile archivo) {

        Turno turno = new Turno(nombre, garage, TipoJornada.desdeInt(jornada));

        List<Viaje> viajesLeidos = extraerViajesDeCsv(archivo);

        for (Viaje viaje : viajesLeidos) {
            turno.agregarViaje(viaje);
        }

        return FormatearTurnoUtil.formatearTurno(turno);
    }

    private List<Viaje> extraerViajesDeCsv(MultipartFile archivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extraerViajesDeCsv'");
    }

}
