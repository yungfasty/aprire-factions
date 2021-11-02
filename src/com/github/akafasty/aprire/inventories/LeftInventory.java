package com.github.akafasty.aprire.inventories;

import com.github.akafasty.aprire.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LeftInventory {

    public LeftInventory(Player player, String factionTag) {

        Inventory inventory = Bukkit.createInventory(null, 4*9, String.format("[%s] - Deixar a facção", factionTag));

        inventory.setItem(20, new ItemBuilder(Material.INK_SACK, 1, (short) 10)
                .setDisplayName("§aConfirmar")
                .setLore("§7Clique para deixar sua facção.")
                .build());

        inventory.setItem(13, new ItemBuilder(Material.PAPER)
                .setDisplayName("§eInformações")
                .setLore("§7Você está prestes a deixar sua facção.", "", "§c* Esta ação não pode ser revertida!")
                .build());

        inventory.setItem(24, new ItemBuilder(Material.INK_SACK, 1, (short) 8)
                .setDisplayName("§cCancelar")
                .setLore("§7Clique para cancelar a ação.")
                .build());

        player.openInventory(inventory);

    }

}
