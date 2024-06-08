package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class view {
    ConfigReader confReader;
    public view(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command.");
            return true;
        }

        List<String> helpMessage = new ArrayList<>();
        helpMessage.add("---- CuteKits Help ----");

        if (args.length > 1) {
            if (Objects.equals(args[1], "personal")) {
                //view all my kits

            } else if (Objects.equals(args[1], "default")) {
                //view server kits

            } else if (Objects.equals(args[1], "global")) {
                //view list of player kits

            } else if (Objects.equals(args[1], "admin")) {
                if (args.length > 2) {
                    if (Objects.equals(args[2], "save")) {
                        helpMessage.add("/ckadmin save toDefault <collectionName> <kitName> fromCurrent");
                        helpMessage.add("/ckadmin save toDefault <collectionName> <kitName> fromDefault <kitName>");
                        helpMessage.add("/ckadmin save toDefault <collectionName> <kitName> fromGlobal <player> <num>");
                        helpMessage.add("/ckadmin save toPersonal <player> <num> fromCurrent");
                        helpMessage.add("/ckadmin save toPersonal <player> <num> fromDefault <kitName>");
                        helpMessage.add("/ckadmin save toPersonal <player> <num> fromGlobal <player> <num>");
                        helpMessage.add("Info: Saves a kit to the default collection or to a player's collection.");

                    } else if (Objects.equals(args[2], "load")) {
                        helpMessage.add("/ckadmin load <player> fromDefault <kitName>");
                        helpMessage.add("/ckadmin load <player> fromGlobal <player> <num>");
                        helpMessage.add("Info: Force-loads a specified kit onto a player");

                    } else if (Objects.equals(args[2], "delete")) {
                        helpMessage.add("/ckadmin delete fromDefault <collectionName> <kitName>");
                        helpMessage.add("/ckadmin delete fromDefault <collectionName> all");
                        helpMessage.add("/ckadmin delete fromGlobal <player> <num>");
                        helpMessage.add("/ckadmin delete fromGlobal <player> all");
                        helpMessage.add("Info: Force-deletes a kit from a collection.");

                    } else {
                        helpMessage.add("/ckits help admin save");
                        helpMessage.add("/ckits help admin load");
                        helpMessage.add("/ckits help admin delete");
                        helpMessage.add("Info: Sends syntax and info about a CuteKits Admin subcommand.");

                    }
                } else {
                    helpMessage.add("/ckits help admin save");
                    helpMessage.add("/ckits help admin load");
                    helpMessage.add("/ckits help admin delete");
                    helpMessage.add("Info: Sends syntax and info about a CuteKits Admin subcommand.");

                }

            } else {
                helpMessage.add("/ckits help save");
                helpMessage.add("/ckits help load");
                helpMessage.add("/ckits help move");
                helpMessage.add("/ckits help delete");
                helpMessage.add("/ckits help shout");
                helpMessage.add("/ckits help items");
                helpMessage.add("/ckits help admin");
                helpMessage.add("Info: Sends syntax and info about a CuteKits subcommand.");

            }
        } else {
            confReader.viewMainMenu((Player) sender);
        }

        for (String s : helpMessage) {
            sender.sendMessage(s);
        }

        return true;
    }
}
