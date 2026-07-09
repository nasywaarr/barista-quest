package model;

import battle.CardType;
import battle.RuleSet;
import battle.BannedCardRule;
import exceptions.BattleOverException;
import java.util.Random;

public class CoffeeCritic extends Enemy {
    private final RuleSet ruleSet;
    private boolean hasReviewed;
    private static final Random RANDOM = new Random();

    public CoffeeCritic(RuleSet ruleSet) { this(ruleSet, 1); }

    public CoffeeCritic(RuleSet ruleSet, int difficulty) {
        super("Coffee Critic",
            65  + (difficulty - 1) * 15,
            5,
            6   + (difficulty - 1) * 2,
            18);
        this.ruleSet = ruleSet;
    }

    @Override
    protected void decideAction(Character target) throws BattleOverException {
        if (!hasReviewed) {
            System.out.println("  Coffee Critic bans HEAL cards for 2 turns!");
            ruleSet.addRule(new BannedCardRule(ruleSet, CardType.HEAL, 2));
            hasReviewed = true;
        } else {
            switch (RANDOM.nextInt(2)) {
                case 0:
                    System.out.println("  Coffee Critic writes a scathing review!");
                    System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                    break;
                case 1:
                    System.out.println("  Coffee Critic scoffs and attacks!");
                    System.out.println("  -> " + target.getName() + " takes " + (getAttackPower() + 2) + " damage!");
                target.takeDamage(getAttackPower() + 2);
                    break;
            }
        }
    }
}
