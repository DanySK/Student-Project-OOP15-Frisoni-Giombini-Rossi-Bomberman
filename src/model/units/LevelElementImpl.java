package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;



/**
 * Implementation of {@link LevelElement}.
 *
 */
public class LevelElementImpl implements LevelElement {

    /**
     * The parameter curPos is the current position
     * of a game element.
     */
    protected Point curPos;
    
    /**
     * The hitBox id the rectangle used for collisions
     * between game elements.
     */
    protected Rectangle hitBox;
    
    /**
     * It creates a LevelElementImpl, a game element.
     * 
     * @param pos
     *          the initial position
     * @param dim
     *          the dimension
     */
    public LevelElementImpl(final Point pos, final Dimension dim) {
        this.curPos = pos;
        this.hitBox = new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }
    
    @Override
    public Point getPosition() {
        return new Point(this.curPos);
    }
    
    @Override
    public Rectangle getHitbox() {
        return (Rectangle) this.hitBox.clone();
    }

    @Override
    public int getX() {
        return this.curPos.x;
    }
    
    @Override
    public int getY() {
        return this.curPos.y;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("\tPosition: (")
                .append(this.getX())
                .append(", ")
                .append(this.getY())
                .append(").")
                .toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((curPos == null) ? 0 : curPos.hashCode());
        result = prime * result + ((hitBox == null) ? 0 : hitBox.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof LevelElementImpl 
                && this.curPos.equals(((LevelElementImpl) obj).curPos)
                && this.hitBox.equals(((LevelElementImpl) obj).hitBox);
    }
    
}
