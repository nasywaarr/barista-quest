package battle;

import exceptions.InvalidRuleException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RuleSet {
    private int startingDeckSize;
    private int handSize;
    private int maxStamina;
    private int enemyScalingFactor;
    private boolean permadeath;
    private final Set<CardType> allowedCardTypes;
    private final Map<CardType, Integer> bannedCardDurations;
    private final List<GameRule> customRules;

    public RuleSet() {
        this.startingDeckSize = 7;
        this.handSize = 4;
        this.maxStamina = 10;
        this.enemyScalingFactor = 1;
        this.permadeath = false;
        this.allowedCardTypes = new HashSet<>(Arrays.asList(CardType.values()));
        this.bannedCardDurations = new HashMap<>();
        this.customRules = new ArrayList<>();
    }

    public void configure(int deckSize, int handSize, int stamina,
                         int difficulty, boolean permadeath) throws InvalidRuleException {
        if (deckSize < 5 || deckSize > 12) {
            throw new InvalidRuleException("Deck size must be 5–12");
        }
        if (handSize < 2 || handSize > 10) {
            throw new InvalidRuleException("Hand size must be 2–10");
        }
        if (stamina < 5 || stamina > 20) {
            throw new InvalidRuleException("Stamina must be 5–20");
        }
        if (difficulty < 1 || difficulty > 3) {
            throw new InvalidRuleException("Difficulty must be 1–3");
        }

        this.startingDeckSize = deckSize;
        this.handSize = handSize;
        this.maxStamina = stamina;
        this.enemyScalingFactor = difficulty;
        this.permadeath = permadeath;
    }

    public void banCardType(CardType type, int turns) {
        bannedCardDurations.put(type, turns);
    }

    public void tick() {
        Iterator<Map.Entry<CardType, Integer>> it = bannedCardDurations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<CardType, Integer> entry = it.next();
            int remaining = entry.getValue() - 1;
            if (remaining <= 0) {
                it.remove();
            } else {
                entry.setValue(remaining);
            }
        }
    }

    public boolean isCardAllowed(CardType type) {
        return allowedCardTypes.contains(type) && !bannedCardDurations.containsKey(type);
    }

    public void addRule(GameRule rule) {
        customRules.add(rule);
    }

    /** Read-only view — rules can only be added through addRule(). */
    public List<GameRule> getRules() {
        return Collections.unmodifiableList(customRules);
    }

    public int getStartingDeckSize() { return startingDeckSize; }
    public int getHandSize() { return handSize; }
    public int getMaxStamina() { return maxStamina; }
    public int getEnemyScalingFactor() { return enemyScalingFactor; }
    public boolean isPermadeath() { return permadeath; }
}
