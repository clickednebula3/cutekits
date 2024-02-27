package nebcutekits.cutekits.Commands.TabCompleters;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO: loop trough maxKitAmount

public class ckitsTabCompleter implements TabCompleter {
    ConfigReader confReader;
    public ckitsTabCompleter(ConfigReader confReader) {
        this.confReader = confReader;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabCompleteString = new ArrayList<>();

        if (args.length == 1)
        {
            tabCompleteString.add("help");
            tabCompleteString.add("save");
            tabCompleteString.add("load");
            tabCompleteString.add("items");
            tabCompleteString.add("move");
            tabCompleteString.add("delete");
            tabCompleteString.add("shout");
        }
        else if (args.length == 2)
        {
            if (Objects.equals(args[0], "help")) {
                tabCompleteString.add("save");
                tabCompleteString.add("load");
                tabCompleteString.add("items");
                tabCompleteString.add("move");
                tabCompleteString.add("delete");
                tabCompleteString.add("shout");
            } else if (Objects.equals(args[0], "save")) {
                tabCompleteString.add("default");
                tabCompleteString.add("current");
                tabCompleteString.add("global");
            } else if (Objects.equals(args[0], "load")) {
                tabCompleteString.add("default");
                tabCompleteString.add("personal");
                tabCompleteString.add("global");
            } else if (Objects.equals(args[0], "move")) {
                tabCompleteString.add("1");
                tabCompleteString.add("2");
                tabCompleteString.add("3");
                tabCompleteString.add("4");
                tabCompleteString.add("5");
            } else if (Objects.equals(args[0], "delete")) {
                tabCompleteString.add("1");
                tabCompleteString.add("2");
                tabCompleteString.add("3");
                tabCompleteString.add("4");
                tabCompleteString.add("5");
            } else if (Objects.equals(args[0], "shout")) {
                tabCompleteString.add("1");
                tabCompleteString.add("2");
                tabCompleteString.add("3");
                tabCompleteString.add("4");
                tabCompleteString.add("5");
            }
        }
        else if (args.length == 3)
        {
            if (Objects.equals(args[0], "save")) {
                if (Objects.equals(args[1], "default")) {
                    tabCompleteString.addAll(confReader.getDefaultKitNames());
                } else if (Objects.equals(args[1], "global")) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        tabCompleteString.add(p.getName());
                    }
                }
            } else if (Objects.equals(args[0], "load")) {
                if (Objects.equals(args[1], "default")) {
                    tabCompleteString.addAll(confReader.getDefaultKitNames());
                } else if (Objects.equals(args[1], "personal")) {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                } else if (Objects.equals(args[1], "global")) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        tabCompleteString.add(p.getName());
                    }
                }
            } else if (Objects.equals(args[0], "move")) {
                tabCompleteString.add("1");
                tabCompleteString.add("2");
                tabCompleteString.add("3");
                tabCompleteString.add("4");
                tabCompleteString.add("5");
            }
        }
        else if (args.length == 4)
        {
            if (Objects.equals(args[0], "save")) {
                if (Objects.equals(args[1], "default")) {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                } else if (Objects.equals(args[1], "global")) {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                }
            }
        }
        else if (args.length == 5)
        {
            if (Objects.equals(args[0], "save") && args[0].equals("global")) {
                tabCompleteString.add("1");
                tabCompleteString.add("2");
                tabCompleteString.add("3");
                tabCompleteString.add("4");
                tabCompleteString.add("5");
            }
        }

        return tabCompleteString;
    }
}
