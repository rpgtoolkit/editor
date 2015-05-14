package net.rpgtoolkit.editor.main.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class ZoomOutAction extends AbstractAction
{
    private MainWindow parent;
    
    public ZoomOutAction()
    {
        
    }
    
    public ZoomOutAction(MainWindow parent)
    {
        this.parent = parent;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        parent.zoomOutOnBoardEditor();
    }
}
