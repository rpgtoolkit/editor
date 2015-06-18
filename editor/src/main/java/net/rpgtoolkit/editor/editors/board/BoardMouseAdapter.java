/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.rpgtoolkit.common.assets.BoardProgram;

import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class BoardMouseAdapter extends MouseAdapter
{

    private Point origin;
    private final BoardEditor editor;

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
    /**
     * Deals with the creation of an object on a board layer.
     * 
     * @param e
     * @param brush 
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
            // Because vectors and programs coordinates are pixel based.
            point = new Point(e.getX(), e.getY());
        }

        this.editor.doPaint(brush, point, bucketSelection);
    }

    /**
     * Deals with the deletion of an object on a board layer.
     * 
     * @param e
     * @param brush 
     */
    private void doMouseButton2Pressed(MouseEvent e, AbstractBrush brush)
    {
        if (brush instanceof ProgramBrush)
        {
            ProgramBrush programBrush = (ProgramBrush) brush;
            
            if (programBrush.isDrawing())
            {
                programBrush.finish();
            }
            
            BoardProgram program = this.editor.boardView.getCurrentSelectedLayer()
                    .getLayer().removeProgramAt(e.getX(), e.getY());
            
            if (program != null)
            {
                if (program == editor.getSelectedObject())
                {
                    editor.getSelectedObject().setSelected(false);
                    editor.setSelectedObject(null);
                }
            }
        }
        else if (brush instanceof VectorBrush)
        {
            VectorBrush vectorBrush = (VectorBrush) brush;

            if (vectorBrush.isDrawing())
            {
                vectorBrush.finish();
            }

            BoardVector vector = this.editor.boardView.getCurrentSelectedLayer()
                    .getLayer().removeVectorAt(e.getX(), e.getY());

            if (vector != null)
            {
                if (vector == editor.getSelectedObject())
                {
                    editor.getSelectedObject().setSelected(false);
                    editor.setSelectedObject(null);
                }
            }
        }
        else if (brush instanceof SpriteBrush)
        {
            this.editor.boardView.getCurrentSelectedLayer().getLayer()
                    .removeSpriteAt(
                            e.getX() / this.editor.board.getTileSet().getTileWidth(), 
                            e.getY() / this.editor.board.getTileSet().getTileHeight());
        }
    }

    /**
     * Deals with the selection of an object on a board layer
     * 
     * @param e
     * @param brush 
     */
    private void doMouseButton3Pressed(MouseEvent e, AbstractBrush brush)
    {
        
        if (brush instanceof ProgramBrush)
        {
            // We are drawing a vector, so lets finish it.
            if (((ProgramBrush) brush).isDrawing())
            {
                ((ProgramBrush) brush).finish();
            }
            else // We want to select a program.
            {
                this.selectProgram(this.editor.boardView.getCurrentSelectedLayer()
                        .getLayer().findProgramAt(e.getX(), e.getY()));
            }
        }
        else if (brush instanceof VectorBrush)
        {
            // We are drawing a vector, so lets finish it.
            if (((VectorBrush) brush).isDrawing())
            {
                ((VectorBrush) brush).finish();
            }
            else // We want to select a vector.
            {
                this.selectVector(this.editor.boardView.getCurrentSelectedLayer()
                        .getLayer().findVectorAt(e.getX(), e.getY()));
            }
        }
        else if (brush instanceof SpriteBrush)
        {
            this.selectSprite(this.editor.boardView.getCurrentSelectedLayer().getLayer()
                    .findSpriteAt(
                            e.getX() / this.editor.board.getTileSet().getTileWidth(), 
                            e.getY() / this.editor.board.getTileSet().getTileHeight()));
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

            if (editor.getSelectedObject() != null)
            {
                editor.getSelectedObject().setSelected(false);
            }

            editor.setSelectedObject(vector);
        }
        else if (editor.getSelectedObject() != null)
        {
            editor.getSelectedObject().setSelected(false);
            editor.setSelectedObject(null);
        }
    }
    
    private void selectProgram(BoardProgram program)
    {
        if (program != null)
        {
            program.getVector().setSelected(true);

            if (editor.getSelectedObject() != null)
            {
                editor.getSelectedObject().setSelected(false);
            }

            editor.setSelectedObject(program);
        }
        else if (editor.getSelectedObject() != null)
        {
            editor.getSelectedObject().setSelected(false);
            editor.setSelectedObject(null);
        }
    }
    
    private void selectSprite(BoardSprite sprite)
    {
        if (sprite != null)
        {
            sprite.setSelected(true);
            
            if (editor.getSelectedObject() != null)
            {
                editor.getSelectedObject().setSelected(false);
            }
            
            editor.setSelectedObject(sprite);
        }
        else if (editor.getSelectedObject() != null)
        {
            editor.getSelectedObject().setSelected(false);
            editor.setSelectedObject(null);
        }
    }
}
