package pti.sb_squash_mvc.services;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import org.springframework.web.client.RestTemplate;

import pti.sb_squash_mvc.model.Location;

/** http://api.napiarfolyam.hu/?valuta=eur */
public class RestHandler {

	public double getEur(Location location) throws JDOMException, IOException {

		double eur = 0;

		// send request to REST API napiarfolyam.hu//
		RestTemplate restT = new RestTemplate();
		XMLParser parser = new XMLParser();
		String xml = restT.getForObject("http://api.napiarfolyam.hu/?valuta=eur", String.class);

		// calculate average EUR price (in Forint)//
		ArrayList<Double> eurList = parser.getEUR(xml);
		double sumEur = 0;

		for (int i = 0; i < eurList.size(); i++) {

			sumEur += eurList.get(i);
		}
		double averageEur = sumEur / eurList.size();

		// calculate the rental price from Forint to EUR and set rentEur attribute
		double rentEur = location.getRent() / averageEur;

		return eur;

	}

}
