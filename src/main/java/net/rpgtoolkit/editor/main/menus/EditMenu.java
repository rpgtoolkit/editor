package net.rpgtoolkit.editor.main.menus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import net.rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public final class EditMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenuItem cutMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem pasteMenuItem;
    private JMenuItem selectAllMenuItem;
    private JMenuItem commentMenuItem;
    private JMenuItem findMenuItem;
    private JMenuItem quickReplaceMenuItem;
    
    public EditMenu(MainWindow parent)
    {
        super("Edit");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_E);
        
        this.configureUndoMenuItem();
        this.configureRedoMenuItem();
        this.configureCutMenuItem();
        this.configureCopyMenuItem();
        this.configurePasteMenuItem();
        this.configureSelectAllMenuItem();
        this.configureCommentMenuItem();
        this.configureFindMenuItem();
        this.configureQuickReplaceMenuItem();
        
        this.add(undoMenuItem);
        this.add(redoMenuItem);
        this.add(new JSeparator());
        this.add(cutMenuItem);
        this.add(copyMenuItem);
        this.add(pasteMenuItem);
        this.add(new JSeparator());
        this.add(selectAllMenuItem);
        this.add(commentMenuItem);
        this.add(new JSeparator());
        this.add(findMenuItem);
        this.add(quickReplaceMenuItem);
    }
    
    public void configureUndoMenuItem()
    {
        undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/undo.png")));
        undoMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undoMenuItem.setMnemonic(KeyEvent.VK_U);
        
        undoMenuItem.setEnabled(false);
    }
    
    public void configureRedoMenuItem()
    {
        redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/redo.png")));
        redoMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        redoMenuItem.setMnemonic(KeyEvent.VK_R);
        
        redoMenuItem.setEnabled(false);
    }
    
    public void configureCutMenuItem()
    {
        cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/cut.png")));
        cutMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cutMenuItem.setMnemonic(KeyEvent.VK_T);
        
        cutMenuItem.setEnabled(false);
    }
    
    public void configureCopyMenuItem()
    {
        copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/copy.png")));
        copyMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copyMenuItem.setMnemonic(KeyEvent.VK_C);
        
        copyMenuItem.setEnabled(false);
    }
    
    public void configurePasteMenuItem()
    {
        pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/paste.png")));
        pasteMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        pasteMenuItem.setMnemonic(KeyEvent.VK_P);
        
        pasteMenuItem.setEnabled(false);
    }
    
    public void configureSelectAllMenuItem()
    {
        selectAllMenuItem = new JMenuItem("Select All");
        selectAllMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAllMenuItem.setMnemonic(KeyEvent.VK_A);
        
        selectAllMenuItem.setEnabled(false);
    }
    
    public void configureCommentMenuItem()
    {
        commentMenuItem = new JMenuItem("Un/Comment Selected");
        commentMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK
                + ActionEvent.SHIFT_MASK));
        commentMenuItem.setMnemonic(KeyEvent.VK_M);
        
        commentMenuItem.setEnabled(false);
    }
    
    public void configureFindMenuItem()
    {
        findMenuItem = new JMenuItem("Quick Find");
        findMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/find.png")));
        findMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        findMenuItem.setMnemonic(KeyEvent.VK_F);
        
        findMenuItem.setEnabled(false);
    }
    
    public void configureQuickReplaceMenuItem()
    {
        quickReplaceMenuItem = new JMenuItem("Quick Replace");
        quickReplaceMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/replace.png")));
        quickReplaceMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        quickReplaceMenuItem.setMnemonic(KeyEvent.VK_R);
        
        quickReplaceMenuItem.setEnabled(false);
    }
}
