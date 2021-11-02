package com.github.akafasty.aprire.inventories.listeners;

import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LeftInventoryListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent event) {

        String title = event.getInventory().getTitle();

        if (!title.endsWith("Deixar a")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {

            case 20:

                player.closeInventory();

                User user = FactionsPlugin.getPlugin().getUserController().fetchByName(player.getName());

                if (!user.hasFaction()) return;

                user.setRoleStr(null); user.setFactionTag(null);

                return;

            case 24:

                player.closeInventory();

                return;

        }
    }

}
