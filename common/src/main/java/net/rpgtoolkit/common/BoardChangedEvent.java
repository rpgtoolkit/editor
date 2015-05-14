package net.rpgtoolkit.common;

import java.util.EventObject;
import net.rpgtoolkit.common.BoardLayer;
import net.rpgtoolkit.common.Board;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardChangedEvent extends EventObject
{
    private BoardLayer layer;
    
    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    
    public BoardChangedEvent(Board board)
    {
        super(board);
    }
    
    /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    
    public BoardLayer getLayer()
    {
        return this.layer;
    }
    
    public void setLayer(BoardLayer layer)
    {
        this.layer = layer;
    }
}
