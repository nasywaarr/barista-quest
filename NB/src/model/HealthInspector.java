package model;

import battle.CardType;
import battle.RuleSet;
import battle.BannedCardRule;
import exceptions.BattleOverException;
import java.util.Random;

public class HealthInspector extends Enemy {
    private final RuleSet ruleSet;
    private boolean hasInspected;
    private static final Random RANDOM = new Random();

    public HealthInspector(RuleSet ruleSet) { this(ruleSet, 1); }

    public HealthInspector(RuleSet ruleSet, int difficulty) {
        super("Health Inspector",
            60  + (difficulty - 1) * 15,
            5,
            5   + (difficulty - 1) * 2,
            15);
        this.ruleSet = ruleSet;
    }

    @Override
    protected void decideAction(Character target) throws BattleOverException {
        if (!hasInspected) {
            System.out.println("  Health Inspector bans ATTACK cards for 2 turns!");
            ruleSet.addRule(new BannedCardRule(ruleSet, CardType.ATTACK, 2));
            hasInspected = true;
        } else {
            switch (RANDOM.nextInt(2)) {
                case 0:
                    System.out.println("  Health Inspector cites a violation!");
                    System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                    break;
                case 1:
                    System.out.println("  Health Inspector writes a warning notice!");
                    System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                    break;
            }
        }
    }
}
