package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryListener implements Listener {

    private final SurvivalGames plugin;

    public InventoryListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(!this.plugin.ingamePlayers.contains(p)) {
            e.setCancelled(true);
        }
        if(e.getSlotType() != InventoryType.SlotType.CONTAINER)return;
        if(e.getClickedInventory().getName().equalsIgnoreCase("§eSpectator")) {
            e.setCancelled(true);
            if(e.getSlotType() == InventoryType.SlotType.CONTAINER) {
                if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null)return;
                if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                    String name = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§e", "");
                    Player target = Bukkit.getPlayer(name);
                    if(target == null) {
                        if(!this.plugin.ingamePlayers.contains(p)) {
                            p.sendMessage(this.plugin.prefix + "§3Der §eSpieler §3ist leider §egestorben");
                        } else {
                            p.sendMessage(this.plugin.prefix + "§3Der §eSpieler §3ist nicht §eonline");
                        }
                    } else {
                        p.sendMessage(this.plugin.prefix + "§3Du hast dich zu §e" + name + " §3teleportiert");
                        p.teleport(target);
                        p.setAllowFlight(true);
                        p.setFlying(true);
                    }
                    p.closeInventory();
                }
            }
        }
    }
}
