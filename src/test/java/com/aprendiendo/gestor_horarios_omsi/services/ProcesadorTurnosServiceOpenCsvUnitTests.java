package com.aprendiendo.gestor_horarios_omsi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

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

    public void procesarCsvConColumnaHorarioFaltante() throws Exception {

    }

    private MockMultipartFile generarCsvValido() throws Exception {
        return obtenerArchivoCsv("/csv_valido_601/turno-coche-601.csv", "turno-coche-601.csv");
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
