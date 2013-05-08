package rpgtoolkit.common.io.types;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.utilities.TileSetCache;
import rpgtoolkit.editor.board.event.BoardChangeListener;
import rpgtoolkit.editor.board.event.BoardChangedEvent;
import rpgtoolkit.editor.board.types.BoardImage;
import rpgtoolkit.editor.board.types.BoardLayerShade;
import rpgtoolkit.editor.board.types.BoardLight;
import rpgtoolkit.editor.board.types.BoardProgram;
import rpgtoolkit.editor.board.types.BoardSprite;
import rpgtoolkit.editor.board.types.BoardVector;
import rpgtoolkit.editor.exceptions.CorruptFileException;

public final class Board extends BasicType
{
    // Non-IO
    private final LinkedList boardChangeListeners = new LinkedList<>();
    
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
    private int layers;
    private int coordinateType;

    private ArrayList<String> tileIndex;
    private HashMap<String, TileSet> tileSetMap;

    private ArrayList<Tile> loadedTilesIndex;
    private int[][][] boardDimensions;
    private byte[] tileType;
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
    public Board(File fileName)
    {
        super(fileName);
        System.out.println("Loading Board " + fileName);
        this.open();
    }
    
     /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    
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
        return boardDimensions[x][y][z];
    }
    
    public int getLayers()
    {
        return layers;
    }

    public void setLayers(int layers)
    {
        this.layers = layers;
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

            if (binaryIO.readBinaryString().equals(FILE_HEADER))
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();

                width = binaryIO.readBinaryInteger();
                height = binaryIO.readBinaryInteger();
                layers = binaryIO.readBinaryInteger();
                coordinateType = binaryIO.readBinaryInteger();

                if (coordinateType == ISO_ROTATED)
                {
                    int tmpWidth = width;
                    int tmpHeight = height;

                    width += tmpHeight;
                    height += tmpWidth;
                }

                boardDimensions = new int[width][height][layers];

                // Total number of distinct tile types used, if we add a 
                // new tile we will have to check if the tileIndex already
                // contains the name of the tile e.g. default.tst2
                int lookUpTableSize = binaryIO.readBinaryInteger();
                binaryIO.readBinaryByte();

                for (int i = 0; i < lookUpTableSize; i++)
                {
                    // Read in the name of the tiles used on this board.
                    tileIndex.add(binaryIO.readBinaryString());
                }

                int totalTiles = width * height * layers;

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
                 * Unsure why ubShading and then shadingLayer are read since the shading is only applied to one(?)
                 * layer, perhaps this was to allow for more shading layers in the future, however it is now unnecessary
                 * and so the ubShading will be ignored.
                 */
                int ubShading = binaryIO.readBinaryInteger();
                long shadingLayer = binaryIO.readBinaryLong(); // apply shading from this layer down

                // WE ARE ASSUMING NO SHADING FOR NOW!
                int totalShading = width * height; // only one layer, so total tiles is just w*h
                int shadingLoaded = 0;

                while (shadingLoaded < totalShading)
                {
                    int count = binaryIO.readBinaryInteger();
                    shadingLoaded += count;
                    int red = binaryIO.readBinaryInteger();
                    int green = binaryIO.readBinaryInteger();
                    int blue = binaryIO.readBinaryInteger();
                }

                // Lights
                int numberLights = binaryIO.readBinaryInteger();
                for (int i = 0; i < numberLights + 1; i++)
                {
                    BoardLight newLight = new BoardLight();

                    // Get the layer the light effect is on
                    long layer = binaryIO.readBinaryLong();
                    // Get the eType??
                    long etype = binaryIO.readBinaryLong();

                    int numberPoints = binaryIO.readBinaryInteger();
                    for (int j = 0; j < numberPoints + 1; j++)
                    {
                        Point newPoint = new Point((int) 
                                binaryIO.readBinaryLong(), 
                                (int) binaryIO.readBinaryLong());
                    }

                    int numColors = binaryIO.readBinaryInteger();
                    for (int j = 0; j < numColors + 1; j++)
                    {
                        // Skip this data for now
                        binaryIO.readBinaryInteger();
                        binaryIO.readBinaryInteger();
                        binaryIO.readBinaryInteger();
                    }
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

                    binaryIO.readBinaryInteger(); // skip one INT of data

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

                    binaryIO.readBinaryInteger(); // skip one INT of data

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
                for (int i = 0; i < layers + 1; i++)
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
                backgroundImage.setScrollRatio(20); // 1 Pixel for every 10 the player moves

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
                ambientEffect = new Color(binaryIO.readBinaryInteger(), 
                        binaryIO.readBinaryInteger(), 
                        binaryIO.readBinaryInteger());
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
     * NOTE: Make a global TileSetCache...
     * 
     * @param cache 
     */
    public void initializeTileSetCache(TileSetCache cache)
    {
        // Load the tiles into memory
        for (String indexString : tileIndex)
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
                    tileSet = cache.loadTileSet(tileSetName);
                }
                else
                {
                    tileSet = cache.getTileSet(tileSetName);
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
    
    /*
     * ************************************************************************* 
     * Protected Methods
     * *************************************************************************
     */
    
    /**
     * 
     */
    protected void fireBoardChanged()
    {
        BoardChangedEvent event = null;
        Iterator iterator = this.boardChangeListeners.iterator();

        while (iterator.hasNext()) 
        {
            if (event == null) 
            {
                event = new BoardChangedEvent(this);
            }
            
            ((BoardChangeListener)iterator.next()).boardChanged(event);
        }
    }
}



