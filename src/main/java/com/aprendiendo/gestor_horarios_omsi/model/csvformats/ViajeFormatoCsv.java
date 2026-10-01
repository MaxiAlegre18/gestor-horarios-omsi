package com.aprendiendo.gestor_horarios_omsi.model.csvformats;

import java.time.LocalTime;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViajeFormatoCsv {

    @CsvBindByPosition(position = 0, required = true)
    private String nombreUnico;
    @CsvBindByPosition(position = 1, required = true)
    private int numeroPerfil;
    @CsvBindByPosition(position = 2, required = true)
    @CsvDate("H:mm")
    private LocalTime horaInicio;

}
