/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.gui.command;

import java.awt.Component;
import java.awt.event.ActionEvent;

import org.jmin.gui.component.GuiView;
import org.jmin.gui.component.GuiViewPane;

/**
 * 视图显示Command
 * 
 * @author Chris Liao
 */

public class GuiViewCommand extends GuiCommand{
	
	/**
	 * title
	 */
	private String title;
	
	/**
	 * 需要显示View
	 */
	private Component component;
	
	/**
	 * 显示View面板
	 */
	private GuiViewPane viewPane;
	
	/**
	 * 构造函数
	 */
	public GuiViewCommand(String title,Component component,GuiViewPane viewPane){
		this.title = title;
		this.component = component;
		this.viewPane = viewPane;
	}
	
	 /**
   * Invoked when an action occurs.
   */
  public void actionPerformed(ActionEvent e) {
  	if(component instanceof GuiView ){
  		viewPane.addView(title,(GuiView)component);
  	}else{
  		viewPane.addTab(title,component);
  	}
  }
}
