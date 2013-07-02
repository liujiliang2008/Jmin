package org.jmin.test.swing.chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
/**
 * 企业部门类,一个部门下有雇员存在
 *
 * @author chris
 *
 */


public class Department extends EnterpriseUnit {

  private List employeeList = new ArrayList();

  public void addEmployee(Employee employee) {
    if (!employeeList.contains(employee)) {
      employeeList.add(employee);
    }
  }

  public void addEmployees(Employee[] employees) {
    for (int i = 0; i < employees.length; i++) {
      addEmployee(employees[i]);
    }
  }

  public void removeEmployee(Employee employee) {
    if (employeeList.contains(employee)) {
      employeeList.remove(employee);
    }
  }

  public void removeEmployees(Employee[] employees) {
    for (int i = 0; i < employees.length; i++) {
      removeEmployee(employees[i]);
    }
  }

  public MutableTreeNode toMutableTreeNode() {
    DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(this);
    for (int i = 0; i < employeeList.size(); i++) {
      currentNode.add(((EnterpriseUnit) employeeList.get(i)).toMutableTreeNode());
    }
    return currentNode;
  }

}