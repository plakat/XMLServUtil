package com.xmlserv.util.db;

import java.sql.*;

/**
 * User: muecke
 * Date: 01.08.11
 * Time: 16:22
 */
public class TimestampParam extends ParamValue
{
    public TimestampParam(Timestamp value)
    {
        this.timestampValue = value;
    }


    @Override
    public void setParam(PreparedStatement pst, int position) throws SQLException
    {
        if(this.timestampValue != null)
            pst.setTimestamp(position, this.timestampValue);
        else
            pst.setNull(position, Types.TIMESTAMP);
    }


    private Timestamp timestampValue;
}
