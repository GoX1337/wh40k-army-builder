package com.gox.wh40k.armybuilder.service.impl;

import com.gox.wh40k.armybuilder.model.*;
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
                .type(UnitType.BATTLELINE)
                .build();
        Unit unit2 = Unit.builder()
                .cost(80)
                .type(UnitType.CHARACTER)
                .build();
        Unit unit3 = Unit.builder()
                .type(UnitType.OTHER_DATASHEETS)
                .cost(50)
                .build();

        ArmyEntry armyEntry1 = ArmyEntry.builder()
                .unit(unit1)
                .build();
        ArmyEntry armyEntry2 = ArmyEntry.builder()
                .unit(unit2)
                .isWarlord(true)
                .build();
        ArmyEntry armyEntry3 = ArmyEntry.builder()
                .unit(unit3)
                .build();

        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("Army name")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .entries(List.of(armyEntry1, armyEntry2, armyEntry3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isTrue();
    }

    @Test
    public void testArmyNoWarlord() {
        Unit unit1 = Unit.builder()
                .cost(150)
                .type(UnitType.BATTLELINE)
                .build();
        Unit unit2 = Unit.builder()
                .cost(80)
                .type(UnitType.CHARACTER)
                .build();
        Unit unit3 = Unit.builder()
                .type(UnitType.OTHER_DATASHEETS)
                .cost(50)
                .build();

        ArmyEntry armyEntry1 = ArmyEntry.builder()
                .unit(unit1)
                .build();
        ArmyEntry armyEntry2 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry3 = ArmyEntry.builder()
                .unit(unit3)
                .build();

        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("Army name")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .entries(List.of(armyEntry1, armyEntry2, armyEntry3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }

    @Test
    public void testArmyPointsExceeded() {
        Unit unit1 = Unit.builder()
                .cost(500)
                .type(UnitType.BATTLELINE)
                .build();
        Unit unit2 = Unit.builder()
                .cost(500)
                .type(UnitType.CHARACTER)
                .build();
        Unit unit3 = Unit.builder()
                .type(UnitType.OTHER_DATASHEETS)
                .cost(500)
                .build();

        ArmyEntry armyEntry1 = ArmyEntry.builder()
                .unit(unit1)
                .build();
        ArmyEntry armyEntry2 = ArmyEntry.builder()
                .unit(unit2)
                .isWarlord(true)
                .build();
        ArmyEntry armyEntry3 = ArmyEntry.builder()
                .unit(unit3)
                .build();

        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("Army name")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .entries(List.of(armyEntry1, armyEntry2, armyEntry3))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }

    @Test
    public void testArmyUnitMaxOccurrence() {
        Unit unit1 = Unit.builder()
                .cost(100)
                .type(UnitType.CHARACTER)
                .build();
        Unit unit2 = Unit.builder()
                .type(UnitType.OTHER_DATASHEETS)
                .cost(50)
                .build();

        ArmyEntry armyEntry1 = ArmyEntry.builder()
                .unit(unit1)
                .isWarlord(true)
                .build();
        ArmyEntry armyEntry2 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry3 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry4 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry5 = ArmyEntry.builder()
                .unit(unit2)
                .build();

        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("Army name")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .entries(List.of(armyEntry1, armyEntry2, armyEntry3, armyEntry4, armyEntry5))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }

    @Test
    public void testArmyUnitMaxOccurrenceBattleLine() {
        Unit unit1 = Unit.builder()
                .cost(100)
                .type(UnitType.CHARACTER)
                .build();
        Unit unit2 = Unit.builder()
                .type(UnitType.BATTLELINE)
                .cost(50)
                .build();

        ArmyEntry armyEntry1 = ArmyEntry.builder()
                .unit(unit1)
                .isWarlord(true)
                .build();
        ArmyEntry armyEntry2 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry3 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry4 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry5 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry6 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry7 = ArmyEntry.builder()
                .unit(unit2)
                .build();
        ArmyEntry armyEntry8 = ArmyEntry.builder()
                .unit(unit2)
                .build();

        Army army = Army.builder()
                .battleSize(BattleSize.INCURSION)
                .name("Army name")
                .detachment("detachment")
                .faction(Faction.ADEPTA_SORORITAS)
                .entries(List.of(armyEntry1, armyEntry2, armyEntry3, armyEntry4, armyEntry5, armyEntry6, armyEntry7, armyEntry8))
                .build();

        assertThat(armyValidator.validateArmy(army)).isFalse();
    }
}