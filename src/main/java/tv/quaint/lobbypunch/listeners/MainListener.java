package tv.quaint.lobbypunch.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;
import tv.quaint.lobbypunch.LobbyPunch;

import java.util.ArrayList;
import java.util.List;

public class MainListener implements Listener {
    public MainListener() {
        LobbyPunch.INSTANCE.getLogger().info("Registered Listener!");
    }

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (entity instanceof Player hit) {
            if (! hit.hasPermission("lobbypunch.hit")) return;
            if (damager instanceof Player puncher) {
                if (! puncher.hasPermission("lobbypunch.punch")) return;
                if (LobbyPunch.onCooldown.containsKey(puncher)) return;
                if (hit.hasPermission("lobbypunch.void")) return;

                if (hit.hasPermission("lobbypunch.nope")) {
                    doPunch(puncher, puncher);
                    return;
                }

                doPunch(hit, puncher);
            }
        }
    }

    @EventHandler
    public void onPunch(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player puncher = event.getPlayer();

        if (entity instanceof Player hit) {
            if (! hit.hasPermission("lobbypunch.hit")) return;
            if (! puncher.hasPermission("lobbypunch.punch")) return;
            if (LobbyPunch.onCooldown.containsKey(puncher)) return;
            if (hit.hasPermission("lobbypunch.void")) return;

            if (hit.hasPermission("lobbypunch.nope")) {
                doPunch(puncher, puncher);
                return;
            }

            doPunch(hit, puncher);
        }
    }

    public void doPunch(Player punched, Player puncher) {
        punched.setVelocity(new Vector(0, 10, 0));

        for (Player online : getOnline()) {
            online.playSound(punched.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
        }

        LobbyPunch.INSTANCE.startCooldown(puncher);
    }

    public List<Player> getOnline() {
        return new ArrayList<>(LobbyPunch.INSTANCE.getServer().getOnlinePlayers());
    }
}
