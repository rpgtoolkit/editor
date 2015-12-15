/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.character;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import net.rpgtoolkit.editor.ui.AnimatedPanel;

/**
 *
 * @author Joshua Michael Daly
 */
public class AnimationsTablePanel extends JPanel {
  
  private final ProfilePanel profilePanel;
  private final AnimatedPanel animatedPanel;
  
  public AnimationsTablePanel(ProfilePanel profilePanel, AnimatedPanel animatedPanel) {
    super(new BorderLayout());
    this.profilePanel = profilePanel;
    this.animatedPanel = animatedPanel;
  }

  @Override
  public Dimension getPreferredSize() {
    return calculateDimensions();
  }

  @Override
  public Dimension getMaximumSize() {
    return calculateDimensions();
  }

  @Override
  public Dimension getMinimumSize() {
    return calculateDimensions();
  }
  
  private Dimension calculateDimensions() {
    int width = animatedPanel.getWidth();
    int height = profilePanel.getHeight() - animatedPanel.getHeight();
    
    return new Dimension(width, height);
  }

}
