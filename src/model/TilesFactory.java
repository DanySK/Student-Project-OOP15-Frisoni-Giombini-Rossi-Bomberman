package model;

import java.awt.Dimension;

public class TilesFactory {

    private final int rows;
    private final int columns;
    private final double density;
    private final int tileDimension;

    public TilesFactory(final int rows, final int columns, final double density, final int tileDimension) {
        this.rows = rows;
        this.columns = columns;
        this.density = density;
        this.tileDimension = tileDimension;
    }

    /**
     * Creates a new tile for the specified coordinates.
     * 
     * @param row
     * @param column
     * @return the new tile
     */
    public Tile createForCoordinates(final int row, final int column) {
        return new Tile(getTypeForCoordinates(row, column), row, column, new Dimension(this.tileDimension, this.tileDimension));
    }

    /**
     * Gets a correct type of tile for the specified coordinates.
     * 
     * @param row
     * @param column
     * @return the type of tile
     */
    private TileType getTypeForCoordinates(final int row, final int column) {
        if (tileIsConcrete(row, column)) {
            return TileType.CONCRETE;
        } else if (Math.random() < density && !isEntryPoint(row, column)) {
            return TileType.RUBBLE;
        } else {
            return TileType.WALKABLE;
        }
    }

    /**
     * Checks if the tile at the specified coordinates refers
     * to a concrete block.
     * 
     * @param row
     * @param column
     * @return true if the tile is concrete, false otherwise
     */
    private boolean tileIsConcrete(final int row, final int column) {
        return row == 0 || column == 0 || row == rows - 1 || column == columns - 1
                || row % 2 == 0 && column % 2 == 0;
    }


    /**
     * Check if the tile refers to a spawn point of the hero.
     * 
     * @param row
     * @param column
     * @return true if the tile is an entry point, false otherwise
     */
    private boolean isEntryPoint(final int row, final int column) {
        return row <= 2 && column <= 2;
    }
}
