/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.events.AnimationChangedEvent;
import net.rpgtoolkit.common.assets.listeners.AnimationChangeListener;
import net.rpgtoolkit.common.utilities.PropertiesSingleton;
import net.rpgtoolkit.editor.editors.animation.AddTimelineFrame;
import net.rpgtoolkit.editor.editors.animation.TimelineFrame;
import net.rpgtoolkit.editor.ui.AnimatedPanel;
import net.rpgtoolkit.editor.ui.DoubleField;
import net.rpgtoolkit.editor.ui.Gui;

import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;
import net.rpgtoolkit.editor.ui.IntegerField;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class AnimationEditor extends ToolkitEditorWindow implements AnimationChangeListener {

  private final Animation animation;
  private AnimatedPanel animatedPanel;
  private JScrollPane timelineScrollPane;
  private JPanel timelinePanel;
  private JPanel controlPanel;

  private final Border defaultEtchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

  // CONTROL PANEL
  private IntegerField frameWidth;
  private IntegerField frameHeight;
  private JButton applyButton;
  private DoubleField frameRate;
  private JComboBox frameSound;

  public AnimationEditor(Animation theAnimation) {
    super("Editing Animation", true, true, true, true);
    animation = theAnimation;
    animation.addAnimationChangeListener(this);

    configureInterface();
    setSize(800, 600);
    setVisible(true);
  }

  @Override
  public boolean save() {
    boolean success = false;
    
    if (animation.getFile() == null) {
      File file = MainWindow.getInstance().saveByType(Animation.class);
      
      if (file != null) {
        success = animation.saveAs(file);
        setTitle("Editing - " + file.getName());
      }
    } else {
      success = animation.saveBinary();
    }
    
    return success;
  }

  /**
   *
   *
   * @param file
   * @return
   */
  @Override
  public boolean saveAs(File file) {
    animation.setFile(file);

    return save();
  }

  public void gracefulClose() {
    animation.removeAnimationChangeListener(this);
  }

  @Override
  public void animationChanged(AnimationChangedEvent e) {
    updateInterface();
  }

  @Override
  public void animationFrameAdded(AnimationChangedEvent e) {
    updateInterface();
  }

  @Override
  public void animationFrameRemoved(AnimationChangedEvent e) {
    updateInterface();
  }

  private void updateInterface() {
    timelinePanel.removeAll();
    configureTimeline();
    revalidate();
    repaint();
  }

  private void configureInterface() {
    animatedPanel = new AnimatedPanel();
    animatedPanel.setAnimation(animation);

    timelinePanel = new JPanel();
    timelinePanel.setBackground(Color.LIGHT_GRAY);
    timelinePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    configureTimeline();

    controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
    configureControlPanel();

    timelineScrollPane = new JScrollPane(timelinePanel);
    timelineScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    JPanel wrappingPanel = new JPanel(new FlowLayout());
    wrappingPanel.add(controlPanel);

    JPanel westContainer = new JPanel(new BorderLayout());
    westContainer.add(animatedPanel, BorderLayout.CENTER);
    westContainer.add(timelineScrollPane, BorderLayout.SOUTH);

    JPanel eastContainer = new JPanel(new BorderLayout());
    eastContainer.add(westContainer, BorderLayout.CENTER);
    eastContainer.add(wrappingPanel, BorderLayout.EAST);

    setContentPane(eastContainer);
  }

  private void configureControlPanel() {
    
    File directory = new File(System.getProperty("project.path") 
            + PropertiesSingleton.getProperty("toolkit.directory.media") 
            + File.separator);
    String[] exts = new String[] {"wav"};
    frameSound = Gui.getFileListJComboBox(directory, exts, true);
    frameSound.setSelectedItem(animation.getSoundEffect());
    frameSound.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        animation.setSoundEffect((String)frameSound.getSelectedItem());
      }

    });
    
    frameWidth = new IntegerField(animation.getAnimationWidth());
    frameWidth.setColumns(15);
    
    frameHeight = new IntegerField(animation.getAnimationHeight());
    frameHeight.setColumns(15);

    applyButton = new JButton("Apply");
    applyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        animation.setAnimationWidth(frameWidth.getValue());
        animation.setAnimationHeight(frameHeight.getValue());
        animation.setFramRate(frameRate.getValue());
      }
    });

    frameRate = new DoubleField(animation.getFrameRate());
    frameRate.setColumns(15);

    JLabel widthLabel = new JLabel("X: ");
    JLabel heightLabel = new JLabel("Y: ");

    JPanel miscPanel = new JPanel();
    miscPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Sound Effect"));
    
    JPanel sizePanel = new JPanel();
    sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.PAGE_AXIS));
    sizePanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Animation Dimensions"));
    
    JPanel delayPanel = new JPanel();
    delayPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Frame Rate (ms)"));

    miscPanel.add(frameSound, BorderLayout.CENTER);
    
    JPanel widthPanel = new JPanel(new FlowLayout());
    widthPanel.add(widthLabel);
    widthPanel.add(frameWidth);
    
    JPanel heightPanel = new JPanel(new FlowLayout());
    heightPanel.add(heightLabel);
    heightPanel.add(frameHeight);
    
    JPanel applyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    applyPanel.add(applyButton);
    
    sizePanel.add(widthPanel);
    sizePanel.add(heightPanel);
    
    delayPanel.add(frameRate);

    controlPanel.add(miscPanel);
    controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    controlPanel.add(sizePanel);
    controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    controlPanel.add(delayPanel);
    controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    controlPanel.add(applyPanel);
  }

  private void configureTimeline() {
    long frameCount = animation.getFrameCount();
    LinkedList<TimelineFrame> timelineFrames = new LinkedList<>();

    for (int i = 0; i < frameCount; i++) {
      if (!animation.getFrame(i).getFrameName().equals("")) {
        BufferedImage bufferedImage = new BufferedImage(
                (int) animation.getAnimationWidth(),
                (int) animation.getAnimationHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bufferedImage.createGraphics();

        timelineFrames.add(new TimelineFrame(animation, i));
      }
    }

    for (TimelineFrame frame : timelineFrames) {
      timelinePanel.add(frame);
    }

    timelinePanel.add(new AddTimelineFrame(animation));
  }
}
