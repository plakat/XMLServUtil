package com.xmlserv.util.db;

import java.util.regex.*;

/**
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: 20.03.12
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class PSTHUtil
{
    public static String extractCondition(String query)
    {
        Matcher m = wherePattern.matcher(query);
        query = m.replaceAll("");
        m = cleanupTail.matcher(query);
        return m.replaceAll("");
    }


    public static Pattern wherePattern = Pattern.compile(".*where\\s+", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE|Pattern.DOTALL);
    public static Pattern cleanupTail = Pattern.compile("(((group|order)\\s+by)|(having)).*", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE|Pattern.DOTALL);
}
