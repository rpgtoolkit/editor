package net.rpgtoolkit.editor.main.panels.properties;

import net.rpgtoolkit.common.io.types.Board;
import net.rpgtoolkit.editor.board.types.BoardLight;
import net.rpgtoolkit.editor.board.types.BoardSprite;
import net.rpgtoolkit.editor.board.types.BoardVector;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public final class ModelPanelFactory
{
    /*
     * *************************************************************************
     * Private Constructors
     * *************************************************************************
     */
    private ModelPanelFactory()
    {
        
    }
    
    /*
     * *************************************************************************
     * Public Static Methods
     * *************************************************************************
     */
    public static AbstractModelPanel getModelPanel(Object model)
    {
        if (model instanceof Board)
        {
            
        }
        else if (model instanceof BoardVector)
        {
            return new BoardVectorPanel((BoardVector)model);
        }
        else if (model instanceof BoardSprite)
        {
            return new BoardSpritePanel((BoardSprite)model);
        }
        else if (model instanceof BoardLight)
        {
            
        }
        
        return null;
    }
    
}
