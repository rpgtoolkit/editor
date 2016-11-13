/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.character;

import java.awt.Point;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.editor.ui.AbstractModelPanel;

/**
 *
 * @author Joshua Michael Daly
 */
public class CharacterModelPanel extends AbstractModelPanel {

  private final JSpinner baseVectorWidthSpinner;
  private final JLabel baseVectorWidthLabel;

  private final JSpinner baseVectorHeightSpinner;
  private final JLabel baseVectorHeightLabel;

  private final JSpinner baseVectorOffsetXSpinner;
  private final JLabel baseVectorOffsetXLabel;

  private final JSpinner baseVectorOffsetYSpinner;
  private final JLabel baseVectorOffsetYLabel;

  private final JSpinner activationVectorWidthSpinner;
  private final JLabel activationVectorWidthLabel;

  private final JSpinner activationVectorHeightSpinner;
  private final JLabel activationVectorHeightLabel;

  private final JSpinner activationVectorOffsetXSpinner;
  private final JLabel activationVectorOffsetXLabel;

  private final JSpinner activationVectorOffsetYSpinner;
  private final JLabel activationVectorOffsetYLabel;

  private final Player player;

  public CharacterModelPanel(Player model) {
    ///
    /// super
    ///
    super(model);
    ///
    /// player
    ///
    this.player = model;
    ///
    /// baseVectorWidthSpinner
    ///
    baseVectorWidthSpinner = getJSpinner(player.getBaseVector().getWidth());
    baseVectorWidthSpinner.setModel(new SpinnerNumberModel(
            player.getBaseVector().getWidth(), 10, 100, 1));
    baseVectorWidthSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int width = ((Double) baseVectorWidthSpinner.getValue()).intValue();
        int height = (int) player.getBaseVector().getHeight();

        // Assumes player base vector is rectangular in shape, will be a limitiation.
        BoardVector boardVector = new BoardVector();
        boardVector.addPoint(0, 0);
        boardVector.addPoint(width, 0);
        boardVector.addPoint(width, height);
        boardVector.addPoint(0, height);
        boardVector.setClosed(true);

        player.setBaseVector(boardVector);
      }
    });
    ///
    /// baseVectorHeightSpinner
    ///
    baseVectorHeightSpinner = getJSpinner(player.getBaseVector().getHeight());
    baseVectorHeightSpinner.setModel(new SpinnerNumberModel(
            player.getBaseVector().getHeight(), 10, 100, 1));
    baseVectorHeightSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int width = (int) player.getBaseVector().getWidth();
        int height = ((Double) baseVectorHeightSpinner.getValue()).intValue();

        // Assumes player base vector is rectangular in shape, will be a limitiation.
        BoardVector boardVector = new BoardVector();
        boardVector.addPoint(0, 0);
        boardVector.addPoint(width, 0);
        boardVector.addPoint(width, height);
        boardVector.addPoint(0, height);
        boardVector.setClosed(true);

        player.setBaseVector(boardVector);
      }
    });
    ///
    /// baseVectorOffsetXSpinner
    ///
    baseVectorOffsetXSpinner = getJSpinner(player.getBaseVectorOffset().getX());
    baseVectorOffsetXSpinner.setModel(new SpinnerNumberModel(
            player.getBaseVectorOffset().getX(), -1000, 1000, 1));
    baseVectorOffsetXSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int x = ((Double) baseVectorOffsetXSpinner.getValue()).intValue();
        int y = (int) player.getBaseVectorOffset().getY();

        player.setBaseVectorOffset(new Point(x, y));
      }
    });
    ///
    /// baseVectorOffsetYSpinner
    ///
    baseVectorOffsetYSpinner = getJSpinner(player.getBaseVectorOffset().getY());
    baseVectorOffsetYSpinner.setModel(new SpinnerNumberModel(
            player.getBaseVectorOffset().getY(), -1000, 1000, 1));
    baseVectorOffsetYSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int x = (int) player.getBaseVectorOffset().getX();
        int y = ((Double) baseVectorOffsetYSpinner.getValue()).intValue();

        player.setBaseVectorOffset(new Point(x, y));
      }
    });
    ///
    /// activationVectorWidthSpinner
    ///
    activationVectorWidthSpinner = getJSpinner(player.getActivationVector().getWidth());
    activationVectorWidthSpinner.setModel(new SpinnerNumberModel(
            player.getActivationVector().getWidth(), 10, 100, 1));
    activationVectorWidthSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int width = ((Double) activationVectorWidthSpinner.getValue()).intValue();
        int height = (int) player.getActivationVector().getHeight();

        // Assumes player base vector is rectangular in shape, will be a limitiation.
        BoardVector boardVector = new BoardVector();
        boardVector.addPoint(0, 0);
        boardVector.addPoint(width, 0);
        boardVector.addPoint(width, height);
        boardVector.addPoint(0, height);
        boardVector.setClosed(true);

        player.setActivationVector(boardVector);
      }
    });
    ///
    /// baseVectorHeightSpinner
    ///
    activationVectorHeightSpinner = getJSpinner(player.getActivationVector().getHeight());
    activationVectorHeightSpinner.setModel(new SpinnerNumberModel(
            player.getActivationVector().getHeight(), 10, 100, 1));
    activationVectorHeightSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int width = (int) player.getActivationVector().getWidth();
        int height = ((Double) activationVectorHeightSpinner.getValue()).intValue();

        // Assumes player base vector is rectangular in shape, will be a limitiation.
        BoardVector boardVector = new BoardVector();
        boardVector.addPoint(0, 0);
        boardVector.addPoint(width, 0);
        boardVector.addPoint(width, height);
        boardVector.addPoint(0, height);
        boardVector.setClosed(true);

        player.setActivationVector(boardVector);
      }
    });
    ///
    /// activationVectorOffsetXSpinner
    ///
    activationVectorOffsetXSpinner = getJSpinner(player.getActivationVectorOffset().getX());
    activationVectorOffsetXSpinner.setModel(new SpinnerNumberModel(
            player.getActivationVectorOffset().getX(), -1000, 1000, 1));
    activationVectorOffsetXSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int x = ((Double) activationVectorOffsetXSpinner.getValue()).intValue();
        int y = (int) player.getActivationVectorOffset().getY();

        player.setActivationVectorOffset(new Point(x, y));
      }
    });
    ///
    /// activationVectorOffsetYSpinner
    ///
    activationVectorOffsetYSpinner = getJSpinner(player.getActivationVectorOffset().getY());
    activationVectorOffsetYSpinner.setModel(new SpinnerNumberModel(
            player.getActivationVectorOffset().getY(), -1000, 1000, 1));
    activationVectorOffsetYSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int x = (int) player.getActivationVectorOffset().getX();
        int y = ((Double) activationVectorOffsetYSpinner.getValue()).intValue();

        player.setActivationVectorOffset(new Point(x, y));
      }
    });
    ///
    /// this
    ///
    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(baseVectorWidthLabel = getJLabel("Base Vector Width"))
            .addComponent(baseVectorHeightLabel = getJLabel("Base Vector Height"))
            .addComponent(baseVectorOffsetXLabel = getJLabel("Base Vector Offset X"))
            .addComponent(baseVectorOffsetYLabel = getJLabel("Base Vector Offset Y"))
            .addComponent(activationVectorWidthLabel = getJLabel("Activation Vector Width"))
            .addComponent(activationVectorHeightLabel = getJLabel("Activation Vector Height"))
            .addComponent(activationVectorOffsetXLabel = getJLabel("Activation Vector Offset X"))
            .addComponent(activationVectorOffsetYLabel = getJLabel("Activation Vector Offset Y")));

    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(baseVectorWidthSpinner)
            .addComponent(baseVectorHeightSpinner)
            .addComponent(baseVectorOffsetXSpinner)
            .addComponent(baseVectorOffsetYSpinner)
            .addComponent(activationVectorWidthSpinner)
            .addComponent(activationVectorHeightSpinner)
            .addComponent(activationVectorOffsetXSpinner)
            .addComponent(activationVectorOffsetYSpinner));

    layout.setHorizontalGroup(horizontalGroup);

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(baseVectorWidthLabel).addComponent(baseVectorWidthSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(baseVectorHeightLabel).addComponent(baseVectorHeightSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(baseVectorOffsetXLabel).addComponent(baseVectorOffsetXSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(baseVectorOffsetYLabel).addComponent(baseVectorOffsetYSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(activationVectorWidthLabel).addComponent(activationVectorWidthSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(activationVectorHeightLabel).addComponent(activationVectorHeightSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(activationVectorOffsetXLabel).addComponent(activationVectorOffsetXSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(activationVectorOffsetYLabel).addComponent(activationVectorOffsetYSpinner));

    layout.setVerticalGroup(verticalGroup);
  }

}
