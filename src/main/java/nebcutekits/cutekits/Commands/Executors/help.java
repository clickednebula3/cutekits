package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class help {
    ConfigReader confReader;
    public help(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> helpMessage = new ArrayList<>();
        helpMessage.add("---- CuteKits Help ----");

        if (args.length > 1) {
            if (Objects.equals(args[1], "save")) {
                helpMessage.add("/ckits save current <personal kit number to override>");
                helpMessage.add("/ckits save default <kitname> <personal kit number to override>");
                helpMessage.add("/ckits save global <player name> <player kit number> <personal kit number to override>");
                helpMessage.add("Info: Adds a kit to your personal kits.");

            } else if (Objects.equals(args[1], "load")) {
                helpMessage.add("/ckits load personal <personal kit number>");
                helpMessage.add("/ckits load default <kitname>");
                helpMessage.add("/ckits load global <player name> <player kit number>");
                helpMessage.add("Info: Loads a kit to your current inventory.");

            } else if (Objects.equals(args[1], "view")) {
                helpMessage.add("/ckits view");
                helpMessage.add("/ckits view personal kit <personal kit number>");
                helpMessage.add("/ckits view personal page <page>");
                helpMessage.add("/ckits view default kit <kitname>");
                helpMessage.add("/ckits view default collection <collection name> page <page>");
                helpMessage.add("/ckits view default page <page>");
                helpMessage.add("/ckits view global player <player name> kit <player kit number>");
                helpMessage.add("/ckits view global player <player name> page <page>");
                helpMessage.add("/ckits view global page <page>");
                helpMessage.add("Info: Opens the Navigable cKits GUI.");

            } else if (Objects.equals(args[1], "move")) {
                helpMessage.add("/ckits move <personal kit number> <personal kit number to override>");
                helpMessage.add("Info: Changes the number of the kit");

            } else if (Objects.equals(args[1], "delete")) {
                helpMessage.add("/ckits delete <personal kit number>");
                helpMessage.add("Description: Deletes a kit from your personal kits");

            } else if (Objects.equals(args[1], "shout")) {
                helpMessage.add("/ckits shout <personal kit number>");
                helpMessage.add("Info: Share your kit in the chat.");
                helpMessage.add("This will send a button to view the kit and a button to save it.");

            } else if (Objects.equals(args[1], "items")) {
                helpMessage.add("/ckits items");
                helpMessage.add("Info: Opens a menu where you can get common kit items.");

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
            helpMessage.add("/ckits help save");
            helpMessage.add("/ckits help load");
            helpMessage.add("/ckits help view");
            helpMessage.add("/ckits help move");
            helpMessage.add("/ckits help delete");
            helpMessage.add("/ckits help shout");
            helpMessage.add("/ckits help items");
            helpMessage.add("/ckits help admin");
            helpMessage.add("Info: Sends syntax and info about a CuteKits subcommand.");

        }

        for (String s : helpMessage) {
            sender.sendMessage(s);
        }

        return true;
    }
}
