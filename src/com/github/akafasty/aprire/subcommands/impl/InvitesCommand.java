package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.inventories.InvitesInventory;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;

public class InvitesCommand extends SubCommand {

    public InvitesCommand() {
        super("invites", "convites");
    }

    public void execute(Player player, String[] arguments) {

        if (arguments.length != 1) { player.sendMessage("§cUse \"/f convites\"."); return; }

        User user = USER_CONTROLLER.fetchByName(player.getName());

        if (user.hasFaction()) { player.sendMessage("§cVocê já faz parte de uma facção."); return; }

        new InvitesInventory(player);

    }
}
