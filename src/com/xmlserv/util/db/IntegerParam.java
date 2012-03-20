package com.xmlserv.util.db;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: 01.08.11
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class IntegerParam extends ParamValue
{
    public IntegerParam(Integer value)
    {
        this.intValue = value;
    }


    @Override
    public void setParam(PreparedStatement pst, int position) throws SQLException
    {
        if(this.intValue != null)
            pst.setInt(position, this.intValue);
        else
            pst.setNull(position, Types.INTEGER);
    }


    private Integer intValue;
}
