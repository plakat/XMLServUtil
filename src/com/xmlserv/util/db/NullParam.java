package com.xmlserv.util.db;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: 01.08.11
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class NullParam extends ParamValue
{
    public NullParam(int type)
    {
        this.type = type;
    }


    @Override
    public void setParam(PreparedStatement pst, int position) throws SQLException
    {
        pst.setNull(position, type);
    }


    private int type;
}
