package tv.quaint.lobbypunch.runnables;

import org.bukkit.entity.Player;
import tv.quaint.lobbypunch.LobbyPunch;

public class PunchCooldown implements Runnable {
    public int cooldown;
    public int reset;
    public Player player;

    public PunchCooldown(int ticks, Player player) {
        cooldown = 0;
        reset = ticks;
        this.player = player;
    }

    @Override
    public void run() {
        if (cooldown <= 0) {
            done();

            cooldown = reset;
        }

        cooldown --;
    }

    public void done() {
        LobbyPunch.INSTANCE.getServer().getScheduler().cancelTask(LobbyPunch.onCooldown.get(player));
        LobbyPunch.onCooldown.remove(player);
    }
}
