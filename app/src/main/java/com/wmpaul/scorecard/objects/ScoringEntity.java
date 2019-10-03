package com.wmpaul.scorecard.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmpaul.scorecard.App;
import com.wmpaul.scorecard.R;

import java.util.ArrayList;

public class ScoringEntity implements Parcelable
{
    public static final Parcelable.Creator<ScoringEntity> CREATOR = new Parcelable.Creator<ScoringEntity>()
    {
        public ScoringEntity createFromParcel(Parcel in)
        {
            return new ScoringEntity(in);
        }

        public ScoringEntity[] newArray(int size)
        {
            return new ScoringEntity[size];
        }
    };

    private String name;
    private ArrayList<Integer> scores;
    private int totalScore;

    public ScoringEntity()
    {
        this.name = ""; // No name
        scores = new ArrayList<>(); // No scores
        this.totalScore = 0; // No score
    }

    public ScoringEntity(String name, int initialScore)
    {
        this.name = name;
        this.scores = new ArrayList<>();
        scores.add(initialScore);
        totalScore += initialScore;
    }

    public ScoringEntity(Parcel in)
    {
        this.name = in.readString();
        this.scores = in.readArrayList(Integer.class.getClassLoader());
        this.totalScore = in.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(name);
        out.writeList(scores);
        out.writeInt(totalScore);
    }

    public void addScore(int score)
    {
        totalScore += score;
        scores.add(totalScore);
    }

    /* General Getters and Setters */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<Integer> getScores()
    {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores)
    {
        this.scores = scores;
    }
    /* End General Getters and Setters */

    public int getLastScore()
    {
        return scores.get(scores.size() - 1);
    }

    public String getChangeFromPreviousScore()
    {
        int change = 0;

        if (scores.size() >= 2) change = scores.get(scores.size() - 1) - scores.get(scores.size() - 2);
        String output = App.getContextResources().getString(R.string.plus_minus) + change;

        if (change > 0) output = "+" + change;
        else if (change < 0) output = change + "";

        return output;
    }
}