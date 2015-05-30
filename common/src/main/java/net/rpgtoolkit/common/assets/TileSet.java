package net.rpgtoolkit.common.assets;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.*;

import net.rpgtoolkit.common.utilities.DOSColors;

/**
 * This class is responsible for managing a tilset inside the editor It stores
 * all of the tiles in the set in a big Array of tiles!
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 * @verison 0.1
 */
public class TileSet extends BasicType
{

    private final DOSColors dosColors = new DOSColors(); // Needed for low colour tilesets

    private LinkedList<Tile> tiles;

    private boolean hasChanged = false;

    private int tilesetVersion;
    private int numberOfTiles;
    private int tilesetType;

    private int tileWidth;
    private int tileHeight;
    private boolean rgbColor = false;
    private boolean hasAlpha = false;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    /**
     * Creates a new tiles
     */
    public TileSet()
    {
        this.tiles = new LinkedList<>();
        this.tileWidth = 32;
        this.tileHeight = 32;
    }

    /**
     * Opens an existing tiles
     *
     * @param fileName File object for the tilset to open
     */
    public TileSet(File fileName)
    {
        super(fileName);
        System.out.println("Loading Tileset: " + fileName);
        tiles = new LinkedList<>();
        this.open();
    }

    public TileSet(File fileName, int flag)
    {
        // Gets a specific tile from a tiles
        super(fileName);
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public Tile getSingleTileFromSet(int index)
    {
        try
        {
            tilesetVersion = inputStream.read();
            inputStream.skip(1);
            numberOfTiles = inputStream.read();
            numberOfTiles += (inputStream.read() * 256);
            tilesetType = inputStream.read();
            inputStream.skip(1);

            // Lets make sure we are reading the correct number of bytes for the tile type.
            switch (tilesetType)
            {
                case 1:
                    tileWidth = 32;
                    tileHeight = 32;
                    rgbColor = true;
                    break;
                case 2:
                    tileWidth = 16;
                    tileHeight = 16;
                    rgbColor = true;
                    break;
                case 3:
                case 5:
                    tileWidth = 32;
                    tileHeight = 32;
                    break;
                case 4:
                case 6:
                    tileWidth = 16;
                    tileHeight = 16;
                    break;
                case 10:
                    tileWidth = 32;
                    tileHeight = 32;
                    rgbColor = true;
            }

            Tile newTile = new Tile(this, index);

            // Calculate data to skip
            int sizeOfTile = tileWidth * tileHeight * 3;
            inputStream.skip(sizeOfTile * (index));

            // Read the next tile into memory
            for (int x = 0; x < tileWidth; x++) // Go through each row
            {
                for (int y = 0; y < tileHeight; y++) // Go through each column
                {
                    if (rgbColor) // is this tile using RGB color space or DOS pallet
                    {
                        int red = inputStream.read();
                        int green = inputStream.read();
                        int blue = inputStream.read();
                        int alpha = 255;
                        if ((red == 0 && green == 1 && blue == 2) || (red == 255 && green == 0 && blue == 255))
                        {
                            red = 255;
                            green = 0;
                            blue = 255;
                            alpha = 0;
                        }

                        newTile.setPixel(x, y, new Color(red, green, blue, alpha));

                    }
                    else
                    {
                        int colorIndex = inputStream.read();
                        newTile.setPixel(x, y, dosColors.getColor(colorIndex));
                    }

                }
            }

            return newTile;

        }
        catch (IOException | TilePixelOutOfRangeException e)
        {
            return null;
        }
    }

    /**
     * Gets a tile from a specified location in the array.
     *
     * @param index Index of the array to get the tile from
     * @return Tile object representing the tile from the requested index
     */
    public Tile getTile(int index)
    {
        return tiles.get(index);
    }
    
    public int getTileIndex(Tile tile)
    {
        return this.tiles.indexOf(tile);
    }

    /**
     * Returns an array of all the tiles in the tiles
     *
     * @return Object array of all the tiles in the tiles
     */
    public LinkedList<Tile> getTiles()
    {
        return tiles;
    }

    public int getTileCount()
    {
        return numberOfTiles;
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }
    
    public String getName()
    {
        return this.file.getName();
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    /**
     * Opens the specified tiles uses the following parameters depending on tile
     * types
     * <p/>
     * Detail == 1 = 32x32 x 16.7m (rgbColor = TRUE) Detail == 2 = 16x16 x 16.7m
     * Detail == 3 = 32x32 x 256 (rgbColor = FALSE) Detail == 4 = 16x16 x 256
     * Detail == 5 = 32x32 x 16 (rgbColor = FALSE) Detail == 6 = 16x16 x 16
     * Detail == 10 = 32x32 x 16.7m + alpha (rgbColor = TRUE)
     *
     * @return true if the file could be opened correctly, false if not
     */
    private boolean open()
    {
        try
        {
            tilesetVersion = inputStream.read();
            inputStream.skip(1);
            numberOfTiles = inputStream.read();
            numberOfTiles += (inputStream.read() * 256);
            tilesetType = inputStream.read();
            inputStream.skip(1);

            // Lets make sure we are reading the correct number of bytes for the tile type.
            switch (tilesetType)
            {
                case 1:
                    tileWidth = 32;
                    tileHeight = 32;
                    rgbColor = true;
                    break;
                case 2:
                    tileWidth = 16;
                    tileHeight = 16;
                    rgbColor = true;
                    break;
                case 3:
                case 5:
                    tileWidth = 32;
                    tileHeight = 32;
                    break;
                case 4:
                case 6:
                    tileWidth = 16;
                    tileHeight = 16;
                    break;
                case 10:
                    tileWidth = 32;
                    tileHeight = 32;
                    rgbColor = true;
                    hasAlpha = true;
                case 150: // Isometric Tile Set : [
                    tileWidth = 32;
                    tileHeight = 32;
                    rgbColor = true;
            }

            /*
             * Read the actual RGB data for each tile
             */
            for (int i = 0; i < numberOfTiles; i++)
            {
                // Read the next tile into memory
                Tile newTile = new Tile(this, i);
                for (int x = 0; x < tileWidth; x++) // Go through each row
                {
                    for (int y = 0; y < tileHeight; y++) // Go through each column
                    {
                        if (rgbColor) // is this tile using RGB color space or DOS pallet
                        {
                            int red = inputStream.read();
                            int green = inputStream.read();
                            int blue = inputStream.read();
                            int alpha;
                            if (hasAlpha)
                            {
                                alpha = inputStream.read();
                            }
                            else
                            {
                                alpha = 255;
                                if ((red == 0 && green == 1 && blue == 2)
                                        || (red == 255 && green == 0 && blue == 255))
                                {
                                    red = 255;
                                    green = 0;
                                    blue = 255;
                                    alpha = 0;
                                }
                            }

                            newTile.setPixel(x, y, new Color(red, green,
                                    blue, alpha));

                        }
                        else
                        {
                            int colorIndex = inputStream.read();
                            newTile.setPixel(x, y, dosColors.getColor(colorIndex));
                        }

                    }
                }
                tiles.add(newTile);
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
        catch (TilePixelOutOfRangeException e)
        {
            return false;
        }
    }

    /**
     * Saves the current tiles, all tiless are now saved in the new format. If
     * it has not been saved before the editor should prompt for a filename this
     * is NOT the responsibility of this class.
     *
     * @return true if the save was successfull, false if not
     */
    public boolean save()
    {
        try
        {
            outputStream = new FileOutputStream(file);

            // Write the header of the tiles
            outputStream.write(tilesetVersion);
            outputStream.write(0);
            outputStream.write(numberOfTiles);
            outputStream.write(0);
            outputStream.write(10);
            outputStream.write(0);

            // Save the tiles to the tiles
            for (Tile tile : tiles)
            {
                for (int x = 0; x < 32; x++) // Go through each row
                {
                    for (int y = 0; y < 32; y++) // Go through each column
                    {
                        Color pixelColor = tile.getPixel(x, y);
                        outputStream.write(pixelColor.getRed());
                        outputStream.write(pixelColor.getGreen());
                        outputStream.write(pixelColor.getBlue());
                        outputStream.write(pixelColor.getAlpha());
                    }
                }
            }

            outputStream.close(); // Release file

            return true;
        }
        catch (FileNotFoundException e)
        {
        }
        catch (IOException e)
        {
        }
        catch (TilePixelOutOfRangeException e)
        {

        }

        return false; // Should not get here! error if we do
    }

    /**
     * Saves the current tiles to the specified filename, it has the effect of
     * leaving the existing file unchanged, any future changes will be to the
     * new file, unless saveAs() is called again
     *
     * @param fileName File to save the tiles as
     * @return true if the save was successfull, false if not
     */
    public boolean saveAs(File fileName)
    {
        this.file = fileName; // Change filename
        return this.save();
    }

    /**
     * Adds a new tile to the tiles, it will add the tile at the end of the
     * array
     *
     * @param newTile Tile object to add to the array
     * @return True if the tile was added correctly, false if not
     */
    public boolean addTile(Tile newTile)
    {
        tiles.add(newTile);
        numberOfTiles++; // Increment tile count
        hasChanged = true;
        return hasChanged;
    }

    public static void main(String[] args)
    {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            TileSet test = new TileSet(fileChooser.getSelectedFile());
        }
    }
}
