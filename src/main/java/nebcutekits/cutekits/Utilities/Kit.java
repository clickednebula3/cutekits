package nebcutekits.cutekits.Utilities;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit {
    public ItemStack[] inventory;
    public String kitName = "A Cute Kit";
    public boolean requiresBuildBreak;

    public Kit(Inventory inventory, boolean requiresBuildBreak, String kitName) {
        this.inventory = inventory.getContents();
        this.requiresBuildBreak = requiresBuildBreak;
        this.kitName = kitName;
    }

    public Kit(Inventory inventory, boolean requiresBuildBreak) {
        this.inventory = inventory.getContents();
        this.requiresBuildBreak = requiresBuildBreak;
    }
    public Kit(ItemStack[] inventoryContents, boolean requiresBuildBreak, String kitName) {
        this.inventory = inventoryContents;
        this.requiresBuildBreak = requiresBuildBreak;
        this.kitName = kitName;
    }
    public Kit(List<ItemStack> inventoryContents, boolean requiresBuildBreak, String kitName) {
        ItemStack[] inventoryContentsItems = new ItemStack[inventoryContents.size()];
        for (int i=0; i<inventoryContents.size(); i++) {
            inventoryContentsItems[i] = inventoryContents.get(i);
        }
        this.inventory = inventoryContentsItems;
        this.requiresBuildBreak = requiresBuildBreak;
        this.kitName = kitName;
    }
}
