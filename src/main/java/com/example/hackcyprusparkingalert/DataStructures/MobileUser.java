package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Date;

public class MobileUser {
   private String UID;
   private String Email;
   private String DeviceId;
   private String QrCodeId;
   private String Provider;
   private Date CreatedOn;
   private Date LastSignedIn;

    public MobileUser() {
    }

    public MobileUser(String UID, String email, String deviceId, String qrCodeId, String provider, Date createdOn, Date lastSignedIn) {
        this.UID = UID;
        Email = email;
        DeviceId = deviceId;
        QrCodeId = qrCodeId;
        Provider = provider;
        CreatedOn = createdOn;
        LastSignedIn = lastSignedIn;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getQrCodeId() {
        return QrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        QrCodeId = qrCodeId;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }

    public Date getLastSignedIn() {
        return LastSignedIn;
    }

    public void setLastSignedIn(Date lastSignedIn) {
        LastSignedIn = lastSignedIn;
    }
}
