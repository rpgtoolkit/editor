package rpgtoolkit.common.io.types;

import rpgtoolkit.common.editor.types.Tile;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import rpgtoolkit.common.utilities.TileSetCache;
import rpgtoolkit.editor.board.types.BoardImage;
import rpgtoolkit.editor.board.types.BoardLayerShade;
import rpgtoolkit.editor.board.types.BoardLight;
import rpgtoolkit.editor.board.types.BoardProgram;
import rpgtoolkit.editor.board.types.BoardSprite;
import rpgtoolkit.editor.board.types.BoardVector;
import rpgtoolkit.editor.exceptions.CorruptFileException;

public final class Board extends BasicType
{
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
    private int[][][] board;
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

                board = new int[width][height][layers];

                int lookUpTableSize = binaryIO.readBinaryInteger();
                binaryIO.readBinaryByte();

                for (int i = 0; i < lookUpTableSize; i++)
                {
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
                        board[x][y][z] = index;
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
                        Point newPoint = new Point((int) binaryIO.readBinaryLong(), (int) binaryIO.readBinaryLong());
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

                // Vector count is one less than it should be so +1 to vectors all round!
                int numberVectors = binaryIO.readBinaryInteger();
                for (int i = 0; i < numberVectors + 1; i++)
                {
                    BoardVector newVector = new BoardVector();
                    // How Many Points in said vector?
                    int numberPoints = binaryIO.readBinaryInteger();
                    for (int j = 0; j < numberPoints + 1; j++)
                    {
                        newVector.addPoint(binaryIO.readBinaryLong(), binaryIO.readBinaryLong());
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
                        programVector.addPoint(binaryIO.readBinaryLong(), binaryIO.readBinaryLong());
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
                ambientEffect = new Color(binaryIO.readBinaryInteger(), binaryIO.readBinaryInteger(), binaryIO.readBinaryInteger());
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
                    AnimatedTile aTile = new AnimatedTile(new File(System.getProperty("project.path") + "/tiles/" + indexString));
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

                loadedTilesIndex.add(tileSet.getTile(Integer.parseInt(indexString.split(".tst")[1]) - 1));
            }
            else
            {
                loadedTilesIndex.add(null);
            }
        }
    }

    public ArrayList<String> getTileIndex()
    {
        return tileIndex;
    }

    public Tile getTileFromIndex(int index)
    {
        return loadedTilesIndex.get(index);
    }

    public int getIndexAtLocation(int x, int y, int z)
    {
        return board[x][y][z];
    }

    public int getWidth()
    {
        return width;

    }

    public int getHeight()
    {
        return height;
    }

    public int getLayerCount()
    {
        return layers;
    }

    public ArrayList<BoardVector> getVectors()
    {
        return vectors;
    }

    public ArrayList<BoardImage> getBackgroundImages()
    {
        return backgroundImages;
    }

    public int getStartingPositionX()
    {
        return startingPositionX;
    }

    public int getStartingPositionY()
    {
        return startingPositionY;
    }

    public int getStartingLayer()
    {
        return startingLayer;
    }

    public ArrayList<BoardSprite> getSprites()
    {
        return sprites;
    }

    public ArrayList<BoardProgram> getPrograms()
    {
        return programs;
    }
}



