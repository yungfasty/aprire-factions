package com.github.akafasty.aprire.factions.controller;

import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.factions.dao.FactionDAO;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import com.github.yungfasty.fastcaching.collections.FastCollection;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.*;
import java.util.*;

public class FactionController {

    private final FastCollection<FactionData> FACTIONS_DATA;

    /**
     * constructor
     **/
    public FactionController() { FACTIONS_DATA = new FastCollection<FactionData>(5000); }

    /**
     * method used to fetch an faction by name
     * @param name to be searched
     * @return faction that matches with name.
     */
    public FactionData fetchByName(@NonNull String name) {

        return FACTIONS_DATA.stream()
                .filter(context -> context.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);

    }

    /**
     * method used to fetch an faction by tag
     * @param tag to be searched
     * @return faction that matches with name.
     */
    public FactionData fetchByTag(@NonNull String tag) {

        return FACTIONS_DATA.fetch(tag);

    }

    /**
     * method used to cache an user
     * @param faction to be inserted
     */
    public void insert(@NonNull FactionData faction) { FACTIONS_DATA.put(faction.getTag(), faction); }

    /**
     * method used to remove a user from the cache
     * @param faction to be removed
     */
    public void remove(@NonNull FactionData faction) {

        new File("aprire/factions", String.format("%s-%s.json", faction.getTag(), faction.getName())).delete();

        FACTIONS_DATA.remove(faction.getTag());
    }

    /**
     * method used to check tag availability
     * @param tag to be checked
     * @return boolean that corresponds to tag's availability
     */
    public boolean isTagTaken(@NonNull String tag) { return Objects.nonNull(fetchByTag(tag)); }

    /**
     * method used to check name availability
     * @param name to be checked
     * @return boolean that corresponds to name's availability
     **/
    public boolean isNameTaken(@NonNull String name) { return Objects.nonNull(fetchByName(name)); }

    /**
     * method used to fetch an faction kdr by tag
     * @param tag to be searched
     * @return total faction kdr
     */
    public int fetchKdrByTag(@NonNull String tag) {

        return FactionsPlugin.getPlugin().getUserController().fetchMembersByTag(tag).stream().mapToInt(User::getKDR).sum();

    }

    /**
     * method used to fetch an faction power by tag
     * @param tag to be searched
     * @return total faction power
     */
    public double fetchPowerByTag(@NonNull String tag) {

        return FactionsPlugin.getPlugin().getUserController().fetchMembersByTag(tag).stream().mapToDouble(User::getPower).sum();

    }

    /**
     * method used to fetch an faction power by tag
     * @param tag to be searched
     * @return total faction power
     */
    public double fetchMaxPowerByTag(@NonNull String tag) {

        return FactionsPlugin.getPlugin().getUserController().fetchMembersByTag(tag).stream().mapToDouble(User::getPowerMax).sum();

    }

    /**
     * load all factions
     **/
    @SneakyThrows
    public void init() {

        FactionDAO factionDAO = FactionsPlugin.getPlugin().getFactionDAO();

        Arrays.asList(new File("aprire/factions").listFiles()).forEach(file -> {

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

                FactionData faction = factionDAO.jsonToFaction(bufferedReader.readLine());

                FACTIONS_DATA.put(faction.getTag(), faction);

            } catch (Exception exception) { exception.printStackTrace(); }

        });

    }

    /**
     * saves all factions
     */
    @SneakyThrows
    public void saveAll() {

        FactionDAO factionDAO = FactionsPlugin.getPlugin().getFactionDAO();

        FACTIONS_DATA.forEach(factionDAO::factionToFile);

    }

}
