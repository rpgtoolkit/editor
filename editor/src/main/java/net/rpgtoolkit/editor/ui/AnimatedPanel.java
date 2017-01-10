/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.Timer;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.assets.events.AnimationChangedEvent;
import net.rpgtoolkit.common.assets.listeners.AnimationChangeListener;
import net.rpgtoolkit.common.utilities.PropertiesSingleton;
import net.rpgtoolkit.editor.utilities.TransparentDrawer;

/**
 *
 * @author Joshua Michael Daly
 */
public class AnimatedPanel extends AbstractImagePanel implements AnimationChangeListener {

  public static final int DEFAULT_HEIGHT = 300;

  private Animation animation;
  private BufferedImage frameImage;
  private Timer timer;

  private BoardVector baseVector;
  private BoardVector activationVector;
  
  private Point baseVectorOffset;
  private Point activationVectorOffset;

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

      frameImage = animation.getFrame(index).getFrameImage();
      repaint();
    }
  };

  public AnimatedPanel() {
    baseVectorOffset = new Point(0, 0);
    activationVectorOffset = new Point(0, 0);
  }

  public AnimatedPanel(Dimension dimension) {
    super(dimension);
    baseVectorOffset = new Point(0, 0);
    activationVectorOffset = new Point(0, 0);
  }

  public Animation getAnimation() {
    return animation;
  }

  public void setAnimation(Animation animation) {
    if (this.animation != null) {
      this.animation.removeAnimationChangeListener(this);
      this.animation.addAnimationChangeListener(this);
    }

    this.animation = animation;

    if (animation == null) {
      timer = null;
      frameImage = null;
    } else if (animation.getFrameCount() > 0) {
      frameImage = animation.getFrame(0).getFrameImage();
    }

    repaint();
  }

  public BoardVector getBaseVector() {
    return baseVector;
  }

  public void setBaseVector(BoardVector baseVector) {
    this.baseVector = baseVector;
  }

  public BoardVector getActivationVector() {
    return activationVector;
  }

  public void setActivationVector(BoardVector activationVector) {
    this.activationVector = activationVector;
  }

  public Point getBaseVectorOffset() {
    return baseVectorOffset;
  }

  public void setBaseVectorOffset(Point baseVectorOffset) {
    this.baseVectorOffset = baseVectorOffset;
  }

  public Point getActivationVectorOffset() {
    return activationVectorOffset;
  }

  public void setActivationVectorOffset(Point activationVectorOffset) {
    this.activationVectorOffset = activationVectorOffset;
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
  public void animationChanged(AnimationChangedEvent e) {
    updateAnimation();
  }

  @Override
  public void animationFrameAdded(AnimationChangedEvent e) {
    updateAnimation();
  }

  @Override
  public void animationFrameRemoved(AnimationChangedEvent e) {
    updateAnimation();
  }

  @Override
  public void paint(Graphics g) {
    TransparentDrawer.drawTransparentBackground(g, getWidth(), getHeight());

    if (animation != null) {
      int x = (getWidth() - (int) animation.getAnimationWidth()) / 2;
      int y = (getHeight() - (int) animation.getAnimationHeight()) / 2;

      if (frameImage != null) {
        int width = (int) animation.getAnimationWidth();
        int height = (int) animation.getAnimationHeight();

        if (frameImage.getWidth() > width || frameImage.getHeight() > height) {
          makeSubImage();
        }

        g.drawImage(frameImage, x, y, null);
      }

      g.setColor(Color.LIGHT_GRAY);
      g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

      if (baseVector != null) {
        g.setColor(Color.WHITE);
        drawVector(baseVector, g, x, y, 
                (int) baseVectorOffset.getX(), (int) baseVectorOffset.getY());
      }

      if (activationVector != null) {
        g.setColor(Color.YELLOW);
        drawVector(activationVector, g, x, y, 
                (int) activationVectorOffset.getX(), (int) activationVectorOffset.getY());
      }
    }

  }

  public void animate() {
    timer = new Timer((int) (animation.getFrameRate() * 1000), animate);
    timer.start();

    if (!animation.getSoundEffect().isEmpty()) {
      String path
              = System.getProperty("project.path")
              + PropertiesSingleton.getProperty("toolkit.directory.media")
              + File.separator
              + animation.getSoundEffect();
    }
  }

  public void stop() {
    if (timer != null) {
      timer.stop();
      timer = null;
    }

    frameImage = animation.getFrame(0).getFrameImage();
    repaint();
  }

  private void updateAnimation() {
    if (animation.getFrameCount() > 0) {
      frameImage = animation.getFrame(0).getFrameImage();
      repaint();
    } else {
      frameImage = null;
    }
  }

  private void makeSubImage() {
    int width = (int) animation.getAnimationWidth();
    int height = (int) animation.getAnimationHeight();
    int frameWidth = frameImage.getWidth();
    int frameHeight = frameImage.getHeight();

    if (frameWidth > width || frameHeight > height) {
      if (frameWidth > width && frameHeight > height) {
        frameImage = frameImage.getSubimage(0, 0, width, height);
      } else if (frameWidth > width) {
        frameImage = frameImage.getSubimage(0, 0, width, frameHeight);
      } else {
        frameImage = frameImage.getSubimage(0, 0, frameWidth, height);
      }
    }
  }

  private void drawVector(BoardVector vector, Graphics g, int x, int y, int xOffset, int yOffset) {
    int count = vector.getPointCount();
    for (int i = 0; i < count - 1; i++) {
      g.drawLine(
              x + vector.getPointX(i) + xOffset,
              y + vector.getPointY(i) + yOffset,
              x + vector.getPointX(i + 1) + xOffset,
              y + vector.getPointY(i + 1) + yOffset);
    }

    if (vector.isClosed()) {
      // Draw the final lines
      g.drawLine(
              x + vector.getPointX(count - 1) + xOffset,
              y + vector.getPointY(count - 1) + yOffset,
              x + vector.getPointX(0) + xOffset,
              y + vector.getPointY(0) + yOffset);
    }
  }

}
