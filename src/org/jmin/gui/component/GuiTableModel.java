/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.gui.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * A Gui table model
 * 
 * @author Chris Liao
 * @version 1.0
 */

public class GuiTableModel extends AbstractTableModel {

	/**
	 * 列名
	 */
	private GuiTableColumn[] columns = null;

	/**
	 * 行列表
	 */
	private List tableRowList = new ArrayList();

	/**
	 * 构造函数
	 */
	public GuiTableModel(GuiTableColumn[] colHead) {
		this.columns = colHead;
	}

	/**
	 * 获得行的数量
	 */
	public int getRowCount() {
		return tableRowList.size();
	}

	/**
	 * 获得列的数量
	 */
	public int getColumnCount() {
		return columns.length;
	}

	/**
	 * 获得某列描述
	 */
	public String getColumnId(int colIndex) {
		return columns[colIndex].getColId();
	}

	/**
	 * 获得某列名
	 */
	public String getColumnName(int colIndex) {
		return columns[colIndex].getcolDesc();
	}

	/**
	 * 获得某行的记录对象
	 */
	public Object getRow(int index) {
		return tableRowList.get(index);
	}
	
	/**
	 * 取得所有行值
	 */
	public Object[] toArray(){
		return this.tableRowList.toArray();
	}
	
	/**
	 * 取得所有行值
	 */
	public Object[] toArray(Object[] array){
		return this.tableRowList.toArray(array);
	}
	
	/**
	 *  删除所有记录
	 */
	public void removeAll(){
		 int count = tableRowList.size();
		 for(int i=0;i<count;i++)
			 removeRow(0);
	}
	
	
	/**
	 * 设置行对象
	 */
	public void addRow(GuiTableRowValue row) {
		tableRowList.add(row);
		int index = tableRowList.size();
		this.fireTableRowsInserted(index-1,index-1);
	}

	/**
	 * 设置行对象
	 */
	public void addRow(int index,GuiTableRowValue row) {
		tableRowList.add(index, row);
		this.fireTableRowsInserted(index, index);
	}
	
	/**
	 * 设置行对象
	 */
	public void setRow(int index,GuiTableRowValue value) {
		tableRowList.set(index, value);
		this.fireTableRowsUpdated(index, index);
	}
	
	/**
	 * 删除某行记录
	 */
	public void removeRow(int index) {
		tableRowList.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	/**
	 * 删除某个对象
	 */
	public int removeRow(GuiTableRowValue row) {
		int index = tableRowList.indexOf(row);
		if(index!= -1) {
			tableRowList.remove(index);
			this.fireTableRowsDeleted(index, index);
			return index;
		} else {
			return -1;
		}
	}
	
	/**
	 * 获得某列值
	 */
	public Object getValueAt(int rowIndex,int colIndex) {
		Object row = getRow(rowIndex);
		if(row != null) {
			GuiTableRowValue data =(GuiTableRowValue) row;
			String colId = getColumnId(colIndex);
			return data.getPropertyValue(colId);
		} else {
			return null;
		}
	}
	
	/**
	 * 设置某列值
	 */
	public void setValueAt(Object value,int rowIndex,int colIndex) {
		if(columns != null) {
			Object row = getRow(rowIndex);
			if (row != null) {
				GuiTableRowValue data = (GuiTableRowValue) row;
				String colId = getColumnId(colIndex);
				data.setPropertyValue(colId,value);
				this.fireTableRowsDeleted(rowIndex,rowIndex);
			}
		}
	}

}