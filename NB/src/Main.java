import battle.*;
import exceptions.*;
import model.*;
import world.*;
import java.util.Scanner;

public class Main {

    // Single shared Scanner — never create multiple Scanner(System.in) instances.
    // private (access) · static (class member) · final (constant) — 3-step information hiding.
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("|  Java Coffee — The Barista's Quest    |");
        System.out.println("========================================");

        RuleSet rules = new RuleSet();
        configureRules(rules);
        configureCustomRules(rules);

        System.out.print("\nEnter your barista's name: ");
        String name = SC.nextLine().trim();
        if (name.isEmpty()) name = "Barista";

        Barista barista = chooseSpecialization(name, rules);
        buildStarterDeck(barista.getDeck(), rules.getStartingDeckSize());

        demonstrateMultityping(barista);

        RunSummary summary = new RunSummary();
        BattleEngine engine = new BattleEngine(rules, summary);
        WorldManager world = new WorldManager(rules, engine);
        world.buildWorld(rules);

        try {
            world.run(barista);
            if (barista.isAlive()) {
                System.out.println("\n----------------------------------------");
                System.out.println("  VICTORY! Congratulations, " + barista.getName() + "!");
                System.out.println("  You are the new Grand Master Barista!");
                System.out.println("----------------------------------------");
            } else {
                System.out.println("\n----------------------------------------");
                System.out.println("  DEFEAT! Better luck next time, " + barista.getName() + "!");
                System.out.println("  Your quest ends here.");
                System.out.println("----------------------------------------");
            }
        } catch (BattleOverException e) {
            System.out.println("\n----------------------------------------");
            System.out.println("  DEFEAT! Better luck next time!");
            System.out.println("  Your quest ends here.");
            System.out.println("----------------------------------------");
        } catch (Exception e) {
            System.out.println("\nUnexpected error: " + e.getMessage());
        }

        summary.setBeans(barista.getBeans());
        summary.printReport();
    }

    private static void configureRules(RuleSet rules) {
        System.out.println("\n--- Game Setup ---");
        System.out.println("(Press Enter to use default values)");
        int deckSize   = readInt("Deck size   (5-12,   default 7): ", 7, 5, 12);
        int handSize   = readInt("Hand size   (2-10,   default 4): ", 4, 2, 10);
        int stamina    = readInt("Max stamina (5-20,   default 10): ", 10, 5, 20);
        int difficulty = readInt("Difficulty  (1-3,    default 1): ", 1, 1, 3);
        System.out.print("Permadeath  (y/n,    default n): ");
        boolean pd = SC.nextLine().trim().equalsIgnoreCase("y");

        try {
            rules.configure(deckSize, handSize, stamina, difficulty, pd);
        } catch (InvalidRuleException e) {
            System.out.println("Invalid setting (" + e.getMessage() + ") — using defaults.");
        }
    }

    private static void configureCustomRules(RuleSet rules) {
        System.out.println("\n--- Custom Rules ---");
        System.out.println("Write your own rules. They fire automatically every turn.");
        System.out.println("Separate multiple rules with ;");
        System.out.println();
        System.out.println("  IF player.health < 30 THEN player.heal(15)");
        System.out.println("  IF player.health < 30 THEN player.gain(X)");
        System.out.println("  IF player.stamina < 3 THEN draw(2)");
        System.out.println("  IF turn % 3 == 0 THEN draw(1)");
        System.out.println("  COMBO 2 THEN damage_boost_20");
        System.out.println();
        System.out.println("  X = OatMilk (+10 HP)  EspressoShot (+10 ST)  SimpleSyrup (+5 HP +5 ST)");
        System.out.println();
        System.out.println("  Tip: IF player.health < 30 THEN player.gain(\"OatMilk\"); IF turn % 3 == 0 THEN draw(1)");
        System.out.println();
        System.out.println("(Press Enter to skip)");
        System.out.print("Your rules: ");
        String input = SC.nextLine().trim();
        while (!input.isEmpty()) {
            java.util.List<GameRule> parsed = RuleParser.parseRules(input);
            if (!parsed.isEmpty()) {
                for (GameRule r : parsed) rules.addRule(r);
                System.out.println("  " + parsed.size() + " rule(s) active this run.");
                break;
            } else {
                System.out.println();
                System.out.println("  Please try again or press Enter to skip.");
                System.out.print("Your rules: ");
                input = SC.nextLine().trim();
            }
        }
    }


    /**
     * MULTI-TYPING DEMONSTRATION (client program).
     * A CoffeeCard implements BOTH Brewable and Describable, so a single object
     * has more than one type at once. The SAME object is accessed here through
     * three different variable types — CoffeeCard, Describable, and Brewable —
     * and behaves correctly through every one of them.
     * An Item (OatMilk) is then accessed through the same Brewable type,
     * proving the interface is shared across two UNRELATED hierarchies.
     */
    private static void demonstrateMultityping(Barista barista) {
        System.out.println("\n--- Multi-typing demonstration ---");

        CoffeeCard card = new HealCard("Oat Latte", 1, 20);  // concrete-class view

        Describable d = card;   // SAME object, Describable view
        System.out.println("  As Describable -> " + d.describe());

        Brewable b = card;      // SAME object, Brewable view
        System.out.println("  As Brewable    -> brew() dispatched on the very same object"
            + " (card instanceof Brewable = " + (card instanceof Brewable) + ")");
        b.brew(barista);

        // Unrelated hierarchy: an Item is ALSO Brewable
        Brewable itemView = new items.OatMilk(10);
        System.out.println("  As Brewable (unrelated Item hierarchy):");
        itemView.brew(barista);

        System.out.println("  One object, three access types: "
            + "CoffeeCard, Describable, Brewable.");
        System.out.println("----------------------------------------");
    }

    private static Barista chooseSpecialization(String name, RuleSet rules) {
        System.out.println("\n--- Choose Your Barista ---");
        System.out.println("  [1] Standard Barista");
        System.out.println("  [2] Ice Barista      (+2 max stamina)");
        System.out.println("  [3] Espresso Barista (+10 max HP)");
        while (true) {
            System.out.print("Choice (1-3): ");
            String in = SC.nextLine().trim();
            switch (in) {
                case "1": return new Barista(name, 100, rules.getMaxStamina());
                case "2": return new SpecialistBarista(name, 100, rules.getMaxStamina(),
                              SpecialistBarista.Specialization.ICE);
                case "3": return new SpecialistBarista(name, 100, rules.getMaxStamina(),
                              SpecialistBarista.Specialization.ESPRESSO);
                default:  System.out.println("Please enter 1, 2, or 3.");
            }
        }
    }

    private static void buildStarterDeck(CoffeeDeck<CoffeeCard> deck, int size) {
        // 7 unique cards covering all 4 card types from proposal
        deck.addCard(new AttackCard("Espresso Shot",     2, 15));
        deck.addCard(new AttackCard("Americano",         3, 22));
        deck.addCard(new AttackCard("Cafe Creme",        1,  8));
        deck.addCard(new HealCard  ("Warm Milk",         0,  0, 4));
        deck.addCard(new HealCard  ("Oat Latte",         1, 20));
        deck.addCard(new DebuffCard("Cappuccino",        2, new model.Steamed(2)));
        deck.addCard(new ComboCard ("Double Shot Combo", 4,          // ComboCard — required by proposal
            new AttackCard("Shot A", 0, 15),
            new AttackCard("Shot B", 0, 15)));
        CoffeeCard[] extras = {
            new BanCard   ("Ristretto Lock",    3, 1),
            new AttackCard("Macchiato Punch",   2, 14),
            new HealCard  ("Steamed Milk",      2, 10),
            new AttackCard("Cold Brew Shot",    3, 18),
            new HealCard  ("Honey Latte",       3, 22),
        };
        for (int i = 7; i < size; i++)
            deck.addCard(extras[(i - 7) % extras.length]);
        deck.shuffle();
    }

    private static int readInt(String prompt, int def, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String in = SC.nextLine().trim();
            if (in.isEmpty()) return def;
            try {
                int v = Integer.parseInt(in);
                if (v >= min && v <= max) return v;
                System.out.println("  Enter a value between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a number.");
            }
        }
    }
}
