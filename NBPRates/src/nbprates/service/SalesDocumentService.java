package nbprates.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesDocumentService {
	public static void insert() {
		String dateString = "2021-02-07";
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
		JsonURLReader jsonURLReader = new JsonURLReader();
		JsonURLConverter jsonURLConverter = new JsonURLConverter();
		String currency = jsonURLReader.getData(date, "USD");
		BigDecimal value = jsonURLConverter.getRates(currency, new BigDecimal("1"));
		System.out.println(value);
	}
	
	public static void main(String[] args) {
		insert();
	}
}
