package net.rpgtoolkit.editor.editors;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.PlayerSpecialMove;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.editor.ui.Gui;
import net.rpgtoolkit.editor.ui.IntegerField;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;
import net.rpgtoolkit.editor.ui.WholeNumberField;

/**
 * Player Character Editor
 *
 * @author Joel Moore
 */
public class CharacterEditor extends ToolkitEditorWindow implements InternalFrameListener
{

    private final Player player; // Player file we are altering
    
    private static final String sep = File.separator;
    private final MainWindow mainWindow = MainWindow.getInstance();

    // Tabs required by the menu
    private JPanel statsPanel;
    private JPanel graphicsPanel;
    private JPanel specialMovesPanel;
    private JPanel equipmentPanel;
    private JPanel levelsPanel;

    private final Border defaultEtchedBorder = BorderFactory.
            createEtchedBorder(EtchedBorder.LOWERED);
    
    //STATS SETTINGS
    private JLabel portraitDisplay;
    private JTextField playerName;
    private IntegerField experience;
    private IntegerField hitPoints;
    private IntegerField maxHitPoints;
    private IntegerField specialPoints;
    private IntegerField maxSpecialPoints;
    private IntegerField fightPower;
    private IntegerField defencePower;
    private IntegerField level;
    private JTextField playerNameVar;
    private JTextField experienceVar;
    private JTextField hitPointsVar;
    private JTextField maxHitPointsVar;
    private JTextField specialPointsVar;
    private JTextField maxSpecialPointsVar;
    private JTextField fightPowerVar;
    private JTextField defencePowerVar;
    private JTextField levelVar;

    // GRAPHICS SETTINGS
    private JList animList;
    private JTextField animLoc;
    private Animation selectedAnim;
    private Timer animTimer;

    // SPECIAL MOVES SETTINGS
    private JCheckBox usesSpecials;
    private JTextField specialsName;
    private JList sMoveList;
    private WholeNumberField sMoveExpMin;
    private WholeNumberField sMoveLvMin;
    private JTextField sMoveVarName;
    private JTextField sMoveVarVal;

    // EQUIPMENT SETTINGS
    private JCheckBox equipHead;
    private JCheckBox equipNeck;
    private JCheckBox equipHandL;
    private JCheckBox equipHandR;
    private JCheckBox equipBody;
    private JCheckBox equipLegs;
    private JList accList;
    private JTextField accName;

    // LEVELS SETTINGS
    private IntegerField initialExpReq;
    private IntegerField expReqIncrease;
    private IntegerField maxLevel;
    private IntegerField maxHPIncrease;
    private IntegerField dpIncrease;
    private IntegerField fpIncrease;
    private IntegerField maxspIncrease;
    private JTextField levelUpProgram;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public CharacterEditor(Player character)
    {
        super("Editing Player Character - " + character.getName(),
                true, true, true, true);
        
        out.println("CharacterEditor begin");
        this.player = character;
        
        this.setSize(800, 530);
        this.constructWindow();
        this.setVisible(true);
        out.println("CharacterEditor end");
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public boolean save()
    {
        return this.player.save();
    }

    public void gracefulClose()
    {

    }

    public void setWindowParent(MainWindow parent)
    {

    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e)
    {

}

    @Override
    public void internalFrameClosing(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e)
    {
        this.gracefulClose();
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e)
    {

    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    /**
     * Builds the Swing interface
     */
    private void constructWindow()
    {
        out.println("constructWindow() begin");
        this.addInternalFrameListener(this);
        
        // Builds the components needed to display the Enemy status.
        JTabbedPane tabPane = new JTabbedPane();

        this.statsPanel = new JPanel();
        this.graphicsPanel = new JPanel();
        this.specialMovesPanel = new JPanel();
        this.equipmentPanel = new JPanel();
        this.levelsPanel = new JPanel();

        this.createStatsPanel();
        this.createGraphicsPanel();
        this.createSpecialMovesPanel();
        this.createEquipmentPanel();
        this.createLevelsPanel();

        tabPane.addTab("Stats and Portrait", this.statsPanel);
        tabPane.addTab("Graphics", this.graphicsPanel);
        tabPane.addTab("Special Moves", this.specialMovesPanel);
        tabPane.addTab("Equipment", this.equipmentPanel);
        tabPane.addTab("Levels", this.levelsPanel);

        this.add(tabPane);
        out.println("constructWindow() end");
    }

    private void createStatsPanel()
    {
        // Configure Class scope components
        this.portraitDisplay = new JLabel();
        this.portraitDisplay.setMaximumSize(new Dimension(64, 64));
        this.playerName = new JTextField(this.player.getName());
        this.experience = new IntegerField(this.player.getInitialExperience());
        this.hitPoints = new IntegerField(this.player.getInitialHP());
        this.maxHitPoints = new IntegerField(this.player.getInitialMaxHP());
        this.specialPoints = new IntegerField(this.player.getInitialMP());
        this.maxSpecialPoints = new IntegerField(this.player.getInitialMaxMP());
        this.fightPower = new IntegerField(this.player.getInitialFP());
        this.defencePower = new IntegerField(this.player.getInitialDP());
        this.level = new IntegerField(this.player.getInitialLevel());
        this.playerNameVar = new JTextField(this.player.getNameVariableName());
        this.experienceVar = new JTextField(this.player.getExpVariableName());
        this.hitPointsVar = new JTextField(this.player.getHpVariableName());
        this.maxHitPointsVar = new JTextField(this.player.getMaxHPVariableName());
        this.specialPointsVar = new JTextField(this.player.getMpVariableName());
        this.maxSpecialPointsVar = new JTextField(this.player.getMaxMPVariableName());
        this.fightPowerVar = new JTextField(this.player.getFpVariableName());
        this.defencePowerVar = new JTextField(this.player.getDpVariableName());
        this.levelVar = new JTextField(this.player.getLvlVariableName());

        // Configure function Scope Components
        String portrait = this.player.getProfilePicture();
        if(portrait.isEmpty() == false) {
            this.portraitDisplay.setIcon(Gui.ImageToIcon(
                    Gui.loadImage(portrait), 64, 64));
        }
        JLabel playerNameLabel = new JLabel("Character Name");
        JLabel experienceLabel = new JLabel("Starting Experience");
        JLabel hitPointsLabel = new JLabel("Starting Health");
        JLabel maxHitPointsLabel = new JLabel("Starting Max Health");
        JLabel specialPointsLabel = new JLabel("Starting Special Move Power");
        JLabel maxSpecialPointsLabel = new JLabel("Starting Max Special Move Power");
        JLabel fightPowerLabel = new JLabel("Starting Fighting Power");
        JLabel defencePowerLabel = new JLabel("Starting Defence Power");
        JLabel levelLabel = new JLabel("Starting Level");
        JLabel playerNameVarLabel = new JLabel("Character Name Variable");
        JLabel experienceVarLabel = new JLabel("Experience Variable");
        JLabel hitPointsVarLabel = new JLabel("Health Variable");
        JLabel maxHitPointsVarLabel = new JLabel("Max Health Variable");
        JLabel specialPointsVarLabel = new JLabel("Special Move Power Variable");
        JLabel maxSpecialPointsVarLabel = new JLabel("Max Special Move Power Variable");
        JLabel fightPowerVarLabel = new JLabel("Fighting Power Variable");
        JLabel defencePowerVarLabel = new JLabel("Defence Power Variable");
        JLabel levelVarLabel = new JLabel("Level Variable");
        JButton portraitFindButton = new JButton("Select Portrait");
        JButton defaultNameBtn = new JButton("Default");
        JButton defaultExperienceBtn = new JButton("Default");
        JButton defaultHitPointsBtn = new JButton("Default");
        JButton defaultMaxHitPointsBtn = new JButton("Default");
        JButton defaultSpecialPointsBtn = new JButton("Default");
        JButton defaultMaxSpecialPointsBtn = new JButton("Default");
        JButton defaultFightPowerBtn = new JButton("Default");
        JButton defaultDefencePowerBtn = new JButton("Default");
        JButton defaultLevelBtn = new JButton("Default");
        
        // Configure listeners
        portraitFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loc = mainWindow.browseByType(
                        "Bitmap", "Supported Files", "png", "gif", "jpg", "jpeg", "bmp");
                if(loc != null) {
                    player.setProfilePicture(loc);
                    portraitDisplay.setIcon(Gui.ImageToIcon(
                            Gui.loadImage(loc), 64, 64));
                }
            }
        });
        defaultNameBtn.addActionListener(
                varDefaultListener(this.playerNameVar, "name", '$'));
        defaultExperienceBtn.addActionListener(
                varDefaultListener(this.experienceVar, "experience", '!'));
        defaultHitPointsBtn.addActionListener(
                varDefaultListener(this.hitPointsVar, "health", '!'));
        defaultMaxHitPointsBtn.addActionListener(
                varDefaultListener(this.maxHitPointsVar, "maxhealth", '!'));
        defaultSpecialPointsBtn.addActionListener(
                varDefaultListener(this.specialPointsVar, "smpower", '!'));
        defaultMaxSpecialPointsBtn.addActionListener(
                varDefaultListener(this.maxSpecialPointsVar, "maxsm", '!'));
        defaultFightPowerBtn.addActionListener(
                varDefaultListener(this.fightPowerVar, "fight", '!'));
        defaultDefencePowerBtn.addActionListener(
                varDefaultListener(this.defencePowerVar, "defence", '!'));
        defaultLevelBtn.addActionListener(
                varDefaultListener(this.levelVar, "level", '!'));

        // Configure the necessary Panels
        JPanel statsEditPanel = new JPanel();
        statsEditPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Stats"));
        JPanel variablesPanel = new JPanel();
        variablesPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Variables"));

        // Create Layout for top level panel
        GroupLayout layout = Gui.createGroupLayout(this.statsPanel);

        // Create Layouts for second level panels
        GroupLayout statsLayout = Gui.createGroupLayout(statsEditPanel);

        GroupLayout variablesLayout = Gui.createGroupLayout(variablesPanel);

        // Configure the STATS EDIT PANEL layout
        statsLayout.setHorizontalGroup(statsLayout.createParallelGroup()
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(playerNameLabel)
                        .addComponent(this.playerName))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(experienceLabel)
                        .addComponent(this.experience))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(hitPointsLabel)
                        .addComponent(this.hitPoints))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(maxHitPointsLabel)
                        .addComponent(this.maxHitPoints))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(specialPointsLabel)
                        .addComponent(this.specialPoints))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(maxSpecialPointsLabel)
                        .addComponent(this.maxSpecialPoints))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(fightPowerLabel)
                        .addComponent(this.fightPower))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(defencePowerLabel)
                        .addComponent(this.defencePower))
                .addGroup(statsLayout.createSequentialGroup()
                        .addComponent(levelLabel)
                        .addComponent(this.level))
        );

        statsLayout.linkSize(SwingConstants.HORIZONTAL,
                playerNameLabel,
                experienceLabel,
                hitPointsLabel,
                maxHitPointsLabel,
                specialPointsLabel,
                maxSpecialPointsLabel,
                fightPowerLabel,
                defencePowerLabel,
                levelLabel);
        statsLayout.linkSize(SwingConstants.VERTICAL,
                this.playerName,
                this.experience,
                this.hitPoints,
                this.maxHitPoints,
                this.specialPoints,
                this.maxSpecialPoints,
                this.fightPower,
                this.defencePower,
                this.level);

        statsLayout.setVerticalGroup(statsLayout.createSequentialGroup()
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(playerNameLabel)
                        .addComponent(this.playerName, Gui.JTF_HEIGHT,
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(experienceLabel)
                        .addComponent(this.experience))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(hitPointsLabel)
                        .addComponent(this.hitPoints))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(maxHitPointsLabel)
                        .addComponent(this.maxHitPoints))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(specialPointsLabel)
                        .addComponent(this.specialPoints))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(maxSpecialPointsLabel)
                        .addComponent(this.maxSpecialPoints))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(fightPowerLabel)
                        .addComponent(this.fightPower))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(defencePowerLabel)
                        .addComponent(this.defencePower))
                .addGroup(statsLayout.createParallelGroup()
                        .addComponent(levelLabel)
                        .addComponent(this.level))
        );

        // Configure the VARIABLES PANEL layout
        variablesLayout.setHorizontalGroup(variablesLayout.createParallelGroup()
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(playerNameVarLabel)
                        .addComponent(this.playerNameVar)
                        .addComponent(defaultNameBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(experienceVarLabel)
                        .addComponent(this.experienceVar)
                        .addComponent(defaultExperienceBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(hitPointsVarLabel)
                        .addComponent(this.hitPointsVar)
                        .addComponent(defaultHitPointsBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(maxHitPointsVarLabel)
                        .addComponent(this.maxHitPointsVar)
                        .addComponent(defaultMaxHitPointsBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(specialPointsVarLabel)
                        .addComponent(this.specialPointsVar)
                        .addComponent(defaultSpecialPointsBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(maxSpecialPointsVarLabel)
                        .addComponent(this.maxSpecialPointsVar)
                        .addComponent(defaultMaxSpecialPointsBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(fightPowerVarLabel)
                        .addComponent(this.fightPowerVar)
                        .addComponent(defaultFightPowerBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(defencePowerVarLabel)
                        .addComponent(this.defencePowerVar)
                        .addComponent(defaultDefencePowerBtn))
                .addGroup(variablesLayout.createSequentialGroup()
                        .addComponent(levelVarLabel)
                        .addComponent(this.levelVar)
                        .addComponent(defaultLevelBtn))
        );

        variablesLayout.linkSize(SwingConstants.HORIZONTAL,
                playerNameVarLabel,
                experienceVarLabel,
                hitPointsVarLabel,
                maxHitPointsVarLabel,
                specialPointsVarLabel,
                maxSpecialPointsVarLabel,
                fightPowerVarLabel,
                defencePowerVarLabel,
                levelVarLabel);
        variablesLayout.linkSize(SwingConstants.VERTICAL,
                this.playerNameVar,
                this.experienceVar,
                this.hitPointsVar,
                this.maxHitPointsVar,
                this.specialPointsVar,
                this.maxSpecialPointsVar,
                this.fightPowerVar,
                this.defencePowerVar,
                this.levelVar);

        variablesLayout.setVerticalGroup(variablesLayout.createSequentialGroup()
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(playerNameVarLabel)
                        .addComponent(this.playerNameVar, Gui.JTF_HEIGHT,
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(defaultNameBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(experienceVarLabel)
                        .addComponent(this.experienceVar)
                        .addComponent(defaultExperienceBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(hitPointsVarLabel)
                        .addComponent(this.hitPointsVar)
                        .addComponent(defaultHitPointsBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(maxHitPointsVarLabel)
                        .addComponent(this.maxHitPointsVar)
                        .addComponent(defaultMaxHitPointsBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(specialPointsVarLabel)
                        .addComponent(this.specialPointsVar)
                        .addComponent(defaultSpecialPointsBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(maxSpecialPointsVarLabel)
                        .addComponent(this.maxSpecialPointsVar)
                        .addComponent(defaultMaxSpecialPointsBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(fightPowerVarLabel)
                        .addComponent(this.fightPowerVar)
                        .addComponent(defaultFightPowerBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(defencePowerVarLabel)
                        .addComponent(this.defencePowerVar)
                        .addComponent(defaultDefencePowerBtn))
                .addGroup(variablesLayout.createParallelGroup()
                        .addComponent(levelVarLabel)
                        .addComponent(this.levelVar)
                        .addComponent(defaultLevelBtn))
        );

        // Configure STATS PANEL layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(this.portraitDisplay)
                        .addComponent(portraitFindButton))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(statsEditPanel, 380, 380, 380)
                        .addComponent(variablesPanel))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, statsEditPanel, variablesPanel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(this.portraitDisplay)
                        .addComponent(portraitFindButton))
                .addGroup(layout.createParallelGroup()
                    .addComponent(statsEditPanel)
                    .addComponent(variablesPanel))
        );
    }

    private void createGraphicsPanel()
    {
        // Configure Class scope components
        final DefaultListModel enemyGraphics = new DefaultListModel();
        final ArrayList<String> standardNames = this.player.getStandardGraphics();
        for(String standardName : standardNames) {
            enemyGraphics.addElement(standardName);
        }
        final ArrayList<String> customNames = this.player.getCustomGraphics();
        for(String customName : customNames) {
            enemyGraphics.addElement(customName);
        }
//        out.println("standardNames="+standardNames.toString());
//        out.println("customNames="+customNames.toString());
//        out.println("customGraphics="+this.enemy.getCustomizedGraphics()); //TODO: This often adds extra blank ones
//        out.println("enemyGraphics="+enemyGraphics.toString());
        this.animList = Gui.createVerticalJList(enemyGraphics);
        
        this.animLoc = new JTextField();
        
        // Configure function Scope Components
        JScrollPane animListScroller = new JScrollPane(this.animList);
        
        JLabel animLabel = new JLabel("Animation");
        final ImageIcon playIcon = new ImageIcon(getClass().
                getResource("/editor/run.png"));
        final ImageIcon stopIcon = new ImageIcon(getClass().
                getResource("/editor/stop.png"));
        final JToggleButton play = new JToggleButton(playIcon);
        final JLabel animDisplay = new JLabel();
        
        JLabel dummy = new JLabel();
        final JButton animFindButton = new JButton("Browse");
        animFindButton.setEnabled(false);
        JButton animAddButton = new JButton("Add");
        final JButton animRemoveButton = new JButton("Remove");
        animRemoveButton.setEnabled(false);
        
        // Configure listeners
        
        //run animation
        final ActionListener animate = new ActionListener() {
            private int frame = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                //switch to the next frame, looping after the last frame
                if(frame < selectedAnim.getFrameCount()-1) {
                    frame++;
                } else {
                    frame = 0;
                }
                animDisplay.setIcon(new ImageIcon(
                        selectedAnim.getFrame(frame).getFrameImage()
                ));
            }
        };
        
        //change selection
        this.animList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting() == false) {
                    if(animList.getSelectedIndex() == -1) {
                        animDisplay.setIcon(null);
                        animFindButton.setEnabled(false);
                        animRemoveButton.setEnabled(false);
                    } else {
                        //switch animation info
                        if(play.isSelected()) { play.doClick(); } //press stop
                        String location;
                        if(animList.getSelectedIndex() < standardNames.size()) {
                            location = player.getStandardGraphics().get(
                                    animList.getSelectedIndex());
                            //out.println("new selection: standard " + animList.getSelectedIndex());
                        } else {
                            location = player.getCustomGraphics().get(
                                    animList.getSelectedIndex() - standardNames.size());
                            //out.println("new selection: custom " + (animList.getSelectedIndex() - standardNames.size()));
                        }
                        //clear animation and images
                        selectedAnim = null;
                        animDisplay.setIcon(null);
                        animTimer = null;
                        //out.println("anim cleared");
                        //out.println("setting location to " + location);
                        animLoc.setText(location); //handles switching to new valid animations
                        
                        animFindButton.setEnabled(true);
                        animRemoveButton.setEnabled(true);
                    }
                }
            }
        });
        
        this.animLoc.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
//                out.println("insert!");
                String text = animLoc.getText();
//                out.println(text);
                updateAnimation(text);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //out.println("remove!");
                String text = animLoc.getText();
                //out.println(text);
                updateAnimation(text);
                if(text.isEmpty()) {
                    //out.println("clearing anim");
                    selectedAnim = null;
                    animDisplay.setIcon(null);
                    animTimer = null;
                }
            }
                
            private void updateAnimation(String text) {
                int index = animList.getSelectedIndex();
//                out.println("update animation index: " + index);
//                out.println(text);
                if(index >= 0 && index < standardNames.size() + customNames.size()) {
                    boolean custom = false;
                    if(index >= standardNames.size()) {
                        custom = true;
                    }
                    if(custom == true) {
                        player.getCustomGraphics().set(
                                index - standardNames.size(), text);
                    } else {
                        player.getStandardGraphics().set(index, text);
                    }
                    if(text.endsWith(".anm")) {
                        //update image if the location is valid
                        File f = new File(System.getProperty("project.path")
                                + sep + "Misc" + sep + text);
                        if(f.canRead()) {
                            selectedAnim = new Animation(f);
//                            out.println("new animation!");
                            //switch animation images
                            if(selectedAnim != null && selectedAnim.getFrameCount() > 0) {
                                animDisplay.setIcon(new ImageIcon(
                                        selectedAnim.getFrame(0).getFrameImage()));
                                animTimer = new Timer((int)(selectedAnim.getFrameDelay() * 1000), animate);
                            }
                        }
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        //play button
        ActionListener playStop = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(play.isSelected()) {
                    if(animTimer != null) {
                        animTimer.start();
                        play.setIcon(stopIcon);
                    }
                } else {
                    if(animTimer != null) {
                        animTimer.stop();
                        play.setIcon(playIcon);}
                    if(selectedAnim != null && selectedAnim.getFrameCount() > 0) {
                        animDisplay.setIcon(new ImageIcon(
                                selectedAnim.getFrame(0).getFrameImage()));
                    }
                }
            }
        };
        play.addActionListener(playStop);
        
        //browse button
        animFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = animList.getSelectedIndex();
                if(index < 0) { return; }
                String loc = mainWindow.browseByType("Misc", "Animation Files", "anm");
                if(loc != null) {
                    if(play.isSelected()) { play.doClick(); } //press stop before we change it
                    animLoc.setText(loc);
                    if(index < standardNames.size()) {
                        player.getStandardGraphics().set(index, loc);
                    } else if(index < standardNames.size() + customNames.size()) {
                        int customIndex = index - standardNames.size();
                        player.getCustomGraphics().set(customIndex, loc);
                    }
                    //changing animation will be handled by animLoc
                }
            }
        });
        
        //add button
        animAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = animList.getSelectedIndex();
                if(index < standardNames.size()) {
                    index = standardNames.size(); //insert at start of custom graphics
                } else if(index > standardNames.size() + customNames.size()) {
                    index = standardNames.size() + customNames.size(); //insert at end
                } else {
                    index++; //insert after current slot
                }
                //add custom graphic
                String name = (String)JOptionPane.showInputDialog(
                        graphicsPanel,
                        "Enter the handle for the new sprite:",
                        "Add Enemy Graphic",
                        JOptionPane.PLAIN_MESSAGE); 
                if(name == null || name.isEmpty()) { return; }
                int customIndex = index - standardNames.size();
                customNames.add(customIndex, name);
                player.getCustomGraphics().add(customIndex, "");
                enemyGraphics.add(index, name);
                //select the new graphic
                animList.setSelectedIndex(index);
                animList.ensureIndexIsVisible(index);
                //changing animation will be handled by animList and animLoc
            }
        });
        
        //remove button
        animRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = animList.getSelectedIndex();
                out.println(index);
                out.println(standardNames.size());
                out.println(customNames.size());
                if(index >= 0) {
                    if(index < standardNames.size()) {
                        if(selectedAnim != null) {
                            //clear standard graphic file location, but don't delete
                            if(play.isSelected()) { play.doClick(); } //press stop before we change it
                            animLoc.setText("");
                            player.getStandardGraphics().set(index, "");
                            //clear animation will be handled by animLoc
                        }
                    } else if(index < standardNames.size() + customNames.size()) {
                        //delete custom graphic
                        int customIndex = index - standardNames.size();
                        customNames.remove(customIndex);
                        player.getCustomGraphics().remove(customIndex);
                        enemyGraphics.remove(index);
                        //move back on the list by 1
                        if(index > 0) {
                            if(index == enemyGraphics.size()) { index--; }
                            animList.setSelectedIndex(index);
                            animList.ensureIndexIsVisible(index);
                            //changing animation will be handled by animList
                        }
                    }
                }
            }
        });

        // Configure the necessary Panels
        JPanel spritePanel = new JPanel();
        spritePanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Sprite List"));

        // Create Layout for Top Level Panel
        GroupLayout layout = Gui.createGroupLayout(this.graphicsPanel);

        // Configure Layouts for Second Level Panels
        GroupLayout spriteLayout = Gui.createGroupLayout(spritePanel);

        // Configure the SPRITE PANEL layout
        spriteLayout.setHorizontalGroup(spriteLayout.createSequentialGroup()
                .addComponent(animListScroller)
                .addGroup(spriteLayout.createParallelGroup()
                        .addComponent(animLabel)
                        .addComponent(this.animLoc)
                        .addGroup(spriteLayout.createSequentialGroup()
                                .addComponent(play)
                                .addComponent(animDisplay)))
                .addGroup(spriteLayout.createParallelGroup()
                        .addComponent(dummy)
                        .addComponent(animFindButton)
                        .addComponent(animAddButton)
                        .addComponent(animRemoveButton))
        );

        spriteLayout.setVerticalGroup(spriteLayout.createParallelGroup()
                .addComponent(animListScroller)
                .addGroup(spriteLayout.createSequentialGroup()
                        .addComponent(animLabel)
                        .addComponent(this.animLoc)
                        .addGroup(spriteLayout.createParallelGroup()
                                .addComponent(play)
                                .addComponent(animDisplay)))
                .addGroup(spriteLayout.createSequentialGroup()
                        .addComponent(dummy)
                        .addComponent(animFindButton)
                        .addComponent(animAddButton)
                        .addComponent(animRemoveButton))
        );

        spriteLayout.linkSize(SwingConstants.VERTICAL, this.animLoc, animLabel,
                dummy, animFindButton, animAddButton, animRemoveButton);

        // Configure the GRAPHICS PANEL layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(spritePanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(spritePanel)
        );
    }

    private void createSpecialMovesPanel()
    {
        // Configure Class scope components
        final DefaultListModel specialMoves = new DefaultListModel();
        final ArrayList<PlayerSpecialMove> sMoves = this.player.getSpecialMoveList();
        for(PlayerSpecialMove sMove : sMoves) {
            String text = getSpecialMoveText(sMove.getName());
            specialMoves.addElement(text);
        }
//        out.println("specialMoves="+sMoveLocs.toString());
//        out.println("strengths="+strengthLocs.toString());
//        out.println("weaknesses="+weaknessLocs.toString());
        this.sMoveList = Gui.createVerticalJList(specialMoves);
        
        // Configure function Scope Components
        JScrollPane sMoveListScroller = new JScrollPane(this.sMoveList);
        
        JButton sMoveFindButton = new JButton("Browse");
        JButton sMoveResetButton = new JButton("The Player Can Always Use This Move");
        final JButton sMoveRemoveButton = new JButton("Remove");
        sMoveRemoveButton.setEnabled(false);
        
        JButton strengthFindButton = new JButton("Browse");
        JButton strengthAddButton = new JButton("Add");
        final JButton strengthRemoveButton = new JButton("Remove");
        strengthRemoveButton.setEnabled(false);
        
        JButton weaknessFindButton = new JButton("Browse");
        JButton weaknessAddButton = new JButton("Add");
        final JButton weaknessRemoveButton = new JButton("Remove");
        weaknessRemoveButton.setEnabled(false);
        
        // Configure listeners
        
        //change selection
        this.sMoveList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting() == false) {
                    if(sMoveList.getSelectedIndex() == -1) {
                        sMoveRemoveButton.setEnabled(false);
                    } else {
                        sMoveRemoveButton.setEnabled(true);
                    }
                }
            }
        });
        
        //browse button
        sMoveFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = sMoveList.getSelectedIndex();
                if(index >= 0) {
                    String loc = browseSpecialMove();
                    if(loc != null) {
                        specialMoves.set(index, getSpecialMoveText(loc));
                    }
                }
            }
        });
        
        //reset button
        sMoveResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //insert after current slot
                int index = sMoveList.getSelectedIndex() + 1;
                String loc = browseSpecialMove();
                if(loc != null) {
                    specialMoves.add(index, getSpecialMoveText(loc));
                }
                //select the added move
                sMoveList.setSelectedIndex(index);
                sMoveList.ensureIndexIsVisible(index);
            }
        });
        
        //remove button
        sMoveRemoveButton.addActionListener(
                Gui.simpleRemoveListener(specialMoves, sMoveList)
        );

        // Configure the necessary Panels
        JPanel sMovePanel = new JPanel();
        sMovePanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Special Moves"));
        JPanel conditionsPanel = new JPanel();
        conditionsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "The Player Can Use This Special Move If"));

        // Create Layout for Top Level Panel
        GroupLayout layout = Gui.createGroupLayout(this.specialMovesPanel);

        // Configure Layouts for Second Level Panels
        GroupLayout sMoveLayout = Gui.createGroupLayout(sMovePanel);
        GroupLayout conditionsLayout = Gui.createGroupLayout(conditionsPanel);

        // Configure the SMOVE PANEL layout
        sMoveLayout.setHorizontalGroup(sMoveLayout.createSequentialGroup()
                .addComponent(sMoveListScroller)
                .addGroup(sMoveLayout.createParallelGroup()
                        .addComponent(sMoveFindButton)
                        .addComponent(sMoveResetButton)
                        .addComponent(sMoveRemoveButton))
        );

        sMoveLayout.setVerticalGroup(sMoveLayout.createParallelGroup()
                .addComponent(sMoveListScroller)
                .addGroup(sMoveLayout.createSequentialGroup()
                        .addComponent(sMoveFindButton)
                        .addComponent(sMoveResetButton)
                        .addComponent(sMoveRemoveButton))
        );

        // Configure the CONDITIONS PANEL layout
        conditionsLayout.setHorizontalGroup(conditionsLayout.createSequentialGroup()
                .addGroup(conditionsLayout.createParallelGroup()
                        .addComponent(strengthFindButton)
                        .addComponent(strengthAddButton)
                        .addComponent(strengthRemoveButton))
        );

        conditionsLayout.setVerticalGroup(conditionsLayout.createParallelGroup()
                .addGroup(conditionsLayout.createSequentialGroup()
                        .addComponent(strengthFindButton)
                        .addComponent(strengthAddButton)
                        .addComponent(strengthRemoveButton))
        );

        // Configure the SPECIAL MOVE PANEL layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(sMovePanel)
                .addComponent(conditionsPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(sMovePanel)
                .addComponent(conditionsPanel)
        );
    }

    private void createEquipmentPanel()
    {   
        this.equipHead = new JCheckBox("Head");
        this.equipNeck = new JCheckBox("Neck Accessory");
        this.equipHandL = new JCheckBox("Left Hand");
        this.equipHandR = new JCheckBox("Right Hand");
        this.equipBody = new JCheckBox("Body Armour");
        this.equipLegs = new JCheckBox("Legs");
        boolean[] armorSlots = this.player.getArmourTypes();
        this.equipHead.setSelected(armorSlots[0]);
        this.equipNeck.setSelected(armorSlots[0]);
        this.equipHandL.setSelected(armorSlots[0]);
        this.equipHandR.setSelected(armorSlots[0]);
        this.equipBody.setSelected(armorSlots[0]);
        this.equipLegs.setSelected(armorSlots[0]);
        
        final JLabel accNameLabel = new JLabel("Slot Name");
        ArrayList<String> accNames = this.player.getAccessoryNames();
        this.accName = new JTextField();
        if(accNames.isEmpty() == false) { this.accName.setText(accNames.get(0)); }
        
        JPanel standardEquipPanel = new JPanel();
        standardEquipPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Standard Equipment Can Be Equipped On"));

        JPanel accessoriesPanel = new JPanel();
        accessoriesPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Accessory List"));
        
        // Configure listeners

        // Configure Layouts
        GroupLayout layout = Gui.createGroupLayout(this.equipmentPanel);

        GroupLayout accessoriesLayout = Gui.createGroupLayout(accessoriesPanel);
        GroupLayout standardEquipLayout = Gui.createGroupLayout(standardEquipPanel);
        
        standardEquipLayout.setHorizontalGroup(standardEquipLayout.createSequentialGroup()
                .addComponent(this.equipHead)
        );

        standardEquipLayout.setVerticalGroup(standardEquipLayout.createParallelGroup()
                .addComponent(this.equipHead)
        );

        accessoriesLayout.setHorizontalGroup(accessoriesLayout.createParallelGroup()
                .addComponent(standardEquipPanel)
                .addGroup(accessoriesLayout.createSequentialGroup()
                        .addComponent(accNameLabel)
                        .addComponent(this.accName))
        );

        accessoriesLayout.setVerticalGroup(accessoriesLayout.createSequentialGroup()
                .addComponent(standardEquipPanel)
                .addGroup(accessoriesLayout.createParallelGroup()
                        .addComponent(accNameLabel)
                        .addComponent(this.accName))
        );
        
        accessoriesLayout.linkSize(SwingConstants.VERTICAL,
                accNameLabel, this.accName
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(accessoriesPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(accessoriesPanel)
        );
    }

    private void createLevelsPanel()
    {
        JLabel experienceAwardedLabel = new JLabel("Experience Gained");
        this.initialExpReq = new IntegerField(this.player.getLevelType());

        JLabel goldAwardedLabel = new JLabel("GP Earned");
        this.expReqIncrease = new IntegerField(this.player.getExpIncreaseFactor());
        
        JLabel levelUpProgramLavel = new JLabel("Level up RPGCode");
        this.levelUpProgram = new JTextField(this.player.getProgramOnLevelUp());
        JButton levelUpProgramFindButton = new JButton("Browse");

        JPanel rewardsSubPanel = new JPanel();
        rewardsSubPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Rewards for Defeating Enemy"));
        
        // Configure listeners
        levelUpProgramFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loc = mainWindow.browseByType("Prg", "Program Files", "prg");
                if(loc != null) {
                    levelUpProgram.setText(loc);
                }
            }
        });

        // Configure Layouts
        GroupLayout layout = Gui.createGroupLayout(this.levelsPanel);

        GroupLayout rewardsPanelLayout = Gui.createGroupLayout(rewardsSubPanel);

        rewardsPanelLayout.setHorizontalGroup(rewardsPanelLayout.createParallelGroup()
                .addGroup(rewardsPanelLayout.createSequentialGroup()
                        .addComponent(experienceAwardedLabel)
                        .addComponent(this.initialExpReq))
                .addGroup(rewardsPanelLayout.createSequentialGroup()
                        .addComponent(goldAwardedLabel)
                        .addComponent(this.expReqIncrease))
                .addComponent(levelUpProgramLavel)
                .addGroup(rewardsPanelLayout.createSequentialGroup()
                        .addComponent(this.levelUpProgram)
                        .addComponent(levelUpProgramFindButton))
        );

        rewardsPanelLayout.setVerticalGroup(rewardsPanelLayout.createSequentialGroup()
                .addGroup(rewardsPanelLayout.createParallelGroup()
                        .addComponent(experienceAwardedLabel)
                        .addComponent(this.initialExpReq))
                .addGroup(rewardsPanelLayout.createParallelGroup()
                        .addComponent(goldAwardedLabel)
                        .addComponent(this.expReqIncrease))
                .addComponent(levelUpProgramLavel)
                .addGroup(rewardsPanelLayout.createParallelGroup()
                        .addComponent(this.levelUpProgram)
                        .addComponent(levelUpProgramFindButton))
        );
        
        rewardsPanelLayout.linkSize(SwingConstants.HORIZONTAL,
                experienceAwardedLabel,
                goldAwardedLabel
        );
        rewardsPanelLayout.linkSize(SwingConstants.VERTICAL,
                experienceAwardedLabel, this.initialExpReq,
                goldAwardedLabel, this.expReqIncrease, levelUpProgramLavel,
                this.levelUpProgram, levelUpProgramFindButton
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(rewardsSubPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(rewardsSubPanel)
        );
    }

    
    private ActionListener varDefaultListener(
            final JTextField varField, String varKey, char type) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                varField.setText(
                        playerName.getText().replace(" ", "_")
                        + "[" + varKey + "]" + type
                );
            }
        };
    }

    private String browseSpecialMove() {
        return mainWindow.browseByType("SpcMove", "Special Move Files", "spc");
    }

    private String getSpecialMoveText(String loc) {
        SpecialMove move = loadSpecialMove(loc);
        String text;
        if(move != null) {
            text = move.getName() + " (" + loc + ")";
        } else {
            text = "Error reading file named: " + loc;
        }
        return text;
    }
    
    private SpecialMove loadSpecialMove(String loc) {
        if(loc.endsWith(".spc")) {
            File f = new File(System.getProperty("project.path")
                    + sep + "SpcMove" + sep + loc);
            if(f.canRead()) {
//                out.println("loaded special move from location " + loc + "!");
                return new SpecialMove(f);
            }
        }
        return null;
    }
}
