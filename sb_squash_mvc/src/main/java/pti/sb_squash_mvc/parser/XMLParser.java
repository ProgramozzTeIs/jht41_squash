package pti.sb_squash_mvc.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser {
	
	public ArrayList<Double> getEUR(String xml) throws JDOMException, IOException {
		
		ArrayList<Double> eurList = new ArrayList<Double>();
		
		SAXBuilder sb = new SAXBuilder();
		StringReader sr = new StringReader(xml);
		Document doc = sb.build(sr);
		
		Element rootElement = doc.getRootElement();
		Element valutaElement = rootElement.getChild("valuta");
		List<Element> itemElements = valutaElement.getChildren("item");
		
		for(int i = 0; i < itemElements.size(); i++) {
			
			Element item = itemElements.get(i);
			Element eladasElement = item.getChild("eladas");
			double eur = Double.parseDouble(eladasElement.getValue());
			
			eurList.add(eur);
		}
		
		return eurList;
	}

}
