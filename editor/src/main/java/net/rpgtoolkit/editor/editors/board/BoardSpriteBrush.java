/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.common.assets.BoardSprite;

/**
 *
 * @author Joshua Michael Daly
 */
public class BoardSpriteBrush extends AbstractBrush {

  private BoardSprite boardSprite;

  /**
   *
   */
  public BoardSpriteBrush() {
    boardSprite = new BoardSprite();
  }

  /**
   *
   *
   * @return
   */
  @Override
  public Shape getShape() {
    return getBounds();
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
  public BoardSprite getBoardSprite() {
    return boardSprite;
  }

  /**
   *
   *
   * @param boardSprite
   */
  public void setBoardSprite(BoardSprite boardSprite) {
    this.boardSprite = boardSprite;
  }

  /**
   *
   *
   * @param g2d
   * @param view
   */
  @Override
  public void drawPreview(Graphics2D g2d, AbstractBoardView view) {

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
    super.doPaint(x, y, selection);

    BoardLayerView boardLayerView = affectedContainer.getLayer(
            initialLayer);

    if (boardLayerView != null) {
      boardSprite = new BoardSprite();
      boardSprite.setX(x);
      boardSprite.setY(y);
      boardSprite.setLayer(initialLayer);

      Board board = boardLayerView.getLayer().getBoard();
      board.addSprite(boardSprite);

      Rectangle shapeBounds = getBounds();
      int centerX = x - shapeBounds.width / 2;
      int centerY = y - shapeBounds.height / 2;

      return new Rectangle(
              centerX, centerY, shapeBounds.width, shapeBounds.height);
    } else {
      return null;
    }
  }

  /**
   *
   *
   * @param brush
   * @return
   */
  @Override
  public boolean equals(Brush brush) {
    return brush instanceof BoardSpriteBrush
            && ((BoardSpriteBrush) brush).boardSprite.equals(boardSprite);
  }
  
}
