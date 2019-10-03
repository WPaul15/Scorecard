package com.wmpaul.scorecard.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.objects.ScoringEntity;

import java.util.ArrayList;

public class SimpleEnterScoreRVAdapter extends RecyclerView.Adapter<SimpleEnterScoreRVAdapter.SimpleScoreViewHolder>
        implements TextView.OnEditorActionListener
{
    private ArrayList<ScoringEntity> scoringEntities;
    private int[] roundScores;

    public SimpleEnterScoreRVAdapter(ArrayList<ScoringEntity> scoringEntities)
    {
        this.scoringEntities = scoringEntities;
        roundScores = new int[scoringEntities.size()];
    }

    @Override
    public SimpleEnterScoreRVAdapter.SimpleScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row_simple_score, parent, false);
        return new SimpleEnterScoreRVAdapter.SimpleScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimpleEnterScoreRVAdapter.SimpleScoreViewHolder holder, int position)
    {
        holder.name.setText(scoringEntities.get(position).getName());
        holder.score.setOnEditorActionListener(this);
        holder.score.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                try
                {
                    roundScores[holder.getAdapterPosition()] = Integer.parseInt(s.toString());
                }
                catch (NumberFormatException ex)
                {
                    Log.d("error", "NumberFormatException: " + ex.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return scoringEntities.size();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event)
    {
        //if (EditorInfo.IME_ACTION_DONE == actionId) ;

        return false;
    }

    public int[] getRoundScores()
    {
        return roundScores;
    }

    static class SimpleScoreViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        EditText score;

        SimpleScoreViewHolder(View view)
        {
            super(view);

            name = (TextView) view.findViewById(R.id.text_view_simple_score_name);
            score = (EditText) view.findViewById(R.id.edit_text_simple_score_score);
        }
    }
}