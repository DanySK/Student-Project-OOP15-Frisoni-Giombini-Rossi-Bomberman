package view.menu;

import javax.swing.ImageIcon;

import view.menu.scenes.MenuScene.MenuObserver;

/**
 * This interface models a menu button.
 *
 */
public interface MenuButton {
    
    /**
     * @return the text of the button.
     */
    String getName();
    
    /**
     * @return the {@link ImageIcon} associated to the button. 
     */
    ImageIcon getIcon();
    
    /**
     * The method called when a button is pressed.
     * 
     * @param controller
     *              the {@link MenuObserver} that manages events
     */
    void clickEvent(MenuObserver controller);
}
