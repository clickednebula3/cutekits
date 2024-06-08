package nebcutekits.cutekits.Handlers;

import nebcutekits.cutekits.CuteKits;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CuteHandler implements Listener {
    CuteKits cuteKits;

    public CuteHandler(CuteKits cuteKits) {
        this.cuteKits = cuteKits;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entityDamager = event.getDamager();
        Entity entity = event.getEntity();

        if (entity.getType() != EntityType.PLAYER || entityDamager.getType() != EntityType.PLAYER) { return; }

        double attackDistance = entityDamager.getLocation().distance(entity.getLocation());
        double attackDistanceRounded = Math.floor(attackDistance * 1000)/1000;

        Player hitterPlayer = (Player) entityDamager;
        Player affectedPlayer = (Player) entity;

        hitterPlayer.sendActionBar("Your Reach: "+attackDistanceRounded);
        affectedPlayer.sendActionBar("Foe's Reach: "+attackDistanceRounded);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {//todo handle all these inventories
        String title = event.getView().getTitle();
        if (title.startsWith("cKits")) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            if (title.startsWith("cKits Menu"))
            {
                if (slot == 9+2) { player.performCommand("ck view personal"); }
                else if (slot == 9+4) { player.performCommand("ck view default"); }
                else if (slot == 9+6) { player.performCommand("ck view global"); }
            }
            else if (title.startsWith("cKits All Global Collections"))
            {

            }
            else if (title.startsWith("cKits All Default Collections"))
            {

            }
            else if (title.startsWith("cKits Player Collection"))
            {

            }
            else if (title.startsWith("cKits Default Collection"))
            {

            }
            else if (title.startsWith("cKits Player Kit"))
            {

            }
            else if (title.startsWith("cKits Default Kit"))
            {

            }

            event.setCancelled(true);
        }

        if (event.getView().getTitle().equals("cKits Menu")) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            if (slot == 9+2) {
                player.performCommand("ck view personal");
            } else if (slot == 9+4) {
                player.performCommand("ck view default");
            } else if (slot == 9+6) {
                player.performCommand("ck view global");
            }

            event.setCancelled(true);
        }
        if (event.getView().getTitle().startsWith("cKits Player")) {
            Player player = (Player) event.getWhoClicked();
            String collectionOwner = event.getView().getTitle().substring(13);
            int slot = event.getSlot();

            if (slot < 9*3) {
                if (player.getName().equals(collectionOwner)) {
                    player.performCommand("ck view personal " + (slot + 1));
                } else {
                    player.performCommand("ck view global " + collectionOwner + " " + (slot + 1));
                }
            }
            if (slot == (9*3)) {
                player.performCommand("ck view");
            }

            event.setCancelled(true);
        }
        if (event.getView().getTitle().startsWith("cKits Personal Kit")) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            int kitIndex = 0;

            String kitIndexStr = event.getView().getTitle().substring(19);
            try {
                kitIndex = Integer.parseInt(kitIndexStr);
                kitIndex--;
            } catch (NumberFormatException ignored) {}

            if (slot == (9*5)+8) {
                player.performCommand("ck load personal "+kitIndex);
            }
            if (slot == (9*5)) {
                player.performCommand("ck view personal");
            }

            event.setCancelled(true);
        }
        if (event.getView().getTitle().equals("cKits Default Collections"))
        {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            event.setCancelled(true);
        }
        if (event.getView().getTitle().equals("cKits Global Collections"))
        {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            event.setCancelled(true);
        }
    }
}
