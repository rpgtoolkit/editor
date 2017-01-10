/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.sprite.listener;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.rpgtoolkit.common.assets.AbstractSprite;
import net.rpgtoolkit.editor.editors.sprite.AbstractSpriteEditor;
import net.rpgtoolkit.editor.editors.sprite.AnimationsTableModel;

/**
 *
 * @author Joshua Michael Daly
 */
public class AnimationListSelectionListener implements ListSelectionListener {
  
  private final AbstractSprite sprite;
  
  private final AbstractSpriteEditor spriteEditor;
  
  private final JTable animationsTable;
  
  public AnimationListSelectionListener(AbstractSpriteEditor editor) {
    spriteEditor = editor;
    sprite = editor.getSprite();
    animationsTable = editor.getAnimationsTable();
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      int row = animationsTable.getSelectedRow();
      if (row == -1) {
        spriteEditor.updateAnimatedPanel();

        spriteEditor.getBrowseButton().setEnabled(false);
        spriteEditor.getRemoveButton().setEnabled(false);
      } else {
        String path;
        if (row < AnimationsTableModel.STANDARD_GRAPHICS.length) {
          path = sprite.getStandardGraphics().get(row);
        } else {
          path = sprite.getCustomGraphics().get(
                  row - AnimationsTableModel.STANDARD_GRAPHICS.length);
        }

        spriteEditor.openAnimation(path);
        spriteEditor.getBrowseButton().setEnabled(true);

        if (row < AnimationsTableModel.STANDARD_GRAPHICS.length) {
          spriteEditor.getRemoveButton().setEnabled(false); // Cannot remove default graphics.
        } else {
          spriteEditor.getRemoveButton().setEnabled(true);
        }
      }
    }
  }

}
