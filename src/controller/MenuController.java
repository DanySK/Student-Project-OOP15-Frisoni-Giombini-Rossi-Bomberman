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

public class MenuController implements MenuObserver {
    
    private final MenuFrame menuFrame;
    
    public MenuController(final MenuFrame menuFrame) {
        final MenuView menuView = (MenuView) MenuCard.HOME.getPanel();
        menuView.setObserver(this);
        this.menuFrame = menuFrame;
        this.menuFrame.initView();
    }
    
    @Override
    public void play() {
        final Level model = new LevelImpl();
        final GameFrame view = new GameFrameImpl(model, false);
        new GameController(model, view);
    }

    @Override
    public void scores() {
    }

    @Override
    public void settings() {
        final SettingsView settingsView = (SettingsView) MenuCard.SETTINGS.getPanel();
        settingsView.setObserver(new MenuPanelObserver() {
            @Override
            public void back() {
                MenuController.this.menuFrame.replaceCard(MenuCard.HOME);
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
