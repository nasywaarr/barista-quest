package model;

public abstract class StatusEffect {
    private final String name;
    private int turnsRemaining;
    private boolean justApplied = true;

    public StatusEffect(String name, int turns) {
        this.name = name;
        this.turnsRemaining = turns;
    }

    public abstract void tick(Character target);

    public boolean isExpired()     { return turnsRemaining <= 0; }
    public void decrementTurn()    { turnsRemaining--; }
    public String getName()        { return name; }
    public int getTurnsRemaining() { return turnsRemaining; }
    public boolean isJustApplied() { return justApplied; }
    public void markApplied()      { justApplied = false; }
}
