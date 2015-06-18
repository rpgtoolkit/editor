/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface Brush
{
    public int getAffectedLayers();
    
    public Rectangle getBounds();
    
    public void startPaint(MultiLayerContainer container, int layer);
    
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception;
    
    public void endPaint();
    
    public void drawPreview(Graphics2D g2d, AbstractBoardView view);
    
    public void drawPreview(Graphics2D g2d, Dimension dimension, 
            AbstractBoardView view);
    
    public boolean equals(Brush brush);
}
