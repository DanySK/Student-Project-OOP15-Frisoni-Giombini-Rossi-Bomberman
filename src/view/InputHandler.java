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
public class InputHandler extends KeyAdapter {

    private final ESource<InputAction> source;
    
    private static final Map<Integer, InputAction> MAP;
    static {
        MAP = new HashMap<>();
        MAP.put(KeyEvent.VK_DOWN, InputAction.MOVE_DOWN);       // Move Down                
        MAP.put(KeyEvent.VK_S, InputAction.MOVE_DOWN);
        MAP.put(KeyEvent.VK_RIGHT, InputAction.MOVE_RIGHT);     // Move Right              
        MAP.put(KeyEvent.VK_D, InputAction.MOVE_RIGHT);
        MAP.put(KeyEvent.VK_UP, InputAction.MOVE_UP);           // Move Up
        MAP.put(KeyEvent.VK_W, InputAction.MOVE_UP);
        MAP.put(KeyEvent.VK_LEFT, InputAction.MOVE_LEFT);       // Move Left        
        MAP.put(KeyEvent.VK_A, InputAction.MOVE_LEFT);
        MAP.put(KeyEvent.VK_SPACE, InputAction.PLANT_BOMB);     // Plant a bomb
    }
    
    /**
     * Constructs a new binding for the hero's actions.
     */
    public InputHandler() {
        super();
        this.source = new ESource<>();
    }
    
    /**
     * @return the event source.
     */
    public ESource<InputAction> getSource() {
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
        // this.source.notifyEObservers(InputAction.STILL);
    }
}
