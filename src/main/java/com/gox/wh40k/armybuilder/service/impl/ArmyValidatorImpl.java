package com.gox.wh40k.armybuilder.service.impl;

import com.gox.wh40k.armybuilder.model.Army;
import com.gox.wh40k.armybuilder.model.ArmyEntry;
import com.gox.wh40k.armybuilder.model.BattleSize;
import com.gox.wh40k.armybuilder.service.ArmyValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ArmyValidatorImpl implements ArmyValidator {

    @Override
    public boolean validateArmy(Army army) {
        int sumPoints = 0;
        boolean hasWarlord = false;
        Map<Long, Integer> unitCount = new HashMap<>();

        for (ArmyEntry armyEntry : army.getEntries()) {
            sumPoints += armyEntry.getUnit().getCost();

            switch (armyEntry.getUnit().getType()) {
                case CHARACTER : {
                    if (armyEntry.isWarlord()) {
                        if (hasWarlord) {
                            return false;
                        } else {
                            hasWarlord = true;
                        }
                    }
                    break;
                }
                case BATTLELINE :
                case DEDICATED_TRANSPORT: {
                    long unitId = armyEntry.getUnit().getId();
                    int count = unitCount.get(unitId) == null ? 0 : unitCount.get(unitId);
                    if (count == 6) {
                        return false;
                    }
                    unitCount.put(unitId, count + 1);
                    break;
                }
                case OTHER_DATASHEETS:
                case ALLIED_UNITS: {
                    long unitId = armyEntry.getUnit().getId();
                    int count = unitCount.get(unitId) == null ? 0 : unitCount.get(unitId);
                    if (count == 3) {
                        return false;
                    }
                    unitCount.put(unitId, count + 1);
                    break;
                }
            }
        }
        return sumPoints <= getMaxPoint(army.getBattleSize()) && hasWarlord;
    }

    private int getMaxPoint(BattleSize battleSize) {
        return switch (battleSize) {
            case INCURSION    -> 1000;
            case STRIKE_FORCE -> 2000;
            case ONSLAUGHT    -> 3000;
        };
    }
}
