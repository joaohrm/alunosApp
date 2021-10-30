package com.bethaCode.alunos.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class NotaDTO {

    private String Nota;
    private String dataNota;
    private Integer idAluno;
    private Integer idDisciplina;

    public NotaDTO(){

    }

}
