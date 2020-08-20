package com.haier.uhome.os.javaandroid.uitls;

import android.text.TextUtils;
import android.util.Log;

import com.haier.uhome.os.javaandroid.BuildConfig;

/**
 * 日志类
 */
public class Logger {
    public static boolean releaseShow = !BuildConfig.DEBUG;
    public static final String defaultTag = "LoveCode";

    public static void d(String msg) {
        Log.d(defaultTag, head() + msg);
    }

    public static void d(String tag, String msg) {
        String t = tag;
        if (TextUtils.isEmpty(t)) {
            t = defaultTag;
        }
        Log.d(t, head() + msg);
    }

    public static void i(String msg) {
        Log.i(defaultTag, head() + msg);
    }

    public static void i(String tag, String msg) {
        String t = tag;
        if (TextUtils.isEmpty(t)) {
            t = defaultTag;
        }
        Log.i(t, head() + msg);
    }

    public static void i(String tag, String msg, boolean releaseShow) {
        if (Logger.releaseShow && releaseShow) {
            String t = tag;
            if (TextUtils.isEmpty(t)) {
                t = defaultTag;
            }
            Log.i(t, head() + msg);
        }
    }

    public static void e(String msg) {
        Log.e(defaultTag, head() + msg);
    }

    public static void e(String tag, String msg) {
        String t = tag;
        if (TextUtils.isEmpty(t)) {
            t = defaultTag;
        }
        Log.e(t, head() + msg);
    }

    private static String head() {
        Thread thread = Thread.currentThread();
        StackTraceElement[] stackTrace = thread.getStackTrace();
        int index = 4;
        String className = stackTrace[index].getClassName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();
        String stringBuilder = "[thread:" +
                thread.getName() +
                "]{" +
                className +
                "#" +
                methodName +
                ":" +
                lineNumber +
                "} ";
        return stringBuilder;
    }
}
