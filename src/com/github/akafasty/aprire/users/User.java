package com.github.akafasty.aprire.users;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.main.FactionsPlugin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;

@Getter @Setter @Builder
public class User {

    private String name, factionTag, roleStr;
    private Collection<String> invites;
    private double power, powerMax;
    private int kills, deaths;
    private long lastActivity;

    public FactionData getFaction() { return FactionsPlugin.getPlugin().getFactionController().fetchByTag(factionTag); }

    public Role getRole() { return Role.fromString(roleStr); }

    public boolean hasFaction() { return Objects.nonNull(factionTag); }

    public int getKDR() {

        int kdr = kills-deaths;

        if (kdr <= 0) return 0;

        else return kdr;

    }

}
