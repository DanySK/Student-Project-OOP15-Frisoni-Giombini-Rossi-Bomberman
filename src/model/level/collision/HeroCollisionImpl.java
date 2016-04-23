package model.level.collision;

import java.awt.Rectangle;
import java.util.Set;

import model.Tile;
import model.TileType;
import model.units.Entity;
import model.units.Hero;

/**
 * Implementation of {@link HeroCollision}.
 */

public class HeroCollisionImpl extends CollisionImpl implements HeroCollision{

    /**
     * Constructs a new HeroCollision object.
     * 
     * @param entity
     *          the hero
     */
    public HeroCollisionImpl(final Entity entity) {
        super(entity);
    }

    @Override
    public boolean powerUpCollision(final Set<Tile> powerUpSet) {
        return super.elementCollision(powerUpSet, (tile) -> {
            if (entityRec.intersects(tile.getHitbox())) {
                tile.getPowerup().get().apply((Hero) entity);
                tile.removePowerUp();
                tile.setType(TileType.WALKABLE);
            }
            return false;
        });
    }

    @Override
    public boolean openDoorCollision(final Rectangle doorOpened) {
            return entityRec.intersects(doorOpened);
    }

}
