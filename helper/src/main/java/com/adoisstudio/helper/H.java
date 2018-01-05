package com.adoisstudio.helper;

import android.util.Log;

/**
 * Created by amitkumar on 05/01/18.
 */

public class H {

    //Documents on http://www.adoisstudio.com/android/helper/log
    public static void log(String tag, String msg) {

        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("log") == 0) {
                currentIndex = i + 1;
                break;
            }
        }

        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName
                .lastIndexOf(".") + 1);
        String methodName = stackTraceElement[currentIndex].getMethodName();
        String lineNumber = String
                .valueOf(stackTraceElement[currentIndex].getLineNumber());

        Log.e(tag, msg + "\n" + className + "." + methodName + "(" + className + ".java:" + lineNumber + ")");
        //Log.e(tag, msg);
    }//log

}
