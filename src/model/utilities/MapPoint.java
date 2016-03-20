package model.utilities;

import java.awt.Point;

/**
 * This class is used to calculate the position
 * of an element in the map.
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
    public static Point getPos(final Point pos, final int tileDimension){
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
    public static int getCoordinate(final int coordinate, final int tileDimension){
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
    public static int getPos(final int coordinate, final int nTiles, final int tileDimension){
        int index = 0;
        boolean stop = false;
        for(int i = tileDimension - 1; i < (tileDimension * nTiles) - 1 && !stop; i += tileDimension){
            if(coordinate <= i){
                stop = true;
            }
            else{
                index++;
            }
        }
        return index * tileDimension;
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
    public static int getInvCoordinate(final int coordinate, final int tileDimension){
        return coordinate / tileDimension;
    }

}
