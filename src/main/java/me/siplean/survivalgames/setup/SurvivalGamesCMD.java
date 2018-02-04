package me.siplean.survivalgames.setup;

import me.siplean.survivalgames.SurvivalGames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalGamesCMD implements CommandExecutor {

    private final SurvivalGames plugin;

    public SurvivalGamesCMD(SurvivalGames plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("SurvivalGames").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(this.plugin.console);
            return false;
        }

        if(!sender.isOp()) {
            sender.sendMessage(this.plugin.noPerms);
            return false;
        }

        final Player p = (Player) sender;

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("setLobby")) {
                this.plugin.getLocManager().setLocation(p, "Lobby");
                p.sendMessage(this.plugin.prefix + "§3Der §eSpawn §3für die §eLobby §3wurde gesetzt");
            } else if(args[0].equalsIgnoreCase("setSpectator")) {
                this.plugin.getLocManager().setLocation(p, "Spectator");
                p.sendMessage(this.plugin.prefix + "§3Der §eDeathmatch Spawn §3für die §eSpectator §3wurde gesetzt");
            } else {
                help(p);
            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("setSpawn")) {
                try {
                    int spawn = Integer.parseInt(args[1]);
                    if(spawn > 0 && spawn < 25) {
                        this.plugin.getLocManager().setLocation(p, "spawn." + spawn);
                        p.sendMessage(this.plugin.prefix + "§3Der §eSpawn " + spawn + " §3wurde gesetzt");
                    } else {
                        p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setSpawn <1-24>");
                    }
                } catch (NumberFormatException e) {
                    p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setSpawn <1-24>");
                }
            } else if(args[0].equalsIgnoreCase("setDMSpawn")) {
                try {
                    int spawn = Integer.parseInt(args[1]);
                    if(spawn > 0 && spawn < 5) {
                        this.plugin.getLocManager().setLocation(p, "dmspawn." + spawn);
                        p.sendMessage(this.plugin.prefix + "§3Der §eDeathmatch Spawn " + spawn + " §3wurde gesetzt");
                    } else {
                        p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setDMSpawn <1-4>");
                    }
                } catch (NumberFormatException e) {
                    p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setDMSpawn <1-4>");
                }
            } else {
                help(p);
            }
        } else {
            this.help(p);
        }
        return false;
    }

    private void help(final Player p) {
        p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setLobby");
        p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setSpectator");
        p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setSpawn <1-24>");
        p.sendMessage(this.plugin.prefix + "§e/SurvivalGames setDMSpawn <1-4>");
    }
}
