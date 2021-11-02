package com.github.akafasty.aprire.inventories.listeners;

import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InviteInventoryListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent event) {

        String title = event.getInventory().getTitle();

        if (!title.contains("Convidar")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String name = title.split(" ")[3];

        switch (event.getSlot()) {

            case 20:

                player.closeInventory();

                User user = FactionsPlugin.getPlugin().getUserController().fetchByName(player.getName()),
                     target = FactionsPlugin.getPlugin().getUserController().fetchByName(name);

                target.getInvites().add(user.getFactionTag());

                ComponentBuilder componentBuilder = new ComponentBuilder(String.format("§aVocê foi convidado para fazer parte da facção §f%s§a!", user.getFaction().getName()))
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/f entrar %s", user.getFactionTag())));

                Bukkit.getPlayer(name).spigot().sendMessage(componentBuilder.create());

                return;

            case 24:

                player.closeInventory();

                return;

        }
    }

}
