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
import rpgtoolkit.editor.board.tool.SelectionBrush;
import rpgtoolkit.editor.board.tool.ShapeBrush;
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
                brush.makeRectangleBrush(new Rectangle(0, 0, 1, 1));
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });
        
        this.selectionButton = new JToggleButton();
        this.selectionButton.setFocusable(false);
        this.selectionButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/shape-rectangle.png")));
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
        this.openButton.setEnabled(true);
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
        this.bucketButton.setEnabled(false);
        this.eraserButton.setEnabled(true);
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
     * Public Methods
     * *************************************************************************
     */
    public void enableRun()
    {
        this.runButton.setEnabled(!this.runButton.isEnabled());
    }

}
