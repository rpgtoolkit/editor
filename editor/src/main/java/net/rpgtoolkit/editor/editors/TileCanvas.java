/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.common.assets.TilePixelOutOfRangeException;

/**
 * TileCanvas class is responsible for managing the drawing and editing of a tile on screen. The
 * canvas is split up into 32x32 "pixels" where each pixel is scaled correctly to match the screen
 * resolution
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 * @version 0.1
 */
public class TileCanvas extends JPanel implements MouseListener, MouseMotionListener {

  private Tile tile;
  private int pixelWidth = 10;
  private int pixelHeight = 10;
  private boolean hasChanged = false;
  private BufferedImage transpImage;

  /**
   *
   * @param tile
   */
  public TileCanvas(Tile tile) {
    setSize(320, 320);
    this.tile = tile;
    addMouseListener(this);
    addMouseMotionListener(this);
    try {
      transpImage = ImageIO.read(getClass().getResourceAsStream(
              "rpgtoolkit/resources/transp.png"));
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  /**
   *
   * @param tile
   */
  public void changeTile(Tile tile) {
    this.tile = tile;
    hasChanged = false;
    repaint();
  }

  /**
   *
   * @param scale
   */
  public void setScale(int scale) {
    pixelWidth = scale;
    pixelHeight = scale;
  }

  /**
   *
   * @return
   */
  public boolean hasChanged() {
    return hasChanged;
  }

  /**
   *
   */
  public void clearChangedFlag() // Use this after saving!
  {
    hasChanged = false;
  }

  private void updateTile(int x, int y) {
    try {
      // Work out which pixel we clicked
      int pixelRow = x / pixelWidth;
      int pixelCol = y / pixelHeight;

      Color c = new Color(255, 255, 255, 0);
      tile.setPixel(pixelRow, pixelCol, c);

      repaint();
    } catch (TilePixelOutOfRangeException e) {
      System.out.println(e.toString());
    }
  }

  /**
   *
   * @param g
   */
  @Override
  public void paint(Graphics g) {
    try {
      g.drawImage(transpImage, 0, 0, null);

      // Draw the tile
      for (int x = 0; x < 32; x++) {
        for (int y = 0; y < 32; y++) {
          g.setColor(tile.getPixel(x, y));
          g.fillRect((x * pixelWidth + 1), (y * pixelHeight + 1),
                  pixelWidth, pixelHeight);

          if (pixelWidth > 5) {
            g.setColor(Color.BLACK);
            g.drawRect((x * pixelWidth + 1), (y * pixelHeight + 1),
                    pixelWidth, pixelHeight);
          }
        }

      }
    } catch (TilePixelOutOfRangeException e) {
      System.out.println(e.toString());
    }
  }

  // TODO: Add mouse adpater here.
  
  /**
   *
   * @param e
   */
  @Override
    public void mouseClicked(MouseEvent e) {
  }

  /**
   *
   * @param e
   */
  @Override
  public void mousePressed(MouseEvent e) {
    updateTile(e.getX(), e.getY());
  }

  /**
   *
   * @param e
   */
  @Override
  public void mouseReleased(MouseEvent e) {

  }

  /**
   *
   * @param e
   */
  @Override
  public void mouseEntered(MouseEvent e) {

  }

  /**
   *
   * @param e
   */
  @Override
  public void mouseExited(MouseEvent e) {

  }

  /**
   *
   * @param e
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    updateTile(e.getX(), e.getY());
    hasChanged = true;
  }

  /**
   *
   * @param e
   */
  @Override
  public void mouseMoved(MouseEvent e) {

  }
}
