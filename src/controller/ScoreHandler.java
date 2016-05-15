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
import java.util.List;
import java.util.Objects;

import com.google.common.base.Optional;

import controller.utilities.Pair;
import controller.utilities.ScoreData;
import view.utilities.ESource;

/**
 * This class keeps in memory all of the players data and of respective scores and their timing.
 */
public final class ScoreHandler extends ESource<ScoreData> {

    private static final String NAME_DIRECTORY = System.getProperty("user.home") 
            + System.getProperty("file.separator") + "Bomberman";
    private static final String FILE_NAME = NAME_DIRECTORY 
            + System.getProperty("file.separator") + "Scores.dat";
    private static final int MAX_LENGTH = 10;

    private static volatile ScoreHandler singleton;
    private final Deque<Pair<Integer, Integer>> scores;
    private Optional<Pair<Integer, Integer>> record;
    private String playerName;

    /**
     * Constructor for ScoreManagementImpl.
     */
    private ScoreHandler() {
        this.scores = new LinkedList<>();
        this.record = Optional.absent();
        final File file = new File(FILE_NAME);
        if (file.exists()) {
            this.readScores();
        }
    }

    /**
     * This method create a file where the scores will be saved.
     */
    public void createFile() {
        final File directory = new File(NAME_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
        final File file = new File(FILE_NAME);
        if (file.exists()) {
            throw new UnsupportedOperationException("The file already exists!");
        } else {
            try {
                file.createNewFile();
            } catch (final IOException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * This method read file and saves scores and their respective times in the list.
     */
    @SuppressWarnings("unchecked")
    private void readScores() {
        try (
                final ObjectInputStream reader = new ObjectInputStream(
                        new BufferedInputStream(new FileInputStream(FILE_NAME)));
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
        if ("".equals(playerName)) {
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
        if (this.scores.size() >= MAX_LENGTH) {
            this.scores.removeFirst();
        }
        this.scores.addLast(new Pair<>(score, time));
        if (!this.record.isPresent() || score > this.record.get().getX()) {
            this.record = Optional.of(new Pair<>(score, time));
            this.notifyEObservers(ScoreData.RECORD);
        }
        this.notifyEObservers(ScoreData.LAST_SCORES);
        this.writeData();
    }

    /**
     * This method writes scores and their respective times to file.
     */
    private void writeData() {
        try (
                final ObjectOutputStream writer = new ObjectOutputStream(
                        new BufferedOutputStream(new FileOutputStream(FILE_NAME)));
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
     * This method check if file exists and in case it delete.
     */
    public void reset() {
        final File file = new File(FILE_NAME);
        if (file.exists()) {
            this.scores.clear();
            this.record = Optional.absent();
            this.writeData();
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
    public List<Pair<Integer, Integer>> getLastScores() {
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
     * This method return the playerName.
     * @return the playerName
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * This method check if file is present and if it not exists creates.
     * @return true if file exists, false otherwise
     */
    public boolean isFilePresent() {
        final File file = new File(FILE_NAME);
        return file.exists();
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
        if (this.record.isPresent()) {
            return score > this.record.get().getX();
        } else {
            return true;
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