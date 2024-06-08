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
            tabCompleteString.add("view");
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
                tabCompleteString.add("current");
                tabCompleteString.add("default");
                tabCompleteString.add("global");
            } else if (Objects.equals(args[0], "load")) {
                tabCompleteString.add("personal");
                tabCompleteString.add("default");
                tabCompleteString.add("global");
            } else if (Objects.equals(args[0], "view")) {
                tabCompleteString.add("personal");
                tabCompleteString.add("default");
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
            } else if (Objects.equals(args[0], "view")) {
                if (Objects.equals(args[1], "personal")) {
                    tabCompleteString.add("kit");
                    tabCompleteString.add("page");
                } else if (Objects.equals(args[1], "default")) {
                    tabCompleteString.add("kit");
                    tabCompleteString.add("collection");
                    tabCompleteString.add("page");
                } else if (Objects.equals(args[1], "global")) {
                    tabCompleteString.add("player");
                    tabCompleteString.add("page");
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
            } else if (Objects.equals(args[0], "view")) {
                if (Objects.equals(args[1], "personal") && (Objects.equals(args[2], "kit") || Objects.equals(args[2], "page"))) {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                } else if (Objects.equals(args[1], "default")) {
                    if (Objects.equals(args[2], "kit")) {
                        tabCompleteString.addAll(confReader.getDefaultKitNames());
                    } else if (Objects.equals(args[2], "collection")) {
                        tabCompleteString.addAll(confReader.getDefaultCollectionNames());
                    } else if (Objects.equals(args[2], "page")) {
                        tabCompleteString.add("1");
                        tabCompleteString.add("2");
                        tabCompleteString.add("3");
                        tabCompleteString.add("4");
                        tabCompleteString.add("5");
                    }
                } else if (Objects.equals(args[1], "global")) {
                    if (Objects.equals(args[2], "player")) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            tabCompleteString.add(player.getName());
                        }
                    } else if (Objects.equals(args[2], "page")) {
                        tabCompleteString.add("1");
                        tabCompleteString.add("2");
                        tabCompleteString.add("3");
                        tabCompleteString.add("4");
                        tabCompleteString.add("5");
                    }
                }
            }
        }
        else if (args.length == 5)
        {
            if (Objects.equals(args[0], "save") && args[1].equals("global")) {
                tabCompleteString.add("1");
                tabCompleteString.add("2");
                tabCompleteString.add("3");
                tabCompleteString.add("4");
                tabCompleteString.add("5");
            } else if (Objects.equals(args[0], "view")) {
                if (Objects.equals(args[1], "default") && Objects.equals(args[2], "collection")) {
                    tabCompleteString.add("page");
                } else if (Objects.equals(args[1], "global") && Objects.equals(args[2], "player")) {
                    tabCompleteString.add("kit");
                    tabCompleteString.add("page");
                }
            }
        }
        else if (args.length == 6)
        {
            if (Objects.equals(args[0], "view")) {
                if (Objects.equals(args[1], "default") && Objects.equals(args[2], "collection") && Objects.equals(args[4], "page")) {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                } else if (Objects.equals(args[1], "global") && Objects.equals(args[2], "player") &&
                        (Objects.equals(args[4], "kit") || Objects.equals(args[4], "page"))
                ) {
                    tabCompleteString.add("1");
                    tabCompleteString.add("2");
                    tabCompleteString.add("3");
                    tabCompleteString.add("4");
                    tabCompleteString.add("5");
                }
            }
        }

        return tabCompleteString;
    }
}
