package com.spark.planetfall.game.actors.components.weapons;


public class Weapon {

    private ReloadMechanism reload;
    private RecoilMechanism recoil;
    private Magazine magazine;
    private FireMechanism mechanism;
    private StatModifiers modifiers;
    private Effects effects;
    private Action action;

    public Weapon(FireMechanism fireMechanism, Magazine magazine, RecoilMechanism recoilMechanism, ReloadMechanism reloadMechanism, StatModifiers statModifiers, Effects effects, Action action) {

        this.mechanism = fireMechanism;
        this.magazine = magazine;
        this.recoil = recoilMechanism;
        this.reload = reloadMechanism;
        this.modifiers = statModifiers;
        this.effects = effects;
        this.action = action;

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

    public Weapon copy() {

        Weapon weapon = new Weapon(mechanism.copy(), magazine.copy(), recoil.copy(), reload.copy(), modifiers.copy(), effects.copy(), action.copy());
        weapon.mechanism.setWeapon(weapon);
        weapon.recoil.setWeapon(weapon);
        weapon.reload.setWeapon(weapon);
        weapon.action.setWeapon(weapon);

        return weapon;

    }

}
