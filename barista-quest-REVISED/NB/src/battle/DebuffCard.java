package battle;

import model.Character;
import model.StatusEffect;
import exceptions.EmptyDeckException;
import exceptions.InvalidBrewException;
import exceptions.OutOfStockException;
import exceptions.BattleOverException;
import exceptions.InvalidRuleException;

public class DebuffCard extends CoffeeCard {
    private final StatusEffect effect;
    
    public DebuffCard(String name, int staminaCost, StatusEffect effect) {
        super(name, staminaCost, CardType.DEBUFF);
        this.effect = effect;
    }
    
    @Override
    public void use(Character user, Character target) 
        throws EmptyDeckException, InvalidBrewException, 
               OutOfStockException, BattleOverException, InvalidRuleException {
        user.useStamina(this.getStaminaCost());
        target.addStatusEffect(this.effect);
        System.out.println("  " + user.getName() + " plays " + this.getName() + 
                          " -> " + target.getName() + " is " + this.effect.getName() + "!");
    }

    @Override
    public String describe() {
        return "[" + getType() + "] " + getName()
            + " (cost: " + getStaminaCost() + " stamina, applies " + effect.getName() + ")";
    }
}
