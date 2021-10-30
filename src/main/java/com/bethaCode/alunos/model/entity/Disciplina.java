package com.bethaCode.alunos.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Deve ser informada a descrição da disciplina!")
    @Column(nullable = false, length = 100)
    private String descricao;

    @Min(value = 1, message = "Deve ser informado o número de horas!")
    @Max(value = 100, message = "A disciplina não pode possuir mais de 100 horas")
    @Column(name = "numero_horas")
    private Integer numeroHoras;

}
