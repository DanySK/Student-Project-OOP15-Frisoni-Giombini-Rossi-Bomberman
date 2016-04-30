package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

import model.units.LevelElementImpl;
import model.units.PowerUpType;

/**
 * This class is used to identify a single Tile
 * in the map.
 */
public class Tile extends LevelElementImpl {

    private TileType type;
    private Optional<PowerUpType> powerup;

    /**
     * Constructs a tile.
     * 
     * @param pos
     *          the position 
     * @param dim
     *          the dimension
     * @param type
     *          the type
     * @param powerup
     *          the associated powerup
     */
    public Tile(final Point pos, final Dimension dim, final TileType type, 
            final Optional<PowerUpType> powerup) {
        super(pos, dim);
        this.type = type;
        this.powerup = powerup;
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
     * Returns the Powerup.
     * 
     * @return the powerup associated
     */
    public Optional<PowerUpType> getPowerup() {
        return this.powerup;
    }

    /**
     * Sets a new TileType for the tile.
     * 
     * @param newType
     *          the new type 
     */
    public void setType(final TileType newType) {
        this.type = newType;
    }

    /**
     * Sets the key.
     */
    public void setKeyPowerUp() {
        this.powerup = Optional.of(PowerUpType.KEY);
    }

    /**
     * Remove the powerup.
     */
    public void removePowerUp() {
        this.powerup = Optional.empty();
    }

    /**
     * Tile's toString.
     * 
     * @return tile's description
     */
    public String toString() {
        return new StringBuilder().append("TILE -  ")
                .append("Type is: ")
                .append(this.getType())
                .append(";\n")
                .append("\tPowerUp is: ")
                .append(this.getPowerup())
                .append(";\n")
                .append(super.toString())
                .toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((powerup == null) ? 0 : powerup.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Tile && this.type.equals(((Tile) obj).type)
                && this.powerup.equals(((Tile) obj).powerup);
    }
}
