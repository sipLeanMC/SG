package me.siplean.survivalgames.manager;

import me.siplean.survivalgames.SurvivalGames;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class CountdownManager {

    private final SurvivalGames plugin;

    public int lobbyCountdown, ingameCountdown, graceCountdown, deathmachCountdown, restartingCountdown;
    public int lobbyTask, ingameTask, graceTask, deathmatchTask, restartingTask;

    public CountdownManager(SurvivalGames plugin) {
        this.plugin = plugin;
        this.lobbyCountdown = 31;
        this.ingameCountdown = 23;
        this.graceCountdown = 23;
        this.deathmachCountdown = 33;
        this.restartingCountdown = 18;
    }

    public void startRestartingCountdown() {
        this.restartingTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if(this.restartingCountdown != 0)this.restartingCountdown--;
            if(this.restartingCountdown == 10 || this.restartingCountdown == 3 || this.restartingCountdown == 2 || this.restartingCountdown == 1) {
                Bukkit.broadcastMessage(this.plugin.prefix + "§3Der §eServer §3startet in §e" + this.restartingCountdown + " Sekunden §3neu");
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);});
            } else if(this.restartingCountdown == 0) {
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);});
                Bukkit.broadcastMessage(this.plugin.prefix + "§3Der §eServer §3startet §enun §3neu");
                Bukkit.getScheduler().cancelTask(this.restartingTask);
                Bukkit.shutdown();
            }
        }, 0, 20);
    }

    public void startDeathmatchCountdown() {
        this.deathmatchTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if(this.deathmachCountdown != 0)this.deathmachCountdown--;
            if(this.deathmachCountdown == 30 || this.deathmachCountdown == 20 || this.deathmachCountdown == 10 || this.deathmachCountdown == 3 || this.deathmachCountdown == 2 || this.deathmachCountdown == 1) {
                Bukkit.broadcastMessage(this.plugin.prefix + "§3Das §eDeathmatch §3beginnt in §e" + this.deathmachCountdown + " Sekunden");
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);});
            } else if(this.deathmachCountdown == 0) {
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);});
                Bukkit.broadcastMessage(this.plugin.prefix + "§3Das §eDeathmatch §3startet §enun");
                Bukkit.getScheduler().cancelTask(this.deathmatchTask);
                this.teleportPlayersToDM();
            }
        }, 0, 20);
    }

    public void teleportPlayersToDM() {
        int spawn = 0;
        for(Player all : this.plugin.ingamePlayers) {
            spawn++;
            all.teleport(this.plugin.getLocManager().getDMSpawn(spawn));
        }
    }

    public void startGraceCountdown() {
        this.graceTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if(this.graceCountdown != 0)this.graceCountdown--;
            if(this.graceCountdown == 20 || this.graceCountdown == 10 || this.graceCountdown == 3 || this.graceCountdown == 2 || this.graceCountdown == 2 || this.graceCountdown == 1) {
                Bukkit.broadcastMessage(this.plugin.prefix + "§3Die §eSchutzzeit §3endet in §e" + this.graceCountdown + " Sekunden");
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);});
            } else if(this.graceCountdown == 0) {
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);});
                Bukkit.broadcastMessage(this.plugin.prefix + "§3Die §eSchutzzeit §3endet §enun");
                this.plugin.grace = false;
                Bukkit.getScheduler().cancelTask(this.graceTask);
            }
        }, 0, 20);
    }

    public void startIngameCountdown() {
        this.ingameTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if(this.ingameCountdown != 0)this.ingameCountdown--;
            if(this.ingameCountdown == 20 || this.ingameCountdown == 10 || this.ingameCountdown == 3 | this.ingameCountdown == 2 || this.ingameCountdown == 1) {
                Bukkit.broadcastMessage(this.plugin.prefix + "§eLuckSG §3startet in §e" + this.ingameCountdown + " Sekunden");
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);});
            } else if(this.ingameCountdown == 0) {
                Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);});
                Bukkit.broadcastMessage(this.plugin.prefix + "§eLuckSG §3startet §enun");
                this.plugin.move = true;
                Bukkit.getScheduler().cancelTask(this.ingameTask);
                this.startGraceCountdown();
            }
        }, 0, 20);
    }

    public void startLobbyCountdown() {
        this.lobbyTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if(this.lobbyCountdown != 0)this.lobbyCountdown--;
            if(this.lobbyCountdown < 31) {
                Bukkit.getOnlinePlayers().forEach(all -> {
                    all.setLevel(this.lobbyCountdown);
                    all.setExp((float) this.lobbyCountdown / 31);
                });
            }
            if(this.lobbyCountdown == 30 || this.lobbyCountdown == 20 || this.lobbyCountdown == 10 || this.lobbyCountdown == 3 || this.lobbyCountdown == 2 || this.lobbyCountdown == 1) {
                if(this.checkPlayers()) {
                    Bukkit.broadcastMessage(this.plugin.prefix + "§3Das §eSpiel §3startet in §e" + this.lobbyCountdown + " Sekunden");
                    Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);});
                } else {
                    Bukkit.broadcastMessage(this.plugin.prefix + "§3Es sind zu §ewenig Spieler §3online, bitte warte..");
                    Bukkit.getScheduler().cancelTask(this.lobbyTask);
                    this.lobbyCountdown = 31;
                }
            } else if(lobbyCountdown == 0) {
                if(this.checkPlayers()) {
                    Bukkit.broadcastMessage(this.plugin.prefix + "§3Das §eSpiel §3startet §enun");
                    this.teleportPlayers();
                    Bukkit.getScheduler().cancelTask(this.lobbyTask);
                    this.plugin.setGameState(GameState.INGAME);
                    this.plugin.move = false;
                    this.startIngameCountdown();
                    Bukkit.getOnlinePlayers().forEach(all -> {all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);});
                } else {
                    Bukkit.broadcastMessage(this.plugin.prefix + "§3Es sind zu §ewenig Spieler §3online, bitte warte..");
                    Bukkit.getScheduler().cancelTask(this.lobbyTask);
                    this.lobbyCountdown = 31;
                }
            }
        }, 0, 20);
    }

    public void teleportPlayers() {
        int spawn = 0;
        for(Player all : this.plugin.ingamePlayers) {
            spawn++;
            all.teleport(this.plugin.getLocManager().getSpawn(spawn));
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player all : Bukkit.getOnlinePlayers()) {
                if(!this.plugin.ingamePlayers.contains(all)) {
                    all.teleport(this.plugin.getLocManager().getSpectator());
                    all.setAllowFlight(true);
                    all.setFlying(true);
                }
            }
        }, 3);
    }

    public boolean checkPlayers() {
        if(this.plugin.ingamePlayers.size() >= this.plugin.minPlayers) {
            return true;
        } else {
            return false;
        }
    }
}
