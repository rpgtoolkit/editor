/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Color;
import java.awt.Rectangle;
import net.rpgtoolkit.common.assets.BoardProgram;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class ProgramBrush extends VectorBrush
{
    
    private BoardProgram boardProgram;
    
    /*
     * *************************************************************************
     * Constructors
     * *************************************************************************
     */
    public ProgramBrush()
    {
        super();
        
        this.previewColor = Color.YELLOW;
        this.boardProgram = new BoardProgram();
        this.boardProgram.setVector(this.boardVector);
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */

    public BoardProgram getBoardProgram()
    {
        return boardProgram;
    }

    public void setBoardProgram(BoardProgram boardProgram)
    {
        this.boardProgram = boardProgram;
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    /**
     * 
     * 
     * @param brush
     * @return 
     */
    @Override
    public boolean equals(Brush brush)
    {
        return brush instanceof ProgramBrush &&
                ((ProgramBrush) brush).boardProgram.equals(this.boardProgram);
    }
    
    /**
     * 
     * 
     * @param x
     * @param y
     * @param selection
     * @return
     * @throws Exception 
     */
    @Override
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception
    {
        BoardLayerView boardLayerView = this.affectedContainer.getLayer(
                this.initialLayer);
        
        this.callRootPaint(x, y, selection);
        
        if (boardLayerView != null)
        {
            if (!this.stillDrawing)
            {
                this.stillDrawing = true;
                this.boardVector = new BoardVector();
                this.boardProgram = new BoardProgram();
                this.boardProgram.setVector(this.boardVector);
                this.boardProgram.setLayer(this.initialLayer);

                this.affectedContainer.getLayer(this.initialLayer).
                        getLayer().getPrograms().add(this.boardProgram);
            }
            
            int[] coordinates = {x, y};

            if (MainWindow.getInstance().isSnapToGrid()) {
                coordinates = MainWindow.getInstance().getCurrentBoardEditor().
                        calculateSnapCoordinates(x, y);
            }

            this.boardVector.addPoint(coordinates[0], coordinates[1]);
            boardLayerView.getLayer().getBoard().fireBoardChanged();
        }
        
        return null;
    }
    
    /**
     * 
     */
    @Override
    public void finish()
    {
        if (this.boardVector.getPointCount() < 2)
        {
            this.affectedContainer.getLayer(initialLayer).getLayer()
                    .getPrograms().remove(this.boardProgram);
        }

        this.boardVector = new BoardVector();
        this.boardProgram = new BoardProgram();
        this.boardProgram.setVector(this.boardVector);
        
        this.stillDrawing = false;
    }
    
}
