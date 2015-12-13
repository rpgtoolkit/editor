/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import net.rpgtoolkit.common.assets.Animation;

/**
 *
 * @author Joshua Michael Daly
 */
public class AnimatedPanel extends AbstractImagePanel {
  public static final int ANIMATION_HEIGHT = 450;
  
  private Animation animation;
  private BufferedImage frame;
  private Timer timer;

  private final ActionListener animate = new ActionListener() {
    private int index = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
      if (index < animation.getFrameCount() - 1) {
        index++;
      } else {
        index = 0;
        timer.stop();
        timer = null;
      }

      frame = animation.getFrame(index).getFrameImage();
      repaint();
    }
  };

  public AnimatedPanel() {
    super(new Dimension(0, ANIMATION_HEIGHT));
  }
  
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(getParent().getWidth() - 25, ANIMATION_HEIGHT);
  }

  @Override
  public Dimension getMaximumSize() {
    return new Dimension(getParent().getWidth() - 25, ANIMATION_HEIGHT);
  }
  
  @Override
  public Dimension getMinimumSize() {
    return new Dimension(getParent().getWidth() - 25, ANIMATION_HEIGHT);
  }

  public void setAnimation(Animation animation) {
    this.animation = animation;

    if (animation == null) {
      timer = null;
      frame = null;
    } else if (animation.getFrameCount() > 0) {
      frame = animation.getFrame(0).getFrameImage();
    }

    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (animation != null) {
      if (animation.getFrameCount() > 0) {
        if (timer == null) {
          animate();
        } else {
          stop();
        }
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, getWidth(), getHeight());

    if (frame != null) {
      int x = (getWidth() - frame.getWidth(null)) / 2;
      int y = (getHeight() - frame.getHeight(null)) / 2;
      g.drawImage(frame, x, y, frame.getWidth(), frame.getHeight(), null);
    }

    g.setColor(Color.LIGHT_GRAY);
    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
  }

  public void animate() {
    timer = new Timer((int) (animation.getFrameDelay() * 1000), animate);
    timer.start();
  }

  public void stop() {
    if (timer != null) {
      timer.stop();
      timer = null;
    }
    
    frame = animation.getFrame(0).getFrameImage();
    repaint();
  }

}
