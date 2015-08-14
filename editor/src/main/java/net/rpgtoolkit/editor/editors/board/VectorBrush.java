/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class VectorBrush extends AbstractBrush {

    protected BoardVector boardVector;
    protected boolean stillDrawing;
    protected Color previewColor;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    /**
     *
     */
    public VectorBrush() {
        this.boardVector = new BoardVector();
        this.stillDrawing = false;
        this.previewColor = Color.WHITE;
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    /**
     *
     *
     * @return
     */
    @Override
    public Shape getShape() {
        return this.getBounds();
    }

    /**
     *
     *
     * @return
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(0, 0, 1, 1);
    }

    /**
     *
     *
     * @return
     */
    public BoardVector getBoardVector() {
        return this.boardVector;
    }

    /**
     *
     *
     * @param vector
     */
    public void setBoardVector(BoardVector vector) {
        this.boardVector = vector;
    }

    /**
     *
     *
     * @return
     */
    public boolean isDrawing() {
        return this.stillDrawing;
    }

    /**
     *
     *
     * @param isDrawing
     */
    public void setDrawing(boolean isDrawing) {
        this.stillDrawing = isDrawing;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    /**
     *
     *
     * @param g2d
     * @param view
     */
    @Override
    public void drawPreview(Graphics2D g2d, AbstractBoardView view) {
        if (this.boardVector.getPoints().size() < 1) {
            return;
        }

        Point cursor = view.getBoardEditor().getCursorLocation();
        Point lastVectorPoint = this.boardVector.getPoints()
                .get(this.boardVector.getPoints().size() - 1);

        int[] coordinates = {cursor.x, cursor.y};

        if (MainWindow.getInstance().isSnapToGrid()) {
            coordinates = MainWindow.getInstance().getCurrentBoardEditor().
                    calculateSnapCoordinates(cursor.x, cursor.y);
        }

        g2d.setColor(this.previewColor);
        g2d.drawLine(lastVectorPoint.x, lastVectorPoint.y, coordinates[0], coordinates[1]);
    }

    /**
     *
     *
     * @param brush
     * @return
     */
    @Override
    public boolean equals(Brush brush) {
        return brush instanceof VectorBrush
                && ((VectorBrush) brush).boardVector.equals(this.boardVector);
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
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception {
        BoardLayerView boardLayerView = this.affectedContainer.getLayer(
                this.initialLayer);

        super.doPaint(x, y, selection);

        if (boardLayerView != null) {
            if (!this.stillDrawing) {
                this.stillDrawing = true;
                this.boardVector = new BoardVector();
                this.boardVector.setLayer(this.initialLayer);

                this.affectedContainer.getLayer(this.initialLayer).
                        getLayer().getVectors().add(this.boardVector);
            }

            if (MainWindow.getInstance().isSnapToGrid()) {
                MainWindow.getInstance().getCurrentBoardEditor().
                        calculateSnapCoordinates(x, y);
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
    public void finish() {
        if (this.boardVector.getPointCount() < 2) {
            this.affectedContainer.getLayer(initialLayer).getLayer()
                    .getVectors().remove(this.boardVector);
        }

        this.boardVector = new BoardVector();
        this.stillDrawing = false;
    }

    /*
     * *************************************************************************
     * Protected Methods
     * *************************************************************************
     */
    /**
     *
     *
     * @param x
     * @param y
     * @param selection
     * @return
     * @throws Exception
     */
    protected Rectangle callRootPaint(int x, int y, Rectangle selection)
            throws Exception {
        // This is bad design, and is monkeying around with the inheritance
        // model by exposing access of parent class of this class to a child.
        // Should implement composition of inheritance in this case.
        return super.doPaint(x, y, selection);
    }

}
