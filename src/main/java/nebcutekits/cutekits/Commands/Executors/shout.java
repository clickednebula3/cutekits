package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class shout {
    ConfigReader confReader;
    public shout(ConfigReader confReader) {
        this.confReader = confReader;
    }

    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command.");
            return true;
        }

        String sharingMsg = "is sharing kit slot";
//        String sharingView = "[View Kit]";
        String sharingLoad = "[Load Kit]";
        String sharingSave = "[Save Kit]";
        String playerName = sender.getName();

        int kitIndex = 0;
        int kitIndexShow = 1;
        if (args.length >= 2) {
            try {
                kitIndex = Integer.parseInt(args[1]);
                kitIndex--;
            } catch (NumberFormatException ignored) {}
        }
        int collectionIndex = confReader.getPlayerCollectionIndex((Player) sender);
        if (collectionIndex == -1) {
            sender.sendMessage("You don't have a kits collection. Learn how to make one with '/ckits help save'");
            return true;
        }
        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= confReader.playerCollections.get(collectionIndex).Kits.size()) {
            kitIndex = confReader.playerCollections.get(collectionIndex).Kits.size()-1;
        }
        if (kitIndex == -1) {
            sender.sendMessage("You don't have any kits. Learn how to make one with '/ckits help save'");
            return true;
        }
        kitIndexShow = kitIndex+1;

        String tellrawContent = "";
        tellrawContent = "[{\"text\":\""+playerName+"\",\"color\":\"red\"},{\"text\":\" is sharing kit slot "+kitIndexShow+"!\\n\",\"color\":\"white\"},{\"text\":\""+sharingSave+"\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/ckits save global "+playerName+" "+kitIndexShow+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Save "+playerName+"'s Kit Slot "+kitIndexShow+"\"}},\" \",{\"text\":\""+sharingLoad+"\",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/ckits load global "+playerName+" "+kitIndexShow+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Load "+playerName+"'s Kit Slot "+kitIndexShow+"\"}}]";

        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw @a "+tellrawContent);
        return true;
    }
}
