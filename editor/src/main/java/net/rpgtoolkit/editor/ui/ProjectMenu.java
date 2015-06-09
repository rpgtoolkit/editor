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
import net.rpgtoolkit.editor.ui.resources.Icons;

/**
 *
 * @author Joshua Michael Daly
 */
public final class ProjectMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenuItem addAnimationMenuItem;
    private JMenuItem addBoardMenuItem;
    private JMenuItem addCharacterMenuItem; 
    private JMenuItem addItemMenuItem;
    private JMenuItem addProgramMenuItem;
    private JMenuItem addSpecialMoveMenuItem;
    private JMenuItem addStatusEffectMenuItem;
    private JMenuItem addTileMenuItem;
    
    public ProjectMenu(MainWindow parent)
    {
        super("Project");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_P);
        
        this.configureAnimationMenuItem();
        this.configureBoardMenuItem();
        this.configureCharacterMenuItem();
        this.configureItemMenuItem();
        this.configureProgramMenuItem();
        this.configureSpecialMoveMenuItem();
        this.configureStatusEffectMenuItem();
        this.configureTileMenuItem();
        
        this.add(addAnimationMenuItem);
        this.add(addBoardMenuItem);
        this.add(addCharacterMenuItem);
        this.add(addItemMenuItem);
        this.add(addProgramMenuItem);
        this.add(addSpecialMoveMenuItem);
        this.add(addStatusEffectMenuItem);
        this.add(addTileMenuItem);
    }
    
    public void configureAnimationMenuItem()
    {
        addAnimationMenuItem = new JMenuItem("Add Animation");
        addAnimationMenuItem.setIcon(Icons.getSmallIcon("new-animation"));
        addAnimationMenuItem.setMnemonic(KeyEvent.VK_A);
        addAnimationMenuItem.setEnabled(false);
    }
    
    public void configureBoardMenuItem()
    {
        addBoardMenuItem = new JMenuItem("Add Board");
        addBoardMenuItem.setIcon(Icons.getSmallIcon("new-board"));
        addBoardMenuItem.setMnemonic(KeyEvent.VK_B);
        addBoardMenuItem.setEnabled(false);
    }
    
    public void configureCharacterMenuItem()
    {
        addCharacterMenuItem = new JMenuItem("Add Character");
        addCharacterMenuItem.setIcon(Icons.getSmallIcon("new-character"));
        addCharacterMenuItem.setMnemonic(KeyEvent.VK_C);
        addCharacterMenuItem.setEnabled(false);
    }
    
    public void configureItemMenuItem()
    {
        addItemMenuItem = new JMenuItem("Add Item");
        addItemMenuItem.setIcon(Icons.getSmallIcon("new-item"));
        addItemMenuItem.setMnemonic(KeyEvent.VK_I);
        addItemMenuItem.setEnabled(false);
    }
    
    public void configureProgramMenuItem()
    {
        addProgramMenuItem = new JMenuItem("Add Program");
        addProgramMenuItem.setIcon(Icons.getSmallIcon("new-program"));
        addProgramMenuItem.setMnemonic(KeyEvent.VK_P);
        addProgramMenuItem.setEnabled(false);
    }
    
    public void configureSpecialMoveMenuItem()
    {
        addSpecialMoveMenuItem = new JMenuItem("Add Special Move");
        addSpecialMoveMenuItem.setIcon(Icons.getSmallIcon("new-special-move"));
        addSpecialMoveMenuItem.setMnemonic(KeyEvent.VK_S);
        addSpecialMoveMenuItem.setEnabled(false);
    }
    
    public void configureStatusEffectMenuItem()
    {
        addStatusEffectMenuItem = new JMenuItem("Add Status Effect");
        addStatusEffectMenuItem.setIcon(Icons.getSmallIcon("new-status-effect"));
        addStatusEffectMenuItem.setMnemonic(KeyEvent.VK_E); 
        addStatusEffectMenuItem.setEnabled(false);
    }
    
    public void configureTileMenuItem()
    {
        addTileMenuItem = new JMenuItem("Add Tile");
        addTileMenuItem.setIcon(Icons.getSmallIcon("new-tile"));
        addTileMenuItem.setMnemonic(KeyEvent.VK_T);
        addTileMenuItem.setEnabled(false);
    }
}
