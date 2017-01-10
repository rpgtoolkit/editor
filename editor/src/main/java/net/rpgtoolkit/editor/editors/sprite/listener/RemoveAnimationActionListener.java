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
import javax.swing.JTable;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AbstractSprite;
import net.rpgtoolkit.editor.editors.sprite.AbstractSpriteEditor;
import net.rpgtoolkit.editor.editors.sprite.AnimationsTableModel;

/**
 *
 * @author Joshua Michael Daly
 */
public class RemoveAnimationActionListener implements ActionListener {
  
  private final AbstractSpriteEditor spriteEditor;
  
  private final AbstractSprite sprite;
  
  private final JTable animationsTable;
  private final AnimationsTableModel animationsTableModel;
  
  public RemoveAnimationActionListener(AbstractSpriteEditor editor) {
    spriteEditor = editor;
    sprite = spriteEditor.getSprite();
    animationsTable = spriteEditor.getAnimationsTable();
    animationsTableModel = (AnimationsTableModel) animationsTable.getModel();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Animation selectedAnim = spriteEditor.getSelectedAnim();
    
    int row = animationsTable.getSelectedRow();
    if (row >= 0) {
      if (row < AnimationsTableModel.STANDARD_GRAPHICS.length) {
        if (selectedAnim != null) {
          sprite.getStandardGraphics().set(row, "");
        }
      } else if (row < AnimationsTableModel.STANDARD_GRAPHICS.length
              + sprite.getCustomGraphicsNames().size()) {
        int customIndex = row - AnimationsTableModel.STANDARD_GRAPHICS.length;
        sprite.getCustomGraphicsNames().remove(customIndex);
        sprite.removeCustomGraphics(customIndex);

        if (row > 0) {
          if (row == animationsTableModel.getRowCount()) {
            row--;
          }

          animationsTable.scrollRectToVisible(animationsTable.getCellRect(row, 0, true));
        }
      }
    }

    spriteEditor.setSelectedAnim(null);
    spriteEditor.updateAnimatedPanel();
  }

}
