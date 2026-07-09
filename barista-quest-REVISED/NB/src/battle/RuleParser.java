package battle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 * Parses player-authored rule syntax into GameRule objects.
 *
 * Supported patterns (as per proposal):
 *
 *   IF player.health < N THEN player.gain("EspressoShot")
 *   IF player.health < N THEN player.heal(N)
 *   IF player.stamina < N THEN draw(N)
 *   IF turn % N == 0 THEN draw(N)
 *   EVERY N turns DRAW N card
 *   COMBO N THEN damage_boost_N
 *
 * Multiple rules separated by semicolons.
 */
public class RuleParser {

    private static final Pattern HEALTH_GAIN = Pattern.compile(
        "if\\s+player\\.health\\s*<\\s*(\\d+)\\s+then\\s+player\\.gain\\(\"?([\\w\\s]+)\"?\\)",
        Pattern.CASE_INSENSITIVE);

    private static final Pattern HEALTH_HEAL = Pattern.compile(
        "if\\s+player\\.health\\s*<\\s*(\\d+)\\s+then\\s+player\\.heal\\((\\d+)\\)",
        Pattern.CASE_INSENSITIVE);

    private static final Pattern STAMINA_DRAW = Pattern.compile(
        "if\\s+player\\.stamina\\s*<\\s*(\\d+)\\s+then\\s+draw\\((\\d+)\\)",
        Pattern.CASE_INSENSITIVE);

    private static final Pattern TURN_DRAW = Pattern.compile(
        "if\\s+turn\\s*%\\s*(\\d+)\\s*==\\s*0\\s+then\\s+draw\\((\\d+)\\)",
        Pattern.CASE_INSENSITIVE);

    private static final Pattern LEGACY_STAMINA = Pattern.compile(
        "if\\s+health\\s*<\\s*(\\d+)\\s+then\\s+gain_stamina_(\\d+)",
        Pattern.CASE_INSENSITIVE);

    private static final Pattern EVERY_DRAW = Pattern.compile(
        "every\\s+(\\d+)\\s+turns?\\s+draw\\s+(\\d+)",
        Pattern.CASE_INSENSITIVE);

    private static final Pattern COMBO_BOOST = Pattern.compile(
        "combo\\s+(\\d+)\\s+then\\s+damage_boost_(\\d+)",
        Pattern.CASE_INSENSITIVE);

    public static List<GameRule> parseRules(String input) {
        List<GameRule> rules = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) return rules;

        for (String line : input.split(";")) {
            line = line.trim();
            if (line.isEmpty()) continue;
            GameRule rule = parseLine(line);
            if (rule != null) {
                rules.add(rule);
                System.out.println("  OK: " + rule.describe());
            } else if (!line.toLowerCase().contains("player.gain")) {
                System.out.println("  Could not parse: \"" + line + "\"");
                System.out.println("  Try: IF player.health < 30 THEN player.gain(\"OatMilk\")");
            }
        }
        return rules;
    }

    private static GameRule parseLine(String line) {
        Matcher m;

        m = HEALTH_GAIN.matcher(line);
        if (m.find()) {
            int threshold = Integer.parseInt(m.group(1));
            String itemName = m.group(2).trim().toLowerCase();
            if (!itemName.equals("oatmilk") && !itemName.equals("oat_milk")
                    && !itemName.equals("espressoshot") && !itemName.equals("espresso_shot")
                    && !itemName.equals("simplesyrup") && !itemName.equals("simple_syrup")) {
                System.out.println("  Unknown item. Use: OatMilk, EspressoShot, or SimpleSyrup");
                return null;
            }
            return new ItemGainRule(threshold, m.group(2).trim());
        }

        m = HEALTH_HEAL.matcher(line);
        if (m.find()) {
            int threshold = Integer.parseInt(m.group(1));
            int healAmount = Integer.parseInt(m.group(2));
            return new HealRule(threshold, healAmount);
        }

        m = STAMINA_DRAW.matcher(line);
        if (m.find()) {
            int threshold = Integer.parseInt(m.group(1));
            int cards = Integer.parseInt(m.group(2));
            return new StaminaDrawRule(threshold, cards);
        }

        m = TURN_DRAW.matcher(line);
        if (m.find()) {
            int interval = Integer.parseInt(m.group(1));
            int cards = Integer.parseInt(m.group(2));
            return new PeriodicDrawRule(interval, cards);
        }

        m = LEGACY_STAMINA.matcher(line);
        if (m.find()) {
            int threshold = Integer.parseInt(m.group(1));
            int bonus = Integer.parseInt(m.group(2));
            return new ConditionalBonusRule(threshold, bonus);
        }

        m = EVERY_DRAW.matcher(line);
        if (m.find()) {
            int interval = Integer.parseInt(m.group(1));
            int cards = Integer.parseInt(m.group(2));
            return new PeriodicDrawRule(interval, cards);
        }

        m = COMBO_BOOST.matcher(line);
        if (m.find()) {
            int threshold = Integer.parseInt(m.group(1));
            int boostPct = Integer.parseInt(m.group(2));
            double multiplier = 1.0 + boostPct / 100.0;
            return new ComboRule(threshold, multiplier);
        }

        return null;
    }
}
