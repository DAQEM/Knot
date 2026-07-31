package com.daqem.knot.events.compat.apothic_enchanting;

public class ApothicEnchantingCompat {

    private static final ApothicEnchantingCompat INSTANCE = new ApothicEnchantingCompat();

    private final ThreadLocal<Integer> capturedCost = new ThreadLocal<>();

    private ApothicEnchantingCompat() {}

    public static ApothicEnchantingCompat getInstance() {
        return INSTANCE;
    }

    public void setCapturedCost(int cost) {
        this.capturedCost.set(cost);
    }

    public int getCapturedCost(int defaultCost) {
        Integer cost = this.capturedCost.get();
        return cost != null ? cost : defaultCost;
    }

    public void clearCapturedCost() {
        this.capturedCost.remove();
    }
}