package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class save {
    ConfigReader confReader;
    public save(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command.");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("Not enough arguments. Check syntax with '/ckits help save'");
            return true;
        }

        if (Objects.equals(args[1], "default")) {
            if (args.length < 3) {
                sender.sendMessage("Not enough arguments. Check syntax with '/ckits help save'");
                return true;
            }
            String kitName = args[2];
            int kitIndex = 99;
            if (args.length > 3) {
                try {
                    kitIndex = Integer.parseInt(args[3]);
                    kitIndex--;
                } catch (NumberFormatException ignored) {}
            }

            int saveResult = confReader.saveDefaultKit((Player) sender, kitName, kitIndex);
            if (saveResult == -1) {
                sender.sendMessage("Could not find collection of '"+kitName+"'");
            } else if (saveResult == -2) {
                sender.sendMessage("Saved kit '"+kitName+"' as your first kit");
            } else if (saveResult == -3) {
                sender.sendMessage("Could not find kit '"+kitName+"'");
            } else if (saveResult == -4) {
                sender.sendMessage("Saved kit '"+kitName+"' to a new kit");
            } else {
                saveResult++;
                sender.sendMessage("Saved kit '"+kitName+"' to kit slot "+saveResult);
            }

        } else if (Objects.equals(args[1], "current")) {
            if (args.length < 3) {
                confReader.saveCurrentKit((Player) sender);
                sender.sendMessage("Saved current inventory to a new kit.");
            } else {
                int kitIndex;
                try {
                    kitIndex = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("Invalid input. please insert a number.");
                    return true;
                }
                kitIndex--;
                confReader.saveCurrentKit((Player) sender, kitIndex);
                kitIndex++;
                sender.sendMessage("Saved current inventory to kit slot "+kitIndex);
            }
        } else if (Objects.equals(args[1], "global")) {
            if (args.length < 3) {
                sender.sendMessage("Not enough arguments. Check syntax with '/ckits help save'");
                return true;
            }
            String globalPlayerName = args[2];
            OfflinePlayer globalPlayer = Bukkit.getServer().getOfflinePlayer(globalPlayerName);
            int kitIndex = 0;
            int toKitIndex = 99;
            if (args.length >= 4) {
                try {
                    kitIndex = Integer.parseInt(args[3]);
                    kitIndex--;
                } catch (NumberFormatException ignored) {}
            }
            if (args.length >= 5) {
                try {
                    toKitIndex = Integer.parseInt(args[4]);
                    toKitIndex--;
                } catch (NumberFormatException ignored) {}
            }
            int saveGlobalResult = confReader.saveGlobalKit(globalPlayer, (Player) sender, kitIndex, toKitIndex);


            if (saveGlobalResult == -1) {
                sender.sendMessage("The selected player doesn't have any kits.");
            } else if (saveGlobalResult == -2) {
                sender.sendMessage("Saved "+globalPlayer.getName()+"'s kit as a new kit");
            } else {
                saveGlobalResult++;
                sender.sendMessage("Saved "+globalPlayer.getName()+"'s kit slot "+saveGlobalResult);
            }

        } else {
            sender.sendMessage("Cannot Find Category. Check syntax with '/ckits help save'");
        }

        confReader.writeConfig();
        return true;
    }
}
