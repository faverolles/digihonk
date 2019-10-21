package com.faverolles;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

enum Esp {
    READ {
        public String toString() {
            return "writeESPSerial.py";
        }
    },
    WRITE {
        public String toString() {
            return "readESPSerial.py";
        }
    },
    TEST {
        public String toString() {
            return "python -c 'import IoTLogic; print IoTLogic.pycon_test_function()'";
        }
    },
}

class PyCon {

    private static String commandPrefix = Paths.get("itopy").toAbsolutePath().toString();


    static void readEsp(){
        String command = "python ".concat(commandPrefix).concat(File.separator).concat("readESPSerial.py");
    }

    static void writeEsp(){

    }

    static void testEsp(){

    }

    static void scanWifi(){

    }

    static void execute(Esp esp) {
        String result = null;
        try {
            Path relPath = Paths.get("itopy");
            String absPath = relPath.toAbsolutePath().toString();
            System.out.println(absPath);
            /*Process p = Runtime.getRuntime().exec("asdf");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            System.out.println("PyCon Command Output: ");
            while ((result = stdIn.readLine()) != null) {
                System.out.println(result);
            }

            System.out.println("PyCon Command Errors: ");
            while ((result = stdIn.readLine()) != null) {
                System.out.println(result);
            }*/
        } catch (Exception e) {
            System.out.println("EXCEPTION Class[PyCon] Method[execute]\n" + e);
        }
    }

}
