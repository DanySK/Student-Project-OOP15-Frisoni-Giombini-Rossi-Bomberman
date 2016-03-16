package controller;

import java.io.File;

import model.level.Level;
import model.level.LevelImpl;
import view.game.GameFrame;
import view.game.GameFrameImpl;
import view.menu.MenuFrame.MenuCard;
import view.menu.views.MenuView;
import view.menu.views.SettingsView;
import view.menu.views.WelcomeView;
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
        final String fileName = System.getProperty("user.home") + System.getProperty("file.separator") + "Scores.properties";
        final File file = new File(fileName);
        if (file.exists()) {
            final MenuView menuView = (MenuView) MenuCard.HOME.getPanel();
            menuView.setObserver(this);
            MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
            MenuFrameImpl.getMenuFrame().initView();
        } else {
            final ScoresManagement score = new ScoresManagementImpl();
            score.createFile(fileName);
            final WelcomeView welcome = (WelcomeView) MenuCard.WELCOME.getPanel();
            welcome.setObserver(new WelcomeView.WelcomeObserver() {
                @Override
                public void setName(final String name) {
                    score.saveName(name);
                    final MenuView menuView = (MenuView) MenuCard.HOME.getPanel();
                    menuView.setObserver(MenuController.this);
                    MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
                }
            });
            MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.WELCOME);
            MenuFrameImpl.getMenuFrame().initView();
        }
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
