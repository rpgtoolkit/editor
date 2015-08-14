package net.rpgtoolkit.editor.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBoxMenuItem;

/**
 *
 * @author Joshua Michael Daly
 */
public class SnapToGridItemListener implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBoxMenuItem snapToGridMenuItem = (JCheckBoxMenuItem)e.getItem();
        
        if (snapToGridMenuItem.getState()) {
            MainWindow.getInstance().setSnapToGrid(true);
        }
        else {
            MainWindow.getInstance().setSnapToGrid(false);
        }
    }
    
}
