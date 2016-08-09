package com.zlm.demo.leshidemo;

/**
 * Created by zhaoliming on 2016/8/9.
 */
public class Duration {
    private long duration;
    private long currentDuration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(long currentDuration) {
        this.currentDuration = currentDuration;
    }

    @Override
    public String toString() {
        return "Duration{" +
                "duration=" + duration +
                ", currentDuration=" + currentDuration +
                '}';
    }
}
