package br.com.washington.uol.uol;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.washington.uol.controller.RequestController;
import br.com.washington.uol.entity.Cliente;
import br.com.washington.uol.util.BusinessException;


@RunWith(MockitoJUnitRunner.class)
public class RestTemplateTest {

	@InjectMocks
	private RequestController requestController;

	private	Cliente cliente = new Cliente();
	private String ipOrigem;
	
	@Before
	public void before() {
		ipOrigem = "172.217.29.4";

		cliente.setNome("Teste");
		cliente.setIdade(21);
	}

	@Test
	public void testConsumerApiIP() throws IOException, BusinessException {

		requestController.consumerApiIP(cliente, ipOrigem);
		
		assertNotNull(cliente.getMax_temp());
	}
	
	@Test(expected=Exception.class)
	public void testExceptionConsumerApiIP() throws Exception {
		ipOrigem = null;

		when(requestController).thenThrow(Exception.class);
	}

}
