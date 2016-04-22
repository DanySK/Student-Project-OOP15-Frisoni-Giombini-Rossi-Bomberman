package model.units;

import java.awt.Dimension;
import java.awt.Point;

/**
 * This class models the characteristics of a
 * dynamic game element.
 */

public class DynamicLevelElement extends LevelElementImpl{

    /**
     * Construct a game element.
     * 
     * @param pos
     *          the position
     * @param dim
     *          the dimension
     */
    public DynamicLevelElement(final Point pos, final Dimension dim) {
        super(pos, dim);
    }
    
    /**
     * This method is used to update the element's position.
     * 
     * @param p
     *          the new position
     */
    private void updatePosition(final Point p) {
        this.curPos = new Point(p);
    }
    
    /**
     * This method updates element's hitbox.
     */
    private void updateHitbox() {
        this.hitBox.setLocation(this.curPos);
    }

    /**
     * This method updates both position and hitbox of the
     * game element.
     * 
     * @param p
     *          the new position
     */
    public void update(final Point p) {
        this.updatePosition(p);
        this.updateHitbox();
    }
}
