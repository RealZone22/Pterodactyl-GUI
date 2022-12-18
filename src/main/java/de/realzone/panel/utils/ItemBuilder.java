package de.realzone.panel.utils;/*
 
Class Created by: RealZone22
 
Package: eu.zonecraft.core.utils
Project: ZoneCraft-Core
 
Date: 16.11.2022
Time: 08:48
    
*/

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {
    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder(Material mat) {
        itemStack = new ItemStack(mat);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayname(String s) {
        itemMeta.setDisplayName(s);
        return this;
    }

    public ItemBuilder setLocalizedName(String s) {
        itemMeta.setLocalizedName(s);
        return this;
    }

    public ItemBuilder setLore(String... s) {
        itemMeta.setLore(Arrays.asList(s));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean s) {
        itemMeta.setUnbreakable(s);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... s) {
        itemMeta.addItemFlags(s);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchants, int level) {
        itemMeta.addEnchant(enchants, level, true);
        return this;
    }


    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
