package com.xmlserv.util.db;

import java.sql.*;
import java.util.*;
import java.util.regex.*;

/**
 * User: muecke
 * Date: 12.09.11
 * Time: 17:08
 */
public class NamedPreparedStatement
{
    public NamedPreparedStatement()
    {}


    public NamedPreparedStatement(String query)
    {
        this.query = query;
    }

    
    public void setPreparedStatement(String query)
    {
        this.query = query;
    }


    public void setValue(String name, String value)
    {
        this.paramValues.put(name, new StringParam(value));
    }


    public void setValue(String name, Double value)
    {
        this.paramValues.put(name, new DoubleParam(value));
    }


    public void setValue(String name, Integer value)
    {
        this.paramValues.put(name, new IntegerParam(value));
    }


    public void setValue(String name, Long value)
    {
        this.paramValues.put(name, new LongParam(value));
    }

    public void setValue(String name, Boolean value)
    {
        this.paramValues.put(name, new BooleanParam(value));
    }


    public void setNull(String name, int type)
    {
        this.paramValues.put(name, new NullParam(type));
    }


    public int executeUpdate(Connection con) throws SQLException
    {
        processParameters(con);

        return psth.executeUpdate();
    }


    public ResultSet executeQuery(Connection con) throws SQLException
    {
        processParameters(con);

        return psth.executeQuery();
    }


    public void close() throws SQLException
    {
        if(psth != null)
            this.psth.close();
    }


    private void processParameters(Connection con) throws SQLException
    {
        String processedQuery = this.query;
        this.psth = new PreparedStatementHelper();
        Matcher m = namedParameterPattern.matcher(processedQuery);
        while(m.find()) {
            String currentName = m.group(1);
            processedQuery = m.replaceFirst("?");
            psth.setValue(this.paramValues.get(currentName));
            m = namedParameterPattern.matcher(processedQuery);
        }

        if(con != null)
            this.psth.setPreparedStatement(con, processedQuery);
    }


    public String getCond() throws SQLException
    {
        if(this.psth != null)
        return this.psth.getCond();
        else
            return "(parameters not processed)";
    }


    public void clearParams()
    {
        this.paramValues.clear();
        if(this.psth != null)
            this.psth.clearParams();
    }


    private String query = "";
    PreparedStatementHelper psth = new PreparedStatementHelper();
    Map<String,ParamValue> paramValues = new HashMap<String, ParamValue>();
    Pattern namedParameterPattern = Pattern.compile("\\?(\\w+)\\?");
}
