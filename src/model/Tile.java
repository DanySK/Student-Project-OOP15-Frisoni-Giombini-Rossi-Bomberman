package model;

import java.awt.Rectangle;
import model.utilities.MapPoint;

/**
 * This class is used to identify a single Tile
 * in the map.
 */
public class Tile {

    private final TileType type;
    private final Rectangle boundBox;
    private final int row;
    private final int column;

    /**
     * Constructs a tile.
     * 
     * @param type
     *          tile's type
     * @param row
     *          tile's row
     * @param column
     *          tile's column
     * @param tileSize
     *          tile's width/height
     */
    public Tile(final TileType type, final int row, final int column, final int tileSize) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.boundBox = new Rectangle(MapPoint.getCoordinate(this.row, tileSize),
                                      MapPoint.getCoordinate(this.column, tileSize),
                                      tileSize, 
                                      tileSize);
    }

    /**
     * This method return the type of the tile.
     * 
     * @return the tile's type
     */
    public TileType getType() {
        return type;
    }
    
    /**
     * This method return the row at which there's
     * the tile.
     * 
     * @return the row
     */
    public int getRow(){
        return this.row;
    }
    
    /**
     * This method return the column at which there's 
     * the tile
     * 
     * @return the column
     */
    public int getCol() {
        return this.column;
    }
    
    /**
     * This method allows to have the boundBox of the tile.
     * 
     * @return the boundBox
     */
    public Rectangle getBoundBox() {
        return (Rectangle) this.boundBox.clone();
    }
}
