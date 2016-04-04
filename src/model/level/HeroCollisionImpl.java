package model.level;

import java.awt.Rectangle;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.TileType;
import model.units.Entity;
import model.units.Hero;

/**
 * Implementation of {@link HeroCollision}
 */

public class HeroCollisionImpl extends CollisionImpl implements HeroCollision{

    /**
     * Constructs a new HeroCollision object.
     * 
     * @param entity
     *          the hero
     */
    public HeroCollisionImpl(Entity entity) {
        super(entity);
    }

    /**
     * This method checks if there's a collision with power ups.
     * 
     * @param powerUpSet
     *          the set of tiles that contains a power up
     * @return true if there's a collision, false otherwise
     */
    public boolean powerUpCollision(final Set<Tile> powerUpSet){
        return super.elementCollision(powerUpSet, new Predicate<Tile>(){
            @Override
            public boolean test(final Tile t) {
                if(entityRec.intersects(t.getHitbox())){
                    t.getPowerup().get().apply((Hero) entity);
                    t.removePowerUp();
                    t.setType(TileType.WALKABLE);
                }
                return false;
            }
        });
    }

    /**
     * This method checks if there's a collision with the open door.
     * 
     * @return true if there's a collision, false otherwise
     */
    @Override
    public boolean openDoorCollision(final Rectangle doorOpened) {
        return entityRec.intersects(doorOpened);
    }

}
