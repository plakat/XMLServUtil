package com.xmlserv.util.db;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: muecke
 * Date: 01.08.11
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class ParamValue
{
    public ParamValue()
    {}


    public abstract void setParam(PreparedStatement pst, int position) throws SQLException;
}
