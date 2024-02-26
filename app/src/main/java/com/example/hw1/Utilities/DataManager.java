package com.example.hw1.Utilities;

import com.example.hw1.Models.Player;

import java.util.ArrayList;

public class DataManager {

    private ArrayList<Player> players=new ArrayList<>();
    public DataManager() {
        players.add(new Player().setName("Netanel").setScore(25));
        players.add(new Player().setName("Netanel").setScore(25));
        players.add(new Player().setName("Netanel").setScore(25));
        players.add(new Player().setName("Netanel").setScore(25));
        players.add(new Player().setName("Netanel").setScore(25));
        players.add(new Player().setName("Netanel").setScore(25));
        players.add(new Player().setName("Netanel").setScore(25));
        players.add(new Player().setName("Netanel").setScore(25));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public DataManager setPlayers(ArrayList<Player> players) {
        this.players = players;
        return this;
    }
}
