package com.faverolles;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Main {

    private static final long TICK_COUNT = 10;
    private static final long delay = 1000;
    private static final long period = 1000;

    static final ReentrantLock lock = new ReentrantLock();

    static String myTimeStamp = "9999999999999999999999999999999999999";


    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        boolean runModeContinuous = false;
        int counter = 1;
        while (counter > 0) {
            System.out.println("Run Mode Continuous (y/n)");
            String input = scn.nextLine();
            if (input.equals("n") || input.equals("y")) {
                runModeContinuous = !input.equals("n");
                break;
            }
            counter--;
        }

        myTimeStamp = String.valueOf(System.currentTimeMillis());
        System.out.println("Writing TimeStamp To ESP: " + myTimeStamp);
        PyCon.print(PyCon.writeEsp(myTimeStamp));

        SortWifiTask sortWifiTask = new SortWifiTask();
        Thread sortThread = new Thread(sortWifiTask);
        sortThread.start();

        System.out.println("Starting Ticker");
        Timer timer = new Timer();
        Ticker ticker = new Ticker(TICK_COUNT);
        timer.scheduleAtFixedRate(new ScanWifiTask(timer, ticker, runModeContinuous), delay, period);

    }
}

class SortWifiTask implements Runnable {

    private static List<String> detectedSignalsList = new ArrayList<>();
    private static boolean stop = false;

    SortWifiTask() { }

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

    static String getMin() {
        String smallest;
        Main.lock.lock();
        try {
            smallest = SortWifiTask.detectedSignalsList.get(0);
            smallest = String.valueOf(SortWifiTask.detectedSignalsList.stream().min(String::compareTo));
        }catch (Exception e){
            System.out.println("No signals to return from sorting");
            smallest = "-1";
        }
        Main.lock.unlock();
        return smallest;
    }
}

class ScanWifiTask extends TimerTask implements Runnable {
    private Ticker ticker;
    private Timer timer;
    private boolean runModeContinuous;

    ScanWifiTask(Timer timer, Ticker ticker, boolean runModeContinuous) {
        this.ticker = ticker;
        this.timer = timer;
        this.runModeContinuous = runModeContinuous;
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
        if (!runModeContinuous) {
            if (this.ticker.ticks >= this.ticker.stop) {
                this.timer.cancel();
                SortWifiTask.stopSorting();
                System.out.println("Waiting period after car stops is finished");
            }
        } else {
            if(Main.myTimeStamp.equals(SortWifiTask.getMin())){
                this.timer.cancel();
                SortWifiTask.stopSorting();
                System.out.println("It's my turn. Stopping algorithm");
            }
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
