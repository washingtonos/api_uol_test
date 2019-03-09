package br.com.washington.uol.controller;

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

@RequestMapping("api")
@RestController
public class ClienteController {

	private final ClienteService clienteService;
	private final RequestController controller;

	private HttpServletRequest request;

	public ClienteController(ClienteService clienteService, HttpServletRequest request, RequestController controller) {
		this.clienteService = clienteService;
		this.request = request;
		this.controller = controller;

	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> save(@RequestBody Cliente clienteIn) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteHost();
		}
		System.out.println(ipAddress);
		
		Cliente clienteOut = clienteService.save(clienteIn);
		return new ResponseEntity<>(clienteOut, HttpStatus.CREATED);
	}

	@GetMapping("/clientes")
	public Iterable<Cliente> retriveAllClientes() throws MalformedURLException, JAXBException {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteHost();
		}
		System.out.println(ipAddress);
		executeController();
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

	public String executeController() throws MalformedURLException, JAXBException{
		String retorno = (String) controller.requestGeographical("172.217.29.4");
		String[] tokens = retorno.split(",");
		return null;
	}
}
