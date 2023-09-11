package com.gox.wh40k.armybuilder.service.impl;

import com.gox.wh40k.armybuilder.model.Army;
import com.gox.wh40k.armybuilder.model.BattleSize;
import com.gox.wh40k.armybuilder.model.Faction;
import com.gox.wh40k.armybuilder.model.Unit;
import com.gox.wh40k.armybuilder.service.ArmyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArmyValidatorImplTest {

    private ArmyValidator armyValidator;

    @BeforeEach
    public void setUp() {
        armyValidator = new ArmyValidatorImpl();
    }

    @Test
    public void testArmyValid() {
        Unit unit1 = Unit.builder()
                .cost(150)
                .build();
        Unit unit2 = Unit.builder()
                .cost(80)
                .isWarlord(true)
                .build();
        Unit unit3 = Unit.builder()
                .cost(50)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("army")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isTrue();
    }

    @Test
    public void testArmyValidStrikeForce() {
        Unit unit1 = Unit.builder()
                .cost(500)
                .build();
        Unit unit2 = Unit.builder()
                .cost(500)
                .isWarlord(true)
                .build();
        Unit unit3 = Unit.builder()
                .cost(100)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.STRIKE_FORCE)
                .name("army")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isTrue();
    }

    @Test
    public void testArmyValidOnslaught() {
        Unit unit1 = Unit.builder()
                .cost(1000)
                .build();
        Unit unit2 = Unit.builder()
                .cost(500)
                .isWarlord(true)
                .build();
        Unit unit3 = Unit.builder()
                .cost(1000)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.ONSLAUGHT)
                .name("army")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isTrue();
    }

    @Test
    public void testArmyNoWarlord() {
        Unit unit1 = Unit.builder()
                .cost(150)
                .build();
        Unit unit2 = Unit.builder()
                .cost(80)
                .build();
        Unit unit3 = Unit.builder()
                .cost(50)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("army")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }

    @Test
    public void testArmyTooMuchPoints() {
        Unit unit1 = Unit.builder()
                .cost(500)
                .build();
        Unit unit2 = Unit.builder()
                .cost(400)
                .isWarlord(true)
                .build();
        Unit unit3 = Unit.builder()
                .cost(200)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("army")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }

    @Test
    public void testArmyValidEmptyName() {
        Unit unit1 = Unit.builder()
                .cost(150)
                .build();
        Unit unit2 = Unit.builder()
                .cost(80)
                .isWarlord(true)
                .build();
        Unit unit3 = Unit.builder()
                .cost(50)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }

    @Test
    public void testArmyValidEmptyDetachment() {
        Unit unit1 = Unit.builder()
                .cost(150)
                .build();
        Unit unit2 = Unit.builder()
                .cost(80)
                .isWarlord(true)
                .build();
        Unit unit3 = Unit.builder()
                .cost(50)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("army")
                .faction(Faction.ADEPTA_SORORITAS)
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }

    @Test
    public void testArmyValidEmptyFaction() {
        Unit unit1 = Unit.builder()
                .cost(150)
                .build();
        Unit unit2 = Unit.builder()
                .cost(80)
                .isWarlord(true)
                .build();
        Unit unit3 = Unit.builder()
                .cost(50)
                .build();
        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("army")
                .detachment("detachment")
                .units(List.of(unit1, unit2, unit3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }
}