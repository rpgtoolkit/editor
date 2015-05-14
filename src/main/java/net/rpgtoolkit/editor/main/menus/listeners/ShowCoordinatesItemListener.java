package net.rpgtoolkit.editor.main.menus.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBoxMenuItem;
import net.rpgtoolkit.editor.main.MainWindow;

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
        
        this.parent.toogleCoordinatesOnBoardEditor(
                showCoordinatesMenuItem.isSelected());
    }
    
}
