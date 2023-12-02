package com.cs407.lab5_milestone;

public class ToDo {
    private String username;
    private String title;
    private String date;
    private String desiredDate;
    private String startTime;
    private String endTime;
    private String todo;
    private String category;
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