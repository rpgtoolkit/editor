package rpgtoolkit.editor.main.menus;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import rpgtoolkit.editor.main.MainWindow;

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
