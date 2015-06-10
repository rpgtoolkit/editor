/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public final class RunMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenuItem debugProgramMenuItem;
    private JMenuItem runProjectMenuItem;
    
    public RunMenu(MainWindow parent)
    {
        super("Run");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_R);
        
        this.configureDebugProgramMenuItem();
        this.configureRunProjectMenuItem();
        
        this.add(debugProgramMenuItem);
        this.add(runProjectMenuItem);
    }
    
    public void configureDebugProgramMenuItem()
    {
        debugProgramMenuItem = new JMenuItem("Debug Program");
        debugProgramMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/bug.png")));
//        debugProgramMenuItem.setAccelerator(
//                KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ACTION_PERFORMED));
        debugProgramMenuItem.setMnemonic(KeyEvent.VK_D);
        
        debugProgramMenuItem.setEnabled(false);
    }
    
    public void configureRunProjectMenuItem()
    {
        runProjectMenuItem = new JMenuItem("Run Project");
        runProjectMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/run.png")));
//        runProjectMenuItem.setAccelerator(
//                KeyStroke.getKeyStroke(KeyEvent.VK_F11, ActionEvent.ACTION_FIRST));
        runProjectMenuItem.setMnemonic(KeyEvent.VK_N);
        
        runProjectMenuItem.setEnabled(false);
    }
}
