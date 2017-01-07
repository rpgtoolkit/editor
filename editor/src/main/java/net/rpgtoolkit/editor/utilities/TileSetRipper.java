/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.utilities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.common.assets.TileSet;

/**
 *
 * @author Joshua Michael Daly
 */
public class TileSetRipper {

  public static TileSet rip(BufferedImage source, int tileWidth, int tileHeight) {
    TileSet tileSet = new TileSet(null);

    int sourceWidth = source.getWidth();
    int sourceHeight = source.getHeight();

    int rows = sourceHeight / tileHeight;
    int columns = sourceWidth / tileWidth;

    Tile tile;
    BufferedImage subImage;
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < columns; y++) {
        subImage = new BufferedImage(tileWidth, tileHeight, source.getType());

        Graphics2D gr = subImage.createGraphics();
        gr.drawImage(source, 0, 0, tileWidth, tileHeight, tileWidth * y, tileHeight * x,
                tileWidth * y + tileWidth, tileHeight * x + tileHeight, null);
        gr.dispose();

        tile = new Tile();
        tile.setRect(subImage.getRaster());
        tileSet.addTile(tile);
      }
    }

    return tileSet;
  }
}
