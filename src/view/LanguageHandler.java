package view;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import view.utilities.ESource;

/**
 * This class uses a Singleton Pattern to make a language handler
 * for the application.
 *
 */
public final class LanguageHandler extends ESource<Locale> {

    private static final String PATH_PROPERTIES = "languages.LabelsBundle";

    /**
     * This enumeration represents the languages supported by the application.
     *
     */
    public enum Language {
        /**
         * Italian language.
         */
        ITALIAN(Locale.ITALY),
        /**
         * English language.
         */
        ENGLISH(Locale.UK),
        /**
         * Polish language.
         */
        POLISH(new Locale("pl"));
        
        private final Locale locale;
        
        Language(final Locale locale) {
            this.locale = locale;
        }
        
        /**
         * @return the locale associated to the supported language
         */
        public Locale getLocale() {
            return this.locale;
        }
    }
    
    private static volatile LanguageHandler singleton;
    private ResourceBundle res;

    /**
     * Creates a new LanguageHandler with default locale.
     */
    private LanguageHandler() {
        res = ResourceBundle.getBundle(PATH_PROPERTIES);
    };

    /**
     * This method returns the LanguageHandler.
     * If the LanguageHandler is null it creates a new one on the first call.
     * This way the resources are loaded only if necessary.
     * 
     * @return the LanguageHandler.
     */
    public static LanguageHandler getHandler() {
        if (singleton == null) {
            synchronized (LanguageHandler.class) {
                if (singleton == null) {
                    singleton = new LanguageHandler();
                }
            }
        }
        return singleton;
    }

    /**
     * This method returns the ResourceBundle for the current LanguageHandler.
     * 
     * @return the ResourceBundle.
     */
    public ResourceBundle getLocaleResource() {
        return singleton.res;
    }
    
    /**
     * @return the current language.
     */
    public Optional<Language> getCurrentLanguage() {
        return Arrays.stream(Language.values()).filter(l -> l.getLocale().equals(singleton.res.getLocale())).findAny();
    }

    /**
     * @return an array with the {@link Language} supported by the application.
     */
    public Language[] getSupportedLanguages() {
        return Arrays.copyOf(Language.values(), Language.values().length);
    }

    /**
     * Sets the ResourceBundle with the specified language and country.
     * It performs the operation only if the required locale is different
     * from the current one.
     * If a ResourceBundle class for the specified Locale does not exist,
     * getBundle tries to find the closest match.
     * If it fails to find a match, it uses the base class.
     * 
     * @param language
     *          the supported language to set
     */
    public void setLocale(final Language language) {
        if (!singleton.res.getLocale().equals(language.getLocale())) {
            singleton.res = ResourceBundle.getBundle(PATH_PROPERTIES, language.getLocale());
            this.notifyEObservers(language.getLocale());
        }
    }
}