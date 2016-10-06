package lv.ctco.helpers;

public class ProcessHelper {

    public static boolean isProcessRunning(Process process) {
        try {
            process.exitValue();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
