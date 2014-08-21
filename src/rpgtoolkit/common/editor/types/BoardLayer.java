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

    public Board getParent()
    {
        return board;
    }

    public void setParent(Board parent)
    {
        this.board = parent;
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
        layer.images = (ArrayList<BoardImage>)this.images.clone();
        layer.lights = (ArrayList<BoardLight>)this.lights.clone();
        layer.name = this.name + "_clone";
        layer.number = this.number;
        layer.programs = (ArrayList<BoardProgram>)this.programs.clone();
        layer.sprites = (ArrayList<BoardSprite>)this.sprites.clone();
        layer.tiles = (Tile[][])this.tiles.clone();
        layer.vectors = (ArrayList<BoardVector>)this.vectors.clone();
        layer.moveLayerUp();
        
        return layer;
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void clearTiles()
    {
        int count = this.board.getWidth() * this.board.getHeight();
        int x = 0;
        int y = 0;

        // Clear all the tiles.
        for (int i = 0; i < count; i++)
        {
            this.tiles[x][y] = new Tile();

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
