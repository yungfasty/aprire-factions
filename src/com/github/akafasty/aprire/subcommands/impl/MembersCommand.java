package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.inventories.MembersInventory;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;

public class MembersCommand extends SubCommand {

    public MembersCommand() {
        super("members", "membros");
    }

    public void execute(Player player, String[] arguments) {

        User user = USER_CONTROLLER.fetchByName(player.getName());

        if (arguments.length != 2) { new MembersInventory(player, user.getFactionTag(), false); return; }

        FactionData faction = null;

        if (FACTION_CONTROLLER.isTagTaken(arguments[1])) faction = FACTION_CONTROLLER.fetchByTag(arguments[1]);
        if (FACTION_CONTROLLER.isNameTaken(arguments[1])) faction = FACTION_CONTROLLER.fetchByName(arguments[1]);

        if (faction == null) { player.sendMessage("§cEsta facção não existe."); return; }

        new MembersInventory(player, faction.getTag(), false);

    }
}
