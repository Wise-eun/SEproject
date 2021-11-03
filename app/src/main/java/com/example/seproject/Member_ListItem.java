package com.example.seproject;

public class Member_ListItem {
    private String name;
    private boolean isLeader;
    private boolean isComplete = true;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsLeader() {return isLeader;}
    public void setLeader(boolean isLeader) {this.isLeader = isLeader;}

    public boolean getIsComplete() {return isComplete;}
    public void setComplete(boolean isComplete) {this.isComplete = isComplete;}

    Member_ListItem(String name, boolean isLeader, boolean isComplete){
        this.name = name;
        this.isLeader = isLeader;
        this.isComplete = isComplete;
    }
}
