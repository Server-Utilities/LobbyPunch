package tv.quaint.lobbypunch;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tv.quaint.lobbypunch.listeners.MainListener;
import tv.quaint.lobbypunch.runnables.PunchCooldown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class LobbyPunch extends JavaPlugin {
    public static HashMap<Player, Integer> onCooldown = new HashMap<>();

    public static LobbyPunch INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(new MainListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void startCooldown(Player player) {
        onCooldown.put(player, getServer().getScheduler().scheduleSyncRepeatingTask(this, new PunchCooldown(3, player), 0, 1));
    }
}
