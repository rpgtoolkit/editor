/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui.menu;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;

/**
 *
 * @author Joshua Michael Daly
 */
public final class ToolsMenu extends JMenu {

  public ToolsMenu() {
    super("Tools");

    this.setMnemonic(KeyEvent.VK_T);
  }
  
}
