/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Rectangle;
import net.rpgtoolkit.common.assets.Tile;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class SelectionBrush extends CustomBrush {

  /**
   *
   *
   * @param tiles
   */
  public SelectionBrush(Tile[][] tiles) {
    super(tiles);
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
    // Do nothing on paint. Perhaps collect the selected tiles here?
    return null;
  }
}
