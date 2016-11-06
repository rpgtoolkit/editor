/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.character;

import net.rpgtoolkit.editor.ui.AbstractImagePanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.utilities.TransparentDrawer;

/**
 *
 * @author Joshua Michael Daly
 */
public class ProfilePanel extends AbstractImagePanel {

  public ProfilePanel() {
    super(new Dimension(280, 0));
    setToolTipText("Double click to select an image.");
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

  @Override
  public void paint(Graphics g) {
    TransparentDrawer.drawTransparentBackground(g, getWidth(), getHeight());

    if (bufferedImages.size() > 0) {
      BufferedImage image = bufferedImages.getFirst();
      int x = (getWidth() - image.getWidth(null)) / 2;
      int y = (getHeight() - image.getHeight(null)) / 2;
      g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
    }

    g.setColor(Color.LIGHT_GRAY);
    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
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

      if (imageFile != null) {
        if (bufferedImages.size() > 0) {
          bufferedImages.remove();
        }
        
        addImage(imageFile);
        repaint();
      }
    }
  }

}
