package com.gox.wh40k.armybuilder.service.impl;

import com.gox.wh40k.armybuilder.model.Army;
import com.gox.wh40k.armybuilder.model.ArmyEntry;
import com.gox.wh40k.armybuilder.model.BattleSize;
import com.gox.wh40k.armybuilder.service.ArmyValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.gox.wh40k.armybuilder.model.UnitType.CHARACTER;

@Service
public class ArmyValidatorImpl implements ArmyValidator {

    @Override
    public boolean validateArmy(Army army) {
        int sumPoints = 0;
        boolean hasWarlord = false;
        Map<Long, Integer> unitCount = new HashMap<>();

        for (ArmyEntry armyEntry : army.getEntries()) {
            sumPoints += armyEntry.getUnit().getCost();

            long unitId = armyEntry.getUnit().getId();
            int count = unitCount.get(unitId) == null ? 0 : unitCount.get(unitId);

            switch (armyEntry.getUnit().getType()) {
                case BATTLELINE :
                case DEDICATED_TRANSPORT: {
                    if (count == 6) {
                        return false;
                    }
                    unitCount.put(unitId, count + 1);
                    break;
                }
                case CHARACTER :
                case OTHER_DATASHEETS:
                case ALLIED_UNITS: {
                    if (CHARACTER == armyEntry.getUnit().getType() && armyEntry.isWarlord()) {
                        if (hasWarlord) {
                            return false;
                        } else {
                            hasWarlord = true;
                        }
                    }
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
