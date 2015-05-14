package net.rpgtoolkit.editor.enemy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.rpgtoolkit.common.io.types.Animation;
import net.rpgtoolkit.common.io.types.Enemy;
import net.rpgtoolkit.common.io.types.SpecialMove;
import net.rpgtoolkit.editor.main.MainWindow;
import net.rpgtoolkit.editor.main.ToolkitEditorWindow;
import net.rpgtoolkit.editor.utilities.Gui;
import net.rpgtoolkit.editor.utilities.IntegerField;
import net.rpgtoolkit.editor.utilities.WholeNumberField;

/**
 * Enemy editor
 *
 * @author Joel Moore
 */
public class EnemyEditor extends ToolkitEditorWindow implements InternalFrameListener
{

    private final Enemy enemy; // Enemy file we are altering
    
    private static final String sep = File.separator;
    private final MainWindow mainWindow = MainWindow.getInstance();

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
    private Timer animTimer;

    // SPECIAL MOVES SETTINGS
    private JList sMoveList;
    private JList strengthList;
    private JList weaknessList;

    // TACTICS SETTINGS
    private JSlider aiLevel;
    private JCheckBox useRPGCodeTactics;
    private JTextField tacticsProgram;

    // REWARDS SETTINGS
    private IntegerField experienceAwarded;
    private IntegerField goldAwarded;
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
    }

    private void createBasicSettingsPanel()
    {
        // Configure Class scope components
        this.enemyName = new JTextField(this.enemy.getName());
        this.maxHitPoints = new WholeNumberField(this.enemy.getMaxHitPoints());
        this.maxSpecialPoints = new WholeNumberField(this.enemy.getMaxMagicPoints());
        this.fightPower = new WholeNumberField(this.enemy.getFightPower());
        this.defencePower = new WholeNumberField(this.enemy.getDefencePower());
        this.canRunAway = new JCheckBox("Player can run from this enemy");
        this.canRunAway.setSelected(this.enemy.canRunAway());
        this.runAwayProgram = new JTextField(this.enemy.getRunAwayProgram());
        this.runAwayProgram.setEnabled(this.canRunAway.isSelected());
        this.critOnEnemy = new WholeNumberField(this.enemy.getSneakChance());
        this.critOnPlayer = new WholeNumberField(this.enemy.getSurpriseChance());

        // Configure function Scope Components
        JLabel enemyNameLabel = new JLabel("Name");
        JLabel maxHitPointsLabel = new JLabel("Max Health Points");
        JLabel maxSpecialPointsLabel = new JLabel("Special Move Power");
        JLabel fightPowerLabel = new JLabel("Fighting Power");
        JLabel defencePowerLabel = new JLabel("Defence Power");
        final JLabel runAwayProgramLabel = new JLabel("Program to run when player runs away");
        final JButton runAwayProgramButton = new JButton("Browse");
        runAwayProgramLabel.setEnabled(this.canRunAway.isSelected());
        runAwayProgramButton.setEnabled(this.canRunAway.isSelected());
        JLabel critOnEnemyLabel = new JLabel("Chances of a critical hit on the enemy: (1 in)");
        JLabel critOnPlayerLabel = new JLabel("Chances of a critical hit on the player: (1 in)");
        
        // Configure listeners
        
        //can run away checkbox disable run away program
        this.canRunAway.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAwayProgramLabel.setEnabled(canRunAway.isSelected());
                runAwayProgram.setEnabled(canRunAway.isSelected());
                runAwayProgramButton.setEnabled(canRunAway.isSelected());
            }
        });
        
        //browse run away button
        runAwayProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loc = mainWindow.browseByType("Program Files", "prg", "Prg");
                if(loc != null) {
                    runAwayProgram.setText(loc);
                }
            }
        });

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
                .addComponent(this.canRunAway)
                .addGroup(fightingConditionsLayout.createSequentialGroup()
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
        fightingConditionsLayout.linkSize(SwingConstants.VERTICAL, 
                this.runAwayProgram,
                this.critOnEnemy,
                this.critOnPlayer);

        fightingConditionsLayout.setVerticalGroup(fightingConditionsLayout.createSequentialGroup()
                .addComponent(this.canRunAway)
                .addGroup(fightingConditionsLayout.createParallelGroup()
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

    private void createGraphicsPanel()
    {
        // Configure Class scope components
        final DefaultListModel enemyGraphics = new DefaultListModel();
        final ArrayList<String> standardNames = this.enemy.getStandardGraphicsNames();
        for(String standardName : standardNames) {
            enemyGraphics.addElement(standardName);
        }
        final ArrayList<String> customNames = this.enemy.getCustomizedGraphicsNames();
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
                            location = enemy.getStandardGraphics().get(
                                    animList.getSelectedIndex());
                            //out.println("new selection: standard " + animList.getSelectedIndex());
                        } else {
                            location = enemy.getCustomizedGraphics().get(
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
                        enemy.getCustomizedGraphics().set(
                                index - standardNames.size(), text);
                    } else {
                        enemy.getStandardGraphics().set(index, text);
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
                String loc = mainWindow.browseByType("Animation Files", "anm", "Misc");
                if(loc != null) {
                    if(play.isSelected()) { play.doClick(); } //press stop before we change it
                    animLoc.setText(loc);
                    if(index < standardNames.size()) {
                        enemy.getStandardGraphics().set(index, loc);
                    } else if(index < standardNames.size() + customNames.size()) {
                        int customIndex = index - standardNames.size();
                        enemy.getCustomizedGraphics().set(customIndex, loc);
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
                enemy.getCustomizedGraphics().add(customIndex, "");
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
                            enemy.getStandardGraphics().set(index, "");
                            //clear animation will be handled by animLoc
                        }
                    } else if(index < standardNames.size() + customNames.size()) {
                        //delete custom graphic
                        int customIndex = index - standardNames.size();
                        customNames.remove(customIndex);
                        enemy.getCustomizedGraphics().remove(customIndex);
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
        final ArrayList<String> sMoveLocs = this.enemy.getSpecialMoves();
        for(String loc : sMoveLocs) {
            String text = getSpecialMoveText(loc);
            specialMoves.addElement(text);
        }
        final DefaultListModel strengths = new DefaultListModel();
        final ArrayList<String> strengthLocs = this.enemy.getStrengths();
        for(String loc : strengthLocs) {
            String text = getSpecialMoveText(loc);
            strengths.addElement(text);
        }
        final DefaultListModel weaknesses = new DefaultListModel();
        final ArrayList<String> weaknessLocs = this.enemy.getWeaknesses();
        for(String loc : weaknessLocs) {
            String text = getSpecialMoveText(loc);
            weaknesses.addElement(text);
        }
//        out.println("specialMoves="+sMoveLocs.toString());
//        out.println("strengths="+strengthLocs.toString());
//        out.println("weaknesses="+weaknessLocs.toString());
        this.sMoveList = Gui.createVerticalJList(specialMoves);
        this.strengthList = Gui.createVerticalJList(strengths);
        this.weaknessList = Gui.createVerticalJList(weaknesses);
        
        // Configure function Scope Components
        JScrollPane sMoveListScroller = new JScrollPane(this.sMoveList);
        JScrollPane strengthListScroller = new JScrollPane(this.strengthList);
        JScrollPane weaknessListScroller = new JScrollPane(this.weaknessList);
        
        JButton sMoveFindButton = new JButton("Browse");
        JButton sMoveAddButton = new JButton("Add");
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
        this.strengthList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting() == false) {
                    if(strengthList.getSelectedIndex() == -1) {
                        strengthRemoveButton.setEnabled(false);
                    } else {
                        strengthRemoveButton.setEnabled(true);
                    }
                }
            }
        });
        this.weaknessList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting() == false) {
                    if(weaknessList.getSelectedIndex() == -1) {
                        weaknessRemoveButton.setEnabled(false);
                    } else {
                        weaknessRemoveButton.setEnabled(true);
                    }
                }
            }
        });
        
        //browse buttons
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
        strengthFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = strengthList.getSelectedIndex();
                if(index >= 0) {
                    String loc = browseSpecialMove();
                    if(loc != null) {
                        strengths.set(index, getSpecialMoveText(loc));
                    }
                }
            }
        });
        weaknessFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = weaknessList.getSelectedIndex();
                if(index >= 0) {
                    String loc = browseSpecialMove();
                    if(loc != null) {
                        weaknesses.set(index, getSpecialMoveText(loc));
                    }
                }
            }
        });
        
        //add buttons
        sMoveAddButton.addActionListener(new ActionListener() {
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
        strengthAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //insert after current slot
                int index = strengthList.getSelectedIndex() + 1;
                String loc = browseSpecialMove();
                if(loc != null) {
                    strengths.add(index, getSpecialMoveText(loc));
                }
                //select the added move
                strengthList.setSelectedIndex(index);
                strengthList.ensureIndexIsVisible(index);
            }
        });
        weaknessAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //insert after current slot
                int index = weaknessList.getSelectedIndex() + 1;
                String loc = browseSpecialMove();
                if(loc != null) {
                    weaknesses.add(index, getSpecialMoveText(loc));
                }
                //select the added move
                weaknessList.setSelectedIndex(index);
                weaknessList.ensureIndexIsVisible(index);
            }
        });
        
        //remove buttons
        sMoveRemoveButton.addActionListener(
                Gui.simpleRemoveListener(specialMoves, sMoveList)
        );
        strengthRemoveButton.addActionListener(
                Gui.simpleRemoveListener(strengths, strengthList)
        );
        weaknessRemoveButton.addActionListener(
                Gui.simpleRemoveListener(weaknesses, weaknessList)
        );

        // Configure the necessary Panels
        JPanel sMovePanel = new JPanel();
        sMovePanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Special Move List"));
        JPanel strengthPanel = new JPanel();
        strengthPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Strength List"));
        JPanel weaknessPanel = new JPanel();
        weaknessPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Weakness List"));

        // Create Layout for Top Level Panel
        GroupLayout layout = Gui.createGroupLayout(this.specialMovesPanel);

        // Configure Layouts for Second Level Panels
        GroupLayout sMoveLayout = Gui.createGroupLayout(sMovePanel);
        GroupLayout strengthLayout = Gui.createGroupLayout(strengthPanel);
        GroupLayout weaknessLayout = Gui.createGroupLayout(weaknessPanel);

        // Configure the SMOVE PANEL layout
        sMoveLayout.setHorizontalGroup(sMoveLayout.createSequentialGroup()
                .addComponent(sMoveListScroller)
                .addGroup(sMoveLayout.createParallelGroup()
                        .addComponent(sMoveFindButton)
                        .addComponent(sMoveAddButton)
                        .addComponent(sMoveRemoveButton))
        );

        sMoveLayout.setVerticalGroup(sMoveLayout.createParallelGroup()
                .addComponent(sMoveListScroller)
                .addGroup(sMoveLayout.createSequentialGroup()
                        .addComponent(sMoveFindButton)
                        .addComponent(sMoveAddButton)
                        .addComponent(sMoveRemoveButton))
        );

        // Configure the STRENGTH PANEL layout
        strengthLayout.setHorizontalGroup(strengthLayout.createSequentialGroup()
                .addComponent(strengthListScroller)
                .addGroup(strengthLayout.createParallelGroup()
                        .addComponent(strengthFindButton)
                        .addComponent(strengthAddButton)
                        .addComponent(strengthRemoveButton))
        );

        strengthLayout.setVerticalGroup(strengthLayout.createParallelGroup()
                .addComponent(strengthListScroller)
                .addGroup(strengthLayout.createSequentialGroup()
                        .addComponent(strengthFindButton)
                        .addComponent(strengthAddButton)
                        .addComponent(strengthRemoveButton))
        );

        // Configure the WEAKNESS PANEL layout
        weaknessLayout.setHorizontalGroup(weaknessLayout.createSequentialGroup()
                .addComponent(weaknessListScroller)
                .addGroup(weaknessLayout.createParallelGroup()
                        .addComponent(weaknessFindButton)
                        .addComponent(weaknessAddButton)
                        .addComponent(weaknessRemoveButton))
        );

        weaknessLayout.setVerticalGroup(weaknessLayout.createParallelGroup()
                .addComponent(weaknessListScroller)
                .addGroup(weaknessLayout.createSequentialGroup()
                        .addComponent(weaknessFindButton)
                        .addComponent(weaknessAddButton)
                        .addComponent(weaknessRemoveButton))
        );

        // Configure the SPECIAL MOVE PANEL layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(sMovePanel)
                .addComponent(strengthPanel)
                .addComponent(weaknessPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(sMovePanel)
                .addComponent(strengthPanel)
                .addComponent(weaknessPanel)
        );
    }

    private void createTacticsPanel()
    {
        this.aiLevel = new JSlider(0, 3, this.enemy.getAiLevel());
        this.aiLevel.setMajorTickSpacing(1);
        this.aiLevel.setPaintTicks(true);
        Hashtable<Integer, JLabel> aiLabels = new Hashtable<>();
        aiLabels.put(0, new JLabel("Low"));
        aiLabels.put(1, new JLabel("Medium"));
        aiLabels.put(2, new JLabel("High"));
        aiLabels.put(3, new JLabel("Very High"));
        this.aiLevel.setLabelTable(aiLabels);
        this.aiLevel.setPaintLabels(true);

        this.useRPGCodeTactics = new JCheckBox("Use RPGCode-guided tactics");
        this.useRPGCodeTactics.setSelected(this.enemy.useRPGCodeTatics());
        this.aiLevel.setEnabled(!(this.useRPGCodeTactics.isSelected()));
        
        final JLabel tacticsProgramLabel = new JLabel("Program to run");
        this.tacticsProgram = new JTextField(this.enemy.getTacticsFile());
        final JButton tacticsProgramFindButton = new JButton("Browse");
        tacticsProgramLabel.setEnabled(this.useRPGCodeTactics.isSelected());
        this.tacticsProgram.setEnabled(this.useRPGCodeTactics.isSelected());
        tacticsProgramFindButton.setEnabled(this.useRPGCodeTactics.isSelected());

        JPanel battleTacticsPanel = new JPanel();
        battleTacticsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Battle Tactics"));
        
        JPanel aiLevelPanel = new JPanel();
        aiLevelPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Artificial Intelligence Level (Internal Algorithm)"));
        
        // Configure listeners
        
        //tactics program checkbox disable built-in ai level
        this.useRPGCodeTactics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aiLevel.setEnabled(!(useRPGCodeTactics.isSelected()));
                tacticsProgramLabel.setEnabled(useRPGCodeTactics.isSelected());
                tacticsProgram.setEnabled(useRPGCodeTactics.isSelected());
                tacticsProgramFindButton.setEnabled(useRPGCodeTactics.isSelected());
            }
        });
        
        //browse for tactics program
        tacticsProgramFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loc = mainWindow.browseByType("Program Files", "prg", "Prg");
                if(loc != null) {
                    tacticsProgram.setText(loc);
                }
            }
        });

        // Configure Layouts
        GroupLayout layout = Gui.createGroupLayout(this.tacticsPanel);

        GroupLayout battleTacticsLayout = Gui.createGroupLayout(battleTacticsPanel);
        GroupLayout aiLevelLayout = Gui.createGroupLayout(aiLevelPanel);
        
        aiLevelLayout.setHorizontalGroup(aiLevelLayout.createSequentialGroup()
                .addComponent(this.aiLevel)
        );

        aiLevelLayout.setVerticalGroup(aiLevelLayout.createParallelGroup()
                .addComponent(this.aiLevel)
        );

        battleTacticsLayout.setHorizontalGroup(battleTacticsLayout.createParallelGroup()
                .addComponent(aiLevelPanel)
                .addComponent(this.useRPGCodeTactics)
                .addGroup(battleTacticsLayout.createSequentialGroup()
                        .addComponent(tacticsProgramLabel)
                        .addComponent(this.tacticsProgram)
                        .addComponent(tacticsProgramFindButton))
        );

        battleTacticsLayout.setVerticalGroup(battleTacticsLayout.createSequentialGroup()
                .addComponent(aiLevelPanel)
                .addComponent(this.useRPGCodeTactics)
                .addGroup(battleTacticsLayout.createParallelGroup()
                        .addComponent(tacticsProgramLabel)
                        .addComponent(this.tacticsProgram)
                        .addComponent(tacticsProgramFindButton))
        );
        
        battleTacticsLayout.linkSize(SwingConstants.VERTICAL,
                tacticsProgramLabel, this.tacticsProgram, tacticsProgramFindButton
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(battleTacticsPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(battleTacticsPanel)
        );
    }

    private void createRewardsPanel()
    {
        JLabel experienceAwardedLabel = new JLabel("Experience Gained");
        this.experienceAwarded = new IntegerField(this.enemy.getExperienceAwarded());

        JLabel goldAwardedLabel = new JLabel("GP Earned");
        this.goldAwarded = new IntegerField(this.enemy.getGoldAwarded());
        
        JLabel victoryProgramLabel = new JLabel("Program to run upon defeating enemy");
        this.victoryProgram = new JTextField(this.enemy.getBeatEnemyProgram());
        JButton victoryProgramFindButton = new JButton("Browse");

        JPanel rewardsSubPanel = new JPanel();
        rewardsSubPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Rewards for Defeating Enemy"));
        
        // Configure listeners
        victoryProgramFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loc = mainWindow.browseByType("Program Files", "prg", "Prg");
                if(loc != null) {
                    victoryProgram.setText(loc);
                }
            }
        });

        // Configure Layouts
        GroupLayout layout = Gui.createGroupLayout(this.rewardsPanel);

        GroupLayout rewardsPanelLayout = Gui.createGroupLayout(rewardsSubPanel);

        rewardsPanelLayout.setHorizontalGroup(rewardsPanelLayout.createParallelGroup()
                .addGroup(rewardsPanelLayout.createSequentialGroup()
                        .addComponent(experienceAwardedLabel)
                        .addComponent(this.experienceAwarded))
                .addGroup(rewardsPanelLayout.createSequentialGroup()
                        .addComponent(goldAwardedLabel)
                        .addComponent(this.goldAwarded))
                .addComponent(victoryProgramLabel)
                .addGroup(rewardsPanelLayout.createSequentialGroup()
                        .addComponent(this.victoryProgram)
                        .addComponent(victoryProgramFindButton))
        );

        rewardsPanelLayout.setVerticalGroup(rewardsPanelLayout.createSequentialGroup()
                .addGroup(rewardsPanelLayout.createParallelGroup()
                        .addComponent(experienceAwardedLabel)
                        .addComponent(this.experienceAwarded))
                .addGroup(rewardsPanelLayout.createParallelGroup()
                        .addComponent(goldAwardedLabel)
                        .addComponent(this.goldAwarded))
                .addComponent(victoryProgramLabel)
                .addGroup(rewardsPanelLayout.createParallelGroup()
                        .addComponent(this.victoryProgram)
                        .addComponent(victoryProgramFindButton))
        );
        
        rewardsPanelLayout.linkSize(SwingConstants.HORIZONTAL,
                experienceAwardedLabel,
                goldAwardedLabel
        );
        rewardsPanelLayout.linkSize(SwingConstants.VERTICAL,
                experienceAwardedLabel, this.experienceAwarded,
                goldAwardedLabel, this.goldAwarded, victoryProgramLabel,
                this.victoryProgram, victoryProgramFindButton
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(rewardsSubPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(rewardsSubPanel)
        );
    }


    private String browseSpecialMove() {
        return mainWindow.browseByType("Special Move Files", "spc", "SpcMove");
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