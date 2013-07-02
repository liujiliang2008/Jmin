/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.execution.base;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * 存储过程调用处理结果
 * 
 * @author Chris
 */
 
public class CallableResultSet implements ResultSet {

	/**
	 *  存储过程定义
	 */
  private CallableStatement cs;
  
  /**
   * 构造函数
   */
  public CallableResultSet(CallableStatement cs) {
    this.cs = cs;
  }

  public boolean absolute(int row) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void afterLast() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void beforeFirst() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void cancelRowUpdates() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void clearWarnings() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void close() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void deleteRow() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public int findColumn(String columnName) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean first() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }
  
  public URL getURL(int columnIndex) throws SQLException{
  	throw new UnsupportedOperationException("Function not support");
  }

  public URL getURL(String columnName) throws SQLException{
  	throw new UnsupportedOperationException("Function not support");
  }
  
  public Array getArray(String colName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Array getArray(int i) throws SQLException {
    return cs.getArray(i);
  }

  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public InputStream getAsciiStream(String columnName) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return cs.getBigDecimal(columnIndex);
  }

  public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public BigDecimal getBigDecimal(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public InputStream getBinaryStream(String columnName) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public Blob getBlob(String colName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Blob getBlob(int i) throws SQLException {
    return cs.getBlob(i);
  }

  public boolean getBoolean(int columnIndex) throws SQLException {
    return cs.getBoolean(columnIndex);
  }

  public boolean getBoolean(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public byte getByte(int columnIndex) throws SQLException {
    return cs.getByte(columnIndex);
  }

  public byte getByte(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public byte[] getBytes(int columnIndex) throws SQLException {
    return cs.getBytes(columnIndex);
  }

  public byte[] getBytes(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Reader getCharacterStream(int columnIndex) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public Reader getCharacterStream(String columnName) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public Clob getClob(String colName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Clob getClob(int i) throws SQLException {
    return cs.getClob(i);
  }

  public int getConcurrency() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public String getCursorName() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public Date getDate(int columnIndex) throws SQLException {
    return cs.getDate(columnIndex);
  }

  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    return cs.getDate(columnIndex, cal);
  }

  public Date getDate(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Date getDate(String columnName, Calendar cal) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public double getDouble(int columnIndex) throws SQLException {
    return cs.getDouble(columnIndex);
  }

  public double getDouble(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public int getFetchDirection() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public int getFetchSize() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public float getFloat(int columnIndex) throws SQLException {
    return cs.getFloat(columnIndex);
  }

  public float getFloat(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public int getInt(int columnIndex) throws SQLException {
    return cs.getInt(columnIndex);
  }

  public int getInt(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public long getLong(int columnIndex) throws SQLException {
    return cs.getLong(columnIndex);
  }

  public long getLong(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public Object getObject(String colName, Map map) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Object getObject(int columnIndex) throws SQLException {
    return cs.getObject(columnIndex);
  }

  public Object getObject(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Object getObject(int i, Map map) throws SQLException {
    return cs.getObject(i, map);
  }

  public Ref getRef(String colName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Ref getRef(int i) throws SQLException {
    return cs.getRef(i);
  }

  public int getRow() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public short getShort(int columnIndex) throws SQLException {
    return cs.getShort(columnIndex);
  }

  public short getShort(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Statement getStatement() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public String getString(int columnIndex) throws SQLException {
    return cs.getString(columnIndex);
  }

  public String getString(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Time getTime(int columnIndex) throws SQLException {
    return cs.getTime(columnIndex);
  }

  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    return cs.getTime(columnIndex, cal);
  }

  public Time getTime(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Time getTime(String columnName, Calendar cal) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return cs.getTimestamp(columnIndex);
  }

  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    return cs.getTimestamp(columnIndex, cal);
  }

  public Timestamp getTimestamp(String columnName) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
  	 throw new UnsupportedOperationException("Function not support");
  }

  public int getType() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public InputStream getUnicodeStream(String columnName) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public SQLWarning getWarnings() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void insertRow() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean isAfterLast() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean isBeforeFirst() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean isFirst() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean isLast() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean last() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void moveToCurrentRow() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void moveToInsertRow() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean next() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean previous() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void refreshRow() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean relative(int rows) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean rowDeleted() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean rowInserted() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean rowUpdated() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void setFetchDirection(int direction) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void setFetchSize(int rows) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateArray(int columnIndex, Array x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateArray(String columnName, Array x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBlob(int columnIndex, Blob x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBlob(String columnName, Blob x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBoolean(int columnIndex, boolean x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBoolean(String columnName, boolean x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateByte(int columnIndex, byte x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateByte(String columnName, byte x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBytes(int columnIndex, byte x[]) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateBytes(String columnName, byte x[]) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateClob(int columnIndex, Clob x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateClob(String columnName, Clob x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateDate(int columnIndex, Date x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateDate(String columnName, Date x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateDouble(int columnIndex, double x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateDouble(String columnName, double x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateFloat(int columnIndex, float x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateFloat(String columnName, float x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateInt(int columnIndex, int x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateInt(String columnName, int x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateLong(int columnIndex, long x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateLong(String columnName, long x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateNull(int columnIndex) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateNull(String columnName) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateObject(int columnIndex, Object x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateObject(String columnName, Object x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateObject(String columnName, Object x, int scale) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateRef(int columnIndex, Ref x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateRef(String columnName, Ref x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateRow() throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateShort(int columnIndex, short x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateShort(String columnName, short x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateString(int columnIndex, String x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateString(String columnName, String x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateTime(int columnIndex, Time x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateTime(String columnName, Time x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
    throw new UnsupportedOperationException("Function not support");
  }

  public boolean wasNull() throws SQLException {
    return cs.wasNull();
  }

}
