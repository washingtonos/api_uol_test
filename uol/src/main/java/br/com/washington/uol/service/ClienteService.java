package br.com.washington.uol.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.washington.uol.entity.Cliente;
import br.com.washington.uol.repository.ClienteRepository;

@Service
public class ClienteService {

	private ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Iterable<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente findById(long id) {
		return clienteRepository.findById(id);
	}

	public Cliente update(Cliente cliente) {
		Optional<Cliente> clienteModify = clienteRepository.findById(cliente.getId());
		if(null != cliente.getNome() && null != cliente.getIdade()) {
			clienteModify.get().setNome(cliente.getNome());
			clienteModify.get().setIdade(cliente.getIdade());
		}
		return clienteRepository.save(clienteModify.get());
	}

	public Cliente delete(Cliente cliente) {
		 clienteRepository.delete(cliente);
		 return cliente;
	}

}
