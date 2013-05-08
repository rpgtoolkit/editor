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
}
