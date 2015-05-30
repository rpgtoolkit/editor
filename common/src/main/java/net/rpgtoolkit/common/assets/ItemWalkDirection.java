/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public enum ItemWalkDirection {
    
    NORTH(0),
    SOUTH(1),
    EAST(2),
    WEST(3),
    NORTH_WEST(4),
    NORTH_EAST(5),
    SOUTH_WEST(6),
    SOUTH_EAST(7);
    
    private final int value;
    
    private ItemWalkDirection(int value) {
        this.value = value;
    }
    
    public int value() {
        return this.value;
    }
    
}
