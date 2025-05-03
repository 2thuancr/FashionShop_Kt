package com.student22110006.fashionshop.data.model.notification;

public class Notification {
    private String title;
    private String message;
    private String date;
    private int iconResId;
    private boolean isNew;

    public Notification(String title, String message, String date, int iconResId, boolean isNew) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.iconResId = iconResId;
        this.isNew = isNew;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isNew() {
        return isNew;
    }
}
