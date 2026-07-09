package battle;

import model.Brewable;
import model.Describable;
import model.Character;
import exceptions.EmptyDeckException;
import exceptions.InvalidBrewException;
import exceptions.OutOfStockException;
import exceptions.BattleOverException;
import exceptions.InvalidRuleException;

public abstract class CoffeeCard implements Brewable, Describable {
    private final String name;
    private final int staminaCost;
    private final CardType type;
    
    public CoffeeCard(String name, int staminaCost, CardType type) {
        this.name = name;
        this.staminaCost = staminaCost;
        this.type = type;
    }
    
    public abstract void use(Character user, Character target) 
        throws EmptyDeckException, InvalidBrewException, 
               OutOfStockException, BattleOverException, InvalidRuleException;
    
    @Override
    public void brew(Character target) {
    }
    
    @Override
    public String describe() {
        return "[" + type + "] " + name + " (cost: " + staminaCost + " stamina)";
    }
    
    public String getName() { return name; }
    public int getStaminaCost() { return staminaCost; }
    public CardType getType() { return type; }
}
