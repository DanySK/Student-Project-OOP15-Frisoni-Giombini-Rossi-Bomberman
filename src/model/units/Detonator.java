package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * This class represent the hero's bomb set.
 */
public class Detonator {

    private static final int INITIAL_RANGE = 1;
    private static final int INITIAL_BOMBS = 1;
    //private static final Point INITIAL_POS = new Point(0, 0);
    private static final long BOMB_DELAY = 3000L;

    private final Dimension dim;
    private int newRange;
    private int maxBombs;
    private Deque<Bomb> bombList = new LinkedList<>();

    /**
     * It creates a detonator.
     * 
     * @param dim
     *          the dimension of a bomb
     */
    public Detonator(final Dimension dim){
        this.dim = dim;
        this.newRange = INITIAL_RANGE;
        this.maxBombs = INITIAL_BOMBS;
    }

    /**
     * It adds a bomb to the List.
     */
    public void addBomb(final Point p){
        this.bombList.addLast(new BombImpl(p, this.dim));
    }
    
    /**
     * It increases the range of a bomb.
     */
    public void increaseRange(){//vedere quale per bene
        this.newRange++;
    }
    
    public void increaseBombs(){
        this.maxBombs++;
    }
    
    /**
     * This method returns the bomb to be planted.
     * 
     * @param p
     *          the new bomb's position
     * @return the bomb with the position updated
     */
    public Bomb plantBomb(){
        final Bomb b = this.getBombToPlant();
        this.checkRange(b);
        b.setPlanted();
        return b;
    }
    
    /**
     * Checks if the bomb range is updated.
     * 
     * @param b
     *          the bomb
     */
    private void checkRange(final Bomb b){
        if(b.getRange() < this.newRange){
            b.setRange(this.newRange);
        }
    }

    /**
     * Reactivates a bomb that has already exploded.
     */
    public void reactivateBomb(){
        this.bombList.removeFirst();
    }

    /**
     * It returns a bomb to plant.
     * 
     * @return a bomb that can be planted.
     */
    private Bomb getBombToPlant(){
        return this.bombList.stream().filter(b -> !b.isPositioned()).findFirst().get();
    }

    /**
     * It returns the bomb to reactivate.
     * 
     * @return a bomb that has to be reactivated
     */
    public Bomb getBombToReactivate(){
        return this.bombList.stream().filter(b -> b.isPositioned()).findFirst().get();
    }

    /**
     * Gets bomb's delay.
     * 
     * @return bomb's delay
     */
    public long getBombDelay(){
        return BOMB_DELAY;
    }

    /**
     * Checks if there are bombs to plant.
     *  
     * @return true if there's at least a bomb to plant.
     */
    public boolean hasBombs(final Point p){
        if(this.bombList.size() < this.maxBombs){
            this.addBomb(p);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the list of planted bombs.
     * 
     * @return the list of planted bombs
     */
    public LinkedList<Bomb> getPlantedBombs(){
        return this.bombList.stream().filter(b -> b.isPositioned()).collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Gets the actual range of a bomb.
     * 
     * @return the actual range of a bomb
     */
    public int getActualRange(){
        return this.newRange;
    }
    
    /**
     * Reset bomb's range.
     */
    public void resetDetonator(){
        this.newRange = INITIAL_RANGE;
        this.bombList.clear();
        this.maxBombs = INITIAL_BOMBS;
    }
}
