package me.siplean.survivalgames.events;

import me.siplean.survivalgames.SurvivalGames;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    private final SurvivalGames plugin;

    public MoveListener(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(this.plugin.move == false) {
            Location from = e.getFrom();
            Location to = e.getTo();
            double x = Math.floor(from.getX());
            double z = Math.floor(from.getZ());
            if(Math.floor(to.getX())!=x||Math.floor(to.getZ())!=z){
                x+=.5;
                z+=.5;
                p.teleport(new Location(from.getWorld(),x,from.getY(),z,from.getYaw(),from.getPitch()));
            }
        }
    }
}
