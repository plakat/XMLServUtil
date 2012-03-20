package com.xmlserv.util.db;

import java.sql.*;
import java.util.*;

/**
 * User: muecke
 * Date: 01.08.11
 * Time: 16:19
 */
public class PreparedStatementHelper
{
    public PreparedStatementHelper()
    {}


    public PreparedStatementHelper(Connection con, String query) throws SQLException
    {
        this.query = query;
        this.pst = con.prepareStatement(query);
    }


    public void setPreparedStatement(PreparedStatement pst)
    {
        this.query = pst.toString();
        this.pst = pst;
    }


    public void setPreparedStatement(Connection con, String query) throws SQLException
    {
        this.query = query;
        this.pst = con.prepareStatement(query);
    }


    /**
     * Used to append query parts when constructing the query on the fly.
     * Does not use query strings set with other methods (i.e. via constructor)
     * @param queryPart
     */
    public void queryAppend(String queryPart) {
        this.queryBuilder.append(" ").append(queryPart);
    }


    /**
     * When using queryAppend() this finishes the construction process and initializes the PReparedStatement used internally.
     * @param con
     * @throws SQLException
     */
    public void finishQuery(Connection con) throws SQLException {
        this.query = queryBuilder.toString();
        this.pst = con.prepareStatement(query);
    }

    public void setValue(int position, String value)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, new StringParam(value));
    }

    public void setValue(String value)
    {
        this.paramValues.add(new StringParam(value));
    }


    public void setValue(int position, Double value)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, new DoubleParam(value));
    }

    public void setValue(Double value)
    {
        this.paramValues.add(new DoubleParam(value));
    }


    public void setValue(int position, Integer value)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, new IntegerParam(value));
    }

    public void setValue(Integer value)
    {
        this.paramValues.add(new IntegerParam(value));
    }


    public void setValue(int position, Long value)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, new LongParam(value));
    }

    public void setValue(Long value)
    {
        this.paramValues.add(new LongParam(value));
    }

    public void setValue(int position, Boolean value)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, new BooleanParam(value));
    }

    public void setValue(Boolean value)
    {
        this.paramValues.add(new BooleanParam(value));
    }


    public void setValue(int position, Timestamp value)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, new TimestampParam(value));
    }

    public void setValue(Timestamp value)
    {
        this.paramValues.add(new TimestampParam(value));
    }


    public void setValue(int position, ParamValue value)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, value);
    }

    public void setValue(ParamValue value)
    {
        this.paramValues.add(value);
    }


    public void setNull(int position, int type)
    {
        this.paramValues.ensureCapacity(position);
        this.paramValues.add(position-1, new NullParam(type));
    }


    public void setNull(int type)
    {
        this.paramValues.add(new NullParam(type));
    }


    public int executeUpdate() throws SQLException
    {
        processParameters();

        return pst.executeUpdate();
    }


    public ResultSet executeQuery() throws SQLException
    {
        if(pst == null) {
            if(queryBuilder.length() > 0) {
                throw new SQLException("-77: PREPAREDSTATEMENTHELPER-PST-NOT-SET", "PreparedStatement not set (maybe finishQuery() was not called?)", new Exception());
            }
            else {
                throw new SQLException("-88: PREPAREDSTATEMENTHELPER-PST-NOT-SET", "PreparedStatement not set", new Exception());
            }
        }

        processParameters();
        return pst.executeQuery();
    }


    public void close() throws SQLException
    {
        if(pst != null)
            this.pst.close();
    }


    private void processParameters() throws SQLException
    {
        int position = 1;
        for(ParamValue value : paramValues)
        {
            value.setParam(pst, position);
            position++;
        }
    }


    public String getCond() throws SQLException
    {
        processParameters();
        String query = pst.toString();
        return PSTHUtil.extractCondition(query);
    }


    public void clearParams()
    {
        this.paramValues.clear();
    }


    @Override
    public String toString()
    {
        if(pst == null)
        {
            return "(Error creating query string: PreparedStatement not set)";
        }
        try
        {
            processParameters();
            return pst.toString();
        }
        catch(Exception e)
        { return "(Error creating query string: "+e.getMessage()+" Empty query string: "+query+")"; }
    }


    /**
     * Just close without throwing an Exception (useful in finally{})
     */
    public void forceClose()
    {
        try {
            this.close();
        }
        catch(SQLException ignored) {
        }
    }


    private StringBuilder queryBuilder = new StringBuilder();
    private String query = "";
    private PreparedStatement pst;
    private ArrayList<ParamValue> paramValues = new ArrayList<ParamValue>();
}
