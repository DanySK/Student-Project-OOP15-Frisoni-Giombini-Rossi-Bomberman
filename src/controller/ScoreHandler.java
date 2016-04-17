package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

import com.google.common.base.Optional;

import controller.utilities.Pair;

/**
 * This class keeps in memory all of the players data and of respective scores and their timing.
 */
public final class ScoreHandler {

    private static final String NAME_DIRECTORY = System.getProperty("user.home") 
            + System.getProperty("file.separator") + "Bomberman";
    private static final String FILE_NAME = NAME_DIRECTORY + System.getProperty("file.separator") + "Scores.dat";
    private static final int MAX_LENGTH = 10;

    private static volatile ScoreHandler singleton;
    private final Deque<Pair<Integer, Integer>> scores;
    private Optional<Pair<Integer, Integer>> record;
    private String playerName;
    private String fileName;

    /**
     * Constructor for ScoreManagementImpl.
     */
    private ScoreHandler() {
        this.scores = new LinkedList<>();
        this.record = Optional.absent();
        this.fileName = FILE_NAME;
        final File file = new File(this.fileName);
        if (file.exists()) {
            this.readScores();
        }
    }
    
    public void init(final String fileName) {
        this.fileName = NAME_DIRECTORY + System.getProperty("file.separator") + fileName;
    }

    /**
     * This method create a file where the scores will be saved.
     */
    private void createFile() {
        final File file = new File(this.fileName);
        try {
            file.createNewFile();
        } catch (final IOException e) {
            System.err.println(e);
        }
    }

    /**
     * This method read file and saves scores and their respective times in the list.
     */
    @SuppressWarnings("unchecked")
    private void readScores() {
        try (
                final ObjectInputStream reader = new ObjectInputStream(
                        new BufferedInputStream(new FileInputStream(this.fileName)));
                ) {
            try {
                this.playerName = reader.readUTF();
                this.record = (Optional<Pair<Integer, Integer>>) reader.readObject();
                final int size = reader.readInt();
                for (int i = 0; i < size; i++) {
                    this.scores.addLast((Pair<Integer, Integer>) reader.readObject());
                }
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            }
        } catch (final IOException e) {
            System.err.println(e);
        }
    }

    /**
     * This method save the player's name in file.
     * @param playerName is the player's name.
     */
    public void saveName(final String playerName) {
        Objects.requireNonNull(playerName);
        if (playerName.equals("")) {
            throw new IllegalArgumentException("The name's player isn't empty!");
        }
        this.playerName = playerName;
        this.writeData();
    }

    /**
     * This method saves the score of a game just ended.
     * @param score is the score of a game.
     * @param time is the time of a game.
     */
    public void saveScore(final int score, final int time) {
        if (score < 0 || time < 0) {
            throw new IllegalArgumentException("The score and time must be positive!");
        }
        if (!this.record.isPresent() || score > this.record.get().getX()) {
            this.record = Optional.of(new Pair<>(score, time));
        }
        if (this.scores.size() >= MAX_LENGTH) {
            this.scores.removeFirst();
        }
        this.scores.addLast(new Pair<>(score, time));
        this.writeData();
    }

    /**
     * This method writes scores and their respective times to file.
     */
    private void writeData() {
        try (
                final ObjectOutputStream writer = new ObjectOutputStream(
                        new BufferedOutputStream(new FileOutputStream(this.fileName)));
                ) {
            writer.writeUTF(this.playerName);
            writer.writeObject(this.record);
            writer.writeInt(this.scores.size());
            for (final Pair<Integer, Integer> p : this.scores) {
                writer.writeObject(p);
            }
        } catch (final IOException e) {
            System.err.println(e);
        }
    }

    /**
     * This method return a pair of score-time of the best score.
     * @return the best score.
     */
    public Pair<Integer, Integer> getRecord() {
        return new Pair<>(this.record.get().getX(), this.record.get().getY());
    }

    /**
     * This method return a list of pairs score-time of the ten last scores.
     * @return a list of the ten last scores.
     */
    public LinkedList<Pair<Integer, Integer>> getLastScores() {
        return new LinkedList<>(this.scores);
    }

    /**
     * This method check if the queue of scores is empty.
     * @return true if is empty, false otherwise
     */
    public boolean isScoreEmpty() {
        return this.scores.isEmpty();
    }

    /**
     * This method check if file is present and if it not exists creates.
     * @return true if file exists, false otherwise
     */
    public boolean isFilePresent() {
        final File directory = new File(NAME_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
        final File file = new File(this.fileName);
        if (file.exists()) {
            return true;
        } else {
            this.createFile();
            return false;
        }
    }

    /**
     * This method check if the parameter score is best score. 
     * @param score
     *          the current game score
     * @return true if it is best score, false otherwise
     */
    public boolean isBestScore(final int score) {
        if (score < 0) {
            throw new IllegalArgumentException("The score must be positive!");
        }
        return score > this.record.get().getX();
    }
    
    /**
     * This method check if file exists and in case it delete.
     */
    public void reset() {
        final File file = new File(this.fileName);
        if (file.exists()) {
            this.scores.clear();
            this.record = Optional.absent();
            this.writeData();
        }
    }

    /**
     * This method returns the ScoreHandler.
     * If the ScoreHandler is null it creates a new one on the first call.
     * @return the ScoreHandler.
     */
    public static ScoreHandler getHandler() {
        if (singleton == null) {
            synchronized (ScoreHandler.class) {
                if (singleton == null) {
                    singleton = new ScoreHandler();
                }
            }
        }
        return singleton;
    }
}
