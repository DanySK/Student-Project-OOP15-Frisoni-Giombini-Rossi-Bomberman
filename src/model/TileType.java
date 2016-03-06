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
    DOOR_OPEN,
    
    /**
     * The closed door.
     */
    DOOR_CLOSED;
}
