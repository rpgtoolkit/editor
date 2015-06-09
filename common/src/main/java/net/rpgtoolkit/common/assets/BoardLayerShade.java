/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

import java.awt.*;

public class BoardLayerShade
{
    private Color colour;
    private long layer;

    public Color getColour()
    {
        return colour;
    }

    public void setColour(Color colour)
    {
        this.colour = colour;
    }

    public long getLayer()
    {
        return layer;
    }

    public void setLayer(long layer)
    {
        this.layer = layer;
    }

    public BoardLayerShade()
    {

    }
    
    public BoardLayerShade(Color color, long layer)
    {
        this.colour = color;
        this.layer = layer;
    }
    
    public BoardLayerShade(int r, int g, int b, long layer)
    {
        this.colour = new Color (r, g, b);
        this.layer = layer;
    }
}

