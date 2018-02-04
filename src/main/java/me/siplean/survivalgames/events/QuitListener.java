package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final SurvivalGames plugin;

    public QuitListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        e.setQuitMessage(null);
        if(this.plugin.ingamePlayers.contains(p)) {
            this.plugin.ingamePlayers.remove(p);
            this.plugin.checkPlayers();
            e.setQuitMessage(this.plugin.prefix + "§3Der Spieler §e" + p.getName() + " §3hat das §eSpiel §3verlassen §8[§c" + this.plugin.ingamePlayers.size() + "§7/§c24§8]");
        }
    }
}
