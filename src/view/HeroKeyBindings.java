package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import view.utilities.ESource;

/**
 * This class handles the keyboard input for the hero's actions.
 * It uses the observer design pattern to notify the occurred event to the controller.
 * 
 */
public class HeroKeyBindings extends KeyAdapter {

    private final ESource<HeroAction> source;
    
    private static final Map<Integer, HeroAction> MAP;
    static {
        MAP = new HashMap<>();
        MAP.put(KeyEvent.VK_DOWN, HeroAction.MOVE_DOWN);        // Move Down                
        MAP.put(KeyEvent.VK_S, HeroAction.MOVE_DOWN);
        MAP.put(KeyEvent.VK_RIGHT, HeroAction.MOVE_RIGHT);      // Move Right              
        MAP.put(KeyEvent.VK_D, HeroAction.MOVE_RIGHT);
        MAP.put(KeyEvent.VK_UP, HeroAction.MOVE_UP);            // Move Up
        MAP.put(KeyEvent.VK_W, HeroAction.MOVE_UP);
        MAP.put(KeyEvent.VK_LEFT, HeroAction.MOVE_LEFT);        // Move Left        
        MAP.put(KeyEvent.VK_A, HeroAction.MOVE_LEFT);
    }
    
    /**
     * Constructs a new binding for the hero's actions.
     */
    public HeroKeyBindings() {
        super();
        this.source = new ESource<>();
    }
    
    /**
     * @return the event source.
     */
    public ESource<HeroAction> getSource() {
        return this.source;
    }
    
    @Override
    public void keyPressed(final KeyEvent evt) {
        if (MAP.containsKey(evt.getKeyCode())) {
            this.source.notifyEObservers(MAP.get(evt.getKeyCode()));
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent evt) {
        this.source.notifyEObservers(HeroAction.STILL);
    }
}
