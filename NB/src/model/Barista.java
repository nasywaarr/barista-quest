package model;

import battle.CoffeeCard;
import battle.CoffeeDeck;
import battle.RuleSet;
import exceptions.BattleOverException;
import items.Inventory;
import items.Item;

public class Barista extends Character {

    private final CoffeeDeck<CoffeeCard> deck;
    private final Inventory<Item> inventory;
    private int beans;
    private int comboBonus;

    public Barista(String name, int health, int stamina) {
        super(name, health, stamina);
        this.deck = new CoffeeDeck<>();
        this.inventory = new Inventory<>();
        this.beans = 0;
    }

    @Override
    public void act(Character target) throws BattleOverException {
    }

    public void earnBeans(int amount) {
        beans += amount;
    }

    public boolean spendBeans(int cost) {
        if (cost > beans) return false;
        beans -= cost;
        return true;
    }

    public CoffeeDeck<CoffeeCard> getDeck() { return deck; }
    public Inventory<Item> getInventory()   { return inventory; }
    public int getBeans()                   { return beans; }

    /** Validating mutator: a combo bonus can never be negative. */
    public void setComboBonus(int bonus) {
        this.comboBonus = Math.max(0, bonus);
    }

    public int consumeComboBonus() {
        int b = comboBonus;
        comboBonus = 0;
        return b;
    }

    public int getEquipmentBonus() {
        int bonus = 0;
        for (Item item : inventory.getAll()) {
            if (item instanceof items.Equipment) {
                items.Equipment eq = (items.Equipment) item;
                if (eq.isEquipped()) bonus += eq.getPowerBonus();
            }
        }
        return bonus;
    }
}
