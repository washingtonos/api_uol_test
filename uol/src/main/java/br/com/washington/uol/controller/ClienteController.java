package br.com.washington.uol.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

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
import br.com.washington.uol.util.BusinessException;

@RequestMapping("api")
@RestController
public class ClienteController {

	private final ClienteService clienteService;
	private final RequestController requestController;

	private HttpServletRequest request;

	public ClienteController(ClienteService clienteService, HttpServletRequest request, RequestController requestController) {
		this.clienteService = clienteService;
		this.request = request;
		this.requestController = requestController;

	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> save(@RequestBody Cliente clienteIn) throws BusinessException, IOException {
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteHost();
			}

			executeController(clienteIn, ipAddress);

			Cliente clienteOut = clienteService.save(clienteIn);
			return new ResponseEntity<>(clienteOut, HttpStatus.CREATED);
		
	}

	@GetMapping("/clientes")
	public Iterable<Cliente> retriveAllClientes() throws MalformedURLException, JAXBException {
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

	public void executeController(Cliente cliente,String ipOrigem) throws IOException, BusinessException {

		requestController.consumerApiIP(cliente, ipOrigem);
		
	}
}
