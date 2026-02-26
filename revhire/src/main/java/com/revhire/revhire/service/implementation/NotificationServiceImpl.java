package com.revhire.revhire.service.implementation;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revhire.revhire.dao.NotificationDAO;
import com.revhire.revhire.dao.implementation.NotificationDAOImpl;
import com.revhire.revhire.modals.Notification;
import com.revhire.revhire.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
	private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class);

    private NotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public String sendNotification(Notification notification) {
        boolean result = notificationDAO.addNotification(notification);
        logger.info("Notification sent to user {}", notification.getUserId());

        return result ? "Notification sent successfully" : "Failed to send notification";
    }
    @Override
    public List<Notification> getNotificationsByUserId(int userId) {
    	logger.info("Fetching notifications for user {}", userId);
        return notificationDAO.getNotificationsByUserId(userId);
    }

    @Override
    public List<Notification> viewUserNotifications(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }
    @Override
    public int countUnreadByUserId(int userId) {
        return notificationDAO.countUnreadByUserId(userId);
    }
    
    @Override
    public boolean markAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    @Override
    public String readNotification(int notificationId) {
        boolean result = notificationDAO.markAsRead(notificationId);
        return result ? "Notification marked as read" : "Unable to update notification";
    }

    @Override
    public String removeNotification(int notificationId) {
        boolean result = notificationDAO.deleteNotification(notificationId);
        return result ? "Notification deleted" : "Failed to delete notification";
    }
}
