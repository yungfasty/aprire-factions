package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.inventories.DisbandInventory;
import com.github.akafasty.aprire.inventories.LeftInventory;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;

public class LeftCommand extends SubCommand {

    public LeftCommand() { super("left", "sair"); }

    public void execute(Player player, String[] arguments) {

        if (arguments.length != 1) { player.sendMessage("§cUse \"/f sair\"."); return; }

        final User user = USER_CONTROLLER.fetchByName(player.getName());

        if (!user.hasFaction()) { player.sendMessage("§cVocê não faz parte de uma facção."); return; }

        if (user.getRole().equals(Role.OWNER)) { player.sendMessage("§cO dono da facção deve desfaze-la."); return; }

        new LeftInventory(player, user.getFactionTag());

    }
}
