package controller;

import model.level.Level;
import model.level.LevelImpl;
import view.game.GameFrame;
import view.game.GameFrameImpl;
import view.menu.MenuFrame.MenuCard;
import view.menu.scenes.MenuScene;
import view.menu.scenes.SettingsScene;
import view.menu.scenes.WelcomeScene;
import view.menu.scenes.MenuScene.MenuObserver;
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
            final MenuScene menuView = (MenuScene) MenuCard.HOME.getPanel();
            menuView.setObserver(this);
            MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
            MenuFrameImpl.getMenuFrame().showView();
        } else {
            final WelcomeScene welcome = (WelcomeScene) MenuCard.WELCOME.getPanel();
            welcome.setObserver(new WelcomeScene.WelcomeObserver() {
                @Override
                public void setName(final String name) {
                    ScoreHandler.getHandler().createFile();
                    ScoreHandler.getHandler().saveName(name);
                    final MenuScene menuView = (MenuScene) MenuCard.HOME.getPanel();
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
        final SettingsScene settingsView = (SettingsScene) MenuCard.SETTINGS.getPanel();
        settingsView.setObserver(new SettingsScene.SettingsObserver() {
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
