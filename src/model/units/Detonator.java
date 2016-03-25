package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * This class represent the hero's bomb set.
 */
public class Detonator {

    private static final int INITIAL_RANGE = 1;
    private static final Point INITIAL_POS = new Point(0, 0);
    private static final long BOMB_DELAY = 3000L;

    private final Dimension dim;
    private int newRange;
    private LinkedList<Bomb> bombList = new LinkedList<>();

    /**
     * It creates a detonator.
     * 
     * @param dim
     *          the dimension of a bomb
     */
    public Detonator(final Dimension dim){
        this.dim = dim;
        this.newRange = INITIAL_RANGE;
        this.addBomb();
    }

    /**
     * It adds a bomb to the List.
     */
    public void addBomb(){
        this.bombList.addLast(new BombImpl(INITIAL_POS, this.dim));
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
     * It increases the range of a bomb.
     */
    public void increaseRange(){//vedere quale per bene
        this.newRange++;
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
     * This method returns the bomb to be planted.
     * 
     * @param p
     *          the new bomb's position
     * @return the bomb with the position updated
     */
    public Bomb plantBomb(final Point p){
        final Bomb b = this.getBombToPlant();
        this.checkRange(b);
        b.updatePosition(p);
        b.setPlanted(true);
        return b;
    }

    /**
     * Reactivates a bomb that has already exploded.
     */
    public void reactivateBomb(){
        this.bombList.removeFirst();
        this.addBomb();
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
    public boolean hasBombs(){
        return this.bombList.stream().anyMatch(b -> !b.isPositioned());
    }

    /**
     * Gets the list of planted bombs.
     * 
     * @return the list of planted bombs
     */
    public LinkedList<Bomb> getPlantedBombs(){
        return this.bombList.stream().filter(b -> b.isPositioned()).collect(Collectors.toCollection(LinkedList::new));
    }
    
    public int getActualRange(){
        return this.newRange;
    }
    
    /**
     * Reset bomb's range.
     */
    public void resetRange(){
        this.newRange = INITIAL_RANGE;
    }
}
