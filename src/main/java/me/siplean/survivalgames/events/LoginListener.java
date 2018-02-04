package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoginListener implements Listener {

    private final SurvivalGames plugin;

    public LoginListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(this.plugin.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() < this.plugin.maxPlayers) {
            e.allow();
        } else if(this.plugin.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() >= this.plugin.maxPlayers) {
           if(e.getPlayer().hasPermission("kusche.join.full")) {
                try {
                    Player target = kickRandom();
                    target.kickPlayer("§cDu wurdest gekickt, um einem Premium Platz zu machen");
                    e.allow();
                } catch (NullPointerException ex) {
                    e.getPlayer().sendMessage("§cEs konnte kein Spieler gekickt werden");
                }
           } else {
               e.disallow(null, "§cUm volle Server zu betreten, brauchst du Premium");
           }
        } else if(this.plugin.getGameState() == GameState.INGAME) {
            e.allow();
        } else if(this.plugin.getGameState() == GameState.DEATHMATCH) {
            if(e.getPlayer().hasPermission("kuschel.team")) {
                e.allow();
            } else {
                e.disallow(null, "§cDu darfst den Server nicht betreten");
            }
        } else if(this.plugin.getGameState() == GameState.RESTARTING) {
            e.disallow(null, "§cDu darfst den Server nicht betreten");
        }
    }

    private Player kickRandom() {
        List<Player> noPerms = new ArrayList<>();
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(!all.hasPermission("kusche.join.full")) {
                noPerms.add(all);
            }
        }
        if(noPerms.size() == 0) {
            return null;
        } else {
            int random = new Random().nextInt(noPerms.size());
            return noPerms.get(random);
        }
    }
}
