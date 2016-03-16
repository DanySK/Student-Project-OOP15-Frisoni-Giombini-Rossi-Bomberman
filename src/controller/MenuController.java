package controller;

import model.level.Level;
import model.level.LevelImpl;
import view.game.GameFrame;
import view.game.GameFrameImpl;
import view.menu.MenuFrame.MenuCard;
import view.menu.views.MenuView;
import view.menu.views.SettingsView;
import view.menu.views.MenuView.MenuObserver;
import view.menu.MenuFrameImpl;

/**
 * Implementation of {@link MenuObserver}.
 * This class will change the current card with the card request,
 * and it allows you to start a new game.
 *
 */
public class MenuController implements MenuObserver {
    
    private boolean darkMode;

    /**
     * Construct a controller for the menu of game.
     *   
     */
    public MenuController() {
        final MenuView menuView = (MenuView) MenuCard.HOME.getPanel();
        menuView.setObserver(this);
        MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
        MenuFrameImpl.getMenuFrame().initView();
        this.darkMode = false;
    }

    @Override
    public void play() {
        final Level model = new LevelImpl();
        final GameFrame view = new GameFrameImpl(this.darkMode);
        new GameControllerImpl(model, view);
    }

    @Override
    public void scores() {
        MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.SCORES);
    }

    @Override
    public void settings() {
        final SettingsView settingsView = (SettingsView) MenuCard.SETTINGS.getPanel();
        settingsView.setObserver(new SettingsView.SettingsObserver() {
            @Override
            public void setDarkMode(final boolean darkMode) {
                MenuController.this.darkMode = darkMode;
            }
        });
        MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.SETTINGS);
    }

    @Override
    public void credits() {
        MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.CREDITS);
    }
}
