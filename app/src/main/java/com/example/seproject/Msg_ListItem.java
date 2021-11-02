package com.example.seproject;



public class Msg_ListItem {
    private String type;
    private String content;
    private String date;


    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    Msg_ListItem(String type, String content, String date){
        this.type = type;
        this.content = content;
        this.date = date;
    }
}
