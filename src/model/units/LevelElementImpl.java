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
    
    public LevelElementImpl(final Point pos, final Dimension dim) {
        this.curPos = pos;
        this.hitBox = new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }
    
    @Override
    public void updatePosition(Point p) {
        this.curPos = new Point(p);
        this.updateHitbox();
    }
    
    @Override
    public void updateHitbox() {
        this.hitBox.setLocation(this.curPos);
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
}
