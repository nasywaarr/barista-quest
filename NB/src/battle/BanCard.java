package battle;

import model.Character;
import model.Enemy;
import exceptions.*;

public class BanCard extends CoffeeCard {
    private final int turns;

    public BanCard(String name, int staminaCost, int turns) {
        super(name, staminaCost, CardType.DEBUFF);
        this.turns = turns;
    }

    @Override
    public void use(Character user, Character target)
            throws InvalidBrewException, BattleOverException {
        user.useStamina(this.getStaminaCost());
        if (target instanceof Enemy) {
            ((Enemy) target).silence(turns);
            System.out.println("  " + user.getName() + " plays " + this.getName()
                + " -> " + target.getName() + " is SILENCED for " + turns + " turn(s)!");
        }
    }

    @Override
    public String describe() {
        return "[" + getType() + "] " + getName()
            + " (cost: " + getStaminaCost() + " stamina, silences enemy " + turns + " turn)";
    }
}
