package nebcutekits.cutekits.Handlers;

import nebcutekits.cutekits.CuteKits;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
        double attackDistanceRounded = attackDistance % 0.01;

        Player hitterPlayer = (Player) entityDamager;
        Player affectedPlayer = (Player) entity;

        hitterPlayer.sendActionBar("Your Reach: "+attackDistanceRounded);
        affectedPlayer.sendActionBar("Foe's Reach: "+attackDistanceRounded);
    }
}
