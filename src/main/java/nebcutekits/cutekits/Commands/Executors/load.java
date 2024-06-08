package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class load {
    ConfigReader confReader;
    public load(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command.");
            return true;
        }

        if (Objects.equals(args[1], "default")) {
            if (args.length < 3) {
                sender.sendMessage("Not enough arguments. Check syntax with '/ckits help load'");
                return true;
            }

            String kitName = args[2];
            int loadResult = confReader.loadDefaultKit((Player) sender, kitName);

            if (loadResult == -1) {
                sender.sendMessage("Couldn't find default kit collection.");
            } else if (loadResult == -2) {
                sender.sendMessage("Couldn't find default kit from kitName.");
            } else {
                sender.sendMessage("Loaded kit '"+kitName+"'");
                if (confReader.getPlayerCollectionIndex((Player) sender) == -1) {
                    int saveResult = confReader.saveDefaultKitToPlayerKit((Player) sender, kitName, 99);
                    if (saveResult == -3) {
                        sender.sendMessage("Since this is your first time loading a default kit, The kit is automatically saved.");
                        sender.sendMessage("To get the same kit in the future, type '/ckits load personal 1'. Learn more with '/ckits help'");
                    }
                }
                Bukkit.broadcastMessage(((Player) sender).getPlayer().getName()+" Loaded Default kit '"+kitName+"'");
            }

        } else if (Objects.equals(args[1], "personal")) {
            int kitIndex = 0;
            if (args.length >= 3) {
                try {
                    kitIndex = Integer.parseInt(args[2]);
                    kitIndex--;
                } catch (NumberFormatException ignored) {}
            }
            int loadResult = confReader.loadPersonalKit((Player) sender, kitIndex);
            if (loadResult == -1) {
                sender.sendMessage("You don't have a kits collection. Learn how to make one with '/ckits help save'");
            } else if (loadResult == -2) {
                sender.sendMessage("You don't have any kits. Learn how to make one with '/ckits help save'");
            } else {
                loadResult++;
                sender.sendMessage("Loaded personal kit "+loadResult);
                Bukkit.broadcastMessage(((Player) sender).getPlayer().getName()+" Loaded their kit slot "+loadResult);
            }

        } else if (Objects.equals(args[1], "global")) {
            if (args.length < 3) {
                sender.sendMessage("Not enough arguments. Check syntax with '/ckits help load'");
                return true;
            }
            String globalPlayerName = args[2];
            Player globalPlayer = Bukkit.getServer().getPlayer(globalPlayerName);
            if (globalPlayer == null) {
                globalPlayer = Bukkit.getServer().getOfflinePlayer(globalPlayerName).getPlayer();
            }
            if (globalPlayer == null) {
                sender.sendMessage("Global Player not found.");
                return true;
            }

            int kitIndex = 0;
            if (args.length >= 4) {
                try {
                    kitIndex = Integer.parseInt(args[3]);
                    kitIndex--;
                } catch (NumberFormatException ignored) {}
            }
            int loadResult = confReader.loadGlobalKit(globalPlayer, (Player) sender, kitIndex);

            if (loadResult == -1) {
                sender.sendMessage("The selected player doesn't have any kits.");
            } else {
                loadResult++;
                sender.sendMessage("Loaded "+globalPlayer.getName()+"'s kit slot "+loadResult);
                Bukkit.broadcastMessage(((Player) sender).getPlayer().getName()+" Loaded "+globalPlayer.getName()+"'s kit slot "+loadResult);
            }

        } else {
            sender.sendMessage("Cannot Find Category. Check syntax with '/ckits help save'");
        }

        return true;
    }
}
