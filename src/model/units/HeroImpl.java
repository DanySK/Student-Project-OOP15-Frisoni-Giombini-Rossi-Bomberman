package model.units;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Implementation of {@link Hero}.
 *
 */
public class HeroImpl extends AbstractEntity implements Hero {
    
    private boolean inMovement;
    
    public HeroImpl(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim);
        this.inMovement = false;
    }

    public void move(final Direction d) {
        super.updatePosition(d.getPoint(d.ordinal()));
        super.updateDirection(d);
    }
    
    @Override
    public void setMoving(final boolean b) {
        this.inMovement = b;
    }

    @Override
    public boolean isMoving() {
        return this.inMovement;
    }
}
