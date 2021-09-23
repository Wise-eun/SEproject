package com.example.seproject;

public class ListItem {
    private String writer;
    private String title;
    private String personnel;
    private String day;

    public String getPersonnel() {
        return personnel;
    }
    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }

    ListItem(String writer, String title, String personnel, String day){
        this.writer = writer;
        this.title = title;
        this.personnel = personnel;
        this.day = day;
    }
}
