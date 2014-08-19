package rpgtoolkit.common.editor.types;

import java.util.ArrayList;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.editor.board.types.BoardImage;
import rpgtoolkit.editor.board.types.BoardLight;
import rpgtoolkit.editor.board.types.BoardProgram;
import rpgtoolkit.editor.board.types.BoardSprite;
import rpgtoolkit.editor.board.types.BoardVector;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardLayer
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
    private Board parent;
    /**
     * A list of all the tiles used on this layer.
     */
    private ArrayList<Tile> tiles;
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
        this.parent = parentBoard;
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

    public Board getParent()
    {
        return parent;
    }

    public void setParent(Board parent)
    {
        this.parent = parent;
    }

    public ArrayList<Tile> getTiles()
    {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles)
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
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    
    
    
}
