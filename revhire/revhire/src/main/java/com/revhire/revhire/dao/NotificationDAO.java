package com.revhire.revhire.dao;

import java.util.List;
import com.revhire.revhire.modals.Notification;

public interface NotificationDAO {

    boolean addNotification(Notification notification);

    List<Notification> getNotificationsByUserId(int userId);

    boolean markAsRead(int notificationId);

    boolean deleteNotification(int notificationId);
}
