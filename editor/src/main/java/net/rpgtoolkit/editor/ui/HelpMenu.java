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
public final class HelpMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenuItem indexMenuItem;
    private JMenuItem aboutMenuItem;
    
    public HelpMenu(MainWindow parent)
    {
        super("Help");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_H);
        
        this.configureIndexMenuItem();
        this.configureAboutMenuItem();
        
        this.add(indexMenuItem);
        this.add(aboutMenuItem);
    }
    
    public void configureIndexMenuItem()
    {
        indexMenuItem = new JMenuItem("Index");     // Help Index Menu (browser based?)
        indexMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/help.png")));
        indexMenuItem.setEnabled(false);
    }
    
    public void configureAboutMenuItem()
    {
        aboutMenuItem = new JMenuItem("About");     // About Menu
        aboutMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/information.png")));
        aboutMenuItem.setMnemonic(KeyEvent.VK_A);
        aboutMenuItem.setEnabled(false);
    }
}
