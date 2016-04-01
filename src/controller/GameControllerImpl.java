package controller;

import java.util.Set;

import model.Tile;
import model.level.Level;
import model.units.Bomb;
import model.units.Direction;
import model.units.Hero;
import view.InputAction;
import view.InputHandler;
import view.game.GameFrame;
import view.game.GameOverPanel;
import view.menu.MenuFrameImpl;
import view.menu.MenuFrame.MenuCard;

/**
 * Implementation of {@link GameController}.
 * The view can call only the functions of this interface.
 */
public class GameControllerImpl implements GameController {

    private static final int FPS = 60;
    private final Level level;
    private final GameFrame view;
    private volatile boolean isPlanted;
    private volatile boolean inPaused;

    /**
     * Constructor for GameControllerImpl.
     * @param model the model object.
     * @param view the view object.
     */
    public GameControllerImpl(final Level model, final GameFrame view) {
        this.level = model;
        this.view = view;
        this.startGame();
        this.isPlanted = false;
        this.inPaused = false;
    }

    /**
     * This method begins the game.
     */
    private void startGame() {
        view.setObserver(this);
        view.initView();
        final InputHandler inputListener = new InputHandler();
        view.setKeyListener(inputListener);
        level.initLevel(view.getTileSize());

        final AbstractGameLoop game = new AbstractGameLoop(FPS) {
            @Override
            public void updateModel() {
                if (inputListener.isInputActive(InputAction.MOVE_DOWN)) {
                    level.moveHero(Direction.DOWN);
                }
                if (inputListener.isInputActive(InputAction.MOVE_LEFT)) {
                    level.moveHero(Direction.LEFT);
                }
                if (inputListener.isInputActive(InputAction.MOVE_RIGHT)) {
                    level.moveHero(Direction.RIGHT);
                }
                if (inputListener.isInputActive(InputAction.MOVE_UP)) {
                    level.moveHero(Direction.UP);
                }
                if (!inputListener.isInputActive(InputAction.MOVE_DOWN)
                        && !inputListener.isInputActive(InputAction.MOVE_LEFT)
                        && !inputListener.isInputActive(InputAction.MOVE_RIGHT)
                        && !inputListener.isInputActive(InputAction.MOVE_UP)) {
                    level.getHero().setMoving(false);
                }
                if (inputListener.isInputActive(InputAction.PLANT_BOMB) && !isPlanted) {
                    if (level.getHero().hasBomb(level.getSize()) && level.canPlantBomb()) {
                        level.plantBomb();
                        this.doOperationAfterDelay(level.getHero().getBombDelay(), new Runnable() {
                            @Override
                            public void run() {
                                view.renderExplosions(level.detonateBomb());
                                doOperationAfterDelay(view.getExplosionDuration(), new Runnable() {
                                    @Override
                                    public void run() {
                                        view.removeExplosion();
                                    }
                                });
                            }
                        });
                    }
                    isPlanted = true;
                }
                if (!inputListener.isInputActive(InputAction.PLANT_BOMB)) {
                    isPlanted = false;
                }
                if (level.getHero().hasKey()) {
                    level.setOpenDoor();
                }
            }

            @Override
            public void updateView() {
                view.update();
            }

            @Override
            public void updateGameState() {
                if (inputListener.isInputActive(InputAction.PAUSE) && !inPaused) {
                    if (!this.isPaused()) {
                        this.pause();
                        view.showPauseMessage();
                    } else {
                        this.unPause();
                        view.removePauseMessage();
                    }
                    inPaused = true;
                }
                if (!inputListener.isInputActive(InputAction.PAUSE)) {
                    inPaused = false;
                }
                if (level.isGameOver()) {
                    super.stopLoop();
                    view.showGameOverPanel(new GameOverPanel.GameOverObserver() {
                        @Override
                        public void replay() {
                            view.closeView();
                            startGame();
                        }
                        @Override
                        public void exit() {
                            MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
                            MenuFrameImpl.getMenuFrame().initView();
                            view.closeView();
                        }
                    });
                }
            }
        };   

        view.setGameLoop(game);
        view.showView();
        game.start();
    }

    @Override
    public Hero getHero() {
        return level.getHero();
    }

    @Override
    public boolean isGameOver() {
        return level.isGameOver();
    }

    @Override
    public int getLevelSize() {
        return level.getSize();
    }

    @Override
    public Set<Bomb> getPlantedBombs() {
        return level.getPlantedBombs();
    }

    @Override
    public Set<Tile> getPowerUp() {
        return level.getPowerUp();
    }

    @Override
    public Set<Tile> getTiles() {
        return level.getTiles();
    }

    @Override
    public int getFPS() {
        return FPS;
    }
    
    @Override
    public long getBombDelay() {
        return level.getHero().getBombDelay();
    }
}
