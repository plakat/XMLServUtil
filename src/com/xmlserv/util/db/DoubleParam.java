package com.xmlserv.util.db;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: 01.08.11
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class DoubleParam extends ParamValue
{
    public DoubleParam(Double value)
    {
        this.doubleValue = value;
    }


    @Override
    public void setParam(PreparedStatement pst, int position) throws SQLException
    {
        if(this.doubleValue != null)
            pst.setDouble(position, this.doubleValue);
        else
            pst.setNull(position, Types.DOUBLE);
    }


    private Double doubleValue;
}
