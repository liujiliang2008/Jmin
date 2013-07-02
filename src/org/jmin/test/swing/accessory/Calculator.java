package org.jmin.test.swing.accessory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jmin.gui.component.GuiView;

public class Calculator extends GuiView implements ActionListener, KeyListener {
  private String command, arg, chg, txt;
  private double result;
  private String number = "123456789.0";
  private String operator = "/*-+=";
  private JTextField entrytext = new JTextField(20);
  private JButton numbut[]; // Number buttons
  private JButton combut[]; // Command buttons

  private JPanel companel = new JPanel();

  private JPanel numpanel = new JPanel();

  public static void main(String[] arguments) {
    JFrame frame = new JFrame();
   // BorderLayout dd;
    frame.getContentPane().add(new Calculator(),BorderLayout.CENTER);
    frame.setSize(350, 250);
    frame.setVisible(true);
  }

  public Calculator() {
    setSize(350, 250);
    this.setPreferredSize(this.getSize());
    addKeyListener(this);
    setBackground(Color.blue);
    companel.setBackground(Color.blue);
    numpanel.setBackground(Color.blue);

    this.setLayout(new GridLayout(1, 1));
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    setLayout(gridbag);
    constraints.weighty = 1;
    constraints.weightx = 1;
    Font bigFont = new Font("Courier", Font.BOLD, 14);

    constraints.gridwidth = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(entrytext, constraints);
    add(entrytext);
    entrytext.setFont(bigFont);
    entrytext.setEditable(false);
    entrytext.setForeground(Color.black);
    entrytext.setBackground(Color.white);
    entrytext.addKeyListener(this);
    entrytext.requestFocus();

    constraints.weighty = 1;
    constraints.weightx = 1;

    constraints.gridwidth = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(companel, constraints);
  /*
    * Command GridLayout --------------------- | Back | CE | C |
    * ---------------------
   */

    companel.setLayout(new GridLayout(1, 3, 5, 5));

    // Create the buttons
    Font comsFont = new Font("Arial", Font.BOLD, 12);
    String[] coms = { "Back", "CE", "C" };
    combut = new JButton[3];
    for (int i = 0; i <= 2; i++) {
      combut[i] = new JButton(coms[i]);
      companel.add(combut[i]);
      combut[i].addActionListener(this);
      combut[i].setFont(comsFont);
      combut[i].addKeyListener(this);

    }
    add(companel);
    companel.addKeyListener(this);

    constraints.weighty = 4;
    constraints.weightx = 1;

    constraints.gridwidth = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(numpanel, constraints);
  /*
    * Number GridLayout --------------------- | 7 | 8 | 9 | / |sqr|
    * --------------------- | 4 | 5 | 6 | * | % | --------------------- | 1 |
    * 2 | 3 | - |1/x| --------------------- | 0 |+/-| . | + | = |
    * ---------------------
   */
    numpanel.setLayout(new GridLayout(4, 5, 3, 3));
    // Create the buttons
    String[] nums = { "7", "8", "9", "/", "sqrt", "4", "5", "6", "*", "%",
      "1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "=" };
    numbut = new JButton[20];
    for (int i = 0; i <= 19; i++) {
      numbut[i] = new JButton(nums[i]);
      numpanel.add(numbut[i]);
      numbut[i].addActionListener(this);
      numbut[i].addKeyListener(this);
      if (operator.indexOf(nums[i]) > -1) {
        numbut[i].setForeground(Color.red);
      } else {
        numbut[i].setForeground(Color.blue);
      }

    }
    add(numpanel);
    numpanel.addKeyListener(this);

    // initialize global variables.
    command = "+";
    //copy = "";
    chg = "N";
    txt = "";
    arg = "";
    result = 0;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "Back")
      back_space();
    else if (e.getActionCommand() == "CE")
      entrytext.setText("");
    else if (e.getActionCommand() == "C") {
      result = 0;
      command = "+";
      entrytext.setText("");
    } else {
      arg = e.getActionCommand();
      txt = entrytext.getText();
      if (number.indexOf(arg) > -1) {
        if (chg == "Y")
          txt = "";
        txt = txt + arg;
        entrytext.setText(txt);
        chg = "N";
      } else
        check_entry();
    }
  }

  public void keyPressed(KeyEvent k) {
  }

  public void keyReleased(KeyEvent k) {
    int ikey = k.getKeyCode();
    if (ikey == 127)
      entrytext.setText("");
  }

  public void keyTyped(KeyEvent k) {
    int ikey = k.getKeyChar();
    if (ikey == 8)
      back_space();
    else if (ikey == 10) {
      arg = "=";
      txt = entrytext.getText();
      display_ans();
    } else {
      txt = entrytext.getText();
      char ckey = (char) ikey;
      arg = String.valueOf(ckey);
      if (number.indexOf(arg) > -1) {
        if (chg == "Y")
          txt = "";
        txt = txt + arg;
        entrytext.setText(txt);
        chg = "N";
      } else
        check_entry();
    }
  }

  public void back_space() {
    txt = entrytext.getText();
    int l = txt.length();
    if (l > 0) {
      txt = txt.substring(0, l - 1);
      entrytext.setText(txt);
    }
  }

  public void display_ans() {
    entrytext.setText(compute_tot(txt, command));
    chg = "Y";
    command = "+";
    result = 0;
  }

  public void check_entry() {
    if(txt==null|| txt.trim().equals(""))
      return;
    if (arg.equals("="))
      display_ans();
    else if (arg.equals("%")) {
      entrytext.setText(compute_tot(txt, arg));
      chg = "Y";
    } else if (operator.indexOf(arg) > -1) {
      entrytext.setText(compute_tot(txt, command));
      command = arg;
      chg = "Y";
    } else if (arg == "sqrt") {
      entrytext.setText(compute_tot(txt, arg));
      chg = "Y";
      command = "";
    } else if (arg == "+/-") {
      Double tnum = Double.valueOf(txt);
      double num = tnum.doubleValue();
      num = num * -1;
      entrytext.setText(String.valueOf(num));
      chg = "Y";
    } else if (arg == "1/x") {
      entrytext.setText(compute_tot(txt, arg));
      chg = "Y";
    }
  }

  String compute_tot(String t, String c) {
    Double tnum = Double.valueOf(t);
    double num = tnum.doubleValue();

    if (c.equals("+"))
      result = result + num;
    else if (c.equals("-"))
      result = result - num;
    else if (c.equals("*"))
      result = result * num;
    else if (c.equals("/"))
      result = result / num;
    else if (c.equals("%"))
      result = num / 100;
    else if (c.equals("+/-"))
      result = num * -1;
    else if (c.equals("1/x"))
      result = 1.000 / num;
    else if (c.equals("sqrt"))
      result = Math.sqrt(num);
    return String.valueOf(result);
  }
}

class CopyPaste implements ClipboardOwner {

  Clipboard clip;

  String ctxt;

  CopyPaste() {
    clip = new Clipboard("clip");
    ctxt = null;
  }

  void doCopy(String txt) {
    StringSelection trans = new StringSelection(txt);
    clip.setContents(trans, this);
  }

  void doPaste() {
    ctxt = null;
    Transferable toPaste = clip.getContents(this);
    if (toPaste != null) {
      try {
        ctxt = (String) toPaste
             .getTransferData(DataFlavor.stringFlavor);
      } catch (Exception e) {
        System.out.println("Error -- " + e.toString());
      }
    }

  }

  public void lostOwnership(Clipboard clip, Transferable contents) {
  }
}