package com.aprendiendo.gestor_horarios_omsi.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvColumnasFaltantes;
import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvDatosIncorrectos;
import com.aprendiendo.gestor_horarios_omsi.model.TipoJornada;
import com.aprendiendo.gestor_horarios_omsi.model.Turno;
import com.aprendiendo.gestor_horarios_omsi.model.Viaje;
import com.aprendiendo.gestor_horarios_omsi.model.ViajeFormatoCsv;
import com.aprendiendo.gestor_horarios_omsi.utils.FormatearTurnoUtil;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Service
@Primary
public class ProcesadorTurnosServiceOpenCsv implements ProcesadorTurnosService {

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
        /*
         * Se abre el flujo de datos del archivo MultipartFile. (try-with-resources,
         * Java cierra automaticamente el archivo)
         */
        try (Reader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {

            // Establecemos la estrategia de mapeo tomando de referencia una clase que
            // representa una fila del archivo CSV
            // La estrategia de mapeo es por la posicion de columnas, para que no sea
            // necesario poner una fila de cabecera
            ColumnPositionMappingStrategy<ViajeFormatoCsv> estrategiaMapeo = new ColumnPositionMappingStrategy<>();

            // Se establece que las filas leidas deben convertirse inicialmente en
            // instancias de la clase ViajeFormatoCsv
            estrategiaMapeo.setType(ViajeFormatoCsv.class);

            // Configura como se va a leer el archivo y se inyecta "csvToBean" al reader con
            // la configuración dada
            CsvToBean<ViajeFormatoCsv> csvToBean = new CsvToBeanBuilder<ViajeFormatoCsv>(reader)
                    .withMappingStrategy(estrategiaMapeo).withIgnoreEmptyLine(true).withThrowExceptions(true).build();

            // csvToBean.parse() ejecuta la lectura del CSV y devuelve una
            // List<ViajeFormatoCsv>
            return csvToBean.parse().stream().map(csvLine -> Viaje.builder()
                    .nombreUnico(csvLine.getNombreUnico())
                    .numeroPerfil(csvLine.getNumeroPerfil())
                    .horaInicio(csvLine.getHoraInicio())
                    .build()).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Error fatal al leer el archivo CSV: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            Throwable causaReal = e.getCause();

            if (causaReal instanceof CsvRequiredFieldEmptyException) {
                throw new CsvColumnasFaltantes("Faltan columnas obligatorias en el CSV", causaReal);
            }

            if (causaReal instanceof CsvDataTypeMismatchException) {
                throw new CsvDatosIncorrectos("El archivo contiene tipos de datos incorrectos", causaReal);
            }

            throw e;
        }
    }

}
