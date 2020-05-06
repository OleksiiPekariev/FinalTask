package ua.nure.pekariev;

public class Constants {

	public static final String CURRENT_ACCOUNT = "CURRENT_ACCOUNT";

	public static final String CAR = "car";

	public static final String START_DATE = "startDate";

	public static final String EXPIRATION_DATE = "endDate";

	public static final String TARGET_URI = "TARGET_URI";

	public static final String REFERER = "referer";

	public static final String SUCCESS_MESSAGE = "SUCCESS_MESSAGE";

	public static final String ERROR_MESSAGE = "ERROR_MESSAGE";

	public static final String SESSION_LOCALE = "SESSION_LOCALE";

	public static final String CONFIRMATION_EMAIL_SUBJECT = "Car Rent. Order Confirmation";

	public static final String BILL_EMAIL_SUBJECT = "Car Rent. Bill";

	public static final int MAX_ACCOUNTS_PER_HTML_PAGE = 10;

	public static final int MAX_CARS_PER_HTML_PAGE = 12;

	public static final int MAX_ORDERS_PER_HTML_PAGE = 12;

	public static final int DRIVER_RENT_VALUE_PER_DAY = 100;

	public static final String BILL_HTML = "<html><head><style type=\"text/css\">TABLE {width: 300px; border-collapse: collapse; }TD, TH {padding: 3px; border: 1px solid black; }TH {background: #b0e0e6; }</style></head><body><div class=\"card\" style=\"width: 18rem;\"><div class=\"card-body\"><h2 class=\"card-title\">Bill</h2><table><tbody><tr><th scope=\"row\">First Name, Last Name</th><td>%NAME% %LASTNAME%</td></tr><tr><th scope=\"row\">Car</th><td>%CARNAME% %CARMODEL%</td></tr><tr><th scope=\"row\">Rent Value</th><td>%RENTVALUE%$</td></tr><tr><th scope=\"row\">Date of Start</th><td>%STARTDATE%</td></tr><tr><th scope=\"row\">Date of End</th><td>%ENDDATE%</td></tr><tr><th scope=\"row\">Days</th><td>%DAYS%</td></tr><tr><th scope=\"row\">Total value</th><td>%TOTALVALUE%$</td></tr></tbody></table><div>Payment bank account: 525486548548984565485456</div><a href=\"http://localhost:8080/payment?orderId=%ORDERID%\">Press to confirm your payment</a></div></div></body></html>";

	public static final String ACCIDENT_BILL_HTML = "<html><head><style type=\"text/css\">TABLE {width: 300px;border-collapse: collapse;}TD, TH {padding: 3px;border: 1px solid black;}TH {background: #b0e0e6;}</style></head><body><div class=\"card\" style=\"width: 18rem;\"><div class=\"card-body\"><h2 class=\"card-title\">Accident Bill</h2><table><tbody><tr><th scope=\"row\">First Name, Last Name</th><td>%NAME% %LASTNAME%</td></tr><tr><th scope=\"row\">Car</th><td>%CARNAME% %CARMODEL%</td></tr><tr><th scope=\"row\">Date of Start</th><td>%STARTDATE%</td></tr><tr><th scope=\"row\">Date of End</th><td>%ENDDATE%</td></tr><tr><th scope=\"row\">Accident penalty value</th><td>%PENALTYVALUE%$</td></tr></tbody></table><div>Payment bank account: 525876988548884655485457</div><a href=\"http://localhost:8080/accident?orderId=%ORDERID%\">Press to confirm your payment</a></div></div></body></html>";

	public static final String PASWORD_REMINDER_EMAIL_SUBJECT = "Car Rent. Password reminder";
}
