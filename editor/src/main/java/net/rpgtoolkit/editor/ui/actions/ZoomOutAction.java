package net.rpgtoolkit.editor.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.rpgtoolkit.editor.ui.MainWindow;

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
