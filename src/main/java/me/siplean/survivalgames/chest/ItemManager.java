package me.siplean.survivalgames.chest;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemManager {

    private final SurvivalGames plugin;

    public List<ItemStack> common, rare;

    public ItemManager(SurvivalGames plugin) {
        this.plugin = plugin;
        this.common = new ArrayList<>();
        this.rare = new ArrayList<>();
        this.common.add(new ItemBuilder(Material.CARROT, 2).build());
        this.common.add(new ItemBuilder(Material.APPLE, 2).build());
        this.common.add(new ItemBuilder(Material.CAKE, 1).build());
        this.common.add(new ItemBuilder(Material.PORK, 1).build());
        this.common.add(new ItemBuilder(Material.GRILLED_PORK, 2).build());
        this.common.add(new ItemBuilder(Material.RAW_BEEF, 3).build());
        this.common.add(new ItemBuilder(Material.COOKED_BEEF, 2).build());
        this.common.add(new ItemBuilder(Material.WOOD_SWORD, 1).build());
        this.common.add(new ItemBuilder(Material.WOOD_AXE, 1).build());
        this.common.add(new ItemBuilder(Material.STICK, 1).build());
        this.common.add(new ItemBuilder(Material.GOLD_INGOT, 1).build());
        this.common.add(new ItemBuilder(Material.WHEAT, 4).build());
        this.common.add(new ItemBuilder(Material.LEATHER_HELMET, 1).build());
        this.common.add(new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).build());
        this.common.add(new ItemBuilder(Material.LEATHER_LEGGINGS, 1).build());
        this.common.add(new ItemBuilder(Material.LEATHER_BOOTS, 1).build());
        this.common.add(new ItemBuilder(Material.STONE_SWORD, 1).build());
        this.common.add(new ItemBuilder(Material.GOLD_SWORD, 1).build());
        this.common.add(new ItemBuilder(Material.GOLD_HELMET, 1).build());
        this.common.add(new ItemBuilder(Material.GOLD_CHESTPLATE, 1).build());
        this.common.add(new ItemBuilder(Material.GOLD_LEGGINGS, 1).build());
        this.common.add(new ItemBuilder(Material.GOLD_BOOTS, 1).build());
        this.common.add(new ItemBuilder(Material.SNOW_BALL, 4).build());
        this.common.add(new ItemBuilder(Material.TNT, 1).build());
        this.common.add(new ItemBuilder(Material.WEB, 1).build());
        this.common.add(new ItemBuilder(Material.FEATHER, 2).build());

        this.rare.add(new ItemBuilder(Material.GOLDEN_APPLE, 1).build());
        this.rare.add(new ItemBuilder(Material.STONE_SWORD, 1).build());
        this.rare.add(new ItemBuilder(Material.IRON_SWORD, 1).build());
        this.rare.add(new ItemBuilder(Material.BOW, 1).build());
        this.rare.add(new ItemBuilder(Material.FISHING_ROD, 1).build());
        this.rare.add(new ItemBuilder(Material.ARROW, 4).build());
        this.rare.add(new ItemBuilder(Material.IRON_HELMET, 1).build());
        this.rare.add(new ItemBuilder(Material.IRON_CHESTPLATE, 1).build());
        this.rare.add(new ItemBuilder(Material.IRON_LEGGINGS, 1).build());
        this.rare.add(new ItemBuilder(Material.IRON_BOOTS, 1).build());
        this.rare.add(new ItemBuilder(Material.CHAINMAIL_HELMET, 1).build());
        this.rare.add(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, 1).build());
        this.rare.add(new ItemBuilder(Material.CHAINMAIL_LEGGINGS, 1).build());
        this.rare.add(new ItemBuilder(Material.CHAINMAIL_BOOTS, 1).build());
        this.rare.add(new ItemBuilder(Material.IRON_INGOT, 1).build());
        this.rare.add(new ItemBuilder(Material.DIAMOND, 1).build());
        this.rare.add(new ItemBuilder(Material.CARROT, 2).build());
        this.rare.add(new ItemBuilder(Material.APPLE, 2).build());
        this.rare.add(new ItemBuilder(Material.CAKE, 1).build());
        this.rare.add(new ItemBuilder(Material.PORK, 1).build());
        this.rare.add(new ItemBuilder(Material.GRILLED_PORK, 2).build());
        this.rare.add(new ItemBuilder(Material.RAW_BEEF, 3).build());
        this.rare.add(new ItemBuilder(Material.COOKED_BEEF, 2).build());
        this.rare.add(new ItemBuilder(Material.WOOD_SWORD, 1).build());
        this.rare.add(new ItemBuilder(Material.WOOD_AXE, 1).build());
        this.rare.add(new ItemBuilder(Material.STICK, 1).build());
        this.rare.add(new ItemBuilder(Material.GOLD_INGOT, 1).build());
        this.rare.add(new ItemBuilder(Material.WHEAT, 4).build());
        this.rare.add(new ItemBuilder(Material.LEATHER_HELMET, 1).build());
        this.rare.add(new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).build());
        this.rare.add(new ItemBuilder(Material.LEATHER_LEGGINGS, 1).build());
        this.rare.add(new ItemBuilder(Material.LEATHER_BOOTS, 1).build());
        this.rare.add(new ItemBuilder(Material.STONE_SWORD, 1).build());
        this.rare.add(new ItemBuilder(Material.GOLD_SWORD, 1).build());
        this.rare.add(new ItemBuilder(Material.GOLD_HELMET, 1).build());
        this.rare.add(new ItemBuilder(Material.GOLD_CHESTPLATE, 1).build());
        this.rare.add(new ItemBuilder(Material.GOLD_LEGGINGS, 1).build());
        this.rare.add(new ItemBuilder(Material.GOLD_BOOTS, 1).build());
        this.rare.add(new ItemBuilder(Material.SNOW_BALL, 4).build());
        this.rare.add(new ItemBuilder(Material.TNT, 1).build());
        this.rare.add(new ItemBuilder(Material.WEB, 1).build());
        this.rare.add(new ItemBuilder(Material.FEATHER, 2).build());
    }

    public ItemStack getRandomRare() {
        int random = new Random().nextInt(this.rare.size());
        return this.rare.get(random);
    }

    public ItemStack getRandomCommon() {
        int random = new Random().nextInt(this.common.size());
        return this.common.get(random);
    }
}
