package ua.nure.pekariev.services.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import ua.nure.pekariev.services.I18nService;

public class I18nServiceImpl implements I18nService {

	@Override
	public String getMessage(String key, Locale locale) {

		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		String value = rb.getString(key);
		return value;
	}

}
