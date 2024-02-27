package com.example.hw1.FragmentViews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw1.Adapters.PlayerAdapter;
import com.example.hw1.Interfaces.PlayerCallback;
import com.example.hw1.Models.Player;
import com.example.hw1.R;
import com.example.hw1.Utilities.DataManager;
import com.example.hw1.Utilities.SharedPreferencesManager;
import com.example.hw1.Views.ScoreActivity;

import java.util.ArrayList;


public class ScoreListFragment extends Fragment {
    private RecyclerView FRAGSCORE_RV_recycler;
    private PlayerAdapter adapter;
    private DataManager dataManager;
    private PlayerCallback callbackCardClicked;


    public ScoreListFragment() {
        dataManager = new DataManager();
        adapter = new PlayerAdapter(getContext(), dataManager.getPlayers());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    public void initViews() {
        FRAGSCORE_RV_recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        FRAGSCORE_RV_recycler.setLayoutManager(layoutManager);
    }

    public PlayerAdapter getAdapter() {
        return adapter;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void updateData(Player player) {
        if (dataManager.getPlayers() != null)
            dataManager.updateScores(player.getScore(), player.getLat(), player.getLon());
    }

    private void findViews(View view) {
        FRAGSCORE_RV_recycler = view.findViewById(R.id.FRAGSCORE_RV_recycler);
    }

    public void save() {
        dataManager.saveData();
    }
}