package rpgtoolkit.editor.main.menus.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBoxMenuItem;
import rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author jmd
 */
public class ShowVectorsItemListener implements ItemListener
{
    private MainWindow parent;
    
    public ShowVectorsItemListener()
    {
        
    }
    
    public ShowVectorsItemListener(MainWindow parent)
    {
        this.parent = parent;
    }

    @Override
    public void itemStateChanged(ItemEvent e) 
    {
        JCheckBoxMenuItem showVectorsMenuItem = (JCheckBoxMenuItem)e.getItem();
        
        this.parent.toogleVectorsOnBoardEditor(showVectorsMenuItem.isSelected());
    }   
}
