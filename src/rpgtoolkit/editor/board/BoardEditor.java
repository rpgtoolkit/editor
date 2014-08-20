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
    
    private MainWindow parentWindow;

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
    
        this.parentWindow = parent;
        this.board = new Board(fileName);
        this.boardView = new BoardView2D(this, board);
        
        this.scrollPane = new JScrollPane(this.boardView);
        this.scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        this.scrollPane.setVerticalScrollBarPolicy
                (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.setTitle("Viewing " + fileName.getAbsolutePath());
        this.add(scrollPane);
        this.pack();
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public MainWindow getParentWindow()
    {
        return parentWindow;
    }

    public void setParentWindow(MainWindow parent)
    {
        this.parentWindow = parent;
    }

    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane)
    {
        this.scrollPane = scrollPane;
    }

    public BoardView2D getBoardView()
    {
        return boardView;
    }

    public void setBoardView(BoardView2D boardView)
    {
        this.boardView = boardView;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
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
