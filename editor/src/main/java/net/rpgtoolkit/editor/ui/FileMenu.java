package net.rpgtoolkit.editor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;

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
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    /**
     * 
     * @param parent 
     */
    public FileMenu(MainWindow parent)
    {
        super("File");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_F);
        
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
    
    /*
     * *************************************************************************
     * Public Getters
     * *************************************************************************
     */
    public MainWindow getParentWindow()
    {
        return parent;
    }

    public JMenu getNewMenu()
    {
        return newMenu;
    }

    public JMenuItem getNewProjectMenuItem()
    {
        return newProjectMenuItem;
    }

    public JMenu getOpenMenu()
    {
        return openMenu;
    }

    public JMenuItem getOpenProjectMenuItem()
    {
        return openProjectMenuItem;
    }

    public JMenuItem getOpenFileMenuItem()
    {
        return openFileMenuItem;
    }

    public JMenuItem getSaveMenuItem()
    {
        return saveMenuItem;
    }

    public JMenuItem getSaveAsMenuItem()
    {
        return saveAsMenuItem;
    }

    public JMenuItem getSaveAllMenuItem()
    {
        return saveAllMenuItem;
    }

    public JMenuItem getExitMenuItem()
    {
        return exitMenuItem;
    }
    
    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    /**
     * 
     */
    private void configureFileMenu()
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
    private void configureNewSubMenu()
    {
        this.configureNewProjectMenuItem();
        
        newMenu = new JMenu("New");
        newMenu.setEnabled(false);
        newMenu.add(newProjectMenuItem);
    }
    
    /**
     * 
     */
    private void configureOpenSubMenu()
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
    private void configureNewProjectMenuItem()
    {
        newProjectMenuItem = new JMenuItem("New Project");
        newProjectMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/new-project.png")));
        newProjectMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newProjectMenuItem.setMnemonic(KeyEvent.VK_N);
        newProjectMenuItem.setEnabled(false);
    }
    
    /**
     * 
     */
    private void configureOpenProjectMenuItem()
    {
        openProjectMenuItem = new JMenuItem("Open Project");
        openProjectMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/project.png")));
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
    private void configureOpenFileMenuItem()
    {
        openFileMenuItem = new JMenuItem("Open File");
        this.openFileMenuItem.setEnabled(false);
        openFileMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/open.png")));
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
    private void configureSaveMenuItem()
    {
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/save.png")));
        saveMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.setMnemonic(KeyEvent.VK_N);
        saveMenuItem.setEnabled(false);
        saveMenuItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (parent.getDesktopPane().getSelectedFrame() != null)
                {
                    if (parent.getDesktopPane().getSelectedFrame() instanceof 
                            ToolkitEditorWindow)
                    {
                        ToolkitEditorWindow window = (ToolkitEditorWindow)
                                parent.getDesktopPane().getSelectedFrame();
                        
                        window.save();
                    }
                }
            }
        });
    }
    
    /**
     * 
     */
    private void configureSaveAsMenuItem()
    {
        saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.setMnemonic(KeyEvent.VK_A);
        saveAsMenuItem.setEnabled(false);
    }
    
    /**
     * 
     */
    private void configureSaveAllMenuItem()
    {
        saveAllMenuItem = new JMenuItem("Save All");
        saveAllMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/save-all.png")));
        saveAllMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK +
                ActionEvent.SHIFT_MASK));
        saveAllMenuItem.setEnabled(false);
    }
    
    /**
     * 
     */
    private void configureExitMenuItem()
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
