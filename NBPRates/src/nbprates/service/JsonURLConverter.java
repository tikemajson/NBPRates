package nbprates.service;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import nbprates.exception.InvalidCurrencyValueException;

public class JsonURLConverter {
	public BigDecimal getRates(String currency, BigDecimal quantity) {
		if(quantity != null && quantity.compareTo(BigDecimal.ZERO) > 0) {
			JSONObject obj = new JSONObject(currency);
			JSONArray jsonArray = obj.getJSONArray("rates");
			JSONObject jsonObject = new JSONObject(jsonArray.toString().substring(1, jsonArray.toString().length() - 1));
			return jsonObject.getBigDecimal("mid").multiply(quantity);
		} else {
			throw new InvalidCurrencyValueException("The value must be greater than 0.");
		}
	}
}
