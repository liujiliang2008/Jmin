package org.jmin.test.swing.security;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.security.Permissions;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jmin.gui.security.User;

public class LoginDialog extends JDialog{
	
  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  private JPanel jPanel1 = new JPanel();

  private JPanel jPanel2 = new JPanel();

  private GridBagLayout gridBagLayout2 = new GridBagLayout();

  private JLabel jLabel1 = new JLabel();

  private JTextField jTextField1 = new JTextField();

  private JLabel jLabel2 = new JLabel();

  private JTextField jTextField2 = new JTextField();

  private JButton jButton1 = new JButton();

  private JButton jButton2 = new JButton();

  private JLabel jLabel3 = new JLabel();

  private JComboBox jComboBox1 = new JComboBox();

  public LoginDialog() {
    super((JFrame) null, "Login", true);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  

  private void jbInit() throws Exception {

    this.setContentPane(new JPanel());
    this.setSize(200, 200);
    this.setResizable(false);

    this.getContentPane().setLayout(gridBagLayout1);
    jPanel2.setLayout(gridBagLayout2);
    jLabel1.setText("用户名：");
    jTextField1.setColumns(10);
    jLabel2.setText("密码：");
    jTextField2.setColumns(10);
    jButton1.setText("确认");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText("取消");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jLabel3.setText("单位：");
    this.getContentPane().add(
                              jPanel1,
                              new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                              GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                              new Insets(0, 0, 0, 0), 0, 0));

    jPanel1.add(jButton1, null);
    jPanel1.add(jButton2, null);

    this.getContentPane().add(
                              jPanel2,
                              new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                              GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                              new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
        GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
        0, 0, 0), 0, 0));
    jPanel2.add(jTextField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,
        5, 5, 5), 0, 0));
    jPanel2.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
        GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
        0, 0, 0), 0, 0));
    jPanel2.add(jTextField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,
        5, 5, 5), 0, 0));
    jPanel2.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
        GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
        0, 0, 0), 0, 0));
    jPanel2.add(jComboBox1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,
        5, 5, 5), 0, 0));

    jComboBox1.insertItemAt("A 厂", 0);
    jComboBox1.insertItemAt("B 厂", 1);
    jComboBox1.insertItemAt("C 厂", 2);
 }
  
  public void keyTyped(KeyEvent e){
  	System.out.println("keyTyped");
  }
  public void keyPressed(KeyEvent e){
  	System.out.println("keyPressed");
  	Object ob = e.getSource();
  	System.out.println(ob);
  	if(ob == jTextField1){
  		System.out.println("jTextField1");
  	}
  	
	  if(ob == jTextField2){
	  	System.out.println("jTextField2");
  	}
  }
  public void keyReleased(KeyEvent e){
  	System.out.println("keyReleased");
  }
  
  
  
  

  public User getUserInfo() {
    User info = new User();
    info.setUserID("Chris");
    info.setPermissionList(new Permissions());
    return info;
  }

  public static void main(String[] args) {
    LoginDialog view = new LoginDialog();
    view.setVisible(true);
  }

  void jButton1_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void jButton2_actionPerformed(ActionEvent e) {
    System.exit(-1);
  }
}