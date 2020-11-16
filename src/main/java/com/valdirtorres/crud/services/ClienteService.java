package com.valdirtorres.crud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.valdirtorres.crud.data.vo.ClienteVO;
import com.valdirtorres.crud.entity.Cliente;
import com.valdirtorres.crud.exception.ResourceNorFoundException;
import com.valdirtorres.crud.repository.ClienteRepository;

@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public ClienteVO Create(ClienteVO clienteVO) {
		ClienteVO clienteVoRetorno = ClienteVO.create(clienteRepository.save(Cliente.create(clienteVO)));
		return clienteVoRetorno;
	}
	
	public Page<ClienteVO> findAll(Pageable pageable) {
		var page = clienteRepository.findAll(pageable);
		return page.map(this::convertToClienteVO);
	}
	
	private ClienteVO convertToClienteVO(Cliente cliente) {
		return ClienteVO.create(cliente);
	}
	
	public ClienteVO findById(Long id) {
		var entity = clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNorFoundException("No records found for this ID"));
		return ClienteVO.create(entity);
	}
	
	public ClienteVO update(ClienteVO clienteVO) {
		final Optional<Cliente> optionalCliente = clienteRepository.findById(clienteVO.getId());
		
		if(!optionalCliente.isPresent()) {
			new ResourceNorFoundException("No records found for this ID");
		}
		
		return ClienteVO.create(clienteRepository.save(Cliente.create(clienteVO)));
	}
	
	public void delete(Long id) {
		var entity = clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNorFoundException("No records found for this ID"));
		clienteRepository.delete(entity);
	}
}
