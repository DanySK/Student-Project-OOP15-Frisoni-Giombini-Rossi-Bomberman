package controller.test;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import controller.ScoresManagement;
import controller.ScoresManagementImpl;
import controller.utilities.Pair;

public class TestScoresManagement {

    /**
     * Test to verify the correct rescue and correct ranking.
     */
    @Test
    public void test1() {
        this.checkDirectory();
        final String fileName = this.getFileName();
        final File file = new File(fileName);
        final ScoresManagement fileProva = new ScoresManagementImpl(fileName);
        if (file.exists()) {
            file.delete();
            fileProva.getScores().clear();
        }
        Assert.assertFalse(file.exists());
        fileProva.createFile();
        fileProva.saveName("Giulia");
        Assert.assertEquals(fileProva.getScores().size(), 0);
        fileProva.saveScore(1000, 100);
        fileProva.saveScore(5000, 500);
        fileProva.saveScore(6000, 600);
        fileProva.saveScore(2000, 200);
        Assert.assertEquals(fileProva.getScores().size(), 4);
        fileProva.saveScore(3000, 300);
        fileProva.saveScore(9000, 900);
        fileProva.saveScore(4000, 400);
        fileProva.saveScore(7000, 700);
        fileProva.saveScore(8000, 800);
        fileProva.saveScore(10000, 1000);
        Assert.assertEquals(fileProva.getScores().size(), 10);
        Assert.assertEquals(fileProva.getMinScore(), 1000);
        fileProva.saveScore(30000000, 600);
        Assert.assertEquals(fileProva.getScores().size(), 10);
        Assert.assertFalse(fileProva.getScores().contains(new Pair<>(1000,100)));
        Assert.assertEquals(fileProva.getMinScore(), 2000);
        fileProva.saveScore(2500, 300);
        Assert.assertFalse(fileProva.getScores().contains(new Pair<>(2000,200)));
        Assert.assertEquals(fileProva.getMinScore(), 2500);
    }

    /**
     * Test the correct exception handling.
     */
    @Test
    public void test2() {
        try {
            new ScoresManagementImpl("");
            fail("The file's name can't empty");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        try {
            new ScoresManagementImpl(null);
            fail("The file's name can't null");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        this.checkDirectory();
        final String fileName = this.getFileName();
        final File file = new File(fileName);
        final ScoresManagement fileProva = new ScoresManagementImpl(fileName);
        if (file.exists()) {
            file.delete();
        }
        fileProva.createFile();
        try {
            fileProva.saveName("");
            fail("The player's name can't empty");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        try {
            fileProva.saveName(null);
            fail("The player's name can't null");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        fileProva.saveName("Giulia");
        try {
            fileProva.saveScore(-100, 1000);
            fail("The score must be positive");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        try {
            fileProva.saveScore(300, -500);
            fail("The time must be positive");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
    }
    
    /**
     * This method return the name of file.
     * @return fileName
     */
    private String getFileName() {
        return System.getProperty("user.home") + System.getProperty("file.separator") + "Bomberman/Prova.properties";
    }
    
    /**
     * This method checks if the Bomberman folder exists, and if it creates.
     */
    private void checkDirectory() {
        final String nameDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "Bomberman";
        final File directory = new File(nameDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
