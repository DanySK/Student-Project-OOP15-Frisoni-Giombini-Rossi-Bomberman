package model.units;

import java.util.Optional;

/**
 * This class models a Tile.
 */
public interface Tile extends LevelElement {
    
    /**
     * This method return the type of the tile.
     * 
     * @return the tile's type
     */
    TileType getType();

    /**
     * Returns the Powerup.
     * 
     * @return the powerup associated
     */
    Optional<PowerUpType> getPowerup();

    /**
     * Sets a new TileType for the tile.
     * 
     * @param newType
     *          the new type 
     */
    void setType(final TileType newType);

    /**
     * Sets the key.
     */
    void setKeyPowerUp();

    /**
     * Remove the powerup.
     */
    void removePowerUp();

}
