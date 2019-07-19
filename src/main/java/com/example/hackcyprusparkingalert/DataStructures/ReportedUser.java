package com.example.hackcyprusparkingalert.DataStructures;

import java.util.Date;

public class ReportedUser {
    private String Id;
    private QrCode QrReported;
    private MobileUser ReportedBy;
    private String ReasonForReport;
    private Date CreatedOn;

    public ReportedUser() {
    }

    public ReportedUser(String id,
                        QrCode qrReported,
                        MobileUser reportedBy,
                        String reasonForReport,
                        Date createdOn) {
        Id = id;//ID SHOULD BE RANDOM PRIMARY
        QrReported = qrReported;
        ReportedBy = reportedBy;
        ReasonForReport = reasonForReport;
        CreatedOn = createdOn;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public QrCode getQrReported() {
        return QrReported;
    }

    public void setQrReported(QrCode qrReported) {
        QrReported = qrReported;
    }

    public MobileUser getReportedBy() {
        return ReportedBy;
    }

    public void setReportedBy(MobileUser reportedBy) {
        ReportedBy = reportedBy;
    }

    public String getReasonForReport() {
        return ReasonForReport;
    }

    public void setReasonForReport(String reasonForReport) {
        ReasonForReport = reasonForReport;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }
}
