package com.xmlserv.util.db;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: 01.08.11
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class LongParam extends ParamValue
{
    public LongParam(Long value)
    {
        this.longValue = value;
    }


    @Override
    public void setParam(PreparedStatement pst, int position) throws SQLException
    {
        if(this.longValue != null)
            pst.setLong(position, this.longValue);
        else
            pst.setNull(position, Types.NUMERIC);
    }


    private Long longValue;
}
