package model.level;

import java.awt.Rectangle;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.TileType;
import model.units.Entity;
import model.units.Hero;

public class HeroCollisionImpl extends CollisionImpl implements HeroCollision{

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
                if(entityRec.intersects(t.getBoundBox())){
                    t.getPowerup().get().apply((Hero) entity);
                    t.removePowerUp();
                    t.setType(TileType.WALKABLE);
                }
                return false;
            }
        });
    }


    /**
     * This method checks if there's a collision with planted bombs.
     * 
     * @param bombSet
     *          the set of planted bombs
     * @param recEntity
     *          the hero's hitbox in that specific moment
     * @return true if there's a collision, false otherwise
     */
    public boolean bombCollision(final Set<Rectangle> bombSet, final Rectangle recEntity){
        return super.elementCollision(bombSet, new Predicate<Rectangle>(){
            @Override
            public boolean test(final Rectangle rec) {
                if(explosionIntersection(rec, recEntity)){
                    return false;
                }
                else{
                    return entityRec.intersects(rec);
                }
            }
        });
    }

    @Override
    public boolean openDoorCollision(final Rectangle doorOpened) {
        return entityRec.intersects(doorOpened);
    }

}
