/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui.actions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.editors.board.ShapeBrush;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class EraserAction extends AbstractAction {

  @Override
  public void actionPerformed(ActionEvent e) {
    BoardEditor.toggleSelectedOnBoardEditor();

    ShapeBrush brush = new ShapeBrush();
    brush.makeRectangleBrush(new Rectangle(0, 0, 1, 1));
    brush.setTile(new Tile());
    MainWindow.getInstance().setCurrentBrush(brush);
  }

}
