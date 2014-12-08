package rpgtoolkit.editor.main;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.Animation;
import rpgtoolkit.common.io.types.Enemy;
import rpgtoolkit.common.io.types.Project;
import rpgtoolkit.common.io.types.TileSet;
import rpgtoolkit.editor.animation.AnimationEditor;
import rpgtoolkit.editor.board.BoardEditor;
import rpgtoolkit.editor.board.tool.AbstractBrush;
import rpgtoolkit.editor.board.tool.BucketBrush;
import rpgtoolkit.editor.board.tool.CustomBrush;
import rpgtoolkit.editor.board.tool.ShapeBrush;
import rpgtoolkit.editor.board.tool.VectorBrush;
import rpgtoolkit.editor.enemy.EnemyEditor;
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

    // Singleton.
    private static final MainWindow instance = new MainWindow();

    private final JDesktopPane desktopPane;

    private final MainMenuBar menuBar;
    private final MainToolBar toolBar;

    private final JPanel toolboxPanel;
    private final JTabbedPane upperTabbedPane;
    private final JTabbedPane lowerTabbedPane;
    private final ProjectPanel projectPanel;
    private final TileSetPanel tileSetPanel;
    private final PropertiesPanel propertiesPanel;
    private final LayerPanel layerPanel;

    private final JFileChooser fileChooser;
    private final String workingDir = System.getProperty("user.dir");

    private final JPanel debugPane;
    private final JTextField debugLog;

    // Project Related.
    private Project activeProject;

    // Board Related.
    private boolean showGrid;
    private boolean showVectors;
    private boolean showCoordinates;
    private AbstractBrush currentBrush;
    private Tile lastSelectedTile;

    // Listeners.
    private final TileSetSelectionListener tileSetSelectionListener;

    /*
     * *************************************************************************
     * Private Constructors
     * *************************************************************************
     */
    private MainWindow()
    {
        super("RPG Toolkit 4.0");

        this.desktopPane = new JDesktopPane();
        this.desktopPane.setBackground(Color.LIGHT_GRAY);
        this.desktopPane.setDesktopManager(new ToolkitDesktopManager(this));

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
        this.toolboxPanel.add(this.upperTabbedPane);
        this.toolboxPanel.add(this.lowerTabbedPane);

        // Application icon.
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

        this.fileChooser = new JFileChooser();
        this.fileChooser.setCurrentDirectory(new File(this.workingDir));

        this.menuBar = new MainMenuBar(this);
        this.toolBar = new MainToolBar();

        this.currentBrush = new ShapeBrush();
        ((ShapeBrush) this.currentBrush).makeRectangleBrush(
                new Rectangle(0, 0, 1, 1));
        
        this.lastSelectedTile = new Tile();

        this.tileSetSelectionListener = new TileSetSelectionListener();

        this.setLayout(new BorderLayout());
        this.add(this.toolBar, BorderLayout.NORTH);
        this.add(this.desktopPane, BorderLayout.CENTER);
        this.add(this.debugPane, BorderLayout.SOUTH);
        this.add(this.toolboxPanel, BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(new Dimension(1024, 768));
        this.setLocationByPlatform(true);
        this.setJMenuBar(this.menuBar);
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
    
    public Tile getLastSelectedTile()
    {
        return this.lastSelectedTile;
    }
    
    public MainMenuBar getMainMenuBar()
    {
        return this.menuBar;
    }
    
    public MainToolBar getMainToolBar()
    {
        return this.toolBar;
    }
    
    public PropertiesPanel getPropertiesPanel()
    {
        return this.propertiesPanel;
    }
    
    public BoardEditor getCurrentBoardEditor()
    {
        if (this.desktopPane.getSelectedFrame() instanceof BoardEditor)
        {
            return (BoardEditor)this.desktopPane.getSelectedFrame();
        }
        
        return null;
    }

    public JFileChooser getFileChooser()
    {
        return this.fileChooser;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public void internalFrameOpened(InternalFrameEvent e)
    {
        if (e.getInternalFrame() instanceof BoardEditor)
        {
            this.upperTabbedPane.setSelectedComponent(this.tileSetPanel);
        }
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e)
    {
        if (e.getInternalFrame() instanceof BoardEditor)
        {
            BoardEditor editor = (BoardEditor) e.getInternalFrame();
            this.layerPanel.setBoardView(editor.getBoardView());

            if (editor.getSelectedObject() != null)
            {
                this.propertiesPanel.setModel(editor.getSelectedObject());
            }
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
            
            if (this.propertiesPanel.getModel() == editor.getSelectedObject())
            {
                this.propertiesPanel.setModel(null);
            }
            
            // So we do not end up drawing the vector on the other board
            // after it has been deactivated.
            if (this.currentBrush instanceof VectorBrush)
            {
                VectorBrush brush = (VectorBrush)this.currentBrush;
                
                if (brush.isDrawing() && brush.getBoardVector() != null)
                {
                    brush.finishVector();
                }
            }
        }
    }

    public void openProject()
    {
        FileNameExtensionFilter filter
                = new FileNameExtensionFilter("Toolkit Project", "gam");
        this.fileChooser.setFileFilter(filter);

        File mainFolder = new File(this.workingDir + "/main");

        if (mainFolder.exists())
        {
            this.fileChooser.setCurrentDirectory(mainFolder);
        }

        if (this.fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            this.activeProject = new Project(this.fileChooser.getSelectedFile());
            ProjectEditor projectEditor = new ProjectEditor(this.activeProject);
            this.desktopPane.add(projectEditor, BorderLayout.CENTER);

            projectEditor.addInternalFrameListener(this);
            projectEditor.setWindowParent(this);
            projectEditor.toFront();

            this.selectToolkitWindow(projectEditor);
            this.setTitle(this.getTitle() + " - " + 
                    this.activeProject.getGameTitle());

            this.menuBar.enableMenus(true);
            this.toolBar.enableButtons(true);
        }
    }
    
    public void primeFileChooser()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Toolkit Files", "brd", "ene", "tem", "itm", "anm", "prg", "tst");
        this.fileChooser.setFileFilter(filter);

        if (this.activeProject != null)
        {
            File projectPath = new File(System.getProperty("project.path"));

            if (projectPath.exists())
            {
                this.fileChooser.setCurrentDirectory(projectPath);
            }
        }
    }

    public void openFile()
    {
        this.primeFileChooser();

        if (this.fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            this.checkFileExtension(this.fileChooser.getSelectedFile());
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
        else if (fileName.endsWith(".ene"))
        {
            this.openEnemy();
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

        this.selectToolkitWindow(animationEditor);
    }

    public void openBoard()
    {
        BoardEditor boardEditor = new BoardEditor(this,
                fileChooser.getSelectedFile());
        boardEditor.addInternalFrameListener(this);
        boardEditor.setVisible(true);
        boardEditor.toFront();

        if (boardEditor.getBoard().getTileSet() != null)
        {
            this.tileSetPanel.setTilesetCanvas(new TilesetCanvas(
                    boardEditor.getBoard().getTileSet()));
            this.tileSetPanel.getTilesetCanvas().addTileSelectionListener(
                    this.tileSetSelectionListener);
        }

        this.desktopPane.add(boardEditor);

        this.selectToolkitWindow(boardEditor);
    }

    /**
     * Creates an animation editor window for modifying the specified animation
     * file.
     */
    public void openEnemy()
    {
        Enemy enemy = new Enemy(fileChooser.getSelectedFile());
        EnemyEditor enemyEditor = new EnemyEditor(enemy);
        desktopPane.add(enemyEditor);

        this.selectToolkitWindow(enemyEditor);
    }

    /**
     * Creates a TileSet editor window for modifying the specified TileSet.
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
    private void selectToolkitWindow(ToolkitEditorWindow window)
    {
        try
        {
            window.setSelected(true);
        }
        catch (PropertyVetoException ex)
        {
            Logger.getLogger(MainWindow.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
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
         * *********************************************************************
         */
        @Override
        public void tileSelected(TileSelectionEvent e)
        {
            if (currentBrush instanceof ShapeBrush)
            {
                ((ShapeBrush) currentBrush).setTile(e.getTile());
                toolBar.getPencilButton().setSelected(true);
            }
            else if (currentBrush instanceof BucketBrush)
            {
                ((BucketBrush) currentBrush).setPourTile(e.getTile());
            }
            else
            {
                currentBrush = new ShapeBrush();
                ((ShapeBrush) currentBrush).makeRectangleBrush(
                        new Rectangle(0, 0, 1, 1));
                toolBar.getPencilButton().setSelected(true);
            }
            
            if (lastSelectedTile != e.getTile())
            {
                lastSelectedTile = e.getTile();
            }
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
            
            toolBar.getPencilButton().setSelected(true);
        }
    }

}
