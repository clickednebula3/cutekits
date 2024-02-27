package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class admin_load {
    ConfigReader confReader;
    public admin_load(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin load'");
            return true;
        }

        Player player = Bukkit.getServer().getPlayer(args[1]);
        if (player == null) {
            player = Bukkit.getServer().getOfflinePlayer(args[1]).getPlayer();
        }
        if (player == null) {
            sender.sendMessage("Global Player not found.");
            return true;
        }

        if (Objects.equals(args[2], "fromDefault")) {
            String kitName = args[3];
            int loadResult = confReader.loadDefaultKit(player, kitName);

            if (loadResult == -1) {
                sender.sendMessage("Couldn't find default kit collection.");
            } else if (loadResult == -2) {
                sender.sendMessage("Couldn't find default kit from kitName.");
            } else {
                sender.sendMessage("Loaded kit '"+kitName+"' onto "+player.getName());
            }

        } else if (Objects.equals(args[2], "fromGlobal")) {
            Player globalPlayer = Bukkit.getServer().getPlayer(args[2]);
            if (globalPlayer == null) {
                globalPlayer = Bukkit.getServer().getOfflinePlayer(args[2]).getPlayer();
            }
            if (globalPlayer == null) {
                sender.sendMessage("Global Player not found.");
                return true;
            }
            int kitIndex = 0;
            if (args.length >= 5) {
                try {
                    kitIndex = Integer.parseInt(args[4]);
                    kitIndex--;
                } catch (NumberFormatException ignored) {}
            }
            int loadResult = confReader.loadGlobalKit(globalPlayer, player, kitIndex);

            if (loadResult == -1) {
                sender.sendMessage("The selected player doesn't have any kits.");
            } else {
                loadResult++;
                sender.sendMessage("Loaded "+globalPlayer.getName()+"'s kit slot "+loadResult+" onto "+player.getName());
            }


        } else {
            sender.sendMessage("Invalid arguments. Check syntax with '/ckits help admin load'");
        }

        return true;
    }
}
