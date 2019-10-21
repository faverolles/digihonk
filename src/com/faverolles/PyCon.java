package com.faverolles;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;


class PyCon {

    private static String pythonPath = "H:\\Apps\\SDK\\Python\\3.7.4\\python ";
    private static String commandPrefix
            = Paths.get("itopy").toAbsolutePath().toString().concat(File.separator);
    private static String iotLogicPath = commandPrefix.concat("IoTLogic");

    /**
     * python readESPSerial.py
     */
    static void test() {
        String command = "H:\\Apps\\SDK\\Python\\3.7.4\\python H:\\Iot\\iotpy\\test.py";
        execute(command);
    }

    /**
     * python readESPSerial.py
     */
    static void readEsp() {
        String command = pythonPath.concat(commandPrefix).concat("readESPSerial.py");
        execute(command);
    }

    /**
     * python writeESPSerial.py -n text
     *
     * @param text New Text for ESP SSID
     */
    static void writeEsp(String text) {
        String command = pythonPath.concat(commandPrefix).concat("writeESPSerial.py -n ").concat(text);
        execute(command);
    }

    /**
     * python -c 'import IoTLogic; print IoTLogic.pycon_test_function()'
     */
    static void testEsp() {
        String x = "H:\\Apps\\SDK\\Python\\3.7.4\\python -c 'import IoTLogic; printIoTLogic.pycon_test_function()'";
        String command = pythonPath.concat(" -c 'import ").concat(iotLogicPath)
                .concat("; print ").concat(iotLogicPath).concat(".pycon_test_function()'");
        execute(x);
    }

    /**
     * python -c 'import IoTLogic; print IoTLogic.scan_wifi()'
     */
    static void scanWifi() {
        String command = getIoTLogicFunctionPath("scan_wifi");
        execute(command);
    }


    /**
     * python -c 'import IoTLogic; print IoTLogic.functionName'
     *
     * @param functionName Function in IoTLogic to call.
     *                     Can pass just the functionName or functionName()
     */
    static void ioTLogic(String functionName) {
        String command = getIoTLogicFunctionPath(functionName);
        execute(command);
    }

    /**
     * Utility method to build the function path call within IoTLogic.py
     *
     * @param name Function in IoTLogic to call.
     *             Can pass just the functionName or functionName()
     * @return python call path String
     */
    private static String getIoTLogicFunctionPath(String name) {
        String callName = !name.contains("()") ? name.concat("()") : name;
        return pythonPath.concat(" -c 'import ").concat(iotLogicPath)
                .concat("; print ").concat(iotLogicPath).concat(callName);
    }

    /**
     * Execute python package iotpy using java Runtime exec()
     *
     * @param command python command
     */
    static void execute(String command) {
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
