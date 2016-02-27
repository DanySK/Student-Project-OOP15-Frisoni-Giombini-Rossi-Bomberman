package view.menu;

import java.util.List;

/**
 * This interface models the strategy for the main menu of the game.
 * 
 */
public interface MenuStrategy {
    
    /**
     * @return a list with information about menu buttons.
     */
    List<MenuButton> getButtons();
}
