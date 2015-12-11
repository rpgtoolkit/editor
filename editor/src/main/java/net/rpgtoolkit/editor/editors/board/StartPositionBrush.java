/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import net.rpgtoolkit.common.assets.Board;

/**
 *
 * 
 * @author Joshua Michael Daly
 */
public class StartPositionBrush  extends AbstractBrush  {

    @Override
    public Shape getShape() {
        return getBounds();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(0, 0, 1, 1);
    }

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
      Board board = affectedContainer.getLayer(initialLayer).getLayer().getBoard();
      board.setStartingPositionX(x);
      board.setStartingPositionY(y);
      board.fireBoardChanged();
      
      return null;
  }

    @Override
    public boolean equals(Brush brush) {
        return brush instanceof StartPositionBrush;
    }
    
}
