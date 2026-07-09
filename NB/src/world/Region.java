package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Region {
    private final String name;
    private final List<Encounter> encounterPool;

    public Region(String name) {
        this.name = name;
        this.encounterPool = new ArrayList<>();
    }

    public abstract void buildEncounterPool(battle.RuleSet ruleSet);

    public void shuffle() { Collections.shuffle(encounterPool); }

    public Encounter nextEncounter() { return encounterPool.remove(0); }

    protected void addEncounter(Encounter e) { encounterPool.add(e); }

    public String getName()          { return name; }
    public boolean hasEncounters()   { return !encounterPool.isEmpty(); }
    public int remainingEncounters() { return encounterPool.size(); }
}
