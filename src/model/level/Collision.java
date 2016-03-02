package model.level;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import model.Tile;
import model.TileType;
import model.units.Direction;
import model.units.Hero;

/**
 * This class is used to implement collision between
 * entities and game's element.
 */
public class Collision {

    private Tile[][] map;
    private Hero hero;
    private Direction curDir;
    
    /**
     * The constructor initialize Collision's element
     * bringing them from the Level.
     * 
     * @param map       
     *          the background of the game
     * @param hero 
     *          the hero entity
     */
    public Collision(final Tile[][] map, final Hero hero){
        this.map = map;
        this.hero = hero;
        this.curDir = Direction.DOWN;
    }
    
    /**
     * This method build a List of all indestructible blocks.
     * 
     * @return the list of blocks
     */
    private List<Rectangle> getBlocks(){
        List<Rectangle> blockList = new ArrayList<>();
        for(int i = 0; i < this.map.length; i++){
            for(int j = 0; j < this.map.length; j++){
                if(!this.map[i][j].getType().equals(TileType.WALKABLE)){
                    blockList.add(this.map[i][j].getBoundBox());
                }
            }
        }
        return blockList;
    }
    
    /**
     * This method checks if there's a collision with blocks.
     * 
     * @return true if there's a collision, false otherwise
     */
    public boolean blockCollision(){
        List<Rectangle> blockList = this.getBlocks();
        Rectangle heroRec = this.getHeroRec();
        for(Rectangle rec: blockList){
            if(heroRec.intersects(rec)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method set the Direction where the entity would go
     * 
     * @param dir
     *          the direction where to go
     */
    public void setDirection(Direction dir){
        this.curDir = dir;
    }
    
    /**
     * This method construct the new Rectangle that the entity would have.
     * 
     * @return
     *          the Rectangle related to the new possible position
     */
    public Rectangle getHeroRec(){
        return new Rectangle(this.hero.getPossiblePos(this.curDir.getPoint()).x, 
                             this.hero.getPossiblePos(this.curDir.getPoint()).y, 
                             this.hero.getHitbox().width, 
                             this.hero.getHitbox().height);
    }
    
}
