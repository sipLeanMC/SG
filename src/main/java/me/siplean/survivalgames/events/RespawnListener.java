package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.GameState;
import me.siplean.survivalgames.manager.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    private final SurvivalGames plugin;

    public RespawnListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        Bukkit.getOnlinePlayers().forEach(all -> {
            if(!all.getName().equalsIgnoreCase(p.getName())) {
                all.hidePlayer(p);
                if(!this.plugin.ingamePlayers.contains(all)) {
                    p.hidePlayer(all);
                }
            }
        });

        p.setGameMode(GameMode.ADVENTURE);
        e.setRespawnLocation(this.plugin.getLocManager().getLobby());
        p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§a§lTeleporter").build());
        p.setAllowFlight(true);
        p.setFlying(true);
    }

}
