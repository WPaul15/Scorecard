package com.wmpaul.scorecard.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmpaul.scorecard.App;
import com.wmpaul.scorecard.R;

import java.util.ArrayList;

public class Team extends ScoringEntity implements Parcelable
{
    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>()
    {
        public Team createFromParcel(Parcel in)
        {
            return new Team(in);
        }

        public Team[] newArray(int size)
        {
            return new Team[size];
        }
    };

    private int teamNumber;
    private ArrayList<Player> players;

    public Team()
    {
        super();
        teamNumber = -1; // Placeholder
        players = new ArrayList<>(); // No players
    }

    public Team(int teamNumber)
    {
        super(String.format(App.getContextResources().getString(R.string.team_name), teamNumber), 0);
        this.teamNumber = teamNumber;
        players = new ArrayList<>();
    }

    public Team(Parcel in)
    {
        super(in);
        this.teamNumber = in.readInt();
        this.players = in.readArrayList(Player.class.getClassLoader());
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
        out.writeInt(teamNumber);
        out.writeList(players);
    }

    public String getPlayerNames()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < players.size(); i++)
        {
            builder.append(players.get(i).getName());
            if (i < players.size() - 1) builder.append("\n");
        }

        return builder.toString();
    }

    public int getNumPlayers()
    {
        return players.size();
    }

    /* General Getters and Setters */
    public void addPlayer(Player player)
    {
        players.add(player);
    }

    public void removePlayer(int index)
    {
        players.remove(index);
    }

    public int getTeamNumber()
    {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }
    /* End General Getters and Setters */

    public String toString()
    {
        String playerNames = "";

        for (int i = 0; i < players.size(); i++)
        {
            playerNames += players.get(i).getName();
            if (i < players.size() - 1) playerNames += ", ";
        }

        String output = "";

        output += "Name: " + getName() + " | ";
        output += "Players: " + playerNames + " | ";
        output += "Scores: " + getScores().toString();

        return output;
    }
}