/*
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: 22.11.2002
 * Time: 14:04:27
 *
 * $Id: HtmlUtil.java,v 1.14 2007/12/30 12:34:38 muecke Exp $
 *
 * Written and Copyright (C) 2002-2012 Wolfram Saringer (muecke).
 * Created: 03.01.2002
 * Last change: $Date: 2007/12/30 12:34:38 $
 */

package com.xmlserv.util;

import com.xmlserv.util.exceptions.*;
import org.apache.log4j.*;
import org.apache.regexp.*;

import java.io.*;
import java.util.regex.*;


public class HtmlUtil
{
    /**
     * convert plain text to some HTML look-a-like:
     * <ul>
     * <li>Convert linebreaks to &lt;br&gt;
     * <li>Convert &lt;, &gt; and &amp; to entities
     * </ul>
     */
    public static String htmlize(String in)
    {
        String out = in;

        try
        {
            RE ampEntity = new RE("&");
            out = ampEntity.subst(out, "&amp;");

            RE ltEntity = new RE("<");
            out = ltEntity.subst(out, "&lt;");

            RE gtEntity = new RE(">");
            out = gtEntity.subst(out, "&gt;");

            //[]
            RE link = new RE("\\s(http://[^\"\\s]+)\\s");
            int i = 0;
            while(link.match(out))
            {
                String thisLink = link.getParen(1);
                out = link.subst(out, " <a href=\""+thisLink+"\" target=\"_blank\">"+thisLink+"</a> ", RE.REPLACE_FIRSTONLY);
                //System.out.println("Iteration "+i+": thisLink="+thisLink+", out="+out);
                i++;
            }

            RE linebreak = new RE("\n");
            out = linebreak.subst(out, "<br/>\n");
        }
        catch(RESyntaxException res)
        { res.printStackTrace(); }

        return out;
    }


    /**
     * Repais minor glitches (differences between HTML and XHTML):
     * <ul>
     * <li>Close all &lt;br&gt; tags</li>
     * <li>Close all &lt;hr&gt; tags</li>
     * <li>Close all &lt;img&gt; tags</li>
     * <li>Ensure that a surrounding tag pair exists</li>
     * <li>Replace HTML entities with numeric entities</li>
     * </ul>
     */
    public static String repair(String in)
    throws XMLServException
    {
        String out = in;
        out = brPattern.matcher(out).replaceAll("<br />");

        out = hrPattern.matcher(out).replaceAll("<hr />");

        out = imgPattern.matcher(out).replaceAll("<img $1 />");

        for(int i=0; i<entityPattern.length;i++)
        {
            out = entityPattern[i].matcher(out).replaceAll(numEntities[i]);
        }

        out = ampPattern.matcher(out).replaceAll("&amp;");
        
        out = com.xmlserv.util.XmlUtil.cleanupControlCharacters(out);
        
        // new version: just try parsing and provide surrounding tag if that fails:
        try
        {
            XmlUtil.toElement(out, null);
        }
        catch(org.jdom.JDOMException e)
        {
            out = "<xmlserv>" + out + "</xmlserv>";
        }

        return out;
    }


    static Pattern brPattern = Pattern.compile("<br *>", Pattern.CASE_INSENSITIVE);
    static Pattern hrPattern = Pattern.compile("<hr *>", Pattern.CASE_INSENSITIVE);
    static Pattern imgPattern = Pattern.compile("<img( [^>]*[^/])>", Pattern.CASE_INSENSITIVE);
    static Pattern[] entityPattern = new Pattern[] {
            Pattern.compile("&quot;"),
            Pattern.compile("&auml;"),
            Pattern.compile("&Auml;"),
            Pattern.compile("&ouml;"),
            Pattern.compile("&Ouml;"),
            Pattern.compile("&uuml;"),
            Pattern.compile("&Uuml;"),
            Pattern.compile("&szlig;"),
            Pattern.compile("&euro;"),
            Pattern.compile("&nbsp;"),
            Pattern.compile("&ndash;"),
            Pattern.compile("&mdash;")
    };

    private static String[] numEntities = new String[] {
            "\"", // quote
            "&#228;", // auml
            "&#196;", // Auml
            "&#246;", // ouml
            "&#214;", // Ouml
            "&#252;", // uuml
            "&#220;", // Uuml
            "&#223;", // szlig
            "&#8364;",// euro
            "&#160;", // nbsp
            "&#8211;",// ndash
            "&#8212;",// mdash
    };

    private static Pattern ampPattern = Pattern.compile("(?!&(#[0-9]+|amp|lt|gt|[AOUaou]uml|szlig|euro|nbsp|[nm]dash);)&");
    

    /**
     * The XMLServ Logo as HTML with CSS styles.
     */
    public static final String XMLServLogo = "<span style='font-family: Arial,sans-serif; font-size: 110%; text-transform: uppercase;'>XML</span>" +
                                             "<span style='font-family: Times, serif; font-style: italic; vertical-align: 20%; font-size: 90%; text-transform: capitalize;'>Serv</span>";


    /**
     * simple text -> HTML converter. Reads text from stdin and writes converted text to stdout.
     */
    public static void main(String[] args)
    {
        try
        {
            StringBuffer in = new StringBuffer();
            BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
            String line = "";

            System.out.println("Waiting for keyboard input (end with CRTL-D) ...");

            while((line = inReader.readLine()) != null)
            {
                in.append(line);
                // append newline (not returned by readLine()):
                in.append("\n");
            }

            System.out.println("Htmlutil.htmlize():");
            System.out.println(HtmlUtil.htmlize(in.toString()));

            System.out.println("Htmlutil.repair():");
            System.out.println(HtmlUtil.repair(in.toString()));
        }
        catch(Exception e)
        {
            LogUtil.logException(Logger.getLogger(HtmlUtil.class), e);
        }
    }
}
