package utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeHelper 
{

	public static String getCurrentDateTime()
	{
		DateFormat formator = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		Calendar calendar = Calendar.getInstance();
		String dateTime = ""+formator.format(calendar.getTime());
		return dateTime;
	}
	
	public static String getCurrentDate()
	{
		return getCurrentDateTime().substring(0, 11);
	}
	
	public static String getCurrentTime()
	{
		return getCurrentDateTime().substring(12);
	}
	
	public static void main (String[] args)
	{
		System.out.println(getCurrentDateTime());
		System.out.println(getCurrentDate());
		System.out.println(getCurrentTime());
	}
	
}
