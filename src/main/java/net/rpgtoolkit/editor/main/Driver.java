package net.rpgtoolkit.editor.main;

import javax.swing.*;

public class Driver
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println(System.getProperty("os.name"));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Don't make the mainWindow visible by default...
            MainWindow.getInstance().setVisible(true);
        }
        catch (Exception e)
        {
            
        }
    }
}
