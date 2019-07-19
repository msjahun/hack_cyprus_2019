package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Date;

public class NotificationLog {
   private String Id;
   private String MessageText;
   private String MessageLabel;
   private String DeliveryDate;
   private String Title;
   private String Target;
   private Date ExpiryDate;
   private String NotificationType;

    public NotificationLog() {
    }

    public NotificationLog(String id,
                           String messageText,
                           String messageLabel,
                           String deliveryDate,
                           String title,
                           String target,
                           Date expiryDate,
                           String notificationType) {
        Id = id;
        MessageText = messageText;
        MessageLabel = messageLabel;
        DeliveryDate = deliveryDate;
        Title = title;
        Target = target;
        ExpiryDate = expiryDate;
        NotificationType = notificationType;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public String getMessageLabel() {
        return MessageLabel;
    }

    public void setMessageLabel(String messageLabel) {
        MessageLabel = messageLabel;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(String notificationType) {
        NotificationType = notificationType;
    }
}
