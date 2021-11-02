package com.example.seproject;

public class Member_ListItem {
    private String name;
    private boolean isLeader;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsLeader() {return isLeader;}
    public void setLeader(boolean isLeader) {this.isLeader = isLeader;}

    Member_ListItem(String name, boolean isLeader){
        this.name = name;
        this.isLeader = isLeader;
    }
}
