package rpgtoolkit.editor.main;

import javax.swing.*;

public class ToolkitDesktopManager extends DefaultDesktopManager
{
    private MainWindow parent;

    public ToolkitDesktopManager(MainWindow parent)
    {
        super();
        this.parent = parent;
    }

    public void maximizeFrame(JInternalFrame f)
    {
        super.maximizeFrame(f);
        //f.setJMenuBar(null);
    }

    public void minimizeFrame(JInternalFrame f)
    {
        super.minimizeFrame(f);
        //f.setJMenuBar(new ProjectEditorMenu(parent));
    }
}
