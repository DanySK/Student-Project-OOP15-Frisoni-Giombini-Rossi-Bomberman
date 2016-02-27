package view.menu;

import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;
import view.menu.MenuView.MenuObserver;

/**
 * An implementation of {@link MenuStrategy}.
 * 
 */
public class MenuStrategyImpl implements MenuStrategy {

    private enum MainMenuButton implements MenuButton {
        GIOCA("play", ImageLoader.getLoader().createImageIcon(GameImage.PLAY)) {
            @Override
            public void clickEvent(final MenuObserver observer) {
                observer.play();
            }
        },
        CLASSIFICA("ranking", ImageLoader.getLoader().createImageIcon(GameImage.RANKING)) {
            @Override
            public void clickEvent(final MenuObserver observer) {
                //observer.ranking();
            }
        },
        IMPOSTAZIONI("settings", ImageLoader.getLoader().createImageIcon(GameImage.SETTINGS)) {
            @Override
            public void clickEvent(final MenuObserver observer) {
                observer.settings();
            }
        },
        INFO("credits", ImageLoader.getLoader().createImageIcon(GameImage.CREDITS)) {
            @Override
            public void clickEvent(final MenuObserver observer) {
                //observer.credits();
            }
        };

        private final String name;
        private final ImageIcon icon;

        MainMenuButton(final String name, final ImageIcon icon) {
            this.name = name;
            this.icon = icon;
        }

        @Override
        public String getName() {
            return LanguageHandler.getHandler().getLocaleResource().getString(this.name);
        }

        @Override
        public ImageIcon getIcon() {
            return this.icon;
        }
    }

    @Override
    public List<MenuButton> getButtons() {
        return Arrays.asList(MainMenuButton.values());
    }
}
