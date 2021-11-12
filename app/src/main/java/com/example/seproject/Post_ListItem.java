package com.example.seproject;

public class Post_ListItem {
    private String writer;
    private String title;
    private String personnel;
    private String day;
    private int pid;
    private String deadline;
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

    public int getPid() {
        return pid;
    }
    public void setPid(String title) {
        this.pid = pid;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDeadline() {
        return deadline;
    }


    Post_ListItem( int pid,String writer, String title, String personnel, String day, String deadline){
        this.writer = writer;
        this.title = title;
        this.personnel = personnel;
        this.day = day;
        this.pid = pid;
        this.deadline= deadline;
    }
}
