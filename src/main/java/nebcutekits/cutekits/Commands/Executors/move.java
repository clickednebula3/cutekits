package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class move {
    ConfigReader confReader;
    public move(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command.");
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage("Not enough arguments. Check syntax with '/ckits help move'");
            return true;
        }

        int indexFrom;
        int indexTo;
        try {
            indexFrom = Integer.parseInt(args[2]);
            indexTo = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid input. please insert a number.");
            return true;
        }
        indexFrom--;
        indexTo++;
        int collectionIndex = confReader.getPlayerCollectionIndex((Player) sender);
        if (collectionIndex == -1) {
            sender.sendMessage("You don't have any kits. Learn how to make one with '/ckits help save'");
            return true;
        }
        int moveResult = confReader.playerCollections.get(collectionIndex).moveKit(indexFrom, indexTo);

        if (moveResult == -1) {
            sender.sendMessage("You can't move a kit to the same slot.");
            return true;
        }
        moveResult++;
        sender.sendMessage("Kit moved to slot "+moveResult);

        confReader.writeConfig();
        return true;
    }
}
