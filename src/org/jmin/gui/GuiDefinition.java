package org.jmin.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import org.jmin.gui.command.GuiClickListener;
import org.jmin.gui.command.GuiCommand;
import org.jmin.gui.command.GuiViewCommand;
import org.jmin.gui.component.GuiToolButton;
import org.jmin.gui.definition.ItemDefinition;
import org.jmin.gui.definition.MenuDefinition;
import org.jmin.gui.definition.SeperatorDefinition;
import org.jmin.gui.resource.LocaleResourceBundle;
import org.jmin.gui.resource.ResourceManager;
import org.jmin.gui.security.SecurityManager;
import org.jmin.gui.util.GuiUtil;

/**
 * Application class for the current framework.
 *
 * @author chris
 */

public class GuiDefinition {

  /**
   * main frame window title
   */
  private String applicationName;

  /**
   * main frame window icon
   */
  private String applicationIconName;

  /**
   * back ground image file name
   */
  private String backgroundImageName;

  /**
   * A resource path for I18N
   */
  private String applicationResourcePath;

  /**
   * tool bar list
   */
  private List toolbarDefList;

  /**
   * Menu List
   */
  private List menuDefList;

  /**
   * 长
   */
  private int windowWidth;
  
  /**
   * 高
   */
  private int windowHeight;
  

  private static LocaleResourceBundle applicationResourceBundle = ResourceManager.getManager().getApplicationResourceBundle();

  /**
   * Return main frame title
   */
  public String getApplicationName() {
    return applicationResourceBundle.getText(applicationName);
  }

  /**
   * Set title name
   * @param title
   */
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  /**
   * Return icon name
   */
  public String getApplicationIconName() {
    return applicationIconName;
  }

  /**
   * set icon name
   * @param iconName
   */
  public void setApplicationIconName(String applicationIcon) {
    this.applicationIconName = applicationIcon;
  }

  /**
   * set application Resource Path
   * @param applicationResourcePath
   */
  public void setApplicationResourcePath(String applicationResourcePath) {
	this.applicationResourcePath = applicationResourcePath;
	if(!isNull(this.applicationResourcePath )){
		ResourceManager.getManager().setApplicationResourcePath(applicationResourcePath);
		ResourceManager.getManager().getApplicationResourceBundle().reload();
	}
  }

  /**
   * Return icon by icon name
   */
  public Image getIcon() {
    return applicationResourceBundle.getImage(applicationIconName);
  }

  /**
   * Return back ground image name
   */
  public String getBackgroundImageName() {
    return backgroundImageName;
  }

  /**
   * set back ground image name
   */
  public void setBackgroundImageName(String backgroundImage) {
    this.backgroundImageName = backgroundImage;
  }

  /**
   * Return back ground image
   */
  private ImageIcon loadBackgroundImage() {
    return (ImageIcon)applicationResourceBundle.getIcon(this.getBackgroundImageName());
  }

  /**
   * set menu list
   * @param menuList
   */
  public void setMenuList(List menuList) {
    this.menuDefList = menuList;
  }

  /**
   * set tool bar list
   */
  public void setToolbarList(List toolbarList) {
    this.toolbarDefList = toolbarList;
  }

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	/**
	 * 判断字符是否为空
	 */
	private boolean isNull(String value) {
		return value == null || value.trim().length()==0;
	}
	
  /**
   * Build a new Menu from configeruation information
   */
  private JMenuBar initMenuBar(List menuItemList,GuiMainFrame mainFrame) {
    /**
     * Create menu bar instance
     */
    JMenuBar menuBar = mainFrame.getJMenuBar();
    for (int i = 0; i < menuItemList.size(); i++) {

      Object config = menuItemList.get(i);
      if (config instanceof MenuDefinition) {
        MenuDefinition menuDesc = (MenuDefinition) config;
        JMenu menu = new JMenu(menuDesc.getName());
        menu.setMnemonic(menuDesc.getMnemonic());
        menu.setIcon(menuDesc.getIcon());
        menu.setToolTipText(menuDesc.getToolTipText());
        menu.setEnabled(isPermitted(menuDesc.getPermissions()));
        menuBar.add(menu);

        /**
         * If there are some sub menuitems below the current menu
         */
        if (menuDesc.getItemList() != null) {
          for (int j = 0; j < menuDesc.getItemList().size(); j++) {
            Object itemDec = menuDesc.getItemList().get(j);
            if (itemDec instanceof SeperatorDefinition) {
              menu.addSeparator();
            } else {
              JMenuItem menuItem = this.createJMenuItem(itemDec,mainFrame);
              if (menuItem != null)
                menu.add(menuItem);
            }
          }
        }
      }
    }

    return menuBar;
  }
  
  
  /**
   * Build a tool bar based on configueration list
   *
   * @param toolItemList
   * @param mainFrame
   */
  private void initToolBar(List toolItemList, GuiMainFrame mainFrame) {
    JToolBar toolBar = mainFrame.getGuiToolBar();

    for (int i = 0; i < toolItemList.size(); i++) {
      Object instance = toolItemList.get(i);
      if (instance instanceof ItemDefinition) {
        ItemDefinition itemDesc = (ItemDefinition) instance;
        GuiToolButton toolButton = new GuiToolButton(itemDesc.getName());
        GuiCommand command = this.getClickCommand(itemDesc,mainFrame);
        if (command != null) {
          toolButton.addActionListener(command);
          toolButton.setIcon(itemDesc.getIcon());
          toolButton.setToolTipText(itemDesc.getToolTipText());
          toolButton.setEnabled(isPermitted(itemDesc.getPermissions()));
          toolBar.add(toolButton);
        }
      } else if (instance instanceof SeperatorDefinition) {

        /**
         * add a seperator on the toolbar
         */
        toolBar.addSeparator();
      }
    }
  }
  

  /**
   * Add short cut
   * @param menuItem
   * @param shortcut
   */
  private void addShortcut(JMenuItem menuItem,String shortcut){
    if (!isNull(shortcut)) {
      int keys[] = GuiUtil.getKeys(shortcut);
      if (keys.length == 1)
        menuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke((char)keys[0]));
      else if (keys.length == 2)
        menuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(keys[1], keys[0], true));
    }
  }

  /**
   * Build a menu item
   */
  private JMenuItem createJMenuItem(Object desc, GuiMainFrame mainFrame) {
    if (desc instanceof ItemDefinition) {
      ItemDefinition itemDesc = (ItemDefinition) desc;
      JMenuItem menuItem = new JMenuItem(itemDesc.getName());
      menuItem.setMnemonic(itemDesc.getMnemonic());
      menuItem.setToolTipText(itemDesc.getToolTipText());
      menuItem.setIcon(itemDesc.getIcon());
      this.addShortcut(menuItem,itemDesc.getShortcut());
      GuiCommand command = this.getClickCommand(itemDesc,mainFrame);

      if (command != null) {
        menuItem.addActionListener(command);
        menuItem.setEnabled(isPermitted(itemDesc.getPermissions()));
        menuItem.setToolTipText(itemDesc.getToolTipText());
        return menuItem;
      } else {
        return null;
      }

    } else if (desc instanceof MenuDefinition) {
      MenuDefinition menuDesc = (MenuDefinition) desc;
      JMenu menu = new JMenu(menuDesc.getName());
      menu.setMnemonic(menuDesc.getMnemonic());
      menu.setToolTipText(menuDesc.getToolTipText());
      this.addShortcut(menu,menuDesc.getShortcut());
      menu.setIcon(menuDesc.getIcon());
      menu.setEnabled(isPermitted(menuDesc.getPermissions()));

      /**
       * Exist sub menu
       */
      if (menuDesc.getItemList() != null) {
        for (int j = 0; j < menuDesc.getItemList().size(); j++) {
          Object itemDec = menuDesc.getItemList().get(j);
          if (itemDec instanceof SeperatorDefinition) {
            menu.addSeparator();
          } else {
            JMenuItem menuItem = createJMenuItem(itemDec,mainFrame);
            if (menuItem != null)
              menu.add(menuItem);
          }
        }
      }
      return menu;
    } else {
      return (JMenuItem) null;
    }
  }
 

  /**
   * Return click command for item desc object
   */
  private GuiCommand getClickCommand(ItemDefinition itemDesc,GuiMainFrame mainFrame){
  	if(itemDesc.getGuiView()!=null){
  		return new GuiViewCommand(itemDesc.getName(),itemDesc.getGuiView(),mainFrame.getGuiViewPane());
  	}else if(itemDesc.getActionListener()!=null){
  		return new GuiClickListener(itemDesc.getActionListener());
  	}else{
  		throw new RuntimeException("You need config gui command or Listener");
  	}
  }

//  private static class WorkBenchListener implements ActionListener {
//    private ApplicationFrame mainFrame;
//
//    public WorkBenchListener(ApplicationFrame mainFrame) {
//      this.mainFrame = mainFrame;
//    }
//
//    /**
//     * Invoked when an action occurs.
//     */
//    public void actionPerformed(ActionEvent e) {
//      mainFrame.restoreWorkBench();
//    }
//  }

//  private static class LayoutListener implements ActionListener {
//    private String feelName = null;
//    private LookAndFeel lookAndFeel = null;
//    private Component owner;
//
//    public LayoutListener(Component owner, String feelName) {
//      this.owner = owner;
//      this.feelName = feelName;
//    }
//
//    public LayoutListener(Component owner, LookAndFeel lookAndFeel) {
//      this.owner = owner;
//      this.lookAndFeel = lookAndFeel;
//    }
//
//    /**
//     * Invoked when an action occurs.
//     */
//    public void actionPerformed(ActionEvent e) {
//      try {
//
//        if (feelName != null)
//          UIManager.setLookAndFeel(feelName);
//        else if (lookAndFeel != null)
//          UIManager.setLookAndFeel(lookAndFeel);
//
//        SwingUtilities.updateComponentTreeUI(owner);
//      } catch (Exception ee) {
//
//      }
//    }
//  }

//  private static class HelpWindowListener implements ActionListener {
//    private ApplicationFrame mainFrame;
//    private TitleDirectionWindow window;
//    public HelpWindowListener(ApplicationFrame mainFrame,
//                              TitleDirectionWindow window) {
//      this.mainFrame = mainFrame;
//      this.window = window;
//    }
//
//    /**
//     * Invoked when an action occurs.
//     */
//    public void actionPerformed(ActionEvent e) {
//      mainFrame.displayTitleDirectionWindow(window);
//    }
//  }

//  /**
//   * Restore action listener,When click its mapping menu,main frame will
//   * restore gui.
//   */
//  private static class RestoreAction implements ActionListener {
//    private ApplicationFrame mainFrame;
//
//    public RestoreAction(ApplicationFrame mainFrame) {
//      this.mainFrame = mainFrame;
//    }
//
//    /**
//     * Invoked when an action occurs.
//     */
//    public void actionPerformed(ActionEvent e) {
//      //mainFrame.restore();
//    }
//  }

  /**
   * Validate permissions
   */
  public boolean isPermitted(String permissions) {
  	if (!isNull(permissions)) {
			String[] permissionArray = GuiUtil.split(permissions, ",");
			return SecurityManager.getInstance().implies(permissionArray);
		} else {
			return true;
		}
  }

  /**
   * Build a new frame,then set some gui components on it.and set it as
   * visible
   */
  public void show(){
    GuiMainFrame mainFrame = new GuiMainFrame();
    mainFrame.setTitle(this.getApplicationName());
    this.initMenuBar(menuDefList, mainFrame);
    this.initToolBar(toolbarDefList, mainFrame);
    mainFrame.setIconImage(this.getIcon());
    mainFrame.setBackGroundImage(loadBackgroundImage());

    if(this.windowWidth <=0)
    	this.windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    if(this.windowHeight <=0)
    	this.windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
 
    mainFrame.setSize(this.windowWidth,this.windowHeight);
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = mainFrame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    
    mainFrame.setLocation((screenSize.width - frameSize.width) / 2,
                     (screenSize.height - frameSize.height) / 2);
    
		try{
		  Method method=mainFrame.getClass().getMethod("setExtendedState",new Class[]{int.class});
		  method.invoke(mainFrame,new Object[]{new Integer(4|2)});
		}catch(Exception e){
		}
    
    mainFrame.validate();
    mainFrame.setVisible(true);
  }

  }