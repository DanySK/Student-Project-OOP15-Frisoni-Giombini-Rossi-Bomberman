package model;

import java.awt.Dimension;
import java.awt.Rectangle;

public class Tile {

    private final TileType type;
    private final Rectangle boundBox;
    private final int row;
    private final int column;

    public Tile(final TileType type, final int row, final int column, final Dimension dim) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.boundBox = new Rectangle(dim);
    }

    public TileType getType() {
        return type;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol() {
        return this.column;
    }
    
    public Rectangle getBoundBox() {
        return (Rectangle) this.boundBox.clone();
    }
}
