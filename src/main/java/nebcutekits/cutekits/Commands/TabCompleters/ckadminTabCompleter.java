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

//TODO: finish looping through default kits
//TODO: loop trough maxKitAmount

public class ckadminTabCompleter implements TabCompleter {
    ConfigReader confReader;
    public ckadminTabCompleter(ConfigReader confReader) {
        this.confReader = confReader;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabCompleteString = new ArrayList<>();

        if (args.length == 1)
        {
            tabCompleteString.add("save");
            tabCompleteString.add("load");
            tabCompleteString.add("delete");
        }


        else if (args.length == 2)
        {
            if (Objects.equals(args[0], "save"))
            {
                tabCompleteString.add("toDefault");
                tabCompleteString.add("toPersonal");
            }

            else if (Objects.equals(args[0], "load"))
            {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    tabCompleteString.add(p.getName());
                }
            }

            else if (Objects.equals(args[0], "delete"))
            {
                tabCompleteString.add("fromDefault");
                tabCompleteString.add("fromGlobal");
            }
        }


        else if (args.length == 3)
        {
            if (Objects.equals(args[0], "save"))
            {
                if (Objects.equals(args[1], "toDefault"))
                {
                    tabCompleteString.add("main");
                }
                else if (Objects.equals(args[1], "toPersonal"))
                {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        tabCompleteString.add(p.getName());
                    }
                }
            }

            else if (Objects.equals(args[0], "load"))
            {
                tabCompleteString.add("fromDefault");
                tabCompleteString.add("fromGlobal");
            }

            else if (Objects.equals(args[0], "delete"))
            {
                if (Objects.equals(args[1], "fromDefault"))
                {
                    tabCompleteString.addAll(confReader.getDefaultCollectionNames());
                }
                else if (Objects.equals(args[1], "fromGlobal"))
                {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        tabCompleteString.add(p.getName());
                    }
                }
            }
        }


        else if (args.length == 4)
        {
            if (Objects.equals(args[0], "save"))
            {
                if (Objects.equals(args[1], "toDefault"))
                {
                    tabCompleteString.add("kit1");
                    tabCompleteString.add("kit2");
                    tabCompleteString.add("kit3");
                }
                else if (Objects.equals(args[1], "toPersonal"))
                {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                }
            }

            else if (Objects.equals(args[0], "load"))
            {
                if (Objects.equals(args[2], "fromDefault"))
                {
                    tabCompleteString.addAll(confReader.getDefaultKitNames());
                }
                else if (Objects.equals(args[2], "fromGlobal"))
                {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        tabCompleteString.add(p.getName());
                    }
                }
            }

            else if (Objects.equals(args[0], "delete"))
            {
                if (Objects.equals(args[1], "fromDefault"))
                {
                    tabCompleteString.add("all");
                    tabCompleteString.addAll(confReader.getDefaultKitNames());
                }
                else if (Objects.equals(args[1], "fromGlobal"))
                {
                    tabCompleteString.add("all");
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
            if (Objects.equals(args[0], "save"))
            {
                if (Objects.equals(args[1], "toDefault") || Objects.equals(args[1], "toPersonal"))
                {
                    tabCompleteString.add("fromCurrent");
                    tabCompleteString.add("fromDefault");
                    tabCompleteString.add("fromGlobal");
                }
            }

            else if (Objects.equals(args[0], "load"))
            {
                if (Objects.equals(args[2], "fromGlobal"))
                {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                }
            }
        }


        else if (args.length == 6)
        {
            if (Objects.equals(args[0], "save"))
            {
                if (Objects.equals(args[1], "toDefault") || Objects.equals(args[1], "toPersonal"))
                {
                    if (Objects.equals(args[4], "fromDefault"))
                    {
                        tabCompleteString.addAll(confReader.getDefaultKitNames());
                    }
                    else if (Objects.equals(args[4], "fromGlobal"))
                    {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            tabCompleteString.add(p.getName());
                        }
                    }
                }
            }
        }


        else if (args.length == 7)
        {
            if (Objects.equals(args[0], "save")) {
                if (Objects.equals(args[1], "toDefault") || Objects.equals(args[1], "toPersonal")) {
                    if (Objects.equals(args[4], "fromGlobal")) {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            tabCompleteString.add("1");
                            tabCompleteString.add("2");
                            tabCompleteString.add("3");
                            tabCompleteString.add("4");
                            tabCompleteString.add("5");
                        }
                    }
                }
            }
        }


        return tabCompleteString;
    }
}
