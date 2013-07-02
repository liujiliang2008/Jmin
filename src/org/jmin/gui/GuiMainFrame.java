/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jmin.gui.component.GuiMenuBar;
import org.jmin.gui.component.GuiToolBar;
import org.jmin.gui.component.GuiViewPane;
import org.jmin.gui.resource.LocaleResourceBundle;
import org.jmin.gui.resource.ResourceManager;

/**
 * 应用主框
 * 
 * @author Chris Liao
 */

public class GuiMainFrame extends JFrame{
	
	/**
	 * 菜单条
	 */
	private GuiMenuBar guiMenuBar;
	
	/**
	 * 工具条
	 */
	private GuiToolBar guiToolBar;
	
	/**
	 * 中心面板
	 */
	private GuiViewPane guiViewPane;
	
	/**
	 * 背景图片
	 */
	private JLabel backgroundLable;
	
	
  /**
   * framework resource bundle
   */
  private static LocaleResourceBundle frameworkResourceBundle = ResourceManager.getManager().getFrameworkResourceBundle();
	
	/**
	 * 构造函数
	 */
	public GuiMainFrame(){
		this.guiMenuBar = new GuiMenuBar(this);
		this.guiToolBar = new GuiToolBar(this);
		this.guiViewPane = new GuiViewPane(this);
		this.setJMenuBar(guiMenuBar);
	  Container contentPane = this.getContentPane();
    contentPane.add(guiToolBar,BorderLayout.NORTH);
    contentPane.add(guiViewPane,BorderLayout.CENTER);
    this.addWindowListener(new WindowExitListener(this));
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
	}
	
	/**
	 * 菜单条
	 */
	public GuiMenuBar getGuiMenuBar() {
		return guiMenuBar;
	}
	
	/**
	 * 工具条
	 */
	public GuiToolBar getGuiToolBar() {
		return guiToolBar;
	}

	/**
	 * 中心面板
	 */
	public GuiViewPane getGuiViewPane() {
		return guiViewPane;
	}
	
	/**
	 * 设置背景颜色
	 * @param backImage
	 */
  void setBackGroundImage(ImageIcon backImage) {
  	if(backImage!=null){
  		backgroundLable = new JLabel(backImage);
  		this.showBackGroundImage();
  	}
  }
	
  /**
   * 显示ViewPane
   */
  public void showGuiViewPane(){
  	Container contentPane = this.getContentPane();
  	if(backgroundLable!=null)
  		contentPane.remove(backgroundLable);
    contentPane.add(guiViewPane,BorderLayout.CENTER);
    contentPane.repaint();
  }
  
  /**
   * 显示背景图片
   */
  public void showBackGroundImage(){
	  Container contentPane = this.getContentPane();
	  contentPane.remove(guiViewPane);
	  if(backgroundLable!=null)
	  	contentPane.add(backgroundLable,BorderLayout.CENTER);
	  contentPane.repaint();
  }
  
  /**
   * Window close event listener
   */
  static class WindowExitListener implements WindowListener {
  	private GuiMainFrame mainFrame;
  	public WindowExitListener(GuiMainFrame mainFrame){
  		this.mainFrame = mainFrame;
  	}
  	
    public void windowClosing(WindowEvent e) {
      int option = JOptionPane.showConfirmDialog(this.mainFrame,
          frameworkResourceBundle.getText("#system.exit"),
          frameworkResourceBundle.getText("#system.exitTitle"),
          JOptionPane.YES_NO_OPTION);

      if(option == JOptionPane.YES_OPTION) {
        System.exit(0);
       } else{
      	 return;
       }
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
  }
	
	/**
	 * 
	 */
	public static void main(String[] args){
		GuiMainFrame frame = new GuiMainFrame();
		frame.setSize(800,800);
		frame.setVisible(true);
	}
}
