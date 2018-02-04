package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

    private final SurvivalGames plugin;

    public PingListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMaxPlayers(2);
        if(this.plugin.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() < this.plugin.maxPlayers) {
            e.setMotd("§8[§aLobby§8]");
        } else if(this.plugin.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() >= this.plugin.maxPlayers) {
            e.setMotd("§8[§cFull§8]");
        } else if(this.plugin.getGameState() == GameState.INGAME) {
            e.setMotd("§8[§eIngame§8]");
        } else if(this.plugin.getGameState() == GameState.DEATHMATCH) {
            e.setMotd("§8[§dEnding§8]");
        } else if(this.plugin.getGameState() == GameState.RESTARTING) {
            e.setMotd("§8[§4Restarting§8]");
        }
    }
}
