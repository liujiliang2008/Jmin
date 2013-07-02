package org.jmin.test.swing.chat;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class EnterpriseTree extends JTree {

  public EnterpriseTree() {
  }

  public EnterpriseTree(Enterprise enterprise) {
    TreeNode rootNode = enterprise.toMutableTreeNode();
    this.setModel(new DefaultTreeModel(rootNode));
  }

  public void setEnterprise(Enterprise enterprise) {
    TreeNode rootNode = enterprise.toMutableTreeNode();
    this.setModel(new DefaultTreeModel(rootNode));
  }

}