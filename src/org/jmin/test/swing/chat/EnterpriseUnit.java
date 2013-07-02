package org.jmin.test.swing.chat;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * 企业结构单元.
 *
 * @author Chris
 */

public class EnterpriseUnit {

  /**
   * 唯一身份标志符
   */
  private String identityID;

  /**
   * 身份描述
   */
  private String identityName;

  /**
   * 身份显示图标
   */
  private Icon identityIcon;

  public String getIdentityID() {
    return this.identityID;
  }

  public void setIdentityID(String identityID) {
    this.identityID = identityID;
  }

  public String getIdentityName() {
    return identityName;
  }

  public void setIdentityName(String identityName) {
    this.identityName = identityName;
  }

  public Icon getIdentityIcon() {
    return identityIcon;
  }

  public void setIdentityIcon(Icon identityIcon) {
    this.identityIcon = identityIcon;
  }

  public MutableTreeNode toMutableTreeNode() {
    return new DefaultMutableTreeNode(this);
  }

  /**
   * send message
   */
  public void sendMessage(String message){

  }

  public String toString() {
    return identityName;
  }
}