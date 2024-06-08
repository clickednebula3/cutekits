package nebcutekits.cutekits.Handlers;

import nebcutekits.cutekits.CuteKits;
import nebcutekits.cutekits.Utilities.ConfigReader;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CuteHandler implements Listener {
    CuteKits cuteKits;
    ConfigReader confReader;

    public CuteHandler(CuteKits cuteKits, ConfigReader confReader) {
        this.cuteKits = cuteKits;
        this.confReader = confReader;
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
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (event.getView().getTitle().startsWith("cKits")) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            String[] titleArgs = title.split(" ");

            if (title.startsWith("cKits Menu"))
            {
                if (slot == 9+2) { player.performCommand("ck view personal"); }
                else if (slot == 9+4) { player.performCommand("ck view default"); }
                else if (slot == 9+6) { player.performCommand("ck view global"); }
                else if (slot == 9*2) { player.getInventory().clear(); }
                else if (slot == (9*2)+8) { player.playSound(player.getLocation(), Sound.ENTITY_FOX_SLEEP, 100, 100); }
            }
            else if (title.startsWith("cKits All Global Collections"))
            {
                int pageIndex = 0;
                try { pageIndex = Integer.parseInt(titleArgs[4]); } catch (NumberFormatException ignored) {}

                if (slot < 9*3) {
                    ItemStack item = event.getCurrentItem();
                    if (item!=null && item.getItemMeta().getDisplayName().split(" ").length>1) {
                        player.performCommand("ck view global player "+item.getItemMeta().getDisplayName().split(" ")[1]);
                    }
                }
                if (slot == (9*3) && pageIndex <= 0) { player.performCommand("ck view"); }
                else if (slot == (9*3)) { player.performCommand("ck view global page "+(pageIndex)); }
                if (slot == (9*3)+8) { player.performCommand("ck view global page "+(pageIndex+2)); }
            }
            else if (title.startsWith("cKits All Default Collections"))
            {
                int pageIndex = 0;
                try { pageIndex = Integer.parseInt(titleArgs[4]); } catch (NumberFormatException ignored) {}

                if (slot < 9*3) {
                    ItemStack item = event.getCurrentItem();
                    if (item!=null && item.getItemMeta().getDisplayName().split(" ").length>1) {
                        player.performCommand("ck view default collection "+item.getItemMeta().getDisplayName().split(" ")[1]);
                    }
                }
                if (slot == (9*3) && pageIndex <= 0) { player.performCommand("ck view"); }
                else if (slot == (9*3)) { player.performCommand("ck view default page "+(pageIndex)); }
                if (slot == (9*3)+8) { player.performCommand("ck view default page "+(pageIndex+2)); }
            }
            else if (title.startsWith("cKits Player Collection"))
            {
                String collectionOwner = titleArgs[3];
                int pageIndex = 0;
                try { pageIndex = Integer.parseInt(titleArgs[4]); } catch (NumberFormatException ignored) {}

                if (slot < 9*3) {
                    if (player.getName().equals(collectionOwner)) { player.performCommand("ck view personal kit " + (pageIndex*9*3 + slot+1)); }
                    else { player.performCommand("ck view global player " + collectionOwner + " kit " + (pageIndex*9*3 + slot+1)); }
                }
                if (slot == (9*3) && pageIndex <= 0) {
                    if (player.getName().equals(collectionOwner)) { player.performCommand("ck view"); }
                    else { player.performCommand("ck view global"); }
                } else if (slot == (9*3)) { player.performCommand("ck view global player "+collectionOwner+" page "+(pageIndex)); }
                if (slot == (9*3)+8) { player.performCommand("ck view global player "+collectionOwner+" page "+(pageIndex+2)); }
                if (slot == 9*3+7 && player.getName().equals(collectionOwner)) {
                    player.performCommand("ck save current");
                    player.performCommand("ck view personal");
                }
            }
            else if (title.startsWith("cKits Default Collection"))
            {
                String collectionName = titleArgs[3];
                int pageIndex = 0;
                try { pageIndex = Integer.parseInt(titleArgs[4]); } catch (NumberFormatException ignored) {}

                if (slot < 9*3) {
                    ItemStack item = event.getCurrentItem();
                    if (item!=null && item.getItemMeta().getDisplayName().split(" ").length>2) {
                        player.performCommand("ck view default kit " + item.getItemMeta().getDisplayName().split(" ")[2]);
                    }
                }
                if (slot == (9*3) && pageIndex <= 0) { player.performCommand("ck view default"); }
                else if (slot == (9*3)) { player.performCommand("ck view default collection "+collectionName+" page "+(pageIndex)); }
                if (slot == (9*3)+8) { player.performCommand("ck view default collection "+collectionName+" page "+(pageIndex+2)); }
            }
            else if (title.startsWith("cKits Player Kit"))
            {
                String kitOwner = titleArgs[3];
                int kitIndex = 0;
                try { kitIndex = Integer.parseInt(titleArgs[4]); } catch (NumberFormatException ignored) {}

                if (slot == (9*5)+8) { player.performCommand("ck load global "+kitOwner+" "+kitIndex); }
                if (slot == (9*5)+7) { player.performCommand("ck save global "+kitOwner+" "+kitIndex); }
                if (slot == (9*5)+6 && Objects.equals(kitOwner, player.getName())) { player.performCommand("ck shout "+kitIndex); }
                if (slot == (9*5)+5 && Objects.equals(kitOwner, player.getName())) {
                    player.performCommand("ck delete "+kitIndex);
                    player.performCommand("ck view personal");
                }
                if (slot == (9*5)) { player.performCommand("ck view global player "+kitOwner); }
            }
            else if (title.startsWith("cKits Default Kit"))
            {
                String kitName = titleArgs[3];
                String collectionName = confReader.defaultCollections.get(confReader.getDefaultCollectionIndexUsingKitName(kitName)).collectionName;
                if (slot == (9*5)+8) { player.performCommand("ck load default "+kitName); }
                if (slot == (9*5)+7) { player.performCommand("ck save default "+kitName); }
                if (slot == (9*5)) { player.performCommand("ck view default collection "+collectionName); }
            }

            event.setCancelled(true);
        }
    }
}
