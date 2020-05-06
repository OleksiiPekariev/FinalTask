package ua.nure.pekariev.utils;

import java.sql.Connection;

public final class ConnectionUtils {

	private static final ThreadLocal<Connection> connections = new ThreadLocal<>();

	private ConnectionUtils() {
	}
	
	public static Connection getConnection() {
		return connections.get();
	}
	
	public static void setConnection(Connection connection) {
		connections.set(connection);
	}
	
	public static void clearConnection() {
		connections.remove();
	}

}
