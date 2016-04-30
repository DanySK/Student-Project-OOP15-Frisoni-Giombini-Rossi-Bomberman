package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

/**
 * Implementation of {@link Tile}.
 */
public class TileImpl extends LevelElementImpl implements Tile {

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
    public TileImpl(final Point pos, final Dimension dim, final TileType type, 
            final Optional<PowerUpType> powerup) {
        super(pos, dim);
        this.type = type;
        this.powerup = powerup;
    }
    
    @Override
    public TileType getType() {
        return type;
    }

    @Override
    public Optional<PowerUpType> getPowerup() {
        return this.powerup;
    }

    @Override
    public void setType(final TileType newType) {
        this.type = newType;
    }

    @Override
    public void setKeyPowerUp() {
        this.powerup = Optional.of(PowerUpType.KEY);
    }

    @Override
    public void removePowerUp() {
        this.powerup = Optional.empty();
    }

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
        return obj instanceof TileImpl && this.type.equals(((TileImpl) obj).type)
                && this.powerup.equals(((TileImpl) obj).powerup);
    }
}
