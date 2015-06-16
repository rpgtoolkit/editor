/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

import java.util.ArrayList;
import java.util.List;

public class Item implements Asset
{

    private final int USER_CHAR_COUNT = 51;
    private final int EQUIP_COUNT = 8;
    private final int ANIMATION_STANDARD_COUNT = 10;
    private final int ANIMATION_STANDING_COUNT = 8;
    private final int ITEM_REST = 8;

    private String name;
    private String description;
    private boolean isEquippable;
    private boolean isMenuDriven;
    private boolean isBoardDriven;
    private boolean isBattleDriven;
    private byte usersSpecified;
    private List<String> userChar;
    private long buyPrice;
    private long sellPrice;
    private byte isKeyItem;
    private List<Byte> equipLocation;
    private String accessory;
    private long equipHP;
    private long equipDP;
    private long equipFP;
    private long equipSMP;
    private String equipProgram;
    private String removeProgram;
    private long menuHP;
    private long menuSMP;
    private String menuProgram;
    private long fightHP;
    private long fightSMP;
    private String fightProgram;
    private String fightAnimation;
    private String boardMultitaskProgram;
    private String boardPickUpProgram;
    private boolean isWide;

    private List<String> standardAnimationFiles;
    private List<Animation> standardAnimations;
    private List<String> animationCustom;
    private List<String> animationCustomHandle;
    private List<String> animationStanding;

    private double speed;
    private double idleTime;

    private BoardVector vectorBase;
    private BoardVector vectorActivate;

    public List<Animation> getStandardAnimations()
    {
        return this.standardAnimations;
    }
    
    public Item()
    {
        this.userChar = new ArrayList<>();
        this.equipLocation = new ArrayList<>();
        this.standardAnimationFiles = new ArrayList<>();
        this.standardAnimations = new ArrayList<>();
        this.animationCustom = new ArrayList<>();
        this.animationCustomHandle = new ArrayList<>();
        this.animationStanding = new ArrayList<>();
        reset();
    }

    @Override
    public void reset()
    {

        name = "";
        description = "";
        speed = 0.05;
        idleTime = 3;
        isEquippable = false;
        isMenuDriven = false;
        isBoardDriven = false;
        isBattleDriven = false;
        usersSpecified = 0;

        userChar.clear();
        for (int i = 0; i != USER_CHAR_COUNT; i++)
        {
            userChar.add("");
        }

        buyPrice = 0;
        sellPrice = 0;
        isKeyItem = 0;
        equipLocation.clear();
        for (int i = 0; i != EQUIP_COUNT; i++)
        {
            Byte tempByte = 0;
            equipLocation.add(tempByte);
        }
        accessory = "";
        equipHP = 0;
        equipDP = 0;
        equipFP = 0;
        equipSMP = 0;
        equipProgram = "";
        removeProgram = "";
        menuHP = 0;
        menuSMP = 0;
        menuProgram = "";
        fightHP = 0;
        fightSMP = 0;
        fightProgram = "";
        fightAnimation = "";
        boardMultitaskProgram = "";
        boardPickUpProgram = "";
        isWide = true;
        standardAnimationFiles.clear();
        for (int i = 0; i != ANIMATION_STANDARD_COUNT; i++)
        {
            standardAnimationFiles.add("");
        }
        animationStanding.clear();
        for (int i = 0; i != ANIMATION_STANDING_COUNT; i++)
        {
            animationStanding.add("");
        }
        animationCustom.clear();
        animationCustomHandle.clear();
        for (int i = 0; i != 5; i++)
        {
            animationCustom.add("");
            animationCustomHandle.add("");
        }
        vectorBase = makeDefaultSpriteVector(true, false);
        vectorActivate = makeDefaultSpriteVector(false, false);
    }

    private BoardVector makeDefaultSpriteVector(boolean isCollisionVector, boolean isIsometric)
    {
        BoardVector toReturn = new BoardVector();
        if (isCollisionVector)
        {
            if (isIsometric)
            {
                toReturn.addPoint(-15, 0);
                toReturn.addPoint(0, 7);
                toReturn.addPoint(15, 0);
                toReturn.addPoint(0, -7);
            } else
            {
                //toReturn.setTileType(TT_SOLID);   //WARNING: needs to work when tiletypes exist
                toReturn.addPoint(-15, -15);
                toReturn.addPoint(15, -15);
                toReturn.addPoint(15, 0);
                toReturn.addPoint(-15, 0);
            }
        } else
        {
            if (isIsometric)
            {
                toReturn.addPoint(-31, 0);
                toReturn.addPoint(0, 15);
                toReturn.addPoint(31, 0);
                toReturn.addPoint(0, -15);
            } else
            {
                //toReturn.setTileType(TT_SOLID);   //WARNING: needs to work when tiletypes exist
                toReturn.addPoint(-24, -24);
                toReturn.addPoint(24, -24);
                toReturn.addPoint(24, 8);
                toReturn.addPoint(-24, 8);
            }
        }
        return (toReturn);
    }

    public List<String> getStandardAnimationFiles()
    {
        return standardAnimationFiles;
    }

    @Override
    public AssetDescriptor getDescriptor()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
