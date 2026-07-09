package model;

import exceptions.BattleOverException;

public abstract class Enemy extends Character {
    private final int attackPower;
    private final int reward;
    private int silencedTurns = 0;

    public Enemy(String name, int health, int stamina, int attackPower, int reward) {
        super(name, health, stamina);
        this.attackPower = attackPower;
        this.reward = reward;
    }

    @Override
    public final void act(Character target) throws BattleOverException {
        if (silencedTurns > 0) {
            System.out.println("  " + getName() + " is silenced and cannot act!");
            silencedTurns--;
            return;
        }
        decideAction(target);
    }

    public void silence(int turns) { this.silencedTurns = turns; }
    public boolean isSilenced()    { return silencedTurns > 0; }

    protected abstract void decideAction(Character target) throws BattleOverException;

    public int getAttackPower() { return attackPower; }
    public int getReward()      { return reward; }
}
