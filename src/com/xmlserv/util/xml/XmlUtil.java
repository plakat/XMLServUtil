/*
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: Aug 12, 2002
 * Time: 7:32:59 PM
 *
 * $Id: XmlUtil.java,v 1.20 2009/03/26 16:55:07 muecke Exp $
 *
 * Written and (C) Copyright 2002 muecke.
 * Created: 03.01.2002
 * Last change: $Date: 2009/03/26 16:55:07 $
 */

package com.xmlserv.util.xml;

import com.xmlserv.util.*;
import com.xmlserv.util.exceptions.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;
import org.apache.log4j.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;


public class XmlUtil
{
    /**
     * Parse a given String, returning a jdom Element starting at tagname.
     *
     * @param xmlIn   String to parse
     * @param tagname The tag to cut out of the parsed string. Set to null to return the root element.
     */
    public static Element toElement(String xmlIn, String tagname)
            throws JDOMException, XMLServException
    {
        Logger logger = Logger.getLogger(XmlUtil.class);
        long startTstamp = System.currentTimeMillis();
        // Get content (an XML document) and parse it:
        SAXBuilder builder = new SAXBuilder();
        //System.out.println("\nXmlUtil.toElement():\n---\n"+xmlIn+"\n---\n\n");
        Document contentDoc = null;
        // todo: use XMLUtil.possiblyWellformed()
        if(!possiblyWellformed(xmlIn))
        {
            logger.debug("Input not wellformed, adding root element");
            xmlIn = "<xmlserv>\n" + xmlIn + "\n</xmlserv>";
        }
        try
        {
            contentDoc = builder.build(new StringReader(xmlIn));
        }
        catch(JDOMException je)
        {
            // Try to avoid parser error by surrounding with a pair of tags:
            logger.debug("XmlUtil: Adding root Element after parse error "+je.getMessage());
            xmlIn = "<xmlserv>\n" + HtmlUtil.cleanupHtml(xmlIn) + "\n</xmlserv>";
            //logger.debug("xmlIn="+xmlIn);
            // If it still does not parse, throw an error to the caller:
            try
            {
                contentDoc = builder.build(new StringReader(xmlIn));
            }
            catch(IOException ignored) // ignored as a StringWriter should not throw an IOException...
            {}
        }
        catch(IOException ignored) // ignored as a StringWriter should not throw an IOException...
        {}


        if(contentDoc == null)
        {
            throw new XMLServException("Error Parsing String: " + xmlIn, "-1: XMLUTIL-TOELEMENT-PARSE-ERROR", new Exception());
        }

        Element contentElement = ((tagname == null || tagname.length() == 0) ? contentDoc.getRootElement() :
                contentDoc.getRootElement().getChild(tagname));

        if(contentElement == null)
        {
            throw new XMLServException("ChildElement " + tagname + " not found.", "-2: XMLUTIL-TOELEMENT-CHILD-NOT-FOUND", new Exception());
        }
        // Remove reference to parent:
        contentElement.detach();

        long stopTstamp = System.currentTimeMillis();
        logger.debug("XmlUtil.toElement took "+(stopTstamp-startTstamp)+"ms");

        return contentElement;
    }


    /**
     * Parse a given String, returning a jdom Element starting at tagname.
     *
     * @param xmlIn String to parse
     */
    public static Element toElement(String xmlIn)
            throws JDOMException, XMLServException
    {
        return toElement(xmlIn, null);
    }


    protected static Pattern xmlStartPattern = Pattern.compile("^<[\\!\\?]");
    protected static Pattern startingElementPattern = Pattern.compile("^\\s*<([a-zA-Z0-9\\-_]+).*");

    public static boolean possiblyWellformed(String in)
    {
        // extract starting element:
        in = in.trim();
        Matcher startMatcher = xmlStartPattern.matcher(in);
        if(startMatcher.find())
            return true;
        

        Matcher m = startingElementPattern.matcher(in);
        if(!m.matches())
            return false;
        String startingElementName = m.group(1);

        // check if fragment ends with the corresponding closing pattern:
        Pattern endPattern = Pattern.compile(".*</"+startingElementName+"\\s*>$");
        Matcher endMatcher = endPattern.matcher(in);

        return endMatcher.matches();
    }


    /**
     * Convert an Element to a String
     *
     * @param el
     * @return The String representing the Element
     */
    public static String elementToString(Element el)
    {
        Document document = new Document(el);
        return documentToString(document);
    }


    public static String documentToString(Document in)
    {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());

        return out.outputString(in);
    }


    /**
     * Generate an alphabet Element containing the letters from A-Z.
     * @return The alphabet Element.
     */
    public static Element getAlphabet()
    {
        Element ret = new Element("alphabet");

        for(char i = (char)'A'; i <= (char)'Z'; i++)
        {
            Element tmp = new Element(Character.toString(i));
            ret.addContent(tmp);
        }

        return ret;
    }


    /**
     * Replace certain characters usually found when cp1252 text is pasted,
     * removing the rest of HTML4 forbidden characters in the range #x7F-#x9F.
     *
     * http://www.w3.org/TR/2006/REC-xml11-20060816/#NT-RestrictedChar
     *
     * @param in The String to correct
     * @return Cleaned up from characters in the range #x7F-#x9F
     */
    public static String cleanupControlCharacters(String in)
    {
        if(in == null)
            return "";

        return in.replaceAll("\\u0084", "\u201E")
                .replaceAll("\\u0093", "\u201C")
                .replaceAll("\\u0095", "\u2022")
                .replaceAll("\\u0096", "\u2013")
                .replaceAll("[\\u0000-\\u0008]", "")
                .replaceAll("[\\u000B-\\u000C]", "")
                .replaceAll("[\\u000E-\\u001F]", "")
                .replaceAll("[\\u007F-\\u0084]", "")
                .replaceAll("[\\u0086-\\u009F]", "");
    }
}
