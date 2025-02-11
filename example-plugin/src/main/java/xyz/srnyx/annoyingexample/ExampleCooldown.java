package xyz.srnyx.annoyingexample;

import org.jetbrains.annotations.Contract;

import xyz.srnyx.annoyingapi.AnnoyingCooldown;


/**
 * Example of a {@link AnnoyingCooldown.CooldownType} implementation
 */
public enum ExampleCooldown implements AnnoyingCooldown.CooldownType {
    /**
     * Example cooldown
     */
    EXAMPLE(3000);

    /**
     * Cooldown duration in milliseconds
     */
    private final long duration;

    /**
     * Constructor for the {@link AnnoyingCooldown.CooldownType}
     *
     * @param   duration  cooldown duration in milliseconds
     */
    @Contract(pure = true)
    ExampleCooldown(long duration) {
        this.duration = duration;
    }

    @Override @Contract(pure = true)
    public long getDuration() {
        return duration;
    }
}
