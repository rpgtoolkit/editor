package rpgtoolkit.editor.board;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.editor.board.brush.AbstractBrush;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.editor.main.ToolkitEditorWindow;

/**
 *
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class BoardEditor extends ToolkitEditorWindow
{

    private MainWindow parentWindow;

    private JScrollPane scrollPane;

    private BoardView2D boardView;
    private Board board;

    private BoardMouseAdapter boardMouseAdapter;

    private Point cursorLocation;

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
     * This constructor is used when opening an existing board, it does not make
     * the window visible.
     *
     * @param parent This BoardEditors parent window.
     * @param fileName The board file that to open.
     */
    public BoardEditor(MainWindow parent, File fileName)
    {
        super("Board Viewer", true, true, true, true);

        this.boardMouseAdapter = new BoardMouseAdapter();

        this.parentWindow = parent;
        this.board = new Board(fileName);
        this.boardView = new BoardView2D(this, board);
        this.boardView.addMouseListener(this.boardMouseAdapter);
        this.boardView.addMouseMotionListener(this.boardMouseAdapter);

        this.scrollPane = new JScrollPane(this.boardView);
        this.scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.cursorLocation = new Point(0, 0);

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

    public Point getCursorLocation()
    {
        return this.cursorLocation;
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

    @Override
    public boolean save()
    {
        return this.board.save();
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void useBrush(Point point)
    {
        try
        {
            AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();

            if (brush == null)
            {
                return;
            }

            brush.startPaint(boardView, boardView.
                    getCurrentSelectedLayer().getLayer().getNumber());
            brush.doPaint(point.x, point.y);
            brush.endPaint();
        }
        catch (Exception ex)
        {
            Logger.getLogger(BoardEditor.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /*
     * *************************************************************************
     * Private Inner Classes 
     * *************************************************************************
     */
    private class BoardMouseAdapter extends MouseAdapter
    {
        /*
         * *********************************************************************
         * Public Constructors
         * *********************************************************************
         */

        public BoardMouseAdapter()
        {

        }

        /*
         * *********************************************************************
         * Public Methods
         * *********************************************************************
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (boardView.getCurrentSelectedLayer() != null)
            {
                Point point = boardView.getTileCoordinates(e.getX(), e.getY());

                useBrush(point);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if (boardView.getCurrentSelectedLayer() != null)
            {
                Point point = boardView.getTileCoordinates(e.getX(), e.getY());
                cursorLocation = point;

                useBrush(point);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            cursorLocation = boardView.getTileCoordinates(e.getX(), e.getY());
            boardView.repaint();
        }
    }

}
