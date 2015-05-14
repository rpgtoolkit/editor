package net.rpgtoolkit.editor.main;

import javax.swing.JInternalFrame;

public abstract class ToolkitEditorWindow extends JInternalFrame
{
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public ToolkitEditorWindow()
    {
        
    }
    
    public ToolkitEditorWindow (String title, boolean resizeable, boolean closeable,
            boolean maximizable, boolean iconifiable)
    {
        super(title, resizeable, closeable, maximizable, iconifiable);
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    
    
    /*
     * *************************************************************************
     * Public Abstract Methods
     * *************************************************************************
     */
    public abstract boolean save();

}
