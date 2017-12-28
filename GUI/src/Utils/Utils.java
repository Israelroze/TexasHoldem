package Utils;

public class Utils {
    static Boolean PrintLog = false;
        public static void sleepForAWhile(long sleepTime) {
            if (sleepTime != 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignored) {

                }
            }
        }

        public static void log(String message) {
           if(PrintLog) System.out.println(message);
        }
    }


