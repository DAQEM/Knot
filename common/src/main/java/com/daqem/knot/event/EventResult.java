package com.daqem.knot.event;

public enum EventResult {
    PASS(false, null),
    INTERRUPT(true, null),
    INTERRUPT_TRUE(true, true),
    INTERRUPT_FALSE(true, false);

    private final boolean interrupts;
    private final Boolean value;

    EventResult(boolean interrupts, Boolean value) {
        this.interrupts = interrupts;
        this.value = value;
    }

    public boolean interrupts() {
        return interrupts;
    }

    public boolean cancelsEvent() {
        return Boolean.FALSE.equals(value);
    }
}
