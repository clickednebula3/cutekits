package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

//TODO: finish all 6 commands

public class admin_save {
    ConfigReader confReader;
    public admin_save(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 5) {
            sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin save'");
            return true;
        }

        if (Objects.equals(args[1], "toDefault")) {
            String collectionName = args[2];
            String toKitName = args[3];

            if (Objects.equals(args[4], "fromCurrent"))
            {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only a player can run this part of this command.");
                    return true;
                }

                int saveResult = confReader.saveCurrentKitToDefaultKit((Player) sender, collectionName, toKitName);

                if (saveResult == -1) {
                    sender.sendMessage("A kit with that name already exists.");
                } else if (saveResult == -2) {
                    sender.sendMessage("Created a new kit collection '"+collectionName+"' and added kit '"+toKitName+"'");
                } else if (saveResult == -3) {
                    sender.sendMessage("Added kit '"+toKitName+"' to kit collection '"+collectionName+"'");
                } else {
                    saveResult++;
                    sender.sendMessage("Added kit '"+toKitName+"' to kit collection '"+collectionName+"' at slot "+saveResult);
                }

            }

            else if (Objects.equals(args[4], "fromDefault"))
            {
                if (args.length < 6) {
                    sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin save'");
                    return true;
                }
                String kitName = args[5];

                int saveResult = confReader.saveDefaultKitToDefaultKit(kitName, collectionName, toKitName);

                if (saveResult == -1) {
                    sender.sendMessage("Couldn't find default kit collection for '"+kitName+"'");
                } else if (saveResult == -2) {
                    sender.sendMessage("Couldn't find default kit for '"+kitName+"'");
                } else if (saveResult == -6) {
                    sender.sendMessage("A kit with the name '"+toKitName+"' already exists");
                } else if (saveResult == -3) {
                    sender.sendMessage("Can't copy kit to same collection '"+collectionName+"' with the same name '"+kitName+"'");
                } else if (saveResult == -4) {
                    sender.sendMessage("Created collection '"+collectionName+"' and saved '"+kitName+"' as a new kit called '"+toKitName+"'");
                } else if (saveResult == -5) {
                    sender.sendMessage("Found collection '"+collectionName+"' and saved '"+kitName+"' as a new kit called '"+toKitName+"'");
                } else {
                    saveResult++;
                    sender.sendMessage("Found collection '"+collectionName+"' and saved '"+kitName+"' as a new kit called '"+toKitName+"' at slot "+saveResult);
                }

            }

            else if (Objects.equals(args[4], "fromGlobal"))
            {
                if (args.length < 6) {
                    sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin save'");
                    return true;
                }
                OfflinePlayer globalPlayer = Bukkit.getServer().getOfflinePlayer(args[5]);
                int kitIndex = 99;
                if (args.length >= 7) {
                    try {
                        kitIndex = Integer.parseInt(args[6]);
                        kitIndex--;
                    } catch (NumberFormatException ignored) {}
                }

                int saveResult = confReader.saveGlobalKitToDefaultKit(globalPlayer, kitIndex, collectionName, toKitName);

                if (saveResult == -1) {
                    sender.sendMessage("Could not find the selected player's kit collection.");
                } else if (saveResult == -2) {
                    sender.sendMessage("The selected player doesn't have any kits.");
                } else if (saveResult == -5) {
                    sender.sendMessage("A kit with the name '"+toKitName+"' already exists");
                } else if (saveResult == -3) {
                    sender.sendMessage("Created collection '"+collectionName+"' and saved "+globalPlayer.getName()+"'s kit as a new kit called '"+toKitName+"'");
                } else if (saveResult == -4) {
                    sender.sendMessage("Found collection '"+collectionName+"' and saved "+globalPlayer.getName()+"'s kit as a new kit called '"+toKitName+"'");
                } else {
                    saveResult++;
                    sender.sendMessage("Found collection '"+collectionName+"' and saved '"+globalPlayer.getName()+"'s kit as a new kit called '"+toKitName+"' at slot "+saveResult);
                }


            }

            else
            {
                sender.sendMessage("Invalid arguments. Check syntax with '/ckits help admin save'");
            }

        }

        else if (Objects.equals(args[1], "toPersonal")) {
            Player player = Bukkit.getServer().getPlayer(args[2]);
            if (player == null) {
                player = Bukkit.getServer().getOfflinePlayer(args[2]).getPlayer();
            }
            if (player == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
            int toKitIndex;
            try {
                toKitIndex = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid input. please insert a number.");
                return true;
            }

            if (Objects.equals(args[4], "fromCurrent"))
            {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only a player can run this part of this command.");
                    return true;
                }

                int saveResult = confReader.saveCurrentKitToPlayerKit((Player) sender, player, toKitIndex);

                if (saveResult == -1) {
                    sender.sendMessage("Created kit collection for "+player.getName()+" and added your current inventory as "+player.getName()+"'s first kit.");
                } else if (saveResult == -2) {
                    sender.sendMessage("Added your current inventory to "+player.getName()+"'s kit collection");
                } else {
                    saveResult++;
                    sender.sendMessage("Added your current inventory to "+player.getName()+"'s kit slot "+saveResult);
                }


            }

            else if (Objects.equals(args[4], "fromDefault"))
            {
                if (args.length < 6) {
                    sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin save'");
                    return true;
                }
                String kitName = args[5];

                int saveResult = confReader.saveDefaultKitToPlayerKit(player, kitName, toKitIndex);

                if (saveResult == -1) {
                    sender.sendMessage("Couldn't find default kit collection for '"+kitName+"'");
                } else if (saveResult == -2) {
                    sender.sendMessage("Couldn't find default kit for '"+kitName+"'");
                } else if (saveResult == -3) {
                    sender.sendMessage("Created kit collection for "+player.getName()+" and added your current inventory as "+player.getName()+"'s first kit.");
                } else if (saveResult == -4) {
                    sender.sendMessage("Found "+player.getName()+"'s collection and saved '"+kitName+"' as a new kit.");
                } else {
                    saveResult++;
                    sender.sendMessage("Found "+player.getName()+"'s collection and saved '"+kitName+"' as a the kit at slot "+saveResult);
                }

            }

            else if (Objects.equals(args[4], "fromGlobal"))
            {
                if (args.length < 6) {
                    sender.sendMessage("Not enough arguments. Check syntax with '/ckits help admin save'");
                    return true;
                }
                OfflinePlayer fromPlayer = Bukkit.getServer().getOfflinePlayer(args[5]);
                int fromKitIndex = 0;
                if (args.length >= 7) {
                    try {
                        fromKitIndex = Integer.parseInt(args[6]);
                        fromKitIndex--;
                    } catch (NumberFormatException ignored) {}
                }

                int saveResult = confReader.saveGlobalKitToPlayerKit(fromPlayer, fromKitIndex, player, toKitIndex);

                if (saveResult == -1) {
                    sender.sendMessage("Couldn't find "+fromPlayer.getName()+"'s kit collection.");
                } else if (saveResult == -2) {
                    sender.sendMessage(fromPlayer.getName()+" doesn't have any kits.");
                } else if (saveResult == -3) {
                    sender.sendMessage("Created kit collection for "+player.getName()+" and saved "+fromPlayer.getName()+"'s kit slot "+fromKitIndex+" as the first kit.");
                } else if (saveResult == -4) {
                    sender.sendMessage("Found "+player.getName()+"'s collection and saved "+fromPlayer.getName()+"'s kit as a new kit.");
                } else {
                    saveResult++;
                    sender.sendMessage("Found "+player.getName()+"'s collection and saved "+fromPlayer.getName()+"'s kit as a the kit at slot "+saveResult);
                }


            }

            else
            {
                sender.sendMessage("Invalid arguments. Check syntax with '/ckits help admin save'");
            }

        }

        else {
            sender.sendMessage("Invalid arguments. Check syntax with '/ckits help admin save'");
        }

        confReader.writeConfig();;
        return true;
    }
}
