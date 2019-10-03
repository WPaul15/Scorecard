package com.wmpaul.scorecard.files;

import android.util.Log;

import com.wmpaul.scorecard.objects.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class GameFileManager extends FileManager
{
    public GameFileManager(String fileName)
    {
        super(fileName);
    }

    public ArrayList<Game> readGameFile(String fileName)
    {
        ArrayList<Game> games = new ArrayList<>();

        if (super.isFilePresent(fileName))
        {
            String jsonData = super.readFile(fileName);
            JSONTokener tokener = new JSONTokener(jsonData);

            String title;
            int minPlayers, maxPlayers, recommendedRange, numTeams, playersPerTeam;

            try
            {
                JSONObject data = (JSONObject) tokener.nextValue();
                JSONArray jsonArray = data.getJSONArray("games");

                for (int i = 0; i < jsonArray.length(); i ++)
                {
                    JSONObject game = jsonArray.getJSONObject(i);

                    if (game != null)
                    {
                        title = game.getString("title");

                        JSONArray players = game.getJSONArray("players");

                        minPlayers = players.getInt(0);
                        maxPlayers = players.getInt(1);

                        recommendedRange = game.getInt("recommendedRange");
                        numTeams = game.getInt("numTeams");
                        playersPerTeam = game.getInt("playersPerTeam");

                        games.add(new Game(title, minPlayers, maxPlayers, recommendedRange, numTeams, playersPerTeam));

                        //Log.d("json", "readGameFile: " + games.get(i).toString());
                    }
                }
            }
            catch (JSONException ex)
            {
                Log.e("json", "readGameFile: " + ex.toString());
            }
        }
        else Log.e("json", "readGameFile: Cannot find file " + fileName);

        return games;
    }
}