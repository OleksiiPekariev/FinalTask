package ua.nure.pekariev.utils;

import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;

public class FormsValidation {

	public static void checkAccountForOrderFields(Account account) throws ValidationException {
		if (!account.getPassportData().matches("^([A-Z]{2}[0-9]{6})?$")) {
			throw new ValidationException("Passport data should be in format like SSNNNNNN");
		}

		if (!String.valueOf(account.getPhone()).matches("^([0-9]{12})?$")) {
			throw new ValidationException("The Phone number should be in format like 360971234567");
		}

		if (account.getAddress().length() < 10) {
			throw new ValidationException(
					"Please sign your actual addres of leaving in format like: Country, Region, City, Street, house/appartment number");
		}
	}

}
