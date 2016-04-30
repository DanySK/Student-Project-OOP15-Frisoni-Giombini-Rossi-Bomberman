package view.menu;

import javax.swing.JPanel;

import view.menu.scenes.InformationScene;
import view.menu.scenes.MenuScene;
import view.menu.scenes.ScoresScene;
import view.menu.scenes.SettingsScene;
import view.menu.scenes.WelcomeScene;

/**
 * This interface handles the panel changes inside the {@link MenuFrameImpl}.
 * It defines the possible "cards" that can be replaced and shown.
 *
 */
public interface MenuFrame {

    /**
     * The possible panels that can be displayed.
     */
    enum MenuCard {
        HOME(new MenuScene()),
        SCORES(new ScoresScene()),
        SETTINGS(new SettingsScene()),
        CREDITS(new InformationScene()),
        WELCOME(new WelcomeScene());

        private final JPanel panel;

        /**
         * Constructs a new MenuCard.
         * 
         * @param panel
         *      the {@link JPanel} to show when the "card" is selected.
         */
        MenuCard(final JPanel panel) {
            this.panel = panel;
        }

        /**
         * @return the {@link JPanel} associated to the MenuCard.
         */
        public JPanel getPanel() {
            return this.panel;
        }
    }

    /**
     * This method is called before the UI is used.
     * It shows the user interface on the screen.
     */
    void showView();
    
    /**
     * Changes the {@link MenuCard} shown in the main frame with another provided.
     * 
     * @param card
     *          the new card
     */
    void replaceCard(MenuCard card);
    
    /**
     * Closes the menu frame.
     */
    void closeView();
}
