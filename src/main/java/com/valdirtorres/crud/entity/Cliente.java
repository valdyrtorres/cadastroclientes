package com.valdirtorres.crud.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.valdirtorres.crud.data.vo.ClienteVO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cliente implements Serializable {

	private static final long serialVersionUID = -8867537884216350670L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 255)
	private String nome;
	
	/*
	 * O tipo String foi utilizado, pois o CPF não é uma quantidade. É um identificador 
	 * que pode até mesmo mudar aceitando letras conforme aconteceu com RG. Nesse sentido,
	 *  A semântica correta para este dado é String afinal ele é descritivo. 
	 */
	@Column(name = "CPF", nullable = false, length = 11)
	private String CPF;
	
	@Column(name = "endereco", nullable = false, length = 255)
	private String endereco;
	
	public static Cliente create(ClienteVO clienteVO) {
		return new ModelMapper().map(clienteVO, Cliente.class);
	}

}
