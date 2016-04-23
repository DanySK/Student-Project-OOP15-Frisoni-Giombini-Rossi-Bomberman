package model.units;

import java.awt.Rectangle;
import java.util.Set;

import model.Tile;

/**
 * This class models a Hero.
 *
 */
public interface Hero extends Entity {

    /**
     * Implements hero's movement.
     * 
     * @param dir
     *          the direction where to move
     * @param blockSet
     *          the set of blocks
     * @param bombSet
     *          the set of bombs
     * @param powerUpSet
     *          the set of powerups
     */
    void move(final Direction dir, final Set<Rectangle> blockSet, final Set<Rectangle> bombSet,
            final Set<Tile> powerUpSet);

    
    /**
     * Verifies if hero can plant a bomb in that position.
     * 
     * @param nTiles
     *          the gameMap's size
     * @return true if he can, false otherwise
     */
    boolean canPlantBomb(final int nTiles);

    /**
     * Plants a bomb.
     * 
     * @param nTiles
     *          the gameMap's size
     */
    void plantBomb(final int nTiles);


    /**
     * Increase hero's score.
     * 
     * @param scoreToAdd
     *          score to add
     */
    void increaseScore(final int scoreToAdd);

    /**
     * Checks if there's a collision with the open door.
     * 
     * @param openDoor
     *          the open door
     * @return true if there's a collision, false otherwise
     */
    boolean checkOpenDoorCollision(final Tile openDoor);

    /**
     * This method sets correctly hero
     * for the next level.
     * 
     * @param lives
     *          the lives
     * @param attack
     *          the attack
     * @param score
     *          the score
     */
    void nextLevel(final int lives, final int attack, final int score);

    /**
     * Gets the direction where the hero would move.
     * 
     * @param dir
     *          the direction
     * @return the direction where to move
     */
    Direction getCorrectDirection(final Direction dir);

    /**
     * Returns hero's deonator.
     * 
     * @return hero's detonator
     */
    Detonator getDetonator();

    /**
     * This method set the hero in movement or not. 
     * 
     * @param bool
     *          true if he's in movements, false otherwise
     */
    void setMoving(boolean bool);

    /**
     * Set the hero to be confused or not.
     * 
     * @param bool
     *          true if the's in confusion, false otherwise
     */
    void setConfusion(final boolean bool);

    /**
     * Set the key.
     */
    void setKey();

    /**
     * Checks if the hero's got the key.
     * 
     * @return true if he's got it, false otherwise
     */
    boolean hasKey();

}