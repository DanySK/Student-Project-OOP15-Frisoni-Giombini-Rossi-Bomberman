package model.units;

import java.awt.Dimension;
import java.awt.Point;

public class BombImpl extends LevelElementImpl implements Bomb{

    private static final int INITIAL_RANGE = 1;
    
    private int range;
    
    public BombImpl(Point pos, Dimension dim) {
        super(pos, dim);
        this.range = INITIAL_RANGE;
    }

    @Override
    public void increaseRange() {
        this.range++;        
    }

    @Override
    public int getRange() {
        return this.range;
    }
}
