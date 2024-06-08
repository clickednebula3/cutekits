package nebcutekits.cutekits.Commands.Executors;

import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class ckitsExecutor implements CommandExecutor {
    ConfigReader confReader;
    help cmd_help;
    save cmd_save;
    load cmd_load;
    move cmd_move;
    delete cmd_delete;
    shout cmd_shout;
    view cmd_view;

    public ckitsExecutor(ConfigReader confReader) {
        this.confReader = confReader;
        cmd_help = new help(confReader);
        cmd_save = new save(confReader);
        cmd_load = new load(confReader);
        cmd_move = new move(confReader);
        cmd_delete = new delete(confReader);
        cmd_shout = new shout(confReader);
        cmd_view = new view(confReader);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Objects.equals(args[0], "view")) {
            return cmd_view.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "save")) {
            return cmd_save.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "load")) {
            return cmd_load.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "move")) {
            return cmd_move.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "delete")) {
            return cmd_delete.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "shout")) {
            return cmd_shout.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "help")) {
            return cmd_help.runCommand(sender, command, label, args);
        } else if (Objects.equals(args[0], "items")) {

        } else {
            sender.sendMessage("Could not find subcommand. Check the Help menu.");
            return cmd_help.runCommand(sender, command, label, args);
        }

        return true;
    }

}
