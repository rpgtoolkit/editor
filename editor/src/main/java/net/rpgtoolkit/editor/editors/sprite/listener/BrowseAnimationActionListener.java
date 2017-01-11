/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.sprite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AbstractSprite;
import net.rpgtoolkit.editor.editors.sprite.AbstractSpriteEditor;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.editor.utilities.EditorFileManager;

/**
 *
 * @author Joshua Michael Daly
 */
public class BrowseAnimationActionListener implements ActionListener {
  
  private final AbstractSprite sprite;
  
  private final AbstractSpriteEditor spriteEditor;
  
  private final JTable animationsTable;
  
  public BrowseAnimationActionListener(AbstractSpriteEditor editor) {
    spriteEditor = editor;
    sprite = editor.getSprite();
    animationsTable = editor.getAnimationsTable();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int row = animationsTable.getSelectedRow();
    if (row < 0) {
      return;
    }

    if (row < sprite.getStandingGraphics().size()) {
      Object[] options = {"Active Animation", "Idle Animation", "Cancel"};
      int result = JOptionPane.showOptionDialog(
              MainWindow.getInstance(),
              "Select Animation type to update.",
              "Update Animation",
              JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              options,
              options[0]
      );

      if (result != -1 && result != 2) { // Cancel
        String path = EditorFileManager.browseByTypeRelative(Animation.class);
        if (path != null) {
          switch (result) { // Active Animation
            case 0:
              sprite.updateStandardGraphics(row, path);
              break;
            case 1: // Idle Animation
              sprite.updateStandingGraphics(row, path);
              break;
          }

          spriteEditor.openAnimation(path);
        }
      }
    } else {
      String path = EditorFileManager.browseByTypeRelative(Animation.class);
      if (path != null) {
        if (row < sprite.getStandardGraphics().size()) {
          sprite.updateStandardGraphics(row, path);
        } else {
          int customIndex = row - sprite.getStandardGraphics().size();
          sprite.updateCustomGraphics(customIndex, path);
        }

        spriteEditor.openAnimation(path);
      }
    }
  }

}
