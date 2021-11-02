package com.github.akafasty.aprire.inventories.listeners;

import com.github.akafasty.aprire.inventories.MembersInventory;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MembersInventoryListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent event) {

        String title = event.getInventory().getTitle();

        if (!title.contains("Membros")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String factionTag = title.split(" ")[0].replace("[", "").replace("]", "");

        switch (event.getSlot()) {

            case 40:

                boolean onlines = title.endsWith("§8");
                new MembersInventory(player, factionTag, !onlines);
                player.updateInventory();

                return;

            case 36:

                player.chat("/f");
                player.updateInventory();

                return;

        }
    }

}
