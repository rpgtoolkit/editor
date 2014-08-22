package rpgtoolkit.editor.main;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import rpgtoolkit.common.io.types.Animation;
import rpgtoolkit.common.io.types.Project;
import rpgtoolkit.common.io.types.TileSet;
import rpgtoolkit.editor.animation.AnimationEditor;
import rpgtoolkit.editor.board.BoardEditor;
import rpgtoolkit.editor.board.brush.AbstractBrush;
import rpgtoolkit.editor.board.brush.CustomBrush;
import rpgtoolkit.editor.board.brush.ShapeBrush;
import rpgtoolkit.editor.main.panels.LayerPanel;
import rpgtoolkit.editor.main.menus.MainMenuBar;
import rpgtoolkit.editor.main.menus.MainToolBar;
import rpgtoolkit.editor.main.panels.ProjectPanel;
import rpgtoolkit.editor.main.panels.PropertiesPanel;
import rpgtoolkit.editor.main.panels.TileSetPanel;
import rpgtoolkit.editor.project.ProjectEditor;
import rpgtoolkit.editor.tile.TileEditor;
import rpgtoolkit.editor.tile.event.TileSelectionEvent;
import rpgtoolkit.editor.tile.event.TileSelectionListener;
import rpgtoolkit.editor.tile.TilesetCanvas;
import rpgtoolkit.editor.tile.event.TileRegionSelectionEvent;

/**
 * Currently opening TileSets, tiles, programs, boards, animations, characters
 * etc.
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class MainWindow extends JFrame implements InternalFrameListener
{

    private static final MainWindow instance = new MainWindow();

    private JDesktopPane desktopPane;

    private final JPanel toolboxPanel;
    private final JTabbedPane upperTabbedPane;
    private final JTabbedPane lowerTabbedPane;
    private final ProjectPanel projectPanel;
    private final TileSetPanel tileSetPanel;
    private final PropertiesPanel propertiesPanel;
    private final LayerPanel layerPanel;

    private final JPanel debugPane;
    private final JTextField debugLog;
    private Project activeProject;
    private final MainToolBar toolBar;
    private final JFileChooser fileChooser;
    private final String workingDir = System.getProperty("user.dir");
    private final LinkedList<ToolkitEditorWindow> activeWindows;

    // Board Related.
    private boolean showGrid;
    private boolean showVectors;
    private boolean showCoordinates;

    private AbstractBrush currentBrush;

    // Listeners
    private final TileSetSelectionListener tileSetSelectionListener;

    /*
     * *************************************************************************
     * Private Constructors
     * *************************************************************************
     */
    private MainWindow()
    {
        super("RPG Toolkit");

        this.activeWindows = new LinkedList<>();

        this.desktopPane = new JDesktopPane();
        this.desktopPane.setDesktopManager(new ToolkitDesktopManager(this));
        this.desktopPane.setBackground(Color.LIGHT_GRAY);

        this.projectPanel = new ProjectPanel();
        this.tileSetPanel = new TileSetPanel();
        this.upperTabbedPane = new JTabbedPane();
        this.upperTabbedPane.addTab("Project", this.projectPanel);
        this.upperTabbedPane.addTab("Tileset", this.tileSetPanel);

        this.propertiesPanel = new PropertiesPanel();
        this.layerPanel = new LayerPanel();
        this.lowerTabbedPane = new JTabbedPane();
        this.lowerTabbedPane.addTab("Properties", this.propertiesPanel);
        this.lowerTabbedPane.addTab("Layers", this.layerPanel);

        this.toolboxPanel = new JPanel(new GridLayout(2, 1));
        this.toolboxPanel.setPreferredSize(new Dimension(320, 0));
        this.toolboxPanel.add(upperTabbedPane);
        this.toolboxPanel.add(lowerTabbedPane);

        this.setIconImage(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/application.png"))
                .getImage());

        this.debugPane = new JPanel();
        this.debugLog = new JTextField("Debug Messages:");
        this.debugLog.setEditable(false);
        this.debugLog.setFocusable(false);

        this.debugLog.setText(System.getProperty("user.dir"));

        this.debugPane.setLayout(new BorderLayout());
        this.debugPane.add(debugLog, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());

        this.fileChooser = new JFileChooser();
        this.fileChooser.setCurrentDirectory(new File(System.
                getProperty("user.dir")));

        this.toolBar = new MainToolBar();

        this.tileSetSelectionListener = new TileSetSelectionListener();

        this.currentBrush = new ShapeBrush();
        ((ShapeBrush) this.currentBrush).makeRectangleBrush(
                new Rectangle(0, 0, 1, 1));

        this.add(this.toolBar, BorderLayout.NORTH);
        this.add(this.desktopPane, BorderLayout.CENTER);
        this.add(this.debugPane, BorderLayout.SOUTH);
        this.add(this.toolboxPanel, BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setJMenuBar(new MainMenuBar(this));
        this.setSize(new Dimension(1024, 768));
        this.setLocationByPlatform(true);

        this.testEditor();
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public static MainWindow getInstance()
    {
        return instance;
    }

    public JDesktopPane getDesktopPane()
    {
        return this.desktopPane;
    }

    public void setDesktopPane(JDesktopPane desktopPane)
    {
        this.desktopPane = desktopPane;
    }

    public boolean isShowGrid()
    {
        return showGrid;
    }

    public void setShowGrid(boolean isShowGrid)
    {
        this.showGrid = isShowGrid;
    }

    public boolean isShowVectors()
    {
        return showVectors;
    }

    public void setShowVectors(boolean showVectors)
    {
        this.showVectors = showVectors;
    }

    public boolean isShowCoordinates()
    {
        return showCoordinates;
    }

    public void setShowCoordinates(boolean isShowCoordinates)
    {
        this.showCoordinates = isShowCoordinates;
    }

    public AbstractBrush getCurrentBrush()
    {
        return this.currentBrush;
    }

    public void setCurrentBrush(AbstractBrush brush)
    {
        this.currentBrush = brush;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public void internalFrameOpened(InternalFrameEvent e)
    {
        System.out.println("opened");
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e)
    {
        System.out.println("closing");
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e)
    {
        if (this.activeWindows.contains((ToolkitEditorWindow) e.getInternalFrame()))
        {
            this.activeWindows.remove((ToolkitEditorWindow) e.getInternalFrame());
        }
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e)
    {
        System.out.println("iconified");
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e)
    {
        System.out.println("deciconified");
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e)
    {
        if (e.getInternalFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor) e.getInternalFrame();
            this.layerPanel.setBoardView(editor.getBoardView());

            this.upperTabbedPane.setSelectedComponent(this.tileSetPanel);
            this.lowerTabbedPane.setSelectedComponent(this.layerPanel);
        }

        if (!this.activeWindows.contains((ToolkitEditorWindow) e.getInternalFrame()))
        {
            this.activeWindows.add((ToolkitEditorWindow) e.getInternalFrame());
        }
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e)
    {
        if (e.getInternalFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor) e.getInternalFrame();

            if (this.layerPanel.getBoardView().equals(editor.getBoardView()))
            {
                this.layerPanel.clearTable();
            }
        }
    }

    public void openProject()
    {
        FileNameExtensionFilter filter
                = new FileNameExtensionFilter("Toolkit Project", "gam");
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
        boardEditor.addInternalFrameListener(this);

        this.desktopPane.add(boardEditor);

        try
        {
            boardEditor.setSelected(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(MainWindow.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
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
        this.tileSetPanel.setTilesetCanvas(new TilesetCanvas(
                new TileSet(fileChooser.getSelectedFile())));
        this.tileSetPanel.getTilesetCanvas().addTileSelectionListener(
                this.tileSetSelectionListener);
        this.upperTabbedPane.setSelectedComponent(this.tileSetPanel);
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
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.zoomIn();
        }
    }

    public void zoomOutOnBoardEditor()
    {
        if (desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.zoomOut();
        }
    }

    public void toogleGridOnBoardEditor(boolean isVisible)
    {
        this.showGrid = isVisible;

        if (this.desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor) this.desktopPane.getSelectedFrame();
            editor.getBoardView().repaint();
        }
    }

    public void toogleCoordinatesOnBoardEditor(boolean isVisible)
    {
        this.showCoordinates = isVisible;

        if (desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.getBoardView().repaint();
        }
    }

    public void toogleVectorsOnBoardEditor(boolean isVisible)
    {
        this.showVectors = isVisible;

        if (desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.getBoardView().repaint();
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

    /*
     * *************************************************************************
     * Private Inner Classes
     * *************************************************************************
     */
    private class TileSetSelectionListener implements TileSelectionListener
    {

        /*
         * *********************************************************************
         * Private Inner Classes
         * ***************************************************d******************
         */
        @Override
        public void tileSelected(TileSelectionEvent e)
        {
            if (!(currentBrush instanceof ShapeBrush))
            {
                currentBrush = new ShapeBrush();
                ((ShapeBrush) currentBrush).makeRectangleBrush(
                        new Rectangle(0, 0, 1, 1));
            }

            ((ShapeBrush) currentBrush).setTile(e.getTile());
        }

        @Override
        public void tileRegionSelected(TileRegionSelectionEvent e)
        {
            if (!(currentBrush instanceof CustomBrush))
            {
                currentBrush = new CustomBrush(e.getTiles());
            }
            else
            {
               ((CustomBrush) currentBrush).setTiles(e.getTiles());
            }        
        }
    }

}
