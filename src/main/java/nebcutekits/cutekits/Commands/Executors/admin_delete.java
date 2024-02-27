package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class admin_delete {
    ConfigReader confReader;
    public admin_delete(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin delete'");
            return true;
        }

        if (Objects.equals(args[1], "fromDefault")) {
            if (args.length < 4) {
                sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin delete'");
                return true;
            }
            String collectionName = args[2];
            String kitName = args[3];

            int collectionIndex = confReader.getDefaultCollectionIndex(collectionName);
            if (collectionIndex == -1) {
                sender.sendMessage("Couldn't find default kit collection.");
                return true;
            }

            if (Objects.equals(kitName, "all")) {
                confReader.defaultCollections.remove(collectionIndex);
                sender.sendMessage("Deleted entire default collection.");
            } else {
                int kitIndex = confReader.getDefaultKitIndexWithenCollection(kitName, collectionIndex);
                if (kitIndex == -1) {
                    sender.sendMessage("Couldn't find default kit from kitName.");
                    return true;
                }
                if (kitIndex == -2) {
                    sender.sendMessage("Couldn't find default kit inside collection.");
                    return true;
                }
                confReader.defaultCollections.get(collectionIndex).deleteKit(kitIndex);
                sender.sendMessage("Deleted kit '"+kitName+"' from default collection '"+collectionName+"'.");
            }

        } else if (Objects.equals(args[1], "fromGlobal")) {
            Player globalPlayer = Bukkit.getServer().getPlayer(args[2]);
            if (globalPlayer == null) {
                globalPlayer = Bukkit.getServer().getOfflinePlayer(args[2]).getPlayer();
            }
            if (globalPlayer == null) {
                sender.sendMessage("Global Player not found.");
                return true;
            }

            int collectionIndex = confReader.getPlayerCollectionIndex(globalPlayer);
            int kitIndex = 99;
            if (collectionIndex == -1) {
                sender.sendMessage("Could not find the selected player's kit collection.");
                return true;
            }

            if (args.length >= 4) {
                if (Objects.equals(args[3], "all")) {
                    kitIndex = -1;
                } else {
                    try {
                        kitIndex = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Invalid input. please insert a number.");
                        return true;
                    }
                }
                kitIndex--;
            }

            if (kitIndex == -2) {
                confReader.playerCollections.remove(collectionIndex);
                sender.sendMessage("Deleted "+globalPlayer.getName()+"'s entire collection.");
            } else {
                int deleteResult = confReader.playerCollections.get(collectionIndex).deleteKit(kitIndex);
                if (deleteResult == -1) {
                    sender.sendMessage("Selected player doesn't have any kits! Delete entire collection with '/ckadmin delete fromGlobal <player> all'");
                } else {
                    deleteResult++;
                    sender.sendMessage("Deleted kit slot "+deleteResult+" from "+globalPlayer.getName()+"'s collection.");
                }
            }

        } else {
            sender.sendMessage("Invalid arguments. Check syntax with '/ckits help admin save'");
        }

        confReader.writeConfig();
        return true;
    }
}
