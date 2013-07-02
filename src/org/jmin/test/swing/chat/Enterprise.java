package org.jmin.test.swing.chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * 企业定义对象,它支持三级结构:企业,部门,工作雇员.
 *
 * 一个企业下是允许有多个部门存在,工作雇员则存在于部门之中.
 *
 */

public class Enterprise extends EnterpriseUnit {

  private List departmentList = new ArrayList();

  public void addDepartment(Department department) {
    if (!departmentList.contains(department)) {
      departmentList.add(department);
    }
  }

  public void addDepartments(Department[] departments) {
    for (int i = 0; i < departments.length; i++) {
      addDepartment(departments[i]);
    }
  }

  public void removeDepartment(Department department) {
    if (departmentList.contains(department)) {
      departmentList.remove(department);
    }
  }

  public void removeDepartments(Department[] departments) {
    for (int i = 0; i < departments.length; i++) {
      removeDepartment(departments[i]);
    }
  }

  public MutableTreeNode toMutableTreeNode() {
    DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(this);
    for (int i = 0; i < departmentList.size(); i++) {
      currentNode.add(((EnterpriseUnit) departmentList.get(i))
      .toMutableTreeNode());
    }
    return currentNode;
  }

}