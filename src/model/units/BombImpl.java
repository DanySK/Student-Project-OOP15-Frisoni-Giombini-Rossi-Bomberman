package model.units;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Implementation of {@link Bomb}.
 */
public class BombImpl extends LevelElementImpl implements Bomb {
    
    private int range;
    private boolean isPlanted;
    
    /**
     * It creates a Bomb.
     * 
     * @param pos
     *          the initial position
     * @param dim
     *          the dimension
     * @param range
     *          the bomb's range
     */
    public BombImpl(final Point pos, final Dimension dim, final int range) {
        super(pos, dim);
        this.range = range;
        this.isPlanted = false;
    }
    
    @Override
    public int getRange() {
        return this.range;
    }

    @Override
    public void setRange(final int range) {
        this.range = range;        
    }

    @Override
    public void setPlanted(final boolean bool) {
        this.isPlanted = bool;
    }

    @Override
    public boolean isPositioned() {
        return this.isPlanted;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append("BOMB -  ")
                .append("Range is: ")
                .append(this.getRange())
                .append(";\n")
                .append("isPositioned is: ")
                .append(this.isPlanted)
                .append(";\n")
                .append(super.toString())
                .toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (isPlanted ? 1231 : 1237);
        result = prime * result + range;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof BombImpl && this.range == ((BombImpl) obj).range
                && this.isPlanted == ((BombImpl) obj).isPlanted;
    }
    
}
