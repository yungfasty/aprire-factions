package com.github.akafasty.aprire.inventories;

import com.github.akafasty.aprire.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KickInventory {

    public KickInventory(Player player, String factionTag, String name) {

        Inventory inventory = Bukkit.createInventory(null, 4*9, String.format("[%s] - Expulsar %s", factionTag, name));

        inventory.setItem(20, new ItemBuilder(Material.INK_SACK, 1, 10)
                .setDisplayName("§aConfirmar")
                .setLore(String.format("§7Clique para expulsar %s de sua facção.", name))
                .build());

        inventory.setItem(13, new ItemBuilder(Material.SKULL_ITEM)
                .setDurability((short) 3)
                .setDisplayName("§eInformações")
                .setLore(String.format("§7Você está prestes a expulsar §f%s§7 de sua facção.", name), "", "§c* Esta ação não pode ser revertida!")
                .setOwner(name)
                .build());

        inventory.setItem(24, new ItemBuilder(Material.INK_SACK, 1, 8)
                .setDisplayName("§cCancelar")
                .setLore("§7Clique para cancelar a ação.")
                .build());

        player.openInventory(inventory);

    }

}
