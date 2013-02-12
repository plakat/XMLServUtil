package com.xmlserv.util;

import org.apache.log4j.*;

import java.io.*;

/**
 * User: muecke
 * Date: 26.03.2009
 * Time: 16:38:12
 */
public class LogUtil
{
    public static void logException(Logger logger, Exception e)
    {
        if(e != null)
        {
//            StringBuilder exception = new StringBuilder(e.getMessage() != null ? e.getMessage() : "("+e.getClass().getName()+": message==null)").append(
//                    ":\n");
//            StackTraceElement[] stackTraceElements = e.getStackTrace();
//            for(StackTraceElement stackTraceElement : stackTraceElements)
//            {
//                exception.append(stackTraceElement.toString()).append("\n");
//            }
//
//            logger.warn(exception.toString());
            StringWriter exception = new StringWriter();
            e.printStackTrace(new PrintWriter(exception));
            logger.warn(exception.toString());
        }
        else
        {
            logger.warn("Trying to log null exception");
        }
    }


    public static void logDebugException(Logger logger, Exception e)
    {
        if(e != null)
        {
            StringBuffer exception = new StringBuffer(e.getMessage() != null ? e.getMessage() : "(exception message==null)").append(":\n");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for(StackTraceElement stackTraceElement : stackTraceElements)
            {
                exception.append(stackTraceElement.toString()).append("\n");
            }

            logger.debug(exception.toString());
        }
        else
        {
            logger.warn("Trying to log null exception");
        }
    }
}
