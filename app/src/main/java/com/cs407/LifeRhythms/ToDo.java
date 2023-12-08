package com.cs407.LifeRhythms;

public class ToDo {
    private final String username;
    private final String title;
    private final String date;
    private final String desiredDate;
    private final String startTime;
    private final String endTime;
    private final String todo;
    private final String category;
    public ToDo(String username, String title, String date, String desiredDate, String startTime, String endTime, String todo, String category){
        this.username = username;
        this.title=title;
        this.date = date;
        this.desiredDate=desiredDate;
        this.startTime=startTime;
        this.endTime=endTime;
        this.todo=todo;
        this.category = category;
    }
    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDesiredDate() {
        return desiredDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTodo() {
        return todo;
    }

    public String getCategory() {
        return category;
    }
}
