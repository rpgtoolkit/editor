/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.rpgtoolkit.editor.editors.BoardEditor;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractModelPanel extends JPanel {

  protected Object model;

  protected GridBagConstraints constraints;
  protected GridBagConstraints constraintsRight;

  public AbstractModelPanel(Object model) {
    this.model = model;

    setLayout(new GridBagLayout());
    constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.NORTHWEST;

    constraintsRight = (GridBagConstraints) constraints.clone();
    constraintsRight.fill = GridBagConstraints.HORIZONTAL;
    constraintsRight.weightx = 1.0;

    Font font = new JLabel().getFont();
    font = new Font(font.getName(), Font.BOLD, font.getSize());

    JLabel nameLabel = new JLabel("Name");
    JLabel valueLabel = new JLabel("Value");
    nameLabel.setFont(font);
    valueLabel.setFont(font);

    constraints.insets = new Insets(15, 15, 15, 15);
    constraintsRight.insets = new Insets(15, 0, 15, 0);

    constraints.gridx = 0;
    constraints.gridy = 0;
    add(nameLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    add(valueLabel, constraintsRight);
  }

  public Object getModel() {
    return model;
  }

  public BoardEditor getBoardEditor() {
    return MainWindow.getInstance().getCurrentBoardEditor();
  }

  public void updateCurrentBoardView() {
    BoardEditor editor = MainWindow.getInstance().getCurrentBoardEditor();

    if (editor != null) {
      editor.getBoardView().repaint();
    }
  }
}
