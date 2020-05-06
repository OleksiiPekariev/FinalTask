package ua.nure.pekariev.services;

public interface NotificationService {

	void sendNotificationMessage(String notificationAddress, String subject, String content);

	void close();
}
