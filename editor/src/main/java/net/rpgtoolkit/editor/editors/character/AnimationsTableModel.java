/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.character;

import javax.swing.table.AbstractTableModel;
import net.rpgtoolkit.common.assets.Player;

/**
 *
 * @author Joshua Michael Daly
 */
public class AnimationsTableModel extends AbstractTableModel {

  private final Player player;

  private static final String[] COLUMNS = {
    "Name", "Active Animation", "Idle Animation"
  };

  private static final String[] STANDARD_GRAPHICS = {
    "South (Front View)", "North (Back View)", "East (Right View)", "West (Left View)",
    "North-West", "North-East", "South-West", "South-East",
    "Attack", "Defend", "Special Move", "Die", "Rest"
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
      switch (columnIndex) {
        case 0:
          return player.getCustomGraphicNames().get(rowIndex - STANDARD_GRAPHICS.length);
        case 1:
          return player.getCustomGraphics().get(rowIndex - STANDARD_GRAPHICS.length);
        default:
          return "NOT SUPPORTED";
      }
    }
  }

}
