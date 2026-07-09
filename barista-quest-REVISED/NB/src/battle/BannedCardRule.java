package battle;

public class BannedCardRule implements GameRule {
    private final RuleSet ruleSet;
    private final CardType bannedType;
    private final int turns;
    private boolean applied;

    public BannedCardRule(RuleSet ruleSet, CardType bannedType, int turns) {
        this.ruleSet = ruleSet;
        this.bannedType = bannedType;
        this.turns = turns;
    }

    @Override
    public boolean appliesTo(GameEvent event) {
        return !applied;
    }

    @Override
    public void execute(GameEvent event) {
        ruleSet.banCardType(bannedType, turns);
        applied = true;
    }

    @Override
    public String describe() {
        return "Banned Card: " + bannedType + " for " + turns + " turn(s)";
    }
}
