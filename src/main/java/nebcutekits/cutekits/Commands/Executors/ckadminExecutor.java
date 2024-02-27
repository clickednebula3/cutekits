package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class ckadminExecutor implements CommandExecutor {
    ConfigReader confReader;
    admin_save cmd_save;
    admin_load cmd_load;
    admin_delete cmd_delete;

    public ckadminExecutor(ConfigReader confReader) {
        this.confReader = confReader;
        cmd_save = new admin_save(confReader);
        cmd_load = new admin_load(confReader);
        cmd_delete = new admin_delete(confReader);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Objects.equals(args[0], "save")) {
            return cmd_save.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "load")) {
            return cmd_load.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "delete")) {
            return cmd_delete.runCommand(sender, command, label, args);
        } else {
            sender.sendMessage("Could not find subcommand. Check '/ckits help admin'");
        }
        return true;
    }

}
