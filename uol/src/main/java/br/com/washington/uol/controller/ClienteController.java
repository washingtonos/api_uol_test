package br.com.washington.uol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.washington.uol.entity.Cliente;
import br.com.washington.uol.service.ClienteService;

@RequestMapping("api")
@RestController
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> save(@RequestBody Cliente clienteIn) {
		Cliente clienteOut = clienteService.save(clienteIn);
		return new ResponseEntity<>(clienteOut, HttpStatus.CREATED);
	}

	@GetMapping("/clientes")
	public Iterable<Cliente> retriveAllClientes() {
		return clienteService.findAll();
	}

	@GetMapping("/cliente/{id}")
	public Cliente findById(@PathVariable(value = "id") long id) {
		return clienteService.findById(id);
	}

	@DeleteMapping("/cliente")
	public Cliente delete(@RequestBody Cliente clienteIn) {
		return clienteService.delete(clienteIn);
	}
}
