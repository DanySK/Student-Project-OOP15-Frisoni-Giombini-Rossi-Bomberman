package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import model.utilities.CopyFactory;

/**
 * Implementation of {@link Detonator}.
 */
public class DetonatorImpl implements Detonator {

    private static final int INITIAL_RANGE = 1;
    private static final int INITIAL_BOMBS = 1;
    private static final long BOMB_DELAY = 3000L;

    private final Dimension dim;
    private int bombRange;
    private int maxBombs;
    private final Deque<Bomb> bombList;

    /**
     * It creates a detonator.
     * 
     * @param dim
     *          the dimension of a bomb
     */
    public DetonatorImpl(final Dimension dim) {
        this.dim = dim;
        this.bombRange = INITIAL_RANGE;
        this.maxBombs = INITIAL_BOMBS;
        this.bombList = new LinkedList<>();
    }

    /**
     * It adds a bomb to the List.
     * 
     * @param pos
     *          the bomb's position
     */
    private void addBomb(final Point pos) {
        synchronized (this.bombList) {
            this.bombList.addLast(new BombImpl(pos, this.dim, this.bombRange));
        }
    }

    @Override
    public void increaseRange() {
        this.bombRange++;
    }

    @Override
    public void increaseBombs() {
        this.maxBombs++;
    }

    @Override
    public void plantBomb(final Point pos) {
        this.addBomb(pos);
        this.getBombToPlant().setPlanted(true);
    }

    @Override
    public void reactivateBomb() {
        synchronized (this.bombList) {
            this.bombList.removeFirst();
        }
    }

    /**
     * It returns a bomb to plant.
     * 
     * @return a bomb that can be planted.
     */
    private Bomb getBombToPlant() {
        synchronized (this.bombList) {
            return this.bombList.stream().filter(b -> !b.isPositioned()).findFirst().get();
        }
    }

    @Override
    public Bomb getBombToReactivate() {
        synchronized (this.bombList) {
            return this.bombList.stream().filter(b -> b.isPositioned()).findFirst().get();
        }
    }

    @Override
    public long getBombDelay() {
        return BOMB_DELAY;
    }

    @Override
    public boolean hasBombs() {
        synchronized (this.bombList) {
            return this.bombList.size() < this.maxBombs;
        }
    }

    @Override
    public Set<Bomb> getPlantedBombs() {
        synchronized (this.bombList) {
            return this.bombList.stream().filter(b -> b.isPositioned())
                    .map(b -> CopyFactory.getCopy(b))
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public int getActualRange() {
        return this.bombRange;
    }

    @Override
    public int getActualBombs() {
        return this.maxBombs;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("DETONATOR -  ")
                .append("Bomb list size is: ")
                .append(this.getActualBombs())
                .append(".\n")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bombRange;
        result = prime * result + ((dim == null) ? 0 : dim.hashCode());
        result = prime * result + maxBombs;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof DetonatorImpl && this.dim.equals(((DetonatorImpl) obj).dim)
                && this.bombRange == ((DetonatorImpl) obj).bombRange 
                && this.maxBombs == ((DetonatorImpl) obj).maxBombs;
    }
}
