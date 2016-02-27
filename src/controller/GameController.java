package controller;

import model.level.Level;
import model.units.Direction;
import view.HeroKeyBindings;
import view.game.GameFrame;

/**
 * An implementation of {@link GameController}.
 *
 */
public class GameController {

    private final Level level;
    private final GameFrame view;

    /**
     * Construct a controller for the game.
     * 
     * @param map
     *            the map's model
     * @param view
     *            the view for the game rendering
     */
    public GameController(final Level level, final GameFrame view) {
        this.level = level;
        this.view = view;
        
        final HeroKeyBindings listener = new HeroKeyBindings();
        listener.getSource().addEObserver((s, action) -> {
            switch (action) {
            case MOVE_DOWN:
                GameController.this.level.setHeroMoving(true);
                GameController.this.level.moveHero(Direction.DOWN);
                GameController.this.view.repaintGamePanel();
                break;
            case MOVE_RIGHT:
                GameController.this.level.setHeroMoving(true);
                GameController.this.level.moveHero(Direction.RIGHT);
                GameController.this.view.repaintGamePanel();
                break;
            case MOVE_UP:
                GameController.this.level.setHeroMoving(true);
                GameController.this.level.moveHero(Direction.UP);
                GameController.this.view.repaintGamePanel();
                break;
            case MOVE_LEFT:
                GameController.this.level.setHeroMoving(true);
                GameController.this.level.moveHero(Direction.LEFT);
                GameController.this.view.repaintGamePanel();
                break;
            case STILL:
                GameController.this.level.setHeroMoving(false);
                break;
            default:
                break;
            }
        });
        
        // Adds a new keyListener to the view to control the hero
        this.view.setKeyListener(listener);
        this.level.initLevel(this.view.getTileSize());
        this.view.initView();
    }
}