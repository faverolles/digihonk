package com.faverolles;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Main {

    private static final long TICK_COUNT = 10;
    private static final long delay = 1000;
    private static final long period = 1000;

    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
//        System.out.println("Press Enter To Start");
//        scn.nextLine();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        System.out.println("Writing TimeStamp To ESP: " + timeStamp);
        PyCon.print(PyCon.writeEsp(timeStamp));

        SortWifiTask sortWifiTask = new SortWifiTask();
        Thread sortThread = new Thread(sortWifiTask);
        sortThread.start();

        System.out.println("Starting Ticker");
        Timer timer = new Timer();
        Ticker ticker = new Ticker(TICK_COUNT);
        timer.scheduleAtFixedRate(new ScanWifiTask(timer, ticker), delay, period);

    }
}

class SortWifiTask implements Runnable {

    private static List<String> detectedSignalsList = new ArrayList<>();
    private static boolean stop = false;

    SortWifiTask() {
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println(String.format("Sorting Items[ %d ]", detectedSignalsList.size()));
            Main.lock.lock();
            Collections.sort(detectedSignalsList);
            System.out.println(String.format("Printing Items[ %d ]", detectedSignalsList.size()));
            detectedSignalsList.forEach(System.out::println);
            Main.lock.unlock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("EXCEPTION: SortWifiTask.run() Unable to sleep.");
                System.out.println(e.toString());
            }
        }
    }

    static void addSignals(List<String> list) {
        Main.lock.lock();
        list.forEach(e -> {
            if (!SortWifiTask.detectedSignalsList.contains(e)) {
                SortWifiTask.detectedSignalsList.add(e);
            }
        });
        Main.lock.unlock();
    }

    static void stopSorting() {
        Main.lock.lock();
        stop = true;
        Main.lock.unlock();
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

        // faverolles 10/29/2019 [A]: check if there is a possibility for map.get("out") to be null
        //  it wont matter if it is an empty list. It just cant be null
        try {
            List<String> results = map.get("out").stream().map(e ->
                    e.replace("DGhonk-", "")).collect(Collectors.toList());

            System.out.println(String.format("Sending [ %d ] detected signals to be sorted", results.size()));
            SortWifiTask.addSignals(results);
        } catch (Exception e) {
            System.out.println("EXCEPTION: ScanWifiTask.run() Error cleaning list");
            System.out.println(e.toString());
        }

        if (this.ticker.ticks >= this.ticker.stop) {
            this.timer.cancel();
            SortWifiTask.stopSorting();
            System.out.println("Waiting period after car stops is finished");
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
