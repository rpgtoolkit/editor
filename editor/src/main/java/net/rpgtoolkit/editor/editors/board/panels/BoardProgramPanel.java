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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.BoardProgram;
import net.rpgtoolkit.editor.editors.board.BoardLayerView;

/**
 *
 * @author Joshua Michael Daly
 */
public class BoardProgramPanel extends AbstractModelPanel {

  private final JTextField programTextField;
  private final JLabel programLabel;

  private final JSpinner layerSpinner;
  private final JLabel layerLabel;
  
  private final JComboBox activationComboBox;
  private final JLabel activationLabel;
  
  private final JCheckBox isClosedCheckBox;
  private final JLabel isClosedLabel;

  private static final String[] ACTIVATION_TYPES = {
    "STEP-ON", "KEYPRESS"
  };

  private int lastSpinnerLayer; // Used to ensure that the selection is valid.

  private final BoardProgram boardProgram;

  public BoardProgramPanel(BoardProgram program) {
    ///
    /// super
    ///
    super(program);
    ///
    /// boardProgram
    ///
    boardProgram = program;
    ///
    /// programTextField
    ///
    programTextField = new JTextField(boardProgram.getFileName());
    programTextField.setColumns(17);
    ///
    /// layerSpinner
    ///
    layerSpinner = new JSpinner();
    layerSpinner.setValue(boardProgram.getLayer());
    layerSpinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        BoardLayerView lastLayerView = getBoardEditor().getBoardView().
                getLayer((int) boardProgram.getLayer());

        BoardLayerView newLayerView = getBoardEditor().getBoardView().
                getLayer((int) layerSpinner.getValue());

        // Make sure this is a valid move.
        if (lastLayerView != null && newLayerView != null) {
          // Do the swap.
          boardProgram.setLayer((int) layerSpinner.getValue());
          newLayerView.getLayer().getPrograms().add(boardProgram);
          lastLayerView.getLayer().getPrograms().remove(boardProgram);
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
    /// activationComboBox
    ///
    activationComboBox = new JComboBox(ACTIVATION_TYPES);

    switch ((int) boardProgram.getActivationType()) {
      case 0:
        activationComboBox.setSelectedIndex(0);
        break;
      case 1:
        activationComboBox.setSelectedIndex(1);
        break;
      default:
    }

    activationComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switch (activationComboBox.getSelectedIndex()) {
          case 0:
            boardProgram.setActivationType(0);
            break;
          case 1:
            boardProgram.setActivationType(1);
            break;
          default:

        }
      }
    });
    ///
    /// isClosedCheckBox
    ///
    isClosedCheckBox = new JCheckBox();
    isClosedCheckBox.setSelected(((BoardProgram) model).getVector().isClosed());
    isClosedCheckBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        ((BoardProgram) model).getVector().setClosed(isClosedCheckBox.isSelected());
        updateCurrentBoardView();
      }
    });
    ///
    /// this
    ///
    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(programLabel = getJLabel("Program"))
                    .addComponent(layerLabel = getJLabel("Layer"))
                    .addComponent(activationLabel = getJLabel("Activation"))
                    .addComponent(isClosedLabel = getJLabel("Is Closed")));
    
    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(programTextField)
                    .addComponent(layerSpinner)
                    .addComponent(activationComboBox)
                    .addComponent(isClosedCheckBox));
    
    layout.setHorizontalGroup(horizontalGroup);
    
    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(programLabel).addComponent(programTextField));
    
    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(layerLabel).addComponent(layerSpinner));
    
    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(activationLabel).addComponent(activationComboBox));
    
    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(isClosedLabel).addComponent(isClosedCheckBox));
  
    layout.setVerticalGroup(verticalGroup);
  }
}
