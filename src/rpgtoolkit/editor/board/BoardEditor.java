package rpgtoolkit.editor.board;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.editor.board.tool.AbstractBrush;
import rpgtoolkit.editor.board.tool.BucketBrush;
import rpgtoolkit.editor.board.tool.SelectionBrush;
import rpgtoolkit.editor.board.tool.ShapeBrush;
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
    private Rectangle selection;
    
    private Tile[][] selectedTiles;

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
    
    public Rectangle getSelection()
    {
        return this.selection;
    }
    
    public Tile[][] getSelectedTiles()
    {
        return this.selectedTiles;
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
     * Private Getters and Setters
     * *************************************************************************
     */
    private void setSelection(Rectangle rectangle)
    {
        this.selection = rectangle;
        this.boardView.repaint();
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void doPaint(AbstractBrush brush, Point point, Rectangle selection)
    {
        try
        {
            if (brush == null)
            {
                return;
            }

            brush.startPaint(boardView, boardView.
                    getCurrentSelectedLayer().getLayer().getNumber());
            brush.doPaint(point.x, point.y, selection);
            brush.endPaint();
        }
        catch (Exception ex)
        {
            Logger.getLogger(BoardEditor.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    
    private Tile[][] createTileLayerFromRegion(Rectangle rectangle)
    {
        Tile[][] tiles = new Tile[rectangle.width + 1][rectangle.height + 1];
        
        for (int y = rectangle.y; y <= rectangle.y + rectangle.height; y++)
        {
            for (int x = rectangle.x; x <= rectangle.x + rectangle.width; x++)
            {
                tiles[x - rectangle.x][y - rectangle.y] = 
                        this.boardView.getCurrentSelectedLayer().
                                getLayer().getTileAt(x, y);
            }
        }
        
        return tiles;
    }

    /*
     * *************************************************************************
     * Private Inner Classes 
     * *************************************************************************
     */
    private class BoardMouseAdapter extends MouseAdapter
    {
        private Point origin;
        
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
        public void mousePressed(MouseEvent e)
        {
            if (boardView.getCurrentSelectedLayer() != null)
            {
                Point point = boardView.getTileCoordinates(e.getX(), e.getY());
                AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();

                if (brush instanceof SelectionBrush)
                {
                    this.origin = point;
                    setSelection(new Rectangle(
                            this.origin.x, this.origin.y, 
                            0, 0));
                    
                    selectedTiles = createTileLayerFromRegion(selection);
                }
                else
                {
                    Rectangle bucketSelection = null;
                    
                    if (brush instanceof ShapeBrush && selection != null)
                    {
                        selection = null;
                    }
                    else if (brush instanceof BucketBrush && selection != null)
                    {
                        // To compensate for the fact that the selection
                        // is 1 size too small in both width and height.
                        // Bit of a hack really.
                        selection.width++;
                        selection.height++;
                        
                        if (selection.contains(point))
                        {
                            bucketSelection = (Rectangle)selection.clone();
                        }
                        
                        // Revert back to original dimensions.
                        selection.width--;
                        selection.height--;
                    }
                    
                    doPaint(brush, point, bucketSelection);
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if (boardView.getCurrentSelectedLayer() != null)
            {
                Point point = boardView.getTileCoordinates(e.getX(), e.getY());
                AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
                cursorLocation = point;

                if (brush instanceof SelectionBrush)
                {
                    Rectangle select = new Rectangle(
                            this.origin.x, this.origin.y, 
                            0, 0);
                    select.add(point);

                    if (!select.equals(selection))
                    {
                        setSelection(select);
                    }
                    
                    selectedTiles = createTileLayerFromRegion(selection);
                }
                else
                {
                    if (brush instanceof ShapeBrush && selection != null)
                    {
                        selection = null;
                    }
                    
                    doPaint(brush, point, null);
                }
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
