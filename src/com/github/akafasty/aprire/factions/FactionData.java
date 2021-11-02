package com.github.akafasty.aprire.factions;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.nexus.Nexus;
import com.github.akafasty.aprire.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Getter @Setter @Builder
public class FactionData {

    private String name, tag;
    private HashMap<EntityType, Integer> generators;
    private Nexus nexus;

    public boolean hasNexus() { return Objects.nonNull(nexus); }

    public List<User> getMembers() { return FactionsPlugin.getPlugin().getUserController().fetchMembersByTag(tag); }

    public User getOwner() { return getMembers().stream().filter(context -> context.getRole().equals(Role.OWNER)).findFirst().orElse(null); }

    public int getLandCount() { return FactionsPlugin.getPlugin().getLandController().fetchLandsByTag(tag).size(); }

    public int getKDR() { return FactionsPlugin.getPlugin().getFactionController().fetchKdrByTag(tag); }

    public double getPower() { return FactionsPlugin.getPlugin().getFactionController().fetchPowerByTag(tag); }

    public double getPowerMax() { return FactionsPlugin.getPlugin().getFactionController().fetchMaxPowerByTag(tag); }

    public Integer getTypeAmount(EntityType entityType) { return generators.get(entityType); }

}
