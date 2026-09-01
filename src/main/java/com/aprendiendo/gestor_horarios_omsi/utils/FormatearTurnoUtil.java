package com.aprendiendo.gestor_horarios_omsi.utils;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

import com.aprendiendo.gestor_horarios_omsi.model.Turno;
import com.aprendiendo.gestor_horarios_omsi.model.Viaje;

public class FormatearTurnoUtil {

    // Salto de linea especial para los archivos de OMSI
    private static final String SALTO_LINEA = "\r\n";
    private static final String SEPARADOR = "------------------------------------";
    private static StringBuilder sb = new StringBuilder();

    public static byte[] formatearTurno(Turno turno) {
        armarCabeceraTurno(turno);

        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

        for (Viaje viaje : turno.getListaViajes()) {
            armarViaje(formatterHora, viaje);
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static void armarCabeceraTurno(Turno turno) {
        sb.append(SEPARADOR).append(SALTO_LINEA);
        sb.append(SALTO_LINEA);
        sb.append("[newtour]").append(SALTO_LINEA);
        sb.append(turno.getNombre()).append(SALTO_LINEA);
        sb.append(turno.getGarage()).append(SALTO_LINEA);
        // Extraemos el código numérico del Enum (ej. 799)
        sb.append(turno.getTipoJornada().getNumeroTipoJornada()).append(SALTO_LINEA);
        sb.append(SALTO_LINEA);
        sb.append(SEPARADOR).append(SALTO_LINEA);
        sb.append(SALTO_LINEA);
    }

    private static void armarViaje(DateTimeFormatter formatterHora, Viaje viaje) {
        String horaFormateada = viaje.getHoraInicio().format(formatterHora) + ":00";
        sb.append("  Dep.: ").append(horaFormateada).append(SALTO_LINEA);
        sb.append("[addtrip]").append(SALTO_LINEA);
        sb.append(viaje.getNombreUnico()).append(SALTO_LINEA);
        sb.append(viaje.getNumeroPerfil()).append(SALTO_LINEA);
        sb.append(viaje.getHoraInicioEnMinutos()).append(SALTO_LINEA);
        sb.append(SALTO_LINEA);
    }
}
