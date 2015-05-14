package net.rpgtoolkit.editor.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class ZoomInAction extends AbstractAction
{
    private MainWindow parent;
    
    /**
     * 
     */
    public ZoomInAction()
    {
        
    }
    
    /**
     * 
     * @param parent
     */
    public ZoomInAction(MainWindow parent)
    {
        this.parent = parent;
    }
  
    /**
     * 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        parent.zoomInOnBoardEditor();
    }
}
