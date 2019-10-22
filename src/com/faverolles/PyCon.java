package com.faverolles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class PyCon {

    private static String prefixPath = "H:\\Apps\\SDK\\Python\\3.7.4\\python H:\\IoT\\iotpy\\";

    /**
     * Executes Command: python testPyCon.py
     */
    static Map<String, List<String>> testPyCon() {
        return execute(prefixPath.concat("testPyCon.py"));
    }

    /**
     * Executes Command: python readESPSerial.py
     */
    static Map<String, List<String>> readEsp() {
        return execute(prefixPath.concat("readESPSerial.py"));
    }

    /**
     * Executes Command: python writeESPSerial.py -n {@param text}
     * @param text New Text for ESP SSID
     */
    static Map<String, List<String>> writeEsp(String text) {
        return execute(prefixPath.concat("writeESPSerial.py -n ").concat(text));
    }


    /**
     * Executes Command: python scanWifi.py
     */
    static Map<String, List<String>> scanWifi() {
        return execute(prefixPath.concat("scanWifi.py"));
    }


    /**
     * Executes the main method in a python file
     * from the package iotpy using java Runtime exec()
     *
     * @param command python command to run
     */
    private static Map<String, List<String>> execute(String command) {
        String result = null;
        Map<String, List<String>> resMap = new HashMap<>();
        resMap.put("out", new ArrayList<>());
        resMap.put("err", new ArrayList<>());
        try {

            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((result = stdIn.readLine()) != null) {
                resMap.get("out").add(result);
            }
            while ((result = stdErr.readLine()) != null) {
                resMap.get("err").add(result);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION Class[PyCon] Method[execute]\n" + e);
        }
        return resMap;
    }

    /**
     * Prints the "out" and "err" values from {@param map}
     * @param map Map<String, List<String>>
     */
    static void print(Map<String, List<String>> map) {
        map.get("out").forEach(System.out::println);
        map.get("err").forEach(System.out::println);
    }
}
