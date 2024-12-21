package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ck2Executor implements CommandExecutor {
    ConfigReader confReader;
    load cmd_load;

    public ck2Executor(ConfigReader confReader) {
        this.confReader = confReader;
        cmd_load = new load(confReader);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can run this command.");
            return true;
        }

        ((Player) sender).performCommand("k load personal 2");

        return true;
    }

}
