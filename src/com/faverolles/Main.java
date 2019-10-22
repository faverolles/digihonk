package com.faverolles;

import java.util.*;

public class Main {

    private static final long TICK_COUNT = 10;
    private static final long delay = 1000;
    private static final long period = 1000;

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        System.out.println("Press Enter To Start");
        scn.nextLine();

//        String timeStamp = String.valueOf(System.currentTimeMillis());
//        System.out.println("Writing TimeStamp To ESP: " + timeStamp);
//        PyCon.print(PyCon.writeEsp(timeStamp));

        System.out.println("Starting Ticker");
        Timer timer = new Timer();
        Ticker ticker = new Ticker(TICK_COUNT);
        timer.scheduleAtFixedRate(new ScanWifiTask(timer, ticker), delay, period);
    }
}

class ScanWifiTask extends TimerTask implements Runnable {
    private Ticker ticker;
    private Timer timer;

    ScanWifiTask(Timer timer, Ticker ticker) {
        this.ticker = ticker;
        this.timer = timer;
    }

    @Override
    public void run() {
        System.out.println(String.format(
                "Current Tick[ %d ] Time[ %d ]", this.ticker.tick(), System.currentTimeMillis()));

        Map<String, List<String>> map = PyCon.scanWifi();
        PyCon.print(map);

        if (this.ticker.ticks >= this.ticker.stop) {
            this.timer.cancel();
        }
    }
}

class Ticker {
    long ticks, stop;

    Ticker(long stop) {
        this.stop = stop;
    }

    long tick() {
        return ++this.ticks;
    }
}
