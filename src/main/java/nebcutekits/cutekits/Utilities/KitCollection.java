package nebcutekits.cutekits.Utilities;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KitCollection {
    public String collectionName; //which is Player Name if isPlayerKit
    public boolean isPlayerCollection = false;
    public String ownerUUID;
    public List<Kit> Kits = new ArrayList<>();

    public KitCollection(String collectionName) {
        this.collectionName = collectionName;
    }

    public KitCollection(String collectionName, List<Kit> Kits) {
        this.collectionName = collectionName;
        this.Kits = Kits;
    }

    public KitCollection(Player player) {
        this.collectionName = player.getName();
        this.isPlayerCollection = true;
        this.ownerUUID = player.getUniqueId().toString();
    }

    public KitCollection(Player player, List<Kit> Kits) {
        this.collectionName = player.getName();
        this.Kits = Kits;
        this.isPlayerCollection = true;
        this.ownerUUID = player.getUniqueId().toString();
    }

    public KitCollection(String collectionName, List<Kit> Kits, UUID playerUUID) {
        this.collectionName = collectionName;
        this.Kits = Kits;
        this.isPlayerCollection = true;
        this.ownerUUID = playerUUID.toString();
    }

    public KitCollection(String collectioName, List<Kit> Kits, String playerUUID) {
        this.collectionName = collectioName;
        this.Kits = Kits;
        this.isPlayerCollection = true;
        this.ownerUUID = playerUUID;
    }


    public int moveKit(int indexFrom, int indexTo) {
        //clamp values
        if (indexFrom < 0) { indexFrom = 0; }
        if (indexFrom >= this.Kits.size()) { indexFrom = this.Kits.size()-1; }
        if (indexTo < 0) { indexTo = 0; }

        //don't move it to same slot
        if (indexFrom == indexTo || (indexFrom == this.Kits.size()-1 && indexTo >= this.Kits.size())) { return -1; }

        Kit kitToMove = this.Kits.get(indexFrom);
        this.Kits.remove(indexFrom);

        if (indexTo < this.Kits.size()) {
            this.Kits.set(indexTo, kitToMove);
            return indexTo;
        } else {
            this.Kits.add(kitToMove);
            return this.Kits.size()-1;
        }
    }

    public int deleteKit(int index) {
        if (index < 0) { index = 0; }
        if (index >= this.Kits.size()) { index = this.Kits.size()-1; }
        if (index < 0) {
            return -1;
        }
        this.Kits.remove(index);
        return index;
    }
}
