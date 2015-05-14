package net.rpgtoolkit.common;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import net.rpgtoolkit.common.BoardImage;
import net.rpgtoolkit.common.BoardLight;
import net.rpgtoolkit.common.BoardProgram;
import net.rpgtoolkit.common.BoardSprite;
import net.rpgtoolkit.common.BoardVector;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class BoardLayer implements Cloneable
{

    /**
     * The name of the layer.
     */
    private String name;
    /**
     * What number is this layer on the board?
     */
    private int number;
    /**
     * A reference to the board this layer belongs to.
     */
    private Board board;
    /**
     * A list of all the tiles used on this layer.
     */
    private Tile[][] tiles;
    /**
     * A list of all the lights used on this layer.
     */
    private ArrayList<BoardLight> lights;
    /**
     * A list of all the vectors on this layer.
     */
    private ArrayList<BoardVector> vectors;
    /**
     * A list of all the programs on this layer.
     */
    private ArrayList<BoardProgram> programs;
    /**
     * A list of all the sprites on this layer.
     */
    private ArrayList<BoardSprite> sprites;
    /**
     * A list of all the images on this layer.
     */
    private ArrayList<BoardImage> images;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BoardLayer(Board parentBoard)
    {
        this.board = parentBoard;
        this.tiles = new Tile[this.board.getWidth()][this.board.getHeight()];
        this.lights = new ArrayList<>();
        this.vectors = new ArrayList<>();
        this.programs = new ArrayList<>();
        this.sprites = new ArrayList<>();
        this.images = new ArrayList<>();

        this.clearTiles();
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public Tile[][] getTiles()
    {
        return tiles;
    }

    public void setTiles(Tile[][] tiles)
    {
        this.tiles = tiles;
    }

    public ArrayList<BoardLight> getLights()
    {
        return lights;
    }

    public void setLights(ArrayList<BoardLight> lights)
    {
        this.lights = lights;
    }

    public ArrayList<BoardVector> getVectors()
    {
        return vectors;
    }

    public void setVectors(ArrayList<BoardVector> vectors)
    {
        this.vectors = vectors;
    }

    public ArrayList<BoardProgram> getPrograms()
    {
        return programs;
    }

    public void setPrograms(ArrayList<BoardProgram> programs)
    {
        this.programs = programs;
    }

    public ArrayList<BoardSprite> getSprites()
    {
        return sprites;
    }

    public void setSprites(ArrayList<BoardSprite> sprites)
    {
        this.sprites = sprites;
    }

    public ArrayList<BoardImage> getImages()
    {
        return images;
    }

    public void setImages(ArrayList<BoardImage> images)
    {
        this.images = images;
    }

    public Tile getTileAt(int x, int y)
    {
        return this.tiles[x][y];
    }

    public void setTileAt(int x, int y, Tile tile)
    {
        this.tiles[x][y] = tile;
        this.board.fireBoardChanged();
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public boolean contains(int x, int y)
    {
        if (x < 0 || y < 0)
        {
            return false;
        }

        return x < this.tiles.length && y < this.tiles[0].length;
    }

    public void moveLayerUp()
    {
        this.number++;

        for (BoardLight light : this.lights)
        {
            light.setLayer(this.number);
        }

        for (BoardVector vector : this.vectors)
        {
            vector.setLayer(this.number);
        }

        for (BoardProgram program : this.programs)
        {
            program.setLayer(this.number);
        }

        for (BoardSprite sprite : this.sprites)
        {
            sprite.setLayer(this.number);
        }

        for (BoardImage image : this.images)
        {
            image.setLayer(this.number);
        }
    }

    public void moveLayerDown()
    {
        this.number--;

        for (BoardLight light : this.lights)
        {
            light.setLayer(this.number);
        }

        for (BoardVector vector : this.vectors)
        {
            vector.setLayer(this.number);
        }

        for (BoardProgram program : this.programs)
        {
            program.setLayer(this.number);
        }

        for (BoardSprite sprite : this.sprites)
        {
            sprite.setLayer(this.number);
        }

        for (BoardImage image : this.images)
        {
            image.setLayer(this.number);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        BoardLayer layer = new BoardLayer(this.board);
        layer.images = (ArrayList<BoardImage>) this.images.clone();
        layer.lights = (ArrayList<BoardLight>) this.lights.clone();
        layer.name = this.name + "_clone";
        layer.number = this.number;
        layer.programs = (ArrayList<BoardProgram>) this.programs.clone();
        layer.sprites = (ArrayList<BoardSprite>) this.sprites.clone();
        layer.tiles = (Tile[][]) this.tiles.clone();
        layer.vectors = (ArrayList<BoardVector>) this.vectors.clone();
        layer.moveLayerUp();

        return layer;
    }

    public BoardVector findVectorAt(int x, int y)
    {
        // Create a small rectangle to represent the bounds of the mouse.
        Rectangle2D mouse = new Rectangle2D.Double(x - 5, y - 5, 10, 10);

        for (BoardVector vector : this.vectors)
        {
            // There are no lines.
            if (vector.getPoints().size() < 2)
            {
                continue;
            }

            for (int i = 0; i < vector.getPoints().size() - 1; i++)
            {
                // Build a line from the points in the polygon.
                Line2D line2D = new Line2D.Double(vector.getPoints().get(i),
                        vector.getPoints().get(i + 1));

                // See if the mouse intersects the line of the polygon.
                if (line2D.intersects(mouse))
                {
                    return vector;
                }
            }
        }

        return null;
    }
    
    public BoardVector removeVectorAt(int x, int y)
    {
        BoardVector vector = findVectorAt(x, y);
        
        if (vector != null)
        {
            this.vectors.remove(vector);
            this.board.fireBoardChanged();
        }
        
        return vector;
    }
    
    public BoardSprite findSpriteAt(int x, int y)
    {
        for (BoardSprite sprite : this.sprites)
        {
            if (sprite.getX() == x && sprite.getY() == y)
            {
                return sprite;
            }
        }
        
        return null;
    }
    
    public BoardSprite removeSpriteAt(int x, int y)
    {
        BoardSprite sprite = findSpriteAt(x, y);
        
        if (sprite != null)
        {
            this.sprites.remove(sprite);
            this.board.fireBoardChanged();
        }
        
        return sprite;
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void clearTiles()
    {
        int count = this.board.getWidth() * this.board.getHeight();
        Tile blankTile = new Tile();
        int x = 0;
        int y = 0;

        // Clear all the tiles.
        for (int i = 0; i < count; i++)
        {
            this.tiles[x][y] = blankTile;

            x++;
            if (x == this.board.getWidth())
            {
                x = 0;
                y++;
                if (y == this.board.getHeight())
                {
                    break;
                }
            }
        }
    }

}
