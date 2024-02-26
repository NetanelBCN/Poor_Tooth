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
import com.example.hw1.Views.ScoreActivity;


public class ScoreListFragment extends Fragment {
    private RecyclerView FRAGSCORE_RV_recycler;
    private PlayerAdapter adapter;
    private DataManager dataManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_list, container, false);
        findViews(view);
        initViews();
        return view;
    }


    private void initViews() {
        dataManager = new DataManager();
        adapter = new PlayerAdapter(getContext(), dataManager.getPlayers());
        FRAGSCORE_RV_recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        FRAGSCORE_RV_recycler.setLayoutManager(layoutManager);
    }

    private void findViews(View view) {
        FRAGSCORE_RV_recycler = view.findViewById(R.id.FRAGSCORE_RV_recycler);
    }


}