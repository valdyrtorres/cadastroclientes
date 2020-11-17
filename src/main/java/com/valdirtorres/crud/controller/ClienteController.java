package com.valdirtorres.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.valdirtorres.crud.data.vo.ClienteVO;
import com.valdirtorres.crud.services.ClienteService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	private final ClienteService clienteService;
	private final PagedResourcesAssembler<ClienteVO> assembler;
	
	@Autowired
	public ClienteController(ClienteService clienteService, PagedResourcesAssembler<ClienteVO> assembler) {
		this.clienteService = clienteService;
		this.assembler = assembler;
	}
	
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml" })
	public ClienteVO findById(@PathVariable("id") Long id) {
		ClienteVO clienteVO = clienteService.findById(id);
		clienteVO.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
		
		return clienteVO;
	}
	
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
		
		Page<ClienteVO> clientes = clienteService.findAll(pageable);
		
		clientes.stream()
			.forEach(p -> p.add(linkTo(methodOn(ClienteController.class).findById(p.getId())).withSelfRel()));
		
		PagedModel<EntityModel<ClienteVO>> pagedModel = assembler.toModel(clientes);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml" },
				 consumes = {"application/json", "application/xml", "application/x-yaml" })
	public ClienteVO create(@RequestBody ClienteVO clienteVO) {
		ClienteVO cliVo = clienteService.create(clienteVO);
		cliVo.add(linkTo(methodOn(ClienteController.class).findById(cliVo.getId())).withSelfRel());
		return cliVo;
	}
	
	@PutMapping(produces = {"application/json", "application/xml", "application/x-yaml" },
			 consumes = {"application/json", "application/xml", "application/x-yaml" })
	public ClienteVO update(@RequestBody ClienteVO clienteVO) {
		ClienteVO cliVo = clienteService.update(clienteVO);
		cliVo.add(linkTo(methodOn(ClienteController.class).findById(clienteVO.getId())).withSelfRel());
		return cliVo;
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		clienteService.delete(id);
		return ResponseEntity.ok().build();
	}
}
