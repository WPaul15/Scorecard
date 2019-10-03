package com.wmpaul.scorecard.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wmpaul.scorecard.App;
import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.objects.Game;

import java.util.ArrayList;

public class SelectGameRVAdapter extends RecyclerView.Adapter<SelectGameRVAdapter.SelectGameViewHolder>
{
    private ArrayList<Game> games;

    public SelectGameRVAdapter(ArrayList<Game> games)
    {
        this.games = games;
    }

    @Override
    public SelectGameViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row_select_game, parent, false);
        return new SelectGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectGameViewHolder holder, int position)
    {
        holder.title.setText(games.get(position).getTitle());
        holder.players.setText(setPlayersText(games.get(position)));
        holder.teams.setText(setTeamsText(games.get(position)));
    }

    @Override
    public int getItemCount()
    {
        return games.size();
    }

    private String setPlayersText(Game game)
    {
        String players;
        int minPlayers = game.getMinPlayers();
        int maxPlayers = game.getMaxPlayers();

        if (maxPlayers == -1)
            players = String.format(App.getContextResources()
                    .getString(R.string.text_select_game_rv_adapter_players_unlimited), minPlayers);
        else if (minPlayers != maxPlayers)
            players = String.format(App.getContextResources()
                    .getString(R.string.text_select_game_rv_adapter_players_range), minPlayers, maxPlayers);
        else
            players = String.format(App.getContextResources()
                    .getString(R.string.text_select_game_rv_adapter_players_value), minPlayers);
        return players;
    }

    private String setTeamsText(Game game)
    {
        String teams;
        int numTeams = game.getNumTeams();

        if (numTeams == 0)
            teams = App.getContextResources()
                    .getString(R.string.text_select_game_rv_adapter_teams_none);
        else
            teams = String.format(App.getContextResources()
                    .getString(R.string.text_select_game_rv_adapter_teams), numTeams, game.getPlayersPerTeam());

        return teams;
    }

    class SelectGameViewHolder extends RecyclerView.ViewHolder
    {
        TextView title, players, teams;

        SelectGameViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.text_view_select_game_title);
            players = (TextView) view.findViewById(R.id.text_view_select_game_players);
            teams = (TextView) view.findViewById(R.id.text_view_select_game_teams);
        }
    }
}