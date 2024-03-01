package com.example.hw1.Utilities;

import com.example.hw1.Models.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Comparator;

public class DataManager {
    private ArrayList<Player> players = new ArrayList<>();
    ;
    private final int MAX = 10;
    public DataManager() {
        loadData();
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Player> other) {
        this.players = new ArrayList<>(other);//
    }
    public String convertArrayToJSON() {
       return new Gson().toJson(players);
    }
    public ArrayList<Player> convertJSONToArray(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<Player>>() {
        }.getType());
    }
    public void updateScores(int score,double lat, double lon) {
        updateArray(score,lat,lon);
    }
    public void loadData() {
        ArrayList<Player> arr = convertJSONToArray(SharedPreferencesManager.getInstance().getString("players", null));
        if (arr != null) {
            setPlayers(arr);
        }
    }
    public void saveData() {
        String json = convertArrayToJSON();
        SharedPreferencesManager.getInstance().putString("players", json);
    }
    private void updateArray(int score, double lat, double lon) {
        Player player = new Player().setScore(score).setLat(lat).setLon(lon);
        if (players.size() < MAX) {
            players.add(player);
            sortPlayersByScore();
            return;
        }
        if (isTopTen(score)) {
            players.add(player);
            sortPlayersByScore();
            players.remove(players.size() - 1);
        }
    }
    private void sortPlayersByScore() {
        players.sort(Comparator.comparingInt(Player::getScore).reversed());
    }
    private boolean isTopTen(int score) {
        return (score > players.get(players.size() - 1).getScore());
    }
}
