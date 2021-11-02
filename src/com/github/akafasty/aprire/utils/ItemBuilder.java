package com.github.akafasty.aprire.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(ItemStack item) {
        this.itemStack = item;
        this.itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder(Material material, int quantity) {
        this(new ItemStack(material, quantity));
    }

    public ItemBuilder(Material material, int quantity, int data) {
        this(new ItemStack(material, quantity, (short) data));
    }

    public ItemBuilder(String texture) {
        ItemStack skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if (texture == null || texture.isEmpty()) {
            this.itemStack = skullItem;
            return;
        }

        if (!texture.startsWith("http://textures.minecraft.net/texture/"))
            texture = "http://textures.minecraft.net/texture/" + texture;

        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
        GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(texture.getBytes()), null);

        profile.getProperties().put("textures", new Property(
                "textures", new String(Base64.encodeBase64(String.format(
                "{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes()))));

        Field field = null;
        try {
            field = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(field).setAccessible(true);

        try {
            field.set(skullMeta, profile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.itemMeta = skullMeta;
        skullItem.setItemMeta(skullMeta);
        this.itemStack = skullItem;
    }

    public ItemBuilder setQuantity(int quantity) {
        this.itemStack.setAmount(quantity);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.itemMeta.setLore(lore.stream()
                .map(l -> ChatColor.translateAlternateColorCodes('&', l))
                .collect(Collectors.toList()));
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.itemMeta.setLore(Arrays.stream(lore)
                .map(l -> ChatColor.translateAlternateColorCodes('&', l))
                .collect(Collectors.toList()));
        return this;
    }

    public ItemBuilder addLore(List<String> lore) {
        List<String> list = this.itemMeta.getLore() == null
                ? new ArrayList<>()
                : this.itemMeta.getLore();

        lore.stream()
                .map(l -> ChatColor.translateAlternateColorCodes('&', l))
                .forEach(list::add);

        this.itemMeta.setLore(list);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        List<String> list = this.itemMeta.getLore() == null
                ? new ArrayList<>()
                : this.itemMeta.getLore();

        Arrays.stream(lore)
                .map(l -> ChatColor.translateAlternateColorCodes('&', l))
                .forEach(list::add);

        this.itemMeta.setLore(list);
        return this;
    }

    public ItemBuilder setGlow(boolean b) {
        if (b) {
            this.itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
            this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.itemMeta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean value) {
        this.itemMeta.spigot().setUnbreakable(value);
        return this;
    }

    public ItemBuilder setOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta) this.itemMeta;
        skullMeta.setOwner(owner);

        this.itemMeta = skullMeta;
        return this;
    }

    public ItemBuilder addNBTTags(NBTFactory... nbtFactories) {
        net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(this.itemStack);

        NBTTagCompound compound = stack.hasTag() ? stack.getTag() : new NBTTagCompound();

        for (NBTFactory nbtFactory : nbtFactories) {
            if (nbtFactory.getValue() instanceof String)
                compound.setString(nbtFactory.getKey(), (String) nbtFactory.getValue());

            if (nbtFactory.getValue() instanceof Integer)
                compound.setInt(nbtFactory.getKey(), (Integer) nbtFactory.getValue());

            if (nbtFactory.getValue() instanceof Double)
                compound.setDouble(nbtFactory.getKey(), (Double) nbtFactory.getValue());

            if (nbtFactory.getValue() instanceof Boolean)
                compound.setBoolean(nbtFactory.getKey(), (Boolean) nbtFactory.getValue());

            if (nbtFactory.getValue() instanceof Long)
                compound.setLong(nbtFactory.getKey(), (Long) nbtFactory.getValue());

            if (nbtFactory.getValue() instanceof Byte)
                compound.setByte(nbtFactory.getKey(), (Byte) nbtFactory.getValue());

            if (nbtFactory.getValue() instanceof Short)
                compound.setShort(nbtFactory.getKey(), (Short) nbtFactory.getValue());
        }


        stack.setTag(compound);
        this.itemStack = CraftItemStack.asBukkitCopy(stack);
        return this;
    }

    public ItemBuilder setDurability(short amount) {

        this.itemStack.setDurability(amount);

        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}
