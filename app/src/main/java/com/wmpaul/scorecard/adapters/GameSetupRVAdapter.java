package com.wmpaul.scorecard.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wmpaul.scorecard.App;
import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.objects.Player;

import java.util.ArrayList;

public class GameSetupRVAdapter extends RecyclerView.Adapter<GameSetupRVAdapter.GameSetupViewHolder>
{
    private ArrayList<Player> players;
    private boolean needsTeams;

    public GameSetupRVAdapter(boolean needsTeams, ArrayList<Player> players)
    {
        this.needsTeams = needsTeams;
        this.players = players;
    }

    @Override
    public GameSetupViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row_game_setup, parent, false);
        return new GameSetupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GameSetupViewHolder holder, int position)
    {
        Player player = players.get(position);
        holder.name.setText(player.getName());

        if (needsTeams)
        {
            holder.team.setVisibility(View.VISIBLE);
            holder.team.setText(String.format(App.getContextResources().getString(R.string.text_game_setup_rv_adapter_team), player.getTeam()));
        }
    }

    @Override
    public int getItemCount()
    {
        return players.size();
    }

    class GameSetupViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, team;

        GameSetupViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.text_view_game_setup_name);
            team = (TextView) view.findViewById(R.id.text_view_game_setup_team);
        }
    }
}