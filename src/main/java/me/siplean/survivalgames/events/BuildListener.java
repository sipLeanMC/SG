package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.GameState;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class BuildListener implements Listener {

    private final SurvivalGames plugin;

    public BuildListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent e) {
        if(this.plugin.getGameState() == GameState.LOBBY || this.plugin.getGameState() == GameState.RESTARTING) {
            e.setCancelled(true);
        } else {
            if(e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                if(!this.plugin.ingamePlayers.contains(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
           e.setCancelled(false);
        } else {
            if(this.plugin.getGameState() == GameState.INGAME || this.plugin.getGameState() == GameState.DEATHMATCH) {
                if(e.getBlock().getType() == Material.WEB) {
                    e.setCancelled(false);
                } else if(e.getBlock().getType() == Material.CAKE_BLOCK) {
                    e.setCancelled(false);
                } else if(e.getBlock().getType() == Material.TNT) {
                    e.setCancelled(false);
                    e.getBlock().setType(Material.AIR);
                    TNTPrimed tnt = (TNTPrimed) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
                    tnt.setFuseTicks(30);
                    tnt.setFireTicks(10);
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        e.blockList().clear();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            e.setCancelled(false);
        } else {
            if(this.plugin.getGameState() == GameState.INGAME || this.plugin.getGameState() == GameState.DEATHMATCH) {
                if(e.getBlock().getType() == Material.WEB) {
                    e.setCancelled(false);
                } else if(e.getBlock().getType() == Material.CAKE_BLOCK) {
                    e.setCancelled(false);
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(this.plugin.getGameState() == GameState.RESTARTING || this.plugin.getGameState() == GameState.LOBBY || !this.plugin.ingamePlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if(this.plugin.getGameState() == GameState.RESTARTING || this.plugin.getGameState() == GameState.LOBBY || !this.plugin.ingamePlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }
}
