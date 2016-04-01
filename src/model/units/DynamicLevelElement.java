package model.units;

import java.awt.Dimension;
import java.awt.Point;

public class DynamicLevelElement extends LevelElementImpl{

    public DynamicLevelElement(Point pos, Dimension dim) {
        super(pos, dim);
    }
    
    private void updatePosition(final Point p) {
        this.curPos = new Point(p);
    }
    
    private void updateHitbox() {
        this.hitBox.setLocation(this.curPos);
    }

    public void update(Point p) {
        this.updatePosition(p);
        this.updateHitbox();
    }
}
