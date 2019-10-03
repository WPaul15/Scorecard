package com.wmpaul.scorecard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.adapters.ScorekeeperRVAdapter;
import com.wmpaul.scorecard.objects.Game;
import com.wmpaul.scorecard.objects.ScoringEntity;

import java.util.ArrayList;

public class ScorekeeperActivity extends AppCompatActivity
{
    public static final int REQUEST_ENTER_SCORES = 0;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    Button roundScores;
    private ScorekeeperRVAdapter adapter;
    private Game game;
    private ArrayList<ScoringEntity> scoringEntities;
    private int round = 0;
    //private boolean showPlayerList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorekeeper);

        game = getIntent().getParcelableExtra("game");
        scoringEntities = game.getScoringEntities();

        getSupportActionBar().setTitle(String.format(getString(R.string.title_scorekeeper), game.getTitle()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_score);
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new ScorekeeperRVAdapter(game.getScoringEntities());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // FIXME Make tapping the RVItem open/close the player list
        /*recyclerView.addOnItemTouchListener(new RVTouchListener(this, recyclerView, new RVTouchListener.OnClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                TextView playerList = (TextView) view.findViewById(R.id.text_view_score_player_list);

                Log.d("debug", "Calling onClick...");

                if (game.needsTeams() && showPlayerList)
                {
                    Log.d("debug", "Setting Visibility: VISIBLE");
                    showPlayerList = false;
                    playerList.setVisibility(View.VISIBLE);
                }
                else
                {
                    Log.d("debug", "Setting Visibility: GONE");
                    showPlayerList = true;
                    playerList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLongClick(View view, int position)
            {
            }
        }));*/
        recyclerView.setAdapter(adapter);

        roundScores = (Button) findViewById(R.id.button_view_round_scores);
        roundScores.setText(getString(R.string.text_first_round));
    }

    public void onViewRoundScoresClick(View view)
    {
        //TODO Make this display other rounds' scores
    }

    public void onFABFinishRoundClick(View view)
    {
        Intent intent = new Intent(this, SimpleEnterScoreActivity.class);
        intent.putParcelableArrayListExtra("scoringEntities", game.getScoringEntities());

        startActivityForResult(intent, REQUEST_ENTER_SCORES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_ENTER_SCORES)
        {
            if (resultCode == RESULT_OK)
            {
                int[] scores = data.getIntArrayExtra("scores");

                for (int i = 0; i < scores.length; i++)
                {
                    scoringEntities.get(i).addScore(scores[i]);
                    adapter.notifyItemChanged(i);
                }

                roundScores.setText(String.format(getString(R.string.text_round), ++round));
                Log.d("gameInfo", game.toString());
            }/*
            else if (resultCode == RESULT_CANCELED)
            {
            }*/
        }
    }
}