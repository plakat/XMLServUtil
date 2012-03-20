package com.xmlserv.util.db;

import java.sql.*;

/**
* Created by IntelliJ IDEA.
* User: muecke
* Date: 01.08.11
* Time: 16:22
* To change this template use File | Settings | File Templates.
*/
public class StringParam extends ParamValue
{
    public StringParam(String value)
    {
        this.stringValue = value;
    }


    @Override
    public void setParam(PreparedStatement pst, int position) throws SQLException
    {
        if(this.stringValue != null)
            pst.setString(position, this.stringValue);
        else
            pst.setNull(position, Types.VARCHAR);
    }


    private String stringValue;
}
