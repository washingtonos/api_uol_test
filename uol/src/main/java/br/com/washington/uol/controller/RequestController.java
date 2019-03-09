package br.com.washington.uol.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

@Component
public class RequestController {

	private static int HTTP_COD_SUCESSO = 200;

	public static Object requestGeographical(String ipOrigem) throws JAXBException, MalformedURLException {

		String responseJson = null;

		try {

			URL url = new URL("https://ipvigilante.com/json/" + ipOrigem);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			responseJson = inputStreamToString(con.getInputStream());
			con.disconnect();
			return responseJson;

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseJson;
	}

	public static Object requestLattLong(String latitudeLongitude) throws JAXBException, MalformedURLException {

		String responseJson = null;

		try {

			URL url = new URL("https://www.metaweather.com/api/location/search/?lattlong="+latitudeLongitude);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			responseJson = inputStreamToString(con.getInputStream());
			con.disconnect();
			return responseJson;

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseJson;
	}

	public static String inputStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
}
