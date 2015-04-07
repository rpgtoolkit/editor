package rpgtoolkit.editor.board.event;

import java.util.EventListener;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface BoardChangeListener extends EventListener 
{
    /**
     * 
     * 
     * @param e
     */
    public void boardChanged(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerAdded(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerMovedUp(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerMovedDown(BoardChangedEvent e);
    
    /**
     * 
     * @param e 
     */
    public void boardLayerCloned(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerDeleted(BoardChangedEvent e);
}
