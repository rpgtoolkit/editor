/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.rpgtoolkit.common.assets.BoardProgram;

import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.MainWindow;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class BoardMouseAdapter extends MouseAdapter {

  private Point origin;
  private final BoardEditor editor;

  /**
   *
   *
   * @param boardEditor
   */
  public BoardMouseAdapter(BoardEditor boardEditor) {
    editor = boardEditor;
  }

  /**
   *
   *
   * @param e
   */
  @Override
  public void mousePressed(MouseEvent e) {
    if (editor.getBoardView().getCurrentSelectedLayer() != null) {
      AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
      int button = e.getButton();
      int x = (int) (e.getX() / editor.getBoardView().getZoom());
      int y = (int) (e.getY() / editor.getBoardView().getZoom());

      switch (button) {
        case MouseEvent.BUTTON1:
          doMouseButton1Pressed(brush, x, y);
          break;
        case MouseEvent.BUTTON2:
          doMouseButton2Pressed(brush, x, y);
          break;
        case MouseEvent.BUTTON3:
          doMouseButton3Pressed(brush, x, y);
          break;
        default:
          break;
      }
    }
  }

  /**
   *
   *
   * @param e
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    if (editor.getBoardView().getCurrentSelectedLayer() != null) {
      AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
      int x = (int) (e.getX() / editor.getBoardView().getZoom());
      int y = (int) (e.getY() / editor.getBoardView().getZoom());

      if (brush instanceof ShapeBrush 
              || brush instanceof SelectionBrush
              || brush instanceof CustomBrush) {
        doMouseButton1Dragged(brush, x, y);
      }
    }
  }

  /**
   *
   *
   * @param e
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    int x = (int) (e.getX() / editor.getBoardView().getZoom());
    int y = (int) (e.getY() / editor.getBoardView().getZoom());
    editor.setCursorTileLocation(editor.getBoardView().getTileCoordinates(x, y));
    editor.setCursorLocation(new Point(x, y));
    editor.getBoardView().repaint();
  }

  /**
   * Deals with the creation of an object on a board layer.
   *
   * @param e
   * @param brush
   */
  private void doMouseButton1Pressed(AbstractBrush brush, int x, int y) {
    Rectangle bucketSelection = null;
    
    Point point;
    if (!(brush instanceof StartPositionBrush)) {
      point = editor.getBoardView().getTileCoordinates(x, y);
    } else {
      point = new Point(x, y);
    }

    if (brush instanceof SelectionBrush) {
      origin = point;
      editor.setSelection(new Rectangle(
              origin.x, origin.y,
              0, 0));
      editor.setSelectedTiles(editor.
              createTileLayerFromRegion(editor.getSelection()));
    } else if (brush instanceof ShapeBrush && editor.getSelection() != null) {
      editor.setSelection(null);
    } else if (brush instanceof BucketBrush && editor.getSelection() != null) {
      // To compensate for the fact that the selection
      // is 1 size too small in both width and height.
      // Bit of a hack really.
      editor.getSelection().width++;
      editor.getSelection().height++;

      if (editor.getSelection().contains(point)) {
        bucketSelection = (Rectangle) editor.getSelection().clone();
      }

      // Revert back to original dimensions.
      editor.getSelection().width--;
      editor.getSelection().height--;
    } else if (brush instanceof VectorBrush) {
      // Because vectors and programs coordinates are pixel based.
      point = new Point(x, y);
    }

    editor.doPaint(brush, point, bucketSelection);
  }

  /**
   * Deals with the deletion of an object on a board layer.
   *
   * @param e
   * @param brush
   */
  private void doMouseButton2Pressed(AbstractBrush brush, int x, int y) {
    Object result = null;
    
    if (brush instanceof ProgramBrush) {
      ProgramBrush programBrush = (ProgramBrush) brush;

      if (programBrush.isDrawing()) {
        programBrush.finish();
      }

      result = editor.getBoardView().getCurrentSelectedLayer().getLayer().removeProgramAt(x, y);
    } else if (brush instanceof VectorBrush) {
      VectorBrush vectorBrush = (VectorBrush) brush;

      if (vectorBrush.isDrawing()) {
        vectorBrush.finish();
      }

      result = editor.getBoardView().getCurrentSelectedLayer().getLayer().removeVectorAt(x, y);

    } else if (brush instanceof SpriteBrush) {
      result = editor.getBoardView().getCurrentSelectedLayer().getLayer().removeSpriteAt(
              x / MainWindow.TILE_SIZE,
              y / MainWindow.TILE_SIZE);
    }
    
    if (result != null) {
        if (result == editor.getSelectedObject()) {
          editor.getSelectedObject().setSelectedState(false);
          editor.setSelectedObject(null);
        }
      }
  }

  /**
   * Deals with the selection of an object on a board layer
   *
   * @param e
   * @param brush
   */
  private void doMouseButton3Pressed(AbstractBrush brush, int x, int y) {

    if (brush instanceof ProgramBrush) {
      // We are drawing a vector, so lets finish it.
      if (((ProgramBrush) brush).isDrawing()) {
        ((ProgramBrush) brush).finish();
      } else // We want to select a program.
      {
        selectProgram(editor.getBoardView().getCurrentSelectedLayer()
                .getLayer().findProgramAt(x, y));
      }
    } else if (brush instanceof VectorBrush) {
      // We are drawing a vector, so lets finish it.
      if (((VectorBrush) brush).isDrawing()) {
        ((VectorBrush) brush).finish();
      } else // We want to select a vector.
      {
        selectVector(editor.getBoardView().getCurrentSelectedLayer()
                .getLayer().findVectorAt(x, y));
      }
    } else if (brush instanceof SpriteBrush) {
      selectSprite(editor.getBoardView().getCurrentSelectedLayer().getLayer()
              .findSpriteAt(
                      x / MainWindow.TILE_SIZE,
                      y / MainWindow.TILE_SIZE));
    }
  }

  /**
   *
   *
   * @param e
   * @param brush
   */
  private void doMouseButton1Dragged(AbstractBrush brush, int x, int y) {
    Point point = editor.getBoardView().getTileCoordinates(x, y);
    editor.setCursorTileLocation(point);
    editor.setCursorLocation(new Point(x, y));

    if (brush instanceof SelectionBrush) {
      Rectangle select = new Rectangle(origin.x, origin.y, 0, 0);
      select.add(point);

      if (!select.equals(editor.getSelection())) {
        editor.setSelection(select);
      }

      editor.setSelectedTiles(editor.
              createTileLayerFromRegion(editor.getSelection()));
    }

    editor.doPaint(brush, point, null);
  }

  /**
   *
   *
   * @param vector
   */
  private void selectVector(BoardVector vector) {
    if (vector != null) {
      vector.setSelectedState(true);

      if (editor.getSelectedObject() != null) {
        editor.getSelectedObject().setSelectedState(false);
      }

      editor.setSelectedObject(vector);
    } else if (editor.getSelectedObject() != null) {
      editor.getSelectedObject().setSelectedState(false);
      editor.setSelectedObject(null);
    }
  }

  /**
   *
   *
   * @param program
   */
  private void selectProgram(BoardProgram program) {
    if (program != null) {
      program.getVector().setSelectedState(true);

      if (editor.getSelectedObject() != null) {
        editor.getSelectedObject().setSelectedState(false);
      }

      editor.setSelectedObject(program);
    } else if (editor.getSelectedObject() != null) {
      editor.getSelectedObject().setSelectedState(false);
      editor.setSelectedObject(null);
    }
  }

  /**
   *
   *
   * @param sprite
   */
  private void selectSprite(BoardSprite sprite) {
    if (sprite != null) {
      sprite.setSelectedState(true);

      if (editor.getSelectedObject() != null) {
        editor.getSelectedObject().setSelectedState(false);
      }

      editor.setSelectedObject(sprite);
    } else if (editor.getSelectedObject() != null) {
      editor.getSelectedObject().setSelectedState(false);
      editor.setSelectedObject(null);
    }
  }
  
}
