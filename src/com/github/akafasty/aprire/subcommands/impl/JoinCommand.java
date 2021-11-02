package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    public JoinCommand() {
        super("join", "entrar");
    }

    public void execute(Player player, String[] arguments) {

        if (arguments.length != 2) { player.sendMessage("§cUse \"/f entrar <facção>\"."); return; }

        User user = USER_CONTROLLER.fetchByName(player.getName());

        if (user.hasFaction()) { player.sendMessage("§cVocê já faz parte de uma facção."); return; }

        FactionData faction = null;

        if (FACTION_CONTROLLER.isTagTaken(arguments[1])) faction = FACTION_CONTROLLER.fetchByTag(arguments[1]);
        if (FACTION_CONTROLLER.isNameTaken(arguments[1])) faction = FACTION_CONTROLLER.fetchByName(arguments[1]);

        if (faction == null) { player.sendMessage("§cEsta facção não existe."); return; }

        if (!user.getInvites().contains(faction.getTag())) { player.sendMessage("§cVocê não foi convidado para esta facção."); return; }

        if (faction.getMembers().size() >= 15) { player.sendMessage("§cEsta facção está lotada!"); return; }

        user.setRoleStr(Role.RECRUIT.name());
        user.setFactionTag(faction.getTag());

    }
}
