package model;

public class SpecialistBarista extends Barista {

    public enum Specialization { ICE, ESPRESSO }
    private final Specialization specialization;

    public SpecialistBarista(String name, int health, int stamina, Specialization spec) {
        super(name, health, stamina);
        this.specialization = spec;
        applyBonus();
    }

    private void applyBonus() {
        switch (specialization) {
            case ICE:
                setMaxStamina(getMaxStamina() + 2);
                restoreStamina(2);
                System.out.println(getName() + " — Ice Barista: +2 max stamina!");
                break;
            case ESPRESSO:
                setMaxHealth(getMaxHealth() + 10);
                heal(10);
                System.out.println(getName() + " — Espresso Barista: +10 max HP!");
                break;
        }
    }

    public Specialization getSpecialization() { return specialization; }
}
