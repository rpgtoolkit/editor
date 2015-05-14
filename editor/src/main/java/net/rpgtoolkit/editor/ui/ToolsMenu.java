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
