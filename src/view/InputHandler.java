package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the keyboard input for the actions that can be performed
 * in the game.
 * 
 */
public final class InputHandler extends KeyAdapter {

    private final EnumMap<InputAction, Boolean> inputs = new EnumMap<>(InputAction.class);

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
        MAP.put(KeyEvent.VK_P, InputAction.PAUSE);              // Pause the game
    }

    /**
     * Constructs a new InputHandler.
     */
    public InputHandler() {
        for (final InputAction i : InputAction.values()) {
            this.inputs.put(i, false);
        }
    }

    @Override
    public void keyPressed(final KeyEvent evt) {
        if (MAP.containsKey(evt.getKeyCode())) {
            this.inputs.put(MAP.get(evt.getKeyCode()), true);
        }
    }

    @Override
    public void keyReleased(final KeyEvent evt) {
        if (MAP.containsKey(evt.getKeyCode())) {
            this.inputs.put(MAP.get(evt.getKeyCode()), false);
        }
    }

    /**
     * Checks if the key associated with the specified @{link InputAction} is pressed or not.
     * 
     * @param input
     *          the input action to check
     * @return true if the key is pressed, false otherwise
     */
    public Boolean isInputActive(final InputAction input) {
        return this.inputs.get(input);
    }
    
    /**
     * @return a map containing all the associated keys to input actions.
     */
    public static Map<Integer, InputAction> getCommandsMap() {
        return new HashMap<Integer, InputAction>(InputHandler.MAP);
    }
}
