/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.editor.editors.board.BoardLayerView;
import net.rpgtoolkit.editor.editors.board.BoardSpriteDialog;
import net.rpgtoolkit.editor.ui.AbstractModelPanel;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.utilities.FileTools;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class BoardSpritePanel extends AbstractModelPanel {

  private final JTextField fileTextField;
  private final JButton fileButton;

  private final JTextField activationProgramTextField;
  private final JButton activationProgramButton;

  private final JTextField multiTaskingTextField;
  private final JButton multiTaskingButton;

  private final JSpinner xSpinner;
  private final JSpinner ySpinner;
  private final JSpinner layerSpinner;

  private int lastSpinnerLayer; // Used to ensure that the selection is valid.

  private final JComboBox typeComboBox;

  private final JButton variablesJButton;

  private static final String[] ACTIVATION_TYPES = {
    "STEP-ON", "KEYPRESS"
  };

  public BoardSpritePanel(final BoardSprite boardSprite) {
    ///
    /// super
    ///
    super(boardSprite);
    ///
    /// filePanel
    ///
    fileTextField = new JTextField(boardSprite.getFileName());
    fileTextField.setColumns(17);

    fileButton = new JButton("...");
    fileButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("itm", "Item", "Item Files");

        if (file != null) {
          fileTextField.setText(file.getName());
        }
      }
    });

    JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    filePanel.add(fileTextField);
    filePanel.add(fileButton);
    ///
    /// activationProgramPanel
    ///
    activationProgramTextField = new JTextField(boardSprite.
            getActivationProgram());
    activationProgramTextField.setColumns(17);

    activationProgramButton = new JButton("...");
    activationProgramButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("prg", "Prg", "Program Files");

        if (file != null) {
          activationProgramTextField.setText(file.getName());
        }
      }
      
    });

    JPanel activationProgramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    activationProgramPanel.add(activationProgramTextField);
    activationProgramPanel.add(activationProgramButton);
    ///
    /// multiTaskingPanel
    ///
    multiTaskingTextField = new JTextField(boardSprite.
            getMultitaskingProgram());
    multiTaskingTextField.setColumns(17);

    multiTaskingButton = new JButton("...");
    multiTaskingButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("prg", "Prg", "Program Files");

        if (file != null) {
          multiTaskingTextField.setText(file.getName());
        }
      }
    });

    JPanel multiTaskingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    multiTaskingPanel.add(multiTaskingTextField);
    multiTaskingPanel.add(multiTaskingButton);
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
    layerSpinner = new JSpinner();
    layerSpinner.setValue(((BoardSprite) model).getLayer());
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
    ///
    /// variablesJButton
    ///
    variablesJButton = new JButton("Configure");
    variablesJButton.addActionListener(new ActionListener() {

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
    constraints.insets = new Insets(8, 15, 0, 3);
    constraintsRight.insets = new Insets(0, 0, 10, 15);

    constraints.gridx = 0;
    constraints.gridy = 1;
    add(new JLabel("Item File"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 2;
    add(new JLabel("Activation"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 3;
    add(new JLabel("MultiTasking"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 4;
    add(new JLabel("X"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 5;
    add(new JLabel("Y"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 6;
    add(new JLabel("Layer"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 7;
    add(new JLabel("Type"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 8;
    add(new JLabel("Variables"), constraints);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 1;
    add(filePanel, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 2;
    add(activationProgramPanel, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 3;
    add(multiTaskingPanel, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 4;
    add(xSpinner, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 5;
    add(ySpinner, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 6;
    add(layerSpinner, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 7;
    add(typeComboBox, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 8;
    add(variablesJButton, constraintsRight);
  }
}
