package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import model.Tile;
import model.level.collision.Collision;
import model.level.collision.HeroCollision;
import model.level.collision.HeroCollisionImpl;
import model.utilities.MapPoint;

/**
 * Implementation of {@link Hero}.
 */
public class HeroImpl extends AbstractEntity implements Hero {

    private final Detonator detonator;
    private final HeroCollision heroCollision;
    private boolean inConfusion;
    private boolean key;

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
    public HeroImpl(final Point pos, final Dimension dim) {
        super(pos, dim);
        this.detonator = new Detonator(dim);
        this.heroCollision = new HeroCollisionImpl(this);
        this.inConfusion = false;
        this.key = false;
    }

    @Override
    public void move(final Direction dir, final Set<Rectangle> blockSet, 
            final Set<Rectangle> bombSet, final Set<Tile> powerUpSet) {
        this.heroCollision.updateEntityRec(dir);
        if (this.heroCollision.blockCollision(blockSet) && this.heroCollision.bombCollision(bombSet)
                && this.heroCollision.powerUpCollision(powerUpSet)) {
            this.setMoving(true);
            super.move(dir);
        }
    }

    @Override
    public boolean canPlantBomb(final int nTiles) {
        final Point point = new Point(MapPoint.getCorrectPos(this.getX(), nTiles,
                this.getHitbox().width), 
                MapPoint.getCorrectPos(this.getY(), nTiles, 
                        this.getHitbox().height));
        return !this.getDetonator().getPlantedBombs().stream()
                .anyMatch(b -> b.getPosition().equals(point));
    }

    @Override
    public void plantBomb(final int nTiles) {
        this.getDetonator().plantBomb(new Point(MapPoint.getCorrectPos(this.getX(), nTiles,
                this.getHitbox().width), 
                MapPoint.getCorrectPos(this.getY(), nTiles, this.getHitbox().height)));
    }

    @Override
    public void increaseScore(final int scoreToAdd) {
        super.score += scoreToAdd; 
    }

    @Override
    public boolean checkOpenDoorCollision(final Tile doorOpened) {
        return this.heroCollision.openDoorCollision(doorOpened.getHitbox());
    }

    @Override
    public void nextLevel(final int lives, final int attack, final int score) {
        this.modifyLife(lives - 1);
        this.increaseAttack(attack - 1);
        this.increaseScore(score); 
    }

    @Override
    public Direction getCorrectDirection(final Direction dir) {
        return this.inConfusion ? dir.getOppositeDirection() : dir;
    }

    @Override
    public Detonator getDetonator() {
        return this.detonator;
    }

    @Override
    public Collision getCollision() {
        return this.heroCollision;
    }

    @Override
    public void setMoving(final boolean bool) {
        super.inMovement = bool;
    }

    @Override
    public void setConfusion(final boolean bool) {
        this.inConfusion = bool;
    }

    @Override
    public void setKey() {
        this.key = true;
    }

    @Override
    public boolean hasKey() {
        return this.key;
    }

    @Override
    public String toString(){
        return new StringBuilder().append("HERO -  ")
                .append(super.toString())
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (inConfusion ? 1231 : 1237);
        result = prime * result + (key ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof HeroImpl && this.inConfusion == ((HeroImpl) obj).inConfusion
                && this.key == ((HeroImpl) obj).key;
    }

}
