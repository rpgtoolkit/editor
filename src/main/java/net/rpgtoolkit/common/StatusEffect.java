package net.rpgtoolkit.common;

import net.rpgtoolkit.editor.CorruptFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StatusEffect extends BasicType
{
    // Constants
    private final String FILE_HEADER = "RPGTLKIT STATUSE";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 2;

    private String name;
    private int roundsActive;
    private int speedUpCharge;
    private int slowDownCharge;
    private int disableTarget;
    private int removeHP;
    private int hpToRemove;
    private int removeMP;
    private int mpToRemove;
    private int runRPGCode;
    private String rpgcodeToRun;

    public StatusEffect()
    {

    }

    public StatusEffect(File file)
    {
        super(file);
        this.open();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRoundsActive()
    {
        return roundsActive;
    }

    public void setRoundsActive(int roundsActive)
    {
        this.roundsActive = roundsActive;
    }

    public int getSpeedUpCharge()
    {
        return speedUpCharge;
    }

    public void setSpeedUpCharge(int speedUpCharge)
    {
        this.speedUpCharge = speedUpCharge;
    }

    public int getSlowDownCharge()
    {
        return slowDownCharge;
    }

    public void setSlowDownCharge(int slowDownCharge)
    {
        this.slowDownCharge = slowDownCharge;
    }

    public int getDisableTarget()
    {
        return disableTarget;
    }

    public void setDisableTarget(int disableTarget)
    {
        this.disableTarget = disableTarget;
    }

    public int getRemoveHP()
    {
        return removeHP;
    }

    public void setRemoveHP(int removeHP)
    {
        this.removeHP = removeHP;
    }

    public int getHpToRemove()
    {
        return hpToRemove;
    }

    public void setHpToRemove(int hpToRemove)
    {
        this.hpToRemove = hpToRemove;
    }

    public int getRemoveMP()
    {
        return removeMP;
    }

    public void setRemoveMP(int removeMP)
    {
        this.removeMP = removeMP;
    }

    public int getMpToRemove()
    {
        return mpToRemove;
    }

    public void setMpToRemove(int mpToRemove)
    {
        this.mpToRemove = mpToRemove;
    }

    public int getRunRPGCode()
    {
        return runRPGCode;
    }

    public void setRunRPGCode(int runRPGCode)
    {
        this.runRPGCode = runRPGCode;
    }

    public String getRpgcodeToRun()
    {
        return rpgcodeToRun;
    }

    public void setRpgcodeToRun(String rpgcodeToRun)
    {
        this.rpgcodeToRun = rpgcodeToRun;
    }

    public boolean open()
    {
        try
        {
            if (binaryIO.readBinaryString().equals(FILE_HEADER)) // Valid Status File
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();

                if (majorVersion == MAJOR_VERSION)
                {
                    name = binaryIO.readBinaryString();
                    roundsActive = binaryIO.readBinaryInteger();
                    speedUpCharge = binaryIO.readBinaryInteger();
                    slowDownCharge = binaryIO.readBinaryInteger();
                    disableTarget = binaryIO.readBinaryInteger();
                    removeHP = binaryIO.readBinaryInteger();
                    hpToRemove = binaryIO.readBinaryInteger();
                    removeMP = binaryIO.readBinaryInteger();
                    mpToRemove = binaryIO.readBinaryInteger();
                    runRPGCode = binaryIO.readBinaryInteger();
                    rpgcodeToRun = binaryIO.readBinaryString();
                }
            }

            inputStream.close();

            return true;
        }
        catch (CorruptFileException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    public boolean save()
    {
        try
        {
            outputStream = new FileOutputStream(this.file);
            binaryIO.setOutputStream(outputStream);

            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            binaryIO.writeBinaryString(name);
            binaryIO.writeBinaryInteger(roundsActive);
            binaryIO.writeBinaryInteger(speedUpCharge);
            binaryIO.writeBinaryInteger(slowDownCharge);
            binaryIO.writeBinaryInteger(disableTarget);
            binaryIO.writeBinaryInteger(removeHP);
            binaryIO.writeBinaryInteger(hpToRemove);
            binaryIO.writeBinaryInteger(removeMP);
            binaryIO.writeBinaryInteger(mpToRemove);
            binaryIO.writeBinaryInteger(runRPGCode);
            binaryIO.writeBinaryString(rpgcodeToRun);

            outputStream.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    public boolean saveAs(File fileName)
    {
        this.file = fileName;
        return this.save();
    }
}

