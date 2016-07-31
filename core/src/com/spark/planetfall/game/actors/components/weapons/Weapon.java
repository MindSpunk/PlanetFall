package com.spark.planetfall.game.actors.components.weapons;


public class Weapon {

    private final ReloadMechanism reload;
    private final RecoilMechanism recoil;
    private final Magazine magazine;
    private final FireMechanism mechanism;
    private final StatModifiers modifiers;
    private final Effects effects;
    private final Action action;
    private final byte type;
    private final String name;

    public Weapon(FireMechanism fireMechanism, Magazine magazine, RecoilMechanism recoilMechanism, ReloadMechanism reloadMechanism, StatModifiers statModifiers, Effects effects, Action action, byte type, String name) {

        this.mechanism = fireMechanism;
        this.magazine = magazine;
        this.recoil = recoilMechanism;
        this.reload = reloadMechanism;
        this.modifiers = statModifiers;
        this.effects = effects;
        this.action = action;
        this.type = type;
        this.name = name;

        this.mechanism.setWeapon(this);
        this.reload.setWeapon(this);
        this.recoil.setWeapon(this);
        this.action.setWeapon(this);

    }

    public ReloadMechanism reload() {
        return reload;
    }

    public RecoilMechanism recoil() {
        return recoil;
    }

    public Magazine magazine() {
        return magazine;
    }

    public FireMechanism mechanism() {
        return mechanism;
    }

    public StatModifiers modifiers() {
        return modifiers;
    }

    public Effects effects() {
        return effects;
    }

    public Action action() {
        return action;
    }

    public byte type() { return type; }

    public String name() { return name; }

    public Weapon copy() {

        Weapon weapon = new Weapon(mechanism.copy(), magazine.copy(), recoil.copy(), reload.copy(), modifiers.copy(), effects.copy(), action.copy(), type, name);
        weapon.mechanism.setWeapon(weapon);
        weapon.recoil.setWeapon(weapon);
        weapon.reload.setWeapon(weapon);
        weapon.action.setWeapon(weapon);

        return weapon;

    }

}
