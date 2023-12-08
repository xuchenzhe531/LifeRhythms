package com.cs407.LifeRhythms;

public class ScheduleItem {
    private final String date;
    private final String startTime;
    private final String endTime;
    private final String item;
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
