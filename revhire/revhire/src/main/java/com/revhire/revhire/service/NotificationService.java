package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Notification;

public interface NotificationService {

    String sendNotification(Notification notification);

    List<Notification> viewUserNotifications(int userId);

    String readNotification(int notificationId);

    String removeNotification(int notificationId);
}
