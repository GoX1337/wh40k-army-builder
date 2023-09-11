package com.gox.wh40k.armybuilder.service.impl;

import com.gox.wh40k.armybuilder.model.Army;
import com.gox.wh40k.armybuilder.model.BattleSize;
import com.gox.wh40k.armybuilder.model.Unit;
import com.gox.wh40k.armybuilder.service.ArmyValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ArmyValidatorImpl implements ArmyValidator {

    @Override
    public boolean validateArmy(Army army) {
        int totalCost = 0;
        boolean hasWarlord = false;

        for (Unit unit : army.getUnits()) {
            totalCost += unit.getCost();
            if (!hasWarlord && unit.isWarlord()) {
                hasWarlord = true;
            }
        }

        return army.getFaction() != null
                && StringUtils.isNotBlank(army.getName())
                && StringUtils.isNotBlank(army.getDetachment())
                && totalCost <= getMaxPoint(army.getBattleSize())
                && hasWarlord;
    }

    private int getMaxPoint(BattleSize battleSize) {
        return switch (battleSize) {
            case INCURSION    -> 1000;
            case STRIKE_FORCE -> 2000;
            case ONSLAUGHT    -> 3000;
        };
    }
}
