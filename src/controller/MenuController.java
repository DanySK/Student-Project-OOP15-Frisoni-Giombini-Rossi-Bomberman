package controller;

import model.level.Level;
import model.level.LevelImpl;
import model.units.Direction;
import view.InputAction;
import view.InputHandler;
import view.game.GameFrame;
import view.game.GameFrameImpl;
import view.menu.MenuFrame.MenuCard;
import view.menu.MenuFrameImpl;
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

    private final static int FPS = 40;
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
        final GameFrame view = new GameFrameImpl(model, this.darkMode);
        final InputHandler inputListener = new InputHandler();
        // Adds a new keyListener to the view to control the hero
        view.setKeyListener(inputListener);
        model.initLevel(view.getTileSize());
        view.initView();
        AbstractGameLoop game = new AbstractGameLoop(FPS){

            @Override
            public void updateModel() {
                if (inputListener.isInputActive(InputAction.MOVE_DOWN)) {
                    model.moveHero(Direction.DOWN);
                }
                if (inputListener.isInputActive(InputAction.MOVE_LEFT)) {
                    model.moveHero(Direction.LEFT);
                }
                if (inputListener.isInputActive(InputAction.MOVE_RIGHT)) {
                    model.moveHero(Direction.RIGHT);
                }
                if (inputListener.isInputActive(InputAction.MOVE_UP)) {
                    model.moveHero(Direction.UP);
                }
                if (!inputListener.isInputActive(InputAction.MOVE_DOWN) &&
                        !inputListener.isInputActive(InputAction.MOVE_LEFT) &&
                        !inputListener.isInputActive(InputAction.MOVE_RIGHT) &&
                        !inputListener.isInputActive(InputAction.MOVE_UP)) {
                    model.getHero().setMoving(false);
                }
            }
            @Override
            public void updateView() {
                view.repaintGamePanel();
            }
        };
        game.start();
    }

    @Override
    public void scores() {
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
