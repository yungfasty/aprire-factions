package com.github.akafasty.aprire.subcommands.impl;

import com.github.akafasty.aprire.enums.Role;
import com.github.akafasty.aprire.factions.FactionData;
import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.users.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PromoteCommand extends SubCommand {

    public PromoteCommand() {
        super("promote", "promover");
    }

    public void execute(Player player, String[] arguments) {

        if (arguments.length != 2) { player.sendMessage("§cUse \"/f promover <jogador>\"."); }

        User user = USER_CONTROLLER.fetchByName(player.getName());

        if (!user.hasFaction()) { player.sendMessage("§cVocê não faz parte de uma facção."); return; }

        if (user.getRole().getValue() < Role.OFFICER.getValue()) { player.sendMessage("§cSua facção não deixa você fazer isso..."); return; }

        FactionData faction = user.getFaction();
        User targetUser = faction.getMembers().stream()
                .filter(context -> context.getName().equalsIgnoreCase(arguments[1]))
                .findFirst().orElse(null);

        if (targetUser == null) { player.sendMessage("§cEste jogador não pertence a sua facção!"); return; }

        if (targetUser.getRole().getValue() >= user.getRole().getValue()) { player.sendMessage("§cVocê não pode promover este jogador."); return; }

        int value = targetUser.getRole().getValue(); ++value;
        Role role = Role.fromInt(value);

        targetUser.setRoleStr(role.name());

        faction.getMembers().forEach(each -> {

            Player eachPlayer = Bukkit.getPlayer(each.getName());

            if (eachPlayer != null) eachPlayer.sendMessage(String.format("§aO jogador %s foi promovido para o cargo %s.", targetUser.getName(), role.getName()));

        });

        Bukkit.getPlayer(targetUser.getName()).sendMessage(String.format("§aVocê foi promovido para o cargo %s.", role.getName()));

    }
}
