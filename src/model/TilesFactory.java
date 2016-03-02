package model;

/**
 * This class allows to establish which type of tile
 * there will be in a specific position.
 */
public class TilesFactory {

    private final int rows;
    private final int columns;
    private final double density;
    private final int tileDimension;

    /**
     * Construct a TileFactory.
     * 
     * @param rows
     *          the number of rows
     * @param columns
     *          the number of columns
     * @param density
     *          the density of the blocks
     * @param tileDimension
     *          the dimension of a tile
     */
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
     *          the row in the map
     * @param column
     *          the column in the map
     * @return the new tile
     */
    public Tile createForCoordinates(final int row, final int column) {
        return new Tile(getTypeForCoordinates(row, column), row, column, this.tileDimension);
    }

    /**
     * Gets a correct type of tile for the specified coordinates.
     * 
     * @param row
     *          the row at which is located
     * @param column
     *          the column at which is located
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
     *          the row of the tile
     * @param column
     *          the column of the tile
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
     *          the tile's row
     * @param column
     *          the tile's column
     * @return true if the tile is an entry point, false otherwise
     */
    private boolean isEntryPoint(final int row, final int column) {
        return row <= 2 && column <= 2;
    }
}
