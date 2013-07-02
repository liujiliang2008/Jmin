package org.jmin.gui.command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiClickListener extends GuiCommand{
	
	private ActionListener listener;
	
	/**
	 * 构造函数
	 */
	public GuiClickListener(ActionListener listener){
		this.listener = listener;
	}
		
  /**
   * Invoked when an action occurs.
   */
  public void actionPerformed(ActionEvent e){
  	listener.actionPerformed(e);
  }
}
