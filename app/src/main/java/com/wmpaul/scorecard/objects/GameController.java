package com.wmpaul.scorecard.objects;

import java.util.ArrayList;

public class GameController
{
    private Game currentGame;
    private ArrayList<ScoringEntity> scoringEntities;

    public GameController(Game game, ArrayList<ScoringEntity> scoringEntities)
    {
        this.currentGame = game;
        this.scoringEntities = scoringEntities;
    }
}