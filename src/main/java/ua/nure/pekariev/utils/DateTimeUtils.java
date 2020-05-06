package ua.nure.pekariev.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ua.nure.pekariev.exceptions.OrderException;

public class DateTimeUtils {

	public static Date stringToDate(String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.parse(dateString);

		return formatter.parse(dateString);
	}

	/**
	 * Get a diff between two dates
	 * 
	 * @param oldestDate the oldest date
	 * @param newerDate  the newest date
	 * @param timeUnit   the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 * @throws OrderException
	 */
	public static long getDateDiff(Date oldestDate, Date newerDate, TimeUnit timeUnit) throws OrderException {
		long diffInMillies = newerDate.getTime() - oldestDate.getTime();
		if (diffInMillies <= 0) {
			throw new OrderException("Order expiration date should be further then starting date");
		}
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

}
