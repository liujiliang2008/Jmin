/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.gui.component;

/**
 * Table 列描述
 * 
 * @author Chris
 *
 */
public class GuiTableColumn {
	
	/**
	 * column ID
	 */
  private String columnId =null;

  /**
   * column description
   */
  private String columnDesc = null;
  
  /**
   * 构造函数
   */
  public GuiTableColumn(String colId,String colDesc) {
    this.columnId = colId;
    this.columnDesc =colDesc;
  }
  
  /**
   * column ID
   */
  public String getColId(){
    return columnId;
  }
  
  /**
   * column description
   */
  public String getcolDesc(){
    return columnDesc;
  }
}