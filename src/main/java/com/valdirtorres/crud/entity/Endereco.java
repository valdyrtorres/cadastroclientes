package com.valdirtorres.crud.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {

	private String logradouro;
    private String CEP;
    private String cidade;
    private String UF;
    private String pais;
    
}

