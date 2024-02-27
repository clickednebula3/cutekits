package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class delete {
    ConfigReader confReader;
    public delete(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command.");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("Not enough arguments. Check syntax with '/ckits help delete'");
            return true;
        }

        int indexToDelete;
        try {
            indexToDelete = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid input. please insert a number.");
            return true;
        }
        int collectionIndex = confReader.getPlayerCollectionIndex((Player) sender);
        if (collectionIndex == -1) {
            sender.sendMessage("You don't have a collection. Learn how to make one with '/ckits help save'");
            return true;
        }
        int deleteResult = confReader.playerCollections.get(collectionIndex).deleteKit(indexToDelete);

        if (deleteResult == -1) {
            sender.sendMessage("You don't have any kits. Learn how to make one with '/ckits help save'");
            return true;
        }
        deleteResult++;
        sender.sendMessage("Deleted kit slot "+deleteResult);

        confReader.writeConfig();
        return true;
    }
}
