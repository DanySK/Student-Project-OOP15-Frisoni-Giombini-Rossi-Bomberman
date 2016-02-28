package view.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import view.GUIFactory;
import view.LanguageHandler;

/**
 * This class handles the credits scene of the menu.
 *
 */
public class CreditsView extends AbstractMenuPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 1713293706664779618L;

    /*
     * This map contains the name of the contributors and the list of jobs they perform.
     */
    private static final Map<String, List<String>> CONTRIBUTORS;
    static {
        CONTRIBUTORS = new HashMap<>();
        CONTRIBUTORS.put("Sofia Rossi", Arrays.asList("Model"));
        CONTRIBUTORS.put("Giacomo Frisoni", Arrays.asList("View"));
        CONTRIBUTORS.put("Giulia Giombini", Arrays.asList("Controller", "Tests", "Enemies"));
    }

    @Override
    public String getTitle() {
        return LanguageHandler.getHandler().getLocaleResource().getString("credits");
    }

    @Override
    public JPanel getPanel() {
        final GUIFactory factory = new GUIFactory.Standard();
        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridwidth = GridBagConstraints.REMAINDER;
        
        final List<FadingLabel> labels = new ArrayList<>();
        CONTRIBUTORS.entrySet()
                    .stream()
                    .sorted((a1, a2) -> a1.getKey().compareTo(a2.getKey()))
                    .map(e -> {
                        final JPanel contributor = new JPanel(new FlowLayout());
                        final FadingLabel nameLabel = 
                                factory.createFadingLabelOfColor(e.getKey() + ":", Color.LIGHT_GRAY);
                        contributor.add(nameLabel);
                        labels.add(nameLabel);
                        for (final String job : e.getValue()) {
                            final FadingLabel jobLabel = factory.createFadingLabelOfColor(job, Color.WHITE);
                            contributor.add(jobLabel);
                            labels.add(jobLabel);
                        }
                        contributor.setOpaque(false);
                        return contributor;
                    })
                    .forEach(p -> {
                        panel.add(p, cnst);
                    });

        /*
         * Starts the fading animations of the labels when the panel is shown
         * and stops them when it isn't visible.
         */
        panel.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(final AncestorEvent event) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (final FadingLabel label : labels) {
                            label.fadeIn();
                            label.waitFor();
                        }
                    }
                }).start();
            }
            @Override
            public void ancestorRemoved(final AncestorEvent event) {
                for (final FadingLabel label : labels) {
                    label.resetFading();
                }
            }
            @Override
            public void ancestorMoved(final AncestorEvent event) {
                // Not used
            }
        });

        panel.setOpaque(false);
        return panel;
    }
}
