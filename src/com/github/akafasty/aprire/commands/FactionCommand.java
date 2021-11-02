package com.github.akafasty.aprire.commands;

import com.github.akafasty.aprire.subcommands.SubCommand;
import com.github.akafasty.aprire.subcommands.impl.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class FactionCommand extends Command {

    public static List<SubCommand> COMMANDS;

    @SneakyThrows
    public FactionCommand() {
        super("faction");
        setAliases(Arrays.asList("f", "factions"));

        COMMANDS = Lists.newArrayList();
        COMMANDS.add(new HelpSubcmd());
        COMMANDS.add(new CreateCommand());
        COMMANDS.add(new DisbandCommand());
        COMMANDS.add(new DemoteCommand());
        COMMANDS.add(new PromoteCommand());
        COMMANDS.add(new KickCommand());
        COMMANDS.add(new InviteCommand());
        COMMANDS.add(new JoinCommand());
        COMMANDS.add(new InvitesCommand());
        COMMANDS.add(new MembersCommand());
        COMMANDS.add(new LeftCommand());

        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        SimpleCommandMap simpleCommandMap = craftServer.getCommandMap();

        simpleCommandMap.register("f", this);

    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] arguments) {

        if (commandSender instanceof ConsoleCommandSender) return true;

        Player player = (Player) commandSender;

        if (arguments.length == 0) {

            SubCommand subCommand = COMMANDS.stream()
                    .filter(context -> context.getCommand().equals("help"))
                    .findFirst().orElse(null);

            subCommand.execute(player, arguments);

            return true;
        }

        String rawCommand = arguments[0].toLowerCase();
        SubCommand subCommand = COMMANDS.stream()
                .filter(context -> context.getCommand().equals(rawCommand) || context.getAliases().contains(rawCommand))
                .findFirst().orElse(null);

        if (subCommand != null) subCommand.execute(player, arguments);

        else player.sendMessage(String.format("§cComando %s não encontrado.", rawCommand));

        return false;
    }
}
