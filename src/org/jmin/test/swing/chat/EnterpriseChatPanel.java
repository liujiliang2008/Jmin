package org.jmin.test.swing.chat;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.jmin.gui.component.GuiToolButton;
import org.jmin.gui.component.GuiView;

public class EnterpriseChatPanel extends GuiView implements ActionListener {

  private JPanel headPanel = new JPanel();

  private JPanel centerPanel = new JPanel();

  private JPanel statusPanel = new JPanel();

  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  private GridBagLayout gridBagLayout2 = new GridBagLayout();

  private EnterpriseTree enterpriseTree = new EnterpriseTree();

  private JScrollPane enterpriseScrollPane = new JScrollPane();

  private JPopupMenu enterprisePopupMenu = new JPopupMenu();

  private JMenuItem sendMessageMenu = new JMenuItem();

  private JMenuItem broadcastMessageMenu = new JMenuItem();

  private JMenuItem showInfoMenu = new JMenuItem();

  private JMenuItem showHistoryMenu = new JMenuItem();

  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GuiToolButton plusButton = new GuiToolButton("");
  private GuiToolButton minusButton = new GuiToolButton("");
  private GuiToolButton connectButton = new GuiToolButton("");
  private GuiToolButton settingButton = new GuiToolButton("");

  public EnterpriseChatPanel() {
    try {
      jbInit();
      initTree();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);

    headPanel.setLayout(gridBagLayout3);
    minusButton.setIcon(ImageUtil.getIcon("minus.png"));
    minusButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	expandAll(enterpriseTree,true);
        }
    });

    plusButton.setIcon(ImageUtil.getIcon("plus.png"));
    plusButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	expandAll(enterpriseTree,false);
        }
    });

    connectButton.setIcon(ImageUtil.getIcon("connect.png"));
    settingButton.setIcon(ImageUtil.getIcon("setting.png"));


    headPanel.add(minusButton,new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    headPanel.add(plusButton,new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    headPanel.add(connectButton,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    headPanel.add(settingButton,  new GridBagConstraints(3, 0, 1, 1, 0.01, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    this.add(headPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
        0, 0, 0, 0), 0, 0));
    centerPanel.setLayout(gridBagLayout2);
    centerPanel.add(enterpriseScrollPane, new GridBagConstraints(0, 0, 1, 1,
            0.01, 0.01, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    enterpriseScrollPane.getViewport().add(enterpriseTree, null);
    this.add(centerPanel, new GridBagConstraints(0, 2, 1, 1, 0.01, 0.01,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
        0, 0, 0, 0), 0, 0));


    this.add(statusPanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
        0, 0, 0, 0), 0, 0));

    sendMessageMenu.setText("发送消息");
    broadcastMessageMenu.setText("广播消息");
    showInfoMenu.setText("查看资料");
    showHistoryMenu.setText("历史记录");

    sendMessageMenu.addActionListener(this);
    broadcastMessageMenu.addActionListener(this);
    showInfoMenu.addActionListener(this);
    showHistoryMenu.addActionListener(this);

    enterprisePopupMenu.add(sendMessageMenu);
    enterprisePopupMenu.add(broadcastMessageMenu);
    enterprisePopupMenu.add(showInfoMenu);
    enterprisePopupMenu.add(showHistoryMenu);
  }

  	private void expandAll(JTree tree, boolean expand) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root), expand);
	}

	private void expandAll(JTree tree, TreePath parent, boolean expand) {

		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}



  /**
   * Invoked when an action occurs.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == sendMessageMenu) {
      DefaultMutableTreeNode selectNode =(DefaultMutableTreeNode)enterpriseTree.getLastSelectedPathComponent();
      if (selectNode.isLeaf()){
        Employee employee =(Employee) selectNode.getUserObject();
        employee.showMessageDialog();
      }
    }
  }

  //init tree panel
  private void initTree() {
    Icon rootIcon = ImageUtil.getIcon("company.gif");
    Icon departIcon = ImageUtil.getIcon("folder.gif");
    Icon userCloseIcon = ImageUtil.getIcon("user.gif");

    Employee p1 = new Employee();
    p1.setIdentityName("P1");

    p1.setIdentityIcon(userCloseIcon);
    Employee p2 = new Employee();
    p2.setIdentityName("P2");
    p2.setIdentityIcon(userCloseIcon);

    Department d1 = new Department();
    d1.setIdentityName("计划部门");
    d1.setIdentityIcon(departIcon);

    d1.addEmployees(new Employee[] { p1 });
    Department d2 = new Department();
    d2.setIdentityName("生产部门");
    d2.addEmployees(new Employee[] { p2 });
    d2.setIdentityIcon(departIcon);

    Enterprise organization = new Enterprise();
    organization.addDepartments(new Department[] { d1, d2 });
    organization.setIdentityIcon(rootIcon);
    organization.setIdentityName("XXXX公司");

    enterpriseTree.setEnterprise(organization);
    enterpriseTree.setCellRenderer(new EnterpriseTreeRender());
    enterpriseTree.getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    enterpriseTree.setShowsRootHandles(true);

    enterpriseTree.addMouseListener(new TreeMouseListener(this));
    // enterpriseTree.addTreeSelectionListener(new SelectionListener(this));
  }

  class TreeMouseListener extends MouseAdapter {
    private JPanel panel;

    public TreeMouseListener(JPanel panel) {
      this.panel = panel;
    }

    public JPanel getJPanel(){
    	return this.panel;
    }
    
    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void mouseClicked(MouseEvent e) {
      try {

        TreePath path = enterpriseTree.getPathForLocation(e.getX(),e.getY());
        if (path == null)
          return;

        DefaultMutableTreeNode selectNode=(DefaultMutableTreeNode)path.getLastPathComponent();
        if (selectNode.isLeaf()){
          if (e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e)) {
            //右键单击
            enterpriseTree.setSelectionPath(path);
            enterprisePopupMenu.show(enterpriseTree, e.getX(), e.getY());

          }else if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)){
            //右键双击
            enterpriseTree.setSelectionPath(path);
            Employee employee =(Employee) selectNode.getUserObject();
            employee.showMessageDialog();
          }
        }


      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    JFrame f = new JFrame();
    EnterpriseChatPanel panel = new EnterpriseChatPanel();
    panel.initTree();
//
//    f.setUndecorated(true); // 去掉窗口的装饰
//    f.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG );


    //f.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG );
    f.getContentPane().add(panel, BorderLayout.CENTER);
    f.setSize(200, 500);
    f.setVisible(true);
  }

}