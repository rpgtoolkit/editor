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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.BoardProgram;
import net.rpgtoolkit.editor.editors.board.BoardLayerView;
import net.rpgtoolkit.editor.ui.AbstractModelPanel;
import net.rpgtoolkit.editor.utilities.FileTools;

/**
 *
 * @author Joshua Michael Daly
 */
public class BoardProgramPanel extends AbstractModelPanel {

  private final JTextField fileTextField;
  private final JButton fileButton;

  private final JSpinner layerSpinner;
  private final JComboBox activationComboBox;
  private final JCheckBox isClosedCheckBox;

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
    /// filePanel
    ///
    fileTextField = new JTextField(boardProgram.getFileName());
    fileTextField.setColumns(17);

    fileButton = new JButton("...");
    fileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("prg", "Prg", "Program Files");

        if (file != null) {
          boardProgram.setFile(file);
          boardProgram.setFileName(file.getName());
          fileTextField.setText(file.getName());
        }
      }

    });

    JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    filePanel.add(fileTextField);
    filePanel.add(fileButton);
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
    /// constraints
    ///
    constraints.insets = new Insets(4, 15, 0, 30);
    constraintsRight.insets = new Insets(0, 0, 10, 15);

    constraints.gridx = 0;
    constraints.gridy = 1;
    add(new JLabel("Program"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 2;
    add(new JLabel("Layer"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 3;
    add(new JLabel("Activation"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 4;
    add(new JLabel("Is Closed"), constraints);
    ///
    /// constraintsRight
    ///
    constraintsRight.gridx = 1;
    constraintsRight.gridy = 1;
    add(filePanel, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 2;
    add(layerSpinner, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 3;
    add(activationComboBox, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 4;
    add(isClosedCheckBox, constraintsRight);
  }
}
