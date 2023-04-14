package me.sakyce.pizzatime;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

// server//
public class HurryUpHandler {
    private static final Map<Player, HurryUp> currentPizzatimes = new HashMap<>();
    public static void tick() {
        for (Map.Entry<Player, HurryUp> entry : currentPizzatimes.entrySet()) {
            entry.getValue().tick();
        }
    }
    public static void start(Player player) {
        if (currentPizzatimes.containsKey(player)) {
            Pizzatime.getLogger().info("Pizza time already started!");
            return;
        }
        currentPizzatimes.put(player, new HurryUp(player));
    }
    public static void newlap(Player player) {
        if (currentPizzatimes.containsKey(player)) {
            currentPizzatimes.get(player).newlap();
        }
    }
    public static void expire(Player player) {
        if (currentPizzatimes.containsKey(player)) {
            currentPizzatimes.get(player).expire();
        }
    }
    public static void victory(Player player) {
        if (currentPizzatimes.containsKey(player)) {
            currentPizzatimes.get(player).victory();
        }
    }
    public static void remove(Player player) {
        currentPizzatimes.remove(player);
    }
}
