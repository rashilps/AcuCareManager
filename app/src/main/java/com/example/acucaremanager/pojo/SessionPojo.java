package com.example.acucaremanager.pojo;


public class SessionPojo {
    private String sessionId;
    private String clientName;
    private String date;
    private String time;
    private String fee;
    private String notes;

    public SessionPojo() {} // ✅ Required for Firebase

    public SessionPojo(String sessionId, String clientName, String date, String time, String fee, String notes) {
        this.sessionId = sessionId;
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.fee = fee;
        this.notes = notes;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
