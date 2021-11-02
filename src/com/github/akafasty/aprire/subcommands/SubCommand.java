package com.github.akafasty.aprire.subcommands;

import com.github.akafasty.aprire.factions.controller.FactionController;
import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.controller.UserController;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;

public abstract class SubCommand {

    public static final UserController USER_CONTROLLER = FactionsPlugin.getPlugin().getUserController();
    public static final FactionController FACTION_CONTROLLER = FactionsPlugin.getPlugin().getFactionController();
    @Getter private final String command;
    @Getter private final Collection<String> aliases;

    public SubCommand(String command, String... aliases) { this.command = command; this.aliases = Arrays.asList(aliases); }

    public abstract void execute(Player player, String[] arguments);

}
