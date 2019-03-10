package br.com.washington.uol.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.washington.uol.entity.Cliente;
import br.com.washington.uol.entity.Geographical;
import br.com.washington.uol.entity.Location;
import br.com.washington.uol.entity.LocationTemp;
import br.com.washington.uol.util.BusinessException;

@Component
public class RequestController {

	/*
	 * Metodo consumerApiIP Resgatar latitude e longitude, pelo IP do cliente
	 * (request) e faz a chamada do metodo consumerApiWoeid
	 */
	public void consumerApiIP(Cliente cliente, String ipOrigem) throws IOException, BusinessException {

		try {
			RestTemplate restTemplate = new RestTemplate();

			UriComponents uri = UriComponentsBuilder.newInstance().scheme("https").host("ipvigilante.com")
					.path("json/" + ipOrigem).build();

			ResponseEntity<Geographical> entity = restTemplate.getForEntity(uri.toString(), Geographical.class);

			consumerApiWoeid(cliente, entity.getBody().getData().getLatitude(),
					entity.getBody().getData().getLongitude());

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(),"UOL_500", "Erro ao consultar localidade por IP"); 
		}

	}

	/*
	 * Metodo consumerApiWoeid Resgatar Woeid e data atual, apenas pela latitude e
	 * longitude e faz a chamada do metodo consumerApiTemp
	 */
	public void consumerApiWoeid(Cliente cliente, String latt, String longi) throws IOException {

		try {
			RestTemplate restTemplate = new RestTemplate();

			UriComponents uri = UriComponentsBuilder.newInstance().scheme("https").host("www.metaweather.com")
					.path("api/location/search").queryParam("lattlong", latt + "," + longi).build();

			Location[] location = restTemplate.getForObject(uri.toString(), Location[].class);
			Integer dist = location[0].getDistance();
			String woeid = location[0].getWoeid();

			for (Location location2 : location) {
				if (location2.getDistance() < dist) {
					woeid = location2.getWoeid();
				}

			}

			Date data = new Date();
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy/MM/dd");
			String dStr = formatador.format(data);

			consumerApiTemp(cliente, woeid, dStr);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Metodo consumerApiTemp Resgatar min_temp & max_temp passando Woeid e data
	 * atual e atribui ao cliente
	 */
	private void consumerApiTemp(Cliente cliente, String woeid, String data) {
		// Resgatar min_temp & max_temp
		// https://www.metaweather.com/api/location/2455920/2019/3/09/

		try {

			RestTemplate restTemplate = new RestTemplate();

			UriComponents uri = UriComponentsBuilder.newInstance().scheme("https").host("www.metaweather.com")
					.path("api/location/location/" + woeid + "/" + data).build();

			LocationTemp[] locationTemp = restTemplate.getForObject(uri.toString(), LocationTemp[].class);

			cliente.setMin_temp(new BigDecimal(locationTemp[0].getMin_temp()));
			cliente.setMax_temp(new BigDecimal(locationTemp[0].getMax_temp()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}