package com.revhire.revhire.service.implementation;

import java.util.List;

import com.revhire.revhire.dao.NotificationDAO;
import com.revhire.revhire.dao.implementation.NotificationDAOImpl;
import com.revhire.revhire.modals.Notification;
import com.revhire.revhire.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public String sendNotification(Notification notification) {
        boolean result = notificationDAO.addNotification(notification);
        return result ? "Notification sent successfully" : "Failed to send notification";
    }

    @Override
    public List<Notification> viewUserNotifications(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
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
