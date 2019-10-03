package com.wmpaul.scorecard.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.adapters.SimpleEnterScoreRVAdapter;
import com.wmpaul.scorecard.objects.ScoringEntity;

import java.util.ArrayList;

public class SimpleEnterScoreActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SimpleEnterScoreRVAdapter adapter;
    private ArrayList<ScoringEntity> scoringEntities;
    private FloatingActionButton fabEnterScore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_enter_score);

        scoringEntities = getIntent().getParcelableArrayListExtra("scoringEntities");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_simple_score);
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new SimpleEnterScoreRVAdapter(scoringEntities);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        fabEnterScore = (FloatingActionButton) findViewById(R.id.fab_enter_scores);
        //fabEnterScore.setVisibility(View.VISIBLE);
    }

    public void onFABEnterScoresClick(View view)
    {
        Intent intent = new Intent();

        intent.putExtra("scores", adapter.getRoundScores());

        for (int i = 0; i < adapter.getRoundScores().length; i++)
        {
            Log.d("scores", "\nscores[" + i + "]: " + adapter.getRoundScores()[i]);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}