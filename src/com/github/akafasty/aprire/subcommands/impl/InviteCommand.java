package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.inventories.InviteInventory;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;

public class InviteCommand extends SubCommand {

    public InviteCommand() {
        super("invite", "convidar");
    }

    public void execute(Player player, String[] arguments) {

        if (arguments.length != 2) { player.sendMessage("§cUse \"/f convidar <jogador>\"."); return; }

        User user = USER_CONTROLLER.fetchByName(player.getName());

        if (!user.hasFaction()) { player.sendMessage("§cVocê não faz parte de uma facção."); return; }

        if (user.getRole().getValue() < Role.OFFICER.getValue()) { player.sendMessage("§cSua facção não deixa você fazer isso..."); return; }

        User targetUser = USER_CONTROLLER.fetchByName(arguments[1]);

        if (targetUser == null) { player.sendMessage("§cEste jogador não foi encontrado!"); return; }

        if (targetUser.hasFaction()) { player.sendMessage("§cEste jogador já faz parte de uma facção."); return; }

        new InviteInventory(player, user.getFactionTag(), targetUser.getName());

    }
}
