package com.github.akafasty.aprire.inventories.listeners;

import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DisbandInventoryListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent event) {

        String title = event.getInventory().getTitle();

        if (!title.endsWith("Desfazer")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {

            case 20:

                player.closeInventory();

                User user = FactionsPlugin.getPlugin().getUserController().fetchByName(player.getName());

                if (!user.hasFaction()) return;

                FactionData faction = user.getFaction();

                faction.getMembers().forEach(each -> {
                    each.setFactionTag(null); each.setRoleStr(null);

                    Player eachPlayer = Bukkit.getPlayer(each.getName());

                    if (eachPlayer != null) eachPlayer.sendMessage("§cUepa! Acho que sua facção foi desfeita.");

                });

                FactionsPlugin.getPlugin().getFactionController().remove(faction);

                return;

            case 24:

                player.closeInventory();

                return;

        }
    }

}
