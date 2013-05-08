package rpgtoolkit.editor.board.event;

import java.util.EventObject;
import rpgtoolkit.common.io.types.Board;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardChangedEvent extends EventObject
{
    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    
    public BoardChangedEvent(Board board)
    {
        super(board);
    }
}
