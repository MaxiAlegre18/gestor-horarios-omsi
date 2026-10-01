package com.aprendiendo.gestor_horarios_omsi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvColumnasFaltantes;
import com.aprendiendo.gestor_horarios_omsi.exceptions.CsvDatosIncorrectos;

@ExtendWith(MockitoExtension.class)
public class ProcesadorTurnosServiceOpenCsvUnitTests {

    @InjectMocks
    private ProcesadorTurnosServiceOpenCsv procesadorTurnosServiceOpenCsv;

    @Test
    public void procesarCsvValidoDevuelveArchivoCorrecto() throws Exception {

        MockMultipartFile archivo = generarCsvValido();

        byte[] bytesActuales = procesadorTurnosServiceOpenCsv.procesarArchivoYGenerarTurno("Coche 601", "Torcuato", 799,
                archivo);

        byte[] bytesEsperados = obtenerBytesEsperados("/csv_valido_601/Turno_Coche_601.txt");

        String textoActual = convertirBytesYNormalizar(bytesActuales);
        String textoEsperado = convertirBytesYNormalizar(bytesEsperados);

        assertEquals(textoEsperado, textoActual, "El contenido actual no coincide");
    }

    @Test
    public void procesarCsvConColumnaHorarioFaltante() throws Exception {

        MockMultipartFile archivo = generarCsvColumnaHorarioFaltante();

        assertThrows(CsvColumnasFaltantes.class,
                () -> procesadorTurnosServiceOpenCsv.procesarArchivoYGenerarTurno("Coche 601", "Torcuato", 799,
                        archivo));

    }

    @Test
    public void procesarCsvConColumnasHorarioYPerfilIntercambiadas() throws Exception {

        MockMultipartFile archivo = generarCsvColumnasHorarioPerfilCambiadas();

        assertThrows(CsvDatosIncorrectos.class,
                () -> procesadorTurnosServiceOpenCsv.procesarArchivoYGenerarTurno("Coche 601", "Torcuato", 799,
                        archivo));

    }

    @Test
    public void procesarCsvConMultiplesTurnosCorrectamente() throws Exception {

        MockMultipartFile archivo = generarCsvValidoConMultiplesTurnos();

        byte[] bytesActuales = procesadorTurnosServiceOpenCsv.procesarArchivoYGenerarMultiplesTurnos(archivo);

        byte[] bytesEsperados = obtenerBytesEsperados("/csv_valido_multiples_turnos/varios-turnos.txt");

        String textoActual = convertirBytesYNormalizar(bytesActuales);
        String textoEsperado = convertirBytesYNormalizar(bytesEsperados);

        assertEquals(textoEsperado, textoActual, "El contenido actual no coincide");
    }

    /*
     * 
     * ------- ------- UTILIDADES ------- -------
     * 
     */

    private MockMultipartFile generarCsvValido() throws Exception {
        return obtenerArchivoCsv("/csv_valido_601/turno-coche-601.csv", "turno-coche-601.csv");
    }

    private MockMultipartFile generarCsvValidoConMultiplesTurnos() throws Exception {
        return obtenerArchivoCsv("/csv_valido_multiples_turnos/varios-turnos.csv", "varios-turnos.csv");
    }

    private MockMultipartFile generarCsvColumnaHorarioFaltante() throws Exception {
        return obtenerArchivoCsv("/csv_invalidos/csv_columnas_faltantes_horario.csv", "csv_columnas_faltantes_horario");
    }

    private MockMultipartFile generarCsvColumnasHorarioPerfilCambiadas() throws Exception {
        return obtenerArchivoCsv("/csv_invalidos/csv_columnas_incorrectas_horario_perfil.csv",
                "csv_columnas_incorrectas_horario_perfil");
    }

    // funciones auxiliares generadas con IA

    private MockMultipartFile obtenerArchivoCsv(String direccion, String nombreArchivo) throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream(direccion)) {

            assertNotNull(inputStream, "No se encontró el archivo CSV en la ruta");
            return new MockMultipartFile("archivo", nombreArchivo, "text/csv", inputStream);
        }
    }

    private byte[] obtenerBytesEsperados(String ubicacion) throws Exception {
        ClassPathResource resource = new ClassPathResource(ubicacion);
        byte[] bytesEsperados;
        try (InputStream is = resource.getInputStream()) {
            bytesEsperados = is.readAllBytes();
        }
        return bytesEsperados;
    }

    private String convertirBytesYNormalizar(byte[] bytes) {
        String texto = new String(bytes, StandardCharsets.UTF_8);
        texto = texto.replace("\r\n", "\n");
        return texto;
    }

}
