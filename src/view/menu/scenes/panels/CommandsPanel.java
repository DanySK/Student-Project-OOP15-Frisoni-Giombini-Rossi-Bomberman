package view.menu.scenes.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.text.Normalizer;
import java.util.stream.IntStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import view.GUIFactory;
import view.InputHandler;
import view.LanguageHandler;

/**
 * This {@link JPanel} is used to realize a view representation of the input commands.
 *
 */
public class CommandsPanel extends JPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 4451048214593900303L;
    
    private final GUIFactory factory;

    /**
     * Creates a new CommandsPanel.
     */
    public CommandsPanel() {
        this.factory = new GUIFactory.Standard();
        initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());

        final DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[] { LanguageHandler.getHandler().getLocaleResource().getString("action"),
                LanguageHandler.getHandler().getLocaleResource().getString("keyName") });
        
        /*
         *  Adds the rows.
         *  The key text is normalized to remove accented chars.
         */
        InputHandler.getCommandsMap().entrySet()
                                     .stream()
                                     .sorted((a1, a2) -> a1.getValue().compareTo(a2.getValue()))
                                     .forEach(e -> model.addRow(new Object[] { e.getValue(), 
                                             Normalizer.normalize(KeyEvent.getKeyText(e.getKey()), Normalizer.Form.NFD)
                                                       .replaceAll("[^\\p{ASCII}]", "") }));

        final JTable table = this.factory.createTable(model);

        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        this.add(scrollPane, BorderLayout.CENTER);

        final CommandCellRenderer cellRenderer = new CommandCellRenderer(); 
        final CommandHeaderRenderer headerRenderer = new CommandHeaderRenderer();
        final TableColumnModel columnModel = table.getColumnModel();
        IntStream.range(0, columnModel.getColumnCount())
                 .forEach(i -> {
                     columnModel.getColumn(i).setCellRenderer(cellRenderer);
                     columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
                 });
        
        this.setBackground(Color.DARK_GRAY);
    }

    private class CommandHeaderRenderer extends JLabel implements TableCellRenderer {
        
        /**
         * Auto-generated UID.
         */
        private static final long serialVersionUID = 7641120098739137628L;

        CommandHeaderRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value,
                final boolean isSelected, final boolean hasFocus, final int row, final int column) {

            this.setFont(CommandsPanel.this.factory.getDescriptionFont());
            this.setText(value.toString());
            
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.DARK_GRAY);

            return this;
        }
    }

    private class CommandCellRenderer extends JLabel implements TableCellRenderer {
        
        /**
         * Auto-generated UID.
         */
        private static final long serialVersionUID = 7641120098739137628L;

        CommandCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value,
                final boolean isSelected, final boolean hasFocus, final int row, final int column) {

            this.setFont(CommandsPanel.this.factory.getDescriptionFont());
            this.setText(value.toString());

            if (isSelected) {
                setBackground(CommandsPanel.this.factory.getBombermanColor());
                setForeground(Color.WHITE);
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            return this;
        }
    }
}
