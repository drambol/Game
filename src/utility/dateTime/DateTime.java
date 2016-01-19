package utility.dateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	
	public static String getCurrentTime() {
		String time = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
		return time;
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
