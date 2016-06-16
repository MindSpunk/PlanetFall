package com.spark.planetfall.utils;

public class Log {

    private static String logInfoTag = "[INFO]";
    private static String logWarnTag = "[WARNING]";
    private static String critWarnTag = "[CRITICAL]";

    public static void logInfo(String message) {
        System.out.println(logInfoTag + message);
    }

    public static void logWarn(String message) {
        System.out.println(logWarnTag + message);
    }

    public static void logCrit(String message) {
        System.out.println(critWarnTag + message);
    }

    public static void logCustom(String customTagName, String message) {
        customTagName = ("[" + customTagName + "]");
        System.out.println(customTagName + message);
    }

}
