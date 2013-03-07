package rpgtoolkit.common.utilities;

import java.io.File;
import java.util.HashMap;
import rpgtoolkit.common.io.types.TileSet;

/**
 * Stores a cache of loaded tile-sets for reuse between boards.
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 * @version 1.0
 */
public class TileSetCache
{
    private HashMap<String, TileSet> tileSets;

    public TileSetCache()
    {
        tileSets = new HashMap<>();
    }

    /**
     * Allows the calling object to check if the tile set has already been loaded,
     * this should be checked before calling loadTileSet
     *
     * @param key Tile Set file to check for
     * @return true if the tile set is already present in the cache
     */
    public boolean contains(String key)
    {
        key = key.toLowerCase();
        return tileSets.containsKey(key);
    }

    /**
     * Loads the specified tile set into the cache, it will only load the file if
     * it is not already present in the cache, it is important to call contains(String key) before
     * calling this method.
     *
     * @param fileName Tile set to attempt to load into the cache
     * @return The loaded tile set is returned, this is to remove the need to call getTileSet(String key) straight after loading a set
     */
    public TileSet loadTileSet(String fileName)
    {
        fileName = fileName.toLowerCase();

        if (!tileSets.containsKey(fileName))
        {
            TileSet set = new TileSet(new File(System.getProperty("project.path") + "/Tiles/" + fileName));
            tileSets.put(fileName, set);
            return set;
        }

        return null;
    }

    /**
     * Gets the tile set with the specified key, if it is present in the cache
     *
     * @param key Filename of the tile set to retrieve
     * @return the Tile set with the corresponding filename
     */
    public TileSet getTileSet(String key)
    {
        key = key.toLowerCase();

        if (tileSets.containsKey(key))
        {
            return tileSets.get(key);
        }
        else
        {
            return null;
        }
    }
}
