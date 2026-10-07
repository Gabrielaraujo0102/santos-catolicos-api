package com.example.santoscatolicosapitrabalho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Santo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String pais;
    private int seculo;
    private String padroeiroDe;
    private boolean canonizado;

    public Santo() {
    }

    public Santo(String nome, String pais, int seculo, String padroeiroDe, boolean canonizado) {
        this.nome = nome;
        this.pais = pais;
        this.seculo = seculo;
        this.padroeiroDe = padroeiroDe;
        this.canonizado = canonizado;
    }
}
