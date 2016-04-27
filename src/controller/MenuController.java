package controller;

import model.level.Level;
import model.level.LevelImpl;
import view.game.GameFrame;
import view.game.GameFrameImpl;
import view.menu.MenuFrame.MenuCard;
import view.menu.scenes.MenuView;
import view.menu.scenes.SettingsView;
import view.menu.scenes.WelcomeView;
import view.menu.scenes.MenuView.MenuObserver;
import view.menu.MenuFrameImpl;

/**
 * Implementation of {@link MenuObserver}.
 * This class will change the current card with the card request,
 * and it allows you to start a new game.
 */
public class MenuController implements MenuObserver {

    private boolean darkMode;

    /**
     * Construct a controller for the menu of game.
     */
    public MenuController() {
        if (ScoreHandler.getHandler().isFilePresent()) {
            final MenuView menuView = (MenuView) MenuCard.HOME.getPanel();
            menuView.setObserver(this);
            MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
            MenuFrameImpl.getMenuFrame().showView();
        } else {
            final WelcomeView welcome = (WelcomeView) MenuCard.WELCOME.getPanel();
            welcome.setObserver(new WelcomeView.WelcomeObserver() {
                @Override
                public void setName(final String name) {
                    ScoreHandler.getHandler().createFile();
                    ScoreHandler.getHandler().saveName(name);
                    final MenuView menuView = (MenuView) MenuCard.HOME.getPanel();
                    menuView.setObserver(MenuController.this);
                    MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
                }
            });
            MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.WELCOME);
            MenuFrameImpl.getMenuFrame().showView();
        }
        this.darkMode = false;
    }

    @Override
    public void play() {
        final Level model = new LevelImpl();
        final GameFrame view = new GameFrameImpl(this.darkMode);
        new GameControllerImpl(model, view, this.darkMode);
        MenuFrameImpl.getMenuFrame().closeView();
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
    public void info() {
        MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.CREDITS);
    }
}
