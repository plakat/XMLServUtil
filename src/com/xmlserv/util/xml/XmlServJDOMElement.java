package com.xmlserv.util.xml;

import com.xmlserv.util.*;
import com.xmlserv.util.exceptions.*;
import org.apache.log4j.*;
import org.jdom2.*;

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
        // todo: com.xmlserv.util.xml.XmlUtil.cleanupControlCharacters()
        super(s, s1);
    }


    public XmlServJDOMElement(String s, String s1, String s2)
    {
        // todo: com.xmlserv.util.xml.XmlUtil.cleanupControlCharacters()
        super(s, s1, s2);
    }


    public XmlServJDOMElement(String s, Namespace namespace)
    {
        super(s, namespace);
    }


    public Element addContent(String s)
    {
        // todo: com.xmlserv.util.xml.XmlUtil.cleanupControlCharacters()
        if(s == null)
            return super.addContent("");
        else
            return super.addContent(XmlUtil.cleanupControlCharacters(s));
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


    @Override
    public Element addContent(Content child)
    {
        return super.addContent(child);    //todo: implement com.xmlserv.util.xml.XmlServJDOMElement.addContent
    }


    public Element setAttribute(String name, String value)
    {
        String nameCleaned = XmlUtil.cleanupControlCharacters(name);
        if(nameCleaned == null || nameCleaned.isEmpty())
            return this;
        if(value == null)
            return super.setAttribute(nameCleaned, "");
        else {
            String valueCleaned = XmlUtil.cleanupControlCharacters(value);
            if(valueCleaned == null) {
                Logger.getLogger(XmlServJDOMElement.class).warn("attribute value is null after cleanup, initial value="+value);
                return super.setAttribute(nameCleaned, "");
            } else {
                return super.setAttribute(nameCleaned, valueCleaned);
            }
        }
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


    public Element setAttributes(Map<String,String> attributes) {
        for(String attribute : attributes.keySet()) {
            setAttribute(attribute, attributes.get(attribute));
        }

        return this;
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


    public Element setAttribute(String name, Object value) {
        if(value instanceof Boolean) {
            return this.setAttribute(name, (boolean) value);
        }

        return this.setAttribute(name, value.toString());
    }
}
