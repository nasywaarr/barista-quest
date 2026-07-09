package model;

import exceptions.BattleOverException;
import java.util.Random;

public class RivalBarista extends Enemy {
    private static final Random RANDOM = new Random();

    public RivalBarista(String name) { this(name, 1); }

    public RivalBarista(String name, int difficulty) {
        super(name,
            80  + (difficulty - 1) * 15,
            6,
            12  + (difficulty - 1) * 2,
            20);
    }

    @Override
    protected void decideAction(Character target) throws BattleOverException {
        switch (RANDOM.nextInt(3)) {
            case 0:
                System.out.println("  " + getName() + " brews a counter-attack!");
                System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                break;
            case 1:
                System.out.println("  " + getName() + " challenges your technique!");
                System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                break;
            case 2:
                System.out.println("  " + getName() + " pours aggressively!");
                System.out.println("  -> " + target.getName() + " takes " + (getAttackPower() + 2) + " damage!");
                target.takeDamage(getAttackPower() + 2);
                break;
        }
    }
}
