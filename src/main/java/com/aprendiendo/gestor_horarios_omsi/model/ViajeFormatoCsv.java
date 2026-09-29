package com.aprendiendo.gestor_horarios_omsi.model;

import java.time.LocalTime;

import com.opencsv.bean.CsvBindByName;
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

    @CsvBindByName(column = "nombreUnico")
    private String nombreUnico;
    @CsvBindByName(column = "numeroPerfil")
    private int numeroPerfil;
    @CsvBindByName(column = "horaInicio")
    @CsvDate("H:mm")
    private LocalTime horaInicio;

}
