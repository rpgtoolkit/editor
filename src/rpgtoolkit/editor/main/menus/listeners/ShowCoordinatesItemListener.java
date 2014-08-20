package rpgtoolkit.editor.main.menus.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBoxMenuItem;
import rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class ShowCoordinatesItemListener implements ItemListener
{
    private MainWindow parent;
    
    public ShowCoordinatesItemListener()
    {
        
    }
    
    public ShowCoordinatesItemListener(MainWindow parent)
    {
        this.parent = parent;
    }

    @Override
    public void itemStateChanged(ItemEvent e) 
    {
        JCheckBoxMenuItem showCoordinatesMenuItem = (JCheckBoxMenuItem)e.getItem();
        
        if (showCoordinatesMenuItem.isSelected())
        {
            parent.toogleCoordinatesOnBoardEditor(true);
        }
        else
        {
            parent.toogleCoordinatesOnBoardEditor(false);
        }
    }
    
}
