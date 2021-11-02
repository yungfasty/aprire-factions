package com.github.akafasty.aprire.lands.controller;

import com.github.akafasty.aprire.lands.Land;
import com.github.akafasty.aprire.lands.dao.LandDAO;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.yungfasty.fastcaching.collections.FastCollection;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class LandController {

    private FastCollection<Land> LANDS_DATA;

    public LandController() { LANDS_DATA = new FastCollection<>(5000); }

    /**
     * method used to fetch an user by name
     * @param x chunk x
     * @param z chunk z
     * @param world where the land must be
     * @return land object that is in specified x, z.
     * @return null if its wilderness
     */
    public Land fetchByName(String world, int x, int z) {

        return LANDS_DATA.stream()
                .filter(context -> context.getX() == x)
                .filter(context -> context.getZ() == z)
                .filter(context -> context.getWorld().equalsIgnoreCase(world))
                .findFirst().orElse(null);

    }

    /**
     * method used to cache an land
     * @param land to be inserted
     */
    public void insert(@NonNull Land land) { LANDS_DATA.add(land); }

    /**
     * method used to remove a land from the cache
     * @param land to be removed
     */
    public void remove(@NonNull Land land) { LANDS_DATA.remove(land); }

    /**
     * method used to fetch lands by tag
     * @param tag faction's tag
     * @return faction's lands set
     */
    public Set<Land> fetchLandsByTag(String tag) { return LANDS_DATA.stream().filter(context -> context.getFaction().equalsIgnoreCase(tag)).collect(Collectors.toSet()); }

    /**
     * method used to fetch lands by name
     * @param name faction's tag
     * @return faction's lands set
     */
    public Set<Land> fetchLandsByName(String name) { return LANDS_DATA.stream().filter(context -> context.getFaction().equalsIgnoreCase(name)).collect(Collectors.toSet()); }


    @SneakyThrows
    public void init() {

        LandDAO landDAO = FactionsPlugin.getPlugin().getLandDAO();
        File file = new File("aprire/lands", "lands.json");

        if (!file.exists()) file.createNewFile();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) { LANDS_DATA.addAll(landDAO.jsonToLands(bufferedReader.readLine())); }

    }

}
