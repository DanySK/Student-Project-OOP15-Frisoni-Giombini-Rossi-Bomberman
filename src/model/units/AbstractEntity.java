package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractEntity implements Entity {

    /* Starting values */
    private static final int START_SPEED = 1;

    private final Point curPos;
    private Direction curDir;
    private final Rectangle hitBox;
    private final int curSpeed;
    private int step;

    /**
     * Constructs a new Entity.
     */
    public AbstractEntity(final Point pos, final Direction dir, final Dimension dim) {
        this.curPos = pos;
        this.curDir = dir;
        this.hitBox = new Rectangle(dim);
        this.curSpeed = START_SPEED;
        this.setStep();
    }

    public abstract void move(Direction d);

    //MODIFICA STEP DEVE DIVENTARE UNA VARIABILE
    @Override
    public int numberOfSteps() {
        return this.curSpeed * this.step;
    }

    @Override
    public void updatePosition(final Point p) {
        this.curPos.setLocation(this.getX() + p.x * this.numberOfSteps(), this.getY() + p.y * this.numberOfSteps());
        this.updateHitbox();
    }
    
    @Override
    public void updateDirection(final Direction dir) {
        this.curDir = dir;
    }

    @Override
    public void updateHitbox() {
        this.hitBox.setLocation(this.curPos);
    }

    @Override
    public final void setStep() {
        this.step = Math.round(this.hitBox.width / 10);
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
    public int getSpeed() {
        return this.curSpeed;
    }

    @Override
    public Direction getDirection() {
        return this.curDir;
    }

}
