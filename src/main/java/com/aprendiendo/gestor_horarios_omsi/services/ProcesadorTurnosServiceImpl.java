package com.aprendiendo.gestor_horarios_omsi.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
        List<Viaje> viajesExtraidos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    numeroLinea++;
                    continue;
                }

                String[] columnas = linea.split(",");

                if (columnas.length != 3) {
                    throw new IllegalArgumentException("Error en la línea " + numeroLinea +
                            ": Se esperaban 3 columnas, pero se encontraron " + columnas.length);
                }

                try {
                    extraerViaje(viajesExtraidos, columnas);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Error en la línea " + numeroLinea +
                            ": El perfil debe ser un número entero.");
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Error en la línea " + numeroLinea +
                            ": La hora debe tener el formato HH:mm (ej. 14:30).");
                }

                numeroLinea++;
            }

        } catch (Exception e) {
            throw new RuntimeException("Error fatal al leer el archivo CSV: " + e.getMessage(), e);
        }

        return viajesExtraidos;
    }

    private void extraerViaje(List<Viaje> viajesExtraidos, String[] columnas) {
        String nombreUnico = columnas[0].trim();
        int numeroPerfil = Integer.parseInt(columnas[1].trim());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime horaInicio = LocalTime.parse(columnas[2].trim(), formatter);

        Viaje nuevoViaje = new Viaje(nombreUnico, numeroPerfil, horaInicio);
        viajesExtraidos.add(nuevoViaje);
    }

}
