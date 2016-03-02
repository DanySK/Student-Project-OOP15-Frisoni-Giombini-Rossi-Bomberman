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

}
