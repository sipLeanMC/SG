package me.siplean.survivalgames.setup;

import me.siplean.survivalgames.SurvivalGames;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class LocManager {

    private final SurvivalGames plugin;

    private File file;
    private FileConfiguration cfg;

    public LocManager(SurvivalGames plugin) {
        this.plugin = plugin;
        this.file = new File("plugins/SurvivalGames", "locations.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.file);
    }

    public void setLocation(final Player p, final String locationName) {
        double x = p.getLocation().getBlockX();
        double y = p.getLocation().getBlockY();
        double z = p.getLocation().getBlockZ();
        double yaw = p.getLocation().getYaw();
        double pitch = p.getLocation().getPitch();

        String world = p.getWorld().getName();

        this.cfg.set("location.x." + locationName, x);
        this.cfg.set("location.y." + locationName, y);
        this.cfg.set("location.z." + locationName, z);
        this.cfg.set("location.yaw." + locationName, yaw);
        this.cfg.set("location.pitch." + locationName, pitch);
        this.cfg.set("location.world." + locationName, world);
        saveConfig();
    }

    public Location getLocation(final String locationName) {
        try {
            double x = this.cfg.getDouble("location.x." + locationName);
            double y = this.cfg.getDouble("location.y." + locationName);
            double z = this.cfg.getDouble("location.z." + locationName);
            double yaw = this.cfg.getDouble("location.yaw." + locationName);
            double pitch = this.cfg.getDouble("location.pitch." + locationName);

            World world = Bukkit.getWorld(this.cfg.getString("location.world." + locationName));

            return new Location(world, x, y, z, (float) yaw, (float) pitch);
        } catch (NullPointerException e) {
            System.out.println("Error: Can't find location: " + locationName);
        }
        return null;
    }

    public void teleportToLocation(final Player p, final Location loc) {
        p.teleport(loc);
    }

    private void saveConfig() {
        try {
            this.cfg.save(this.file);
        } catch (IOException e) {
            System.out.println("Error: Can't save locations.yml file");
        }
    }

    public Location getLobby() {
        return getLocation("Lobby");
    }

    public Location getSpectator() {
        return getLocation("Spectator");
    }

    public Location getSpawn(int spawn) {
        return getLocation("spawn." + spawn);
    }

    public Location getDMSpawn(int spawn) {
        return getLocation("dmspawn." + spawn);
    }
}
