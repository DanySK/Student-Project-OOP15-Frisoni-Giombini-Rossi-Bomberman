package model.units;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Implementation of {@link Hero}.
 *
 */
public class HeroImpl extends AbstractEntity implements Hero {
    
    private boolean inMovement;
    
    /**
     * This allow to create a Hero.
     * 
     * @param pos
     *          the initial position
     * @param dir
     *          the initial direction
     * @param dim
     *          the dimension of the hitBox
     */
    public HeroImpl(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim);
        this.inMovement = false;
    }

    /**
     * Hero's strategy to move.
     * 
     * @param d
     *          the direction where to move
     */
    public void move(final Direction d) {
        super.updatePosition(this.getPossiblePos(d.getPoint()));
        super.updateDirection(d);
    }

    @Override
    public void setMoving(boolean b) {
        this.inMovement = b;        
    }

    @Override
    public boolean isMoving() {
        return this.inMovement;
    }
}
