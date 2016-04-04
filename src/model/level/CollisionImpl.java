package model.level;

import java.awt.Rectangle;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.units.Direction;
import model.units.Entity;

/**
 * Implementation of {@link Collision} 
 */
public class CollisionImpl implements Collision{

    protected final Entity entity;
    protected final Rectangle entityRec;

    /**
     * The constructor initialize Collision's element
     * bringing them from the Level.
     * 
     * @param map       
     *          the background of the game
     * @param hero 
     *          the hero entity
     */
    public CollisionImpl(final Entity entity){
        this.entity = entity;
        this.entityRec = this.entity.getHitbox();
    }

    /**
     * This method checks if there's a collision with blocks.
     * 
     * @param blockSet
     *          the set of concrete and rubble blocks
     * @return true if there's a collision, false otherwise
     */
    public boolean blockCollision(final Set<Rectangle> blockSet){
        return this.elementCollision(blockSet, new Predicate<Rectangle>(){
            @Override
            public boolean test(final Rectangle rec) {
                return entityRec.intersects(rec);
            }
        });
    }
    
    /**
     * This method checks if there's a collision with planted bombs.
     * 
     * @param bombSet
     *          the set of planted bombs
     * @return true if there's a collision, false otherwise
     */
    public boolean bombCollision(final Set<Rectangle> bombSet){
        return this.elementCollision(bombSet, new Predicate<Rectangle>(){
            @Override
            public boolean test(final Rectangle rec) {
                if(explosionIntersection(rec)){
                    return false;
                }
                else{
                    return entityRec.intersects(rec);
                }
            }
        });
    }

    /**
     * This method checks if there's a collision with all afflicted tiles.
     * 
     * @param afflictedTiles
     *          the set of afflicted tiles
     * @return true if there's a collision, false otherwise
     */
    public boolean fireCollision(final Set<Tile> afflictedTiles){ 
        for(final Tile tile: afflictedTiles){                                          
            if(this.explosionIntersection(tile.getHitbox())){
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to check if there's a collision whit a generic element.
     * @param <Y>
     * 
     * @param set
     *          the set of elements
     * @param pred
     *          the predicate
     * @return true if there's a collision, false otherwise
     */
    public <X> boolean elementCollision(final Set<X> set, final Predicate<X> pred){
        for(final X rec: set){
            if(pred.test(rec)){
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to check a "static" collision, 
     * if there's a collision without a movement.
     * 
     * @param rec       
     *          the element hitbox
     * @return true if there's a collision, false otherwise
     */
    public boolean explosionIntersection(final Rectangle rec){       
        return this.entity.getHitbox().intersects(rec);
    }

    /**
     * This method updates the new Rectangle that the entity would have.
     * 
     * @param dir 
     *          the direction where the entity would move
     */
    public void updateEntityRec(final Direction dir){
        this.entityRec.setBounds(new Rectangle(this.entity.getPossiblePos(dir.getPoint()).x, 
                this.entity.getPossiblePos(dir.getPoint()).y, 
                this.entity.getHitbox().width, 
                this.entity.getHitbox().height));
    }

}
