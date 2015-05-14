package net.rpgtoolkit.editor.ui;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public final class ProjectMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenuItem addAnimationMenuItem;
    private JMenuItem addBoardMenuItem;
    private JMenuItem addCharacterMenuItem; 
    private JMenuItem addItemMenuItem;
    private JMenuItem addProgramMenuItem;
    private JMenuItem addSpecialMoveMenuItem;
    private JMenuItem addStatusEffectMenuItem;
    private JMenuItem addTileMenuItem;
    
    public ProjectMenu(MainWindow parent)
    {
        super("Project");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_P);
        
        this.configureAnimationMenuItem();
        this.configureBoardMenuItem();
        this.configureCharacterMenuItem();
        this.configureItemMenuItem();
        this.configureProgramMenuItem();
        this.configureSpecialMoveMenuItem();
        this.configureStatusEffectMenuItem();
        this.configureTileMenuItem();
        
        this.add(addAnimationMenuItem);
        this.add(addBoardMenuItem);
        this.add(addCharacterMenuItem);
        this.add(addItemMenuItem);
        this.add(addProgramMenuItem);
        this.add(addSpecialMoveMenuItem);
        this.add(addStatusEffectMenuItem);
        this.add(addTileMenuItem);
    }
    
    public void configureAnimationMenuItem()
    {
        addAnimationMenuItem = new JMenuItem("Add Animation");
        addAnimationMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-animation.png")));
        addAnimationMenuItem.setMnemonic(KeyEvent.VK_A);
        addAnimationMenuItem.setEnabled(false);
    }
    
    public void configureBoardMenuItem()
    {
        addBoardMenuItem = new JMenuItem("Add Board");
        addBoardMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-board.png")));
        addBoardMenuItem.setMnemonic(KeyEvent.VK_B);
        addBoardMenuItem.setEnabled(false);
    }
    
    public void configureCharacterMenuItem()
    {
        addCharacterMenuItem = new JMenuItem("Add Character");
        addCharacterMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-character.png")));
        addCharacterMenuItem.setMnemonic(KeyEvent.VK_C);
        addCharacterMenuItem.setEnabled(false);
    }
    
    public void configureItemMenuItem()
    {
        addItemMenuItem = new JMenuItem("Add Item");
        addItemMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-item.png")));
        addItemMenuItem.setMnemonic(KeyEvent.VK_I);
        addItemMenuItem.setEnabled(false);
    }
    
    public void configureProgramMenuItem()
    {
        addProgramMenuItem = new JMenuItem("Add Program");
        addProgramMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-program.png")));
        addProgramMenuItem.setMnemonic(KeyEvent.VK_P);
        addProgramMenuItem.setEnabled(false);
    }
    
    public void configureSpecialMoveMenuItem()
    {
        addSpecialMoveMenuItem = new JMenuItem("Add Special Move");
        addSpecialMoveMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-special-move.png")));
        addSpecialMoveMenuItem.setMnemonic(KeyEvent.VK_S);
        addSpecialMoveMenuItem.setEnabled(false);
    }
    
    public void configureStatusEffectMenuItem()
    {
        addStatusEffectMenuItem = new JMenuItem("Add Status Effect");
        addStatusEffectMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-status-effect.png")));
        addStatusEffectMenuItem.setMnemonic(KeyEvent.VK_E); 
        addStatusEffectMenuItem.setEnabled(false);
    }
    
    public void configureTileMenuItem()
    {
        addTileMenuItem = new JMenuItem("Add Tile");
        addTileMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-tile.png")));
        addTileMenuItem.setMnemonic(KeyEvent.VK_T);
        addTileMenuItem.setEnabled(false);
    }
}
