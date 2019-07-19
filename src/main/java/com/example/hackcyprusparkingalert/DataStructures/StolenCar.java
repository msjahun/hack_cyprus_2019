package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Date;

public class StolenCar {
    private String Id;
    private QrCode QrCode;
    private Date CreatedOn;

    public StolenCar() {
    }

    public StolenCar(String id,
                     QrCode qrCode,
                     Date createdOn) {
        Id = id;//ID SHOULD BE QR CODE ID
        QrCode = qrCode;
        CreatedOn = createdOn;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public QrCode getQrCode() {
        return QrCode;
    }

    public void setQrCode(QrCode qrCode) {
        QrCode = qrCode;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }
}
