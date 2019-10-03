package com.wmpaul.scorecard.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Game implements Parcelable, Comparable<Game>//, IScorecardFile
{
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>()
    {
        public Game createFromParcel(Parcel in)
        {
            return new Game(in);
        }

        public Game[] newArray(int size)
        {
            return new Game[size];
        }
    };

    private String title;
    private ArrayList<Player> players;
    private ArrayList<Team> teams;
    private ArrayList<ScoringEntity> scoringEntities;
    private int minPlayers;
    private int maxPlayers;
    private int recommendedRange;
    private int numTeams;
    private int playersPerTeam;

    public Game()
    {
        this.title = ""; // No title
        this.players = new ArrayList<>(); // No players
        this.teams = new ArrayList<>(); // No teams
        this.scoringEntities = new ArrayList<>(); // No scoring entities
        this.minPlayers = -1; // No minimum
        this.maxPlayers = -1; // No maximum
        this.recommendedRange = -1; // No recommended range
        this.numTeams = 0; // No teams
        this.playersPerTeam = 0; // No teams
    }

    public Game(String title, int minPlayers, int maxPlayers, int recommendedRange, int numTeams, int playersPerTeam)
    {
        this.title = title;
        this.players = new ArrayList<>();
        this.teams = initTeams(numTeams);
        this.scoringEntities = new ArrayList<>();
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.recommendedRange = recommendedRange;
        this.numTeams = numTeams;
        this.playersPerTeam = playersPerTeam;
    }

    public Game(Parcel in)
    {
        this.title = in.readString();
        this.players = in.readArrayList(Player.class.getClassLoader());
        this.teams = in.readArrayList(Team.class.getClassLoader());
        this.scoringEntities = in.readArrayList(ScoringEntity.class.getClassLoader());
        this.minPlayers = in.readInt();
        this.maxPlayers = in.readInt();
        this.recommendedRange = in.readInt();
        this.numTeams = in.readInt();
        this.playersPerTeam = in.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(title);
        out.writeList(players);
        out.writeList(teams);
        out.writeList(scoringEntities);
        out.writeInt(minPlayers);
        out.writeInt(maxPlayers);
        out.writeInt(recommendedRange);
        out.writeInt(numTeams);
        out.writeInt(playersPerTeam);
    }

    public boolean areTeamSizesValid()
    {
        for (Team t : teams)
        {
            if (t.getNumPlayers() != playersPerTeam) return false;
        }

        return true;
    }

    public boolean isNumPlayersInRecommended()
    {
        return (getNumPlayers() >= minPlayers && getNumPlayers() <= minPlayers + recommendedRange);
    }

    public void consolidateEntities()
    {
        if (needsTeams()) scoringEntities.addAll(teams);
        else scoringEntities.addAll(players);
    }

    @Override
    public int compareTo(Game game)
    {
        if (game == null) return -1;
        else return (this.title.compareTo(game.title));
    }

    public String toString()
    {
        String output;

        output = "\nTitle: " + title + "\n";
        output += "Players: " + (players == null ? "null" : players.toString()) + "\n";
        output += "ScoringEntities: " + (scoringEntities == null ? "null" : scoringEntities.toString()) + "\n";
        output += "minPlayers: " + minPlayers + "\n";
        output += "maxPlayers: " + maxPlayers + "\n";
        output += "recommendedRange: " + recommendedRange + "\n";
        output += "numTeams: " + numTeams + "\n";
        output += "playersPerTeam: " + playersPerTeam;

        return output;
    }

    /*@Override
    public JSONObject toJson()
    {
        JSONObject game = new JSONObject();

        try
        {
            game.put("title", title);

            JSONArray arrayPlayers = new JSONArray();
            arrayPlayers.put(0, minPlayers);
            arrayPlayers.put(1, maxPlayers);

            game.put("players", arrayPlayers);
            game.put("recommendedRange", recommendedRange);
            game.put("numTeams", numTeams);
            game.put("playersPerTeam", playersPerTeam);
        }
        catch (JSONException ex)
        {
            Log.e("json", "toJson: " + ex.getLocalizedMessage());
            return null;
        }

        return game;
    }*/

    public int getNumPlayers()
    {
        return players.size();
    }

    public int getNumRecommendedPlayers()
    {
        return minPlayers + recommendedRange;
    }

    public void addPlayer(int index, Player player)
    {
        players.add(index, player);

        if (numTeams > 0) teams.get(player.getTeam() - 1).addPlayer(player);
    }

    public Player getPlayer(int index)
    {
        return players.get(index);
    }

    public void removePlayer(int index)
    {
        Player player = players.remove(index);

        if (numTeams > 0) teams.get(player.getTeam() - 1).removePlayer(index);
    }

    public boolean needsTeams()
    {
        return numTeams > 0;
    }

    /* General Getters and Setters */
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }

    public ArrayList<Team> getTeams()
    {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams)
    {
        this.teams = teams;
    }

    public ArrayList<ScoringEntity> getScoringEntities()
    {
        return scoringEntities;
    }

    public void setScoringEntities(ArrayList<ScoringEntity> scoringEntities)
    {
        this.scoringEntities = scoringEntities;
    }

    public int getMinPlayers()
    {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers)
    {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getRecommendedRange()
    {
        return recommendedRange;
    }

    public void setRecommendedRange(int recommendedRange)
    {
        this.recommendedRange = recommendedRange;
    }

    public int getNumTeams()
    {
        return numTeams;
    }

    public void setNumTeams(int numTeams)
    {
        this.numTeams = numTeams;
    }

    public int getPlayersPerTeam()
    {
        return playersPerTeam;
    }

    public void setPlayersPerTeam(int playersPerTeam)
    {
        this.playersPerTeam = playersPerTeam;
    }
    /* End General Getters and Setters */

    private ArrayList<Team> initTeams(int numTeams)
    {
        ArrayList<Team> teams = new ArrayList<>();

        for (int i = 0; i < numTeams; i++)
        {
            teams.add(new Team(i + 1));
        }

        return teams;
    }
}