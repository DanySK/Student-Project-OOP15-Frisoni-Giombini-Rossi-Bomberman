package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Implementation of {@link ScoreManagement}.
 * This class keeps in memory all of the players data and of respective scores and their timing.
 */
public class ScoresManagementImpl implements ScoresManagement{

    private static final int MAX_LENGTH = 10;
    private Optional<List<Pair<Integer, Integer>>> scores = Optional.empty();
    private final String fileName;
    private final Properties prop;

    /**
     * Constructor for ScoreManagementImpl.
     */
    public ScoresManagementImpl(){
        this.fileName = System.getProperty("user.home")+System.getProperty("file.separator")+"Scores.properties";
        //System.out.println("Using file: "+fileName);
        this.prop = new Properties();
        if(this.scores.equals(Optional.empty())){
            this.scores = Optional.of(new ArrayList<>());
        }
        File file = new File(this.fileName);
        if (file.exists()) {
            this.readScores();
        }
    }

    @Override
    public void saveName(final String playerName){
        try(
                OutputStream output = new FileOutputStream(this.fileName);
                ){
            this.prop.setProperty("NAME:", playerName);
            this.prop.setProperty("N_SCORES:", String.valueOf(this.scores.get().size()));
            this.prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method read file and saves scores and their respective times in the list.
     */
    private void readScores(){
        try(
                InputStream input = new FileInputStream(this.fileName);
                ){
            this.prop.load(input);
            for(int i = 1; i <= Integer.valueOf(this.prop.getProperty("N_SCORES:")); i++){
                this.scores.get().add(new Pair<>(Integer.valueOf(this.prop.getProperty(i+" SCORE:")), Integer.valueOf(this.prop.getProperty(i+" TIME:"))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveScore(int score, int time) {
        if(score < 0 || time < 0){
            throw new IllegalArgumentException();
        }
        if(this.scores.get().size() < MAX_LENGTH){
            this.scores.get().add(new Pair<>(score, time));
        }
        else{
            if(score > this.checkMinScore()){
                this.scores.get().remove(this.getIndexMinScore());
                this.scores.get().add(new Pair<>(score, time));
            }
        }
        this.writeScores();
    }

    /**
     * This method writes scores and their respective times to file.
     */
    private void writeScores(){
        try(
                OutputStream output = new FileOutputStream(this.fileName);
                ){
            this.prop.setProperty("N_SCORES:", String.valueOf(this.scores.get().size()));
            for(int i = 1; i <= this.scores.get().size(); i++){
                this.prop.setProperty(i+" SCORE:", String.valueOf(this.scores.get().get(i-1).getX()));
                this.prop.setProperty(i+" TIME:", String.valueOf(this.scores.get().get(i-1).getY()));
            }
            this.prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pair<Integer, Integer>> getScores() {
        return this.scores.get();
    }

    /**
     * @return the index
     */
    private int getIndexMinScore() {
        int index = -1;
        for(int i = 0; i < this.scores.get().size(); i++){
            if(this.scores.get().get(i).getX() == this.checkMinScore()){
                index = i;
            }
        }
        return index;
    }

    private int checkMinScore() {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < this.scores.get().size(); i++){
            if(this.scores.get().get(i).getX() < min){
                min = this.scores.get().get(i).getX();
            }
        }
        return min;
    }

    @Override
    public void createFile(String nameFile) {
        File file = new File(nameFile);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
