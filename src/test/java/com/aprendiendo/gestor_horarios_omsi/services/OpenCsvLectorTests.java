package com.aprendiendo.gestor_horarios_omsi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvColumnasFaltantes;
import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvDatosIncorrectos;
import com.aprendiendo.gestor_horarios_omsi.model.TipoJornada;
import com.aprendiendo.gestor_horarios_omsi.model.Turno;
import com.aprendiendo.gestor_horarios_omsi.model.Viaje;

@ExtendWith(MockitoExtension.class)
public class OpenCsvLectorTests {

    @InjectMocks
    private OpenCsvLectorService openCsvLectorService;

    private MockMultipartFile csvTurnoValido;
    private MockMultipartFile csvMultiplesTurnosValido;
    private MockMultipartFile csvTurnoColumnaFaltante;
    private MockMultipartFile csvTurnoColumnasHorarioPerfilCambiadas;

    @Test
    public void extraerViajesDelCsvCorrectamente() {
        csvTurnoValido = generarCsvValido();

        List<Viaje> viajesEsperados = List.of(
                new Viaje("501_Lemos-Fonavi", 1, LocalTime.of(5, 0)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(5, 50)),
                new Viaje("501_Lemos-Fonavi", 0, LocalTime.of(6, 20)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(7, 20)));

        assertEquals(viajesEsperados, openCsvLectorService.extraerViajesDelCsv(csvTurnoValido));
    }

    @Test
    public void extraerTurnosDelCsvCorrectamente() {
        csvMultiplesTurnosValido = generarCsvValidoConMultiplesTurnos();

        List<Viaje> viajesPrimerTurno = List.of(
                new Viaje("501_Lemos-Fonavi", 1, LocalTime.of(5, 0)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(5, 50)),
                new Viaje("501_Lemos-Fonavi", 0, LocalTime.of(6, 20)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(7, 20)));

        List<Viaje> viajesSegundoTurno = List.of(
                new Viaje("501_Lemos-Fonavi", 1, LocalTime.of(5, 10)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(6, 0)),
                new Viaje("501_Lemos-Fonavi", 0, LocalTime.of(6, 30)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(7, 30)));

        List<Turno> turnosEsperados = List.of(
                new Turno("Coche 601", "Torcuato", TipoJornada.desdeInt(799), viajesPrimerTurno),
                new Turno("Coche 602", "Torcuato", TipoJornada.desdeInt(799), viajesSegundoTurno));

        assertEquals(turnosEsperados, openCsvLectorService.extraerTurnosDelCsv(csvMultiplesTurnosValido));
    }

    @Test
    public void extraerViajesDelCsvConColumnasFaltantesArrojaExcepcion() {
        csvTurnoColumnaFaltante = generarCsvColumnaHorarioFaltante();

        assertThrows(CsvColumnasFaltantes.class,
                () -> openCsvLectorService.extraerViajesDelCsv(csvTurnoColumnaFaltante));
    }

    @Test
    public void extraerViajesDelCsvConColumnasIntercambiadasArrojaExcepcion() {
        csvTurnoColumnasHorarioPerfilCambiadas = generarCsvColumnasHorarioPerfilCambiadas();

        assertThrows(CsvDatosIncorrectos.class,
                () -> openCsvLectorService.extraerViajesDelCsv(csvTurnoColumnasHorarioPerfilCambiadas));
    }

    /*
     * UTILIDADES
     */

    private MockMultipartFile generarCsvValido() {
        return obtenerArchivoCsv("/csv_valido_601/turno-coche-601.csv", "turno-coche-601.csv");
    }

    private MockMultipartFile generarCsvValidoConMultiplesTurnos() {
        return obtenerArchivoCsv("/csv_valido_multiples_turnos/varios-turnos.csv", "varios-turnos.csv");
    }

    private MockMultipartFile generarCsvColumnaHorarioFaltante() {
        return obtenerArchivoCsv("/csv_invalidos/csv_columnas_faltantes_horario.csv", "csv_columnas_faltantes_horario");
    }

    private MockMultipartFile generarCsvColumnasHorarioPerfilCambiadas() {
        return obtenerArchivoCsv("/csv_invalidos/csv_columnas_incorrectas_horario_perfil.csv",
                "csv_columnas_incorrectas_horario_perfil");
    }

    private MockMultipartFile obtenerArchivoCsv(String direccion, String nombreArchivo) {
        try (InputStream inputStream = getClass().getResourceAsStream(direccion)) {

            assertNotNull(inputStream, "No se encontró el archivo CSV en la ruta");
            return new MockMultipartFile("archivo", nombreArchivo, "text/csv", inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
