package com.library.base.thread;

/**
 * Created by admin on 2016/11/16.
 */

public abstract class PriorityRunnable implements Runnable {

    private int priority = Thread.NORM_PRIORITY;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
