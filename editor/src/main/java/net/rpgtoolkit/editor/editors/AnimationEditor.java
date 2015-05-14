package net.rpgtoolkit.editor.editors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import net.rpgtoolkit.common.Tile;
import net.rpgtoolkit.common.Animation;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;
import net.rpgtoolkit.editor.ui.Gui;

/**
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class AnimationEditor extends ToolkitEditorWindow
{

    private final Animation animation;
    private JPanel timeLinePanel;
    private JPanel framePanel;
    private JPanel controlPanel;

    private final Border defaultEtchedBorder = BorderFactory.
            createEtchedBorder(EtchedBorder.LOWERED);

    // CONTROL PANEL
    private JTextField frameWidth;
    private JTextField frameHeight;
    private JTextField frameTime;
    private JTextField frameFile;
    private JTextField frameSound;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public AnimationEditor(Animation theAnimation)
    {
        super("Editing Animation", true, true, true, true);
        this.animation = theAnimation;

        this.configureInterface();
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public boolean save()
    {
        return this.animation.save();
    }

    public void gracefulClose()
    {

    }

    public void setWindowParent(MainWindow parent)
    {

    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void configureInterface()
    {
        this.setSize(800, 600);
        this.setVisible(true);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        this.framePanel = new JPanel();
        //this.framePanel.setBackground(new Color(255,0,255));

        this.timeLinePanel = new JPanel();
        this.timeLinePanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Timeline"));
        this.configureTimeLine();

        this.controlPanel = new JPanel();
        this.configureControlPanel();

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(this.framePanel)
                        .addComponent(this.controlPanel, 150, 150, 150))
                .addComponent(this.timeLinePanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(this.framePanel)
                        .addComponent(this.controlPanel))
                .addComponent(this.timeLinePanel, 75, 75, 75)
        );

    }

    private void configureControlPanel()
    {
        this.frameWidth = new JTextField(Long.toString(this.animation.getAnimationWidth()));
        this.frameHeight = new JTextField(Long.toString(this.animation.getAnimationHeight()));
        this.frameTime = new JTextField(Double.toString(this.animation.getFrameDelay()));
        this.frameFile = new JTextField(this.animation.getFrame(0).getFrameName());
        this.frameSound = new JTextField(this.animation.getFrame(0).getFrameSound());

        JLabel widthLabel = new JLabel("x");
        JLabel heightLabel = new JLabel("y");
        JLabel timeLabel = new JLabel("Frame Time");
        JLabel nameLabel = new JLabel("Frame File");
        JLabel soundLabel = new JLabel("Frame Sound");
        JButton nameBrowseButton = new JButton("...");
        JButton soundBrowseButton = new JButton("...");

        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder(this.defaultEtchedBorder, "Size"));
        JPanel delayPanel = new JPanel();
        delayPanel.setBorder(BorderFactory.createTitledBorder(this.defaultEtchedBorder, "Time"));
        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createTitledBorder(this.defaultEtchedBorder, "Color"));
        JPanel miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createTitledBorder(this.defaultEtchedBorder, "Details"));

        GroupLayout layout = Gui.createGroupLayout(this.controlPanel);

        GroupLayout sizeLayout = Gui.createGroupLayout(sizePanel);

        GroupLayout delayLayout = Gui.createGroupLayout(delayPanel);

        GroupLayout miscLayout = Gui.createGroupLayout(miscPanel);

        miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
                .addComponent(nameLabel)
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(this.frameFile)
                        .addComponent(nameBrowseButton))
                .addComponent(soundLabel)
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(this.frameSound)
                        .addComponent(soundBrowseButton))
        );

        miscLayout.linkSize(this.frameFile, this.frameSound);

        miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
                .addComponent(nameLabel)
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(this.frameFile, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(nameBrowseButton))
                .addComponent(soundLabel)
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(this.frameSound)
                        .addComponent(soundBrowseButton))
        );

        delayLayout.setHorizontalGroup(delayLayout.createParallelGroup()
                .addComponent(timeLabel)
                .addComponent(this.frameTime)
        );

        delayLayout.setVerticalGroup(delayLayout.createSequentialGroup()
                .addComponent(timeLabel)
                .addComponent(this.frameTime, Gui.JTF_HEIGHT, 
                        Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
        );

        sizeLayout.setHorizontalGroup(sizeLayout.createParallelGroup()
                .addGroup(sizeLayout.createSequentialGroup()
                        .addComponent(widthLabel)
                        .addComponent(this.frameWidth))
                .addGroup(sizeLayout.createSequentialGroup()
                        .addComponent(heightLabel)
                        .addComponent(this.frameHeight))
        );

        sizeLayout.linkSize(SwingConstants.VERTICAL, this.frameWidth, 
                this.frameHeight);

        sizeLayout.setVerticalGroup(sizeLayout.createSequentialGroup()
                .addGroup(sizeLayout.createParallelGroup()
                        .addComponent(widthLabel)
                        .addComponent(this.frameWidth, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT))
                .addGroup(sizeLayout.createParallelGroup()
                        .addComponent(heightLabel)
                        .addComponent(this.frameHeight))
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(sizePanel)
                .addComponent(delayPanel)
                .addComponent(colorPanel)
                .addComponent(miscPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(sizePanel)
                .addComponent(delayPanel)
                .addComponent(colorPanel)
                .addComponent(miscPanel)
        );
    }

    private void configureTimeLine()
    {
        long frameCount = this.animation.getFrameCount();
        ArrayList<JLabel> frames = new ArrayList<>();

        for (int i = 0; i < frameCount; i++)
        {
            JLabel tempButton = new JLabel();

            if (!this.animation.getFrame(i).getFrameName().equals(""))
            {

                BufferedImage bi = new BufferedImage(
                        (int) this.animation.getAnimationWidth(),
                        (int) this.animation.getAnimationHeight(),
                        BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g = bi.createGraphics();

                Tile aTile = this.animation.getFrame(i).getFrameTile();
                // Draw the tile

                BufferedImage test = aTile.getTileAsImage();

                tempButton.setIcon(new ImageIcon(aTile.getTileAsImage()));
                tempButton.paint(g);

                if (i == 0)
                {
                    tempButton.setBorder(BorderFactory.createLineBorder(Color.RED));
                }
                else
                {
                    tempButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }

                this.timeLinePanel.add(tempButton);
            }
        }

        for (JLabel button : frames)
        {
            this.timeLinePanel.add(button);
        }
    }
}
