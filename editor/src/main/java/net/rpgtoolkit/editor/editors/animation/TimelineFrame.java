/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JPanel;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AnimationFrame;
import net.rpgtoolkit.common.utilities.PropertiesSingleton;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.utilities.TransparentDrawer;

/**
 *
 * @author Joshua Michael Daly
 */
public class TimelineFrame extends JPanel implements MouseListener {

  protected Dimension dimension;

  private boolean entered;

  private int index;
  protected Animation animation;
  private AnimationFrame animationFrame;

  public TimelineFrame() {
    entered = false;
    animation = null;
    animationFrame = null;
    addMouseListener(this);
    dimension = new Dimension(150, 150);
  }

  public TimelineFrame(Animation animation, int index) {
    entered = false;
    this.index = index;
    this.animation = animation;
    this.animationFrame = animation.getFrame(index);
    dimension = new Dimension((int) animation.getAnimationWidth(), (int) animation.getAnimationHeight());
    addMouseListener(this);
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
    TransparentDrawer.drawTransparentBackground(g, dimension.width, dimension.height);

    if (animationFrame != null) {
      g.drawImage(animationFrame.getFrameImage(), 0, 0, null);
    }

    if (entered) {
      g.setColor(new Color(0, 0, 255, 64));
      g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
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

        animationFrame = new AnimationFrame(path,
                animationFrame.getTransparentColour(),
                animationFrame.getFrameSound());
        animation.setFrame(animationFrame, index);

        repaint();
      }
    } else if (e.getButton() == MouseEvent.BUTTON2) {
      animation.removeFrame(index);
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
    entered = !entered;
    repaint();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    entered = !entered;
    repaint();
  }

}
