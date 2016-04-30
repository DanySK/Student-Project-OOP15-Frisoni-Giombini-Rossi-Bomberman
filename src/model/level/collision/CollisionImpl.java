package model.level.collision;

import java.awt.Rectangle;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.units.Direction;
import model.units.Entity;

/**
 * Implementation of {@link Collision}. 
 */
public class CollisionImpl implements Collision {

    protected final Entity entity;
    protected final Rectangle entityRec;

    /**
     * The constructor initialize Collision's element
     * bringing them from the Level.
     * 
     * @param entity 
     *          the entity that has the collision
     */
    public CollisionImpl(final Entity entity) {
        this.entity = entity;
        this.entityRec = this.entity.getHitbox();
    }

    @Override
    public boolean blockCollision(final Set<Rectangle> blockSet) {
        return this.elementCollision(blockSet, (rec) -> entityRec.intersects(rec));
    }

    @Override
    public boolean bombCollision(final Set<Rectangle> bombSet) {
        return this.elementCollision(bombSet, (rec) -> {
            if (this.explosionIntersection(rec)) {
                return false;
            } else {
                return entityRec.intersects(rec);
            }
        });
    }

    @Override
    public boolean fireCollision(final Set<Tile> afflictedTiles) { 
        return afflictedTiles.stream()
                .anyMatch(tile -> this.explosionIntersection(tile.getHitbox()));
    }

    @Override
    public <X> boolean elementCollision(final Set<X> set, final Predicate<X> pred) {
        return !set.stream().anyMatch(rec -> pred.test(rec));
    }

    @Override
    public boolean explosionIntersection(final Rectangle rec) {       
        return this.entity.getHitbox().intersects(rec);
    }

    @Override
    public void updateEntityRec(final Direction dir) {
        this.entityRec.setBounds(new Rectangle(this.entity.getPossiblePos(dir.getTranslation()).x, 
                this.entity.getPossiblePos(dir.getTranslation()).y, 
                this.entity.getHitbox().width, 
                this.entity.getHitbox().height));
    }

}
