/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.character;

import javax.swing.table.AbstractTableModel;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.events.PlayerChangedEvent;
import net.rpgtoolkit.common.assets.listeners.PlayerChangeListener;

/**
 *
 * @author Joshua Michael Daly
 */
public class AnimationsTableModel extends AbstractTableModel implements PlayerChangeListener {

  public static final String[] STANDARD_GRAPHICS = {
    "South (Front View)", "North (Back View)", "East (Right View)", "West (Left View)",
    "North-West", "North-East", "South-West", "South-East",
    "Attack", "Defend", "Special Move", "Die", "Rest"
  };

  private final Player player;

  private static final String[] COLUMNS = {
    "Name", "Active Animation", "Idle Animation"
  };

  public AnimationsTableModel(Player player) {
    this.player = player;
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
    return (player.getStandardGraphics().size() + player.getCustomGraphics().size()) - 1;
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
          return player.getStandardGraphics().get(rowIndex);
        default:
          if (rowIndex < 8) {
            return player.getStandingGraphics().get(rowIndex);
          } else {
            return "NOT SUPPORTED";
          }
      }
    } else { // Custom Graphics.
      int index;
      switch (columnIndex) {
        case 0:
          index = rowIndex - STANDARD_GRAPHICS.length;
          if (player.getAccessoryNames().size() > index) {
            return player.getCustomGraphicNames().get(index);
          } else {
            return null;
          }
        case 1:
          index = rowIndex - STANDARD_GRAPHICS.length;
          if (player.getCustomGraphics().size() > index) {
             return player.getCustomGraphics().get(index);
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
        player.getCustomGraphicNames().set(customIndex, (String) value);
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
  public void playerChanged(PlayerChangedEvent e) {
  }

  @Override
  public void playerAnimationAdded(PlayerChangedEvent e) {
    fireTableDataChanged();
  }

  @Override
  public void playerAnimationUpdated(PlayerChangedEvent e) {
    fireTableDataChanged();
  }

  @Override
  public void playerAnimationRemoved(PlayerChangedEvent e) {
    fireTableDataChanged();
  }

}
