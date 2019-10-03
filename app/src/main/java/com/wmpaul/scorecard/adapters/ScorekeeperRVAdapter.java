package com.wmpaul.scorecard.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wmpaul.scorecard.App;
import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.objects.Player;
import com.wmpaul.scorecard.objects.ScoringEntity;
import com.wmpaul.scorecard.objects.Team;

import java.util.ArrayList;

public class ScorekeeperRVAdapter extends RecyclerView.Adapter<ScorekeeperRVAdapter.ScoreViewHolder>
{
    private static final int ITEM_TYPE_TEAM = 0;
    private static final int ITEM_TYPE_PLAYER = 1;

    private ArrayList<ScoringEntity> scoringEntities;

    public ScorekeeperRVAdapter(ArrayList<ScoringEntity> scoringEntities)
    {
        this.scoringEntities = scoringEntities;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (scoringEntities.get(position) instanceof Team) return ITEM_TYPE_TEAM;
        else return ITEM_TYPE_PLAYER;
    }

    @Override
    public ScorekeeperRVAdapter.ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row_scorekeeper, parent, false);
        return new ScorekeeperRVAdapter.ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScorekeeperRVAdapter.ScoreViewHolder holder, int position)
    {
        ScoringEntity entity = scoringEntities.get(position);

        if (entity instanceof Team) holder.bindTeam((Team) entity);
        else holder.bindPlayer((Player) entity);
    }

    @Override
    public int getItemCount()
    {
        return scoringEntities.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, score, change, players;

        ScoreViewHolder(View view)
        {
            super(view);

            name = (TextView) view.findViewById(R.id.text_view_score_name);
            score = (TextView) view.findViewById(R.id.text_view_score_score);
            change = (TextView) view.findViewById(R.id.text_view_score_change);
            players = (TextView) view.findViewById(R.id.text_view_score_player_list);
        }

        void bindTeam(Team team)
        {
            name.setText(team.getName());
            score.setText(String.format(App.getContextResources().getString(R.string.int_string), team.getLastScore()));
            change.setText(team.getChangeFromPreviousScore());

            players.setVisibility(View.VISIBLE);
            players.setText(team.getPlayerNames());
        }

        void bindPlayer(Player player)
        {
            name.setText(player.getName());
            score.setText(String.format(App.getContextResources().getString(R.string.int_string), player.getLastScore()));
            change.setText(player.getChangeFromPreviousScore());

            // FIXME Make change TextView disappear during the first round
        }
    }
}
