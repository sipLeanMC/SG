package me.siplean.survivalgames.cmds;

import me.siplean.survivalgames.SurvivalGames;
import me.siplean.survivalgames.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCMD implements CommandExecutor {

    private final SurvivalGames plugin;

    public StartCMD(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("Start").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(this.plugin.console);
            return false;
        }

        if(!sender.isOp()) {
            sender.sendMessage(this.plugin.noPerms);
            return false;
        }

        final Player p = (Player) sender;

        if(this.plugin.getGameState() == GameState.LOBBY) {
            if(Bukkit.getScheduler().isCurrentlyRunning(this.plugin.getCountdownManager().lobbyTask)) {
                if(this.plugin.getCountdownManager().lobbyCountdown > 11) {
                    this.plugin.getCountdownManager().lobbyCountdown = 11;
                    p.sendMessage(this.plugin.prefix + "§3Der §eCountdown §3wurde auf §e10 Sekunden §3verkürzt");
                } else {
                    p.sendMessage(this.plugin.prefix + "§cDas Spiel startet bereits");
                }
            } else {
                p.sendMessage(this.plugin.prefix + "§cDas Spiel hat nicht genügend Spieler");
            }
        } else {
            p.sendMessage(this.plugin.prefix + "§cDas Spiel läuft bereits");
        }

        return false;
    }
}
