package com.bsrvch.entape.models;

public class Message {

    private String from;
    private String to;
    private String text;
    private String room;

    public String getFrom() {
        return from;
    }

    public String getTo() {return to;}

    public void setTo(String to) {this.to = to;}

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRoom() {return room;}

    public void setRoom(String room) {this.room = room;}
}
