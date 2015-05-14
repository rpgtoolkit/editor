package net.rpgtoolkit.editor.ui;

import net.rpgtoolkit.common.Board;
import net.rpgtoolkit.common.BoardLight;
import net.rpgtoolkit.common.BoardSprite;
import net.rpgtoolkit.common.BoardVector;

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
