package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Date;

public class Device {
   private String Id;
   private String DeviceId;
   private QrCode QrCode;
   private MobileUser MobileUser;
   private Date CreatedOn;

    public Device() {
    }

    public Device(String id,
                  String deviceId,
                  QrCode qrCode,
                  MobileUser mobileUser,
                  Date createdOn) {
        Id = id;//Should be QRCodeId
        DeviceId = deviceId;
        QrCode = qrCode;
        MobileUser = mobileUser;
        CreatedOn = createdOn;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public QrCode getQrCode() {
        return QrCode;
    }

    public void setQrCode(QrCode qrCode) {
        QrCode = qrCode;
    }

    public MobileUser getMobileUser() {
        return MobileUser;
    }

    public void setMobileUser(MobileUser mobileUser) {
        MobileUser = mobileUser;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }
}
