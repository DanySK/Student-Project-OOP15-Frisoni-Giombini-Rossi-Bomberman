package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import controller.utilities.Pair;
import model.Tile;
import model.level.Level;
import model.units.Bomb;
import model.units.Direction;
import model.units.Hero;
import model.units.enemy.Enemy;
import view.InputAction;
import view.InputHandler;
import view.game.DrawableFrameImpl.GameMessage;
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
    private static final int MULTIPLY = 2;
    private static final long WAITING_TIME = 3000;
    private final Level level;
    private final GameFrame view;
    private volatile boolean isPlanted;
    private volatile boolean inPaused;
    private int time;
    private final boolean darkMode;
    
    /**
     * Constructor for GameControllerImpl.
     * @param model the model object.
     * @param view the view object.
     */
    public GameControllerImpl(final Level model, final GameFrame view, final boolean darkMode) {
        this.level = model;
        this.view = view;
        this.level.setFirstStage();
        this.startGame();
        this.isPlanted = false;
        this.inPaused = false;
        this.time = 0;
        this.darkMode = darkMode;
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
                level.moveEnemies();
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
                /*if (inputListener.isInputActive(InputAction.PLANT_BOMB) && !isPlanted) {
                    if (level.canPlantBomb() && level.getHero().getDetonator().hasBombs()) {
                        level.plantBomb();
                        this.doOperationAfterDelay(level.getHero().getDetonator().getBombDelay(), new Runnable() {
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
                }*/
                if (level.getHero().hasKey()) {
                    level.setOpenDoor();
                }
                if (level.getHero().hasKey() && level.getHero().checkOpenDoorCollision(level.getDoor())) {
                    pauseLoop();
                    view.showMessage(GameMessage.STAGE);
                    level.setNextStage();
                    //level.setNumberTiles();
                    try {
                        Thread.sleep(WAITING_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    view.updateStage();
                    level.initLevel(view.getTileSize());
                    view.removeMessage();
                    unPauseLoop();
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
                        this.pauseLoop();
                        view.showMessage(GameMessage.PAUSE);
                    } else {
                        this.unPauseLoop();
                        view.removeMessage();
                    }
                    inPaused = true;
                }
                if (!inputListener.isInputActive(InputAction.PAUSE)) {
                    inPaused = false;
                }
                if (level.isGameOver()) {
                    super.stopLoop();
                    final int score = darkMode ? level.getHero().getScore() * MULTIPLY : level.getHero().getScore();
                    view.showGameOverPanel(score, time, ScoreHandler.getHandler().isBestScore(level.getHero().getScore()),
                            new GameOverPanel.GameOverObserver() {
                        @Override
                        public void replay() {
                            view.closeView();
                            level.setFirstStage();
                            time = 0;
                            //level.setNumberTiles();
                            startGame();
                        }

                        @Override
                        public void exit() {
                            MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME);
                            MenuFrameImpl.getMenuFrame().showView();
                            view.closeView();
                        }
                    });
                    ScoreHandler.getHandler().saveScore(score, time);
                }
            }

            @Override
            public void updateEnemies() {
                level.setDirectionEnemies();
            }

            @Override
            public void updateTime() {
                view.updateTime(time++);
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

    /*@Override
    public List<Bomb> getPlantedBombs() {
        return level.getHero().getDetonator().getPlantedBombs();
    }*/

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
        return level.getHero().getDetonator().getBombDelay();
    }
    @Override
    public Set<Enemy> getEnemies() {
        return level.getEnemies();
    }
    
    @Override
    public int getTime() {
        return this.time;
    }

    @Override
    public Pair<Integer, Integer> getRecord() {
        return null;
    }

    @Override
    public LinkedList<Pair<Integer, Integer>> getLastScores() {
        return null;
    }

    @Override
    public boolean isScoreEmpty() {
        return false;
    }
}
