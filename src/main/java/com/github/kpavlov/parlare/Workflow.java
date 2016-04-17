package com.github.kpavlov.parlare;

/**
 * Marker interface for a sequence of operation.
 * <p>
 * With syntax sugar.
 */
public interface Workflow<SELF extends Workflow> {

    default Workflow when(Runnable operation) {
        operation.run();
        return this;
    }

    /**
     * Syntax sugar
     */
    default SELF given() {
        return (SELF) this;
    }

    /**
     * Syntax sugar
     */
    default SELF when() {
        return (SELF) this;
    }
}
