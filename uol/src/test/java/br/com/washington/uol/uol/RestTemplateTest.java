package br.com.washington.uol.uol;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.washington.uol.entity.Geographical;
import br.com.washington.uol.entity.Location;
import br.com.washington.uol.entity.LocationTemp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

	// Resgatar WOEID
	// https://www.metaweather.com/api/location/search/?lattlong=37.38600,-122.08380

	private String ipOrigem = "172.217.29.4";

	@Test
	public void consumerApiIP() throws IOException {
		RestTemplate restTemplate = new RestTemplate();

		UriComponents uri = UriComponentsBuilder.newInstance().scheme("https").host("ipvigilante.com")
				.path("json/" + ipOrigem).build();

		ResponseEntity<Geographical> entity = restTemplate.getForEntity(uri.toString(), Geographical.class);
		consumerApiWoeid(entity.getBody().getData().getLatitude(), entity.getBody().getData().getLongitude());
	}

	public void consumerApiWoeid(String latt, String longi) throws IOException {
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
		consumerApiTemp(woeid, dStr);
	}

	private void consumerApiTemp(String woeid, String data) {
		// Resgatar min_temp & max_temp
		// https://www.metaweather.com/api/location/2455920/2019/3/09/

		RestTemplate restTemplate = new RestTemplate();

		UriComponents uri = UriComponentsBuilder.newInstance().scheme("https").host("www.metaweather.com")
				.path("api/location/location/" + woeid + "/" + data).build();

		LocationTemp[] locationTemp = restTemplate.getForObject(uri.toString(), LocationTemp[].class);
		System.out.println(" Temperatura Maxima: " + locationTemp[0].getMax_temp() + " Temperatura Minima: "
				+ locationTemp[0].getMin_temp());
	}

}
