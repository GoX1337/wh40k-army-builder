package com.gox.wh40k.armybuilder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Entity
@Data
public class Army {

    private String name;

    @Enumerated(EnumType.STRING)
    private Faction faction;

    @Enumerated(EnumType.STRING)
    private BattleSize battleSize;

    private String detachment;

}
