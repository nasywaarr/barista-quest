# ☕ Java Coffee — The Barista's Quest

A console-based turn-based RPG built in Java SE 17 as a final project for the Object-Oriented Programming course at Università degli Studi di Messina.

**Student:** Nasywa Azzahra Rizqi Ramadhani  
**Matriculation Number:** 574106  
**Instructor:** Prof. Salvatore Distefano  

---

## 📋 Documentation

- [📄 View OOP Project Proposal (PDF)](./OOP_Proposal.pdf)
- [📘 View OOP Project Report (PDF)](./OOP_Report.pdf)

---

## 🎮 About the Game

You are a young barista on a quest across three Indonesian coffee regions — **Warung Jogja**, **Solo Heritage**, and **Dieng Highlands** — battling rival brewers in coffee card battles, collecting rare beans, and working towards the title of **Grand Master Barista**.

---

## 🗺️ Game Flow

```
Region 1 — Warung Jogja
  └── Angry Customer → Shop

Region 2 — Solo Heritage
  └── Solo Rival → Shop

Region 3 — Dieng Highlands
  └── Health Inspector → Coffee Critic → Shop → Brewmaster (BOSS)
```

---

## ☕ Cards

| Card | Type | Cost | Effect |
|---|---|---|---|
| Espresso Shot | ATTACK | 2 ST | 15 damage |
| Americano | ATTACK | 3 ST | 22 damage |
| Cafe Creme | ATTACK | 1 ST | 8 damage |
| Warm Milk | HEAL | 0 ST | +4 stamina (free) |
| Oat Latte | HEAL | 1 ST | +20 HP |
| Cappuccino | DEBUFF | 2 ST | Steamed (3 dmg + stamina drain x 2 turns) |
| Double Shot Combo | COMBO | 4 ST | 15 + 15 = 30 damage |

### Extra Cards (added when deck size > 7)

| Card | Type | Cost | Effect |
|---|---|---|---|
| Ristretto Lock | DEBUFF | 3 ST | Silences enemy for 1 turn |
| Macchiato Punch | ATTACK | 2 ST | 14 damage |
| Steamed Milk | HEAL | 2 ST | +10 HP |
| Cold Brew Shot | ATTACK | 3 ST | 18 damage |
| Honey Latte | HEAL | 3 ST | +22 HP |

---

## 🎯 Custom Rules

At game start, author your own rules using:

```
IF player.health < 30 THEN player.heal(15)
IF player.health < 30 THEN player.gain(X)
IF player.stamina < 3 THEN draw(2)
IF turn % 3 == 0 THEN draw(1)
COMBO 2 THEN damage_boost_20
```

X: `OatMilk` (+10 HP) · `EspressoShot` (+10 ST) · `SimpleSyrup` (+15 HP +7 ST)

---

## 🏗️ OOP Concepts

| Concept | Implementation |
|---|---|
| Abstraction | `Character`, `CoffeeCard`, `Region` abstract classes; `Brewable`, `Describable` interfaces |
| Encapsulation | All fields private; accessed via `takeDamage()`, `heal()`, `useStamina()` |
| Information Hiding | Enemy AI hidden behind `act()` → `decideAction()` pattern |
| Inheritance | 3-level: `Character → Barista → SpecialistBarista` |
| Subtyping | `BattleEngine` operates on `Character` references regardless of runtime type |
| Multi-typing | `CoffeeCard` implements both `Brewable` AND `Describable` |
| Modularity | 5 packages: `model`, `battle`, `world`, `items`, `exceptions` |
| Composition | `Barista` HAS-A `CoffeeDeck`, HAS-A `Inventory` |
| Polymorphism (Inclusion) | `enemy.act()` dispatches differently per enemy type |
| Polymorphism (Overloading) | `CoffeeDeck.draw()`, `draw(int n)`, `draw(CardType filter)` |
| Polymorphism (Parametric) | `Inventory<T extends Item>`, `CoffeeDeck<T extends CoffeeCard>` |
| Polymorphism (Coercion) | `Ingredient` widened to `Brewable` reference |
| Extensibility | New cards, enemies, regions added without modifying existing classes |
| Exception Handling | `EmptyDeckException`, `InvalidBrewException`, `OutOfStockException`, `BattleOverException`, `InvalidRuleException` |

---

## 🚀 How to Run

**Requirements:** Java SE 17+, Apache Ant, NetBeans

```bash
# Clone the repository
git clone https://github.com/nasywaarr/barista-quest.git

# Navigate to the NetBeans project folder
cd barista-quest/NB

# Build and run
ant run
```

Or in NetBeans: **File → Open Project → select the `NB` folder → Run**

---

## 📦 Project Structure

```
barista-quest/
├── OOP_Proposal.pdf
├── OOP_Report.pdf
├── README.md
└── NB/                    # NetBeans project
    ├── .gitignore
    ├── build.xml
    ├── manifest.mf
    ├── nbproject/
    └── src/
        ├── Main.java
        ├── battle/        # CoffeeCard, CoffeeDeck, BattleEngine, GameRule system
        ├── model/         # Character, Barista, Enemy, StatusEffect hierarchies
        ├── items/         # Item, Ingredient, Equipment, Collectible, Inventory<T>
        ├── world/         # Region, Encounter, WorldManager
        └── exceptions/    # 5 custom exceptions
```

---

## 🛠️ Built With

- Java SE 17
- Apache Ant
- NetBeans IDE
- No external libraries

---

*Università degli Studi di Messina — OOP Course 2026*
