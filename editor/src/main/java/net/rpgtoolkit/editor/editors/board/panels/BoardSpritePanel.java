/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.editor.editors.board.BoardLayerView;
import net.rpgtoolkit.editor.editors.board.BoardSpriteDialog;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.common.utilities.PropertiesSingleton;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class BoardSpritePanel extends AbstractModelPanel {

  private final JComboBox fileComboBox;
  private final JLabel fileLabel;

  private final JComboBox activationComboBox;
  private final JLabel activationProgramLabel;

  private final JComboBox multiTaskingComboBox;
  private final JLabel multiTaskingLabel;

  private final JSpinner xSpinner;
  private final JLabel xLabel;

  private final JSpinner ySpinner;
  private final JLabel yLabel;

  private final JSpinner layerSpinner;
  private final JLabel layerLabel;

  private int lastSpinnerLayer; // Used to ensure that the selection is valid.

  private final JComboBox typeComboBox;
  private final JLabel typeLabel;

  private final JButton variablesButton;
  private final JLabel variablesLabel;

  private static final String[] ACTIVATION_TYPES = {
    "STEP-ON", "KEYPRESS"
  };

  public BoardSpritePanel(final BoardSprite boardSprite) {
    ///
    /// super
    ///
    super(boardSprite);
    ///
    /// fileComboBox
    ///
    File directory = new File(System.getProperty("project.path") 
            + PropertiesSingleton.getProperty("toolkit.directory.item") 
            + File.separator);
    String[] exts = new String[]{"itm"};
    fileComboBox = getFileListJComboBox(directory, exts, true);
    fileComboBox.setSelectedItem(boardSprite.getFileName());
    fileComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        boardSprite.setFileName((String) fileComboBox.getSelectedItem());
      }

    });
    ///
    /// activationComboBox
    ///
    directory = new File(System.getProperty("project.path") + "Prg" + File.separator);
    exts = new String[]{"prg"};
    activationComboBox = getFileListJComboBox(directory, exts, true);
    activationComboBox.setSelectedItem(boardSprite.getFileName());
    activationComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        boardSprite.setActivationProgram((String) activationComboBox.getSelectedItem());
      }

    });
    ///
    /// multiTaskingTextField
    ///
    multiTaskingComboBox = getFileListJComboBox(directory, exts, true);
    multiTaskingComboBox.setSelectedItem(boardSprite.getFileName());
    multiTaskingComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        boardSprite.setMultitaskingProgram((String) multiTaskingComboBox.getSelectedItem());
      }

    });
    ///
    /// xSpinner
    ///
    xSpinner = new JSpinner();
    xSpinner.setValue(((BoardSprite) model).getX());
    xSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        BoardSprite sprite = (BoardSprite) model;

        if (sprite.getX() != (int) xSpinner.getValue()) {
          sprite.setX((int) xSpinner.getValue());
          updateCurrentBoardView();
        }
      }

    });
    ///
    /// ySpinner
    ///
    ySpinner = new JSpinner();
    ySpinner.setValue(((BoardSprite) model).getY());
    ySpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        BoardSprite sprite = (BoardSprite) model;

        if (sprite.getY() != (int) ySpinner.getValue()) {
          sprite.setY((int) ySpinner.getValue());
          updateCurrentBoardView();
        }
      }

    });
    ///
    /// layerSpinner
    ///
    layerSpinner = getJSpinner(((BoardSprite) model).getLayer());
    layerSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        BoardSprite sprite = (BoardSprite) model;

        BoardLayerView lastLayerView = getBoardEditor().getBoardView().
                getLayer((int) sprite.getLayer());

        BoardLayerView newLayerView = getBoardEditor().getBoardView().
                getLayer((int) layerSpinner.getValue());

        // Make sure this is a valid move.
        if (lastLayerView != null && newLayerView != null) {
          // Do the swap.
          sprite.setLayer((int) layerSpinner.getValue());
          newLayerView.getLayer().getSprites().add(sprite);
          lastLayerView.getLayer().getSprites().remove(sprite);
          updateCurrentBoardView();

          // Store new layer selection index.
          lastSpinnerLayer = (int) layerSpinner.getValue();
        } else {
          // Not a valid layer revert selection.
          layerSpinner.setValue(lastSpinnerLayer);
        }
      }

    });
    ///
    /// typeComboBox
    ///
    typeComboBox = new JComboBox(ACTIVATION_TYPES);
    typeComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String type = (String) typeComboBox.getSelectedItem();
        if (type.equals(ACTIVATION_TYPES[0])) {
          boardSprite.setActivationType(0);
        } else {
          boardSprite.setActivationType(1);
        }
      }

    });
    ///
    /// variablesButton
    ///
    variablesButton = getJButton("Configure");
    variablesButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        BoardSpriteDialog dialog = new BoardSpriteDialog(
                MainWindow.getInstance(), "Configure Variables",
                true, (BoardSprite) model);

        if (dialog.showDialog() == BoardSpriteDialog.APPLY) {
          String initialVariable = dialog.getInitialVariable();
          String initialValue = dialog.getInitialValue();
          String finalVariable = dialog.getFinalVariable();
          String finalValue = dialog.getFinalValue();
          String loadingVariable = dialog.getLoadingVariable();
          String loadingValue = dialog.getLoadingValue();

          if (!boardSprite.getInitialVariable().equals(initialVariable)) {
            boardSprite.setInitialVariable(initialVariable);
          }

          if (!boardSprite.getInitialValue().equals(initialValue)) {
            boardSprite.setInitialValue(initialValue);
          }

          if (!boardSprite.getFinalVariable().equals(finalVariable)) {
            boardSprite.setFinalVariable(finalVariable);
          }

          if (!boardSprite.getFinalValue().equals(finalValue)) {
            boardSprite.setFinalValue(finalValue);
          }

          if (!boardSprite.getLoadingVariable().equals(loadingVariable)) {
            boardSprite.setLoadingVariable(loadingVariable);
          }

          if (!boardSprite.getLoadingValue().equals(loadingValue)) {
            boardSprite.setLoadingValue(loadingValue);
          }
        }
      }

    });
    ///
    /// this
    ///
    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(fileLabel = getJLabel("Item File"))
            .addComponent(activationProgramLabel = getJLabel("Activation Program"))
            .addComponent(multiTaskingLabel = getJLabel("MultiTasking Program"))
            .addComponent(xLabel = getJLabel("X"))
            .addComponent(yLabel = getJLabel("Y"))
            .addComponent(layerLabel = getJLabel("Layer"))
            .addComponent(typeLabel = getJLabel("Type"))
            .addComponent(variablesLabel = getJLabel("Variables")));

    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(fileComboBox)
            .addComponent(activationComboBox)
            .addComponent(multiTaskingComboBox)
            .addComponent(xSpinner)
            .addComponent(ySpinner)
            .addComponent(layerSpinner)
            .addComponent(typeComboBox)
            .addComponent(variablesButton));

    layout.setHorizontalGroup(horizontalGroup);

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(fileLabel).addComponent(fileComboBox));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(activationProgramLabel).addComponent(activationComboBox));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(multiTaskingLabel).addComponent(multiTaskingComboBox));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(xLabel).addComponent(xSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(yLabel).addComponent(ySpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(yLabel).addComponent(ySpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(layerLabel).addComponent(layerSpinner));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(typeLabel).addComponent(typeComboBox));

    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(variablesLabel).addComponent(variablesButton));

    layout.setVerticalGroup(verticalGroup);
  }
}
