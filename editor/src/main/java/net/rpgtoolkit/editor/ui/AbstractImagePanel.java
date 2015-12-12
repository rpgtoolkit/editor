/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractImagePanel extends JPanel implements MouseListener {

  protected File file;
  protected Dimension dimension;
  protected LinkedList<BufferedImage> bufferedImages;

  public AbstractImagePanel(Dimension newDimension) {
    dimension = newDimension;
    bufferedImages = new LinkedList<>();
    addMouseListener(this);
  }

  public Dimension getDimension() {
    return dimension;
  }

  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  @Override
  public Dimension getPreferredSize() {
    return dimension;
  }

  @Override
  public Dimension getMaximumSize() {
    return dimension;
  }
  
  @Override
  public Dimension getMinimumSize() {
    return dimension;
  }

  public LinkedList<BufferedImage> getBufferedImages() {
    return bufferedImages;
  }

  public void setBufferedImages(LinkedList<BufferedImage> images) {
    this.bufferedImages = images;
  }

  public File getFile() {
    return file;
  }

  public void addImage(File file) {
    if (file != null) {
      try {
        this.file = file;
        bufferedImages.add(ImageIO.read(file));
      } catch (IOException ex) {
        System.out.println(ex.toString());
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

}
