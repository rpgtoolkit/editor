/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.rpgtoolkit.editor.ui.MainWindow;

public class Driver {

  public static void main(String[] args) {
    try {
      System.out.println(System.getProperty("os.name"));
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      MainWindow.getInstance().setVisible(true);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {

    }
  }
}
