package items;

/**
 * MokaPot — a named Equipment subtype.
 * Matches the proposal's item hierarchy: Equipment -> (MokaPot).
 */
public class MokaPot extends Equipment {
    public MokaPot(int powerBonus) {
        super("MokaPot", powerBonus);
    }
}
