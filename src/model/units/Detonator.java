package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class Detonator {

    private static final int INITIAL_BOMBS = 3;
    private static final Point INITIAL_POS = new Point(0, 0);
    private static final long BOMB_DELAY = 6000L;

    private final Dimension dim;
    private List<Bomb> bombList = new ArrayList<>();

    public Detonator(final Dimension dim){
        this.dim = dim;
        this.initializeBombList();
    }

    private void initializeBombList(){
        for(int i = 0; i < INITIAL_BOMBS; i++){
            this.bombList.add(new BombImpl(INITIAL_POS, this.dim));
        }
    }

    public void addBomb(){
        this.bombList.add(new BombImpl(INITIAL_POS, this.dim ));
    }

    private Bomb getBomb(){
        return this.bombList.get(0);
    }

    public void increaseRange(){
        this.bombList.get(new Random().nextInt(this.bombList.size())).increaseRange();
    }

    public Bomb plantBomb(Point p){
        final Bomb b = this.getBomb();
        b.updatePosition(p);
        return b;
    }

    public void detonate(Bomb b, Set<Bomb> plantedBombs) {
        final Timer timer = new Timer();
        plantedBombs.add(b);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        plantedBombs.remove(b);
                    }
                });
            }
        }, BOMB_DELAY, 1);
    }

    public boolean hasBombs(){
        return this.bombList.size() > 0;
    }

    public void removeBomb(Bomb b){
        this.bombList.remove(b);
    }

    public long getBombDelay(){
        return BOMB_DELAY;
    }
}
