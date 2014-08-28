package rpgtoolkit.editor.board;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import rpgtoolkit.editor.board.tool.AbstractBrush;
import rpgtoolkit.editor.board.tool.BucketBrush;
import rpgtoolkit.editor.board.tool.SelectionBrush;
import rpgtoolkit.editor.board.tool.ShapeBrush;
import rpgtoolkit.editor.board.tool.VectorBrush;
import rpgtoolkit.editor.board.types.BoardVector;
import rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class BoardMouseAdapter extends MouseAdapter
{

    private Point origin;
    private final BoardEditor editor;
    private BoardVector lastSelectedVector;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BoardMouseAdapter(BoardEditor boardEditor)
    {
        this.editor = boardEditor;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        if (this.editor.boardView.getCurrentSelectedLayer() != null)
        {
            AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();

            if (e.getButton() == MouseEvent.BUTTON1)
            {
                this.doMouseButton1Pressed(e, brush);
            }
            else if (e.getButton() == MouseEvent.BUTTON2)
            {
                this.doMouseButton2Pressed(e, brush);
            }
            else if (e.getButton() == MouseEvent.BUTTON3)
            {
                this.doMouseButton3Pressed(e, brush);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (this.editor.boardView.getCurrentSelectedLayer() != null)
        {
            AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
            this.doMouseButton1Dragged(e, brush);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        this.editor.cursorTileLocation = this.editor.boardView.getTileCoordinates(
                (int) (e.getX() / this.editor.boardView.getZoom()),
                (int) (e.getY() / this.editor.boardView.getZoom()));
        this.editor.cursorLocation = new Point(e.getX(), e.getY());
        this.editor.boardView.repaint();
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void doMouseButton1Pressed(MouseEvent e, AbstractBrush brush)
    {
        Rectangle bucketSelection = null;
        Point point = this.editor.boardView.getTileCoordinates(
                (int) (e.getX() / this.editor.boardView.getZoom()),
                (int) (e.getY() / this.editor.boardView.getZoom()));

        if (brush instanceof SelectionBrush)
        {
            this.origin = point;
            this.editor.setSelection(new Rectangle(
                    this.origin.x, this.origin.y,
                    0, 0));
            this.editor.selectedTiles = this.editor.
                    createTileLayerFromRegion(this.editor.selection);
        }
        else if (brush instanceof ShapeBrush && this.editor.selection != null)
        {
            this.editor.selection = null;
        }
        else if (brush instanceof BucketBrush && this.editor.selection != null)
        {
            // To compensate for the fact that the selection
            // is 1 size too small in both width and height.
            // Bit of a hack really.
            this.editor.selection.width++;
            this.editor.selection.height++;

            if (this.editor.selection.contains(point))
            {
                bucketSelection = (Rectangle) this.editor.selection.clone();
            }

            // Revert back to original dimensions.
            this.editor.selection.width--;
            this.editor.selection.height--;
        }
        else if (brush instanceof VectorBrush)
        {
            // Because vectors are coordinates are pixel based.
            point = new Point(e.getX(), e.getY());
        }

        this.editor.doPaint(brush, point, bucketSelection);
    }

    private void doMouseButton2Pressed(MouseEvent e, AbstractBrush brush)
    {
        if (brush instanceof VectorBrush)
        {
            VectorBrush vectorBrush = (VectorBrush) brush;

            if (vectorBrush.isDrawing())
            {
                vectorBrush.finishVector();
            }

            this.editor.boardView.getCurrentSelectedLayer().getLayer().
                    removeVectorAt(e.getX(), e.getY());
        }
    }

    private void doMouseButton3Pressed(MouseEvent e, AbstractBrush brush)
    {
        if (brush instanceof VectorBrush)
        {
            // We are drawing a vector, so lets finish it.
            if (((VectorBrush) brush).isDrawing())
            {
               ((VectorBrush) brush).finishVector();
            }
            else // We want to select a vector.
            {
                this.selectVector(this.editor.boardView.getCurrentSelectedLayer()
                        .getLayer().findVectorAt(e.getX(), e.getY()));
            }
        }
    }

    private void doMouseButton1Dragged(MouseEvent e, AbstractBrush brush)
    {
        Point point = this.editor.boardView.getTileCoordinates(
                (int) (e.getX() / this.editor.boardView.getZoom()),
                (int) (e.getY() / this.editor.boardView.getZoom()));
        this.editor.cursorTileLocation = point;
        this.editor.cursorLocation = new Point(e.getX(), e.getY());
        
        if (brush instanceof VectorBrush)
        {
            return;
        }
        else if (brush instanceof SelectionBrush)
        {
            Rectangle select = new Rectangle(this.origin.x, this.origin.y, 0, 0);
            select.add(point);

            if (!select.equals(this.editor.selection))
            {
                this.editor.setSelection(select);
            }

            this.editor.selectedTiles = this.editor.
                    createTileLayerFromRegion(this.editor.selection);
        }
        else if (brush instanceof ShapeBrush && this.editor.selection != null)
        {
            this.editor.selection = null;
        }

        this.editor.doPaint(brush, point, null);
    }

    private void selectVector(BoardVector vector)
    {
        if (vector != null)
        {
            vector.setSelected(true);

            if (this.lastSelectedVector != null)
            {
                this.lastSelectedVector.setSelected(false);
            }

            this.lastSelectedVector = vector;
            MainWindow.getInstance().getPropertiesPanel().setModel(vector);
            
            this.editor.boardView.repaint();
        }
        else if (this.lastSelectedVector != null)
        {
            this.lastSelectedVector.setSelected(false);
            this.lastSelectedVector = null;
            
            this.editor.boardView.repaint();
        }
    }
}
