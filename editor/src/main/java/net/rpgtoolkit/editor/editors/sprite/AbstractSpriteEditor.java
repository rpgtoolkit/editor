/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.sprite;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AbstractSprite;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.utilities.CoreProperties;
import net.rpgtoolkit.common.assets.events.SpriteChangedEvent;
import net.rpgtoolkit.common.assets.listeners.SpriteChangeListener;
import net.rpgtoolkit.editor.editors.sprite.listener.AddAnimationActionListener;
import net.rpgtoolkit.editor.editors.sprite.listener.AnimationListSelectionListener;
import net.rpgtoolkit.editor.editors.sprite.listener.BrowseAnimationActionListener;
import net.rpgtoolkit.editor.editors.sprite.listener.RemoveAnimationActionListener;
import net.rpgtoolkit.editor.ui.AnimatedPanel;
import net.rpgtoolkit.editor.ui.DoubleField;
import net.rpgtoolkit.editor.utilities.GuiHelper;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.editor.utilities.EditorFileManager;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;
import net.rpgtoolkit.editor.ui.resources.Icons;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractSpriteEditor extends ToolkitEditorWindow implements InternalFrameListener, SpriteChangeListener {

  public static final int DEFAULT_INPUT_COLUMNS = 12;

  // Model for the animation tab.
  private final AbstractSprite sprite;

  protected static final List<String> STANDARD_PLACE_HOLDERS
          = Arrays.asList(
                  "SOUTH",
                  "NORTH",
                  "EAST",
                  "WEST",
                  "NORTH_WEST",
                  "NORTH_EAST",
                  "SOUTH_WEST",
                  "SOUTH_EAST",
                  "ATTACK",
                  "DEFEND",
                  "SPECIAL_MOVE",
                  "DIE",
                  "REST");

  protected static final List<String> STANDING_PLACE_HOLDERS
          = Arrays.asList(
                  "SOUTH_IDLE",
                  "NORTH_IDLE",
                  "EAST_IDLE",
                  "WEST_IDLE",
                  "NORTH_WEST_IDLE",
                  "NORTH_EAST_IDLE",
                  "SOUTH_WEST_IDLE",
                  "SOUTH_EAST_IDLE");

  // Tabs.
  protected final JTabbedPane tabbedPane;

  // Stats Panel.
  protected final JPanel statsPanel;
  protected final ProfilePanel profilePanel;
  protected final JPanel statsEditPanel;
  protected String profileImagePath;

  // Animations Panel.
  protected final JPanel animationsPanel;
  protected AnimatedPanel animatedPanel;
  protected JTable animationsTable;
  protected AnimationsTableModel animationsTableModel;
  protected Animation selectedAnim;
  protected DoubleField idleTimeoutField;
  protected DoubleField stepRateField;

  protected final Border defaultEtchedBorder;
  
  private JButton browseButton;
  private JButton addButton;
  private JButton removeButton;

  public AbstractSpriteEditor(String title, AbstractSprite model, ImageIcon icon) {
    super(title, true, true, true, true, icon);
    this.sprite = model;
    tabbedPane = new JTabbedPane();

    statsPanel = new JPanel();
    animationsPanel = new JPanel();

    profilePanel = new ProfilePanel();
    statsEditPanel = new JPanel();

    defaultEtchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  }

  public AbstractSprite getSprite() {
    return sprite;
  }

  public ProfilePanel getProfilePanel() {
    return profilePanel;
  }

  public JPanel getAnimationsPanel() {
    return animationsPanel;
  }

  public JButton getBrowseButton() {
    return browseButton;
  }

  public JButton getAddButton() {
    return addButton;
  }

  public JButton getRemoveButton() {
    return removeButton;
  }

  public String getProfileImagePath() {
    return profileImagePath;
  }

  public void setProfileImagePath(String profileImagePath) {
    this.profileImagePath = profileImagePath;
  }

  public AnimatedPanel getAnimatedPanel() {
    return animatedPanel;
  }

  public void setAnimatedPanel(AnimatedPanel animatedPanel) {
    this.animatedPanel = animatedPanel;
  }

  public JTable getAnimationsTable() {
    return animationsTable;
  }

  public void setAnimationsTable(JTable animationsTable) {
    this.animationsTable = animationsTable;
  }

  public AnimationsTableModel getAnimationsTableModel() {
    return animationsTableModel;
  }

  public void setAnimationsTableModel(AnimationsTableModel animationsTableModel) {
    this.animationsTableModel = animationsTableModel;
  }

  public Animation getSelectedAnim() {
    return selectedAnim;
  }

  public void setSelectedAnim(Animation selectedAnim) {
    this.selectedAnim = selectedAnim;
  }

  public DoubleField getIdleTimeoutField() {
    return idleTimeoutField;
  }

  public void setIdleTimeoutField(DoubleField idleTimeoutField) {
    this.idleTimeoutField = idleTimeoutField;
  }

  public DoubleField getStepRateField() {
    return stepRateField;
  }

  public void setStepRateField(DoubleField stepRateField) {
    this.stepRateField = stepRateField;
  }
  
    @Override
  public void internalFrameOpened(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameClosing(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameClosed(InternalFrameEvent e) {
    sprite.removeSpriteChangeListener(this);
    sprite.removeSpriteChangeListener(animationsTableModel);
  }

  @Override
  public void internalFrameIconified(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameDeiconified(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameActivated(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameDeactivated(InternalFrameEvent e) {

  }
  
  @Override
  public void spriteChanged(SpriteChangedEvent e) {
    updateAnimatedPanel();
  }

  @Override
  public void spriteAnimationAdded(SpriteChangedEvent e) {
  }

  @Override
  public void spriteAnimationUpdated(SpriteChangedEvent e) {
  }

  @Override
  public void spriteAnimationRemoved(SpriteChangedEvent e) {
  }

    public void setupNewSprite() {
    String undefined = "Undefined";
    sprite.setName(undefined);
    sprite.setProfilePicture("");

    sprite.setStandardGraphics(new ArrayList<>(STANDARD_PLACE_HOLDERS));
    sprite.setStandingGraphics(new ArrayList<>(STANDING_PLACE_HOLDERS));

    BoardVector base = new BoardVector();
    base.addPoint(0, 0);
    base.addPoint(30, 0);
    base.addPoint(30, 20);
    base.addPoint(0, 20);
    base.setClosed(true);
    sprite.setBaseVector(base);

    BoardVector activation = new BoardVector();
    activation.addPoint(0, 0);
    activation.addPoint(30, 0);
    activation.addPoint(30, 20);
    activation.addPoint(0, 20);
    activation.setClosed(true);
    sprite.setActivationVector(activation);

    sprite.setBaseVectorOffset(new Point(40, 0));
    sprite.setActivationVectorOffset(new Point(0, 0));

    sprite.setCustomGraphics(new ArrayList<>());
    sprite.setCustomGraphicNames(new ArrayList<>());
  }
  
  public void updateAnimatedPanel() {
    if (animatedPanel == null) {
      return;
    }

    if (selectedAnim != null) {
      animatedPanel.setBaseVector(sprite.getBaseVector());
      animatedPanel.setActivationVector(sprite.getActivationVector());
      animatedPanel.setBaseVectorOffset(sprite.getBaseVectorOffset());
      animatedPanel.setActivationVectorOffset(sprite.getActivationVectorOffset());
    }

    animatedPanel.setAnimation(selectedAnim);
  }

  public void openAnimation(String path) {
    if (!path.isEmpty()) {
      File file = EditorFileManager.getPath(
              EditorFileManager.getTypeSubdirectory(Animation.class)
              + File.separator
              + path);
      if (file.exists()) {
        selectedAnim = MainWindow.getInstance().openAnimation(file);
      } else {
        selectedAnim = null;
      }

      updateAnimatedPanel();
    }
  }

  protected void build() {
    tabbedPane.addTab("Stats", statsPanel);
    tabbedPane.addTab("Animations", animationsPanel);

    JScrollPane scrollPane = new JScrollPane(tabbedPane);
    add(scrollPane);
  }

  protected void buildStatsPanel(List<Component> labels, List<Component> inputs) {
    // Configure the necessary Panels
    statsEditPanel.setBorder(BorderFactory.createTitledBorder(
            defaultEtchedBorder, "Starting Stats"));

    // Create Layout for top level panel
    GroupLayout layout = GuiHelper.createGroupLayout(statsPanel);

    // Create Layouts for second level panels
    GroupLayout statsLayout = GuiHelper.createGroupLayout(statsEditPanel);

    GroupLayout.ParallelGroup horizontalParallelGroup = statsLayout.createParallelGroup();
    GroupLayout.SequentialGroup sequentialGroup;
    int length = labels.size();
    for (int i = 0; i < length; i++) {
      sequentialGroup = statsLayout.createSequentialGroup();
      sequentialGroup.addComponent(labels.get(i));
      sequentialGroup.addComponent(inputs.get(i));

      statsLayout.setHorizontalGroup(horizontalParallelGroup
              .addGroup(sequentialGroup));
    }

    GroupLayout.SequentialGroup verticalSequentialGroup = statsLayout.createSequentialGroup();
    GroupLayout.ParallelGroup parallelGroup;
    for (int i = 0; i < length; i++) {
      parallelGroup = statsLayout.createParallelGroup();
      parallelGroup.addComponent(labels.get(i));
      parallelGroup.addComponent(inputs.get(i));
      statsLayout.setVerticalGroup(verticalSequentialGroup
              .addGroup(parallelGroup));
    }

    statsLayout.linkSize(SwingConstants.HORIZONTAL,
            labels.toArray(new Component[labels.size()]));

    statsLayout.linkSize(SwingConstants.VERTICAL,
            labels.toArray(new Component[inputs.size()]));

    JPanel configPanel = new JPanel(new BorderLayout());
    configPanel.add(statsEditPanel, BorderLayout.NORTH);

    if (!profileImagePath.isEmpty()) {
      profilePanel.addImage(new File(
              System.getProperty("project.path")
              + File.separator
              + CoreProperties.getProperty("toolkit.directory.bitmap")
              + File.separator
              + profileImagePath));
    }

    // Configure STATS PANEL layout
    layout.setHorizontalGroup(
            layout.createSequentialGroup()
            .addComponent(profilePanel)
            .addComponent(configPanel)
    );

    layout.linkSize(SwingConstants.VERTICAL, profilePanel, configPanel);

    layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(profilePanel)
                    .addComponent(configPanel))
    );
  }

  protected void buildAnimationsPanel() {
    // Configure Class scope components
    animationsTableModel = new AnimationsTableModel(sprite);
    sprite.addSpriteChangeListener(animationsTableModel);

    animationsTable = new JTable(animationsTableModel);
    animationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JScrollPane animationScrollPane = new JScrollPane(animationsTable);

    animatedPanel = new AnimatedPanel(new Dimension(0, AnimatedPanel.DEFAULT_HEIGHT));

    addButton = new JButton();
    addButton.setIcon(Icons.getSmallIcon("new"));

    browseButton = new JButton();
    browseButton.setIcon(Icons.getSmallIcon("open"));
    browseButton.setEnabled(false);

    removeButton = new JButton();
    removeButton.setIcon(Icons.getSmallIcon("delete"));
    removeButton.setEnabled(false);

    animationsTable.getSelectionModel().addListSelectionListener(
            new AnimationListSelectionListener(this));

    browseButton.addActionListener(new BrowseAnimationActionListener(this));
    addButton.addActionListener(new AddAnimationActionListener(this));
    removeButton.addActionListener(new RemoveAnimationActionListener(this));

    // Configure the necessary Panels
    JPanel configurationPanel = new JPanel();
    configurationPanel.add(addButton);
    configurationPanel.add(browseButton);
    configurationPanel.add(removeButton);

    configurationPanel.add(new JLabel("Idle Timeout: "));
    idleTimeoutField = new DoubleField(sprite.getIdleTimeBeforeStanding());
    configurationPanel.add(idleTimeoutField);

    configurationPanel.add(new JLabel("Step Rate: "));
    stepRateField = new DoubleField(sprite.getFrameRate());
    configurationPanel.add(stepRateField);

    // Fix the size of this panel to stop the JTable growing beyond the Window.
    AnimationsTablePanel southPanel = new AnimationsTablePanel(profilePanel);
    southPanel.add(animationScrollPane, BorderLayout.CENTER);
    southPanel.add(configurationPanel, BorderLayout.SOUTH);

    // Create Layout for Top Level Panel
    GroupLayout layout = GuiHelper.createGroupLayout(animationsPanel);

    // Configure the GRAPHICS PANEL layout
    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(animatedPanel)
            .addComponent(southPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(animatedPanel)
            .addComponent(southPanel)
    );
  }
  
  protected void checkProfileImagePath() {
    if (profilePanel.getFile() != null) {
      String remove = 
              System.getProperty("project.path")
              + File.separator
              + CoreProperties.getProperty("toolkit.directory.bitmap")
              + File.separator;
      String path = profilePanel.getFile().getAbsolutePath().replace(remove, "");
      sprite.setProfilePicture(path);
    }
  }

}
