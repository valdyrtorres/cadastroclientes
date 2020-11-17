package com.valdirtorres.crud;


import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

class CrudClientesTests {

	@Test
	public void testeListagemDeClientes() {
		Response response = RestAssured.request(Method.GET, "http://localhost:8081/crud-clientes/cliente");
		
		Assert.assertTrue("A listagem dos clientes não está funcionando, pois o status code deveria ser 200", response.statusCode() == 200);
		
	}
	
	@Test
	public void deveCriarCliente() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\r\n"
					+ "	\"nome\": \"Steve Rogers\",\r\n"
					+ "	\"CPF\": \"55544433309\",\r\n"
					+ "	\"endereco\": \"Rua da Assembléia, 55, CEP: 110002-221, Rio de Janeiro, RJ - Brasil\"\r\n"
					+ "}")
		.when()
			.post("http://localhost:8081/crud-clientes/cliente")
		.then()
			.log().all()
			.statusCode(201);
	}
	
	@Test
	public void naoDeveSalvarCPFMaiorQue11Caracteres() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\r\n"
					+ "	\"nome\": \"Roberto da Costa\",\r\n"
					+ "	\"CPF\": \"555444333092222222\",\r\n"
					+ "	\"endereco\": \"Rua do Carmo, 55, CEP: 110002-221, Rio de Janeiro, RJ - Brasil\"\r\n"
					+ "}")
		.when()
			.post("http://localhost:8081/crud-clientes/cliente")
		.then()
			.log().all()
			.statusCode(500);
	}
	
	@Test
	public void deveAlterarCliente() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\r\n"
					+ "	\"id\": 1,\r\n"
					+ "	\"nome\": \"Usuário Alterado\",\r\n"
					+ "	\"CPF\": \"77777777777\",\r\n"
					+ "	\"endereco\": \"Av Nsra Copacabana, 412, CEP: 20222-120, Rio de Janeiro, RJ - Brasil\"\r\n"
					+ "}")
		.when()
			.put("http://localhost:8081/crud-clientes/cliente")
		.then()
			.log().all()
			.statusCode(200)
			.body("id",  is(1))
			.body("nome",  is("Usuário Alterado"))
			.body("CPF",  is("77777777777"));
	}

	/*
	@Test
	public void deveRemoverCliente() {
		given()
			.log().all()
		.when()
			.delete("http://localhost:8081/crud-clientes/cliente/1")
		.then()
			.log().all()
			.statusCode(204)
		;
	}
	*/
	
	@Test
	public void naoDeveRemoverClienteInexistente() {
		given()
			.log().all()
		.when()
			.delete("http://localhost:8081/crud-clientes/cliente/4000")
		.then()
			.log().all()
			.statusCode(400)
		;
	}
}
