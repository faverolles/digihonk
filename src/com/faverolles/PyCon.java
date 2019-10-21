package com.faverolles;

import java.io.BufferedReader;
import java.io.InputStreamReader;


class PyCon {

    private static String prefixPath = "H:\\Apps\\SDK\\Python\\3.7.4\\python H:\\IoT\\iotpy\\ ";

    /**
     * python test.py
     */
    static void testPyCon() {
        String command = prefixPath.concat("testPyCon.py");
        execute(command);
    }

    /**
     * python readESPSerial.py
     */
    static void readEsp() {
        String command = prefixPath.concat("readESPSerial.py");
        execute(command);
    }

    /**
     * python writeESPSerial.py -n {@param text}
     *
     * @param text New Text for ESP SSID
     */
    static FInterface.FIB writeEsp = (String text) ->
            execute(prefixPath.concat("writeESPSerial.py -n ").concat(text));

    /**
     * python -c 'import IoTLogic; print IoTLogic.scan_wifi()'
     */
    static ILambda scanWifi = () -> execute(prefixPath.concat("scanWifi.py"));


    /**
     * Execute python package iotpy using java Runtime exec()
     *
     * @param command python command
     */
    private static void execute(String command) {
        String result = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            System.out.println("PyCon Command Output: ");
            while ((result = stdIn.readLine()) != null) {
                System.out.println(result);
            }

            System.out.println("PyCon Command Errors: ");
            while ((result = stdErr.readLine()) != null) {
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION Class[PyCon] Method[execute]\n" + e);
        }
    }

}
