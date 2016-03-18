package controller;

import java.util.Set;

import model.TileType;
import model.level.Level;
import model.units.Bomb;
import model.units.Direction;
import model.units.Hero;
import model.utilities.PowerUp;
import view.InputAction;
import view.InputHandler;
import view.game.GameFrame;

/**
 * Implementation of {@link GameController}.
 * The view can call only the functions of this interface.
 */
public class GameControllerImpl implements GameController {
    
    private static final int FPS = 60;
    private final Level model;
    private final GameFrame view;

    /**
     * Constructor for GameControllerImpl.
     * @param model the model object.
     * @param view the view object.
     */
    public GameControllerImpl(final Level model, final GameFrame view) {
        this.model = model;
        this.view = view;
        this.startGame();
    }
    
    /**
     * This method begins the game.
     */
    private void startGame() {
        view.setObserver(this);
        view.initView();
        final InputHandler inputListener = new InputHandler();
        view.setKeyListener(inputListener);
        model.initLevel(view.getTileSize());
        
        final AbstractGameLoop game = new AbstractGameLoop(FPS) {
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
                if (!inputListener.isInputActive(InputAction.MOVE_DOWN)
                        && !inputListener.isInputActive(InputAction.MOVE_LEFT)
                        && !inputListener.isInputActive(InputAction.MOVE_RIGHT)
                        && !inputListener.isInputActive(InputAction.MOVE_UP)) {
                    model.getHero().setMoving(false);
                }
                if (inputListener.isInputActive(InputAction.PLANT_BOMB)) {
                    model.plantBomb();
                }
            }
            
            @Override
            public void updateView() {
                view.update();
            }
        };   
        
        view.setGameLoop(game);
        view.showView();
        game.start();
    }
    
    @Override
    public Hero getHero() {
        return model.getHero();
    }

    @Override
    public Set<PowerUp> getPowerUpInLevel() {
        return model.getPowerupInLevel();
    }

    @Override
    public TileType[][] getMap() {
        return model.getMap();
    }

    @Override
    public boolean isGameOver() {
        return model.isGameOver();
    }
    
    @Override
    public int getLevelSize() {
        return model.getSize();
    }

    @Override
    public Set<Bomb> getPlantedBombs() {
        return model.getPlantedBombs();
    }

    @Override
    public long getBombDelay() {
        return model.getHero().getBombDelay();
    }
}
