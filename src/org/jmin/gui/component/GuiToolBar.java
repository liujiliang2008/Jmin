/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.gui.component;

import javax.swing.JToolBar;

import org.jmin.gui.GuiMainFrame;

/**
 * 工具条
 * 
 * @author Chris Liao
 */
public class GuiToolBar extends JToolBar{
	
	/**
	 * 主框
	 */
	private GuiMainFrame mainFrame;
	
	/**
	 * 构造函数
	 */
	public GuiToolBar(GuiMainFrame mainFrame){
		this.mainFrame = mainFrame;
	}
	
	/**
	 * 主框
	 */
	public GuiMainFrame getMainFrame() {
		return mainFrame;
	}
	
}
