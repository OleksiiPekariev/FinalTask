package ua.nure.pekariev.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ua.nure.pekariev.context.ApplicationContext;

public final class FileUtils {
	private static final String ENCODING = "Cp1251";

	private FileUtils() {
		super();
	}

	public static String readFile(String path) {
		String res = null;
		try {
			ApplicationContext.class.getClassLoader().getResourceAsStream("application.properties");
			byte[] bytes = Files.readAllBytes(Paths.get(path));
			res = new String(bytes, ENCODING);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return res;
	}
}
