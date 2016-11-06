/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import net.rpgtoolkit.editor.ui.listeners.PopupListFilesListener;

/**
 * Contains useful shortcuts and constants for building editors via Swing.
 *
 * @author Joel Moore
 */
public abstract class Gui {

  public static final int JTF_HEIGHT = 24;

  /**
   * Creates a GroupLayout for the specified panel and assigns it to that panel. Common settings are
   * applied: auto create gaps and auto create container gaps are true.
   *
   * @param forPanel the JPanel to use as the host for the layout; this panel's layout will also be
   * set to the created layout
   * @return the created GroupLayout
   */
  public static GroupLayout createGroupLayout(JPanel forPanel) {
    GroupLayout layout = new GroupLayout(forPanel);
    forPanel.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    return layout;
  }

  /**
   * Creates a JList with the specified data. Common settings are applied: selection mode is single
   * selection, layout orientation is vertical, visible row count is given a negative number.
   *
   * @param dataModel the list of data to assign to the JList
   * @return a new, configured JList
   */
  public static JList createVerticalJList(ListModel dataModel) {
    JList list = new JList(dataModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setLayoutOrientation(JList.VERTICAL);
    list.setVisibleRowCount(-1);
    return list;
  }

  /**
   * Creates and returns a new ActionListener that removes the currently selected item from a JList
   * and from its DefaultListModel.
   *
   * @param backingList
   * @param listComponent
   * @return a new ActionListener that removes the currently selected item when triggered
   */
  public static ActionListener simpleRemoveListener(
          final DefaultListModel backingList, final JList listComponent) {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = listComponent.getSelectedIndex();
        if (index >= 0) {
          backingList.remove(index);
          if (index == backingList.size()) {
            index--;
          }
          listComponent.setSelectedIndex(index);
          listComponent.ensureIndexIsVisible(index);
        }
      }
    };
  }

  /**
   * Given an image, create an icon, scaled to fit in a box of the given width and height. Preserves
   * aspect ratio. Will scale up if the box is bigger than the image's dimensions in both
   * directions. Will scale down if the box is smaller than image's dimensions in either direction.
   *
   * Hooray for Stack Overflow (David Kroukamp's answer):
   * http://stackoverflow.com/questions/14548808/scale-the-imageicon-automatically-to-label-size
   *
   * @param image
   * @param width
   * @param height
   * @return a new ImageIcon from the scaled image, or null if image was null
   */
  public static ImageIcon ImageToIcon(BufferedImage image, int width, int height) {
    if (image == null) {
      return null;
    }
    double scale;
    int originalW = image.getWidth();
    int originalH = image.getHeight();
    if (originalW >= originalH) {
      scale = (double) width / originalW;
    } else {
      scale = (double) height / originalH;
    }
    int resizedW = (int) (originalW * scale);
    int resizedH = (int) (originalH * scale);
    BufferedImage resized = new BufferedImage(
            resizedW, resizedH, BufferedImage.TRANSLUCENT
    );
    Graphics2D g = resized.createGraphics();
    g.addRenderingHints(new RenderingHints(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
    ));
    g.drawImage(image, 0, 0, resizedW, resizedH, null);
    g.dispose();
    return new ImageIcon(resized);
  }

  /**
   * Loads an image given a filename relative to the Bitmap directory.
   *
   * @param fileName the fileName of the image, relative to the Bitmap directory
   * @return the loaded image, or null if given a blank fileName or if an exception occurred
   */
  public static BufferedImage loadImage(String fileName) {
    try {
      if (!fileName.equals("")) {
        String subdir = MainWindow.getInstance().getImageSubdirectory();
        FileInputStream fis = new FileInputStream(
                MainWindow.getInstance().getPath(subdir + "/" + fileName));
        return ImageIO.read(fis);
      }
    } catch (IOException e) {
      System.err.println(e);
    }
    return null;
  }

  /**
   * Gets a list of all the documents under a directory with the required extensions. For use with
   * JComboBoxes.
   * 
   * @param rootDirectory
   * @param extensions
   * @param recursive
   * @return 
   */
  public static JComboBox getFileListJComboBox(File rootDirectory, String[] extensions,
          boolean recursive) {
    JComboBox comboBox = new JComboBox();
    comboBox.addPopupMenuListener(new PopupListFilesListener(
            rootDirectory, extensions, recursive, comboBox));
    comboBox.setPrototypeDisplayValue("*****************");
    comboBox.insertItemAt("", 0);
    return comboBox;
  }

}
