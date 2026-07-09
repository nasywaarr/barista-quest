package battle;

import model.Character;
import exceptions.EmptyDeckException;
import exceptions.InvalidBrewException;
import exceptions.OutOfStockException;
import exceptions.BattleOverException;
import exceptions.InvalidRuleException;

public class ComboCard extends CoffeeCard {
    private final CoffeeCard firstCard;
    private final CoffeeCard secondCard;

    public ComboCard(String name, int staminaCost, CoffeeCard first, CoffeeCard second) {
        super(name, staminaCost, CardType.COMBO);
        this.firstCard = first;
        this.secondCard = second;
    }

    @Override
    public void use(Character user, Character target)
        throws EmptyDeckException, InvalidBrewException,
               OutOfStockException, BattleOverException, InvalidRuleException {
        user.useStamina(this.getStaminaCost());
        System.out.println("  COMBO! " + this.getName() + "!");
        firstCard.use(user, target);
        secondCard.use(user, target);
    }

    @Override
    public String describe() {
        int dmg1 = firstCard instanceof AttackCard ? ((AttackCard) firstCard).getDamage() : 0;
        int dmg2 = secondCard instanceof AttackCard ? ((AttackCard) secondCard).getDamage() : 0;
        return "[" + getType() + "] " + getName()
            + " (cost: " + getStaminaCost() + " stamina, "
            + dmg1 + "+" + dmg2 + "=" + (dmg1 + dmg2) + " dmg)";
    }
}
