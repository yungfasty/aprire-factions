package com.github.akafasty.aprire.inventories;

import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.factions.controller.FactionController;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import com.github.akafasty.aprire.utils.ItemBuilder;
import com.github.akafasty.aprire.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class MembersInventory {

    public MembersInventory(Player player, String factionTag, boolean onlines) {

        Inventory inventory = Bukkit.createInventory(null, 5*9, String.format("[%s] - Membros %s", factionTag, (onlines ? "§8" : "")));
        User user = FactionsPlugin.getPlugin().getUserController().fetchByName(player.getName());
        FactionController factionController = FactionsPlugin.getPlugin().getFactionController();
        AtomicInteger slot = new AtomicInteger(10);

        inventory.setItem(36, new ItemBuilder(Material.ARROW)
                .setDisplayName("§cVoltar")
                .setLore("§7Clique para voltar ao menu principal.")
                .build());

        if (onlines)
            inventory.setItem(40, new ItemBuilder(Material.INK_SACK, 1, 10)
                    .setDisplayName("§aFiltrar jogadores onlines")
                    .setLore("§7Clique para remover", "§7o filtro de jogadores onlines.")
                    .build());

        else
            inventory.setItem(40, new ItemBuilder(Material.INK_SACK, 1, 8)
                    .setDisplayName("§cFiltrar jogadores onlines")
                    .setLore("§7Clique para adicionar", "§7o filtro de jogadores onlines.")
                    .build());

        if (factionTag == null)
            inventory.setItem(22, new ItemBuilder(Material.WEB)
                    .setDisplayName("§cSem membros!")
                    .setLore("§7Parece que você não faz parte de nenhuma facção.")
                    .build());

        else {

            FactionData faction = factionController.fetchByTag(factionTag);

            if (onlines)
                faction.getMembers().stream()
                        .sorted(Comparator.comparingInt(context -> context.getRole().getValue()))
                        .filter(context -> Bukkit.getPlayer(context.getName()) != null)
                        .forEach(each -> {

                            if (slot.get() == 16) slot.set(19);
                            if (slot.get() == 25) slot.set(27);
                            if (slot.get() == 34) slot.set(36);
                            if (slot.get() == 43) slot.set(45);

                            inventory.setItem(slot.getAndIncrement(), new ItemBuilder(Material.SKULL_ITEM)
                                    .setDurability((short) 3)
                                    .setDisplayName(String.format("§6[Diretor] %s", each.getName()))
                                    .setLore(
                                            String.format("§7KDR: §f%s §8(Mortes: %s | Kills: %s)", NumberUtils.numberFormat(each.getKDR()), NumberUtils.numberFormat(each.getDeaths()), NumberUtils.numberFormat(each.getKills())),
                                            String.format("§7Cargo: §f%s%s", each.getRole().getSymbol(), each.getRole().getName()),
                                            String.format("§7Poder: §f%s/%s", user.getPower(), user.getPowerMax()))
                                    .setOwner(each.getName())
                                    .build());
                        });


            else
                faction.getMembers().stream()
                    .sorted(Comparator.comparingInt(context -> context.getRole().getValue()))
                    .forEach(each -> {

                        if (slot.get() == 16) slot.set(19);
                        if (slot.get() == 25) slot.set(27);
                        if (slot.get() == 34) slot.set(36);
                        if (slot.get() == 43) slot.set(45);

                        inventory.setItem(slot.getAndIncrement(), new ItemBuilder(Material.SKULL_ITEM)
                                .setDurability((short) 3)
                                .setDisplayName(String.format("§6[Diretor] %s", each.getName()))
                                .setLore(
                                        String.format("§7KDR: §f%s §8(Mortes: %s | Kills: %s)", NumberUtils.numberFormat(each.getKDR()), NumberUtils.numberFormat(each.getDeaths()), NumberUtils.numberFormat(each.getKills())),
                                        String.format("§7Cargo: §f%s%s", each.getRole().getSymbol(), each.getRole().getName()),
                                        String.format("§7Poder: §f%s/%s", user.getPower(), user.getPowerMax()),
                                        (Bukkit.getPlayer(each.getName()) == null ? String.format("§7Última atividade: %s", NumberUtils.numberTimeFormat(each.getLastActivity())) : "§7Atividade: §aonline"))
                                .setOwner(each.getName())
                                .build());
                    });
        }

        player.openInventory(inventory);

    }

}
