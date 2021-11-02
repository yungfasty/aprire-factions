package com.github.akafasty.aprire.nexus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Arrays;

@Getter @Setter @Builder
public class Nexus {

    private int x, z, level, health;
    private String factionTag;

    public EnderCrystal getEntity() {
        return (EnderCrystal) Arrays
            .stream(Bukkit.getWorld("world").getChunkAt(x, z).getEntities())
            .filter(context -> context.getType() == EntityType.ENDER_CRYSTAL)
            .findFirst().orElse(null);
    }

}
