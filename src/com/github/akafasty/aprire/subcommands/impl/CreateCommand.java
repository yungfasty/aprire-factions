package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.entity.Player;

public class CreateCommand extends SubCommand {

    public CreateCommand() { super("create", "criar"); }

    public void execute(Player player, String[] arguments) {

        if (arguments.length != 3) { player.sendMessage("§cUse \"/f criar <tag> <nome>\"."); return; }

        final User user = USER_CONTROLLER.fetchByName(player.getName());

        if (user.hasFaction()) { player.sendMessage("§cVocê já faz parte de uma facção."); return; }

        final String tag = arguments[1].toUpperCase();

        if (tag.length() != 2 && tag.length() != 3) { player.sendMessage("§cA tag da facção deve conter somente 2 ou 3 caracteres."); return; }

        if (FACTION_CONTROLLER.isTagTaken(tag)) { player.sendMessage(String.format("§cA tag \"%s\" já se encontra em uso!", tag)); return; }

        final String name = arguments[2];

        if (FACTION_CONTROLLER.isNameTaken(name)) { player.sendMessage(String.format("§cO nome \"%s\" já se encontra em uso!", tag)); return; }

        if (name.length() > 12 || name.length() < 5) { player.sendMessage("§cO nome da facção deve conter entre 5 e 12 caracteres."); return; }

        user.setFactionTag(tag);
        user.setRoleStr(Role.OWNER.name());

        FACTION_CONTROLLER.insert(FactionData.builder()
                .name(name)
                .tag(tag)
                .build());

        player.sendMessage("§aYAY! Facção criada com sucesso!");

    }
}