package ua.nure.pekariev.utils;

import java.text.SimpleDateFormat;

import ua.nure.pekariev.Constants;
import ua.nure.pekariev.model.Order;

public class OrderUtils {

	private final static String DATE_PATTERN = "dd-MM-yyyy HH:mm";

	public static Long countTotalRentValue(Integer rentValuePerDay, Long amountOfDays, Boolean withDriver) {
		if (withDriver) {
			return (long) (amountOfDays * (rentValuePerDay + Constants.DRIVER_RENT_VALUE_PER_DAY));
		} else {
			return (long) (amountOfDays * rentValuePerDay);
		}
	}

	public static String createOrderMessage(Order order) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
		return Constants.BILL_HTML.replace("%NAME%", order.getAccount().getFirstName())
				.replace("%LASTNAME%", order.getAccount().getLastName())
				.replace("%CARNAME%", order.getCar().getManufacturer()).replace("%CARMODEL%", order.getCar().getModel())
				.replace("%RENTVALUE%", String.valueOf(order.getCar().getRentValuePerDay()))
				.replace("%STARTDATE%", simpleDateFormat.format(order.getStarts()))
				.replace("%ENDDATE%", simpleDateFormat.format(order.getExpires()))
				.replace("%DAYS%", String.valueOf(order.getDaysAmount()))
				.replace("%TOTALVALUE%", String.valueOf(order.getTotalRentValue()))
				.replace("%ORDERID%", String.valueOf(order.getId()));
	}

	public static String createPenaltyMessage(Order order, Long penaltyValue) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
		return Constants.ACCIDENT_BILL_HTML.replace("%NAME%", order.getAccount().getFirstName())
				.replace("%LASTNAME%", order.getAccount().getLastName())
				.replace("%CARNAME%", order.getCar().getManufacturer()).replace("%CARMODEL%", order.getCar().getModel())
				.replace("%RENTVALUE%", String.valueOf(order.getCar().getRentValuePerDay()))
				.replace("%STARTDATE%", simpleDateFormat.format(order.getStarts()))
				.replace("%ENDDATE%", simpleDateFormat.format(order.getExpires()))
				.replace("%PENALTYVALUE%", String.valueOf(penaltyValue))
				.replace("%ORDERID%", String.valueOf(order.getId()));
	}

}
