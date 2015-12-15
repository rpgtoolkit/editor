/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AnimationFrame;
import net.rpgtoolkit.common.utilities.PropertiesSingleton;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.resources.Icons;
import net.rpgtoolkit.editor.utilities.TransparentDrawer;

/**
 *
 * @author Joshua Michael Daly
 */
public class AddTimelineFrame extends TimelineFrame {

  public AddTimelineFrame(Animation animation) {
    this.animation = animation;
    dimension = new Dimension((int)animation.getAnimationWidth(), (int)animation.getAnimationHeight());
  }

  @Override
  public void paint(Graphics g) {
    TransparentDrawer.drawTransparentBackground(g, dimension.width, dimension.height);
   
    Image image = Icons.getIcon("plus", Icons.Size.LARGE).getImage();
    
    int x = getWidth() / 2 - (image.getWidth(null) / 2);
    int y  = getHeight() / 2 - (image.getHeight(null)/ 2);
    g.drawImage(Icons.getIcon("plus", Icons.Size.LARGE).getImage(), x, y, this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    MainWindow mainWindow = MainWindow.getInstance();
    File imageFile = mainWindow.browseLocationBySubdir(
            mainWindow.getImageSubdirectory(),
            mainWindow.getImageFilterDescription(),
            mainWindow.getImageExtensions()
    );

    if (imageFile != null) {
      String remove = System.getProperty("project.path")
              + PropertiesSingleton.getProperty("toolkit.directory.bitmap")
              + File.separator;
      String path = imageFile.getAbsolutePath().replace(remove, "");
      
      animation.addFrame(new AnimationFrame(path, 0, ""));
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

}
