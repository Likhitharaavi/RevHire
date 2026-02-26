package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Notification;

public interface NotificationService {

    String sendNotification(Notification notification);

    List<Notification> getNotificationsByUserId(int userId);
    
    List<Notification> viewUserNotifications(int userId);
    
    int countUnreadByUserId(int userId);
    
    boolean markAsRead(int notificationId);

    String readNotification(int notificationId);

    String removeNotification(int notificationId);
}
