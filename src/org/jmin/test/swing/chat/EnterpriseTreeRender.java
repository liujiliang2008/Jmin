package org.jmin.test.swing.chat;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.jmin.test.swing.chat.EnterpriseUnit;

/** */
/**
 * 树节点渲染器
 * @author: sitinspring(junglesong@gmail.com)
 * @date: 2008-2-12
 */
public class EnterpriseTreeRender extends DefaultTreeCellRenderer {

  public Component getTreeCellRendererComponent(JTree tree, Object value,
      boolean sel, boolean expanded, boolean leaf, int row,
      boolean hasFocus) {

    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                                       row, hasFocus);
    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
    EnterpriseUnit node = (EnterpriseUnit) treeNode.getUserObject();
    setIcon(node.getIdentityIcon());
    return this;
  }
}