package com.warpaint.challengeservice;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StopWatch {

    private List<Long> laps = new ArrayList<>(2000);
    private long lapStarted = System.nanoTime();

    public void lap() {
        laps.add((System.nanoTime() - lapStarted)/1000);
        lapStarted = System.nanoTime();
    }

    public String toString() {
        return "Run times:\n" + StringUtils.join(laps, " us\n")
                + " us\nsum:" + laps.stream().mapToLong(Long::longValue).sum() + " us";
    }
}
