package rpgtoolkit.editor.enemy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.System.out;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import rpgtoolkit.common.io.types.Animation;
import rpgtoolkit.common.io.types.Enemy;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.editor.main.ToolkitEditorWindow;
import rpgtoolkit.editor.utilities.Gui;
import rpgtoolkit.editor.utilities.WholeNumberField;

/**
 * Enemy editor
 *
 * @author Joel Moore
 */
public class EnemyEditor extends ToolkitEditorWindow implements InternalFrameListener
{

    private JFileChooser openEnemy;
    private final Enemy enemy; // Enemy file we are altering

    // Tabs required by the menu
    private JPanel basicSettingsPanel;
    private JPanel graphicsPanel;
    private JPanel specialMovesPanel;
    private JPanel tacticsPanel;
    private JPanel rewardsPanel;

    private final Border defaultEtchedBorder = BorderFactory.
            createEtchedBorder(EtchedBorder.LOWERED);
    
    //BASIC SETTINGS
    private JTextField enemyName;
    private WholeNumberField maxHitPoints;
    private WholeNumberField maxSpecialPoints;
    private WholeNumberField fightPower;
    private WholeNumberField defencePower;
    private JCheckBox canRunAway;
    private JTextField runAwayProgram;
    private WholeNumberField critOnEnemy;
    private WholeNumberField critOnPlayer;

    // GRAPHICS SETTINGS
    private JList animList;
    private JTextField animLoc;
    private Animation selectedAnim;
    private JLabel animDisplay = new JLabel();
    private Timer animTimer;

    // SPECIAL MOVES SETTINGS
    private JTextField initialBoard;
    private JTextField initialChar;
    private JSlider charSpeed;
    private JRadioButton pixelMovement;
    private JRadioButton tileMovement;
    private JCheckBox pushInPixels;
    private JComboBox pathfinding;

    // TACTICS SETTINGS
    private JTextField runTimeProgram;
    private JTextField startupProgram;
    private JTextField gameOverProgram;
    private JTextField runTimeKey;
    private JTextField menuKey;
    private JTextField generalKey;

    // REWARDS SETTINGS
    private WholeNumberField experienceAwarded;
    private WholeNumberField goldAwarded;
    private JTextField victoryProgram;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    /**
     * Create a new blank Enemy
     */
    public EnemyEditor()
    {
        super("New Enemy", true, true, true, true);

        this.enemy = new Enemy();
        this.setVisible(true);
    }

    /**
     * Opens an existing enemy
     *
     * @param theEnemy Enemy to edit
     */
    public EnemyEditor(Enemy theEnemy)
    {
        super("Editing Enemy - " + theEnemy.toString(), true, true, true, true);

        this.enemy = theEnemy;

        this.setSize(555, 530);
        this.constructWindow();
        this.setVisible(true);
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public boolean save()
    {
        return this.enemy.save();
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
        this.addInternalFrameListener(this);
        
        // Builds the components needed to display the Enemy status.
        JTabbedPane tabPane = new JTabbedPane();

        this.basicSettingsPanel = new JPanel();
        this.graphicsPanel = new JPanel();
        this.specialMovesPanel = new JPanel();
        this.tacticsPanel = new JPanel();
        this.rewardsPanel = new JPanel();

        this.createBasicSettingsPanel();
        this.createGraphicsPanel();
        this.createSpecialMovesPanel();
        this.createTacticsPanel();
        this.createRewardsPanel();

        tabPane.addTab("Basic Settings", this.basicSettingsPanel);
        tabPane.addTab("Graphics", this.graphicsPanel);
        tabPane.addTab("Special Moves", this.specialMovesPanel);
        tabPane.addTab("Tactics", this.tacticsPanel);
        tabPane.addTab("Rewards", this.rewardsPanel);

        this.add(tabPane);
        // this.setJMenuBar(new ProjectEditorMenu(this.parent));
    }

    private void createBasicSettingsPanel()
    {
        // Configure Class scope components
        this.enemyName = new JTextField(this.enemy.getName());
        this.maxHitPoints = new WholeNumberField(this.enemy.getMaxHitPoints());
        this.maxSpecialPoints = new WholeNumberField(this.enemy.getMaxMagicPoints());
        this.fightPower = new WholeNumberField(this.enemy.getFightPower());
        this.defencePower = new WholeNumberField(this.enemy.getDefencePower());
        this.canRunAway = new JCheckBox();
        this.canRunAway.setEnabled(this.enemy.canRunAway());
        this.runAwayProgram = new JTextField(this.enemy.getRunAwayProgram());
        this.critOnEnemy = new WholeNumberField(this.enemy.getSneakChance());
        this.critOnPlayer = new WholeNumberField(this.enemy.getSurpriseChance());

        // Configure function Scope Components
        JLabel enemyNameLabel = new JLabel("Name");
        JLabel maxHitPointsLabel = new JLabel("Max Health Points");
        JLabel maxSpecialPointsLabel = new JLabel("Special Move Power");
        JLabel fightPowerLabel = new JLabel("Fighting Power");
        JLabel defencePowerLabel = new JLabel("DefencePower");
        JLabel canRunAwayLabel = new JLabel("Player can run from this enemy");
        JLabel dummy = new JLabel();
        JLabel runAwayProgramLabel = new JLabel("Program to run when player runs away");
        JButton runAwayProgramButton = new JButton("Browse");
        JLabel critOnEnemyLabel = new JLabel("Chances of a critical hit on the enemy: (1 in)");
        JLabel critOnPlayerLabel = new JLabel("Chances of a critical hit on the player: (1 in)");

        // Configure the necessary Panels
        JPanel basicInfoPanel = new JPanel();
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Basic Information"));
        JPanel fightingConditionsPanel = new JPanel();
        fightingConditionsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Fighting Conditions"));

        // Create Layout for top level panel
        GroupLayout layout = Gui.createGroupLayout(this.basicSettingsPanel);

        // Create Layouts for second level panels
        GroupLayout basicInfoLayout = Gui.createGroupLayout(basicInfoPanel);

        GroupLayout fightingConditionsLayout = Gui.createGroupLayout(fightingConditionsPanel);

        // Configure the BASIC INFO PANEL layout
        basicInfoLayout.setHorizontalGroup(basicInfoLayout.createParallelGroup()
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(enemyNameLabel)
                        .addComponent(this.enemyName))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(maxHitPointsLabel)
                        .addComponent(this.maxHitPoints))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(maxSpecialPointsLabel)
                        .addComponent(this.maxSpecialPoints))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(fightPowerLabel)
                        .addComponent(this.fightPower))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(defencePowerLabel)
                        .addComponent(this.defencePower))
        );

        basicInfoLayout.linkSize(SwingConstants.HORIZONTAL,
                enemyNameLabel,
                maxHitPointsLabel,
                maxSpecialPointsLabel,
                fightPowerLabel,
                defencePowerLabel);
        basicInfoLayout.linkSize(SwingConstants.VERTICAL,
                this.enemyName,
                this.maxHitPoints,
                this.maxSpecialPoints,
                this.fightPower,
                this.defencePower);

        basicInfoLayout.setVerticalGroup(basicInfoLayout.createSequentialGroup()
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(enemyNameLabel)
                        .addComponent(this.enemyName, Gui.JTF_HEIGHT,
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(maxHitPointsLabel)
                        .addComponent(this.maxHitPoints))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(maxSpecialPointsLabel)
                        .addComponent(this.maxSpecialPoints))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(fightPowerLabel)
                        .addComponent(this.fightPower))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(defencePowerLabel)
                        .addComponent(this.defencePower))
        );

        // Configure the FIGHTING CONDITIONS PANEL layout
        fightingConditionsLayout.setHorizontalGroup(fightingConditionsLayout.createParallelGroup()
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addComponent(this.canRunAway)
                        .addComponent(canRunAwayLabel))
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addComponent(dummy) //TODO: surely there's a good way to get the width + padding of a checkbox?
                        .addComponent(runAwayProgramLabel)
                        .addComponent(this.runAwayProgram)
                        .addComponent(runAwayProgramButton))
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addComponent(critOnEnemyLabel)
                        .addComponent(this.critOnEnemy))
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addComponent(critOnPlayerLabel)
                        .addComponent(this.critOnPlayer))
        );

        fightingConditionsLayout.linkSize(SwingConstants.HORIZONTAL, 
                runAwayProgramLabel,
                critOnEnemyLabel,
                critOnPlayerLabel);
        fightingConditionsLayout.linkSize(SwingConstants.HORIZONTAL, 
                this.canRunAway,
                dummy);
        fightingConditionsLayout.linkSize(SwingConstants.VERTICAL, 
                this.canRunAway,
                canRunAwayLabel);
        fightingConditionsLayout.linkSize(SwingConstants.VERTICAL, 
                this.runAwayProgram,
                this.critOnEnemy,
                this.critOnPlayer);

        fightingConditionsLayout.setVerticalGroup(fightingConditionsLayout.createSequentialGroup()
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(this.canRunAway)
                        .addComponent(canRunAwayLabel))
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(dummy)
                        .addComponent(runAwayProgramLabel)
                        .addComponent(this.runAwayProgram, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(runAwayProgramButton))
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(critOnEnemyLabel)
                        .addComponent(this.critOnEnemy))
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(critOnPlayerLabel)
                        .addComponent(this.critOnPlayer))
        );

        // Configure BASIC SETTINGS PANEL layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(basicInfoPanel, 515, 515, 515)
                .addComponent(fightingConditionsPanel)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, basicInfoPanel, fightingConditionsPanel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(basicInfoPanel)
                .addComponent(fightingConditionsPanel)
        );
    }

    private void createSpecialMovesPanel()
    {
        this.initialBoard = new JTextField();
        this.initialChar = new JTextField();
        this.charSpeed = new JSlider();
        this.charSpeed.setMaximum(3);
        this.charSpeed.setMinimum(-3);
        this.charSpeed.setMajorTickSpacing(1);
        this.charSpeed.setMinorTickSpacing(1);
        this.charSpeed.setPaintLabels(true);
        this.charSpeed.setValue(0);
        this.pixelMovement = new JRadioButton();
        this.tileMovement = new JRadioButton();
        this.pushInPixels = new JCheckBox();
        this.pathfinding = new JComboBox();
        this.pathfinding.addItem("Axial");
        this.pathfinding.addItem("Diagonal");
        this.pathfinding.addItem("Vector");
        this.pathfinding.setEditable(false);
        this.pathfinding.setSelectedIndex(2);

        JLabel initialBoardLabel = new JLabel("Initial Board");
        JButton initialBoardButton = new JButton("Browse");
        JLabel initialCharLabel = new JLabel("Initial Character");
        JButton initialCharButton = new JButton("Browse");
        JLabel blankBoardNote = new JLabel("You may leave the initial board "
                + "blank if you wish");
        JLabel charSpeedLabel = new JLabel("Adjust game speed");
        JLabel charSpeedNote = new JLabel("This is a secondary option, leave at "
                + "zero for default character speeds");
        JLabel movementLabel = new JLabel("Movement Style");
        JLabel pixelMovementLabel = new JLabel("Per Pixel");
        JLabel tileMovementLabel = new JLabel("Per Tile");
        JLabel pushInPixelsLabel = new JLabel("Push[] in pixel increments");
        JButton mouseCursorButton = new JButton("Mouse Cursor");
        JLabel pathFindingLabel = new JLabel("Default Path Finding Mode");

        // Configure the necessary Panels
        JPanel conditionsPanel = new JPanel();
        conditionsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Startup Settings"));
        JPanel miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Miscellaneous Settings"));

        GroupLayout layout = new GroupLayout(this.specialMovesPanel);
        this.specialMovesPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout conditionsLayout = new GroupLayout(conditionsPanel);
        conditionsPanel.setLayout(conditionsLayout);
        conditionsLayout.setAutoCreateGaps(true);
        conditionsLayout.setAutoCreateContainerGaps(true);

        GroupLayout miscLayout = new GroupLayout(miscPanel);
        miscPanel.setLayout(miscLayout);
        miscLayout.setAutoCreateGaps(true);
        miscLayout.setAutoCreateContainerGaps(true);

        conditionsLayout.setHorizontalGroup(conditionsLayout.createParallelGroup()
                .addGroup(conditionsLayout.createSequentialGroup()
                        .addComponent(initialBoardLabel, 75, 75, 75)
                        .addComponent(this.initialBoard)
                        .addComponent(initialBoardButton))
                .addGroup(conditionsLayout.createSequentialGroup()
                        .addComponent(initialCharLabel)
                        .addComponent(this.initialChar)
                        .addComponent(initialCharButton))
                .addComponent(blankBoardNote)
        );

        conditionsLayout.linkSize(SwingConstants.HORIZONTAL, initialBoardLabel, 
                initialCharLabel);
        conditionsLayout.linkSize(SwingConstants.VERTICAL, this.initialBoard, 
                this.initialChar);

        conditionsLayout.setVerticalGroup(conditionsLayout.createSequentialGroup()
                .addGroup(conditionsLayout.createParallelGroup()
                        .addComponent(initialBoardLabel)
                        .addComponent(this.initialBoard, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(initialBoardButton))
                .addGroup(conditionsLayout.createParallelGroup()
                        .addComponent(initialCharLabel)
                        .addComponent(this.initialChar)
                        .addComponent(initialCharButton))
                .addComponent(blankBoardNote)
        );

        miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(charSpeedLabel)
                        .addComponent(this.charSpeed))
                .addComponent(charSpeedNote)
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(movementLabel)
                        .addComponent(this.pixelMovement)
                        .addComponent(pixelMovementLabel)
                        .addComponent(this.tileMovement)
                        .addComponent(tileMovementLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(this.pushInPixels)
                        .addComponent(pushInPixelsLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(mouseCursorButton)
                        .addComponent(pathFindingLabel)
                        .addComponent(this.pathfinding))
        );

        miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(charSpeedLabel)
                        .addComponent(this.charSpeed))
                .addComponent(charSpeedNote)
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(movementLabel)
                        .addComponent(this.pixelMovement)
                        .addComponent(pixelMovementLabel)
                        .addComponent(this.tileMovement)
                        .addComponent(tileMovementLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(this.pushInPixels)
                        .addComponent(pushInPixelsLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(mouseCursorButton)
                        .addComponent(pathFindingLabel)
                        .addComponent(this.pathfinding, 22, 22, 22))
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(conditionsPanel, 515, 515, 515)
                .addComponent(miscPanel)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, conditionsPanel, miscPanel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(conditionsPanel)
                .addComponent(miscPanel)
        );
    }

    private void createTacticsPanel()
    {
        // Configure Class scope components
        this.runTimeProgram = new JTextField();
        this.startupProgram = new JTextField();
        this.gameOverProgram = new JTextField();
        this.runTimeKey = new JTextField();
        this.menuKey = new JTextField();
        this.generalKey = new JTextField();

        // Configure Function scope components
        JLabel runTimeProgramLabel = new JLabel("Run Time Program");
        JLabel startupProgramLabel = new JLabel("Startup Program");
        JLabel gameOverProgramLabel = new JLabel("Game Over Program");
        JButton runTimeProgramButton = new JButton("Browse");
        JButton startupProgramButton = new JButton("Browse");
        JButton gameOverProgramButton = new JButton("Browse");
        JLabel runTimeKeyLabel = new JLabel("Run Time");
        JLabel menuKeyLabel = new JLabel("Display Menu");
        JLabel generalKeyLabel = new JLabel("General Key");
        JButton moreKeysButton = new JButton("More");

        // Configure Panels
        JPanel programPanel = new JPanel();
        programPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Programs"));
        JPanel keysPanel = new JPanel();
        keysPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Keys"));

        // Configure Layouts
        GroupLayout layout = new GroupLayout(this.tacticsPanel);
        this.tacticsPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout programPanelLayout = new GroupLayout(programPanel);
        programPanel.setLayout(programPanelLayout);
        programPanelLayout.setAutoCreateGaps(true);
        programPanelLayout.setAutoCreateContainerGaps(true);

        GroupLayout keysPanelLayout = new GroupLayout(keysPanel);
        keysPanel.setLayout(keysPanelLayout);
        keysPanelLayout.setAutoCreateGaps(true);
        keysPanelLayout.setAutoCreateContainerGaps(true);

        programPanelLayout.setHorizontalGroup(programPanelLayout.createParallelGroup()
                .addGroup(programPanelLayout.createSequentialGroup()
                        .addComponent(runTimeProgramLabel)
                        .addComponent(this.runTimeProgram)
                        .addComponent(runTimeProgramButton))
                .addGroup(programPanelLayout.createSequentialGroup()
                        .addComponent(startupProgramLabel)
                        .addComponent(this.startupProgram)
                        .addComponent(startupProgramButton))
                .addGroup(programPanelLayout.createSequentialGroup()
                        .addComponent(gameOverProgramLabel)
                        .addComponent(this.gameOverProgram)
                        .addComponent(gameOverProgramButton))
        );

        programPanelLayout.linkSize(SwingConstants.VERTICAL, this.gameOverProgram,
                this.startupProgram, this.runTimeProgram);
        programPanelLayout.linkSize(SwingConstants.HORIZONTAL, 
                gameOverProgramLabel, startupProgramLabel, runTimeProgramLabel);

        programPanelLayout.setVerticalGroup(programPanelLayout.createSequentialGroup()
                .addGroup(programPanelLayout.createParallelGroup()
                        .addComponent(runTimeProgramLabel)
                        .addComponent(this.runTimeProgram, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(runTimeProgramButton))
                .addGroup(programPanelLayout.createParallelGroup()
                        .addComponent(startupProgramLabel)
                        .addComponent(this.startupProgram)
                        .addComponent(startupProgramButton))
                .addGroup(programPanelLayout.createParallelGroup()
                        .addComponent(gameOverProgramLabel)
                        .addComponent(this.gameOverProgram)
                        .addComponent(gameOverProgramButton))
        );

        keysPanelLayout.setHorizontalGroup(keysPanelLayout.createParallelGroup()
                .addGroup(keysPanelLayout.createSequentialGroup()
                        .addComponent(runTimeKeyLabel)
                        .addComponent(this.runTimeKey, 50, 50, 50))
                .addGroup(keysPanelLayout.createSequentialGroup()
                        .addComponent(menuKeyLabel)
                        .addComponent(this.menuKey))
                .addGroup(keysPanelLayout.createSequentialGroup()
                        .addComponent(generalKeyLabel)
                        .addComponent(this.generalKey))
                .addComponent(moreKeysButton)
        );

        keysPanelLayout.linkSize(SwingConstants.VERTICAL, this.runTimeKey, 
                this.menuKey, this.generalKey);
        keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, this.runTimeKey, 
                this.menuKey, this.generalKey);
        keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, runTimeKeyLabel, 
                menuKeyLabel, generalKeyLabel);

        keysPanelLayout.setVerticalGroup(keysPanelLayout.createSequentialGroup()
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(runTimeKeyLabel)
                        .addComponent(this.runTimeKey, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT))
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(menuKeyLabel)
                        .addComponent(this.menuKey))
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(generalKeyLabel)
                        .addComponent(this.generalKey))
                .addComponent(moreKeysButton)
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(programPanel, 515, 515, 515)
                .addComponent(keysPanel)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, programPanel, keysPanel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(programPanel)
                .addComponent(keysPanel)
        );

    }

    private void createRewardsPanel()
    {
        this.experienceAwarded = new WholeNumberField(this.enemy.getExperienceAwarded());

        JLabel enableFightLabel = new JLabel("Enable Battle System?");
        JButton configureFight = new JButton("Configure");

        JPanel fightControlPanel = new JPanel();
        fightControlPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Configuration"));

        // Configure Layouts
        GroupLayout layout = new GroupLayout(this.rewardsPanel);
        this.rewardsPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout fightControlPanelLayout = new GroupLayout(fightControlPanel);
        fightControlPanel.setLayout(fightControlPanelLayout);
        fightControlPanelLayout.setAutoCreateGaps(true);
        fightControlPanelLayout.setAutoCreateContainerGaps(true);

        fightControlPanelLayout.setHorizontalGroup(fightControlPanelLayout.createParallelGroup()
                .addGroup(fightControlPanelLayout.createSequentialGroup()
                        .addComponent(this.experienceAwarded)
                        .addComponent(enableFightLabel))
                .addComponent(configureFight)
        );

        fightControlPanelLayout.setVerticalGroup(fightControlPanelLayout.createSequentialGroup()
                .addGroup(fightControlPanelLayout.createParallelGroup()
                        .addComponent(this.experienceAwarded)
                        .addComponent(enableFightLabel))
                .addComponent(configureFight)
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(fightControlPanel, 515, 515, 515)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(fightControlPanel)
        );
    }

    private void createGraphicsPanel()
    {
        // Configure Class scope components
        this.animList = new JList(this.enemy.getStandardGraphicsNames().toArray());
        this.animList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.animList.setLayoutOrientation(JList.VERTICAL);
        this.animList.setVisibleRowCount(-1);
        
        this.animLoc = new JTextField();
        
        // Configure function Scope Components
        JScrollPane animListScroller = new JScrollPane(this.animList);
        
        JLabel animLabel = new JLabel("Animation");
        final ImageIcon playIcon = new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/run.png"));
        final ImageIcon stopIcon = new ImageIcon(getClass().
                getResource("/rpgtoolkit/editor/resources/stop.png"));
        final JToggleButton play = new JToggleButton(playIcon);
        
        JLabel dummy = new JLabel();
        JButton animFindButton = new JButton("Browse");
        JButton animAddButton = new JButton("Add");
        JButton animRemoveButton = new JButton("Remove");
        
        // Configure listeners
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
        
        this.animList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting() == false) {
                    if(animList.getSelectedIndex() == -1) {
                        animDisplay.setIcon(null);
                    } else {
                        //switch animation info
                        if(play.isSelected()) { play.doClick(); } //press stop
                        String location = enemy.getStandardGraphics().get(
                                animList.getSelectedIndex());
                        animLoc.setText(location);
                        if(location.isEmpty() == false) {
                            selectedAnim = new Animation(new File(
                                    System.getProperty("project.path") + "/Misc/" + location));
                        } else {
                            selectedAnim = null;
                        }
                        //switch animation images
                        if(selectedAnim != null && selectedAnim.getFrameCount() > 0) {
                            animDisplay.setIcon(new ImageIcon(
                                    selectedAnim.getFrame(0).getFrameImage()));
                            animTimer = new Timer((int)(selectedAnim.getFrameDelay() * 1000), animate);
                        } else {
                            animDisplay.setIcon(null);
                            animTimer = null;
                        }
                    }
                }
            }
        });
        
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
}
