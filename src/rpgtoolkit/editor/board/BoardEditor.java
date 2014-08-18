package rpgtoolkit.editor.board;

import java.io.File;
import javax.swing.*;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.editor.main.MainWindow;

/**
 * 
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class BoardEditor extends JInternalFrame
{
    /*
     * *************************************************************************
     * Class Members
     * *************************************************************************
     */
    
    private MainWindow parent;

    private JScrollPane scrollPane;
    
    private BoardView2D boardView;
    private Board board;

    /*
     * *************************************************************************
     * Constructors
     * *************************************************************************
     */
    
    /**
     * Default Constructor.
     */
    public BoardEditor()
    {
        
    }

    /**
     * This constructor is used when opening an existing board, it does not 
     * make the window visible.
     * 
     * @param parent This BoardEditors parent window.
     * @param fileName The board file that to open.
     */
    public BoardEditor(MainWindow parent, File fileName)
    {
        super("Board Viewer", true, true, true, true);
    
        this.parent = parent;
        this.board = new Board(fileName);
        this.boardView = new BoardView2D(this, board);
        
        this.scrollPane = new JScrollPane(this.boardView);
        this.scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        this.scrollPane.setVerticalScrollBarPolicy
                (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        // Randomly create a Board Layer frame here for testing
        LayerFrame layerFrame = new LayerFrame(this.boardView);
        layerFrame.setVisible(true);
        layerFrame.pack();
        
        this.parent.getDesktopPane().add(layerFrame);
        
        this.setTitle("Viewing " + fileName.getAbsolutePath());
        this.add(scrollPane);
        this.pack();
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    
    /**
     * Zoom in on the board view.
     */
    public void zoomIn()
    {
        this.boardView.zoomIn();
        this.scrollPane.getViewport().revalidate();
    }
    
    /**
     * Zoom out on the board view.
     */
    public void zoomOut()
    {
        this.boardView.zoomOut();
        this.scrollPane.getViewport().revalidate();
    }
    
    /**
     * Toggle the grid on the board view.
     * @param isVisible Is it visible?
     */
    public void toggleGrid(boolean isVisible)
    {
        boardView.setShowGrid(isVisible);
    }
    
    /**
     * Toggle the coordinates on the board view.
     * @param isVisible Is it visible?
     */
    public void toogleCoordinates(boolean isVisible)
    {
        boardView.setShowCoordinates(isVisible);
    }
}
