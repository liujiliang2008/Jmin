/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.gui.component;

import javax.swing.JPanel;

/**
 * GUI视图
 * 
 * @author chris liao
 */
public class GuiView extends JPanel {
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * View是否存在变化
	 */
	private boolean changed;
	
	/**
	 * 标题
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * View是否存在变化
	 */
	public boolean isChanged() {
		return changed;
	}
	
	/**
	 * View是否存在变化
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * 真正打开之前执行
	 */
  public void preOpen() {}
  
	/**
	 * 真正打开
	 */
  public void open() {}
  
  /**
   * 被选中后执行
   */
  public void select() {}
  
  /**
   * 选中离开后执行
   */
  public void deselect() {}
  
  /**
   * 关闭之前执行
   */
  public void preClose() {}
  
  /**
   * 执行关闭
   */
  public void close() {}


	
}
