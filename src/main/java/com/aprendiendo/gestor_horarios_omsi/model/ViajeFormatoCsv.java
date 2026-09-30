package com.aprendiendo.gestor_horarios_omsi.model;

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

    @CsvBindByPosition(position = 0)
    private String nombreUnico;
    @CsvBindByPosition(position = 1)
    private int numeroPerfil;
    @CsvBindByPosition(position = 2)
    @CsvDate("H:mm")
    private LocalTime horaInicio;

}
