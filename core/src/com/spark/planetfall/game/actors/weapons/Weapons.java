package com.spark.planetfall.game.actors.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.components.weapons.*;


public enum Weapons {

    //AK_47
    AK_47(
            new FullAuto(750),
            new Magazine(143, 480, 1, 0f, 30),
            new RecoilMechanism(4f, 0.3f, 0.2f, new Vector2(20, 10), 3),
            new MagazineReload(2.5f, 1.5f, 1),
            new StatModifiers(1, 1, 0.5f),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1)),
            new FireBullet(1)),

    //SCAR_H
    SCAR_H(
            new SemiAuto(255),
            new Magazine(334, 520, 1, 0, 20),
            new RecoilMechanism(6f, 0.4f, 0.2f, new Vector2(60, 20), 2),
            new MagazineReload(2f, 1.2f, 1),
            new StatModifiers(1, 1, 0.75f),
            new Effects("particle/SCAR_H_SHOOT.p", new Color(1, 1, 0, 1)),
            new FireBullet(1)),

    //MED_KITS AKA SKILLSTIX
    MED_KITS(
            new SemiAutoReloadAuto(60),
            new Magazine(500, 420, 1, 0f, 1),
            new RecoilMechanism(0f, 0f, 0f, new Vector2(0f, 0f), 1),
            new MagazineReload(1f, 1f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/MED_KIT_SHOOT.p", new Color(1, 1, 1, 1)),
            new FireHeal(false, false)),

    //TANK_BUSTER
    TANK_BUSTER(
            new FullAuto(600),
            new Magazine(167, 300, 2, 1f, 15),
            new RecoilMechanism(2f, 2f, 0.5f, new Vector2(0f, 0f), 1),
            new MagazineReload(1f, 1f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 1, 1)),
            new FireBullet(1)),

    //Python AP
    PYTHON_AP(
            new SemiAutoReloadAuto(420),
            new Magazine(1200, 225, 1, 0f, 1),
            new RecoilMechanism(2f, 2f, 0.5f, new Vector2(0f, 0f), 1),
            new MagazineReload(1.5f, 1.5f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 1, 1)),
            new FireBullet(3)),

    //BASILISK
    BASILISK(
            new FullAuto(450),
            new Magazine(200, 480, 1, 0f, 30),
            new RecoilMechanism(0.3f, 0.3f, 0f, new Vector2(0, 0), 0),
            new MagazineReload(2.5f, 2.5f, 1),
            new StatModifiers(1, 1, 1),
            new Effects("particle/AK_47_SHOOT.p", new Color(1, 1, 0, 1)),
            new FireBullet(2));


    public FireMechanism fireMechanism;
    public ReloadMechanism reloadMechanism;
    public Magazine magazine;
    public RecoilMechanism recoilMechanism;
    public StatModifiers statModifiers;
    public Effects effects;
    public Action action;

    Weapons(FireMechanism fireMechanism, Magazine magazine, RecoilMechanism recoilMechanism, ReloadMechanism reloadMechanism, StatModifiers statModifiers, Effects effects, Action action) {

        this.fireMechanism = fireMechanism;
        this.magazine = magazine;
        this.reloadMechanism = reloadMechanism;
        this.recoilMechanism = recoilMechanism;
        this.statModifiers = statModifiers;
        this.effects = effects;
        this.action = action;

    }

    public Weapon copy() {

        return new Weapon(this.fireMechanism.copy(), this.magazine.copy(), this.recoilMechanism.copy(), this.reloadMechanism.copy(), this.statModifiers.copy(), this.effects.copy(), this.action.copy());

    }

}
