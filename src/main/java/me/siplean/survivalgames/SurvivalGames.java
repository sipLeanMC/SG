package me.siplean.survivalgames;

import me.siplean.survivalgames.chest.ItemManager;
import me.siplean.survivalgames.cmds.StartCMD;
import me.siplean.survivalgames.events.*;
import me.siplean.survivalgames.manager.CountdownManager;
import me.siplean.survivalgames.manager.GameState;
import me.siplean.survivalgames.setup.LocManager;
import me.siplean.survivalgames.setup.SurvivalGamesCMD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SurvivalGames extends JavaPlugin {

    public String prefix, noPerms, console;
    public List<Player> ingamePlayers;
    public int minPlayers, maxPlayers, minDM;
    public boolean grace, move;

    private GameState gameState;
    private LocManager locManager;
    private CountdownManager countdownManager;
    private ItemManager itemManager;

    @Override
    public void onEnable() {
        this.init();
    }

    @Override
    public void onDisable() {
    }

    private void init() {
        this.prefix = "§6LuckSG§8│ §r";
        this.noPerms = this.prefix + "§cDu hast keine Berechtigung";
        this.console = this.prefix + "§cDieser Befehl ist nicht für die Konsole";
        this.ingamePlayers = new ArrayList<>();
        this.minPlayers = 3;
        this.maxPlayers = 24;
        this.minDM = 2;
        this.grace = true;
        this.move = true;

        this.gameState = GameState.LOBBY;
        this.locManager = new LocManager(this);
        this.countdownManager = new CountdownManager(this);
        this.itemManager = new ItemManager(this);

        new SurvivalGamesCMD(this);
        new StartCMD(this);

        new JoinListener(this);
        new QuitListener(this);
        new BuildListener(this);
        new MoveListener(this);
        new DeathListener(this);
        new DamageListener(this);
        new InteractListener(this);
        new RespawnListener(this);
        new InventoryListener(this);
        new PingListener(this);
        new LoginListener(this);

        Bukkit.getWorld("world").setAutoSave(false);
    }

    public LocManager getLocManager() {
        return locManager;
    }

    public CountdownManager getCountdownManager() {
        return countdownManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void checkPlayers() {
        if(this.getGameState() == GameState.INGAME || this.getGameState() == GameState.DEATHMATCH) {
            if(this.ingamePlayers.size() == 1) {
                for(Player winner : this.ingamePlayers) {
                    Bukkit.broadcastMessage(this.prefix + "§3Der Spieler §e" + winner.getName() + " §3hat das §eSpiel §3gewonnen");
                    this.setGameState(GameState.RESTARTING);
                    Bukkit.getScheduler().cancelAllTasks();
                    this.getCountdownManager().startRestartingCountdown();
                }
            } else if(this.ingamePlayers.size() <= this.minDM && this.getGameState() != GameState.DEATHMATCH) {
                this.setGameState(GameState.DEATHMATCH);
                this.getCountdownManager().startDeathmatchCountdown();
            }
        }
    }
}
