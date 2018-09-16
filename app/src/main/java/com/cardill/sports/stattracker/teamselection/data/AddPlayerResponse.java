package com.cardill.sports.stattracker.teamselection.data;

public class AddPlayerResponse {
    private String message;
    private NewPlayer newPlayer;

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public NewPlayer getNewPlayer() { return newPlayer; }
    public void setNewPlayer(NewPlayer value) { this.newPlayer = value; }
}