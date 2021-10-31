package com.example.seproject;

public class Alarm_ListItem {
    private String sender;
    private String title;
    private int type; //type=0 참여신청, type=1 참여신청거절, type=2 참여신청수락, type=3 메신저

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle(){return title;}
    public void setTitle(String title) {this.title = title;}

    public int getType() {return type;}
    public void setType(int type) {this.type = type;}

    Alarm_ListItem(String sender, String title, int type){
        this.sender = sender;
        this.title = title;
        this.type = type;
    }
}
