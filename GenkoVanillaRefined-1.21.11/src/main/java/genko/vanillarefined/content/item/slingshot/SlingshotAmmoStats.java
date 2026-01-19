package genko.vanillarefined.content.item.slingshot;

public record SlingshotAmmoStats(float damage, float rangeMultiplier, boolean appliesStuck, int stuckTicks) {

    public static SlingshotAmmoStats of(float damage, float rangeMultiplier) {
        return new SlingshotAmmoStats(damage, rangeMultiplier, false, 0);
    }

    public static SlingshotAmmoStats ofStuck(float damage, float rangeMultiplier, int stuckTicks) {
        return new SlingshotAmmoStats(damage, rangeMultiplier, true, stuckTicks);
    }
}
