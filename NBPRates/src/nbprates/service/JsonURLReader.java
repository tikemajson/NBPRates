package nbprates.service;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import nbprates.exception.DataNotFoundException;
import nbprates.exception.TimeOutException;

public class JsonURLReader extends DataService{
	private String NBP_TABLES = "http://api.nbp.pl/api/exchangerates/tables/A/";
	private String NBP_RATES = "http://api.nbp.pl/api/exchangerates/rates/A/";
	private final CloseableHttpClient closableHttpClient = HttpClients.createDefault();
	
	private Date checkData(Date date) {
		Date tempDate = date;
		HttpGet httpGet = new HttpGet(NBP_TABLES + new SimpleDateFormat("yyyy-MM-dd").format(tempDate));
		try {
			CloseableHttpResponse closableHttpResponse = closableHttpClient.execute(httpGet);
			while(closableHttpResponse.getStatusLine().getStatusCode() != 200) {
				httpGet.releaseConnection();
				closableHttpResponse.close();
				tempDate = getDate(tempDate);
				httpGet = new HttpGet(NBP_TABLES + new SimpleDateFormat("yyyy-MM-dd").format(tempDate));
				closableHttpResponse = closableHttpClient.execute(httpGet);
			}
			closableHttpResponse.close();
			return tempDate;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	public String getData(Date date, String code) {
		Date newDate = checkData(date);
		System.out.println(NBP_RATES + code + "/" + new SimpleDateFormat("yyyy-MM-dd").format(newDate) + "/?format=json");
		HttpGet httpGet = new HttpGet(NBP_RATES + code + "/" + new SimpleDateFormat("yyyy-MM-dd").format(newDate) + "/?format=json");
		try {
			CloseableHttpResponse closeableHttpResponse = closableHttpClient.execute(httpGet);
			HttpEntity httpEntity = closeableHttpResponse.getEntity();
			if(closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpEntity);
			} else {
				throw new DataNotFoundException("Data not found.");
			}
		} catch (ConnectException ce) {
			throw new TimeOutException("Connection timeout.");
		} catch (IOException ioe){
			throw new RuntimeException(ioe);
		}
	}
}
