package com.xmlserv.util.tests;

import com.xmlserv.util.db.*;
import junit.framework.*;

import java.sql.*;

/**
 * User: muecke
 * Date: 12.09.11
 * Time: 18:53
 */
public class TestNamedPreparedStatement extends TestCase
{
    public void testProcessParameters()
    {
        NamedPreparedStatement nps = new NamedPreparedStatement();
        nps.setPreparedStatement("SELECT * FROM accounts WHERE id=?id? AND uid LIKE ?uid?;");
        nps.setValue("id", 111);
        nps.setValue("uid", "admin");
        try
        {
            nps.executeQuery(null);
        }
        catch(SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
