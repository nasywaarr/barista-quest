package items;

/**
 * RareBean — a named Collectible subtype.
 * Matches the proposal's item hierarchy: Collectible -> (RareBean).
 */
public class RareBean extends Collectible {
    public RareBean(int scoreValue) {
        super("RareBean", scoreValue);
    }
}
