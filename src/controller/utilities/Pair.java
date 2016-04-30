package controller.utilities;

import java.io.Serializable;

/**
 * A standard generic Pair<X, Y>, with getters, hashCode, equals, and toString well implemented. 
 * @param <X>
 *      the type of the first element
 * @param <Y>
 *      the type of the second element 
 */
public class Pair<X, Y> implements Serializable {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 568672775975227372L;
    
    private final X x;
    private final Y y;

    /**
     * Constructor for Pair.
     * @param x
     *          the first element of pair
     * @param y
     *          the second element of pair
     */
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * This method return the first element.
     * @return the first element
     */
    public X getX() {
        return x;
    }

    /**
     * This method return the second element.
     * @return the second element
     */
    public Y getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Pair && this.x.equals(((Pair<X, Y>) obj).x) 
                && this.y.equals(((Pair<X, Y>) obj).y);
    }

    @Override
    public String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }
}
