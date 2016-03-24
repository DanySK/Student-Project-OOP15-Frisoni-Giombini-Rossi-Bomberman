package model.level;

import java.awt.Rectangle;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.units.Direction;
import model.units.Entity;

/**
 * This class is used to implement collision between
 * entities and game's element.
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
     * This method checks if there's a collision with all afflicted tiles.
     * 
     * @param afflictedTiles
     *          the set of afflicted tiles
     * @param recEntity 
     *          hero's hitbox in that specific moment
     * @return true if there's a collision, false otherwise
     */
    public boolean fireCollision(final Set<Tile> afflictedTiles, final Rectangle recEntity){ 
        for(final Tile tile: afflictedTiles){                                          
            if(this.explosionIntersection(tile.getBoundBox(), recEntity)){
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
     * @param eRec
     *          the entity hitbox
     * @return true if there's a collision, false otherwise
     */
    public boolean explosionIntersection(final Rectangle rec, final Rectangle eRec){       
        return eRec.intersects(rec);
    }

    /**
     * This method construct the new Rectangle that the entity would have.
     * 
     * @return the Rectangle related to the new possible position
     */
    public void updateEntityRec(final Direction dir){
        this.entityRec.setBounds(new Rectangle(this.entity.getPossiblePos(dir.getPoint()).x, 
                this.entity.getPossiblePos(dir.getPoint()).y, 
                this.entity.getHitbox().width, 
                this.entity.getHitbox().height));
    }

}
