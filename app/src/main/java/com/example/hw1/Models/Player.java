package com.example.hw1.Models;

public class Player {

    private int score=0;
    private double lat;
    private double lon;

    public Player() {

    }




    public int getScore() {
        return score;
    }

    public Player setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Player setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Player setLon(double lon) {
        this.lon = lon;
        return this;
    }
}
