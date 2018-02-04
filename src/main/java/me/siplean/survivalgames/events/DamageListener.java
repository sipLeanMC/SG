package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.GameState;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final SurvivalGames plugin;

    public DamageListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Player p = null;
        Player hitter = null;
        if(e.getDamager() instanceof  Player) {
            p = (Player) e.getEntity();
            hitter = hitter = (Player) e.getDamager();
        }
        if(this.plugin.getGameState() == GameState.LOBBY || this.plugin.getGameState() == GameState.RESTARTING) {
            e.setCancelled(true);
        } else if(this.plugin.getGameState() == GameState.INGAME || this.plugin.getGameState() == GameState.DEATHMATCH) {
            if(!this.plugin.grace && this.plugin.ingamePlayers.contains(p) && hitter == null) {
                e.setCancelled(false);
            } else if(!this.plugin.grace && this.plugin.ingamePlayers.contains(p) && this.plugin.ingamePlayers.contains(hitter)) {
                e.setCancelled(false);
            } else if(this.plugin.grace) {
                e.setCancelled(true);
            } else if(e.getDamager() instanceof Player) {
                if(!this.plugin.ingamePlayers.contains(hitter)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(this.plugin.getGameState() == GameState.LOBBY || this.plugin.getGameState() == GameState.RESTARTING) {
                if(e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                } else if(e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    e.setCancelled(true);
                    p.teleport(this.plugin.getLocManager().getLobby());
                }
            } else {
                if(!this.plugin.ingamePlayers.contains(p)) {
                    e.setCancelled(true);
                } else if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    if(!this.plugin.grace && this.plugin.ingamePlayers.contains(p)) {
                        e.setCancelled(false);
                    }
                }
            }
        }
    }
}
