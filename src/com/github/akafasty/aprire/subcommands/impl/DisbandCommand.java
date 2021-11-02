package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.inventories.DisbandInventory;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;

public class DisbandCommand extends SubCommand {

    public DisbandCommand() { super("disband", "desfazer"); }

    public void execute(Player player, String[] arguments) {

        if (arguments.length != 1) { player.sendMessage("§cUse \"/f desfazer\"."); return; }

        final User user = USER_CONTROLLER.fetchByName(player.getName());

        if (!user.hasFaction()) { player.sendMessage("§cVocê não faz parte de uma facção."); return; }

        if (!user.getRole().equals(Role.OWNER)) { player.sendMessage("§cSomente o dono da facção pode desfaze-la."); return; }

        new DisbandInventory(player, user.getFactionTag());

        /**
        final Faction faction = user.getFaction();

        faction.getMembers()
                .forEach(each -> each.setFactionTag(null));

        FACTION_CONTROLLER.remove(faction);

        player.sendMessage("§aFacção desfeita!");

         */

    }
}
