package controller;

import java.util.List;

import controller.utilities.Pair;

public interface ScoresManagement {

    /**
     * This method saves the score of a game just ended.
     * @param score is the score of a game.
     * @param time is the time of a game.
     */
    void saveScore(final int score, final int time);
    
    /**
     * @return a list of pairs-time score of the ten best scores.
     */
    List<Pair<Integer, Integer>> getScores();
    
    /**
     * This method create a file where the scores will be saved.
     */
    void createFile();
    
    /**
     * This method save the player's name in file.
     * @param playerName is the player's name.
     */
    void saveName(final String playerName);
    
    /**
     * This method find the lowest score.
     * @return the lowest score.
     */
    int getMinScore();
    
}
