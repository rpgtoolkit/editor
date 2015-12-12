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
import java.io.File;
import net.rpgtoolkit.editor.ui.MainWindow;

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
  public void paint(Graphics g) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, getWidth(), getHeight());

    if (bufferedImages.size() > 0) {
      g.drawImage(bufferedImages.getFirst(), 0, 0, getWidth(), getHeight(), null);
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

      bufferedImages.remove();
      addImage(imageFile);
      repaint();
    }
  }

}
