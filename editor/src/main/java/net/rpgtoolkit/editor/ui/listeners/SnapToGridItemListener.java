package net.rpgtoolkit.editor.ui.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBoxMenuItem;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class SnapToGridItemListener implements ItemListener {

  @Override
  public void itemStateChanged(ItemEvent e) {
    JCheckBoxMenuItem snapToGridMenuItem = (JCheckBoxMenuItem) e.getItem();

    if (snapToGridMenuItem.getState()) {
      MainWindow.getInstance().setSnapToGrid(true);
    } else {
      MainWindow.getInstance().setSnapToGrid(false);
    }
  }

}
