package com.aprendiendo.gestor_horarios_omsi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvColumnasFaltantes;
import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvDatosIncorrectos;
import com.aprendiendo.gestor_horarios_omsi.model.TipoJornada;
import com.aprendiendo.gestor_horarios_omsi.model.Turno;
import com.aprendiendo.gestor_horarios_omsi.model.Viaje;

public class OpenCsvLectorTests {

    private OpenCsvLectorService openCsvLectorService;

    @BeforeEach
    public void setUp() {
        openCsvLectorService = new OpenCsvLectorService();
    }

    @Test
    public void extraerViajesDelCsvCorrectamente() throws IOException {
        MockMultipartFile csvTurnoValido = obtenerArchivoCsv("/csv_valido_601/turno-coche-601.csv",
                "turno-coche-601.csv");

        List<Viaje> viajesEsperados = List.of(
                new Viaje("501_Lemos-Fonavi", 1, LocalTime.of(5, 0)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(5, 50)),
                new Viaje("501_Lemos-Fonavi", 0, LocalTime.of(6, 20)),
                new Viaje("501_Fonavi-Lemos", 0, LocalTime.of(7, 20)));

        assertEquals(viajesEsperados, openCsvLectorService.extraerViajesDelCsv(csvTurnoValido));
    }

    @Test
    public void extraerTurnosDelCsvCorrectamente() throws IOException {
        MockMultipartFile csvMultiplesTurnosValido = obtenerArchivoCsv("/csv_valido_multiples_turnos/varios-turnos.csv",
                "varios-turnos.csv");

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
    public void extraerViajesDelCsvConColumnasFaltantesArrojaExcepcion() throws IOException {
        MockMultipartFile csvTurnoColumnaFaltante = obtenerArchivoCsv(
                "/csv_invalidos/csv_columnas_faltantes_horario.csv", "csv_columnas_faltantes_horario");

        assertThrows(CsvColumnasFaltantes.class,
                () -> openCsvLectorService.extraerViajesDelCsv(csvTurnoColumnaFaltante));
    }

    @Test
    public void extraerViajesDelCsvConColumnasIntercambiadasArrojaExcepcion() throws IOException {
        MockMultipartFile csvTurnoColumnasHorarioPerfilCambiadas = obtenerArchivoCsv(
                "/csv_invalidos/csv_columnas_incorrectas_horario_perfil.csv",
                "csv_columnas_incorrectas_horario_perfil");

        assertThrows(CsvDatosIncorrectos.class,
                () -> openCsvLectorService.extraerViajesDelCsv(csvTurnoColumnasHorarioPerfilCambiadas));
    }

    /*
     * UTILIDADES
     */

    private MockMultipartFile obtenerArchivoCsv(String direccion, String nombreArchivo) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(direccion)) {

            assertNotNull(inputStream, "No se encontró el archivo CSV en la ruta");
            return new MockMultipartFile("archivo", nombreArchivo, "text/csv", inputStream);
        }
    }

}
