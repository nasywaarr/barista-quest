# Code changes applied (post-supervisor-remarks)

Base: barista-quest-PATCHED.zip
Verified: `javac -encoding UTF-8 -Xlint:all` — clean (only 5 benign
`serialVersionUID` warnings on the custom exceptions). Full playthrough run;
multi-typing demo, player-authored rules, battles and RunSummary all behave
identically to the pre-patch build.

## 1. Step 3 of the 3-step information hiding engineering (Remark 2)

47 attributes were assigned once in the constructor and never reassigned, but
were not declared `final`. They are now `private final`, so Step 3
(constant vs variable) is enforced consistently across all 61 classes.

Each of the 47 was verified individually by the compiler: adding `final`
compiles, meaning the field is provably never reassigned.

Files touched (28):
  model/      Burned, Enemy, SpecialistBarista, StatusEffect, Barista
  battle/     AttackCard, BanCard, BattleContext, BattleEngine, ComboCard,
              ComboRule, ConditionalBonusRule, DebuffCard, HealCard, HealRule,
              ItemGainRule, PeriodicDrawRule, RuleSet, StaminaDrawRule
  items/      Collectible, Equipment, Ingredient, Inventory
  world/      BattleEncounter, RandomEvent, Region, ShopEncounter, WorldManager

33 attributes correctly REMAIN non-final because they are genuinely mutable
state (health, stamina, beans, comboBonus, phase, triggered/applied flags,
silencedTurns, turnsRemaining, justApplied, equipped, RunSummary counters,
RuleSet configuration values). These are the "variable" half of Step 3.

Attributes that are `private static final` (class + constant):
  - SC : Scanner            in BattleEngine, ShopEncounter, Main
  - RANDOM : Random         in AngryCustomer, RivalBarista, HealthInspector,
                               CoffeeCritic, Brewmaster
  - HEALTH_GAIN, HEALTH_HEAL, STAMINA_DRAW, TURN_DRAW, LEGACY_STAMINA,
    EVERY_DRAW, COMBO_BOOST : Pattern   in RuleParser   (SEVEN patterns)

## 2. RuleSet.java — field declaration hygiene

`customRules` was declared in the middle of the class body, after
`isCardAllowed()`, carrying a leftover patch comment
(`// + import java.util.ArrayList`). It is now declared with the other
attributes at the top of the class and initialised in the constructor,
consistent with `allowedCardTypes` and `bannedCardDurations`.

## 3. Barista.setComboBonus() — validating mutator

Was a bare assignment. Section 3.3 of the report claims mutable state
"only changes through validating methods", so the setter now clamps to
a non-negative value:

    public void setComboBonus(int bonus) { this.comboBonus = Math.max(0, bonus); }

## Not changed (intentionally)

Nothing else in the source is incorrect. The remaining items raised in review
are REPORT errors, not code errors, and must be fixed in the .pdf:

  - Sec 4.4 screenshot of HealthInspector shows `ruleSet.banCardType(...)`;
    the real code (unchanged, and correct) calls
    `ruleSet.addRule(new BannedCardRule(ruleSet, CardType.ATTACK, 2))`.
  - Sec 8.3 justification: enemies DO add to List<GameRule> via addRule();
    the ban store is Map<CardType,Integer>, not Set<CardType>. No
    ConcurrentModificationException occurs because addRule() is called from
    enemy.act(), outside the applyCustomRules() iteration, and no rule's
    execute() adds rules.
  - Sec 2.3 loop order: actual order is rules -> player turn -> enemy turn ->
    tickStatusEffects() -> ruleSet.tick() -> restoreStamina().
  - Sec 4.3: seven compiled Patterns, mapping to six player-authorable rule types.
  - Sec 8.6 caption + Figure 2: the ban system is Map-based, not Set-based.
  - Sec 8.4: instanceof appears at 16 sites across 11 files, not "two places".
  - Rebuttal/Remark 2: two external classes, three accesses (ComboCard x2,
    BattleEngine x1).
  - Sec 6.4 screenshot of AttackCard.use() predates the combo-bonus code.
  - Verify the GitHub URL cited in Sec 1.3 / Sec 3 / Sec 4 / Sec 5.
