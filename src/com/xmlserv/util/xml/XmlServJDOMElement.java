package com.xmlserv.util.xml;

import com.xmlserv.util.*;
import com.xmlserv.util.exceptions.*;
import com.xmlserv.util.xml.*;
import org.jdom.*;

import java.text.*;
import java.util.*;

/**
 * User: muecke
 * Date: 01.09.2008
 * Time: 19:17:46
 */
public class XmlServJDOMElement
        extends Element
{
    protected XmlServJDOMElement()
    {
        super();
    }


    public XmlServJDOMElement(String s)
    {
        super(s);
    }


    public XmlServJDOMElement(String s, String s1)
    {
        // todo: com.xmlserv.util.XmlUtil.cleanupControlCharacters()
        super(s, s1);
    }


    public XmlServJDOMElement(String s, String s1, String s2)
    {
        // todo: com.xmlserv.util.XmlUtil.cleanupControlCharacters()
        super(s, s1, s2);
    }


    public XmlServJDOMElement(String s, Namespace namespace)
    {
        super(s, namespace);
    }


    public Element addContent(String s)
    {
        // todo: com.xmlserv.util.XmlUtil.cleanupControlCharacters()
        if(s == null)
            return super.addContent("");
        else
            return super.addContent(s);
    }


    public Element addContent(int value)
    {
        return super.addContent(Integer.toString(value));
    }


    public Element addContent(long value)
    {
        return super.addContent(Long.toString(value));
    }


    public Element addContent(float value)
    {
        return super.addContent(Float.toString(value));
    }


    public Element addContent(double value)
    {
        return super.addContent(Double.toString(value));
    }

    public Element addContent(XMLAbstraction obj)
    {
        return super.addContent(obj.toElement());
    }
    

    public Element setAttribute(String name, String value)
    {
        // todo: com.xmlserv.util.XmlUtil.cleanupControlCharacters()
        if(value == null)
            return super.setAttribute(XmlUtil.cleanupControlCharacters(name), "");
        else
            return super.setAttribute(XmlUtil.cleanupControlCharacters(name), XmlUtil.cleanupControlCharacters(value));
    }


    public Element setAttribute(String name, int value)
    {
        return super.setAttribute(name, Integer.toString(value));
    }


    public Element setAttribute(String name, long value)
    {
        return super.setAttribute(name, Long.toString(value));
    }


    public Element setAttribute(String name, double value)
    {
        return super.setAttribute(name, Double.toString(value));
    }


    public Element setAttribute(String name, boolean value)
    {
        if(value)
            return super.setAttribute(name, "1");
        else
            return this;
    }


    public Element setAttribute(String name, Date date)
    {
        if(date == null)
            return this;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return this.setAttribute(name, df.format(date));
    }


    public Element addContentAsHtml(String text) throws XMLServException, JDOMException
    {
        return super.addContent(XmlUtil.toElement(HtmlUtil.repair(HtmlUtil.htmlize(XmlUtil.cleanupControlCharacters(text.trim())))));
    }


    public Element setAttribute(String name, StringBuffer buffer)
    {
        if(buffer != null)
            return super.setAttribute(name, buffer.toString());
        else
            return super.setAttribute(name, "");
    }
}