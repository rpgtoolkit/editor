package rpgtoolkit.editor.main.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public final class FileMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenu newMenu;
    private JMenuItem newProjectMenuItem;
    private JMenu openMenu;
    private JMenuItem openProjectMenuItem;
    private JMenuItem openFileMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem saveAllMenuItem;
    private JMenuItem exitMenuItem;
    
    /**
     * 
     * @param parent 
     */
    public FileMenu(MainWindow parent)
    {
        super("File");
        
        this.parent = parent;
        
        this.configureFileMenu();
        
        this.add(newMenu);
        this.add(openMenu);
        this.add(new JSeparator());
        this.add(saveMenuItem);
        this.add(saveAsMenuItem);
        this.add(saveAllMenuItem);
        this.add(new JSeparator());
        this.add(exitMenuItem);
    }
    
    /**
     * 
     */
    public void configureFileMenu()
    {
        this.configureNewSubMenu();
        this.configureOpenSubMenu();
        this.configureSaveMenuItem();
        this.configureSaveAsMenuItem();
        this.configureSaveAllMenuItem();
        this.configureExitMenuItem();
    }
    
    /**
     * 
     */
    public void configureNewSubMenu()
    {
        this.configureNewProjectMenuItem();
        
        newMenu = new JMenu("New");
        newMenu.add(newProjectMenuItem);
    }
    
    /**
     * 
     */
    public void configureOpenSubMenu()
    {
        this.configureOpenProjectMenuItem();
        this.configureOpenFileMenuItem();
        
        openMenu = new JMenu("Open");
        openMenu.add(openProjectMenuItem);
        openMenu.add(openFileMenuItem);
    }
    
    /**
     * 
     */
    public void configureNewProjectMenuItem()
    {
        newProjectMenuItem = new JMenuItem("New Project");
        newProjectMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/new-project.png")));
        newProjectMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newProjectMenuItem.setMnemonic(KeyEvent.VK_N);
        newProjectMenuItem.setEnabled(false);
    }
    
    /**
     * 
     */
    public void configureOpenProjectMenuItem()
    {
        openProjectMenuItem = new JMenuItem("Open Project");
        openProjectMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/project.png")));
        openProjectMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK +
                ActionEvent.SHIFT_MASK));
        openProjectMenuItem.setMnemonic(KeyEvent.VK_T);
        openProjectMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.openProject();
            }
        });
    }
    
    /**
     * 
     */
    public void configureOpenFileMenuItem()
    {
        openFileMenuItem = new JMenuItem("Open File");
        openFileMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/open.png")));
        openFileMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openFileMenuItem.setMnemonic(KeyEvent.VK_O);
        openFileMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.openFile();
            }
        });
    }
    
    /**
     * 
     */
    public void configureSaveMenuItem()
    {
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/save.png")));
        saveMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.setMnemonic(KeyEvent.VK_N);
        saveMenuItem.setEnabled(false);
    }
    
    /**
     * 
     */
    public void configureSaveAsMenuItem()
    {
        saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.setMnemonic(KeyEvent.VK_A);
        saveAsMenuItem.setEnabled(false);
    }
    
    /**
     * 
     */
    public void configureSaveAllMenuItem()
    {
        saveAllMenuItem = new JMenuItem("Save All");
        saveAllMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/save-all.png")));
        saveAllMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK +
                ActionEvent.SHIFT_MASK));
        saveAllMenuItem.setEnabled(false);
    }
    
    /**
     * 
     */
    public void configureExitMenuItem()
    {
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        exitMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
    }
}
