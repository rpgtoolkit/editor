package net.rpgtoolkit.common.assets;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import net.rpgtoolkit.common.assets.BoardLayer;
import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.common.utilities.TileSetCache;
import net.rpgtoolkit.common.assets.BoardChangeListener;
import net.rpgtoolkit.common.assets.BoardChangedEvent;
import net.rpgtoolkit.common.assets.BoardImage;
import net.rpgtoolkit.common.assets.BoardLayerShade;
import net.rpgtoolkit.common.assets.BoardLight;
import net.rpgtoolkit.common.assets.BoardProgram;
import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.CorruptFileException;
import net.rpgtoolkit.common.utilities.BinaryIO;

/**
 * There is a bug in this file with regard to IO read/writes, saving a board
 * file more than once corrupts the trailing data, see end of open and save
 * methods for more details.
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public final class Board extends BasicType
{

    // Non-IO
    private final LinkedList<BoardChangeListener> boardChangeListeners = new LinkedList<>();
    private final LinkedList<BoardLayer> layers = new LinkedList<>();

    // Constants
    private final String FILE_HEADER = "RPGTLKIT BOARD";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 4;
    private final int STANDARD = 1;
    private final int ISO_STACKED = 2;
    private final int ISO_ROTATED = 6;
    
    // Variables
    private int width;
    private int height;
    private int layerCount;
    private int coordinateType;
    private ArrayList<String> tileIndex; // Contains string names e.g. default.tst1
    private HashMap<String, TileSet> tileSetMap;
    private ArrayList<Tile> loadedTilesIndex; // Contains tile objects of e.g. default.tst1
    private int[][][] boardDimensions;
    private byte[] tileType;
    private int ubShading;
    private long shadingLayer;
    private ArrayList<BoardLayerShade> tileShading;
    private ArrayList<BoardImage> images;
    private ArrayList<BoardImage> spriteImages;
    private ArrayList<BoardImage> backgroundImages;
    private long backgroundColour;
    private ArrayList<BoardProgram> programs;
    private ArrayList<BoardLight> lights;
    private ArrayList<BoardVector> vectors;
    private ArrayList<BoardSprite> sprites;
    private ArrayList<String> threads;
    private ArrayList<String> constants;
    private ArrayList<String> layerTitles;
    private ArrayList<String> directionalLinks;
    private String backgroundMusic;
    private String firstRunProgram;
    private String battleBackground;
    private int enemyBattleLevel;
    private boolean allowBattles;
    private boolean allowSaving;
    private Color ambientEffect;
    private int startingPositionX;
    private int startingPositionY;
    private int startingLayer;
    private String lastLoadedTileSet;
    private TileSet tileSet;

    // Stuff that is randomly skipped?
    private byte randomByte;
    private int associatedVector;
    private int imageTransluceny;

    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    /**
     * Create a blank board (for editor use)
     */
    public Board()
    {

    }

    /**
     * Opens the specified board
     *
     * @param fileName Board file to open
     */
    public Board(File fileName) throws FileNotFoundException
    {
        super(fileName);
        System.out.println("Loading Board " + fileName);
        this.binaryIO = new BinaryIO(new FileInputStream(fileName));
        this.open();
    }

    /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    public LinkedList<BoardLayer> getLayers()
    {
        return this.layers;
    }
    
    public Tile getTileFromIndex(int index)
    {
        return loadedTilesIndex.get(index);
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public ArrayList<String> getTileIndex()
    {
        return tileIndex;
    }

    public void setTileIndex(ArrayList<String> tileIndex)
    {
        this.tileIndex = tileIndex;
    }

    public ArrayList<BoardImage> getBackgroundImages()
    {
        return backgroundImages;
    }

    public void setBackgroundImages(ArrayList<BoardImage> backgroundImages)
    {
        this.backgroundImages = backgroundImages;
    }

    public ArrayList<BoardProgram> getPrograms()
    {
        return programs;
    }

    public void setPrograms(ArrayList<BoardProgram> programs)
    {
        this.programs = programs;
    }

    public ArrayList<BoardVector> getVectors()
    {
        return vectors;
    }

    public void setVectors(ArrayList<BoardVector> vectors)
    {
        this.vectors = vectors;
    }

    public ArrayList<BoardSprite> getSprites()
    {
        return sprites;
    }

    public void setSprites(ArrayList<BoardSprite> sprites)
    {
        this.sprites = sprites;
    }

    public int getStartingPositionX()
    {
        return startingPositionX;
    }

    public void setStartingPositionX(int startingPositionX)
    {
        this.startingPositionX = startingPositionX;
    }

    public int getStartingPositionY()
    {
        return startingPositionY;
    }

    public void setStartingPositionY(int startingPositionY)
    {
        this.startingPositionY = startingPositionY;
    }

    public int getStartingLayer()
    {
        return startingLayer;
    }

    public void setStartingLayer(int startingLayer)
    {
        this.startingLayer = startingLayer;
    }

    public int getIndexAtLocation(int x, int y, int z)
    {
        return this.layers.get(z).getTiles()[x][y].getIndex();
        //return boardDimensions[x][y][z];
    }

    public int getLayerCount()
    {
        return layerCount;
    }

    public void setLayerCount(int count)
    {
        this.layerCount = count;
    }

    public int getCoordinateType()
    {
        return coordinateType;
    }

    public void setCoordinateType(int coordinateType)
    {
        this.coordinateType = coordinateType;
    }

    public HashMap<String, TileSet> getTileSetMap()
    {
        return tileSetMap;
    }

    public void setTileSetMap(HashMap<String, TileSet> tileSetMap)
    {
        this.tileSetMap = tileSetMap;
    }

    public ArrayList<Tile> getLoadedTilesIndex()
    {
        return loadedTilesIndex;
    }

    public void setLoadedTilesIndex(ArrayList<Tile> loadedTilesIndex)
    {
        this.loadedTilesIndex = loadedTilesIndex;
    }

    public int[][][] getBoardDimensions()
    {
        return boardDimensions;
    }

    public void setBoardDimensions(int[][][] boardDimensions)
    {
        this.boardDimensions = boardDimensions;
    }

    public byte[] getTileType()
    {
        return tileType;
    }

    public void setTileType(byte[] tileType)
    {
        this.tileType = tileType;
    }

    public ArrayList<BoardLayerShade> getTileShading()
    {
        return tileShading;
    }

    public void setTileShading(ArrayList<BoardLayerShade> tileShading)
    {
        this.tileShading = tileShading;
    }

    public ArrayList<BoardImage> getImages()
    {
        return images;
    }

    public void setImages(ArrayList<BoardImage> images)
    {
        this.images = images;
    }

    public ArrayList<BoardImage> getSpriteImages()
    {
        return spriteImages;
    }

    public void setSpriteImages(ArrayList<BoardImage> spriteImages)
    {
        this.spriteImages = spriteImages;
    }

    public long getBackgroundColour()
    {
        return backgroundColour;
    }

    public void setBackgroundColour(long backgroundColour)
    {
        this.backgroundColour = backgroundColour;
    }

    public ArrayList<BoardLight> getLights()
    {
        return lights;
    }

    public void setLights(ArrayList<BoardLight> lights)
    {
        this.lights = lights;
    }

    public ArrayList<String> getThreads()
    {
        return threads;
    }

    public void setThreads(ArrayList<String> threads)
    {
        this.threads = threads;
    }

    public ArrayList<String> getConstants()
    {
        return constants;
    }

    public void setConstants(ArrayList<String> constants)
    {
        this.constants = constants;
    }

    public ArrayList<String> getLayerTitles()
    {
        return layerTitles;
    }

    public void setLayerTitles(ArrayList<String> layerTitles)
    {
        this.layerTitles = layerTitles;
    }

    public String getLayerTitle(int index)
    {
        return this.layerTitles.get(index);
    }

    public void setLayerTitle(int index, String title)
    {
        this.layerTitles.set(index, title);
        this.fireBoardChanged();
    }

    public ArrayList<String> getDirectionalLinks()
    {
        return directionalLinks;
    }

    public void setDirectionalLinks(ArrayList<String> directionalLinks)
    {
        this.directionalLinks = directionalLinks;
    }

    public String getBackgroundMusic()
    {
        return backgroundMusic;
    }

    public void setBackgroundMusic(String backgroundMusic)
    {
        this.backgroundMusic = backgroundMusic;
    }

    public String getFirstRunProgram()
    {
        return firstRunProgram;
    }

    public void setFirstRunProgram(String firstRunProgram)
    {
        this.firstRunProgram = firstRunProgram;
    }

    public String getBattleBackground()
    {
        return battleBackground;
    }

    public void setBattleBackground(String battleBackground)
    {
        this.battleBackground = battleBackground;
    }

    public int getEnemyBattleLevel()
    {
        return enemyBattleLevel;
    }

    public void setEnemyBattleLevel(int enemyBattleLevel)
    {
        this.enemyBattleLevel = enemyBattleLevel;
    }

    public boolean isAllowBattles()
    {
        return allowBattles;
    }

    public void setAllowBattles(boolean allowBattles)
    {
        this.allowBattles = allowBattles;
    }

    public boolean isAllowSaving()
    {
        return allowSaving;
    }

    public void setAllowSaving(boolean allowSaving)
    {
        this.allowSaving = allowSaving;
    }

    public Color getAmbientEffect()
    {
        return ambientEffect;
    }

    public void setAmbientEffect(Color ambientEffect)
    {
        this.ambientEffect = ambientEffect;
    }

    public String getLastLoadedTileSet()
    {
        return lastLoadedTileSet;
    }

    public void setLastLoadedTileSet(String lastLoadedTileSet)
    {
        this.lastLoadedTileSet = lastLoadedTileSet;
    }

    public TileSet getTileSet()
    {
        return tileSet;
    }

    public void setTileSet(TileSet tileSet)
    {
        this.tileSet = tileSet;
    }

    /*
     * ************************************************************************* 
     * Public Methods
     * *************************************************************************
     */
    /**
     * Method to performing opening of the board
     * 
     * TODO: Need to account for the new way layers are stored at run time!
     *
     * @return true for success, false for failure
     */
    public boolean open()
    {
        try
        {
            tileIndex = new ArrayList<>();
            tileSetMap = new HashMap<>();
            loadedTilesIndex = new ArrayList<>();
            tileShading = new ArrayList<>();
            images = new ArrayList<>();
            spriteImages = new ArrayList<>();
            programs = new ArrayList<>();
            threads = new ArrayList<>();
            lights = new ArrayList<>();
            vectors = new ArrayList<>();
            sprites = new ArrayList<>();
            constants = new ArrayList<>();
            layerTitles = new ArrayList<>();
            directionalLinks = new ArrayList<>();
            backgroundImages = new ArrayList<>();
            tileSet = new TileSet();

            if (binaryIO.readBinaryString().equals(FILE_HEADER))
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();

                width = binaryIO.readBinaryInteger();
                height = binaryIO.readBinaryInteger();
                layerCount = binaryIO.readBinaryInteger();
                coordinateType = binaryIO.readBinaryInteger();

                if (coordinateType == ISO_ROTATED)
                {
                    int tmpWidth = width;
                    int tmpHeight = height;

                    width += tmpHeight;
                    height += tmpWidth;
                }

                boardDimensions = new int[width][height][layerCount];

                // Total number of distinct tile types used, if we add a 
                // new tile we will have to check if the tileIndex already
                // contains the name of the tile e.g. default.tst2
                int lookUpTableSize = binaryIO.readBinaryInteger();

                // Appears to be a random "" null string at the start of
                // the look up table. To avoid any issues we must "eat" this
                // null string.
                this.randomByte = binaryIO.readBinaryByte();

                for (int i = 0; i < lookUpTableSize; i++)
                {
                    // Read in the name of the tiles used on this board.
                    tileIndex.add(binaryIO.readBinaryString());
                }

                int totalTiles = width * height * layerCount;

                int x = 0;
                int y = 0;
                int z = 0;
                int tilesLoaded = 0;

                while (tilesLoaded < totalTiles)
                {
                    int index = binaryIO.readBinaryInteger();
                    int count = 1;

                    if (index < 0) // compressed data
                    {
                        count = -index;
                        index = binaryIO.readBinaryInteger();
                    }

                    for (int i = 0; i < count; i++)
                    {
                        boardDimensions[x][y][z] = index;
                        tilesLoaded++;
                        x++;
                        if (x == width)
                        {
                            x = 0;
                            y++;
                            if (y == height)
                            {
                                y = 0;
                                z++;
                            }
                        }
                    }
                }

                /* Tile Shading Data Notes
                 *
                 * Unsure why ubShading and then shadingLayer are read since 
                 * the shading is only applied to one(?)
                 * layer, perhaps this was to allow for more shading layerCount in 
                 * the future, however it is now unnecessary
                 * and so the ubShading will be ignored.
                 */
                this.ubShading = binaryIO.readBinaryInteger();

                // apply shading from this layer down
                this.shadingLayer = binaryIO.readBinaryLong();

                // WE ARE ASSUMING NO SHADING FOR NOW!
                // only one layer, so total tiles is just w * h
                int totalShading = width * height;
                int shadingLoaded = 0;

                while (shadingLoaded < totalShading)
                {
                    int count = binaryIO.readBinaryInteger();
                    shadingLoaded += count;

                    int red = binaryIO.readBinaryInteger();
                    int green = binaryIO.readBinaryInteger();
                    int blue = binaryIO.readBinaryInteger();

                    this.tileShading.add(new BoardLayerShade(red, green, blue,
                            count));
                }

                // Lights
                int numberLights = binaryIO.readBinaryInteger();
                for (int i = 0; i < numberLights + 1; i++)
                {
                    BoardLight newLight = new BoardLight();

                    newLight.setLayer(binaryIO.readBinaryLong());
                    newLight.setType(binaryIO.readBinaryLong());

                    int numberPoints = binaryIO.readBinaryInteger();
                    for (int j = 0; j < numberPoints + 1; j++)
                    {
                        newLight.addPoint(new Point(
                                (int) binaryIO.readBinaryLong(),
                                (int) binaryIO.readBinaryLong()));
                    }

                    int numColors = binaryIO.readBinaryInteger();
                    for (int j = 0; j < numColors + 1; j++)
                    {
                        newLight.addColor(new Color(
                                binaryIO.readBinaryInteger(),
                                binaryIO.readBinaryInteger(),
                                binaryIO.readBinaryInteger()));
                    }

                    this.lights.add(newLight);
                }

                // Vector count is one less than it should be so +1 
                // to vectors all round!
                int numberVectors = binaryIO.readBinaryInteger();
                for (int i = 0; i < numberVectors + 1; i++)
                {
                    BoardVector newVector = new BoardVector();

                    // How Many Points in said vector?
                    int numberPoints = binaryIO.readBinaryInteger();

                    for (int j = 0; j < numberPoints + 1; j++)
                    {
                        newVector.addPoint(binaryIO.readBinaryLong(),
                                binaryIO.readBinaryLong());
                    }

                    newVector.setAttributes(binaryIO.readBinaryInteger());
                    newVector.setClosed(binaryIO.readBinaryInteger() == 1);
                    newVector.setLayer(binaryIO.readBinaryInteger());
                    newVector.setTileType(binaryIO.readBinaryInteger());
                    newVector.setHandle(binaryIO.readBinaryString());

                    vectors.add(newVector);
                }

                // Programs
                int numberPrograms = binaryIO.readBinaryInteger();

                for (int i = 0; i < numberPrograms + 1; i++)
                {
                    BoardProgram newProgram = new BoardProgram();

                    newProgram.setFileName(binaryIO.readBinaryString());
                    newProgram.setGraphic(binaryIO.readBinaryString());
                    newProgram.setInitialVariable(binaryIO.readBinaryString());
                    newProgram.setInitialValue(binaryIO.readBinaryString());
                    newProgram.setFinalVariable(binaryIO.readBinaryString());
                    newProgram.setFinalValue(binaryIO.readBinaryString());
                    newProgram.setActivate(binaryIO.readBinaryInteger());
                    newProgram.setActivationType(binaryIO.readBinaryInteger());
                    newProgram.setDistanceRepeat(binaryIO.readBinaryInteger());
                    newProgram.setLayer(binaryIO.readBinaryInteger());

                    BoardVector programVector = new BoardVector();

                    int numberPoints = binaryIO.readBinaryInteger();

                    for (int j = 0; j < numberPoints + 1; j++)
                    {
                        programVector.addPoint(binaryIO.readBinaryLong(),
                                binaryIO.readBinaryLong());
                    }

                    programVector.setClosed(binaryIO.readBinaryInteger() == 1);
                    programVector.setHandle(binaryIO.readBinaryString());

                    newProgram.setVector(programVector);
                    programs.add(newProgram);
                }

                // Sprites
                int numberSprites = binaryIO.readBinaryInteger();

                for (int i = 0; i < numberSprites + 1; i++)
                {
                    BoardSprite newSprite = new BoardSprite();

                    newSprite.setFileName(binaryIO.readBinaryString());
                    newSprite.setActivationProgram(binaryIO.readBinaryString());
                    newSprite.setMultitaskingProgram(binaryIO.readBinaryString());
                    newSprite.setInitialVariable(binaryIO.readBinaryString());
                    newSprite.setInitialValue(binaryIO.readBinaryString());
                    newSprite.setFinalVariable(binaryIO.readBinaryString());
                    newSprite.setFinalValue(binaryIO.readBinaryString());
                    newSprite.setLoadingVariable(binaryIO.readBinaryString());
                    newSprite.setLoadingValue(binaryIO.readBinaryString());
                    newSprite.setActivate(binaryIO.readBinaryInteger());
                    newSprite.setActivationType(binaryIO.readBinaryInteger());
                    newSprite.setX(binaryIO.readBinaryInteger());
                    newSprite.setY(binaryIO.readBinaryInteger());
                    newSprite.setLayer(binaryIO.readBinaryInteger());

                    // skip one INT of data
                    this.associatedVector = binaryIO.readBinaryInteger();

                    sprites.add(newSprite);
                }

                //Images
                int numberImage = binaryIO.readBinaryInteger();

                for (int i = 0; i < numberImage + 1; i++)
                {
                    BoardImage newImage = new BoardImage();
                    newImage.setFileName(binaryIO.readBinaryString());
                    newImage.setBoundLeft(binaryIO.readBinaryLong());
                    newImage.setBoundTop(binaryIO.readBinaryLong());
                    newImage.setLayer(binaryIO.readBinaryInteger());
                    newImage.setDrawType(binaryIO.readBinaryInteger());
                    newImage.setTransparentColour(binaryIO.readBinaryLong());

                    // skip one INT of data
                    this.imageTransluceny = this.binaryIO.readBinaryInteger();

                    images.add(newImage);
                }

                // Threads
                int numberThread = binaryIO.readBinaryInteger();

                for (int i = 0; i < numberThread + 1; i++)
                {
                    threads.add(binaryIO.readBinaryString());
                }

                // Constants
                int numberConstants = binaryIO.readBinaryInteger();

                for (int i = 0; i < numberConstants + 1; i++)
                {
                    constants.add(binaryIO.readBinaryString());
                }

                // Layer Titles
                // Geoff's random +1 here causes problems at save time!
                //for (int i = 0; i < layerCount + 1; i++) 
                for (int i = 0; i < layerCount; i++)
                {
                    layerTitles.add(binaryIO.readBinaryString());
                }

                for (int i = 0; i < 4; i++)
                {
                    directionalLinks.add(binaryIO.readBinaryString());
                }

                BoardImage backgroundImage = new BoardImage();
                backgroundImage.setFileName(binaryIO.readBinaryString());
                backgroundImage.setDrawType(binaryIO.readBinaryLong());

                // 1 Pixel for every 10 the player moves
                backgroundImage.setScrollRatio(20);

                if (!backgroundImage.getFileName().equals(""))
                {
                    backgroundImages.add(backgroundImage);
                }

                backgroundColour = binaryIO.readBinaryLong();
                backgroundMusic = binaryIO.readBinaryString();

                firstRunProgram = binaryIO.readBinaryString();
                battleBackground = binaryIO.readBinaryString();
                enemyBattleLevel = binaryIO.readBinaryInteger();
                allowBattles = binaryIO.readBinaryInteger() == -1;
                allowSaving = !(binaryIO.readBinaryInteger() == -1);

                try
                {
                    ambientEffect = new Color(
                            binaryIO.readBinaryInteger(),
                            binaryIO.readBinaryInteger(),
                            binaryIO.readBinaryInteger());
                }
                catch (Exception e)
                {
                    // Ignore it for now...
                    ambientEffect = new Color(0, 0, 0);
                }

                startingPositionX = binaryIO.readBinaryInteger();
                startingPositionY = binaryIO.readBinaryInteger();
                startingLayer = binaryIO.readBinaryInteger();
            }

            binaryIO.closeInput();
        }
        catch (CorruptFileException e)
        {

        }

        return true;
    }

    /**
     * This save routine does not work correctly, or so it appears! It is
     * identical with regard to the new open routine and the previous save
     * routine!
     * 
     * @return
     */
    public boolean save()
    {
        this.updateBoardIO();
        
        try
        {
            this.outputStream = new FileOutputStream(this.file);
            this.binaryIO.setOutputStream(this.outputStream);

            this.binaryIO.writeBinaryString(this.FILE_HEADER);
            this.binaryIO.writeBinaryInteger(this.MAJOR_VERSION);
            this.binaryIO.writeBinaryInteger(this.MINOR_VERSION);

            this.binaryIO.writeBinaryInteger(this.width);
            this.binaryIO.writeBinaryInteger(this.height);
            this.binaryIO.writeBinaryInteger(this.layerCount);
            this.binaryIO.writeBinaryInteger(this.coordinateType);

            this.binaryIO.writeBinaryInteger(this.tileIndex.size());
            this.binaryIO.writeBinaryByte(this.randomByte);

            for (String tile : this.tileIndex)
            {
                this.binaryIO.writeBinaryString(tile);
            }

            // Tiles
            int x;
            int y;
            int z;
            int count;
            int index;
            int[] array;

            for (int k = 0; k < layerCount; k++)
            {
                for (int j = 0; j < height; j++)
                {
                    for (int i = 0; i < width; i++)
                    {
                        x = i;
                        y = j;
                        z = k;

                        array = this.findDuplicateTiles(x, y, z);

                        count = array[0];
                        index = this.boardDimensions[x][y][z];

                        if (count > 1)
                        {
                            this.binaryIO.writeBinaryInteger(-count);
                            this.binaryIO.writeBinaryInteger(index);

                            i = array[1] - 1;
                            j = array[2];
                            k = array[3];
                        }
                        else
                        {
                            this.binaryIO.writeBinaryInteger(index);
                        }
                    }
                }
            }

            // Shading
            this.binaryIO.writeBinaryInteger(this.ubShading);
            this.binaryIO.writeBinaryLong(this.shadingLayer);

            for (BoardLayerShade layerShade : this.tileShading)
            {
                this.binaryIO.writeBinaryInteger((int) layerShade.getLayer());
                this.binaryIO.writeBinaryInteger(layerShade.getColour().getRed());
                this.binaryIO.writeBinaryInteger(layerShade.getColour().getGreen());
                this.binaryIO.writeBinaryInteger(layerShade.getColour().getBlue());
            }

            // Lights
            this.binaryIO.writeBinaryInteger(this.lights.size() - 1);

            for (BoardLight light : this.lights)
            {
                this.binaryIO.writeBinaryLong(light.getLayer());
                this.binaryIO.writeBinaryLong(light.getType());

                for (Point point : light.getPoints())
                {
                    this.binaryIO.writeBinaryLong(point.x);
                    this.binaryIO.writeBinaryLong(point.y);
                }

                for (Color color : light.getColors())
                {
                    this.binaryIO.writeBinaryInteger(color.getRed());
                    this.binaryIO.writeBinaryInteger(color.getGreen());
                    this.binaryIO.writeBinaryInteger(color.getBlue());
                }
            }

            // Vectors
            this.binaryIO.writeBinaryInteger(this.vectors.size() - 1);

            for (BoardVector vector : this.vectors)
            {
                this.binaryIO.writeBinaryInteger(vector.getPoints().size());

                for (Point point : vector.getPoints())
                {
                    this.binaryIO.writeBinaryLong((long) point.x);
                    this.binaryIO.writeBinaryLong((long) point.y);
                }

                this.binaryIO.writeBinaryInteger(vector.getAttributes());

                if (vector.isClosed())
                {
                    this.binaryIO.writeBinaryInteger(1);
                }
                else
                {
                    this.binaryIO.writeBinaryInteger(0);
                }

                this.binaryIO.writeBinaryInteger(vector.getLayer());
                this.binaryIO.writeBinaryInteger(vector.getTileType());
                this.binaryIO.writeBinaryString(vector.getHandle());
            }

            // Programs
            this.binaryIO.writeBinaryInteger(this.programs.size() - 1);

            for (BoardProgram program : this.programs)
            {
                this.binaryIO.writeBinaryString(program.getFileName());
                this.binaryIO.writeBinaryString(program.getGraphic());
                this.binaryIO.writeBinaryString(program.getInitialVariable());
                this.binaryIO.writeBinaryString(program.getInitialValue());
                this.binaryIO.writeBinaryString(program.getFinalVariable());
                this.binaryIO.writeBinaryString(program.getFinalValue());
                this.binaryIO.writeBinaryInteger((int) program.getActivate());
                this.binaryIO.writeBinaryInteger((int) program.getActivationType());
                this.binaryIO.writeBinaryInteger((int) program.getDistanceRepeat());
                this.binaryIO.writeBinaryInteger((int) program.getLayer());

                BoardVector programVector = program.getVector();

                for (Point point : programVector.getPoints())
                {
                    this.binaryIO.writeBinaryLong((long) point.x);
                    this.binaryIO.writeBinaryLong((long) point.y);
                }

                if (programVector.isClosed())
                {
                    this.binaryIO.writeBinaryInteger(1);
                }
                else
                {
                    this.binaryIO.writeBinaryInteger(0);
                }

                this.binaryIO.writeBinaryString(programVector.getHandle());
            }

            // Sprites
            this.binaryIO.writeBinaryInteger(this.sprites.size() - 1);

            for (BoardSprite sprite : this.sprites)
            {
                this.binaryIO.writeBinaryString(sprite.getFileName());
                this.binaryIO.writeBinaryString(sprite.getActivationProgram());
                this.binaryIO.writeBinaryString(sprite.getMultitaskingProgram());
                this.binaryIO.writeBinaryString(sprite.getInitialVariable());
                this.binaryIO.writeBinaryString(sprite.getInitialValue());
                this.binaryIO.writeBinaryString(sprite.getFinalVariable());
                this.binaryIO.writeBinaryString(sprite.getFinalValue());
                this.binaryIO.writeBinaryString(sprite.getLoadingVariable());
                this.binaryIO.writeBinaryString(sprite.getLoadingValue());
                this.binaryIO.writeBinaryInteger((int) sprite.getActivate());
                this.binaryIO.writeBinaryInteger((int) sprite.getActivationType());
                this.binaryIO.writeBinaryInteger((int) sprite.getX());
                this.binaryIO.writeBinaryInteger((int) sprite.getY());
                this.binaryIO.writeBinaryInteger((int) sprite.getLayer());

                // INT will be skipped.
                this.binaryIO.writeBinaryInteger(this.associatedVector);
            }

            // Images
            this.binaryIO.writeBinaryInteger(this.images.size() - 1);

            for (BoardImage image : this.images)
            {
                this.binaryIO.writeBinaryString(image.getFileName());
                this.binaryIO.writeBinaryLong(image.getBoundLeft());
                this.binaryIO.writeBinaryLong(image.getBoundTop());
                this.binaryIO.writeBinaryInteger((int) image.getLayer());
                this.binaryIO.writeBinaryInteger((int) image.getDrawType());
                this.binaryIO.writeBinaryLong(image.getTransparentColour());

                // INT will be skipped.
                this.binaryIO.writeBinaryInteger(this.imageTransluceny);
            }

            // Threads
            this.binaryIO.writeBinaryInteger(this.threads.size() - 1);

            for (String thread : this.threads)
            {
                this.binaryIO.writeBinaryString(thread);
            }

            // Constants
            this.binaryIO.writeBinaryInteger(this.constants.size() - 1);

            for (String constant : this.constants)
            {
                this.binaryIO.writeBinaryString(constant);
            }

            // Layer Titles
            for (String layerTitle : this.layerTitles)
            {
                this.binaryIO.writeBinaryString(layerTitle);
            }

            // Directonal Links
            for (String link : this.directionalLinks)
            {
                this.binaryIO.writeBinaryString(link);
            }

            // Background Image 
            if (this.backgroundImages.size() > 0)
            {
                BoardImage backgroundImage = this.backgroundImages.get(0);
                this.binaryIO.writeBinaryString(backgroundImage.getFileName());
                this.binaryIO.writeBinaryLong(backgroundImage.getDrawType());
            }

            // Misc 
            this.binaryIO.writeBinaryLong(this.backgroundColour);
            this.binaryIO.writeBinaryString(this.backgroundMusic);

            this.binaryIO.writeBinaryString(this.firstRunProgram);
            this.binaryIO.writeBinaryString(this.battleBackground);
            this.binaryIO.writeBinaryInteger(this.enemyBattleLevel);

            if (this.allowBattles)
            {
                this.binaryIO.writeBinaryInteger(-1);
            }
            else
            {
                this.binaryIO.writeBinaryInteger(0);
            }

            if (this.allowSaving)
            {
                this.binaryIO.writeBinaryInteger(0);
            }
            else
            {
                this.binaryIO.writeBinaryInteger(-1);
            }

            this.binaryIO.writeBinaryInteger(this.ambientEffect.getRed());
            this.binaryIO.writeBinaryInteger(this.ambientEffect.getGreen());
            this.binaryIO.writeBinaryInteger(this.ambientEffect.getBlue());
            this.binaryIO.writeBinaryInteger(this.startingPositionX);
            this.binaryIO.writeBinaryInteger(this.startingPositionY);
            this.binaryIO.writeBinaryInteger(this.startingLayer);

            this.binaryIO.closeOutput();
            this.outputStream.close();

            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    /**
     * TODO: Make a global TileSetCache...
     *
     * @param cache
     */
    public void initializeTileSetCache(TileSetCache cache)
    {
        // Load the tiles into memory
        for (String indexString : this.tileIndex)
        {
            if (!indexString.isEmpty())
            {
                if (indexString.substring(indexString.length() - 3).equals("tan"))
                {
                    // Animated Tile
                    AnimatedTile aTile = new AnimatedTile(new File(
                            System.getProperty("project.path") + "/tiles/"
                            + indexString));
                    indexString = aTile.getFirstFrame();
                }

                String tileSetName = indexString.split(".tst")[0] + ".tst";
                if (!cache.contains(tileSetName))
                {
                    this.tileSet = cache.loadTileSet(tileSetName);
                }
                else
                {
                    this.tileSet = cache.getTileSet(tileSetName);
                }

                loadedTilesIndex.add(tileSet.getTile(Integer.parseInt(
                        indexString.split(".tst")[1]) - 1));
            }
            else
            {
                loadedTilesIndex.add(null);
            }
        }
    }

    /**
     *
     *
     * @param listener
     */
    public void addBoardChangeListener(BoardChangeListener listener)
    {
        this.boardChangeListeners.add(listener);
    }

    /**
     *
     *
     * @param listener
     */
    public void removeBoardChangeListener(BoardChangeListener listener)
    {
        this.boardChangeListeners.remove(listener);
    }

    /**
     *
     */
    public void fireBoardChanged()
    {
        BoardChangedEvent event = null;
        Iterator iterator = this.boardChangeListeners.iterator();

        while (iterator.hasNext())
        {
            if (event == null)
            {
                event = new BoardChangedEvent(this);
            }

            ((BoardChangeListener) iterator.next()).boardChanged(event);
        }
    }

    public void fireBoardLayerAdded(BoardLayer layer)
    {
        BoardChangedEvent event = null;
        Iterator iterator = this.boardChangeListeners.iterator();

        while (iterator.hasNext())
        {
            if (event == null)
            {
                event = new BoardChangedEvent(this);
                event.setLayer(layer);
            }

            ((BoardChangeListener) iterator.next()).boardLayerAdded(event);
        }
    }
    
    public void fireBoardLayerMovedUp(BoardLayer layer)
    {
        BoardChangedEvent event = null;
        Iterator iterator = this.boardChangeListeners.iterator();

        while (iterator.hasNext())
        {
            if (event == null)
            {
                event = new BoardChangedEvent(this);
                event.setLayer(layer);
            }

            ((BoardChangeListener) iterator.next()).boardLayerMovedUp(event);
        }
    }
    
    public void fireBoardLayerMovedDown(BoardLayer layer)
    {
        BoardChangedEvent event = null;
        Iterator iterator = this.boardChangeListeners.iterator();

        while (iterator.hasNext())
        {
            if (event == null)
            {
                event = new BoardChangedEvent(this);
                event.setLayer(layer);
            }

            ((BoardChangeListener) iterator.next()).boardLayerMovedDown(event);
        }
    }
    
    public void fireBoardLayerCloned(BoardLayer layer)
    {
        BoardChangedEvent event = null;
        Iterator iterator = this.boardChangeListeners.iterator();

        while (iterator.hasNext())
        {
            if (event == null)
            {
                event = new BoardChangedEvent(this);
                event.setLayer(layer);
            }

            ((BoardChangeListener) iterator.next()).boardLayerCloned(event);
        }
    }

    public void fireBoardLayerDeleted(BoardLayer layer)
    {
        BoardChangedEvent event = null;
        Iterator iterator = this.boardChangeListeners.iterator();

        while (iterator.hasNext())
        {
            if (event == null)
            {
                event = new BoardChangedEvent(this);
                event.setLayer(layer);
            }

            ((BoardChangeListener) iterator.next()).boardLayerDeleted(event);
        }
    }

    public void addLayer()
    {
        int layerNumber = this.layers.size() + 1;
        
        BoardLayer layer = new BoardLayer(this);
        layer.setName("Untitled Layer " + layerNumber);
        layer.setNumber(this.layers.size());
        this.layers.add(layer);
        
        this.fireBoardLayerAdded(layer);
    }

    public void moveLayerUp(int index)
    {
        // Highest possible index, can't be move up!
        if (index == this.layers.size())
        {
            return;
        }
        
        BoardLayer down = this.layers.get(index + 1);
        BoardLayer up = this.layers.get(index);
        this.layers.set(index + 1, up);
        this.layers.set(index, down);
        
        down.moveLayerDown();
        up.moveLayerUp();

        this.fireBoardLayerMovedUp(up);
    }

    public void moveLayerDown(int index)
    {
        // Lowest possible layer, can't be move down!
        if (index == 0)
        {
            return;
        }
        
        BoardLayer down = this.layers.get(index);
        BoardLayer up = this.layers.get(index - 1);
        this.layers.set(index - 1, down);
        this.layers.set(index, up);
        
        down.moveLayerDown();
        up.moveLayerUp();

        this.fireBoardLayerMovedDown(down);
    }

    public void cloneLayer(int index)
    {
        try
        {
            Iterator iterator = this.layers.listIterator(index + 1);

            while (iterator.hasNext())
            {
                BoardLayer layer = (BoardLayer) iterator.next();
                layer.moveLayerUp();
            }
            
            BoardLayer clone = (BoardLayer)this.layers.get(index).clone();
            this.layers.add(index + 1, clone);
            
            this.fireBoardLayerCloned(clone);
        }
        catch (CloneNotSupportedException ex)
        {

        }
        
    }

    public void deleteLayer(int index)
    {
        Iterator iterator = this.layers.listIterator(index + 1);

        while (iterator.hasNext())
        {
            BoardLayer layer = (BoardLayer)iterator.next();
            layer.moveLayerDown();
        }
        
        BoardLayer removedLayer = this.layers.get(index);
        this.layers.remove(index);
        
        this.fireBoardLayerDeleted(removedLayer);
    }
    
    /**
     * An inefficient routine used to create the BoardLayers, currently this is
     * need for testing. Later this will be integrated into the open and save
     * methods.
     */
    public void createLayers()
    {
        for (int i = 0; i < this.layerCount; i++)
        {
            BoardLayer layer = new BoardLayer(this);
            layer.setName(this.layerTitles.get(i));
            layer.setNumber(i);
            
            int count = this.width * this.height;
            int x = 0;
            int y = 0;
            
            for (int j = 0; j < count; j++)
            {
                if (this.boardDimensions[x][y][i] - 1 >= 0)
                {
                    layer.getTiles()[x][y] = this.getTileFromIndex(
                            this.boardDimensions[x][y][i] - 1);
                }
                
                x++;
                if (x == this.width)
                {
                    x = 0;
                    y++;
                    if (y == this.height)
                    {
                        break;
                    }
                }
            }
            
            for (BoardLight light : this.lights)
            {
                if (light.getLayer() == i)
                {
                    layer.getLights().add(light);
                }
            }

            for (BoardVector vector : this.vectors)
            {
                if (vector.getLayer() == i)
                {
                    layer.getVectors().add(vector);
                }
            }

            for (BoardProgram program : this.programs)
            {
                if (program.getLayer() == i)
                {
                    layer.getPrograms().add(program);
                }
            }

            for (BoardSprite sprite : this.sprites)
            {
                if (sprite.getLayer() == i)
                {
                    layer.getSprites().add(sprite);
                }
            }

            for (BoardImage image : this.images)
            {
                if (image.getLayer() == i)
                {
                    layer.getImages().add(image);
                }
            }
            
            this.layers.add(layer);
        }
    }

    /*
     * ************************************************************************* 
     * Protected Methods
     * *************************************************************************
     */
    
    
    
    /*
     * ************************************************************************* 
     * Private Methods
     * *************************************************************************
     */
    
    /**
     * Searches ahead of a given position for duplicate tiles, and returns the
     * amount followed by the position at which they end at.
     *
     * @param x starting x position (width)
     * @param y starting y position (height)
     * @param z starting z position (layerCount)
     * @return returns an array containing 4 <code>int</code>'s, the first
     * element contains the number of duplicate tile, the others contain the
     * positions of x, y, and z at the end of the loop. They would have normally
     * been passed by reference but Java doesn't support this.
     */
    private int[] findDuplicateTiles(int x, int y, int z)
    {
        int tile = this.boardDimensions[x][y][z];
        int count = 0;

        int k;
        int j = 0;
        int i = 0;

        for (k = z; k < layerCount; k++)
        {
            for (j = y; j < height; j++)
            {
                for (i = x; i < width; i++)
                {
                    if (this.boardDimensions[i][j][k] != tile)
                    {
                        int[] array =
                        {
                            count, i, j, k
                        };
                        
                        return array;
                    }

                    count++;
                }

                x = 0;
            }

            y = 0;
        }

        int[] array =
        {
            count, i, j, k
        };

        return array;
    }
    
    /**
     * Updates all of the IO attributes used in saving, gets the new information
     * from all of the layers that have been created. Maintains compatability
     * with Geoff's original open and save routines.
     */
    private void updateBoardIO()
    {
        this.layerCount = this.layers.size();
        this.layerTitles.clear();
        this.lights.clear();
        this.vectors.clear();
        this.programs.clear();
        this.sprites.clear();
        this.images.clear();
        
        this.boardDimensions = new int[this.width][this.height][this.layerCount];
        int count = this.width * this.height;
        int layerIndex = 0;
        
        for (BoardLayer layer : this.layers)
        {
            this.layerTitles.add(layer.getName());
            
            int x = 0;
            int y = 0;
            
            for (int i = 0; i < count; i++)
            {
                this.boardDimensions[x][y][layerIndex] = layer.getTiles()[x][y]
                        .getIndex();
                
                x++;
                if (x == this.width)
                {
                    x = 0;
                    y++;
                    if (y == this.height)
                    {
                        break;
                    }
                }
            }
            
            this.lights.addAll(layer.getLights());
            this.vectors.addAll(layer.getVectors());
            this.programs.addAll(layer.getPrograms());
            this.sprites.addAll(layer.getSprites());
            this.images.addAll(layer.getImages());
            
            layerIndex++;
        }
    }
}
