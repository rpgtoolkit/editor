package net.rpgtoolkit.editor.ui;

import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;

/**
 *
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class ToolkitDesktopManager extends DefaultDesktopManager
{

    private final MainWindow parent;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public ToolkitDesktopManager(MainWindow parent)
    {
        super();
        this.parent = parent;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public void openFrame(JInternalFrame f)
    {
        super.openFrame(f);
    }

    @Override
    public void closeFrame(JInternalFrame f)
    {
        super.closeFrame(f);
    }

    @Override
    public void activateFrame(JInternalFrame f)
    {
        super.activateFrame(f);
    }

    @Override
    public void deactivateFrame(JInternalFrame f)
    {
        super.deactivateFrame(f);
    }

    @Override
    public void maximizeFrame(JInternalFrame f)
    {
        super.maximizeFrame(f);
    }

    @Override
    public void minimizeFrame(JInternalFrame f)
    {
        super.minimizeFrame(f);
    }

}
