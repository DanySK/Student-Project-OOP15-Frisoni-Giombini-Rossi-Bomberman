package model.level;

import java.awt.Rectangle;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import model.units.Direction;
import model.units.Entity;
import model.units.Hero;
import model.units.PowerUpType;

/**
 * This class is used to implement collision between
 * entities and game's element.
 */
public class Collision {

    private Entity entity;
    private Rectangle entityRec;

    /**
     * The constructor initialize Collision's element
     * bringing them from the Level.
     * 
     * @param map       
     *          the background of the game
     * @param hero 
     *          the hero entity
     */
    public Collision(final Entity entity){
        this.entity = entity;
        this.entityRec = this.entity.getHitbox();
    }

    /**
     * This method checks if there's a collision with blocks.
     * 
     * @return true if there's a collision, false otherwise
     */
    public boolean blockCollision(Set<Rectangle> blockSet){
        return this.elementCollision(blockSet, new Predicate<Rectangle>(){
            @Override
            public boolean test(Rectangle rec) {
                return entityRec.intersects(rec);
            }
        });
    }

    //SISTEMARE!!!
    public boolean powerupCollision(Map<Rectangle, PowerUpType> powerUpMap){
        if(this.entity instanceof Hero){
            return this.elementCollision(powerUpMap.keySet(), new Predicate<Rectangle>(){
                @Override
                public boolean test(Rectangle rec) {
                    if(entityRec.intersects(rec)){
                        powerUpMap.get(rec).apply((Hero) entity);
                    }
                    return false;
                }
            });
        }
        else{
            return false;
        }
    }

    private boolean elementCollision(Set<Rectangle> set, Predicate<Rectangle> pred){
        for(Rectangle rec: set){
            if(pred.test(rec)){
                return false;
            }
        }
        return true;
    }

    /**
     * This method construct the new Rectangle that the entity would have.
     * 
     * @return
     *          the Rectangle related to the new possible position
     */
    /*public Rectangle updateEntityRec(Direction dir){
        return new Rectangle(this.entity.getPossiblePos(dir.getPoint()).x, 
                             this.entity.getPossiblePos(dir.getPoint()).y, 
                             this.entity.getHitbox().width, 
                             this.entity.getHitbox().height);
    }*/
    public void updateEntityRec(Direction dir){
        this.entityRec.setBounds(new Rectangle(this.entity.getPossiblePos(dir.getPoint()).x, 
                this.entity.getPossiblePos(dir.getPoint()).y, 
                this.entity.getHitbox().width, 
                this.entity.getHitbox().height));
    }

}
