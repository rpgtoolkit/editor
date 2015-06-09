/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public final class ToolsMenu extends JMenu 
{
    private final MainWindow parent;
    
    public ToolsMenu(MainWindow parent)
    {
        super("Tools");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_T); 
    }
}
