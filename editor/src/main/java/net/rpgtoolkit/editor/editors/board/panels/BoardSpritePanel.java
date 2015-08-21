/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.editor.editors.board.BoardLayerView;
import net.rpgtoolkit.editor.editors.board.BoardSpriteDialog;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class BoardSpritePanel extends AbstractModelPanel {

  private final JTextField fileTextField;
  private final JLabel fileLabel;

  private final JTextField activationProgramTextField;
  private final JLabel activationProgramLabel;

  private final JTextField multiTaskingTextField;
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
    /// fileTextField
    ///
    fileTextField = new JTextField(boardSprite.getFileName());
    fileTextField.setColumns(17);
    ///
    /// activationTextField
    ///
    activationProgramTextField = new JTextField(boardSprite.
            getActivationProgram());
    activationProgramTextField.setColumns(17);
    ///
    /// multiTaskingTextField
    ///
    multiTaskingTextField = new JTextField(boardSprite.
            getMultitaskingProgram());
    multiTaskingTextField.setColumns(17);
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
    /// variablesButton
    ///
    variablesButton = new JButton("Configure");
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
                    .addComponent(fileTextField)
                    .addComponent(activationProgramTextField)
                    .addComponent(multiTaskingTextField)
                    .addComponent(xSpinner)
                    .addComponent(ySpinner)
                    .addComponent(layerSpinner)
                    .addComponent(typeComboBox)
                    .addComponent(variablesButton));
    
    layout.setHorizontalGroup(horizontalGroup);
    
    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(fileLabel).addComponent(fileTextField));
    
    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(activationProgramLabel).addComponent(activationProgramTextField));
    
    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(multiTaskingLabel).addComponent(multiTaskingTextField));
    
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
