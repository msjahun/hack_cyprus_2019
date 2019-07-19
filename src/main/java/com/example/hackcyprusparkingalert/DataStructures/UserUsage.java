package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Calendar;
import java.util.Date;

public class UserUsage {
    private String Id;
    private MobileUser ScannedBy;
    private QrCode ScannedQrCode;
    private String ParkingMessage;
    private Date CreatedOn;

    public UserUsage() {
    }

    public UserUsage(String id,
                     MobileUser scannedBy,
                     QrCode scannedQrCode) {
        Id = id; //ID SHOULD BE RANDOM PRIMARY
        ScannedBy = scannedBy;
        ScannedQrCode = scannedQrCode;
        CreatedOn = Calendar.getInstance().getTime();
    }

    public UserUsage(String id, MobileUser scannedBy, QrCode scannedQrCode, String parkingMessage) {
        Id = id;
        ScannedBy = scannedBy;
        ScannedQrCode = scannedQrCode;
        ParkingMessage = parkingMessage;
        CreatedOn = Calendar.getInstance().getTime();
    }

    public String getParkingMessage() {
        return ParkingMessage;
    }

    public void setParkingMessage(String parkingMessage) {
        ParkingMessage = parkingMessage;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public MobileUser getScannedBy() {
        return ScannedBy;
    }

    public void setScannedBy(MobileUser scannedBy) {
        ScannedBy = scannedBy;
    }

    public QrCode getScannedQrCode() {
        return ScannedQrCode;
    }

    public void setScannedQrCode(QrCode scannedQrCode) {
        ScannedQrCode = scannedQrCode;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }
}
