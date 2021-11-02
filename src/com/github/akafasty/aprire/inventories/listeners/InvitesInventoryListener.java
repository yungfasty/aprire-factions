package com.github.akafasty.aprire.inventories.listeners;

import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvitesInventoryListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent event) {

        String title = event.getInventory().getTitle();

        if (!title.contains("Convites")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {

            case 36:

                player.chat("/f");
                player.updateInventory();

                return;

            default:

                if (event.getCurrentItem().getType() == Material.AIR) return;

                User user = FactionsPlugin.getPlugin().getUserController().fetchByName(player.getName());
                String displayName = event.getCurrentItem().getItemMeta().getDisplayName(),
                        factionTag = displayName.replaceAll("§", "").split(" ")[0].replace("[", "").replace("]", "");

                FactionData faction = FactionsPlugin.getPlugin().getFactionController().fetchByTag(factionTag);

                if (faction == null) { user.getInvites().remove(factionTag); return; }

                player.closeInventory();
                player.chat(String.format("/f entrar %s", factionTag));

        }
    }

}
