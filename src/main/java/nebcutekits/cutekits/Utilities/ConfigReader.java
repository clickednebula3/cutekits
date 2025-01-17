package nebcutekits.cutekits.Utilities;

import nebcutekits.cutekits.CuteKits;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ConfigReader {
    public CuteKits cutekits;
    public FileConfiguration config;

    public List<KitCollection> defaultCollections = new ArrayList<>();
    public List<KitCollection> playerCollections = new ArrayList<>();

    private final int v = 2;//config data version


    private String perm_kit_access = "cutekits.kit.";
    private String perm_bypass = perm_kit_access+"bypass";
    private String perm_explicit_default = perm_kit_access+"default.";
    private String perm_explicit_global = perm_kit_access+"global.";

    public ConfigReader(CuteKits cutekits, FileConfiguration config) {
        this.cutekits = cutekits;
        this.config = config;
    }

    public void loadConfig() {
        config = cutekits.getConfig();

//        if (config.contains("data")) {
//            HashMap<String, ?> data = (HashMap<String, ?>) config.get("data");
//            //update to v format depending on last format
//            if (data!=null || !data.containsKey("v")) {
//                //0-1 -> v
//
//            } else if (!Objects.equals(data.get("v").toString(), Integer.toString(v))) {
//                //v_old -> v
//
//            }
//
//            if (data!=null && data.containsKey("maxPersonalKitCount")) {
//                //do smth with it idk
//
//            }
//        }

        if (config.contains("defaultCollections")) {
            List<?> defaultCollections = config.getList("defaultCollections");
            this.defaultCollections = new ArrayList<>();

            for (Object collection : defaultCollections) {
                HashMap<String, ?> thisCollectionHash = (HashMap<String, ?>) collection;
                KitCollection thisCollection = new KitCollection( (String) thisCollectionHash.get("collectionName") );

                List<?> kits = (List<?>) thisCollectionHash.get("kits");
                for (Object kit : kits) {
                    HashMap<String, ?> thisKitHash = (HashMap<String, ?>) kit;

                    Kit thisKit = new Kit(
                            (List<ItemStack>) thisKitHash.get("inventory"),
                            (boolean) thisKitHash.get("requiresBuildBreak"),
                            (String) thisKitHash.get("creation_world"),
                            (String) thisKitHash.get("kitName")
                    );
                    thisCollection.Kits.add(thisKit);
                }
                this.defaultCollections.add(thisCollection);
            }
        }

        if (config.contains("playerCollections")) {
            List<?> playerCollections = config.getList("playerCollections");
            this.playerCollections = new ArrayList<>();

            for (Object collection : playerCollections) {
                HashMap<String, ?> thisCollectionHash = (HashMap<String, ?>) collection;

                List<?> kits = (List<?>) thisCollectionHash.get("kits");
                List<Kit> thisCollectionKits = new ArrayList<>();
                for (Object kit : kits) {
                    HashMap<String, ?> thisKitHash = (HashMap<String, ?>) kit;
                    Kit thisKit = new Kit(
                            (List<ItemStack>) thisKitHash.get("inventory"),
                            (boolean) thisKitHash.get("requiresBuildBreak"),
                            (String) thisKitHash.get("creation_world"),
                            (String) thisKitHash.get("kitName")
                    );
                    thisCollectionKits.add(thisKit);
                }

                KitCollection thisCollection = new KitCollection(
                        (String) thisCollectionHash.get("collectionName"),
                        thisCollectionKits,
                        (String) thisCollectionHash.get("ownerUUID")
                );
                this.playerCollections.add(thisCollection);
            }
        }

    }

    public void writeConfig() {
        List<HashMap<String, Object>> defaultCollections = new ArrayList<>();
        List<HashMap<String, Object>> playerCollections = new ArrayList<>();

        for (KitCollection thisCollection : this.defaultCollections) {
            List<HashMap<String, Object>> collectionKits = new ArrayList<>();
            for (Kit thisKit : thisCollection.Kits) {
                HashMap<String, Object> thisKitHash = new HashMap<>();
                thisKitHash.put("kitName", thisKit.kitName);
                thisKitHash.put("requiresBuildBreak", thisKit.requiresBuildBreak);
                thisKitHash.put("creation_world", thisKit.creation_world);
                thisKitHash.put("inventory", thisKit.inventory);
                collectionKits.add(thisKitHash);
            }

            HashMap<String, Object> thisCollectionHash = new HashMap<>();
            thisCollectionHash.put("collectionName", thisCollection.collectionName);
            thisCollectionHash.put("kits", collectionKits);
            defaultCollections.add(thisCollectionHash);
        }

        for (KitCollection thisCollection : this.playerCollections) {
            List<HashMap<String, Object>> collectionKits = new ArrayList<>();
            for (Kit thisKit : thisCollection.Kits) {
                HashMap<String, Object> thisKitHash = new HashMap<>();
                thisKitHash.put("kitName", thisKit.kitName);
                thisKitHash.put("requiresBuildBreak", thisKit.requiresBuildBreak);
                thisKitHash.put("creation_world", thisKit.creation_world);
                thisKitHash.put("inventory", thisKit.inventory);
                collectionKits.add(thisKitHash);
            }

            HashMap<String, Object> thisCollectionHash = new HashMap<>();
            thisCollectionHash.put("collectionName", thisCollection.collectionName);
            thisCollectionHash.put("ownerUUID", thisCollection.ownerUUID);
            thisCollectionHash.put("kits", collectionKits);
            playerCollections.add(thisCollectionHash);
        }

        config.set("defaultCollections", defaultCollections);
        config.set("playerCollections", playerCollections);

        cutekits.saveConfig();
    }

    public int getPlayerCollectionIndex(Player player) {
        for (int i = 0; i< playerCollections.size(); i++) {
            if (Objects.equals(playerCollections.get(i).ownerUUID, player.getUniqueId().toString())) {
                return i;
            }
        }
        return -1;
    }
    public int getPlayerCollectionIndex(OfflinePlayer player) {
        for (int i = 0; i< playerCollections.size(); i++) {
            if (Objects.equals(playerCollections.get(i).ownerUUID, player.getUniqueId().toString())) {
                return i;
            }
        }
        return -1;
    }

    public List<String> getDefaultKitNames() {
        List<String> kitNames = new ArrayList<>();
        for (KitCollection defaultCollection : defaultCollections) {
            for (Kit kit : defaultCollection.Kits) {
                kitNames.add(kit.kitName.toLowerCase());
            }
        }
        return kitNames;
    }

    public List<String> getDefaultCollectionNames() {
        List<String> collectionNames = new ArrayList<>();
        for (KitCollection defaultCollection : defaultCollections) {
            collectionNames.add(defaultCollection.collectionName);
        }
        return collectionNames;
    }

    public int getDefaultCollectionIndex(String collectionName) {
        for (int i=0; i<defaultCollections.size();i++) {
            KitCollection thisCollection = defaultCollections.get(i);
            if (Objects.equals(collectionName.toLowerCase(), thisCollection.collectionName.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    public int getDefaultCollectionIndexUsingKitName(String kitName) {
        for (int i=0; i<defaultCollections.size();i++) {
            KitCollection thisCollection = defaultCollections.get(i);
            for (int j=0; j<thisCollection.Kits.size(); j++) {
                Kit thisKit = thisCollection.Kits.get(j);
                if (Objects.equals(kitName.toLowerCase(), thisKit.kitName.toLowerCase())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getDefaultKitIndex(String kitName) {
        for (KitCollection thisCollection : defaultCollections) {
            for (int j = 0; j < thisCollection.Kits.size(); j++) {
                Kit thisKit = thisCollection.Kits.get(j);
                if (Objects.equals(kitName.toLowerCase(), thisKit.kitName.toLowerCase())) {
                    return j;
                }
            }
        }
        return -1;
    }
    public int getDefaultKitIndexWithinCollection(String kitName, int collectionIndex) {
        if (collectionIndex < 0) { collectionIndex = 0; }
        if (collectionIndex >= defaultCollections.size()) { collectionIndex = defaultCollections.size()-1; }
        if (collectionIndex == -1) {
            return -2; //didn't find collection
        }
        for (int j = 0; j < defaultCollections.get(collectionIndex).Kits.size(); j++) {
            Kit thisKit = defaultCollections.get(collectionIndex).Kits.get(j);
            if (Objects.equals(kitName.toLowerCase(), thisKit.kitName.toLowerCase())) {
                return j;
            }
        }
        return -1; //didn't find kit in collection
    }

    public void viewMainMenu(Player player) {
        String title = "cKits Menu";
        Inventory mainInv = Bukkit.createInventory(player, 9*3, title);

        mainInv.setItem(9+2, generateItem(new ItemStack(Material.DIAMOND), "Personal", "View your owned kits"));
        mainInv.setItem(9+4, generateItem(new ItemStack(Material.IRON_INGOT), "Default", "View server predefined kits"));
        mainInv.setItem(9+6, generateItem(new ItemStack(Material.GOLD_INGOT), "Global", "View kits by other players"));
        mainInv.setItem((9*2)+8, generateItem(new ItemStack(Material.SWEET_BERRIES), "Credits", "cuteKits is by clickednebula3 \"Nebby\"", "", "type '/ck help' for help and commands"));
        mainInv.setItem(9*2, generateItem(new ItemStack(Material.STRUCTURE_VOID), "Clear Inventory", "Irreversible Action.", "Deletes every item currently in your inventory."));

        player.openInventory(mainInv);
    }
    public void viewAllGlobalCollections(Player player, int page) {
        String title = "cKits All Global Collections "+page;
        Inventory mainInv = Bukkit.createInventory(player, 9*4, title);

        for (int i=0; i<9*3; i++) {
            int collectionIndex = page*9*3 + i;
            if (collectionIndex < playerCollections.size()) {
                mainInv.setItem(i, generateItem(new ItemStack(Material.GOLD_BLOCK), "Collection "+playerCollections.get(collectionIndex).collectionName, "View this collection"));
            } else {
                mainInv.setItem(i, generateItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " "));
            }
        }

        mainInv.setItem(9*3, generateItem(new ItemStack(Material.ARROW), "Back"));
        mainInv.setItem(9*3+8, generateItem(new ItemStack(Material.ARROW), "Next"));
        player.openInventory(mainInv);
    }
    public void viewAllDefaultCollections(Player player, int page) {
        String title = "cKits All Default Collections "+page;
        Inventory mainInv = Bukkit.createInventory(player, 9*4, title);

        for (int i=0; i<9*3; i++) {
            int collectionIndex = page*3*9 + i;
            if (collectionIndex < defaultCollections.size()) {
                mainInv.setItem(i, generateItem(new ItemStack(Material.IRON_BLOCK), "Collection "+defaultCollections.get(collectionIndex).collectionName, "View this collection"));
            } else {
                mainInv.setItem(i, generateItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " "));
            }
        }

        mainInv.setItem(9*3, generateItem(new ItemStack(Material.ARROW), "Back"));
        mainInv.setItem(9*3+8, generateItem(new ItemStack(Material.ARROW), "Next"));
        player.openInventory(mainInv);
    }
    public int viewPlayerCollection(Player player, Player collectionOwner, int page) {
        int collectionIndex = getPlayerCollectionIndex(collectionOwner);
        if (collectionIndex < 0) { return collectionIndex; }
        boolean playerIsCollectionOwner = player.getName().equals(collectionOwner.getName());
        String title = "cKits Player Collection "+collectionOwner.getName()+" "+page;
        Inventory mainInv = Bukkit.createInventory(player, 9*4, title);

        for (int i=0; i<9*3; i++) {
            int kitIndex = page*3*9 + i;
            if (kitIndex < playerCollections.get(collectionIndex).Kits.size()) {
                if (playerIsCollectionOwner) {
                    mainInv.setItem(i, generateItem(new ItemStack(Material.DIAMOND_SWORD), "Player Kit " + (kitIndex + 1), "View this kit"));
                } else {
                    mainInv.setItem(i, generateItem(new ItemStack(Material.GOLDEN_SWORD), "Player Kit " + (kitIndex + 1), "View this kit"));
                }
            } else {
                mainInv.setItem(i, generateItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " "));
            }
        }

        if (playerIsCollectionOwner) {
            mainInv.setItem(9*3+7, generateItem(new ItemStack(Material.LIGHT_BLUE_WOOL), "Save Current Inventory", "A new kit will be created"));
        }

        mainInv.setItem(9*3, generateItem(new ItemStack(Material.ARROW), "Back"));
        mainInv.setItem(9*3+8, generateItem(new ItemStack(Material.ARROW), "Next"));
        player.openInventory(mainInv);

        return collectionIndex;
    }
    public int viewPlayerCollection(Player player, OfflinePlayer collectionOwner, int page) {
        int collectionIndex = getPlayerCollectionIndex(collectionOwner);
        boolean playerIsCollectionOwner = player.getName().equals(collectionOwner.getName());
        if (collectionIndex < 0) { return collectionIndex; }
        String title = "cKits Player Collection "+collectionOwner.getName()+" "+page;
        Inventory mainInv = Bukkit.createInventory(player, 9*4, title);

        for (int i=0; i<9*3; i++) {
            int kitIndex = page*3*9 + i;
            if (kitIndex < playerCollections.get(collectionIndex).Kits.size()) {
                if (playerIsCollectionOwner) {
                    mainInv.setItem(i, generateItem(new ItemStack(Material.DIAMOND_SWORD), "Player Kit " + (kitIndex + 1), "View this kit"));
                } else {
                    mainInv.setItem(i, generateItem(new ItemStack(Material.GOLDEN_SWORD), "Player Kit " + (kitIndex + 1), "View this kit"));
                }
            } else {
                mainInv.setItem(i, generateItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " "));
            }
        }

        mainInv.setItem(9*3, generateItem(new ItemStack(Material.ARROW), "Back"));
        mainInv.setItem(9*3+8, generateItem(new ItemStack(Material.ARROW), "Next"));

        if (playerIsCollectionOwner) {
            mainInv.setItem(9*3+7, generateItem(new ItemStack(Material.LIGHT_BLUE_WOOL), "Save Current Inventory", "A new kit will be created"));
        }

        player.openInventory(mainInv);

        return collectionIndex;
    }
    public int viewDefaultCollection(Player player, String collectionName, int page) {
        int collectionIndex = getDefaultCollectionIndex(collectionName);//can fail
        if (collectionIndex < 0) { return collectionIndex; }
        String title = "cKits Default Collection "+collectionName+" "+page;
        Inventory mainInv = Bukkit.createInventory(player, 9*4, title);

        for (int i=0; i<9*3; i++) {
            int kitIndex = page*9*3 + i;
            if (kitIndex < defaultCollections.get(collectionIndex).Kits.size()) {
                mainInv.setItem(i, generateItem(new ItemStack(Material.IRON_SWORD), "Default Kit "+defaultCollections.get(collectionIndex).Kits.get(kitIndex).kitName, "View this kit"));
            } else {
                mainInv.setItem(i, generateItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " "));
            }
        }

        mainInv.setItem(9*3, generateItem(new ItemStack(Material.ARROW), "Back"));
        mainInv.setItem(9*3+8, generateItem(new ItemStack(Material.ARROW), "Next"));
        player.openInventory(mainInv);

        return collectionIndex;
    }
    public int viewPlayerKit(Player player, Player kitOwner, int kitIndex){
        String title = "cKits Player Kit "+kitOwner.getName()+" "+(kitIndex+1);
        Inventory mainInv = Bukkit.createInventory(player, 9*6, title);

        int collectionIndex = getPlayerCollectionIndex(kitOwner);
        if (collectionIndex == -1) { return -1; }

        KitCollection playerCollection = playerCollections.get(collectionIndex);

        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= playerCollection.Kits.size()) { kitIndex = playerCollection.Kits.size()-1; }
        if (kitIndex < 0) {
            return -2; //player got no kits
        }

        Kit kitToView = playerCollection.Kits.get(kitIndex);
        mainInv.setContents(kitToView.inventory);

        mainInv.setItem((9*5)+8, generateItem(new ItemStack(Material.LIME_WOOL), "Load Kit"));
        mainInv.setItem((9*5)+7, generateItem(new ItemStack(Material.YELLOW_WOOL), "Save Kit"));
        if (player == kitOwner) {
            mainInv.setItem((9*5)+6, generateItem(new ItemStack(Material.ORANGE_WOOL), "Shout Kit", "Sends a button in chat to", "help others save/load this kit."));
            mainInv.setItem((9*5)+5, generateItem(new ItemStack(Material.RED_WOOL), "Delete Kit", "Will be lost permanently."));
        }
        mainInv.setItem((9*5), generateItem(new ItemStack(Material.REDSTONE_BLOCK), "Back"));

        player.openInventory(mainInv);
        return kitIndex;
    }
    public int viewPlayerKit(Player player, OfflinePlayer kitOwner, int kitIndex){
        String title = "cKits Player Kit "+kitOwner.getName()+" "+(kitIndex+1);
        Inventory mainInv = Bukkit.createInventory(player, 9*6, title);

        int collectionIndex = getPlayerCollectionIndex(kitOwner);
        if (collectionIndex == -1) { return -1; }

        KitCollection playerCollection = playerCollections.get(collectionIndex);

        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= playerCollection.Kits.size()) { kitIndex = playerCollection.Kits.size()-1; }
        if (kitIndex < 0) {
            return -2; //player got no kits
        }

        Kit kitToView = playerCollection.Kits.get(kitIndex);
        mainInv.setContents(kitToView.inventory);

        mainInv.setItem((9*5)+8, generateItem(new ItemStack(Material.LIME_WOOL), "Load Kit"));
        mainInv.setItem((9*5)+7, generateItem(new ItemStack(Material.YELLOW_WOOL), "Save Kit"));
        if (player == kitOwner) {
            mainInv.setItem((9*5)+6, generateItem(new ItemStack(Material.ORANGE_WOOL), "Shout Kit", "Sends a button in chat to", "help others save/load this kit."));
            mainInv.setItem((9*5)+5, generateItem(new ItemStack(Material.RED_WOOL), "Delete Kit", "Will be lost permanently."));
        }
        mainInv.setItem((9*5), generateItem(new ItemStack(Material.REDSTONE_BLOCK), "Back"));

        player.openInventory(mainInv);
        return kitIndex;
    }
    public int viewDefaultKit(Player player, String kitName){
        String title = "cKits Default Kit "+kitName;
        Inventory mainInv = Bukkit.createInventory(player, 9*6, title);

        int collectionIndex = getDefaultCollectionIndexUsingKitName(kitName);
        int kitIndex = getDefaultKitIndex(kitName);
        if (collectionIndex == -1) { return -1; }
        if (kitIndex == -1) { return -2; }

        Kit kitToView = defaultCollections.get(collectionIndex).Kits.get(kitIndex);
        mainInv.setContents(kitToView.inventory);

        mainInv.setItem((9*5)+8, generateItem(new ItemStack(Material.LIME_WOOL), "Load Kit"));
        mainInv.setItem((9*5)+7, generateItem(new ItemStack(Material.YELLOW_WOOL), "Save Kit"));
        mainInv.setItem((9*5), generateItem(new ItemStack(Material.REDSTONE_BLOCK), "Back"));

        player.openInventory(mainInv);
        return kitIndex;
    }

    private ItemStack generateItem(ItemStack item, String name, String ... lores) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        List<String> loresList = new ArrayList<>(Arrays.asList(lores));
        meta.setLore(loresList);

        item.setItemMeta(meta);
        return item;
    }

    public int saveKitToCollection(KitCollection collection, Kit kitToSave, int kitIndex) {
        if (kitIndex < 0) { kitIndex = 0; }

        int collectionIndex = getDefaultCollectionIndex(collection.collectionName);
        collection = defaultCollections.get(collectionIndex);//get the loaded-instance of the kit

        if (kitIndex >= collection.Kits.size()) {
            collection.Kits.add(kitToSave);
            return -1;
        }

        collection.Kits.set(kitIndex, kitToSave);

        return  kitIndex;
    }

    public void saveCurrentKit(Player player) {
        Inventory inventory = player.getInventory();
        Kit kitToAdd = new Kit(inventory, false);

        int collectionIndex = getPlayerCollectionIndex(player);

        if (collectionIndex == -1) {
            //collection not found
            List<Kit> kitListToCreate = new ArrayList<>();
            kitListToCreate.add(kitToAdd);
            KitCollection collectionToAdd = new KitCollection(player, kitListToCreate);
            playerCollections.add(collectionToAdd);
        } else {
            //collection found
            playerCollections.get(collectionIndex).Kits.add(kitToAdd);
        }
    }

    public void saveCurrentKit(Player player, int kitIndex) {
        Inventory inventory = player.getInventory();

        int collectionIndex = getPlayerCollectionIndex(player);

        if (collectionIndex == -1) {
            //collection not found
            Kit kitToSave = new Kit(inventory, false);
            List<Kit> kitListToCreate = new ArrayList<>();
            kitListToCreate.add(kitToSave);
            KitCollection collectionToAdd = new KitCollection(player, kitListToCreate);
            playerCollections.add(collectionToAdd);
        } else {
            //collection found
            if (kitIndex < 0) { kitIndex = 0; }
            Kit kitToSave = new Kit(inventory, false);

            if (kitIndex >= playerCollections.get(collectionIndex).Kits.size()) {
                playerCollections.get(collectionIndex).Kits.add(kitToSave);
            } else {
                playerCollections.get(collectionIndex).Kits.set(kitIndex, kitToSave);
            }

        }
    }

    public int saveDefaultKit(Player player, String kitName, int toKitIndex) {
        int collectionIndex = getDefaultCollectionIndexUsingKitName(kitName);
        if (collectionIndex == -1) { return -1; }
        int kitIndex = getDefaultKitIndex(kitName);
        if (kitIndex == -1) { return -3; }
        int toCollectionIndex = getPlayerCollectionIndex(player);
        if (toCollectionIndex == -1) {
            Kit kitToSave = defaultCollections.get(collectionIndex).Kits.get(kitIndex);
            List<Kit> kitListToCreate = new ArrayList<>();
            kitListToCreate.add(kitToSave);
            KitCollection collectionToAdd = new KitCollection(player, kitListToCreate);
            playerCollections.add(collectionToAdd);
            return -2;
        }

        int savingResult = saveKitToCollection(playerCollections.get(toCollectionIndex), defaultCollections.get(collectionIndex).Kits.get(kitIndex), toKitIndex);

        if (savingResult == -1) {
            return -4; //added kit
        }
        return kitIndex; //replaced kit
    }

    public int saveCurrentKitToPlayerKit(Player player, Player globalPlayer, int globalKitIndex) {
        Inventory inventory = player.getInventory();
        Kit kitToAdd = new Kit(inventory, false);

        int collectionIndex = getPlayerCollectionIndex(globalPlayer);
        if (collectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(globalPlayer);
            collectionToAdd.Kits.add(kitToAdd);
            playerCollections.add(collectionToAdd);
            return -1; //created collection for global player
        }

        if (globalKitIndex < 0) { globalKitIndex = 0; }
        if (globalKitIndex >= playerCollections.get(collectionIndex).Kits.size()) {
            playerCollections.get(collectionIndex).Kits.add(kitToAdd);
            return -2; //added new kit for global player
        }

        playerCollections.get(collectionIndex).Kits.set(globalKitIndex, kitToAdd);
        return collectionIndex; //added kit to their kits
    }

    public int saveCurrentKitToDefaultKit(Player player, String collectionName, String kitName) {
        Inventory inventory = player.getInventory();
        Kit kitToAdd = new Kit(inventory, false, kitName);

        if (getDefaultKitNames().contains(kitName.toLowerCase())) {
            return -1; //kitname already exists somewhere
        }

        int collectionIndex = getDefaultCollectionIndex(collectionName);
        if (collectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(collectionName);
            collectionToAdd.Kits.add(kitToAdd);
            defaultCollections.add(collectionToAdd);

            writeConfig();
            return -2; //created collection and saved its first kit
        }

        defaultCollections.get(collectionIndex).Kits.add(kitToAdd);
        int kitIndex = getDefaultKitIndex(kitName);

        writeConfig();
        if (kitIndex == -1) {
            return -3; //somehow I can't find the kit I just added...
        }
        return kitIndex; //found collection and added kit
    }

    public int saveDefaultKitToDefaultKit(String fromKitName, String collectionName, String kitName) {
        int fromCollectionIndex = getDefaultCollectionIndexUsingKitName(fromKitName);
        if (fromCollectionIndex == -1) {
            return -1; //did not find fromKit's collection
        }
        int fromKitIndex = getDefaultKitIndex(fromKitName);
        if (fromKitIndex == -1) {
            return -2; //did not find fromKit despite finding its collection
        }
        if (getDefaultKitNames().contains(kitName.toLowerCase())) {
            return -5; //kitname already exists somewhere
        }

        if ( Objects.equals(fromKitName.toLowerCase(), kitName.toLowerCase())) {
            return -3; //can't copy kit to same collection with the same name
        }

        Kit kitToAdd = new Kit(defaultCollections.get(fromCollectionIndex).Kits.get(fromKitIndex).inventory, defaultCollections.get(fromCollectionIndex).Kits.get(fromKitIndex).requiresBuildBreak, kitName);

        int collectionIndex = getDefaultCollectionIndex(collectionName);
        if (collectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(collectionName);
            collectionToAdd.Kits.add(kitToAdd);
            defaultCollections.add(collectionToAdd);
            return -4; //created collection and saved its first kit
        }

        defaultCollections.get(collectionIndex).Kits.add(kitToAdd);
        int kitIndex = getDefaultKitIndex(kitName);
        if (kitIndex == -1) {
            return -5; //somehow I can't find the kit I just added...
        }
        return kitIndex; //found collection and added kit
    }

    public int saveDefaultKitToPlayerKit(Player globalPlayer, String kitName, int globalKitIndex) {
        int collectionIndex = getDefaultCollectionIndexUsingKitName(kitName);
        int kitIndex = getDefaultKitIndex(kitName);
        if (collectionIndex == -1) {
            return -1; //could not find kit's collection
        }
        if (kitIndex == -1) {
            return -2; //could not find kit from its collection
        }

        Kit kitToAdd = defaultCollections.get(collectionIndex).Kits.get(kitIndex);

        int playerCollectionIndex = getPlayerCollectionIndex(globalPlayer);

        if (playerCollectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(globalPlayer);
            collectionToAdd.Kits.add(kitToAdd);
            playerCollections.add(collectionToAdd);
            return -3; //created collection for global player
        }

        if (globalKitIndex < 0) { globalKitIndex = 0; }
        if (globalKitIndex >= playerCollections.get(collectionIndex).Kits.size()) {
            playerCollections.get(collectionIndex).Kits.add(kitToAdd);
            return -4; //added new kit for global player
        }

        playerCollections.get(collectionIndex).Kits.set(globalKitIndex, kitToAdd);
        return collectionIndex; //added kit to their kits
    }

    public int saveGlobalKitToPlayerKit(Player fromPlayer, int fromKitIndex, Player toPlayer, int toKitIndex) {
        int fromCollectionIndex = getPlayerCollectionIndex(fromPlayer);
        if (fromCollectionIndex == -1) {
            return -1; //couldn't find fromPlayer's collection
        }

        if (fromKitIndex < 0) { fromKitIndex = 0; }
        if (fromKitIndex >= playerCollections.get(fromCollectionIndex).Kits.size()) { fromKitIndex = playerCollections.get(fromCollectionIndex).Kits.size()-1; }
        if (fromKitIndex < 0) {
            return -2; //fromPlayer doesn't have any kits
        }

        Kit kitToAdd = playerCollections.get(fromCollectionIndex).Kits.get(fromKitIndex);

        int toCollectionIndex = getPlayerCollectionIndex(toPlayer);

        if (toCollectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(toPlayer);
            collectionToAdd.Kits.add(kitToAdd);
            playerCollections.add(collectionToAdd);
            writeConfig();
            return -3; //created collection for toPlayer
        }

        if (toKitIndex < 0) { toKitIndex = 0; }
        if (toKitIndex >= playerCollections.get(toCollectionIndex).Kits.size()) {
            playerCollections.get(toCollectionIndex).Kits.add(kitToAdd);
            writeConfig();
            return -4; //added new kit for toPlayer
        }

        playerCollections.get(toCollectionIndex).Kits.set(toKitIndex, kitToAdd);
        writeConfig();
        return toKitIndex; //added fromPlayer kit to toPlayer kits
    }
    public int saveGlobalKitToPlayerKit(OfflinePlayer fromPlayer, int fromKitIndex, Player toPlayer, int toKitIndex) {
        int fromCollectionIndex = getPlayerCollectionIndex(fromPlayer);
        if (fromCollectionIndex == -1) {
            return -1; //couldn't find fromPlayer's collection
        }

        if (fromKitIndex < 0) { fromKitIndex = 0; }
        if (fromKitIndex >= playerCollections.get(fromCollectionIndex).Kits.size()) { fromKitIndex = playerCollections.get(fromCollectionIndex).Kits.size()-1; }
        if (fromKitIndex < 0) {
            return -2; //fromPlayer doesn't have any kits
        }

        Kit kitToAdd = playerCollections.get(fromCollectionIndex).Kits.get(fromKitIndex);

        int toCollectionIndex = getPlayerCollectionIndex(toPlayer);

        if (toCollectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(toPlayer);
            collectionToAdd.Kits.add(kitToAdd);
            playerCollections.add(collectionToAdd);
            writeConfig();
            return -3; //created collection for toPlayer
        }

        if (toKitIndex < 0) { toKitIndex = 0; }
        if (toKitIndex >= playerCollections.get(toCollectionIndex).Kits.size()) {
            playerCollections.get(toCollectionIndex).Kits.add(kitToAdd);
            writeConfig();
            return -4; //added new kit for toPlayer
        }

        playerCollections.get(toCollectionIndex).Kits.set(toKitIndex, kitToAdd);
        writeConfig();
        return toKitIndex; //added fromPlayer kit to toPlayer kits
    }

    public int saveGlobalKitToDefaultKit(Player globalPlayer, int fromKitIndex, String collectionName, String kitName) {
        int fromCollectionIndex = getPlayerCollectionIndex(globalPlayer);
        if (fromCollectionIndex == -1) {
            return -1; //did not find player's collection
        }
        if (fromKitIndex < 0) { fromKitIndex = 0; }
        if (fromKitIndex >= playerCollections.get(fromCollectionIndex).Kits.size()) { fromKitIndex = playerCollections.get(fromCollectionIndex).Kits.size()-1; }
        if (fromKitIndex < 0) {
            return -2; //player got no kits
        }
        if (getDefaultKitNames().contains(kitName.toLowerCase())) {
            return -5; //kitname already exists somewhere
        }

        Kit kitToAdd = playerCollections.get(fromCollectionIndex).Kits.get(fromKitIndex);
        kitToAdd.kitName = kitName;

        int collectionIndex = getDefaultCollectionIndex(collectionName);
        if (collectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(collectionName);
            collectionToAdd.Kits.add(kitToAdd);
            defaultCollections.add(collectionToAdd);

            return -3; //created collection and saved its first kit
        }

        defaultCollections.get(collectionIndex).Kits.add(kitToAdd);
        int kitIndex = getDefaultKitIndex(kitName);
        if (kitIndex == -1) {
            return -4; //somehow I can't find the kit I just added...
        }
        return kitIndex; //found collection and added kit
    }
    public int saveGlobalKitToDefaultKit(OfflinePlayer globalPlayer, int fromKitIndex, String collectionName, String kitName) {
        int fromCollectionIndex = getPlayerCollectionIndex(globalPlayer);
        if (fromCollectionIndex == -1) {
            return -1; //did not find player's collection
        }
        if (fromKitIndex < 0) { fromKitIndex = 0; }
        if (fromKitIndex >= playerCollections.get(fromCollectionIndex).Kits.size()) { fromKitIndex = playerCollections.get(fromCollectionIndex).Kits.size()-1; }
        if (fromKitIndex < 0) {
            return -2; //player got no kits
        }
        if (getDefaultKitNames().contains(kitName.toLowerCase())) {
            return -5; //kitname already exists somewhere
        }

        Kit kitToAdd = playerCollections.get(fromCollectionIndex).Kits.get(fromKitIndex);
        kitToAdd.kitName = kitName;

        int collectionIndex = getDefaultCollectionIndex(collectionName);
        if (collectionIndex == -1) {
            KitCollection collectionToAdd = new KitCollection(collectionName);
            collectionToAdd.Kits.add(kitToAdd);
            defaultCollections.add(collectionToAdd);

            return -3; //created collection and saved its first kit
        }

        defaultCollections.get(collectionIndex).Kits.add(kitToAdd);
        int kitIndex = getDefaultKitIndex(kitName);
        if (kitIndex == -1) {
            return -4; //somehow I can't find the kit I just added...
        }
        return kitIndex; //found collection and added kit
    }

    public int saveGlobalKit(Player globalPlayer, Player selfPlayer, int kitIndex, int toKitIndex) {
        int collectionIndex = getPlayerCollectionIndex(globalPlayer);
        if (collectionIndex == -1) { return -1; }
        int myCollectionIndex = getPlayerCollectionIndex(selfPlayer);

        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= playerCollections.get(collectionIndex).Kits.size()) {
            kitIndex = playerCollections.get(collectionIndex).Kits.size()-1;
        }
        if (toKitIndex < 0) { toKitIndex = 0; }

        Kit kitToSave = playerCollections.get(collectionIndex).Kits.get(kitIndex);

        if (myCollectionIndex == -1) {
            List<Kit> kitListToCreate = new ArrayList<>();
            kitListToCreate.add(kitToSave);
            KitCollection collectionToAdd = new KitCollection(selfPlayer, kitListToCreate);
            playerCollections.add(collectionToAdd);
        } else {
            if (toKitIndex >= playerCollections.get(myCollectionIndex).Kits.size()) {
                playerCollections.get(myCollectionIndex).Kits.add(kitToSave);
                return -2;
            } else {
                playerCollections.get(myCollectionIndex).Kits.set(toKitIndex, kitToSave);
            }
        }

        return toKitIndex;
    }
    public int saveGlobalKit(OfflinePlayer globalPlayer, Player selfPlayer, int kitIndex, int toKitIndex) {
        int collectionIndex = getPlayerCollectionIndex(globalPlayer);
        if (collectionIndex == -1) { return -1; }
        int myCollectionIndex = getPlayerCollectionIndex(selfPlayer);

        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= playerCollections.get(collectionIndex).Kits.size()) {
            kitIndex = playerCollections.get(collectionIndex).Kits.size()-1;
        }
        if (toKitIndex < 0) { toKitIndex = 0; }

        Kit kitToSave = playerCollections.get(collectionIndex).Kits.get(kitIndex);

        if (myCollectionIndex == -1) {
            List<Kit> kitListToCreate = new ArrayList<>();
            kitListToCreate.add(kitToSave);
            KitCollection collectionToAdd = new KitCollection(selfPlayer, kitListToCreate);
            playerCollections.add(collectionToAdd);
        } else {
            if (toKitIndex >= playerCollections.get(myCollectionIndex).Kits.size()) {
                playerCollections.get(myCollectionIndex).Kits.add(kitToSave);
                return -2;
            } else {
                playerCollections.get(myCollectionIndex).Kits.set(toKitIndex, kitToSave);
            }
        }

        return toKitIndex;
    }

    public int loadDefaultKit(Player player, String kitName) {
        int collectionIndex = getDefaultCollectionIndexUsingKitName(kitName);
        int kitIndex = getDefaultKitIndex(kitName);
        if (collectionIndex == -1) { return -1; }
        if (kitIndex == -1) { return -2; }

        Kit kitToApply = defaultCollections.get(collectionIndex).Kits.get(kitIndex);

        if (player.hasPermission(perm_bypass) || player.getWorld().getName().equalsIgnoreCase(kitToApply.creation_world))

        player.getInventory().setContents(kitToApply.inventory);
        return kitIndex;
    }

    public int loadPersonalKit(Player player, int kitIndex) {
        int collectionIndex = getPlayerCollectionIndex(player);
        if (collectionIndex == -1) { return -1; }

        KitCollection personalCollection = playerCollections.get(collectionIndex);

        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= personalCollection.Kits.size()) { kitIndex = personalCollection.Kits.size()-1; }
        if (kitIndex < 0) {
            return -2; //player got no kits
        }
        Kit kitToApply = personalCollection.Kits.get(kitIndex);

        player.getInventory().setContents(kitToApply.inventory);
        return kitIndex;
    }

    public int loadGlobalKit(Player globalPlayer, Player selfPlayer, int kitIndex) {
        int collectionIndex = getPlayerCollectionIndex(globalPlayer);
        if (collectionIndex == -1) { return -1; }

        KitCollection personalCollection = playerCollections.get(collectionIndex);

        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= personalCollection.Kits.size()) { kitIndex = personalCollection.Kits.size()-1; }
        Kit kitToApply = personalCollection.Kits.get(kitIndex);

        selfPlayer.getInventory().setContents(kitToApply.inventory);
        return kitIndex;
    }
    public int loadGlobalKit(OfflinePlayer globalPlayer, Player selfPlayer, int kitIndex) {
        int collectionIndex = getPlayerCollectionIndex(globalPlayer);
        if (collectionIndex == -1) { return -1; }

        KitCollection personalCollection = playerCollections.get(collectionIndex);

        if (kitIndex < 0) { kitIndex = 0; }
        if (kitIndex >= personalCollection.Kits.size()) { kitIndex = personalCollection.Kits.size()-1; }
        Kit kitToApply = personalCollection.Kits.get(kitIndex);

        selfPlayer.getInventory().setContents(kitToApply.inventory);
        return kitIndex;
    }

}
