package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Calendar;
import java.util.Date;

public class QrCode {
    private String Id;
    private String Initiator;
    private String CarPlateNo;
    private String CarModelNo;
    private MobileUser CurrentMobileUser;
    private Boolean IsActive;
    private Date CreatedOn;
public QrCode(){

}

    public QrCode(String id, String initiator, String carPlateNo, String carModelNo, MobileUser currentMobileUser, Boolean isActive) {
        Id = id;
        Initiator = initiator;
        CarPlateNo = carPlateNo;
        CarModelNo = carModelNo;
        CurrentMobileUser = currentMobileUser;
        IsActive = isActive;
        CreatedOn = Calendar.getInstance().getTime();
    }

    public String getCarPlateNo() {
        return CarPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        CarPlateNo = carPlateNo;
    }

    public String getCarModelNo() {
        return CarModelNo;
    }

    public void setCarModelNo(String carModelNo) {
        CarModelNo = carModelNo;
    }

    public QrCode(String id,
                  String initiator,
                  MobileUser currentMobileUser,
                  Boolean isActive) {
        Id = id;
        Initiator = initiator;
        CurrentMobileUser = currentMobileUser;
        IsActive = isActive;
        CreatedOn = Calendar.getInstance().getTime();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInitiator() {
        return Initiator;
    }

    public void setInitiator(String initiator) {
        Initiator = initiator;
    }

    public MobileUser getCurrentMobileUser() {
        return CurrentMobileUser;
    }

    public void setCurrentMobileUser(MobileUser currentMobileUser) {
        CurrentMobileUser = currentMobileUser;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }
}
