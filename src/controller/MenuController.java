package controller;

import model.level.Level;
import model.level.LevelImpl;
import view.game.GameFrame;
import view.game.GameFrameImpl;
import view.menu.AbstractMenuPanel.MenuPanelObserver;
import view.menu.CreditsView;
import view.menu.MenuFrame;
import view.menu.MenuFrame.MenuCard;
import view.menu.MenuView;
import view.menu.MenuView.MenuObserver;
import view.menu.SettingsView;

/**
 * Implementation of {@link MenuObserver}.
 * This class will change the current card with the card request,
 * and it allows you to start a new game.
 *
 */
public class MenuController implements MenuObserver {
    
    private final MenuFrame menuFrame;
    private boolean darkMode;
    
    /**
     * Construct a controller for the menu of game.
     * 
     * @param menuFrame
     *                  the initial frame         
     */
    public MenuController(final MenuFrame menuFrame) {
        final MenuView menuView = (MenuView) MenuCard.HOME.getPanel();
        menuView.setObserver(this);
        this.menuFrame = menuFrame;
        this.menuFrame.initView();
        this.darkMode = false;
    }
    
    @Override
    public void play() {
        final Level model = new LevelImpl();
        final GameFrame view = new GameFrameImpl(model, this.darkMode);
        new GameController(model, view);
    }

    @Override
    public void scores() {
    }

    @Override
    public void settings() {
        final SettingsView settingsView = (SettingsView) MenuCard.SETTINGS.getPanel();
        settingsView.setObserver(new SettingsView.SettingsObserver() {
            @Override
            public void back() {
                MenuController.this.menuFrame.replaceCard(MenuCard.HOME);
            }

            @Override
            public void setDarkMode(boolean darkMode) {
                MenuController.this.darkMode = darkMode;
            }
        });
        this.menuFrame.replaceCard(MenuCard.SETTINGS);
    }

    @Override
    public void credits() {
        final CreditsView creditsView = (CreditsView) MenuCard.CREDITS.getPanel();
        creditsView.setObserver(new MenuPanelObserver() {
            @Override
            public void back() {
                MenuController.this.menuFrame.replaceCard(MenuCard.HOME);
            }
        });
        this.menuFrame.replaceCard(MenuCard.CREDITS);
    }
}
