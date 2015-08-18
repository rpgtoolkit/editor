/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class SaveAction extends AbstractAction {

  @Override
  public void actionPerformed(ActionEvent e) {
    MainWindow w = MainWindow.getInstance();
    if (w.getDesktopPane().getSelectedFrame() != null) {
      if (w.getDesktopPane().getSelectedFrame() instanceof ToolkitEditorWindow) {
        ToolkitEditorWindow window = (ToolkitEditorWindow) w.getDesktopPane().getSelectedFrame();

        if (!window.save()) {
          JOptionPane.showMessageDialog(MainWindow.getInstance(),
                  "Error saving file please ensure the filename extension are correct.",
                  "Error on Save",
                  JOptionPane.ERROR_MESSAGE);

        }
      }
    }
  }

}
