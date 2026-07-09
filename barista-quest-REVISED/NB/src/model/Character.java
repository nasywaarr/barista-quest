package model;

import exceptions.BattleOverException;
import exceptions.InvalidBrewException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Character {
    private final String name;
    private int health;
    private int maxHealth;
    private int stamina;
    private int maxStamina;
    private final List<StatusEffect> statusEffects;

    public Character(String name, int health, int stamina) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.stamina = stamina;
        this.maxStamina = stamina;
        this.statusEffects = new ArrayList<>();
    }

    public abstract void act(Character target) throws BattleOverException;

    public void takeDamage(int amount) throws BattleOverException {
        if (amount < 0) amount = 0;
        health = Math.max(0, health - amount);
        if (health == 0)
            throw new BattleOverException(name + " has been defeated!");
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + Math.max(0, amount));
    }

    public void useStamina(int cost) throws InvalidBrewException {
        if (cost > stamina)
            throw new InvalidBrewException(name + " needs " + cost + " stamina but only has " + stamina + "!");
        stamina -= cost;
    }

    public void restoreStamina(int amount) {
        stamina = Math.min(maxStamina, stamina + Math.max(0, amount));
    }

    public void addStatusEffect(StatusEffect effect) {
        statusEffects.add(effect);
        System.out.println("  " + name + " is now " + effect.getName() + "!");
    }

    public void tickStatusEffects() {
        Iterator<StatusEffect> it = statusEffects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            if (e.isJustApplied()) {
                e.markApplied();
                continue;
            }
            e.tick(this);
            e.decrementTurn();
            if (e.isExpired()) {
                System.out.println("  " + name + " is no longer " + e.getName() + ".");
                it.remove();
            }
        }
    }

    public boolean hasStatusEffect(Class<? extends StatusEffect> type) {
        for (StatusEffect e : statusEffects)
            if (type.isInstance(e)) return true;
        return false;
    }

    public List<StatusEffect> getStatusEffects() {
        return Collections.unmodifiableList(statusEffects);
    }

    public boolean isAlive()     { return health > 0; }
    public String getName()      { return name; }
    public int getHealth()       { return health; }
    public int getMaxHealth()    { return maxHealth; }
    public int getStamina()      { return stamina; }
    public int getMaxStamina()   { return maxStamina; }

    protected void setMaxHealth(int v)  { maxHealth = v; }
    protected void setMaxStamina(int v) { maxStamina = v; }

    @Override
    public String toString() {
        return name + " [HP:" + health + "/" + maxHealth +
               " ST:" + stamina + "/" + maxStamina + "]";
    }
}
