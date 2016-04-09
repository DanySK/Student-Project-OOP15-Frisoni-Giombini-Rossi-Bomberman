package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;


/**
 * Implementation of {@link LevelElement}.
 *
 */
public class LevelElementImpl implements LevelElement{

    protected Point curPos;
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
    
    /**
     * Gets the position.
     * 
     * @return the game element's position
     */
    @Override
    public Point getPosition() {
        return new Point(this.curPos);
    }

    /**
     * Gets the hitbox.
     * 
     * @return the game element's hitbox
     */
    @Override
    public Rectangle getHitbox() {
        return (Rectangle) this.hitBox.clone();
    }

    /**
     * Gets x coordinate.
     * 
     * @return x coordinate
     */
    @Override
    public int getX() {
        return this.curPos.x;
    }

    /**
     * Gets y coordinate.
     * 
     * @return y coordinate.
     */
    @Override
    public int getY() {
        return this.curPos.y;
    }
        
    /**
     * LevelElement's toString.
     * 
     * @return level element's description
     */
    @Override
    public String toString(){
        return new StringBuilder().append("\tPosition: (")
                .append(this.getX())
                .append(", ")
                .append(this.getY())
                .append(").")
                .toString();
    }
    
    
}
