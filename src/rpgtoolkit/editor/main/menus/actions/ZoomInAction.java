package rpgtoolkit.editor.main.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import rpgtoolkit.editor.main.MainWindow;

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
     * @param boardView 
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
