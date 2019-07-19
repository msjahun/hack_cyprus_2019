package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Date;

public class QrCodeUserClaim {


    private String Id;
    private MobileUser MobileUser;
    private QrCode QrCode;
    private Date TimeStamp;

    public QrCodeUserClaim() {
    }


    public QrCodeUserClaim(String id,
                           MobileUser mobileUser,
                           QrCode qrCode,
                           Date timeStamp) {
        Id = id;//ID SHOULD BE USER ID
        MobileUser = mobileUser;
        QrCode = qrCode;
        TimeStamp = timeStamp;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public MobileUser getMobileUser() {
        return MobileUser;
    }

    public void setMobileUser(MobileUser mobileUser) {
        MobileUser = mobileUser;
    }

    public QrCode getQrCode() {
        return QrCode;
    }

    public void setQrCode(QrCode qrCode) {
        QrCode = qrCode;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }
}
