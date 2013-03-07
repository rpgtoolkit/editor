package rpgtoolkit.editor.animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.Animation;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.editor.main.ToolkitEditorWindow;

/**
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class AnimationEditor extends JInternalFrame implements ToolkitEditorWindow
{
    private final int JTF_HEIGHT = 24;

    private Animation animation;
    private JPanel timeLinePanel;
    private JPanel framePanel;
    private JPanel controlPanel;

    private final Border defaultEtchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // CONTROL PANEL
    private JTextField frameWidth;
    private JTextField frameHeight;
    private JTextField frameTime;
    private JTextField frameFile;
    private JTextField frameSound;

    public AnimationEditor()
    {

    }

    public AnimationEditor(Animation theAnimation)
    {
        super("Editing Animation", true, true, true, true);
        this.animation = theAnimation;

        this.configureInterface();
    }

    private void configureInterface()
    {
        this.setSize(800, 600);
        this.setVisible(true);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        framePanel = new JPanel();
        //framePanel.setBackground(new Color(255,0,255));

        timeLinePanel = new JPanel();
        timeLinePanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Timeline"));
        configureTimeLine();

        controlPanel = new JPanel();
        configureControlPanel();

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(framePanel)
                        .addComponent(controlPanel, 150, 150, 150))
                .addComponent(timeLinePanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(framePanel)
                        .addComponent(controlPanel))
                .addComponent(timeLinePanel, 75, 75, 75)
        );

    }

    private void configureControlPanel()
    {
        frameWidth = new JTextField(Long.toString(animation.getAnimationWidth()));
        frameHeight = new JTextField(Long.toString(animation.getAnimationHeight()));
        frameTime = new JTextField(Double.toString(animation.getFrameDelay()));
        frameFile = new JTextField(animation.getFrame(0).getFrameName());
        frameSound = new JTextField(animation.getFrame(0).getFrameSound());

        JLabel widthLabel = new JLabel("x");
        JLabel heightLabel = new JLabel("y");
        JLabel timeLabel = new JLabel("Frame Time");
        JLabel nameLabel = new JLabel("Frame File");
        JLabel soundLabel = new JLabel("Frame Sound");
        JButton nameBrowseButton = new JButton("...");
        JButton soundBrowseButton = new JButton("...");

        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Size"));
        JPanel delayPanel = new JPanel();
        delayPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Time"));
        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Color"));
        JPanel miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Details"));

        GroupLayout layout = new GroupLayout(controlPanel);
        controlPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout sizeLayout = new GroupLayout(sizePanel);
        sizePanel.setLayout(sizeLayout);
        sizeLayout.setAutoCreateGaps(true);
        sizeLayout.setAutoCreateContainerGaps(true);

        GroupLayout delayLayout = new GroupLayout(delayPanel);
        delayPanel.setLayout(delayLayout);
        delayLayout.setAutoCreateGaps(true);
        delayLayout.setAutoCreateContainerGaps(true);

        GroupLayout miscLayout = new GroupLayout(miscPanel);
        miscPanel.setLayout(miscLayout);
        miscLayout.setAutoCreateGaps(true);
        miscLayout.setAutoCreateContainerGaps(true);

        miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
                .addComponent(nameLabel)
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(frameFile)
                        .addComponent(nameBrowseButton))
                .addComponent(soundLabel)
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(frameSound)
                        .addComponent(soundBrowseButton))
        );

        miscLayout.linkSize(frameFile, frameSound);

        miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
                .addComponent(nameLabel)
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(frameFile, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
                        .addComponent(nameBrowseButton))
                .addComponent(soundLabel)
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(frameSound)
                        .addComponent(soundBrowseButton))
        );


        delayLayout.setHorizontalGroup(delayLayout.createParallelGroup()
                .addComponent(timeLabel)
                .addComponent(frameTime)
        );

        delayLayout.setVerticalGroup(delayLayout.createSequentialGroup()
                .addComponent(timeLabel)
                .addComponent(frameTime, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
        );

        sizeLayout.setHorizontalGroup(sizeLayout.createParallelGroup()
                .addGroup(sizeLayout.createSequentialGroup()
                        .addComponent(widthLabel)
                        .addComponent(frameWidth))
                .addGroup(sizeLayout.createSequentialGroup()
                        .addComponent(heightLabel)
                        .addComponent(frameHeight))
        );

        sizeLayout.linkSize(SwingConstants.VERTICAL, frameWidth, frameHeight);

        sizeLayout.setVerticalGroup(sizeLayout.createSequentialGroup()
                .addGroup(sizeLayout.createParallelGroup()
                        .addComponent(widthLabel)
                        .addComponent(frameWidth, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT))
                .addGroup(sizeLayout.createParallelGroup()
                        .addComponent(heightLabel)
                        .addComponent(frameHeight))
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
        long frameCount = animation.getFrameCount();
        ArrayList<JLabel> frames = new ArrayList<JLabel>();

        for (int i = 0; i < frameCount; i++)
        {
            JLabel tempButton = new JLabel();

            if (!animation.getFrame(i).getFrameName().equals(""))
            {

                BufferedImage bi = new BufferedImage((int) animation.getAnimationWidth(), (int) animation.getAnimationHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g = bi.createGraphics();

                Tile aTile = animation.getFrame(i).getFrameTile();
                // Draw the tile
                   
                BufferedImage test = aTile.getTileAsImage();
                
                tempButton.setIcon(new ImageIcon(aTile.getTileAsImage()));
                tempButton.paint(g);
                if (i == 0) tempButton.setBorder(BorderFactory.createLineBorder(Color.RED));
                else tempButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                timeLinePanel.add(tempButton);
            }

        }

        for (JLabel button : frames)
        {
            timeLinePanel.add(button);
        }
    }

    public boolean save()
    {
        return this.animation.save();
    }

    public void gracefulClose()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setWindowParent(MainWindow parent)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
