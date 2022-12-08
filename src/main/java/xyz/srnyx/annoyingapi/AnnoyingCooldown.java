package xyz.srnyx.annoyingapi;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * This class is used to create and manage cooldowns for players
 */
public class AnnoyingCooldown {
    @NotNull private final AnnoyingPlugin plugin;
    @NotNull private final UUID uuid;
    @NotNull private final CooldownType type;

    /**
     * Creates and starts a cooldown of the specified {@link CooldownType} for the specified player
     *
     * @param   plugin  the plugin that is creating the cooldown
     * @param   uuid    the player's UUID
     * @param   type    the cooldown type
     */
    @Contract(pure = true)
    public AnnoyingCooldown(@NotNull AnnoyingPlugin plugin, @NotNull UUID uuid, @NotNull CooldownType type) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.type = type;
    }

    /**
     * Starts the cooldown
     */
    public void start() {
        plugin.cooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(type, System.currentTimeMillis() + type.getDuration());
    }

    /**
     * Stops the cooldown
     */
    public void stop() {
        final Map<CooldownType, Long> cooldowns = plugin.cooldowns.get(uuid);
        if (cooldowns != null) cooldowns.remove(type);
    }

    /**
     * A {@code long} of the time remaining on the cooldown
     *
     * @return  amount of time left in the cooldown (in milliseconds)
     *
     * @see     AnnoyingCooldown#getRemainingPretty(String)
     */
    public long getRemaining() {
        final Map<CooldownType, Long> map = plugin.cooldowns.get(uuid);
        if (map == null) return 0;
        final Long time = map.get(type);
        if (time == null) return 0;
        return time - System.currentTimeMillis();
    }

    /**
     * {@link #getRemaining()} but pretty
     *
     * @param   pattern the pattern to format the time with
     *
     * @return          the formatted time left in the cooldown
     *
     * @see             AnnoyingCooldown#getRemaining()
     */
    public String getRemainingPretty(@Nullable String pattern) {
        return AnnoyingUtility.formatMillis(getRemaining(), pattern);
    }

    /**
     * The duration of the {@link CooldownType}
     *
     * @return  how long the cooldown is (in milliseconds)
     *
     * @see     CooldownType#getDuration()
     */
    public long getDuration() {
        return type.getDuration();
    }

    /**
     * A {@code boolean} of whether the cooldown is active. If the cooldown is not active, it will be removed from {@link AnnoyingPlugin#cooldowns}
     *
     * @return  whether the player is on cooldown
     */
    public boolean isOnCooldown() {
        final boolean onCooldown = getRemaining() > 0;
        if (!onCooldown) stop();
        return onCooldown;
    }

    /**
     * Implement this interface to create your own cooldown types
     */
    public interface CooldownType {
        /**
         * Returns the cooldown's duration in milliseconds
         *
         * @return  the duration of the cooldown in milliseconds
         */
        long getDuration();
    }
}
