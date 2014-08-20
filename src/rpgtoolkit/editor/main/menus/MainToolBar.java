package rpgtoolkit.editor.main.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import rpgtoolkit.common.editor.types.EditorButton;
import rpgtoolkit.editor.main.MainWindow;

public class MainToolBar extends JToolBar
{
    private final MainWindow parent;

    private JPopupMenu popupMenu;
    private JMenuItem newAnimationMenu;
    private JMenuItem newProjectMenu;

    private EditorButton newButton;
    private EditorButton openButton;
    private EditorButton saveButton;
    private EditorButton saveAllButton;
    
    private EditorButton cutButton;
    private EditorButton copyButton;
    private EditorButton pasteButton;
    private EditorButton deleteButton;
    
    private EditorButton undoButton;
    private EditorButton redoButton;
    
    private EditorButton pencilButton;
    private EditorButton rectangleButton;
    private EditorButton ellipseButton;
    private EditorButton bucketButton;
    
    private EditorButton zoomInButton;
    private EditorButton zoomOutButton;
    
    private EditorButton runButton;
    private EditorButton stopButton;
    
    private EditorButton helpButton;

    public MainToolBar(MainWindow mainWindow)
    {
        super();

        this.parent = mainWindow;

        this.setFloatable(true);

        popupMenu = new JPopupMenu();
        newAnimationMenu = new JMenuItem("Animation");
        newProjectMenu = new JMenuItem("Project");

        popupMenu.add(newAnimationMenu);
        popupMenu.add(newProjectMenu);

        newButton = new EditorButton();
        newButton.setIcon(new ImageIcon(getClass().getResource("/rpgtoolkit/editor/resources/new.png")));

        openButton = new EditorButton();
        openButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/open.png")));

        saveButton = new EditorButton();
        saveButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/save.png")));
        
        saveAllButton = new EditorButton();
        saveAllButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/save-all.png")));
        
        cutButton = new EditorButton();
        cutButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/cut.png")));
        
        copyButton = new EditorButton();
        copyButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/copy.png")));
        
        pasteButton = new EditorButton();
        pasteButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/paste.png")));
        
        deleteButton = new EditorButton();
        deleteButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/delete.png")));
        
        undoButton = new EditorButton();
        undoButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/undo.png")));
        
        redoButton = new EditorButton();
        redoButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/redo.png"))); 
        
        pencilButton = new EditorButton();
        pencilButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/pencil.png")));
        
        rectangleButton = new EditorButton();
        rectangleButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/shape-rectangle.png")));
        
        ellipseButton = new EditorButton();
        ellipseButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/shape-ellipse.png")));
        
        bucketButton = new EditorButton();
        bucketButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/bucket.png")));
        
        zoomInButton = new EditorButton();
        zoomInButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/zoom-in.png")));
        
        zoomOutButton = new EditorButton();
        zoomOutButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/zoom-out.png")));

        runButton = new EditorButton();
        runButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/run.png")));
        runButton.setEnabled(false);
        
        stopButton = new EditorButton();
        stopButton.setIcon(new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/stop.png")));
        
        helpButton = new EditorButton();
        helpButton.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/help.png")));
        
        // Disable all the buttons for now
        newButton.setEnabled(false);
        openButton.setEnabled(false);
        saveButton.setEnabled(false);
        saveAllButton.setEnabled(false);
        cutButton.setEnabled(false);
        copyButton.setEnabled(false);
        pasteButton.setEnabled(false);
        deleteButton.setEnabled(false);
        undoButton.setEnabled(false);
        redoButton.setEnabled(false);
        pencilButton.setEnabled(false);
        rectangleButton.setEnabled(false);
        ellipseButton.setEnabled(false);
        bucketButton.setEnabled(false);
        zoomInButton.setEnabled(false);
        zoomOutButton.setEnabled(false);
        runButton.setEnabled(false);
        stopButton.setEnabled(false);
        helpButton.setEnabled(false);
        
        this.add(newButton);
        this.add(openButton);
        this.add(saveButton);
        this.add(saveAllButton);
        this.addSeparator();
        this.add(cutButton);
        this.add(copyButton);
        this.add(pasteButton);
        this.add(deleteButton);
        this.addSeparator();
        this.add(undoButton);
        this.add(redoButton);
        this.addSeparator();
        this.add(pencilButton);
        this.add(rectangleButton);
        this.add(ellipseButton);
        this.add(bucketButton);
        this.addSeparator();
        this.add(zoomInButton);
        this.add(zoomOutButton);
        this.addSeparator();
        this.add(runButton);
        this.add(stopButton);
        this.addSeparator();
        this.add(helpButton);
    }

    public void enableRun()
    {
        runButton.setEnabled(!runButton.isEnabled());
    }

}
