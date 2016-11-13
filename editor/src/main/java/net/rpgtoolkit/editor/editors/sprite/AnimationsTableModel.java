/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.sprite;

import javax.swing.table.AbstractTableModel;
import net.rpgtoolkit.common.assets.AbstractSprite;
import net.rpgtoolkit.common.assets.events.SpriteChangedEvent;
import net.rpgtoolkit.common.assets.listeners.SpriteChangeListener;

/**
 *
 * @author Joshua Michael Daly
 */
public class AnimationsTableModel extends AbstractTableModel implements SpriteChangeListener {

  public static final String[] STANDARD_GRAPHICS = {
    "South (Front View)", "North (Back View)", "East (Right View)", "West (Left View)",
    "North-West", "North-East", "South-West", "South-East",
    "Attack", "Defend", "Special Move", "Die", "Rest"
  };

  private final AbstractSprite sprite;

  private static final String[] COLUMNS = {
    "Name", "Active Animation", "Idle Animation"
  };

  public AnimationsTableModel(AbstractSprite sprite) {
    this.sprite = sprite;
  }

  /**
   *
   *
   * @param column
   * @return
   */
  @Override
  public Class getColumnClass(int column) {
    switch (column) {
      case 0:
        return String.class;
      case 1:
        return String.class;
      case 2:
        return String.class;
    }

    return null;
  }

  /**
   *
   *
   * @param column
   * @return
   */
  @Override
  public String getColumnName(int column) {
    return COLUMNS[column];
  }

  @Override
  public int getRowCount() {
    return (sprite.getStandardGraphics().size() + sprite.getCustomGraphics().size());
  }

  @Override
  public int getColumnCount() {
    return COLUMNS.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if (rowIndex < 13) { // Standard Graphics.
      switch (columnIndex) {
        case 0:
          return STANDARD_GRAPHICS[rowIndex];
        case 1:
          return sprite.getStandardGraphics().get(rowIndex);
        default:
          if (rowIndex < 8) {
            return sprite.getStandingGraphics().get(rowIndex);
          } else {
            return "NOT SUPPORTED";
          }
      }
    } else { // Custom Graphics.
      int index;
      switch (columnIndex) {
        case 0:
          index = rowIndex - STANDARD_GRAPHICS.length;
          if (sprite.getCustomGraphicsNames().size() > index) {
            return sprite.getCustomGraphicsNames().get(index);
          } else {
            return null;
          }
        case 1:
          index = rowIndex - STANDARD_GRAPHICS.length;
          if (sprite.getCustomGraphics().size() > index) {
             return sprite.getCustomGraphics().get(index);
          } else {
            return null;
          }
        default:
          return "NOT SUPPORTED";
      }
    }
  }

  /**
   *
   *
   * @param value
   * @param rowIndex
   * @param columnIndex
   */
  @Override
  public void setValueAt(Object value, int rowIndex, int columnIndex) {
    switch (columnIndex) {
      case 0:
        int customIndex = rowIndex - STANDARD_GRAPHICS.length;
        sprite.getCustomGraphicsNames().set(customIndex, (String) value);
        break;
      case 1:
        break;
      case 2:
        break;
    }

    fireTableCellUpdated(rowIndex, columnIndex);
  }

  /**
   *
   *
   * @param rowIndex
   * @param columnIndex
   * @return
   */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return rowIndex > STANDARD_GRAPHICS.length && columnIndex == 0;
  }

  @Override
  public void spriteChanged(SpriteChangedEvent e) {
  }

  @Override
  public void spriteAnimationAdded(SpriteChangedEvent e) {
    fireTableDataChanged();
  }

  @Override
  public void spriteAnimationUpdated(SpriteChangedEvent e) {
    fireTableDataChanged();
  }

  @Override
  public void spriteAnimationRemoved(SpriteChangedEvent e) {
    fireTableDataChanged();
  }

}
