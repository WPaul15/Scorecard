package com.wmpaul.scorecard.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Player extends ScoringEntity implements Parcelable
{
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>()
    {
        public Player createFromParcel(Parcel in)
        {
            return new Player(in);
        }

        public Player[] newArray(int size)
        {
            return new Player[size];
        }
    };

    private int team;
    private int oldTeam; // Used for moving players in GameSetupActivity.onFinishEditPlayer()

    public Player()
    {
        super();
        this.team = 0; // No team
        this.oldTeam = 0; // No old team
    }

    public Player(String name, int team)
    {
        super(name, 0);
        this.team = team;
        this.oldTeam = team;
    }

    public Player(Parcel in)
    {
        super(in);
        this.team = in.readInt();
        this.oldTeam = in.readInt();
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
        out.writeInt(team);
        out.writeInt(oldTeam);
    }

    /*@Override
    public int compareTo(Player player)
    {
        if (player == null) throw new NullPointerException();
        else
        {
            if (this.text_view_game_setup_team > player.text_view_game_setup_team) return 1;
            else if (this.text_view_game_setup_team < player.text_view_game_setup_team) return -1;
            else return 0;
        }
    }*/

    /* General Getters and Setters */
    public int getTeam()
    {
        return team;
    }

    public void setTeam(int team)
    {
        this.team = team;
    }

    public int getOldTeam()
    {
        return oldTeam;
    }

    public void setOldTeam(int oldTeam)
    {
        this.oldTeam = oldTeam;
    }
    /* End General Getters and Setters */

    public String toString()
    {
        String output = "";

        output += "Name: " + getName() + " | ";
        output += "Scores: " + getScores().toString() + " | ";
        output += "Team: " + this.team;

        return output;
    }
}