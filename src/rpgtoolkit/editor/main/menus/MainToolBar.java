package rpgtoolkit.editor.main.menus;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import rpgtoolkit.common.editor.types.EditorButton;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.editor.board.tool.BucketBrush;
import rpgtoolkit.editor.board.tool.SelectionBrush;
import rpgtoolkit.editor.board.tool.ShapeBrush;
import rpgtoolkit.editor.board.tool.SpriteBrush;
import rpgtoolkit.editor.board.tool.VectorBrush;
import rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class MainToolBar extends JToolBar
{

    private final JPopupMenu popupMenu;
    private final JMenuItem newAnimationMenu;
    private final JMenuItem newProjectMenu;

    private final EditorButton newButton;
    private final EditorButton openButton;
    private final EditorButton saveButton;
    private final EditorButton saveAllButton;

    private final EditorButton cutButton;
    private final EditorButton copyButton;
    private final EditorButton pasteButton;
    private final EditorButton deleteButton;

    private final EditorButton undoButton;
    private final EditorButton redoButton;

    private final ButtonGroup toolButtonGroup;
    private final JToggleButton pencilButton;
    private final JToggleButton selectionButton;
    private final JToggleButton bucketButton;
    private final JToggleButton eraserButton;
    private final JToggleButton vectorButton;
    private final JToggleButton spriteButton;
    private final JToggleButton lightButton;

    private final EditorButton zoomInButton;
    private final EditorButton zoomOutButton;

    private final EditorButton runButton;
    private final EditorButton stopButton;

    private final EditorButton helpButton;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public MainToolBar()
    {
        super();

        this.setFloatable(false);

        this.popupMenu = new JPopupMenu();
        this.newAnimationMenu = new JMenuItem("Animation");
        this.newProjectMenu = new JMenuItem("Project");

        this.popupMenu.add(this.newAnimationMenu);
        this.popupMenu.add(this.newProjectMenu);

        this.newButton = new EditorButton();
        this.newButton.setIcon(new ImageIcon(getClass().getResource(
                "/rpgtoolkit/editor/resources/new.png")));

        this.openButton = new EditorButton();
        this.openButton.setEnabled(false);
        this.openButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/open.png")));
        this.openButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainWindow.getInstance().openFile();
            }
        });

        this.saveButton = new EditorButton();
        this.saveButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/save.png")));

        this.saveAllButton = new EditorButton();
        this.saveAllButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/save-all.png")));

        this.cutButton = new EditorButton();
        this.cutButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/cut.png")));

        this.copyButton = new EditorButton();
        this.copyButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/copy.png")));

        this.pasteButton = new EditorButton();
        this.pasteButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/paste.png")));

        this.deleteButton = new EditorButton();
        this.deleteButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/delete.png")));

        this.undoButton = new EditorButton();
        this.undoButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/undo.png")));

        this.redoButton = new EditorButton();
        this.redoButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/redo.png")));

        this.pencilButton = new JToggleButton();
        this.pencilButton.setFocusable(false);
        this.pencilButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/pencil.png")));
        this.pencilButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                ShapeBrush brush = new ShapeBrush();
                brush.setTile(MainWindow.getInstance().getLastSelectedTile());
                brush.makeRectangleBrush(new Rectangle(0, 0, 1, 1));
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.selectionButton = new JToggleButton();
        this.selectionButton.setFocusable(false);
        this.selectionButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/selection.png")));
        this.selectionButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                SelectionBrush brush = new SelectionBrush(new Tile[1][1]);
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.bucketButton = new JToggleButton();
        this.bucketButton.setFocusable(false);
        this.bucketButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/bucket.png")));
        this.bucketButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                BucketBrush brush = new BucketBrush();
                brush.setPourTile(MainWindow.getInstance().getLastSelectedTile());
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.eraserButton = new JToggleButton();
        this.eraserButton.setFocusable(false);
        this.eraserButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/eraser.png")));
        this.eraserButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                ShapeBrush brush = new ShapeBrush();
                brush.makeRectangleBrush(new Rectangle(0, 0, 1, 1));
                brush.setTile(new Tile());
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.vectorButton = new JToggleButton();
        this.vectorButton.setFocusable(false);
        this.vectorButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/layer-shape-polyline.png")));
        this.vectorButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                VectorBrush brush = new VectorBrush();
                MainWindow.getInstance().setCurrentBrush(brush);

                if (MainWindow.getInstance().getMainMenuBar().getViewMenu()
                        .getShowVectorsMenuItem().isSelected() == false)
                {
                    MainWindow.getInstance().getMainMenuBar().getViewMenu()
                        .getShowVectorsMenuItem().setSelected(true);
                }
            }
        });

        this.spriteButton = new JToggleButton();
        this.spriteButton.setFocusable(false);
        this.spriteButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/user.png")));
        this.spriteButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                SpriteBrush brush = new SpriteBrush();
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.lightButton = new JToggleButton();
        this.lightButton.setFocusable(false);
        this.lightButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/flashlight-shine.png")));
        this.lightButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });

        this.zoomInButton = new EditorButton();
        this.zoomInButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/zoom-in.png")));
        this.zoomInButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainWindow.getInstance().zoomInOnBoardEditor();
            }
        });

        this.zoomOutButton = new EditorButton();
        this.zoomOutButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/zoom-out.png")));
        this.zoomOutButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainWindow.getInstance().zoomOutOnBoardEditor();
            }
        });

        this.runButton = new EditorButton();
        this.runButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/run.png")));
        this.runButton.setEnabled(false);

        this.stopButton = new EditorButton();
        this.stopButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/stop.png")));

        this.helpButton = new EditorButton();
        this.helpButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/help.png")));

        // Disable all the buttons for now
        this.newButton.setEnabled(false);
        this.openButton.setEnabled(false);
        this.saveButton.setEnabled(false);
        this.saveAllButton.setEnabled(false);
        this.cutButton.setEnabled(false);
        this.copyButton.setEnabled(false);
        this.pasteButton.setEnabled(false);
        this.deleteButton.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.pencilButton.setEnabled(true);
        this.selectionButton.setEnabled(true);
        this.bucketButton.setEnabled(true);
        this.eraserButton.setEnabled(true);
        this.vectorButton.setEnabled(true);
        this.spriteButton.setEnabled(true);
        this.lightButton.setEnabled(false);
        this.zoomInButton.setEnabled(true);
        this.zoomOutButton.setEnabled(true);
        this.runButton.setEnabled(false);
        this.stopButton.setEnabled(false);
        this.helpButton.setEnabled(false);

        this.toolButtonGroup = new ButtonGroup();
        this.toolButtonGroup.add(this.pencilButton);
        this.toolButtonGroup.add(this.selectionButton);
        this.toolButtonGroup.add(this.bucketButton);
        this.toolButtonGroup.add(this.eraserButton);
        this.toolButtonGroup.add(this.vectorButton);
        this.toolButtonGroup.add(this.spriteButton);
        this.toolButtonGroup.add(this.lightButton);

        this.add(this.newButton);
        this.add(this.openButton);
        this.add(this.saveButton);
        this.add(this.saveAllButton);
        this.addSeparator();
        this.add(this.cutButton);
        this.add(this.copyButton);
        this.add(this.pasteButton);
        this.add(this.deleteButton);
        this.addSeparator();
        this.add(this.undoButton);
        this.add(this.redoButton);
        this.addSeparator();
        this.add(this.pencilButton);
        this.add(this.selectionButton);
        this.add(this.bucketButton);
        this.add(this.eraserButton);
        this.add(this.vectorButton);
        this.add(this.spriteButton);
        this.add(this.lightButton);
        this.addSeparator();
        this.add(this.zoomInButton);
        this.add(this.zoomOutButton);
        this.addSeparator();
        this.add(this.runButton);
        this.add(this.stopButton);
        this.addSeparator();
        this.add(this.helpButton);
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public JPopupMenu getPopupMenu()
    {
        return popupMenu;
    }

    public JMenuItem getNewAnimationMenu()
    {
        return newAnimationMenu;
    }

    public JMenuItem getNewProjectMenu()
    {
        return newProjectMenu;
    }

    public EditorButton getNewButton()
    {
        return newButton;
    }

    public EditorButton getOpenButton()
    {
        return openButton;
    }

    public EditorButton getSaveButton()
    {
        return saveButton;
    }

    public EditorButton getSaveAllButton()
    {
        return saveAllButton;
    }

    public EditorButton getCutButton()
    {
        return cutButton;
    }

    public EditorButton getCopyButton()
    {
        return copyButton;
    }

    public EditorButton getPasteButton()
    {
        return pasteButton;
    }

    public EditorButton getDeleteButton()
    {
        return deleteButton;
    }

    public EditorButton getUndoButton()
    {
        return undoButton;
    }

    public EditorButton getRedoButton()
    {
        return redoButton;
    }

    public ButtonGroup getToolButtonGroup()
    {
        return toolButtonGroup;
    }

    public JToggleButton getPencilButton()
    {
        return pencilButton;
    }

    public JToggleButton getSelectionButton()
    {
        return selectionButton;
    }

    public JToggleButton getBucketButton()
    {
        return bucketButton;
    }

    public JToggleButton getEraserButton()
    {
        return eraserButton;
    }

    public EditorButton getZoomInButton()
    {
        return zoomInButton;
    }

    public EditorButton getZoomOutButton()
    {
        return zoomOutButton;
    }

    public EditorButton getRunButton()
    {
        return runButton;
    }

    public EditorButton getStopButton()
    {
        return stopButton;
    }

    public EditorButton getHelpButton()
    {
        return helpButton;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void enableButtons(boolean enable)
    {
        this.openButton.setEnabled(enable);
    }

}
