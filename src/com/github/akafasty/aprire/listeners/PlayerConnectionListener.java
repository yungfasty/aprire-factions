package com.github.akafasty.aprire.listeners;

import com.github.akafasty.aprire.configuration.Configuration;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import com.github.akafasty.aprire.users.controller.UserController;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionListener implements Listener {

    private final UserController USER_CONTROLLER = FactionsPlugin.getPlugin().getUserController();
    private final Configuration CONFIGURATION = FactionsPlugin.getPlugin().getConfiguration();
    private final String BASE = "level://../%s";

    @EventHandler(priority = EventPriority.HIGH)
    void onJoin(PlayerJoinEvent event) {

        String name = event.getPlayer().getName();
        Player player = event.getPlayer();

        if (USER_CONTROLLER.fetchByName(name) == null) {

            if (USER_CONTROLLER.exists(name))
                USER_CONTROLLER.load(name);

            else
                USER_CONTROLLER.insert(User.builder()
                    .name(name)
                    .factionTag(null)
                    .roleStr(null)
                    .invites(Lists.newArrayList())
                    .power((int) CONFIGURATION.getValue("start-power"))
                    .powerMax((int) CONFIGURATION.getValue("max-power"))
                    .lastActivity(System.currentTimeMillis())
                    .deaths(0)
                    .kills(0)
                    .build());
        }

    }

}
