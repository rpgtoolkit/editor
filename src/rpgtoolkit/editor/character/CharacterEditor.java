package rpgtoolkit.editor.character;

import javax.swing.*;
import rpgtoolkit.common.io.types.Player;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.editor.main.ToolkitEditorWindow;

public class CharacterEditor extends ToolkitEditorWindow
{
    public CharacterEditor(Player character)
    {
        super(character.getName(), true, true, true, true);
    }

    public boolean save()
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void gracefulClose()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setWindowParent(MainWindow parent)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
