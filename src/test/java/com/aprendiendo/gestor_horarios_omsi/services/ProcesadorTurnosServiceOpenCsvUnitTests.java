package com.aprendiendo.gestor_horarios_omsi.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ExtendWith(MockitoExtension.class)
public class ProcesadorTurnosServiceOpenCsvUnitTests {

    @MockitoBean
    private OpenCsvLectorService openCsvLectorService;

    @InjectMocks
    private ProcesadorTurnosServiceImpl procesadorTurnosServiceImpl;

    @Test
    public void procesarCsvYGenerarTurnosCorrectamente() throws Exception {

    }

    @Test
    public void procesarCsvYGenerarMultiplesTurnosCorrectamente() {

    }

    /*
     * UTILIDADES
     */

    /* */

    @SuppressWarnings("unused")
    private MockMultipartFile obtenerArchivoCsv(String direccion, String nombreArchivo) throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream(direccion)) {

            assertNotNull(inputStream, "No se encontró el archivo CSV en la ruta");
            return new MockMultipartFile("archivo", nombreArchivo, "text/csv", inputStream);
        }
    }

    @SuppressWarnings("unused")
    private byte[] obtenerBytesEsperados(String ubicacion) throws Exception {
        ClassPathResource resource = new ClassPathResource(ubicacion);
        byte[] bytesEsperados;
        try (InputStream is = resource.getInputStream()) {
            bytesEsperados = is.readAllBytes();
        }
        return bytesEsperados;
    }

    @SuppressWarnings("unused")
    private String convertirBytesYNormalizar(byte[] bytes) {
        String texto = new String(bytes, StandardCharsets.UTF_8);
        texto = texto.replace("\r\n", "\n");
        return texto;
    }

}
