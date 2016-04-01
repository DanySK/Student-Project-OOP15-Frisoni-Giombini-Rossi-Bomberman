package model.units;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Implementation of {@link Bomb}
 */
public class BombImpl extends DynamicLevelElement implements Bomb{

    private static final int INITIAL_RANGE = 1;
    
    private int range;
    private boolean isPlanted;
    
    /**
     * It creates a Bomb.
     * 
     * @param pos
     *          the initial position
     * @param dim
     *          the dimension
     */
    public BombImpl(final Point pos, final Dimension dim) {
        super(pos, dim);
        this.range = INITIAL_RANGE;
        this.isPlanted = false;
    }

    /**
     * It gets bomb's range.
     */
    @Override
    public int getRange() {
        return this.range;
    }
    
    /**
     * It increases the range of a bomb.
     */
    @Override
    public void setRange(final int range) {
        this.range = range;        
    }

    /**
     * It set the bomb to be planted or not.
     * 
     * @param b
     *          true if it has to be planted, false otherwise
     */
    @Override
    public void setPlanted() {
        this.isPlanted = true;
    }

    /**
     * It verifies if the bomb is planted.
     */
    @Override
    public boolean isPositioned() {
        return this.isPlanted;
    }
    
}
