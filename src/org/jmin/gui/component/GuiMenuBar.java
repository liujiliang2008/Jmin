/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.gui.component;

import javax.swing.JMenuBar;

import org.jmin.gui.GuiMainFrame;

/**
 * 菜单条
 * 
 * @author Chris Liao
 */

public class GuiMenuBar extends JMenuBar{
	
	/**
	 * 主框
	 */
	private GuiMainFrame mainFrame;
	
	/**
	 * 构造函数
	 */
	public GuiMenuBar(GuiMainFrame mainFrame){
		this.mainFrame = mainFrame;
	}
	
	/**
	 * 主框
	 */
	public GuiMainFrame getMainFrame() {
		return mainFrame;
	}
}
