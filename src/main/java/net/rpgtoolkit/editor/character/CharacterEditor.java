package net.rpgtoolkit.editor.character;

import net.rpgtoolkit.common.io.types.Player;
import net.rpgtoolkit.editor.main.MainWindow;
import net.rpgtoolkit.editor.main.ToolkitEditorWindow;

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
