package nebcutekits.cutekits;

import nebcutekits.cutekits.Commands.Executors.*;
import nebcutekits.cutekits.Commands.TabCompleters.*;
import nebcutekits.cutekits.Handlers.CuteHandler;
import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CuteKits extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Hello World! My name is  CuteKits, and I will help organise your kit handouts! Have fun with neat inventories!");

        FileConfiguration config = this.getConfig();
        ConfigReader confReader = new ConfigReader(this, config);
        confReader.loadConfig();

        this.getCommand("ckits").setExecutor(new ckitsExecutor(confReader));
        this.getCommand("ckadmin").setExecutor(new ckadminExecutor(confReader));

        this.getCommand("ckits").setTabCompleter(new ckitsTabCompleter(confReader));
        this.getCommand("ckadmin").setTabCompleter(new ckadminTabCompleter(confReader));

        Bukkit.getPluginManager().registerEvents(new CuteHandler(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Goodnight! Even CuteKits has to take a nap sometimes...");
    }

}
