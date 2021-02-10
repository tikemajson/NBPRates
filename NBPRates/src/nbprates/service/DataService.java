package nbprates.service;

import java.util.Calendar;
import java.util.Date;

public class DataService {
	
	public Date getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
}
