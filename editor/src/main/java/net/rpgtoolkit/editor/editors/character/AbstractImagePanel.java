/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.character;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractImagePanel extends JPanel implements MouseListener {

  protected File file;
  protected Dimension dimension;
  protected BufferedImage bufferedImage;

  public AbstractImagePanel() {
    dimension = new Dimension(280, 0);
    addMouseListener(this);
    setToolTipText("Double click to select an image.");
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

  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }

  public void setBufferedImage(BufferedImage bufferedImage) {
    this.bufferedImage = bufferedImage;
  }

  @Override
  public Dimension getMinimumSize() {
    return dimension;
  }

  public File getFile() {
    return file;
  }
  
  public void openImage(File file) {
    if (file != null) {
        try {
          this.file = file;
          bufferedImage = ImageIO.read(file);
          repaint();
        } catch (IOException ex) {
          System.out.println(ex.toString());
        }
      }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
      MainWindow mainWindow = MainWindow.getInstance();
      File imageFile = mainWindow.browseLocationBySubdir(
              mainWindow.getImageSubdirectory(),
              mainWindow.getImageFilterDescription(),
              mainWindow.getImageExtensions()
      );

      openImage(imageFile);
    }
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
