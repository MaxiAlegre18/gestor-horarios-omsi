package com.aprendiendo.gestor_horarios_omsi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import com.aprendiendo.gestor_horarios_omsi.utils.FormatearTurnoUtil;

public class ProcesadorTurnosServiceUnitTests {

    private ProcesadorTurnosServiceImpl procesadorTurnosServiceImpl;

    @BeforeEach
    void setUp() {
        /*
         * Al hacerlo de esta manera, se ignora la inyeccion de depdencias de Spring
         */
        procesadorTurnosServiceImpl = new ProcesadorTurnosServiceImpl();
        /*
         * Como esta clase es estatica y las variables sobreviven en tiempo de ejecucion
         * Debemos limpiar las variables de estado ya que si no, se ensucian los tests
         * Esto debe cambiarse a futuro
         */
        FormatearTurnoUtil.limpiarEstado();
    }

    @Test
    public void procesarCsvValidoDevuelveArchivoCorrecto() throws Exception {

        MockMultipartFile archivo = generarCsvValido();

        byte[] bytesActuales = procesadorTurnosServiceImpl.procesarArchivoYGenerarTurno("Coche 601", "Torcuato", 799,
                archivo);

        byte[] bytesEsperados = obtenerBytesEsperados("/csv_valido_601/Turno_Coche_601.txt");

        String textoActual = convertirBytesYNormalizar(bytesActuales);
        String textoEsperado = convertirBytesYNormalizar(bytesEsperados);

        assertEquals(textoEsperado, textoActual, "El contenido actual no coincide");
    }

    @Test
    public void procesarCsvValidoDevuelveArchivoIncorrecto() throws Exception {

        MockMultipartFile archivo = generarCsvValido();

        byte[] bytesActuales = procesadorTurnosServiceImpl.procesarArchivoYGenerarTurno("Coche 601", "Torcuato", 799,
                archivo);

        byte[] bytesEsperados = obtenerBytesEsperados("/csv_valido_601/Turno_Coche_601_horarios_incorrectos.txt");

        String textoActual = convertirBytesYNormalizar(bytesActuales);
        String textoEsperado = convertirBytesYNormalizar(bytesEsperados);

        assertNotEquals(textoEsperado, textoActual, "El contenido actual coincide (no esperado)");

    }

    // funciones generadas con IA

    private MockMultipartFile generarCsvValido() throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream("/csv_valido_601/turno-coche-601.csv")) {

            assertNotNull(inputStream, "No se encontró el archivo CSV en la ruta");
            return new MockMultipartFile("archivo", "turno-coche-601.csv", "text/csv", inputStream);
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
