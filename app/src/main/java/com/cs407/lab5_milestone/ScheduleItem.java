package com.cs407.lab5_milestone;

public class ScheduleItem {
    private String date;
    private String startTime;
    private String endTime;
    private String item;
    public ScheduleItem(String date, String startTime, String endTime, String item){
        this.date =date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.item=item;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getItem() {
        return item;
    }
}
