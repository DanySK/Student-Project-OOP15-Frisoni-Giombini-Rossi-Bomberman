package model;

/**
 * Possible types of tile.
 */
public enum TileType {
    
    /**
     * The grass.
     */
    WALKABLE,
    
    /**
     * The destructible block.
     */
    RUBBLE,
    
    /**
     * The indestructible block.
     */
    CONCRETE,
    
    /**
     * The open door.
     */
    DOOR_OPENED,
    
    /**
     * The closed door.
     */
    DOOR_CLOSED,
    
    /**
     * The tile is uncovered.
     */
    POWERUP_STATUS;
}
