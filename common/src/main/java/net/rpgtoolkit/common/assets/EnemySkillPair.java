package net.rpgtoolkit.common.assets;

/**
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class EnemySkillPair
{
    private String enemy; // Could in the future be changed to the actual enemey file
    private int skill;

    public EnemySkillPair(String enemy, int skill)
    {
        this.enemy = enemy;
        this.skill = skill;
    }

    public String getEnemy()
    {
        return this.enemy;
    }

    public int getSkill()
    {
        return this.skill;
    }

    public void setEnemy(String enemy)
    {
        this.enemy = enemy;
    }

    public void setSkill(int skill)
    {
        this.skill = skill;
    }
}
