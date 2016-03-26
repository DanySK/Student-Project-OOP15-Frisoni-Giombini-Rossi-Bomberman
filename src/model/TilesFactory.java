package model;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import model.units.PowerUpType;
import model.utilities.MapPoint;

/**
 * This class allows to establish which type of tile
 * there will be in a specific position.
 */
public class TilesFactory {

    private final int rows;
    private final int columns;
    private final double blockDensity;
    private final double powerupDensity;
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
    public TilesFactory(final int rows, final int columns, final double blockDensity, 
            final double powerupDensity, final int tileDimension) {
        this.rows = rows;
        this.columns = columns;
        this.blockDensity = blockDensity;
        this.powerupDensity = powerupDensity;
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
        final TileType type = getTypeForCoordinates(row, column);
        final Optional<PowerUpType> powerup = this.getPowerup(type);
        return new Tile(type, powerup, row, column, this.tileDimension);
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
        if (this.tileIsConcrete(row, column)) {
            return TileType.CONCRETE;
        } else if (Math.random() < this.blockDensity && !MapPoint.isEntryPoint(row, column)) {
            return TileType.RUBBLE;
        }else {
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
        return row == 0 || column == 0 || row == this.rows - 1 || column == this.columns - 1
                || row % 2 == 0 && column % 2 == 0;
    }

    /**
     * Gets a correct type of powerup for the specified block.
     * 
     * @param type
     *          block type
     * @return an Optional<Powerup> because a block might not have a powerup
     */
    private Optional<PowerUpType> getPowerup(final TileType type){
        if(!type.equals(TileType.RUBBLE)){
                return Optional.empty();
        }
        else{
            if(Math.random() < this.powerupDensity){
                return Optional.empty();
            } else {
                int pType = this.getPowerUpType();
                return Optional.of(PowerUpType.values()[pType]);
            }
        }
    }

    /**
     * Gets a random powerup type.
     * 
     * @return a powerup type
     */
    public int getPowerUpType(){
        return new Random().nextInt(PowerUpType.values().length - 1);
    }
    
    /**
     * Set a random tile's type equals to the closed door.
     * 
     * @param walkableTiles
     *          the set of walkable tiles
     */
    public void setDoor(final Set<Tile> walkableTiles){
        walkableTiles.stream().findAny().get().setType(TileType.DOOR_CLOSED);
    }

    /**
     * Set the powerup of a random tile equals to the key.
     * 
     * @param rubbleTiles
     *          the set of rubbles tiles
     */
    public void setKey(final Set<Tile> rubbleTiles) {
        rubbleTiles.stream().findAny().get().setKeyPowerUp();
    }
}
