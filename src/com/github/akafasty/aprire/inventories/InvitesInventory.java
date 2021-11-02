package com.github.akafasty.aprire.inventories;

import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.factions.controller.FactionController;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import com.github.akafasty.aprire.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.concurrent.atomic.AtomicInteger;

public class InvitesInventory {

    public InvitesInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 6*9, String.format("[%s] - Convites", player.getName()));
        User user = FactionsPlugin.getPlugin().getUserController().fetchByName(player.getName());
        FactionController factionController = FactionsPlugin.getPlugin().getFactionController();

        AtomicInteger slot = new AtomicInteger(10);

        inventory.setItem(45, new ItemBuilder(Material.ARROW)
                .setDisplayName("§cVoltar")
                .setLore("§7Clique para voltar ao menu principal.")
                .build());

        if (user.getInvites().isEmpty())
            inventory.setItem(22, new ItemBuilder(Material.WEB)
                    .setDisplayName("§cSem convites!")
                    .setLore("§7Parece que você não foi convidado para nenhuma facção.")
                    .build());

        user.getInvites().stream()
                .filter(context -> factionController.fetchByTag(context) != null)
                .forEach(each -> {
                    if (slot.get() == 16) slot.set(19);
                    if (slot.get() == 25) slot.set(27);
                    if (slot.get() == 34) slot.set(36);
                    if (slot.get() == 43) slot.set(45);
                    if (slot.get() == 54) return;

                    FactionData faction = factionController.fetchByTag(each);

                    inventory.setItem(slot.getAndIncrement(), Banners.getAlphabet(new ItemBuilder(Material.BANNER)
                            .setDisplayName(String.format("§7[%s] %s", faction.getTag(), faction.getName()))
                            .setLore(
                                    String.format("§7Dono: §f%s", faction.getOwner().getName()),
                                    String.format("§7Terras: §f%s", faction.getLandCount()),
                                    String.format("§7Membros: §f%s/15", faction.getMembers().size()),
                                    String.format("§7Poder: §f%s/%s", faction.getPower(), faction.getPowerMax()),
                                    String.format("§7KDR: §f%s", faction.getKDR())
                            )
                            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                            .build(), each.substring(0, 1), DyeColor.WHITE, DyeColor.BLACK));

                });

        player.openInventory(inventory);

    }

}
