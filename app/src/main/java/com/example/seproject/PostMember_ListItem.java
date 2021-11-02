package com.example.seproject;

public class PostMember_ListItem {

    private String name;
    private  boolean isLeader;

    public PostMember_ListItem(String name, boolean isLeader){
        this.name = name;
        this.isLeader=isLeader;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean getIsLeader() {return this.isLeader;}
    public void setLeader(boolean isLeader) {this.isLeader = isLeader;}

}