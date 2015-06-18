/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import javax.swing.*;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 * 
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class MainMenuBar extends JMenuBar
{
    private final MainWindow parent;

    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final ViewMenu viewMenu;
    private final ProjectMenu projectMenu;
    private final RunMenu runMenu;
    private final ToolsMenu toolsMenu;
    private final HelpMenu helpMenu;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public MainMenuBar(MainWindow menuBarParent)
    {
        this.parent = menuBarParent;

        fileMenu = new FileMenu(this.parent);
        editMenu = new EditMenu(this.parent);
        viewMenu = new ViewMenu(this.parent);
        projectMenu = new ProjectMenu(this.parent);
        runMenu = new RunMenu(this.parent);
        toolsMenu = new ToolsMenu(this.parent);
        helpMenu = new HelpMenu(this.parent);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(viewMenu);
        this.add(projectMenu);
        this.add(runMenu);
        this.add(toolsMenu);
        this.add(helpMenu);
    }
    
    /*
     * *************************************************************************
     * Public Getters
     * *************************************************************************
     */
    public MainWindow getParentWindow()
    {
        return parent;
    }

    public FileMenu getFileMenu()
    {
        return fileMenu;
    }

    public EditMenu getEditMenu()
    {
        return editMenu;
    }

    public ViewMenu getViewMenu()
    {
        return viewMenu;
    }

    public ProjectMenu getProjectMenu()
    {
        return projectMenu;
    }

    public RunMenu getRunMenu()
    {
        return runMenu;
    }

    public ToolsMenu getToolsMenu()
    {
        return toolsMenu;
    }

    @Override
    public HelpMenu getHelpMenu()
    {
        return helpMenu;
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void enableMenus(boolean enable)
    {
        this.fileMenu.getOpenFileMenuItem().setEnabled(enable);
    }
    
}


