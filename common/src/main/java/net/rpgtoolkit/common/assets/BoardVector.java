/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import net.rpgtoolkit.common.Selectable;

/**
 *
 * TT_NULL = -1 'To denote empty slot in editor. 
 * TT_NORMAL = 0 'See TILE_TYPE enumeration, board conversion.h 
 * TT_SOLID = 1 
 * TT_UNDER = 2 
 * TT_UNIDIRECTIONAL = 4 'Incomplete / unnecessary. 
 * TT_STAIRS = 8 
 * TT_WAYPOINT = 16
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class BoardVector implements Cloneable, Selectable
{

    // Appears in the TableModel.
    private int layer;                  //layer the vector is on
    private int attributes;             //???
    private boolean isClosed;           //whether the vector is closed
    private String handle;              //vector's handle
    private int tileType;

    private ArrayList<Point> points;    //the points in the vector
    private Polygon polygon;
    private boolean selected;

    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    public BoardVector()
    {
        this.layer = 0;
        this.attributes = 0;
        this.isClosed = false;
        this.points = new ArrayList<>();
        this.handle = "";
        this.tileType = 1;
        this.polygon = new Polygon();
        this.selected = false;
    }

    /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    public ArrayList<Point> getPoints()
    {
        return points;
    }

    public int getTileType()
    {
        return tileType;
    }

    public int getPointCount()
    {
        return (points.size());
    }

    public int getPointX(int index)
    {
        return (int) points.get(index).getX();
    }

    public int getPointY(int index)
    {
        return (int) points.get(index).getY();
    }

    public int getLayer()
    {
        return (layer);
    }

    public int getAttributes()
    {
        return (attributes);
    }

    public String getHandle()
    {
        return (handle);
    }

    public boolean isClosed()
    {
        return (isClosed);
    }

    public void addPoint(long xVal, long yVal)
    {
        points.add(new Point((int) xVal, (int) yVal));
        polygon.addPoint((int) xVal, (int) yVal);
    }

    public void setLayer(int layer)
    {
        this.layer = layer;
    }

    public void setAttributes(int attributes)
    {
        this.attributes = attributes;
    }

    public void setClosed(boolean closed)
    {
        isClosed = closed;
    }

    public void setHandle(String handle)
    {
        this.handle = handle;
    }

    public void setTileType(int tileType)
    {
        this.tileType = tileType;
    }

    public Polygon getPolygon()
    {
        return polygon;
    }

    public void setIsClosed(boolean isClosed)
    {
        this.isClosed = isClosed;
    }

    public void setPoints(ArrayList<Point> points)
    {
        this.points = points;
    }

    public void setPolygon(Polygon polygon)
    {
        this.polygon = polygon;
    }

    @Override
    public boolean isSelected()
    {
        return this.selected;
    }
    
    @Override
    public void setSelected(boolean state)
    {
        this.selected = state;
    }

    /*
     * ************************************************************************* 
     * Public Methods
     * *************************************************************************
     */
    /**
     *
     * @return @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        super.clone();

        BoardVector clone = new BoardVector();
        clone.layer = this.layer;
        clone.attributes = this.attributes;
        clone.handle = this.handle;
        clone.isClosed = this.isClosed;
        clone.points = (ArrayList<Point>) this.points.clone();
        clone.polygon = this.polygon;
        clone.tileType = this.tileType;

        return clone;
    }
}
