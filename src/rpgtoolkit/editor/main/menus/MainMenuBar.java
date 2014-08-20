package rpgtoolkit.editor.main.menus;

import javax.swing.*;
import rpgtoolkit.editor.main.MainWindow;

/**
 * Consider splitting this class up! Done...
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class MainMenuBar extends JMenuBar
{
    private final MainWindow parent;

    private FileMenu fileMenu;
    private EditMenu editMenu;
    private ViewMenu viewMenu;
    private ProjectMenu projectMenu;
    private RunMenu runMenu;
    private ToolsMenu toolsMenu;
    private HelpMenu helpMenu;
    
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
}


