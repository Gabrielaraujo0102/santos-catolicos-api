package com.example.santoscatolicosapitrabalho.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Santo {

    private int id;
    private String nome;
    private String pais;
    private int seculo;
    private String padroeiroDe;
    private boolean canonizado;

    public Santo() {
    }

    public Santo(int id, String nome, String pais, int seculo, String padroeiroDe, boolean canonizado) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.seculo = seculo;
        this.padroeiroDe = padroeiroDe;
        this.canonizado = canonizado;
    }
}
