package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        Player player = (Player) sender;

        if (args.length > 1) {
            if (Objects.equals(args[1], "personal"))
            {
                if (args.length > 3 && Objects.equals(args[2], "kit")) {
                    int kitIndex = 0;
                    try {
                        kitIndex = Integer.parseInt(args[3]);
                        kitIndex--;
                    } catch (NumberFormatException ignored) {}
                    int viewResult = confReader.viewPlayerKit(player, player, kitIndex);
                    if (viewResult == -1) {
                        sender.sendMessage("You don't have a kits collection. Learn how to make one with '/ckits help save'");
                    } else if (viewResult == -2) {
                        sender.sendMessage("You don't have any kits. Learn how to make one with '/ckits help save'");
                    } else {
                        viewResult++;
//                        sender.sendMessage("Viewing personal kit "+viewResult);
                    }
                } else {
                    int pageIndex = 0;
                    if (args.length > 3 && Objects.equals(args[2], "page")) {
                        try {
                            pageIndex = Integer.parseInt(args[3]);
                            pageIndex--;
                        } catch (NumberFormatException ignored) {}
                    }
                    int viewResult = confReader.viewPlayerCollection(player, player, pageIndex);
                    if (viewResult == -1) {
                        sender.sendMessage("You don't have any kits. Learn how to make one with '/ckits help save'");
                    } else {
//                        sender.sendMessage("Viewing personal collection page "+pageIndex);
                    }
                }
            }
            else if (Objects.equals(args[1], "default"))
            {
                if (args.length > 3 && Objects.equals(args[2], "kit")) {
                    String kitName = args[3];//could be invalid
                    int viewResult = confReader.viewDefaultKit(player, kitName);
                    if (viewResult == -1) {
                        sender.sendMessage("Couldn't find default kit collection.");
                    } else if (viewResult == -2) {
                        sender.sendMessage("Couldn't find default kit from kitName.");
                    } else {
//                        sender.sendMessage("Viewing default kit '"+kitName+"'");
                        Bukkit.broadcastMessage(sender.getName() + " is viewing default kit '"+kitName+"'");
                    }
                } else if (args.length > 3 && Objects.equals(args[2], "collection")) {
                    String collectionName = args[3];//could be invalid
                    int pageIndex = 0;
                    if (args.length > 5 && Objects.equals(args[4], "page")) {
                        try {
                            pageIndex = Integer.parseInt(args[3]);
                            pageIndex--;
                        } catch (NumberFormatException ignored) {}
                    }
                    int viewResult = confReader.viewDefaultCollection(player, collectionName, pageIndex);
                    if (viewResult == -1) {
                        sender.sendMessage("Couldn't find default kit collection.");
                    } else {
//                        sender.sendMessage("Viewing default collection '"+collectionName+"' page "+pageIndex);
                    }
                } else {
                    int pageIndex = 0;
                    if (args.length > 3 && Objects.equals(args[2], "page")) {
                        try {
                            pageIndex = Integer.parseInt(args[3]);
                            pageIndex--;
                        } catch (NumberFormatException ignored) {}
                    }
                    confReader.viewAllDefaultCollections(player, pageIndex);
//                    sender.sendMessage("Viewing default collections page "+pageIndex);
                }
            }
            else if (Objects.equals(args[1], "global"))
            {
                if (args.length > 3 && Objects.equals(args[2], "player")) {
                    String kitOwnerName = args[3];//could be invalid
                    OfflinePlayer kitOwner = Bukkit.getOfflinePlayer(kitOwnerName);
                    if (args.length > 5 && Objects.equals(args[4], "kit")) {
                        int kitIndex = 0;
                        try {
                            kitIndex = Integer.parseInt(args[5]);
                            kitIndex--;
                        } catch (NumberFormatException ignored) {
                        }
                        int viewResult = confReader.viewPlayerKit(player, kitOwner, kitIndex);
                        if (viewResult == -1) {
                            sender.sendMessage(kitOwnerName + " doesn't have a kits collection.");
                        } else if (viewResult == -2) {
                            sender.sendMessage(kitOwnerName + " doesn't have any kits.");
                        } else {
                            viewResult++;
//                            sender.sendMessage("Viewing " + kitOwnerName + "'s global kit " + viewResult);
                            Bukkit.broadcastMessage(sender.getName()+" is viewing "+kitOwnerName+"'s global kit "+viewResult);
                        }
                    } else {
                        int pageIndex = 0;
                        if (args.length > 5 && Objects.equals(args[4], "page")) {
                            try {
                                pageIndex = Integer.parseInt(args[5]);
                                pageIndex--;
                            } catch (NumberFormatException ignored) {
                            }
                        }
                        int viewResult = confReader.viewPlayerCollection(player, kitOwner, pageIndex);
                        if (viewResult == -1) {
                            sender.sendMessage("The selected player doesn't have any kits.");
                        } else {
//                            sender.sendMessage("Viewing " + kitOwnerName + "'s global collection page " + pageIndex);
                        }
                    }

                }
                else
                {
                    int pageIndex = 0;
                    if (args.length > 3 && Objects.equals(args[2], "page")) {
                        try {
                            pageIndex = Integer.parseInt(args[3]);
                            pageIndex--;
                        } catch (NumberFormatException ignored) {}
                    }
                    confReader.viewAllGlobalCollections(player, pageIndex);
//                    sender.sendMessage("Viewing global collections page "+pageIndex);
                }
            } else {
                confReader.viewMainMenu(player);
            }
        } else {
            confReader.viewMainMenu(player);
        }
        return true;
    }
}
