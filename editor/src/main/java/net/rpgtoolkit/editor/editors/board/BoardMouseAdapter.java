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
    /**
     * 
     * 
     * @param boardEditor 
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
    /**
     * 
     * 
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        if (this.editor.getBoardView().getCurrentSelectedLayer() != null)
        {
            AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
            int button = e.getButton();

            if (button == MouseEvent.BUTTON1)
            {
                this.doMouseButton1Pressed(e, brush);
            }
            else if (button == MouseEvent.BUTTON2)
            {
                this.doMouseButton2Pressed(e, brush);
            }
            else if (button == MouseEvent.BUTTON3)
            {
                this.doMouseButton3Pressed(e, brush);
            }
        }
    }

    /**
     * 
     * 
     * @param e 
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (this.editor.getBoardView().getCurrentSelectedLayer() != null)
        {
            AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
            this.doMouseButton1Dragged(e, brush);
        }
    }

    /**
     * 
     * 
     * @param e 
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        this.editor.setCursorTileLocation(this.editor.getBoardView().
                getTileCoordinates(
                (int) (e.getX() / this.editor.getBoardView().getZoom()),
                (int) (e.getY() / this.editor.getBoardView().getZoom())));
        this.editor.setCursorLocation(new Point(e.getX(), e.getY()));
        this.editor.getBoardView().repaint();
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
        Point point = this.editor.getBoardView().getTileCoordinates(
                (int) (e.getX() / this.editor.getBoardView().getZoom()),
                (int) (e.getY() / this.editor.getBoardView().getZoom()));

        if (brush instanceof SelectionBrush)
        {
            this.origin = point;
            this.editor.setSelection(new Rectangle(
                    this.origin.x, this.origin.y,
                    0, 0));
            this.editor.setSelectedTiles(this.editor.
                    createTileLayerFromRegion(this.editor.getSelection()));
        }
        else if (brush instanceof ShapeBrush && this.editor.getSelection() != null)
        {
            this.editor.setSelection(null);
        }
        else if (brush instanceof BucketBrush && this.editor.getSelection() != null)
        {
            // To compensate for the fact that the selection
            // is 1 size too small in both width and height.
            // Bit of a hack really.
            this.editor.getSelection().width++;
            this.editor.getSelection().height++;

            if (this.editor.getSelection().contains(point))
            {
                bucketSelection = (Rectangle) this.editor.getSelection().clone();
            }

            // Revert back to original dimensions.
            this.editor.getSelection().width--;
            this.editor.getSelection().height--;
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
            
            BoardProgram program = this.editor.getBoardView().getCurrentSelectedLayer()
                    .getLayer().removeProgramAt(e.getX(), e.getY());
            
            if (program != null)
            {
                if (program == editor.getSelectedObject())
                {
                    editor.getSelectedObject().setSelectedState(false);
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

            BoardVector vector = this.editor.getBoardView().getCurrentSelectedLayer()
                    .getLayer().removeVectorAt(e.getX(), e.getY());

            if (vector != null)
            {
                if (vector == editor.getSelectedObject())
                {
                    editor.getSelectedObject().setSelectedState(false);
                    editor.setSelectedObject(null);
                }
            }
        }
        else if (brush instanceof SpriteBrush)
        {
            this.editor.getBoardView().getCurrentSelectedLayer().getLayer()
                    .removeSpriteAt(
                            e.getX() / MainWindow.TILE_SIZE + 1, 
                            e.getY() / MainWindow.TILE_SIZE + 1);
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
                this.selectProgram(this.editor.getBoardView().getCurrentSelectedLayer()
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
                this.selectVector(this.editor.getBoardView().getCurrentSelectedLayer()
                        .getLayer().findVectorAt(e.getX(), e.getY()));
            }
        }
        else if (brush instanceof SpriteBrush)
        {
            this.selectSprite(this.editor.getBoardView().getCurrentSelectedLayer().getLayer()
                    .findSpriteAt(
                            e.getX() / MainWindow.TILE_SIZE + 1, 
                            e.getY() / MainWindow.TILE_SIZE + 1));
        }
    }

    /**
     * 
     * 
     * @param e
     * @param brush 
     */
    private void doMouseButton1Dragged(MouseEvent e, AbstractBrush brush)
    {
        Point point = this.editor.getBoardView().getTileCoordinates(
                (int) (e.getX() / this.editor.getBoardView().getZoom()),
                (int) (e.getY() / this.editor.getBoardView().getZoom()));
        this.editor.setCursorTileLocation(point);
        this.editor.setCursorLocation(new Point(e.getX(), e.getY()));

        if (brush instanceof VectorBrush)
        {
            return;
        }
        else if (brush instanceof SelectionBrush)
        {
            Rectangle select = new Rectangle(this.origin.x, this.origin.y, 0, 0);
            select.add(point);

            if (!select.equals(this.editor.getSelection()))
            {
                this.editor.setSelection(select);
            }

            this.editor.setSelectedTiles(this.editor.
                    createTileLayerFromRegion(this.editor.getSelection()));
        }
        else if (brush instanceof ShapeBrush && this.editor.getSelection() != null)
        {
            this.editor.setSelection(null);
        }

        this.editor.doPaint(brush, point, null);
    }

    /**
     * 
     * 
     * @param vector 
     */
    private void selectVector(BoardVector vector)
    {
        if (vector != null)
        {
            vector.setSelectedState(true);

            if (editor.getSelectedObject() != null)
            {
                editor.getSelectedObject().setSelectedState(false);
            }

            editor.setSelectedObject(vector);
        }
        else if (editor.getSelectedObject() != null)
        {
            editor.getSelectedObject().setSelectedState(false);
            editor.setSelectedObject(null);
        }
    }
    
    /**
     * 
     * 
     * @param program 
     */
    private void selectProgram(BoardProgram program)
    {
        if (program != null)
        {
            program.getVector().setSelectedState(true);

            if (editor.getSelectedObject() != null)
            {
                editor.getSelectedObject().setSelectedState(false);
            }

            editor.setSelectedObject(program);
        }
        else if (editor.getSelectedObject() != null)
        {
            editor.getSelectedObject().setSelectedState(false);
            editor.setSelectedObject(null);
        }
    }
    
    /**
     * 
     * 
     * @param sprite 
     */
    private void selectSprite(BoardSprite sprite)
    {
        if (sprite != null)
        {
            sprite.setSelectedState(true);
            
            if (editor.getSelectedObject() != null)
            {
                editor.getSelectedObject().setSelectedState(false);
            }
            
            editor.setSelectedObject(sprite);
        }
        else if (editor.getSelectedObject() != null)
        {
            editor.getSelectedObject().setSelectedState(false);
            editor.setSelectedObject(null);
        }
    }
    
}
