package com.wmpaul.scorecard.objects;

import java.util.Comparator;

public class GameComparator implements Comparator<Game>
{
    @Override
    public int compare(Game game1, Game game2)
    {
        return game1.compareTo(game2);
    }
}
