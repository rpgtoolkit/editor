package rpgtoolkit.editor.main;

public interface ToolkitEditorWindow
{
    public boolean save();

    public void gracefulClose();

    public void setWindowParent(MainWindow parent);
}
