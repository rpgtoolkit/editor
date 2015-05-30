package net.rpgtoolkit.common.assets;

import net.rpgtoolkit.common.CorruptAssetException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.out;
import net.rpgtoolkit.common.utilities.JSON;
import org.json.JSONObject;
import org.json.JSONStringer;

public class SpecialMove extends BasicType implements JSON.Saveable
{
    // Constants
    private final String FILE_HEADER = "RPGTLKIT SPLMOVE";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 2;

    private String name;
    private long fightPower;
    private long mpCost;
    private String rpgcodeProgram;
    private long mpDrainedFromTarget;
    private byte canUseInBattle; // Should be a bool
    private byte canUseInMenu;   // Should be a bool :'(
    private String associatedStatusEffect;
    private String associatedAnimation;
    private String description;

    public SpecialMove()
    {

    }

    public SpecialMove(File file)
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

    public long getFightPower()
    {
        return fightPower;
    }

    public void setFightPower(long fightPower)
    {
        this.fightPower = fightPower;
    }

    public long getMpCost()
    {
        return mpCost;
    }

    public void setMpCost(long mpCost)
    {
        this.mpCost = mpCost;
    }

    public String getRpgcodeProgram()
    {
        return rpgcodeProgram;
    }

    public void setRpgcodeProgram(String rpgcodeProgram)
    {
        this.rpgcodeProgram = rpgcodeProgram;
    }

    public long getMpDrainedFromTarget()
    {
        return mpDrainedFromTarget;
    }

    public void setMpDrainedFromTarget(long mpDrainedFromTarget)
    {
        this.mpDrainedFromTarget = mpDrainedFromTarget;
    }

    public boolean getCanUseInBattle()
    {
        return canUseInBattle == 1;
    }

    public void setCanUseInBattle(boolean canUseInBattle)
    {
        if(canUseInBattle == true) { this.canUseInBattle = 1; }
        else { this.canUseInBattle = 0; }
    }

    public boolean getCanUseInMenu()
    {
        return canUseInMenu == 1;
    }

    public void setCanUseInMenu(boolean canUseInMenu)
    {
        if(canUseInMenu == true) { this.canUseInMenu = 1; }
        else { this.canUseInMenu = 0; }
    }

    public String getAssociatedStatusEffect()
    {
        return associatedStatusEffect;
    }

    public void setAssociatedStatusEffect(String associatedStatusEffect)
    {
        this.associatedStatusEffect = associatedStatusEffect;
    }

    public String getAssociatedAnimation()
    {
        return associatedAnimation;
    }

    public void setAssociatedAnimation(String associatedAnimation)
    {
        this.associatedAnimation = associatedAnimation;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean open()
    {
        try
        {
            JSONObject json = JSON.load(this.file);
            if(json == null) { return this.openBinary(); } //falback to binary
            this.harvestJSON(json);
            this.inputStream.close(); //not using binary
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    @Override
    public boolean save()
    {
        //convert to new format without overwriting old file
        if(this.file.getName().endsWith(".spc")) {
            this.file = new File(this.file.getPath() + "4");
        }
        return JSON.save(this, this.file);
    }

    public boolean saveAs(File fileName)
    {
        this.file = fileName;
        return this.save();
    }
    
    public boolean openBinary()
    {
        out.println("Attempting to open file as binary.");
        try
        {
            if (binaryIO.readBinaryString().equals(FILE_HEADER)) // Valid Status File
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();

                if (majorVersion == MAJOR_VERSION)
                {
                    name = binaryIO.readBinaryString();
                    fightPower = binaryIO.readBinaryLong();
                    mpCost = binaryIO.readBinaryLong();
                    rpgcodeProgram = binaryIO.readBinaryString();
                    mpDrainedFromTarget = binaryIO.readBinaryLong();
                    canUseInBattle = binaryIO.readBinaryByte();
                    canUseInMenu = binaryIO.readBinaryByte();
                    associatedStatusEffect = binaryIO.readBinaryString();
                    associatedAnimation = binaryIO.readBinaryString();
                    description = binaryIO.readBinaryString();
                }
            }

            inputStream.close();

            return true;
        }
        catch (CorruptAssetException e)
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

    public boolean saveBinary()
    {
        try
        {
            outputStream = new FileOutputStream(this.file);
            binaryIO.setOutputStream(outputStream);

            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            binaryIO.writeBinaryString(name);
            binaryIO.writeBinaryLong(fightPower);
            binaryIO.writeBinaryLong(mpCost);
            binaryIO.writeBinaryString(rpgcodeProgram);
            binaryIO.writeBinaryLong(mpDrainedFromTarget);
            binaryIO.writeBinaryByte(canUseInBattle);
            binaryIO.writeBinaryByte(canUseInMenu);
            binaryIO.writeBinaryString(associatedStatusEffect);
            binaryIO.writeBinaryString(associatedAnimation);
            binaryIO.writeBinaryString(description);

            outputStream.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void populateJSON(JSONStringer json) {
        json.key("name").value(this.name);
        json.key("description").value(this.description);
        json.key("mpCost").value(this.mpCost);
        json.key("fightPower").value(this.fightPower);
        json.key("rpgcodeProgram").value(this.rpgcodeProgram);
        json.key("mpDrainedFromTarget").value(this.mpDrainedFromTarget);
        json.key("associatedStatusEffect").value(this.associatedStatusEffect);
        json.key("associatedAnimation").value(this.associatedAnimation);
        json.key("canUseInBattle").value(this.getCanUseInBattle());
        json.key("canUseInMenu").value(this.getCanUseInMenu());
}

    @Override
    public String toJSONString() {
        JSONStringer json = new JSONStringer();
        json.object();
        this.populateJSON(json);
        json.endObject();
        return JSON.toPrettyJSON(json);
    }
    
    public void harvestJSON(JSONObject json) {
        this.name = json.optString("name");
        this.description = json.optString("description");
        this.mpCost = json.optLong("mpCost");
        this.fightPower = json.optLong("fightPower");
        this.rpgcodeProgram = json.optString("rpgcodeProgram");
        this.mpDrainedFromTarget = json.optLong("mpDrainedFromTarget");
        this.associatedStatusEffect = json.optString("associatedStatusEffect");
        this.associatedAnimation = json.optString("associatedAnimation");
        this.setCanUseInBattle(json.optBoolean("canUseInBattle"));
        this.setCanUseInMenu(json.optBoolean("canUseInMenu"));
    }
}
