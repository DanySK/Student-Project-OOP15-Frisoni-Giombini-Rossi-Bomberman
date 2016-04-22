package view;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import view.utilities.ESource;

/**
 * This class uses a Singleton Pattern to make a language handler
 * for the application.
 *
 */
public final class LanguageHandler extends ESource<Locale> {

    private static final String PATH_PROPERTIES = "languages.LabelsBundle";

    // The languages supported by the application.
    private final Locale[] languages = new Locale[] {Locale.ITALY, Locale.UK, new Locale("pl")};

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
     * @return a list with the {@link Locale} associated to the languages
     * supported by the application.
     */
    public Locale[] getSupportedLanguages() {
        return Arrays.copyOf(this.languages, this.languages.length);
    }

    /**
     * Sets the ResourceBundle with the specified language and country.
     * It performs the operation only if the required locale is different
     * from the current one.
     * If a ResourceBundle class for the specified Locale does not exist,
     * getBundle tries to find the closest match.
     * If it fails to find a match, it uses the base class.
     * 
     * @param locale
     *          the language locale to set
     */
    public void setLocale(final Locale locale) {
        if (!singleton.res.getLocale().equals(locale)) {
            singleton.res = ResourceBundle.getBundle(PATH_PROPERTIES, locale);
            this.notifyEObservers(locale);
        }
    }
}