package lv.ctco.beans;

import lv.ctco.enums.LogType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogItem {

    private long logDate;
    private LogType logType;
    private String message;

    public LogItem(long logDate, LogType logType, String message) {
        this.logDate = logDate;
        this.logType = logType;
        this.message = message;
    }

    public long getLogDate() {
        return logDate;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getMessage() {
        return message;
    }

    public String getLogDateFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(new Date(logDate));
    }
}
