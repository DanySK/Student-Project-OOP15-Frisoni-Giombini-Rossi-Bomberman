package model.utilities;

import java.awt.Point;

import model.units.Direction;

/**
 * This class is used to calculate the position
 * of an element in the map or to execute some
 * calculations and conversions.
 */
public final class MapPoint {

    private MapPoint() { }

    /**
     * This method is used to obtain the position's
     * coordinates.
     * 
     * @param pos
     *          the initial position
     * @param tileDimension
     *          the conversion factor
     * @return the position
     */
    public static Point getPos(final Point pos, final int tileDimension) {
        return new Point(pos.x * tileDimension, pos.y * tileDimension);
    }

    /**
     * This method is used to obtain a single coordinate.
     * 
     * @param coordinate
     *          the coordinate to convert
     * @param tileDimension
     *          the conversion factor
     * @return the coordinate
     */
    public static int getCoordinate(final int coordinate, final int tileDimension) {
        return coordinate * tileDimension;
    }  

    /**
     * Calculates the position in the map.
     * 
     * @param coordinate
     *          the coordinate
     * @param nTiles
     *          the number of tiles
     * @param tileDimension
     *          the width of the tile
     * @return the coordinate in the map
     */
    private static int getPos(final int coordinate, final int nTiles, final int tileDimension) {
        int index = 0;
        boolean stop = false;
        for (int i = tileDimension - 1; i < (tileDimension * nTiles) - 1 
                && !stop; i += tileDimension) {
            if (coordinate <= i) {
                stop = true;
            } else {
                index++;
            }
        }
        return index * tileDimension;
    }

    /**
     * Calculates the correct position where to place a bomb.
     * 
     * @param coordinate
     *          the coordinate
     * @param nTiles
     *          the number of tiles in the map
     * @param tileDimension
     *          the dimension of a tile          
     * @return the correct coordinate
     */
    public static int getCorrectPos(final int coordinate, final int nTiles,
            final int tileDimension) {
        if (MapPoint.getPos(coordinate + tileDimension, nTiles, tileDimension) - coordinate 
                < coordinate + tileDimension 
                - MapPoint.getPos(coordinate + tileDimension, nTiles, tileDimension)) {
            return MapPoint.getPos(coordinate + tileDimension, nTiles, tileDimension);
        } else {
            return MapPoint.getPos(coordinate, nTiles, tileDimension);
        }
    }

    /**
     * Calculates the position in the matrix;
     * 
     * @param coordinate
     *          the coordinate to convert
     * @param tileDimension
     *          the conversion factor
     * @return the coordinate converted
     */
    public static int getInvCoordinate(final int coordinate, final int tileDimension) {
        return coordinate / tileDimension;
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
    public static boolean isEntryPoint(final int row, final int column) {
        return row <= 2 && column <= 2;
    }
    
    /**
     * Checks boundaries.
     * 
     * @param coordinate
     *          the coordinate
     * @param range
     *          the bomb's range
     * @return the maximum possible coordinate
     */
    public static int checkBoundaries(final int coordinate, final int range, final int nTiles) {
        if ((coordinate + range) > nTiles - 1) {
            return nTiles - 1;
        } else if ((coordinate + range) < 0) {
            return 0;
        } else {
            return coordinate + range;
        }
    }

    /**
     * Checks if the cycle must be stopped or not.
     * 
     * @param coordinate
     *          the coordinate
     * @param max
     *          the max coordinate
     * @param dir
     *          the direction
     * @return true if the cycle can continue, false otherwise
     */
    public static boolean stopCycle(final int coordinate, final int max, final Direction dir) {
        if (dir.equals(Direction.UP) || dir.equals(Direction.LEFT)) {
            return coordinate >= max;
        } else {
            return coordinate <= max; 
        }
    }

    /**
     * Return the next value the coordinate has to add.
     * 
     * @param dir
     *          the direction
     * @return the integer to add to the coordinate
     */
    public static int continueCycle(final Direction dir) {
        if (dir.equals(Direction.UP) || dir.equals(Direction.LEFT)) {
            return -1;
        } else {
            return 1;
        }
    }
}
