package rpgtoolkit.editor.character;

import rpgtoolkit.common.io.types.Player;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.editor.main.ToolkitEditorWindow;

public class CharacterEditor extends ToolkitEditorWindow
{

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public CharacterEditor(Player character)
    {
        super(character.getName(), true, true, true, true);
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public boolean save()
    {
        return false;
    }

    public void gracefulClose()
    {

    }

    public void setWindowParent(MainWindow parent)
    {

    }
}
