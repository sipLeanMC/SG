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
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final SurvivalGames plugin;

    public JoinListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        e.setJoinMessage(null);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setHealth(20);
        p.setFireTicks(0);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setExp(0);
        p.teleport(this.plugin.getLocManager().getLobby());
        Bukkit.getOnlinePlayers().forEach(all -> {
            p.showPlayer(all);
            all.showPlayer(p);
        });
        if(this.plugin.getGameState() == GameState.LOBBY) {
            this.plugin.ingamePlayers.add(p);
            p.setGameMode(GameMode.SURVIVAL);
            e.setJoinMessage(this.plugin.prefix + "§3Der Spieler §e" + p.getName() + " §3hat das §eSpiel §3betreten §8[§c" + this.plugin.ingamePlayers.size() + "§7/§c24§8]");
            if(Bukkit.getOnlinePlayers().size() >= this.plugin.minPlayers && !Bukkit.getScheduler().isCurrentlyRunning(this.plugin.getCountdownManager().lobbyTask)) {
                this.plugin.getCountdownManager().startLobbyCountdown();
            }
        }
        if(this.plugin.getGameState() == GameState.INGAME || this.plugin.getGameState() == GameState.DEATHMATCH) {
            p.setGameMode(GameMode.ADVENTURE);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage(this.plugin.prefix + "§3Du hast das §eSpiel §3als §eSpectator §3betreten");
            p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§a§lTeleporter").build());
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(!all.getName().equalsIgnoreCase(p.getName())) {
                    all.hidePlayer(p);
                    if(!this.plugin.ingamePlayers.contains(all)) {
                        p.hidePlayer(all);
                    }
                }
            });
        }
    }
}
