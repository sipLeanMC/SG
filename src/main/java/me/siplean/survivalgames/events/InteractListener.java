package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.GameState;
import me.siplean.survivalgames.manager.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    private final SurvivalGames plugin;

    public InteractListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        try {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                if(this.plugin.getGameState() == GameState.INGAME && this.plugin.move || this.plugin.getGameState() == GameState.DEATHMATCH && this.plugin.move) {
                    if(this.plugin.ingamePlayers.contains(p)) {
                        e.setCancelled(true);
                        e.getClickedBlock().setType(Material.AIR);
                        p.playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
                        p.getInventory().addItem(this.plugin.getItemManager().getRandomRare());
                        p.updateInventory();
                        p.getInventory().addItem(this.plugin.getItemManager().getRandomRare());
                        p.updateInventory();
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            } else if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CHEST) {
                if(this.plugin.ingamePlayers.contains(p)) {
                    e.setCancelled(true);
                    e.getClickedBlock().setType(Material.AIR);
                    p.playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
                    p.getInventory().addItem(this.plugin.getItemManager().getRandomCommon());
                    p.updateInventory();
                    p.getInventory().addItem(this.plugin.getItemManager().getRandomCommon());
                    p.updateInventory();
                } else {
                    e.setCancelled(true);
                }
            } else if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lTeleporter")) {
                ItemStack i = p.getItemInHand();
                if(i == null || i.getItemMeta() == null || i.getType() == null || i.getItemMeta().getDisplayName() == null || i.getData() == null)return;
                if (!this.plugin.ingamePlayers.contains(p)) {
                    e.setCancelled(true);
                    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                        Inventory inv = null;
                        if (this.plugin.ingamePlayers.size() > 18) {
                            inv = Bukkit.createInventory(null, 27, "§eSpectator");
                        } else if (this.plugin.ingamePlayers.size() > 9) {
                            inv = Bukkit.createInventory(null, 18, "§eSpectator");
                        } else {
                            inv = Bukkit.createInventory(null, 9, "§eSpectator");
                        }
                        int slot = 0;
                        for (Player all : this.plugin.ingamePlayers) {
                            slot++;
                            inv.setItem(slot - 1, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(all.getName()).setName("§e" + all.getName()).build());
                        }
                        p.openInventory(inv);
                    });
                }
            }
        } catch(NullPointerException ex){}
    }
}
