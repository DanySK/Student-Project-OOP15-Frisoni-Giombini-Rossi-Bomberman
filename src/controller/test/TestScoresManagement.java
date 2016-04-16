package controller.test;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import controller.ScoreHandler;
import controller.utilities.Pair;

public class TestScoresManagement {

    //private static final String FILE_NAME = System.getProperty("user.home") 
      //      + System.getProperty("file.separator") + "Bomberman/Scores.dat";

    /**
     * Test to verify the correct rescue and correct ranking.
     */
    @Test
    public void test1() {
        ScoreHandler.getHandler().init("Test.dat");
        ScoreHandler.getHandler().isFilePresent();
        ScoreHandler.getHandler().saveName("Giulia");
        ScoreHandler.getHandler().saveScore(1000, 100);
        ScoreHandler.getHandler().saveScore(5000, 500);
        ScoreHandler.getHandler().saveScore(6000, 600);
        ScoreHandler.getHandler().saveScore(2000, 200);
        Assert.assertEquals(ScoreHandler.getHandler().getLastScores().size(), 4);
        Assert.assertEquals(ScoreHandler.getHandler().getRecord(), new Pair<>(6000, 600));
        System.out.println(ScoreHandler.getHandler().getLastScores());
        System.out.println(ScoreHandler.getHandler().getRecord());
        ScoreHandler.getHandler().saveScore(3000, 300);
        ScoreHandler.getHandler().saveScore(9000, 900);
        ScoreHandler.getHandler().saveScore(4000, 400);
        ScoreHandler.getHandler().saveScore(7000, 700);
        ScoreHandler.getHandler().saveScore(8000, 800);
        ScoreHandler.getHandler().saveScore(10000, 1000);
        Assert.assertEquals(ScoreHandler.getHandler().getLastScores().size(), 10);
        Assert.assertEquals(ScoreHandler.getHandler().getRecord(), new Pair<>(10000, 1000));
        System.out.println(ScoreHandler.getHandler().getLastScores());
        System.out.println(ScoreHandler.getHandler().getRecord());
        ScoreHandler.getHandler().saveScore(30000000, 600);
        Assert.assertEquals(ScoreHandler.getHandler().getLastScores().size(), 10);
        Assert.assertFalse(ScoreHandler.getHandler().getLastScores().contains(new Pair<>(1000,100)));
        Assert.assertEquals(ScoreHandler.getHandler().getRecord(), new Pair<>(30000000, 600));
        System.out.println(ScoreHandler.getHandler().getLastScores());
        System.out.println(ScoreHandler.getHandler().getRecord());
        ScoreHandler.getHandler().saveScore(2500, 300);
        Assert.assertFalse(ScoreHandler.getHandler().getLastScores().contains(new Pair<>(5000, 500)));
        Assert.assertEquals(ScoreHandler.getHandler().getRecord(), new Pair<>(30000000, 600));
        System.out.println(ScoreHandler.getHandler().getLastScores());
        System.out.println(ScoreHandler.getHandler().getRecord());
    }

    /**
     * Test the correct exception handling.
     */
    @Test
    public void test2() {
        ScoreHandler.getHandler().init("Test.dat");
        try {
            ScoreHandler.getHandler().saveName("");
            fail("The player's name can't empty");
        } catch (final IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        try {
            ScoreHandler.getHandler().saveName(null);
            fail("The player's name can't null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        try {
            ScoreHandler.getHandler().saveScore(-100, 1000);
            fail("The score must be positive");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        try {
            ScoreHandler.getHandler().saveScore(300, -500);
            fail("The time must be positive");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
        try {
            ScoreHandler.getHandler().isBestScore(-200);
            fail("The score must be positive");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
    }
}
