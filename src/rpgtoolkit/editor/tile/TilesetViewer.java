package rpgtoolkit.editor.tile;

import rpgtoolkit.editor.tile.TilesetCanvas;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.common.io.types.TileSet;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TilesetViewer extends JInternalFrame
{
    private MainWindow parent;

    private TileSet tileset;
    private TilesetCanvas canvas;
    private JScrollPane scroll;

    public TilesetViewer()
    {
        setupWindow();
    }

    public TilesetViewer(File fileName)
    {
        super("Tileset Viewer", true, true, true, true);
        setupWindow();
        tileset = new TileSet(fileName);
        canvas = new TilesetCanvas(tileset);
        canvas.setPreferredSize(canvas.getPreferredSize());
        this.setTitle("Viewing " + fileName.getAbsolutePath());
        this.scroll = new JScrollPane(canvas);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll, BorderLayout.CENTER);

    }

    private void setupWindow()
    {
        this.setSize(640, 480);
        this.setLayout(new BorderLayout());

        this.setVisible(true);
    }
}
