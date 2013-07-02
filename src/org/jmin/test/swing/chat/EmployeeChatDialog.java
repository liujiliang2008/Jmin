package org.jmin.test.swing.chat;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class EmployeeChatDialog extends JDialog {

  private JPanel headButtonPanel = new JPanel();

  private JPanel headColorPanel = new JPanel();

  private JPanel headPanel = new JPanel();

  private JPanel jPanel2 = new JPanel();

  private JPanel jPanel6 = new JPanel();

  private JPanel jPanel7 = new JPanel();

  private JPanel jPanel8 = new JPanel();

  private JPanel jPanel9 = new JPanel();

  private JPanel jPanel10 = new JPanel();

  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  private GridBagLayout gridBagLayout2 = new GridBagLayout();

  private GridBagLayout gridBagLayout3 = new GridBagLayout();

  private GridBagLayout gridBagLayout4 = new GridBagLayout();

  private GridBagLayout gridBagLayout5 = new GridBagLayout();

  private GridBagLayout gridBagLayout6 = new GridBagLayout();

  private GridBagLayout gridBagLayout7 = new GridBagLayout();

  private JScrollPane receiveScrollPane = new JScrollPane();

  private JScrollPane sendScrollPane = new JScrollPane();

  private JSplitPane messageSplitPane = new JSplitPane();

  private JTextArea receiveTextArea = new JTextArea();

  private JTextArea sendTextArea = new JTextArea();

  private JButton sendButton = new JButton();

  private JButton cancelButton = new JButton();

  private JButton historyButton = new JButton();

  private JButton searchButton = new JButton(ImageUtil.getIcon("person.gif"));

  private JButton searchButton1 = new JButton(ImageUtil.getIcon("person.gif"));

  private JButton searchButton2 = new JButton(ImageUtil.getIcon("person.gif"));

  private JButton searchButton3 = new JButton(ImageUtil.getIcon("person.gif"));

  private JButton searchButton4 = new JButton(ImageUtil.getIcon("person.gif"));

  /**
   *
   *
   */
  public EmployeeChatDialog(Frame owner,String title) {
    super(owner,title);
    jbInit();
  }

  void jbInit() {
    this.setSize(500, 500);
    this.getContentPane().setLayout(gridBagLayout1);

    headPanel.setLayout(new BorderLayout());
    headButtonPanel.setLayout(gridBagLayout2);
    headColorPanel.setSize(headColorPanel.getWidth(), 10);

    jPanel2.setLayout(gridBagLayout3);
    jPanel6.setLayout(gridBagLayout4);
    jPanel7.setLayout(gridBagLayout5);

    jPanel8.setLayout(gridBagLayout6);
    jPanel10.setLayout(gridBagLayout7);
    sendButton.setText("发送");
    cancelButton.setText("关闭");
    historyButton.setToolTipText("");
    historyButton.setText("消息记录");

    this.getContentPane().add(
                              headPanel,
                              new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                              GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                              new Insets(0, 0, 0, 0), 0, 0));

    this.getContentPane().add(
                              jPanel2,
                              new GridBagConstraints(0, 1, 1, 1, 0.01, 0.01,
                              GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                              new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jPanel6, new GridBagConstraints(1, 0, 1, 1, 0.01, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
        0, 0, 0, 0), 0, 0));
    jPanel2.add(jPanel7, new GridBagConstraints(0, 0, 1, 1, 0.01, 0.01,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
        0, 0, 0, 0), 0, 0));
    jPanel7.add(messageSplitPane, new GridBagConstraints(0, 0, 1, 1, 0.01,
        0.01, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));

    jPanel8.add(jPanel9, new GridBagConstraints(0, 0, 1, 1, 0.01, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
        0, 0, 0, 0), 0, 0));
    jPanel8.add(sendScrollPane, new GridBagConstraints(0, 1, 1, 1, 0.01,
        0.01, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel8.add(jPanel10, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
        0, 0, 0, 0), 0, 0));
    jPanel10.add(sendButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
        0, 0, 0, 0), 0, 0));
    jPanel10.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
        0, 0, 0, 0), 0, 0));
    jPanel10.add(historyButton, new GridBagConstraints(0, 0, 1, 1, 0.01,
        0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));

    headButtonPanel.add(searchButton, new GridBagConstraints(0, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    headButtonPanel.add(searchButton1, new GridBagConstraints(1, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    headButtonPanel.add(searchButton2, new GridBagConstraints(2, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    headButtonPanel.add(searchButton3, new GridBagConstraints(3, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    headButtonPanel.add(searchButton4, new GridBagConstraints(4, 0, 1, 1,
        0.01, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));

    headPanel.add(headButtonPanel, BorderLayout.NORTH);
    headPanel.add(headColorPanel, BorderLayout.SOUTH);

    receiveTextArea.setLineWrap(true);
    receiveTextArea.setEditable(false);
    sendTextArea.setLineWrap(true);

    sendScrollPane.getViewport().add(sendTextArea, null);
    receiveScrollPane.getViewport().add(receiveTextArea, null);
    messageSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    messageSplitPane.add(receiveScrollPane, JSplitPane.TOP);
    messageSplitPane.add(jPanel8, JSplitPane.BOTTOM);
    this.addComponentListener(new MCA());
  }

  void restoreGui() {
    messageSplitPane.setDividerLocation(0.7);
  }

  /**
   * Send a message to current employee
   */
  public void sendMessage(String messsage) {

  }

  /**
   * Receive a mssage from current employee
   */
  public void receiveMessage(String message) {

  }

  class MCA extends ComponentAdapter {
    public void componentResized(ComponentEvent e) {
      restoreGui();
    }
  }

  public static void main(String[] args) throws Exception {
    EmployeeChatDialog dialog = new EmployeeChatDialog(null,"Talk with XXX");
    dialog.setVisible(true);
    dialog.restoreGui();
  }
}