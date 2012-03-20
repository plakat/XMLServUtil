package com.xmlserv.util.db;

import java.sql.*;

/**
 * User: muecke
 * Date: 01.08.11
 * Time: 16:22
 */
public class BooleanParam extends ParamValue
{
    public BooleanParam(Boolean value)
    {
        this.booleanValue = value;
    }


    @Override
    public void setParam(PreparedStatement pst, int position) throws SQLException
    {
        if(this.booleanValue != null)
            pst.setBoolean(position, this.booleanValue);
        else
            pst.setNull(position, Types.BOOLEAN);
    }


    private Boolean booleanValue;
}
