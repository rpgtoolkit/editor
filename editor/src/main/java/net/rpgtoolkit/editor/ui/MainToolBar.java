/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.editors.board.BucketBrush;
import net.rpgtoolkit.editor.editors.board.ProgramBrush;
import net.rpgtoolkit.editor.editors.board.SelectionBrush;
import net.rpgtoolkit.editor.editors.board.ShapeBrush;
import net.rpgtoolkit.editor.editors.board.SpriteBrush;
import net.rpgtoolkit.editor.editors.board.VectorBrush;
import net.rpgtoolkit.editor.ui.resources.Icons;

/**
 *
 * @author Joshua Michael Daly
 */
public class MainToolBar extends JToolBar
{

    private final JPopupMenu popupMenu;
    private final JMenuItem newAnimationMenu;
    private final JMenuItem newProjectMenu;

    private final EditorButton newButton;
    private final EditorButton openButton;
    private final EditorButton saveButton;
    private final EditorButton saveAllButton;

    private final EditorButton cutButton;
    private final EditorButton copyButton;
    private final EditorButton pasteButton;
    private final EditorButton deleteButton;

    private final EditorButton undoButton;
    private final EditorButton redoButton;

    private final ButtonGroup toolButtonGroup;
    private final JToggleButton pencilButton;
    private final JToggleButton selectionButton;
    private final JToggleButton bucketButton;
    private final JToggleButton eraserButton;
    private final JToggleButton vectorButton;
    private final JToggleButton programButton;
    private final JToggleButton spriteButton;
    private final JToggleButton lightButton;

    private final EditorButton zoomInButton;
    private final EditorButton zoomOutButton;

    private final EditorButton runButton;
    private final EditorButton stopButton;

    private final EditorButton helpButton;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public MainToolBar()
    {
        super();

        this.setFloatable(false);

        this.popupMenu = new JPopupMenu();
        this.newAnimationMenu = new JMenuItem("Animation");
        this.newProjectMenu = new JMenuItem("Project");

        this.popupMenu.add(this.newAnimationMenu);
        this.popupMenu.add(this.newProjectMenu);

        this.newButton = new EditorButton();
        this.newButton.setIcon(Icons.getSmallIcon("new"));

        this.openButton = new EditorButton();
        this.openButton.setEnabled(false);
        this.openButton.setIcon(Icons.getSmallIcon("open"));
        this.openButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainWindow.getInstance().openFile();
            }
        });

        this.saveButton = new EditorButton();
        this.saveButton.setIcon(Icons.getSmallIcon("save"));
        this.saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow w = MainWindow.getInstance();
                if (w.getDesktopPane().getSelectedFrame() != null)
                {
                    if (w.getDesktopPane().getSelectedFrame() instanceof 
                            ToolkitEditorWindow)
                    {
                        ToolkitEditorWindow window = (ToolkitEditorWindow)
                                w.getDesktopPane().getSelectedFrame();
                        
                        window.save();
                    }
                }
            }
        });


        this.saveAllButton = new EditorButton();
        this.saveAllButton.setIcon(Icons.getSmallIcon("save-all"));

        this.cutButton = new EditorButton();
        this.cutButton.setIcon(Icons.getSmallIcon("cut"));

        this.copyButton = new EditorButton();
        this.copyButton.setIcon(Icons.getSmallIcon("copy"));

        this.pasteButton = new EditorButton();
        this.pasteButton.setIcon(Icons.getSmallIcon("paste"));

        this.deleteButton = new EditorButton();
        this.deleteButton.setIcon(Icons.getSmallIcon("delete"));

        this.undoButton = new EditorButton();
        this.undoButton.setIcon(Icons.getSmallIcon("undo"))
                ;
        this.redoButton = new EditorButton();
        this.redoButton.setIcon(Icons.getSmallIcon("redo"));

        this.pencilButton = new JToggleButton();
        this.pencilButton.setFocusable(false);
        this.pencilButton.setIcon(Icons.getSmallIcon("pencil"));
        this.pencilButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                toggleSelectedOnBoardEditor();
                
                ShapeBrush brush = new ShapeBrush();
                brush.setTile(MainWindow.getInstance().getLastSelectedTile());
                brush.makeRectangleBrush(new Rectangle(0, 0, 1, 1));
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.selectionButton = new JToggleButton();
        this.selectionButton.setFocusable(false);
        this.selectionButton.setIcon(Icons.getSmallIcon("selection"));
        this.selectionButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                toggleSelectedOnBoardEditor();
                
                SelectionBrush brush = new SelectionBrush(new Tile[1][1]);
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.bucketButton = new JToggleButton();
        this.bucketButton.setFocusable(false);
        this.bucketButton.setIcon(Icons.getSmallIcon("bucket"));
        this.bucketButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                toggleSelectedOnBoardEditor();
                
                BucketBrush brush = new BucketBrush();
                brush.setPourTile(MainWindow.getInstance().getLastSelectedTile());
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.eraserButton = new JToggleButton();
        this.eraserButton.setFocusable(false);
        this.eraserButton.setIcon(Icons.getSmallIcon("eraser"));
        this.eraserButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                toggleSelectedOnBoardEditor();
                
                ShapeBrush brush = new ShapeBrush();
                brush.makeRectangleBrush(new Rectangle(0, 0, 1, 1));
                brush.setTile(new Tile());
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.vectorButton = new JToggleButton();
        this.vectorButton.setFocusable(false);
        this.vectorButton.setIcon(Icons.getSmallIcon("layer-shape-polyline"));
        this.vectorButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                toggleSelectedOnBoardEditor();
                
                VectorBrush brush = new VectorBrush();
                MainWindow.getInstance().setCurrentBrush(brush);

                if (MainWindow.getInstance().getMainMenuBar().getViewMenu()
                        .getShowVectorsMenuItem().isSelected() == false)
                {
                    MainWindow.getInstance().getMainMenuBar().getViewMenu()
                        .getShowVectorsMenuItem().setSelected(true);
                }
            }
        });
        
        this.programButton = new JToggleButton();
        this.programButton.setFocusable(false);
        this.programButton.setIcon(Icons.getSmallIcon("layer-shape-polyline"));
        this.programButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                toggleSelectedOnBoardEditor();
                
                ProgramBrush brush = new ProgramBrush();
                MainWindow.getInstance().setCurrentBrush(brush);

                if (MainWindow.getInstance().getMainMenuBar().getViewMenu()
                        .getShowProgramsMenuItem().isSelected() == false)
                {
                    MainWindow.getInstance().getMainMenuBar().getViewMenu()
                        .getShowProgramsMenuItem().setSelected(true);
                }
            }
        });

        this.spriteButton = new JToggleButton();
        this.spriteButton.setFocusable(false);
        this.spriteButton.setIcon(Icons.getSmallIcon("user"));
        this.spriteButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                toggleSelectedOnBoardEditor();
                
                SpriteBrush brush = new SpriteBrush();
                MainWindow.getInstance().setCurrentBrush(brush);
            }
        });

        this.lightButton = new JToggleButton();
        this.lightButton.setFocusable(false);
        this.lightButton.setIcon(Icons.getSmallIcon("flashlight-shine"));
        this.lightButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });

        this.zoomInButton = new EditorButton();
        this.zoomInButton.setIcon(Icons.getSmallIcon("zoom-in"));
        this.zoomInButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainWindow.getInstance().zoomInOnBoardEditor();
            }
        });

        this.zoomOutButton = new EditorButton();
        this.zoomOutButton.setIcon(Icons.getSmallIcon("zoom-out"));
        this.zoomOutButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainWindow.getInstance().zoomOutOnBoardEditor();
            }
        });

        this.runButton = new EditorButton();
        this.runButton.setIcon(Icons.getSmallIcon("run"));
        this.runButton.setEnabled(false);

        this.stopButton = new EditorButton();
        this.stopButton.setIcon(Icons.getSmallIcon("stop"));

        this.helpButton = new EditorButton();
        this.helpButton.setIcon(Icons.getSmallIcon("help"));

        // Disable all the buttons for now
        this.newButton.setEnabled(false);
        this.openButton.setEnabled(false);
        this.saveButton.setEnabled(false);
        this.saveAllButton.setEnabled(false);
        this.cutButton.setEnabled(false);
        this.copyButton.setEnabled(false);
        this.pasteButton.setEnabled(false);
        this.deleteButton.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.pencilButton.setEnabled(true);
        this.selectionButton.setEnabled(true);
        this.bucketButton.setEnabled(true);
        this.eraserButton.setEnabled(true);
        this.vectorButton.setEnabled(true);
        this.programButton.setEnabled(true);
        this.spriteButton.setEnabled(true);
        this.lightButton.setEnabled(false);
        this.zoomInButton.setEnabled(true);
        this.zoomOutButton.setEnabled(true);
        this.runButton.setEnabled(false);
        this.stopButton.setEnabled(false);
        this.helpButton.setEnabled(false);

        this.toolButtonGroup = new ButtonGroup();
        this.toolButtonGroup.add(this.pencilButton);
        this.toolButtonGroup.add(this.selectionButton);
        this.toolButtonGroup.add(this.bucketButton);
        this.toolButtonGroup.add(this.eraserButton);
        this.toolButtonGroup.add(this.vectorButton);
        this.toolButtonGroup.add(this.programButton);
        this.toolButtonGroup.add(this.spriteButton);
        this.toolButtonGroup.add(this.lightButton);

        this.add(this.newButton);
        this.add(this.openButton);
        this.add(this.saveButton);
        this.add(this.saveAllButton);
        this.addSeparator();
        this.add(this.cutButton);
        this.add(this.copyButton);
        this.add(this.pasteButton);
        this.add(this.deleteButton);
        this.addSeparator();
        this.add(this.undoButton);
        this.add(this.redoButton);
        this.addSeparator();
        this.add(this.pencilButton);
        this.add(this.selectionButton);
        this.add(this.bucketButton);
        this.add(this.eraserButton);
        this.add(this.vectorButton);
        this.add(this.programButton);
        this.add(this.spriteButton);
        this.add(this.lightButton);
        this.addSeparator();
        this.add(this.zoomInButton);
        this.add(this.zoomOutButton);
        this.addSeparator();
        this.add(this.runButton);
        this.add(this.stopButton);
        this.addSeparator();
        this.add(this.helpButton);
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public JPopupMenu getPopupMenu()
    {
        return popupMenu;
    }

    public JMenuItem getNewAnimationMenu()
    {
        return newAnimationMenu;
    }

    public JMenuItem getNewProjectMenu()
    {
        return newProjectMenu;
    }

    public EditorButton getNewButton()
    {
        return newButton;
    }

    public EditorButton getOpenButton()
    {
        return openButton;
    }

    public EditorButton getSaveButton()
    {
        return saveButton;
    }

    public EditorButton getSaveAllButton()
    {
        return saveAllButton;
    }

    public EditorButton getCutButton()
    {
        return cutButton;
    }

    public EditorButton getCopyButton()
    {
        return copyButton;
    }

    public EditorButton getPasteButton()
    {
        return pasteButton;
    }

    public EditorButton getDeleteButton()
    {
        return deleteButton;
    }

    public EditorButton getUndoButton()
    {
        return undoButton;
    }

    public EditorButton getRedoButton()
    {
        return redoButton;
    }

    public ButtonGroup getToolButtonGroup()
    {
        return toolButtonGroup;
    }

    public JToggleButton getPencilButton()
    {
        return pencilButton;
    }

    public JToggleButton getSelectionButton()
    {
        return selectionButton;
    }

    public JToggleButton getBucketButton()
    {
        return bucketButton;
    }

    public JToggleButton getEraserButton()
    {
        return eraserButton;
    }

    public EditorButton getZoomInButton()
    {
        return zoomInButton;
    }

    public EditorButton getZoomOutButton()
    {
        return zoomOutButton;
    }

    public EditorButton getRunButton()
    {
        return runButton;
    }

    public EditorButton getStopButton()
    {
        return stopButton;
    }

    public EditorButton getHelpButton()
    {
        return helpButton;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void enableButtons(boolean enable)
    {
        this.openButton.setEnabled(enable);
        this.saveButton.setEnabled(enable);
    }
    
    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    public void toggleSelectedOnBoardEditor()
    {
        BoardEditor editor = MainWindow.getInstance().getCurrentBoardEditor();
        
        if (editor != null)
        {
            if (editor.getSelectedObject() != null)
            {
                editor.getSelectedObject().setSelected(false);
            }
            
            editor.setSelectedObject(null);
        }
    }

}
