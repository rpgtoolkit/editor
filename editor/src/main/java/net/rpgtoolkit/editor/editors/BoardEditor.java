/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors;

import net.rpgtoolkit.editor.editors.board.BoardView2D;
import net.rpgtoolkit.editor.editors.board.BoardMouseAdapter;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.editor.editors.board.AbstractBrush;
import net.rpgtoolkit.common.Selectable;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;

/**
 *
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class BoardEditor extends ToolkitEditorWindow {

    private JScrollPane scrollPane;

    private BoardView2D boardView;
    private Board board;

    private BoardMouseAdapter boardMouseAdapter;

    private Point cursorTileLocation;
    private Point cursorLocation;
    private Rectangle selection;

    private Tile[][] selectedTiles;

    private Selectable selectedObject;

    private boolean selectedState;

    /*
     * *************************************************************************
     * Constructors
     * *************************************************************************
     */
    /**
     * Default Constructor.
     */
    public BoardEditor() {

    }

    /**
     * This constructor is used when opening an existing board, it does not make
     * the window visible.
     *
     * @param file The board file that to open.
     * @throws java.io.FileNotFoundException
     */
    public BoardEditor(File file) throws FileNotFoundException {
        super("Board Viewer", true, true, true, true);
        this.boardMouseAdapter = new BoardMouseAdapter(this);

        board = new Board(file);

        initialise(board, file.getAbsolutePath());
    }

    public BoardEditor(String fileName, int width, int height) {
        super("Board Viewer", true, true, true, true);
        this.boardMouseAdapter = new BoardMouseAdapter(this);

        board = new Board(width, height);
        board.addLayer();

        initialise(board, fileName);
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public BoardView2D getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView2D boardView) {
        this.boardView = boardView;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Point getCursorTileLocation() {
        return this.cursorTileLocation;
    }

    public void setCursorTileLocation(Point location) {
        cursorTileLocation = location;
    }

    public Point getCursorLocation() {
        return this.cursorLocation;
    }

    public void setCursorLocation(Point location) {
        cursorLocation = location;
    }

    public Rectangle getSelection() {
        return this.selection;
    }

    public Tile[][] getSelectedTiles() {
        return this.selectedTiles;
    }

    public void setSelectedTiles(Tile[][] tiles) {
        selectedTiles = tiles;
    }

    public Selectable getSelectedObject() {
        return this.selectedObject;
    }

    public void setSelectedObject(Selectable object) {
        if (object == null) {
            selectedObject = board;
        } else {
            this.selectedObject = object;
        }

        MainWindow.getInstance().getPropertiesPanel().setModel(
                this.selectedObject);
        this.boardView.repaint();
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    /**
     * Zoom in on the board view.
     */
    public void zoomIn() {
        this.boardView.zoomIn();
        this.scrollPane.getViewport().revalidate();
    }

    /**
     * Zoom out on the board view.
     */
    public void zoomOut() {
        this.boardView.zoomOut();
        this.scrollPane.getViewport().revalidate();
    }

    @Override
    public boolean save() {
        return this.board.save();
    }

    /*
     * *************************************************************************
     * Protected Getters and Setters
     * *************************************************************************
     */
    public void setSelection(Rectangle rectangle) {
        this.selection = rectangle;
        this.boardView.repaint();
    }

    /*
     * *************************************************************************
     * Protected Methods
     * *************************************************************************
     */
    public void doPaint(AbstractBrush brush, Point point, Rectangle selection) {
        try {
            if (brush == null) {
                return;
            }

            brush.startPaint(this.boardView, this.boardView.
                    getCurrentSelectedLayer().getLayer().getNumber());
            brush.doPaint(point.x, point.y, selection);
            brush.endPaint();
        } catch (Exception ex) {
            Logger.getLogger(BoardEditor.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    public Tile[][] createTileLayerFromRegion(Rectangle rectangle) {
        Tile[][] tiles = new Tile[rectangle.width + 1][rectangle.height + 1];

        for (int y = rectangle.y; y <= rectangle.y + rectangle.height; y++) {
            for (int x = rectangle.x; x <= rectangle.x + rectangle.width; x++) {
                tiles[x - rectangle.x][y - rectangle.y]
                        = this.boardView.getCurrentSelectedLayer().
                        getLayer().getTileAt(x, y);
            }
        }

        return tiles;
    }

    /**
     * 
     * 
     * @param x
     * @param y
     * @return 
     */
    public int[] calculateSnapCoordinates(int x, int y) {
        int tileSize = MainWindow.TILE_SIZE;
        int[] coordinates = {0, 0};
        
        int mx = x % tileSize; 
        int my = y % tileSize;

        if (mx < tileSize / 2) {
            coordinates[0] = x - mx;
        } else {
            coordinates[0] = x + (tileSize - mx);
        }

        if (my < tileSize / 2) {
            coordinates[1] = y - my;
        } else {
            coordinates[1] = y + (tileSize - my);
        }

        return coordinates;
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void initialise(Board board, String fileName) {
        this.boardView = new BoardView2D(this, board);
        this.boardView.addMouseListener(this.boardMouseAdapter);
        this.boardView.addMouseMotionListener(this.boardMouseAdapter);

        this.scrollPane = new JScrollPane(this.boardView);
        this.scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.cursorTileLocation = new Point(0, 0);
        this.cursorLocation = new Point(0, 0);

        this.setTitle("Viewing " + fileName);
        this.add(scrollPane);
        this.pack();
    }
}
