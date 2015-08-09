/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.rpgtoolkit.common.CorruptAssetException;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.common.assets.BasicType;
import net.rpgtoolkit.common.assets.Enemy;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.Project;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.common.assets.TileSet;
import net.rpgtoolkit.common.assets.files.FileAssetHandleResolver;
import net.rpgtoolkit.common.assets.serialization.JsonSMoveSerializer;

import net.rpgtoolkit.editor.editors.AnimationEditor;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.editors.CharacterEditor;
import net.rpgtoolkit.editor.editors.board.AbstractBrush;
import net.rpgtoolkit.editor.editors.board.BucketBrush;
import net.rpgtoolkit.editor.editors.board.CustomBrush;
import net.rpgtoolkit.editor.editors.board.ShapeBrush;
import net.rpgtoolkit.editor.editors.board.VectorBrush;
import net.rpgtoolkit.editor.editors.EnemyEditor;
import net.rpgtoolkit.editor.editors.ProjectEditor;
import net.rpgtoolkit.editor.editors.SpecialMoveEditor;
import net.rpgtoolkit.editor.editors.TileEditor;
import net.rpgtoolkit.editor.editors.TileSelectionEvent;
import net.rpgtoolkit.editor.editors.TileSelectionListener;
import net.rpgtoolkit.editor.editors.TilesetCanvas;
import net.rpgtoolkit.editor.editors.TileRegionSelectionEvent;
import net.rpgtoolkit.editor.ui.resources.Icons;

/**
 * Currently opening TileSets, tiles, programs, boards, animations, characters
 * etc.
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class MainWindow extends JFrame implements InternalFrameListener {

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
    private MainWindow() {
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
        this.setIconImage(
                Icons.getLargeIcon("application").getImage());

        this.debugPane = new JPanel();
        this.debugLog = new JTextField("Debug Messages:");
        this.debugLog.setEditable(false);
        this.debugLog.setFocusable(false);

        this.debugLog.setText(System.getProperty("user.dir"));

        this.debugPane.setLayout(new BorderLayout());
        this.debugPane.add(debugLog, BorderLayout.CENTER);

        this.registerResolvers();
        this.registerSerializers();
        
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
    public static MainWindow getInstance() {
        return instance;
    }

    public JDesktopPane getDesktopPane() {
        return this.desktopPane;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean isShowGrid) {
        this.showGrid = isShowGrid;
    }

    public boolean isShowVectors() {
        return showVectors;
    }

    public void setShowVectors(boolean showVectors) {
        this.showVectors = showVectors;
    }

    public boolean isShowCoordinates() {
        return showCoordinates;
    }

    public void setShowCoordinates(boolean isShowCoordinates) {
        this.showCoordinates = isShowCoordinates;
    }

    public AbstractBrush getCurrentBrush() {
        return this.currentBrush;
    }

    public void setCurrentBrush(AbstractBrush brush) {
        this.currentBrush = brush;
    }

    public Tile getLastSelectedTile() {
        return this.lastSelectedTile;
    }

    public MainMenuBar getMainMenuBar() {
        return this.menuBar;
    }

    public MainToolBar getMainToolBar() {
        return this.toolBar;
    }

    public PropertiesPanel getPropertiesPanel() {
        return this.propertiesPanel;
    }

    public BoardEditor getCurrentBoardEditor() {
        if (this.desktopPane.getSelectedFrame() instanceof BoardEditor) {
            return (BoardEditor) this.desktopPane.getSelectedFrame();
        }

        return null;
    }

    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }

    public Project getActiveProject() {
        return activeProject;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        if (e.getInternalFrame() instanceof BoardEditor) {
            this.upperTabbedPane.setSelectedComponent(this.tileSetPanel);
        }
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
        if (e.getInternalFrame() instanceof BoardEditor) {
            BoardEditor editor = (BoardEditor) e.getInternalFrame();
            this.layerPanel.setBoardView(editor.getBoardView());

            if (editor.getSelectedObject() != null) {
                this.propertiesPanel.setModel(editor.getSelectedObject());
            }
        }
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
        if (e.getInternalFrame() instanceof BoardEditor) {
            BoardEditor editor = (BoardEditor) e.getInternalFrame();

            if (this.layerPanel.getBoardView().equals(editor.getBoardView())) {
                this.layerPanel.clearTable();
            }

            if (this.propertiesPanel.getModel() == editor.getSelectedObject()) {
                this.propertiesPanel.setModel(null);
            }

            // So we do not end up drawing the vector on the other board
            // after it has been deactivated.
            if (this.currentBrush instanceof VectorBrush) {
                VectorBrush brush = (VectorBrush) this.currentBrush;

                if (brush.isDrawing() && brush.getBoardVector() != null) {
                    brush.finishVector();
                }
            }
        }
    }
    
    private void registerResolvers() {
        AssetManager.getInstance().registerResolver(new FileAssetHandleResolver());
    }

    private void registerSerializers() {
        AssetManager.getInstance().registerSerializer(new JsonSMoveSerializer());
    }

    public void openProject() {
        this.fileChooser.resetChoosableFileFilters();
        FileNameExtensionFilter filter
                = new FileNameExtensionFilter("Toolkit Project", "gam");
        this.fileChooser.setFileFilter(filter);

        File mainFolder = new File(this.workingDir + "/main");

        if (mainFolder.exists()) {
            this.fileChooser.setCurrentDirectory(mainFolder);
        }

        if (this.fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.activeProject = new Project(this.fileChooser.getSelectedFile());
            System.setProperty("project.path", 
                    this.fileChooser.getCurrentDirectory().getParent() + "/game/" 
                            + this.activeProject.getGameTitle() + "/");
            
            ProjectEditor projectEditor = new ProjectEditor(this.activeProject);
            this.desktopPane.add(projectEditor, BorderLayout.CENTER);

            projectEditor.addInternalFrameListener(this);
            projectEditor.setWindowParent(this);
            projectEditor.toFront();

            this.selectToolkitWindow(projectEditor);
            this.setTitle(this.getTitle() + " - "
                    + this.activeProject.getGameTitle());

            this.menuBar.enableMenus(true);
            this.toolBar.enableButtons(true);
        }
    }

    public void primeFileChooser() {
        this.fileChooser.resetChoosableFileFilters();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Toolkit Files", this.getTKFileExtensions());
        this.fileChooser.setFileFilter(filter);

        if (this.activeProject != null) {
            File projectPath = new File(System.getProperty("project.path"));

            if (projectPath.exists()) {
                this.fileChooser.setCurrentDirectory(projectPath);
            }
        }
    }

    public void openFile() {
        this.primeFileChooser();

        if (this.fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.checkFileExtension(this.fileChooser.getSelectedFile());
        }
    }
    
    private String[] getTKFileExtensions() {
        return new String[] {
            "brd", "ene", "tem", "itm", "anm", "prg", "tst", "spc", "json"
        };
    }

    public void checkFileExtension(File file) {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".anm")) {
            this.openAnimation();
        } else if (fileName.endsWith(".brd")) {
            this.openBoard();
        } else if (fileName.endsWith(".ene")) {
            this.openEnemy();
        } else if (fileName.endsWith(".tem")) {
            this.openCharacter();
        } else if (fileName.endsWith(".prg")) {

        } else if (fileName.endsWith(".tst")) {
            this.openTileset();
        } else if (fileName.endsWith(".spc") || fileName.endsWith(".spc.json")) {
            this.openSpecialMove();
        }
    }

    /**
     * Creates an animation editor window for modifying the specified animation
     * file.
     */
    public void openAnimation() {
        Animation animation = new Animation(fileChooser.getSelectedFile());
        AnimationEditor animationEditor = new AnimationEditor(animation);
        desktopPane.add(animationEditor);

        this.selectToolkitWindow(animationEditor);
    }

    public void openBoard() {
        try {
            BoardEditor boardEditor = new BoardEditor(this,
                    fileChooser.getSelectedFile());
            boardEditor.addInternalFrameListener(this);
            boardEditor.setVisible(true);
            boardEditor.toFront();

            if (boardEditor.getBoard().getTileSet() != null) {
                this.tileSetPanel.setTilesetCanvas(new TilesetCanvas(
                        boardEditor.getBoard().getTileSet()));
                this.tileSetPanel.getTilesetCanvas().addTileSelectionListener(
                        this.tileSetSelectionListener);
            }

            this.desktopPane.add(boardEditor);

            this.selectToolkitWindow(boardEditor);
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to open board: " + ex.getMessage());
        }
    }

    /**
     * Creates an enemy editor window for modifying the specified enemy file.
     */
    public void openEnemy() {
        Enemy enemy = new Enemy(fileChooser.getSelectedFile());
        EnemyEditor enemyEditor = new EnemyEditor(enemy);
        desktopPane.add(enemyEditor);

        this.selectToolkitWindow(enemyEditor);
    }

    /**
     * Creates a character editor window for modifying the specified character
     * file.
     */
    public void openCharacter()
    {
        System.out.println("openCharacter()");
        Player player = new Player(fileChooser.getSelectedFile());
        CharacterEditor chEditor = new CharacterEditor(player);
        desktopPane.add(chEditor);

        this.selectToolkitWindow(chEditor);
    }

    /**
     * Creates a TileSet editor window for modifying the specified TileSet.
     */
    public void openTile() {
        TileEditor testTileEditor = new TileEditor(
                fileChooser.getSelectedFile());
        desktopPane.add(testTileEditor);
    }

    public void openTileset() {
        this.tileSetPanel.setTilesetCanvas(new TilesetCanvas(
                new TileSet(fileChooser.getSelectedFile())));
        this.tileSetPanel.getTilesetCanvas().addTileSelectionListener(
                this.tileSetSelectionListener);
        this.upperTabbedPane.setSelectedComponent(this.tileSetPanel);
    }

    public void openSpecialMove() {
        try {
            SpecialMoveEditor sMoveEditor;
            if(fileChooser.getSelectedFile().canRead()) {
                AssetHandle handle = AssetManager.getInstance().deserialize(
                        new AssetDescriptor(fileChooser.getSelectedFile().toURI()));
                SpecialMove move = (SpecialMove)handle.getAsset();
                sMoveEditor = new SpecialMoveEditor(move);
            } else {
                sMoveEditor = new SpecialMoveEditor();
            }
            desktopPane.add(sMoveEditor);
        this.selectToolkitWindow(sMoveEditor);
        } catch(IOException | CorruptAssetException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getTypeSubdirectory(Class<? extends BasicType> type) {
        switch(type.getSimpleName()) {
            case "Animation":
                return "Misc";
            case "Board":
                return "Boards";
            case "Enemy":
                return "Enemy";
            case "Item":
                return "Item";
            case "Player":
                return "Chrs";
            case "Program":
                return "Prg";
            case "StatusEffect":
                return "StatusE";
            case "SpecialMove":
                return "SpcMove";
            case "Tileset":
                return "Tiles";
            default:
                return "";
        }
    }
    
    public String getImageSubdirectory() {
        return "Bitmap";
    }
    
    public String getTypeFilterDescription(Class<? extends BasicType> type) {
        switch(type.getSimpleName()) {
            case "Animation":
                return "Animations";
            case "Board":
                return "Boards";
            case "Enemy":
                return "Enemies";
            case "Item":
                return "Items";
            case "Player":
                return "Characters";
            case "Program":
                return "Programs";
            case "StatusEffect":
                return "Status Effects";
            case "Tileset":
                return "Tilesets";
            case "SpecialMove":
                return "Special Moves";
            default:
                return "Toolkit Files";
        }
    }
    
    public String getImageFilterDescription() {
        return "Supported Files";
    }
    
    public String[] getTypeExtensions(Class<? extends BasicType> type) {
        switch(type.getSimpleName()) {
            case "Animation":
                return new String[] {"anm"};
            case "Board":
                return new String[] {"brd"};
            case "Enemy":
                return new String[] {"ene"};
            case "Item":
                return new String[] {"itm"};
            case "Player":
                return new String[] {"tem"};
            case "Program":
                return new String[] {"prg"};
            case "StatusEffect":
                return new String[] {"ste"};
            case "Tileset":
                return new String[] {"tst"};
            case "SpecialMove":
                return new String[] {"spc", "json"};
            default:
                return this.getTKFileExtensions();
        }
    }
    
    public String[] getImageExtensions() {
        return new String[] {"png", "gif", "jpg", "jpeg", "bmp"};
    }
    
    /**
     * Browse for a file of the given type, starting in the subdirectory for
     * that type, and return its location relative to the subdirectory for that
     * type. Filters by extensions relevant to that type. This is a shortcut
     * method for browseLocationBySubdir().
     *
     * @param type a BasicType class
     * @return the location of the file the user selects, relative to the
     * subdirectory corresponding to that type; or null if no file or an invalid
     * file is selected (see browseLocationBySubdir())
     */
    public String browseByTypeRelative(Class<? extends BasicType> type) {
        File path = this.browseByType(type);
        if(path == null) { return null; }
        return this.getRelativePath(path,
                this.getPath(this.getTypeSubdirectory(type)));
    }
    
    /**
     * Browse for a file of the given type, starting in the subdirectory for
     * that type, and return its location. Filters by extensions relevant to
     * that type. This is a shortcut method for browseLocationBySubdir().
     *
     * @param type a BasicType class
     * @return the location of the file the user selects; or null if no file or
     * an invalid file is selected (see browseLocationBySubdir())
     */
    public File browseByType(Class<? extends BasicType> type) {
        String subdir = this.getTypeSubdirectory(type);
        String desc = getTypeFilterDescription(type);
        String[] exts = getTypeExtensions(type);
        return this.browseLocationBySubdir(subdir, desc, exts);
    }
    
    /**
     * Browse for a file of the given type, starting in the subdirectory for
     * that type, and return its location relative to the subdirectory for that
     * type. The file may not exist yet if the user types it in. Filters by
     * extensions relevant to that type. This is a shortcut method for
     * saveLocationBySubdir().
     *
     * @param type a BasicType class
     * @return the location of the file the user selects, relative to the
     * subdirectory corresponding to that type; or null if no file or an invalid
     * file is selected (see saveLocationBySubdir())
     */
    public String saveByTypeRelative(Class<? extends BasicType> type) {
        File path = this.saveByType(type);
        if(path == null) { return null; }
        return this.getRelativePath(path,
                this.getPath(this.getTypeSubdirectory(type)));
    }

    /**
     * Browse for a file of the given type, starting in the subdirectory for
     * that type, and return its location. The file may not exist yet if the
     * user types it in. Filters by extensions relevant to that type. This is a
     * shortcut method for saveLocationBySubdir().
     *
     * @param type a BasicType class
     * @return the location of the file the user selects; or null if no file or
     * an invalid file is selected (see saveLocationBySubdir())
     */
    public File saveByType(Class<? extends BasicType> type) {
        String subdir = this.getTypeSubdirectory(type);
        String desc = getTypeFilterDescription(type);
        String[] exts = getTypeExtensions(type);
        return this.saveLocationBySubdir(subdir, desc, exts);
    }
    
    /**
     * Browse for a file with one of the given extensions, starting in the given
     * subdirectory of the project, and return its location.
     *
     * @param subdirectory where within the project to start the file chooser
     * @param description what to name the filter (for example, "Program Files")
     * @param extensions the file extensions to filter by (the portion of the
     * file name after the last ".")
     * @return the location of the file the user selects; or null if no file or
     * an invalid file is selected
     */
    public File browseLocationBySubdir(
            String subdirectory, String description, String... extensions) {
        File path = setFileChooserSubdirAndFilters(subdirectory, description, extensions);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (validateFileChoice(path, extensions) == true) {
                return fileChooser.getSelectedFile();
            }
        }
        return null;
    }
    
    /**
     * Browse for a file with one of the given extensions, starting in the given
     * subdirectory of the project, and return its location. May return a new
     * filename if the user types one rather than selecting an existing file.
     *
     * @param subdirectory where within the project to start the file chooser
     * @param description what to name the filter (for example, "Program Files")
     * @param extensions the file extensions to filter by (the portion of the
     * file name after the last ".")
     * @return the location of the file the user selects; or null if no file or
     * an invalid file is selected
     */
    public File saveLocationBySubdir(
            String subdirectory, String description, String... extensions) {
        File path = setFileChooserSubdirAndFilters(subdirectory, description, extensions);
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (validateFileChoice(path, extensions) == true) {
                return fileChooser.getSelectedFile();
            }
        }
        return null;
    }
    
    /**
     * Constructs a File with a full path, given a path relative to the project
     * directory.
     *
     * @param relativePath the relative path, such as "Enemy/foo.ene.json"
     * @return the full path, for example
     * "C:\Bar\RPG Toolkit 4\game\example\Enemy\foo.ene.json" on Windows
     */
    public File getPath(String relativePath) {
        return new File(System.getProperty("project.path") + File.separator
                + relativePath);
    }
    
    /**
     * Gets the part of fullPath relative to the project directory.
     *
     * @param fullPath
     * @return
     */
    public String getRelativePath(File fullPath) {
        return this.getRelativePath(fullPath,
                new File(System.getProperty("project.path") + File.separator));
    }

    /**
     * Gets the part of fullPath relative to relativeTo. Use when relativeTo is
     * an ancestor of fullPath.
     *
     * @param fullPath
     * @param relativeTo
     * @return
     */
    public String getRelativePath(File fullPath, File relativeTo) {
        return fullPath.getPath().replace(
                relativeTo.getPath() + File.separator, "");
    }

    public void zoomInOnBoardEditor() {
        if (desktopPane.getSelectedFrame() instanceof BoardEditor) {
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.zoomIn();
        }
    }

    public void zoomOutOnBoardEditor() {
        if (desktopPane.getSelectedFrame() instanceof BoardEditor) {
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.zoomOut();
        }
    }

    public void toogleGridOnBoardEditor(boolean isVisible) {
        this.showGrid = isVisible;

        if (this.desktopPane.getSelectedFrame() instanceof BoardEditor) {
            BoardEditor editor = (BoardEditor) this.desktopPane.getSelectedFrame();
            editor.getBoardView().repaint();
        }
    }

    public void toogleCoordinatesOnBoardEditor(boolean isVisible) {
        this.showCoordinates = isVisible;

        if (desktopPane.getSelectedFrame() instanceof BoardEditor) {
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.getBoardView().repaint();
        }
    }

    public void toogleVectorsOnBoardEditor(boolean isVisible) {
        this.showVectors = isVisible;

        if (desktopPane.getSelectedFrame() instanceof BoardEditor) {
            BoardEditor editor = (BoardEditor) desktopPane.getSelectedFrame();
            editor.getBoardView().repaint();
        }
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void selectToolkitWindow(ToolkitEditorWindow window) {
        try {
            window.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainWindow.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the file chooser's directory to the given subdirectory of the
     * project, sets its filter to the given description and extensions, and
     * returns its new file path.
     *
     * @param subdirectory
     * @param description
     * @param extensions
     * @return
     */
    private File setFileChooserSubdirAndFilters(
            String subdirectory, String description, String... extensions) {
        fileChooser.resetChoosableFileFilters();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                description, extensions);
        fileChooser.setFileFilter(filter);
        File path = this.getPath(subdirectory);
        if (path.exists()) {
            fileChooser.setCurrentDirectory(path);
        }
        return path;
    }

    /**
     * Gets the location, relative to the given path, of the file chooser's
     * currently selected file. Returns null if the file does not end with one
     * of the given extensions.
     *
     * @param extensions the file is required to end with one of these (do not
     * include the dot that comes immediately before the extension)
     * @param path the location will be relative to this path
     * @return the location of the currently selected file of one of the given
     * extensions, relative to the given path; or null if the extension does not
     * match
     */
    private boolean validateFileChoice(File path, String... extensions) {
        String fileName = fileChooser.getSelectedFile().getName().toLowerCase();
        for(String ext : extensions) {
            if(fileName.endsWith("." + ext)) { return true; }
        }
        return false;
    }

    /*
     * *************************************************************************
     * Private Inner Classes
     * *************************************************************************
     */
    private class TileSetSelectionListener implements TileSelectionListener {

        /*
         * *********************************************************************
         * Private Inner Classes
         * *********************************************************************
         */
        @Override
        public void tileSelected(TileSelectionEvent e) {
            if (currentBrush instanceof ShapeBrush) {
                ((ShapeBrush) currentBrush).setTile(e.getTile());
                toolBar.getPencilButton().setSelected(true);
            } else if (currentBrush instanceof BucketBrush) {
                ((BucketBrush) currentBrush).setPourTile(e.getTile());
            } else {
                currentBrush = new ShapeBrush();
                ((ShapeBrush) currentBrush).makeRectangleBrush(
                        new Rectangle(0, 0, 1, 1));
                toolBar.getPencilButton().setSelected(true);
            }

            if (lastSelectedTile != e.getTile()) {
                lastSelectedTile = e.getTile();
            }
        }

        @Override
        public void tileRegionSelected(TileRegionSelectionEvent e) {
            if (!(currentBrush instanceof CustomBrush)) {
                currentBrush = new CustomBrush(e.getTiles());
            } else {
                ((CustomBrush) currentBrush).setTiles(e.getTiles());
            }

            toolBar.getPencilButton().setSelected(true);
        }
    }

}
