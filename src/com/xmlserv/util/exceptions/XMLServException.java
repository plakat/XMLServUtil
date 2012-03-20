/*
 * $Id: XMLServException.java,v 1.11 2009/02/09 21:42:19 muecke Exp $
 *
 * Written and Copyright (C) 2001-2012 Wolfram Saringer.
 * Created: 19.12.2001
 * Last change: $Date: 2009/02/09 21:42:19 $
 */

package com.xmlserv.util.exceptions;

/**
 * Common Baseclass for various exceptions used in the XMLServ context.
 *
 * @author Wolfram Saringer
 * @version $Id: XMLServException.java,v 1.11 2009/02/09 21:42:19 muecke Exp $
 */
public class XMLServException
extends Exception
{
    public XMLServException(String msg)
    {
        super(msg);
    }


    public XMLServException(Exception e)
    {
        super(e.getMessage());
    }


    public XMLServException(String msg, String code)
    {
        super(msg);
        this.code = code;
    }


    public XMLServException(String msg, String code, Exception e)
    {
        super(msg +": "+e.getMessage()+" (Code): "+code+")");
        this.code = code;
        this.exception = e;
    }


    public String toString()
    {
        return getMessage()+" Code: "+this.code;
    }


    public String getCode()
    { return code; }
    
    String code = "";
    Exception exception = null;
}
