package rpgtoolkit.editor.board.types;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: geoff
 * Date: 19/12/10
 * Time: 00:47
 * To change this template use File | Settings | File Templates.
 */
public class BoardLight
{
    private long layer;
    private long type;
    private Color color;
    private ArrayList<Color> colors;
    private ArrayList<Point> points;

    public BoardLight()
    {

    }
    
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

    public void addPoint(Point point)
    {
        this.points.add(point);
    }
    
    public void addColor(Color color)
    {
        this.colors.add(color);
    }
}
