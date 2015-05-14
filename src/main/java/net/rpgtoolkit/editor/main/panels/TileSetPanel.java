package net.rpgtoolkit.editor.main.panels;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import net.rpgtoolkit.editor.tile.TilesetCanvas;

/**
 *
 * @author Joshua Michael Daly
 */
public class TileSetPanel extends JPanel
{
    
    private TilesetCanvas tilesetCanvas;
    private final JScrollPane scrollPane;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public TileSetPanel()
    {
        this.scrollPane = new JScrollPane();
        this.scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        
        this.setLayout(new BorderLayout());
        this.add(this.scrollPane, BorderLayout.CENTER);
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public TilesetCanvas getTilesetCanvas()
    {
        return tilesetCanvas;
    }

    public void setTilesetCanvas(TilesetCanvas tilesetCanvas)
    {
        this.tilesetCanvas = tilesetCanvas;
        this.scrollPane.setViewportView(this.tilesetCanvas);
        this.scrollPane.getViewport().revalidate();
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
}
