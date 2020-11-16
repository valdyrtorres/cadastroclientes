package com.valdirtorres.crud.data.vo;

import java.io.Serializable;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.valdirtorres.crud.entity.Cliente;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"id", "nome", "CPF", "endereco"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClienteVO implements Serializable {
	
	private static final long serialVersionUID = -1379001966879414523L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("CPF")
	private String CPF;
	
	@JsonProperty("endereco")
	private String endereco;
	
	public static ClienteVO create(Cliente cliente) {
		return new ModelMapper().map(cliente, ClienteVO.class);
	}

}
