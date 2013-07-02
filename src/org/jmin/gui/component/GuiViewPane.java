/*
* Copyright(c) jmin Organization. All rights reserved.
*/
package org.jmin.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.jmin.gui.GuiMainFrame;
import org.jmin.gui.resource.LocaleResourceBundle;
import org.jmin.gui.resource.ResourceManager;

/**
 * Gui TabbedPane
 * 
 * @author chris liao
 */

public class GuiViewPane extends JTabbedPane implements MouseListener{
	
	/**
	 * 主框
	 */
	private GuiMainFrame mainFrame;
	
	/**
	 * 弹出是菜单
	 */
	private JPopupMenu popupMenu = null;
	
	/**
	 * 选中视图的菜单
	 */
	private JMenu selectViewMenu = null;
	
	/**
	 * 选中视图的菜单
	 */
	private Map componentMenuMap= null;
	
  /**
   * framework resource bundle
   */
  private static LocaleResourceBundle frameworkResourceBundle = ResourceManager.getManager().getFrameworkResourceBundle();
	
	/**
	 * 构造函数
	 */
	public GuiViewPane(GuiMainFrame mainFrame){
		this.mainFrame = mainFrame;
		this.componentMenuMap = new HashMap();
		this.popupMenu = createPopupMenu();
		this.addMouseListener(this);
	}
	
	/**
	 * 获得UI
	 */
	public void updateUI() {
		setUI(new GuiViewPaneUI());
	}

	/**
	 * 主框
	 */
	public GuiMainFrame getMainFrame() {
		return mainFrame;
	}
	
  /**
	 * 增加一个tab
	 */
	public void addView(String title,GuiView view) {
		Component component = this.getSelectedComponent();
		if(component instanceof GuiView){
			GuiView currentView = (GuiView)component;
			currentView.deselect();
		}
		
		int index = getComponentIndex(view);
		if(index >= 0) {// 已经存在列表
			this.setSelectedIndex(index);
			view.select();
		} else {
			this.insertTab(title, null,view,title, 0);
			view.preOpen();
			view.open();
			this.setSelectedIndex(0);
			view.select();
		}
	}	
	
	/**
	 * 插入一个Tab
	 */
  public void insertTab(String title, Icon icon, Component component, String tip, int index) {
  	if(this.getComponents().length ==0)
    	mainFrame.showGuiViewPane();
  	super.insertTab(title,new CloseTabIcon(),component,tip,index);
  	JMenuItem selectMenu=new JMenuItem(title);
  	selectMenu.addActionListener(new SelectAction(this,component));
  	this.selectViewMenu.add(selectMenu);
  	this.componentMenuMap.put(component,selectMenu);
  	
  
  }
	
	/**
	 * *删除一个View
	 */
	public void removeView(GuiView view) {
		int index = getComponentIndex(view);
		if (index >= 0) {
			this.remove(index);
		}
	}
	
  /**
	 *删除一个Tab
	 */
	public void remove(int index) {
		int tabCount = this.getComponentCount();
		if (index < tabCount) {
			Component component = this.getComponentAt(index);
			if(component instanceof GuiView) {
				GuiView view =(GuiView)component;
				if(view.isChanged() && JOptionPane.showConfirmDialog(mainFrame,
		        frameworkResourceBundle.getText("#tabpane.closeTab")+ " '" + this.getTitleAt(index) + "'?",
		        frameworkResourceBundle.getText("#tabpane.closeTabTitle"),
		        JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
		      return;
				
				view.preClose();
				view.close();
			}
		
			super.remove(index);
			int selectIndex = index;
			if(index == tabCount-1){
				selectIndex = this.getComponentCount()-1;
			} 
			
			JMenuItem selectMenu =(JMenuItem)this.componentMenuMap.remove(component);
			if(selectMenu!=null)
				this.selectViewMenu.remove(selectMenu);
			
			if(selectIndex>0 && selectIndex<this.getComponentCount()){
				this.setSelectedIndex(selectIndex);
				component = this.getComponentAt(selectIndex);
				if(component instanceof GuiView) {
					GuiView view =(GuiView)component;
					view.select();
				}
			}
			
	  	if(this.getComponents().length ==0)
	    	mainFrame.showBackGroundImage();
		}
	}
	
	/**
	 * 是否包含View
	 */
	private int getComponentIndex(GuiView view){
    int index =-1;
		Component[] componentArry  =  this.getComponents();
		for(int i=0;i<componentArry.length;i++){
			if(componentArry[i]==view){
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * 构造菜单
	 */
	private JPopupMenu createPopupMenu(){
		this.popupMenu = new JPopupMenu();
		JMenuItem closeViewMenu=new JMenuItem("Close");
		closeViewMenu.addActionListener(new CloseAction(this));
	  popupMenu.add(closeViewMenu);
		JMenuItem closeAllViewMenu=new JMenuItem("Close All");
		closeAllViewMenu.addActionListener(new CloseAction(this,true));
 
		popupMenu.add(closeAllViewMenu);
		selectViewMenu = new JMenu("Select");
		popupMenu.add(selectViewMenu);
		return popupMenu;
	}

  public void mousePressed(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseClicked(MouseEvent e){
	 int tabNumber=getUI().tabForCoordinate(this, e.getX(), e.getY());
   if (tabNumber < 0) return;
   Rectangle rect=((CloseTabIcon)getIconAt(tabNumber)).getBounds();
   if (rect.contains(e.getX(), e.getY())) {
     this.remove(tabNumber);
   }
  }
  
  public void mouseReleased(MouseEvent e){
  	if (e.isMetaDown() &&  indexAtLocation(e.getX(),e.getY())!= -1) {
  		popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
  }
  
  public int indexAtLocation(int x, int y) {
    if (ui != null) 
       return ((TabbedPaneUI)ui).tabForCoordinate(this, x, y);
    else
     return -1;
   } 
  
  static class CloseAction implements ActionListener {
		private GuiViewPane pane;
		private boolean closeAll = false;
		public CloseAction(GuiViewPane pane) {
			this(pane, false);
		}

		public CloseAction(GuiViewPane pane,boolean closeAll) {
			this.pane = pane;
			this.closeAll = closeAll;
		}

		public void actionPerformed(ActionEvent e) {
			if(closeAll){
				pane.removeAll();
				pane.selectViewMenu.removeAll();
			}else{
				int selectIndex = pane.getSelectedIndex();
				if(selectIndex > -1){
					pane.remove(selectIndex);
				}
			}
		}
	}
 
  static class SelectAction implements ActionListener {
  	private GuiViewPane pane;
  	private Component component;
  	public SelectAction(GuiViewPane pane,Component component) {
			this.pane = pane;
			this.component = component;
		}
  	
  	public void actionPerformed(ActionEvent e) {
  		Component curComponent = pane.getSelectedComponent();
  		if(curComponent!= null && curComponent instanceof GuiView){
  			GuiView view =(GuiView)curComponent;
  			view.deselect();
  		}
  
  		pane.setSelectedComponent(component);
  		if(component!= null && component instanceof GuiView){
  			GuiView selectView =(GuiView)component;
  			selectView.select();
  		}
		}
  }
}


class GuiViewPaneUI extends BasicTabbedPaneUI {
	
	/**
	 * 
	 */
	private int horizontalTextPosition;
	
	/**
	 * 构造函数
	 */
	public GuiViewPaneUI() {
		this(SwingUtilities.LEFT);
	}
	
	/**
	 * 构造函数
	 */
	public GuiViewPaneUI(int horTextPosition) {
		horizontalTextPosition = horTextPosition;
	}
	
	/**
	 * 计算Tab的高度
	 */
	protected int calculateTabHeight(int tabPlacement, int tabIndex,int fontHeight) {
		return fontHeight + 10;
	}
	
	/**
	 * layoutLabel
	 */
	protected void layoutLabel(int tabPlacement, FontMetrics metrics,
			int tabIndex, String title, Icon icon, Rectangle tabRect,
			Rectangle iconRect, Rectangle textRect, boolean isSelected) {

		textRect.x = 0;
		textRect.y = 0;
		iconRect.x = 0;
		iconRect.y = 0;
		
		SwingUtilities.layoutCompoundLabel((JComponent) tabPane, metrics, title,
				icon, SwingUtilities.CENTER, SwingUtilities.CENTER,
				SwingUtilities.CENTER, horizontalTextPosition, tabRect, iconRect,
				textRect, textIconGap + 2);
		tabPane.invalidate();
	}
}

class CloseTabIcon implements Icon {
  private int x_pos;
  private int y_pos;
  private int width;
  private int height;
 
  public CloseTabIcon() {
		this.width = 16;
		this.height = 16;
	}
 
  public void paintIcon(Component c, Graphics g, int x, int y) {
    this.x_pos=x;
    this.y_pos=y;
    Color col=g.getColor();
    g.setColor(Color.black);
    int y_p=y+2;
    g.drawLine(x+1, y_p, x+12, y_p);
    g.drawLine(x+1, y_p+13, x+12, y_p+13);
    g.drawLine(x, y_p+1, x, y_p+12);
    g.drawLine(x+13, y_p+1, x+13, y_p+12);
    g.drawLine(x+3, y_p+3, x+10, y_p+10);
    g.drawLine(x+3, y_p+4, x+9, y_p+10);
    g.drawLine(x+4, y_p+3, x+10, y_p+9);
    g.drawLine(x+10, y_p+3, x+3, y_p+10);
    g.drawLine(x+10, y_p+4, x+4, y_p+10);
    g.drawLine(x+9, y_p+3, x+3, y_p+9);
    g.setColor(col);
  }
 
  public int getIconWidth() {
    return width;
  }
 
  public int getIconHeight() {
    return height;
  }
 
  public Rectangle getBounds() {
    return new Rectangle(x_pos, y_pos, width, height);
  }
}

