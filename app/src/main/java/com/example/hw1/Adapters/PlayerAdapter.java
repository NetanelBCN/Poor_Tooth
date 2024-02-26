package com.example.hw1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1.Interfaces.PlayerCallback;
import com.example.hw1.Models.Player;
import com.example.hw1.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {


    private Context context;
    private ArrayList<Player> players;
    private PlayerCallback playerCallback;


    public PlayerAdapter(Context context, ArrayList<Player> players) {
        this.context = context;
        this.players = players;
    }

    public PlayerAdapter setMovieCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
        return this;
    }

    @NonNull
    @Override
    public PlayerAdapter.PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_score_item, parent, false);
        return new PlayerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.PlayerViewHolder holder, int position) {
        Player player=getItem(position);
        holder.SCORE_MTV_rank.setText(position+1+")"); // the rank is the index in the sorted array +1 (start from 0 and we need 1)
        holder.SCORE_MTV_score.setText("Score: "+player.getScore());
    }

    @Override
    public int getItemCount() {
        return players == null ? 0 : players.size();
    }

    private Player getItem(int position) {
        return players.get(position);
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        ////////
        private LinearLayoutCompat SCORE_LLC_card;
        private MaterialTextView SCORE_MTV_rank;
        private MaterialTextView SCORE_MTV_score;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            SCORE_LLC_card = itemView.findViewById(R.id.SCORE_LLC_card);
            SCORE_MTV_rank = itemView.findViewById(R.id.SCORE_MTV_rank);
            SCORE_MTV_score = itemView.findViewById(R.id.SCORE_MTV_score);

            SCORE_LLC_card.setOnClickListener(v -> {
                if (playerCallback != null) {
                    playerCallback.CardClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}