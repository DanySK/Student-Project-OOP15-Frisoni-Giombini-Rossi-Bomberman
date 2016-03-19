package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Detonator {

    private static final Point INITIAL_POS = new Point(0, 0);
    private static final long BOMB_DELAY = 6000L;

    private final Dimension dim;
    private LinkedList<Bomb> bombList = new LinkedList<>();

    public Detonator(final Dimension dim){
        this.dim = dim;
        this.addBomb();
    }

    public void addBomb(){
        this.bombList.addLast(new BombImpl(INITIAL_POS, this.dim ));
    }

    private Bomb getBombToPlant(){
        return this.bombList.stream().filter(b -> !b.isPlanted()).findFirst().get();
    }
    
    private Bomb getBombToReactivate(){
        return this.bombList.stream().filter(b -> b.isPlanted()).findFirst().get();
    }

    public void increaseRange(){
        this.bombList.getFirst().increaseRange();
    }

    public Bomb plantBomb(Point p){
        final Bomb b = this.getBombToPlant();
        b.updatePosition(p);
        b.setPlanted(true);
        return b;
    }
    
    public void reactivateBomb(){
        this.getBombToReactivate().setPlanted(false);
    }

    public long getBombDelay(){
        return BOMB_DELAY;
    }
    
    public boolean hasBombs(){
        return this.bombList.stream().anyMatch(b -> !b.isPlanted());
    }
    
    public LinkedList<Bomb> getPlantedBombs(){
        return this.bombList.stream().filter(b -> b.isPlanted()).collect(Collectors.toCollection(LinkedList::new));
    }
}
