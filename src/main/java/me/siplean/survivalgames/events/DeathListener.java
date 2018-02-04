package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeathListener implements Listener {

    private final SurvivalGames plugin;

    private List<String> messages;

    public DeathListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.messages = new ArrayList<>();
        this.messages.add("hat sich das Leben genommen");
        this.messages.add("wurde vom Boden aufgefressen");
        this.messages.add("hat Suizid gemacht");
        this.messages.add("wurde zerleant");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player toter = (Player) e.getEntity().getPlayer();
        Player killer = (Player) toter.getKiller();
        if(this.plugin.ingamePlayers.contains(toter)) {
            this.plugin.ingamePlayers.remove(toter);
            if(killer == null) {
                Bukkit.broadcastMessage(this.plugin.prefix + "§e" + toter.getName() + " §3" + getRandomMessage());
            } else {
                Bukkit.broadcastMessage(this.plugin.prefix + "§e" + toter.getName() + " §3wurde von §e" + killer.getName() + " §3getötet");
            }
            this.plugin.checkPlayers();
            respawnPlayer(toter);
        }
    }

    private String getRandomMessage() {
        int random = new Random().nextInt(this.messages.size());
        return this.messages.get(random);
    }

    private void respawnPlayer(final Player p) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            p.spigot().respawn();
        }, 3);
    }
}
