/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.character;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Joshua Michael Daly
 */
public class ProfilePanel extends AbstractImagePanel {

  public ProfilePanel() {
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, getWidth(), getHeight());

    g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);

    g.setColor(Color.LIGHT_GRAY);
    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
  }

}
