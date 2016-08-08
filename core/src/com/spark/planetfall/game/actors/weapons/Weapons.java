package com.spark.planetfall.game.actors.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spark.planetfall.game.actors.components.weapons.*;


public enum Weapons {

    /*
    *  DEBUG WEAPONS
    *
    *  These weapons are purely for testing purposes and MUST be commented out later

    //AK_47
    AK_47(
            new FullAuto(500),
            new Magazine(143, 480, 1, 0f, 30),
            new RecoilMechanism(4f, 0.3f, 0.2f, new Vector2(20, 10), 3),
            new MagazineReload(2.5f, 1.5f, 1),
            new StatModifiers(1, 1, 0.5f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1), "LMGs/NC6_Gauss_Saw", "NC6_Gauss_SAW"),
            new FireBullet(0.5f),
            WeaponTypes.PRIMARY_WEAPON,
            "AK 47"),

    //SCAR_H
    SCAR_H(
            new SemiAuto(255),
            new Magazine(334, 520, 1, 0, 20),
            new RecoilMechanism(6f, 0.4f, 0.2f, new Vector2(60, 20), 2),
            new MagazineReload(2f, 1.2f, 1),
            new StatModifiers(1, 1, 0.75f),
            new Effects("particle/SCAR_H_SHOOT.p", new Color(1, 1, 0, 1),"LMGs/NC6_Gauss_Saw", "NC6_Gauss_SAW"),
            new FireBullet(0.5f),
            WeaponTypes.SECONDARY_WEAPON,
            "Scar H"),

    //MED_KITS AKA SKILLSTIX
    MED_KITS(
            new SemiAutoReloadAuto(60),
            new Magazine(500, 420, 1, 0f, 1),
            new RecoilMechanism(0f, 0f, 0f, new Vector2(0f, 0f), 1),
            new MagazineReload(1f, 1f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/MED_KIT_SHOOT.p", new Color(1, 1, 1, 1),"LMGs/NC6_Gauss_Saw", "NC6_Gauss_SAW"),
            new FireHeal(false, false),
            WeaponTypes.PRIMARY_WEAPON,
            "MED KIT"),

    //TANK_BUSTER
    TANK_BUSTER(
            new FullAuto(600),
            new Magazine(167, 300, 2, 1f, 15),
            new RecoilMechanism(2f, 2f, 0.5f, new Vector2(0f, 0f), 1),
            new MagazineReload(1f, 1f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 1, 1),"LMGs/NC6_Gauss_Saw", "NC6_Gauss_SAW"),
            new FireBullet(1),
            WeaponTypes.PRIMARY_WEAPON,
            "TANK BUSTER"),

    //Python AP
    PYTHON_AP(
            new SemiAutoReloadAuto(420),
            new Magazine(1200, 225, 1, 0f, 1),
            new RecoilMechanism(2f, 2f, 0.5f, new Vector2(0f, 0f), 1),
            new MagazineReload(1.5f, 1.5f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 1, 1),"LMGs/NC6_Gauss_Saw", "NC6_Gauss_SAW"),
            new FireBullet(3),
            WeaponTypes.PRIMARY_WEAPON,
            "PYTHON AP"),

    //BASILISK
    BASILISK(
            new FullAuto(450),
            new Magazine(200, 480, 1, 0f, 30),
            new RecoilMechanism(0.3f, 0.3f, 0f, new Vector2(0, 0), 0),
            new MagazineReload(2.5f, 2.5f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1),"LMGs/NC6_Gauss_Saw", "NC6_Gauss_SAW"),
            new FireBullet(2),
            WeaponTypes.PRIMARY_WEAPON,
            "BASILISK"),

    */



    /*
    *
    * Final Weapons
    *
     */

    SCAR_H(
            new FullAuto(500),
            new Magazine(new DamageModel(200,15,167,85), 600, 1, 0f, 25),
            new RecoilMechanism(5f, 0f, 0.12f, new Vector2(15, 10), 2),
            new MagazineReload(2.5f, 2f, 1),
            new StatModifiers(1, 1, 0.5f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1), "Assault_Rifles/SCAR_H", "SCAR_H"),
            new FireBullet(0.5f),
            WeaponTypes.PRIMARY_WEAPON,
            "SCAR H"),

    M9(
            new SemiAuto(400),
            new Magazine(new DamageModel(167,10,112,50), 390, 1, 0f, 21),
            new RecoilMechanism(1f, 0.3f, 0.05f, new Vector2(3, 5), 1),
            new MagazineReload(1.8f, 1.525f, 1),
            new StatModifiers(1, 1, 0.75f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1), "Pistols/M9", "M9"),
            new FireBullet(0.5f),
            WeaponTypes.SECONDARY_WEAPON,
            "M9"),

    M14(
            new SemiAuto(255),
            new Magazine(new DamageModel(334,15,280,75), 520, 1, 0f, 15),
            new RecoilMechanism(3f, 0.05f, 0.15f, new Vector2(3, 5), 1),
            new MagazineReload(3.5f, 2.6f, 1),
            new StatModifiers(1, 1, 0.75f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1), "Assault_Rifles/M14", "M14"),
            new FireBullet(0.5f),
            WeaponTypes.PRIMARY_WEAPON,
            "M14"),

    SPAZ_12(
            new SemiAuto(71),
            new Magazine(new DamageModel(130,8,50,25), 300, 11, 3.5f, 4),
            new RecoilMechanism(3f, 0.5f, 0.5f, new Vector2(10, 5), 1),
            new MagazineReload(1.8f, 1.525f, 1),
            new StatModifiers(1, 1, 0.75f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1), "Shotguns/SPAZ_12", "SPAZ_12"),
            new FireBullet(0.5f),
            WeaponTypes.PRIMARY_WEAPON,
            "SPAZ 12"),

    ACR(
            new FullAuto(857),
            new Magazine(new DamageModel(125,10,112,55), 620, 1, 0f, 40),
            new RecoilMechanism(2.5f, 0.2f, 0.08f, new Vector2(3, 5), 1),
            new MagazineReload(2.92f, 2.12f, 1),
            new StatModifiers(1, 1, 0.5f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1), "Assault_Rifles/ACR", "ACR"),
            new FireBullet(0.5f),
            WeaponTypes.PRIMARY_WEAPON,
            "ACR"),

    AKM(
            new FullAuto(600),
            new Magazine(new DamageModel(167,10,143,75), 570, 1, 0f, 30),
            new RecoilMechanism(3f, 0.1f, 0.1f, new Vector2(3, 5), 1),
            new MagazineReload(2.92f, 2.12f, 1),
            new StatModifiers(1, 1, 0.5f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1), "Assault_Rifles/AKM", "AKM"),
            new FireBullet(0.5f),
            WeaponTypes.PRIMARY_WEAPON,
            "AKM");




    public final FireMechanism fireMechanism;
    public final ReloadMechanism reloadMechanism;
    public final Magazine magazine;
    public final RecoilMechanism recoilMechanism;
    public final StatModifiers statModifiers;
    public final Effects effects;
    public final Action action;
    public final byte type;
    public final String name;

    Weapons(FireMechanism fireMechanism, Magazine magazine, RecoilMechanism recoilMechanism, ReloadMechanism reloadMechanism, StatModifiers statModifiers, Effects effects, Action action, byte type, String name) {

        this.fireMechanism = fireMechanism;
        this.magazine = magazine;
        this.reloadMechanism = reloadMechanism;
        this.recoilMechanism = recoilMechanism;
        this.statModifiers = statModifiers;
        this.effects = effects;
        this.action = action;
        this.type = type;
        this.name = name;

    }

    public Weapon copy() {

        return new Weapon(this.fireMechanism.copy(), this.magazine.copy(), this.recoilMechanism.copy(), this.reloadMechanism.copy(), this.statModifiers.copy(), this.effects.copy(), this.action.copy(), this.type, this.name);

    }

    public Weapons[] getType(byte type) {
        Array<Weapons> weaponArray = new Array<Weapons>();

        for (int i = 0; i < Weapons.values().length; i++) {
            if (Weapons.values()[i].type == type) {
                weaponArray.add(Weapons.values()[i]);
            }
        }

        Weapons[] ret;

        ret = weaponArray.toArray(Weapons.class);

        return ret;

    }

}
