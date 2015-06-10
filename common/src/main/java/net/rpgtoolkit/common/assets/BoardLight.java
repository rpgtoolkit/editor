/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class BoardLight implements Cloneable
{
    private long layer;
    private long type;
    private Color color;
    private ArrayList<Color> colors;
    private ArrayList<Point> points;

    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    
    public BoardLight()
    {

    }
    
    /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    
    public long getLayer()
    {
        return layer;
    }

    public long getType()
    {
        return type;
    }

    public Color getColor()
    {
        return color;
    }
    
    public ArrayList<Color> getColors()
    {
        return colors;
    }

    public ArrayList<Point> getPoints()
    {
        return points;
    }

    public void setLayer(long layer)
    {
        this.layer = layer;
    }

    public void setType(long eType)
    {
        this.type = eType;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    public void setColors(ArrayList<Color> colors)
    {
        this.colors = colors;
    }
    
    public void setPoints(ArrayList<Point> points)
    {
        this.points = points;
    }

    public void addPoint(Point point)
    {
        this.points.add(point);
    }
    
    public void addColor(Color color)
    {
        this.colors.add(color);
    }
    
    /*
     * ************************************************************************* 
     * Public Methods
     * *************************************************************************
     */
    
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        super.clone();
        
        BoardLight clone = new BoardLight();
        clone.setLayer(this.getLayer());
        clone.setType(this.type);
        clone.setColor(this.color);
        clone.setColors((ArrayList<Color>)this.colors.clone());
        clone.setPoints((ArrayList < Point>)this.points.clone());
        
        return clone;
    }
}
