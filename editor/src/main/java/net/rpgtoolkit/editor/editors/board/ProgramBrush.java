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
import net.rpgtoolkit.editor.MainWindow;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class ProgramBrush extends VectorBrush {

  private BoardProgram boardProgram;

  /**
   *
   */
  public ProgramBrush() {
    super();

    previewColor = Color.YELLOW;
    boardProgram = new BoardProgram();
    boardProgram.setVector(boardVector);
  }

  /**
   *
   * @return
   */
  public BoardProgram getBoardProgram() {
    return boardProgram;
  }

  /**
   *
   * @param boardProgram
   */
  public void setBoardProgram(BoardProgram boardProgram) {
    this.boardProgram = boardProgram;
  }

  /**
   *
   *
   * @param brush
   * @return
   */
  @Override
  public boolean equals(Brush brush) {
    return brush instanceof ProgramBrush
            && ((ProgramBrush) brush).boardProgram.equals(boardProgram);
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
    BoardLayerView boardLayerView = affectedContainer.getLayer(
            initialLayer);

    callRootPaint(x, y, selection);

    if (boardLayerView != null) {
      if (!stillDrawing) {
        stillDrawing = true;
        boardVector = new BoardVector();
        boardProgram = new BoardProgram();
        boardProgram.setVector(boardVector);
        boardProgram.setLayer(initialLayer);

        affectedContainer.getLayer(initialLayer).
                getLayer().getPrograms().add(boardProgram);
      }

      int[] coordinates = {x, y};

      if (MainWindow.getInstance().isSnapToGrid()) {
        coordinates = MainWindow.getInstance().getCurrentBoardEditor().
                calculateSnapCoordinates(x, y);
      }

      boardVector.addPoint(coordinates[0], coordinates[1]);
      boardLayerView.getLayer().getBoard().fireBoardChanged();
    }

    return null;
  }

  /**
   *
   */
  @Override
  public void finish() {
    if (boardVector.getPointCount() < 2) {
      affectedContainer.getLayer(initialLayer).getLayer()
              .getPrograms().remove(boardProgram);
    }

    boardVector = new BoardVector();
    boardProgram = new BoardProgram();
    boardProgram.setVector(boardVector);

    stillDrawing = false;
  }
  
}
