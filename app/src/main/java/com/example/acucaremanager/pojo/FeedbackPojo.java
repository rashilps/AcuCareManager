package com.example.acucaremanager.pojo;

public class FeedbackPojo {
    private String feedbackId;
    private String clientName;
    private String feedbackText;
    private String timestamp;

    public FeedbackPojo() {} // ✅ Required for Firebase

    public FeedbackPojo(String feedbackId, String clientName, String feedbackText, String timestamp) {
        this.feedbackId = feedbackId;
        this.clientName = clientName;
        this.feedbackText = feedbackText;
        this.timestamp = timestamp;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
