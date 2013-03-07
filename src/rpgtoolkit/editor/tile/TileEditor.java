package rpgtoolkit.editor.tile;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.TileSet;

public class TileEditor extends JInternalFrame
{
    private TileSet tileSet;
    private TileCanvas canvas;
    private JSpinner tileSelector;
    private JFileChooser openBox;
    private SpinnerListModel slm;

    public TileEditor(File fileName)
    {
        super("Tile Editor", true, true, true, true);

        this.setResizable(true);

        tileSet = new TileSet(fileName);
        slm = new SpinnerListModel(tileSet.getTiles());

        tileSelector = new JSpinner(slm);
        tileSelector.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                if (canvas.hasChanged())
                {
                    switch (JOptionPane.showInternalConfirmDialog(canvas, "This Tile has changed, would you like to save the changes?", "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION))
                    {
                        case JOptionPane.NO_OPTION:
                            canvas.changeTile((Tile) tileSelector.getValue());
                            break;
                        case JOptionPane.CANCEL_OPTION:
                            break;
                        case JOptionPane.YES_OPTION:
                            tileSet.save();
                            canvas.changeTile((Tile) tileSelector.getValue());
                            break;
                    }
                }
                else
                {
                    canvas.changeTile((Tile) tileSelector.getValue());
                }
            }
        });

        canvas = new TileCanvas((Tile) tileSelector.getValue());

        this.setSize(700, 700);
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        this.add(tileSelector, BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
