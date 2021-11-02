package com.github.akafasty.aprire.inventories.listeners;

import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KickInventoryListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent event) {

        String title = event.getInventory().getTitle();

        if (!title.contains("Expulsar")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String name = title.split(" ")[3];

        switch (event.getSlot()) {

            case 20:

                player.closeInventory();

                User user = FactionsPlugin.getPlugin().getUserController().fetchByName(name);

                user.getFaction().getMembers().forEach(each -> {

                    Player eachPlayer = Bukkit.getPlayer(each.getName());

                    if (eachPlayer != null) eachPlayer.sendMessage(String.format("§aO jogador §f%s§a foi expulso de sua facção por §f%s§a.", user.getName(), player.getName()));

                });

                user.setFactionTag(null);
                user.setRoleStr(null);

                return;

            case 24:

                player.closeInventory();

                return;

        }
    }

}
