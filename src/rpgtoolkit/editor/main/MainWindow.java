package rpgtoolkit.editor.main;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import rpgtoolkit.common.io.types.Animation;
import rpgtoolkit.common.io.types.Project;
import rpgtoolkit.editor.animation.AnimationEditor;
import rpgtoolkit.editor.board.BoardEditor;
import rpgtoolkit.editor.main.panels.LayerPanel;
import rpgtoolkit.editor.main.menus.MainMenuBar;
import rpgtoolkit.editor.main.menus.MainToolBar;
import rpgtoolkit.editor.project.ProjectEditor;
import rpgtoolkit.editor.tile.TileEditor;
import rpgtoolkit.editor.tile.TilesetViewer;

/**
 * Currently opening Tilesets, tiles, programs, boards, animations, characters
 * etc.
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class MainWindow extends JFrame
{
    private JDesktopPane desktopPane;
    
    private final JPanel toolboxPanel;
    private final JTabbedPane upperTabbedPane;
    private final JTabbedPane lowerTabbedPane;
    private LayerPanel layerPanel;
    
    private JPanel debugPane;
    private JTextField debugLog;
    private Project activeProject;
    private MainToolBar toolBar;
    private JFileChooser fileChooser;
    private final String workingDir = System.getProperty("user.dir");
    private ArrayList<ToolkitEditorWindow> activeWindows;

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    
     public JDesktopPane getDesktopPane()
    {
        return this.desktopPane;
    }

    public void setDesktopPane(JDesktopPane desktopPane)
    {
        this.desktopPane = desktopPane;
    }
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    
    public MainWindow()
    {
        super("RPG Toolkit");

        activeWindows = new ArrayList();

        desktopPane = new JDesktopPane();
        desktopPane.setDesktopManager(new ToolkitDesktopManager(this));
        desktopPane.setBackground(Color.LIGHT_GRAY);
        
        this.upperTabbedPane = new JTabbedPane();
        
        this.layerPanel = new LayerPanel();
        this.lowerTabbedPane = new JTabbedPane();
        this.lowerTabbedPane.addTab("Layers", this.layerPanel);
        
        this.toolboxPanel = new JPanel(new GridLayout(2, 1));
        this.toolboxPanel.setPreferredSize(new Dimension(320, 0));
        this.toolboxPanel.add(upperTabbedPane);
        this.toolboxPanel.add(lowerTabbedPane);

        this.setIconImage(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/application.png"))
                .getImage());

        debugPane = new JPanel();
        debugLog = new JTextField("Debug Messages:");
        debugLog.setEditable(false);
        debugLog.setFocusable(false);

        debugLog.setText(System.getProperty("user.dir"));

        debugPane.setLayout(new BorderLayout());
        debugPane.add(debugLog, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.
                getProperty("user.dir")));

        toolBar = new MainToolBar(this);

        this.add(toolBar, BorderLayout.NORTH);
        this.add(desktopPane, BorderLayout.CENTER);
        this.add(debugPane, BorderLayout.SOUTH);
        this.add(this.toolboxPanel, BorderLayout.EAST);   
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setJMenuBar(new MainMenuBar(this));
        this.setSize(new Dimension(1024, 768));
        this.setLocationByPlatform(true);
        this.setVisible(true);
        
        this.testEditor();
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */

    public void openProject()
    {
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("Toolkit Project", "gam");
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            activeProject = new Project(fileChooser.getSelectedFile());
            ProjectEditor projectEditor = new ProjectEditor(activeProject);
            this.setTitle(this.getTitle() + " - " + activeProject.getGameTitle() 
                    + " project loaded");
            desktopPane.add(projectEditor, BorderLayout.CENTER);
            projectEditor.setWindowParent(this);
            activeWindows.add(projectEditor);
            //toolBar.enableRun();
        }
    }
    
    public void openFile()
    { 
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            this.checkFileExtension(fileChooser.getSelectedFile());
        }
    }
    
    public void checkFileExtension(File file)
    {
        String fileName = file.getName().toLowerCase();
        
        if (fileName.endsWith(".anm"))
        {
            this.openAnimation();
        }
        else if (fileName.endsWith(".brd"))
        {
            this.openBoard();
        }
        else if (fileName.endsWith(".prg"))
        {
            
        }
        else if (fileName.endsWith(".tst"))
        {
            this.openTileset();
        }
    }
    
    /**
     * Creates an animation editor window for modifying the specified animation 
     * file.
     */
    public void openAnimation()
    {
        Animation animation = new Animation(fileChooser.getSelectedFile());
        AnimationEditor animationEditor = new AnimationEditor(animation);
        desktopPane.add(animationEditor);
    }

    public void openBoard()
    {
        BoardEditor boardEditor = new BoardEditor(this, 
                fileChooser.getSelectedFile());
        boardEditor.setVisible(true);
        boardEditor.toFront();

        if (this.layerPanel == null)
        {
            this.layerPanel = new LayerPanel(boardEditor.getBoardView());
            this.layerPanel.setPreferredSize(this.toolboxPanel.getSize());
            this.toolboxPanel.add(this.layerPanel);
        }
        
        try 
        {
            boardEditor.setSelected(true);
        } 
        catch (PropertyVetoException ex) 
        {
            Logger.getLogger(MainWindow.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        
        this.desktopPane.add(boardEditor);
    }

    /**
     * Creates a tileset editor window for modifying the specified tileset
     */
    public void openTile()
    {
        TileEditor testTileEditor = new TileEditor(
                fileChooser.getSelectedFile());
        desktopPane.add(testTileEditor);
    }

    public void openTileset()
    {
        TilesetViewer testTileEditor = new TilesetViewer(
                fileChooser.getSelectedFile());
        desktopPane.add(testTileEditor);
    }

    public void openBoardForView()
    {
        BoardEditor testBoardEditor = new BoardEditor(this, 
                fileChooser.getSelectedFile());
        testBoardEditor.setVisible(true);
        desktopPane.add(testBoardEditor);
        testBoardEditor.toFront();
        
        try 
        {
            testBoardEditor.setSelected(true);
        } 
        catch (PropertyVetoException ex) 
        {
            Logger.getLogger(MainWindow.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeActiveWindow(ToolkitEditorWindow window)
    {
        activeWindows.remove(window);
    }
    
    public void zoomInOnBoardEditor()
    {
        if (desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor)desktopPane.getSelectedFrame();
            editor.zoomIn();
        }
    }
    
    public void zoomOutOnBoardEditor()
    {
        if (desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor)desktopPane.getSelectedFrame();
            editor.zoomOut();
        }
    }
    
    public void toogleGridOnBoardEditor(boolean isVisible)
    {
        if (desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor)desktopPane.getSelectedFrame();
            editor.toggleGrid(isVisible);
        }
    }
    
    public void toogleCoordinatesOnBoardEditor(boolean isVisible)
    {
        if (desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor)desktopPane.getSelectedFrame();
            editor.toogleCoordinates(isVisible);
        }
    }
    
    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    
    /**
     * Saves having to open file menu all the time!
     */
    private void testEditor()
    {
        
    }
}
