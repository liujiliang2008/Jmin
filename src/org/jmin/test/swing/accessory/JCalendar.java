package org.jmin.test.swing.accessory;

/**
 * Created by IntelliJ IDEA.
 * User: chenzixine
 * Date: 2008-1-5
 * Time: 14:34:21
 * Hello World.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.jmin.gui.component.GuiView;

/**
 * <p>Title: Swing日历</p>
 * <p>Description: 操作日期</p>
 * @author duxu2004
 * @version 1.0.1
 */

public class JCalendar extends GuiView {
  //动态表示年月日
  private int year = 0;

  private int month = 0;

  private int day = 0;

  //主面板
  private JPanel Main = new JPanel();

  //日面板
  private JPanel jPanelDay = new JPanel();

  //月面板
  private JPanel jPanelMonth = new JPanel();

  //年的输入位置
  private JTextField Year = new JTextField();

  //月的输入位置
  private JTextField Month = new JTextField();

  //减少月份
  private JButton MonthDown = new JButton();

  //增加月份
  private JButton MonthUp = new JButton();

  private JPanel jPanelButton = new JPanel();

  //减少年份
  private JButton YearDown = new JButton();

  //增加年份
  private JButton YearUp = new JButton();

  //显示日期的位置
  private JLabel Out = new JLabel();

  //中国时区，以后可以从这里扩展可以设置时区的功能
  private Locale l = Locale.CHINESE;

  //主日历
  private GregorianCalendar cal = new GregorianCalendar(l);

  //星期面板
  private JPanel weekPanel = new JPanel();

  //天按钮组
  private JToggleButton[] days = new JToggleButton[42];

  //天面板
  private JPanel Days = new JPanel();

  //标示
  private JLabel jLabel1 = new JLabel();

  private JLabel jLabel2 = new JLabel();

  private JLabel jLabel3 = new JLabel();

  private JLabel jLabel4 = new JLabel();

  private JLabel jLabel5 = new JLabel();

  private JLabel jLabel6 = new JLabel();

  private JLabel jLabel7 = new JLabel();


  //月份天数数组，用来取得当月有多少天
  // 1 2 3 4 5 6 7 8 9 10 11 12
  private int[] mm = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  //空日期构造函数
  public JCalendar() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  //带日期设置的构造函数
  public JCalendar(int year, int month, int day) {
    cal.set(year, month, day);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //带日历输入的构造函数
  public JCalendar(GregorianCalendar calendar) {
    cal = calendar;
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //带日期输入的构造函数
  public JCalendar(Date date) {
    cal.setTime(date);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void open() {
    iniCalender();
    showMe();
  }

  //初始化组件
  private void jbInit() throws Exception {
    //初始化年、月、日
    iniCalender();
    this.setLayout(new BorderLayout());
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    this.setMaximumSize(new Dimension(200, 200));
    this.setMinimumSize(new Dimension(200, 200));
    this.setPreferredSize(new Dimension(200, 200));

    Main.setLayout(new BorderLayout());
    Main.setBackground(SystemColor.info);
    Main.setBorder(null);

    Out.setBackground(Color.lightGray);
    Out.setHorizontalAlignment(SwingConstants.CENTER);
    Out.setMaximumSize(new Dimension(100, 19));
    Out.setMinimumSize(new Dimension(100, 19));
    Out.setPreferredSize(new Dimension(100, 19));

    jLabel1.setForeground(Color.red);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText("日");
    jLabel2.setForeground(Color.blue);
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel2.setText("六");
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel3.setText("五");
    jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel4.setText("四");
    jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel5.setText("三");
    jLabel6.setBorder(null);
    jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel6.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel6.setText("二");
    jLabel7.setBackground(Color.lightGray);
    jLabel7.setForeground(Color.black);
    jLabel7.setBorder(null);
    jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel7.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel7.setText("一");

    weekPanel.setBackground(UIManager
                            .getColor("InternalFrame.activeTitleGradient"));
    weekPanel.setBorder(BorderFactory.createEtchedBorder());
    weekPanel.setLayout(new GridLayout(1, 7));
    weekPanel.add(jLabel1, null);
    weekPanel.add(jLabel7, null);
    weekPanel.add(jLabel6, null);
    weekPanel.add(jLabel5, null);
    weekPanel.add(jLabel4, null);
    weekPanel.add(jLabel3, null);
    weekPanel.add(jLabel2, null);

    MonthUp.setAlignmentX((float) 0.0);
    MonthUp.setActionMap(null);

    jPanelMonth.setBackground(SystemColor.info);
    jPanelMonth.setLayout(new BorderLayout());
    jPanelMonth.setBorder(BorderFactory.createEtchedBorder());

    Month.setBorder(null);
    Month.setHorizontalAlignment(SwingConstants.CENTER);
    Month.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Month_mouseClicked(e);
      }
    });
    Month.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        Month_keyPressed(e);
      }
    });

    MonthDown.setBorder(null);
    MonthDown.setText("\u25C4");
    MonthDown.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MonthDown_actionPerformed(e);
      }
    });
    MonthUp.setBorder(null);
    MonthUp.setText("\u25BA");
    MonthUp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MonthUp_actionPerformed(e);
      }
    });

    jPanelButton.setLayout(null);
    jPanelButton.setBorder(null);
    jPanelButton
    .addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(
          java.awt.event.ComponentEvent evt) {
        jPanelButtonComponentResized(evt);
      }
    });

    Year.setBorder(BorderFactory.createEtchedBorder());
    Year.setMaximumSize(new Dimension(80, 25));
    Year.setMinimumSize(new Dimension(80, 25));
    Year.setPreferredSize(new Dimension(80, 25));
    Year.setHorizontalAlignment(SwingConstants.CENTER);
    Year.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Year_mouseClicked(e);
      }
    });
    Year.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        Year_keyPressed(e);
      }
    });

    YearDown.setBorder(null);
    YearDown.setMaximumSize(new Dimension(16, 16));
    YearDown.setMinimumSize(new Dimension(16, 16));
    YearDown.setPreferredSize(new Dimension(16, 16));
    YearDown.setSize(new Dimension(16, 16));
    YearDown.setText("▼");
    YearDown.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        YearDown_actionPerformed(e);
      }
    });
    YearUp.setBorder(null);
    YearUp.setMaximumSize(new Dimension(16, 16));
    YearUp.setMinimumSize(new Dimension(16, 16));
    YearUp.setPreferredSize(new Dimension(16, 16));
    YearUp.setSize(new Dimension(16, 16));
    YearUp.setText("▲");
    YearUp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        YearUp_actionPerformed(e);
      }
    });

    jPanelDay.setLayout(new BorderLayout());

    Days.setLayout(new GridLayout(6, 7));
    Days.setBackground(SystemColor.info);

    for (int i = 0; i < 42; i++) {
      days[i] = new JToggleButton();
      days[i].setBorder(null);
      days[i].setBackground(SystemColor.info);
      days[i].setHorizontalAlignment(SwingConstants.CENTER);
      days[i].setHorizontalTextPosition(SwingConstants.CENTER);
      //days[i].setSize(l,l);
      days[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          day = Integer.parseInt(((JToggleButton) e.getSource())
                                 .getText());
          showDate();
          showDays();
        }
      });
      Days.add(days[i]);
    }

    this.add(Main, BorderLayout.NORTH);
    this.add(jPanelDay, BorderLayout.CENTER);
    this.add(jPanelMonth, BorderLayout.SOUTH);

    Main.add(Year, BorderLayout.CENTER);
    Main.add(Out, BorderLayout.WEST);
    Main.add(jPanelButton, BorderLayout.EAST);

    jPanelButton.add(YearUp);
    jPanelButton.add(YearDown);

    jPanelDay.add(weekPanel, BorderLayout.NORTH);
    jPanelDay.add(Days, BorderLayout.CENTER);

    jPanelMonth.add(Month, BorderLayout.CENTER);
    jPanelMonth.add(MonthDown, BorderLayout.WEST);
    jPanelMonth.add(MonthUp, BorderLayout.EAST);

    showMonth();
    showYear();
    showDate();
    showDays();
  }

  //自定义重画年选择面板
  void jPanelButtonComponentResized(java.awt.event.ComponentEvent evt) {
    YearUp.setLocation(0, 0);
    YearDown.setLocation(0, YearUp.getHeight());
    jPanelButton.setSize(YearUp.getWidth(), YearUp.getHeight() * 2);
    jPanelButton.setPreferredSize(new Dimension(YearUp.getWidth(), YearUp
        .getHeight() * 2));
    jPanelButton.updateUI();
  }

  //增加年份
  void YearUp_actionPerformed(ActionEvent e) {
    year++;
    showYear();
    showDate();
    showDays();
  }

  //减少年份
  void YearDown_actionPerformed(ActionEvent e) {
    year--;
    showYear();
    showDate();
    showDays();
  }

  //减少月份
  void MonthDown_actionPerformed(ActionEvent e) {
    month--;
    if (month < 0) {
      month = 11;
      year--;
      showYear();
    }
    showMonth();
    showDate();
    showDays();
  }

  //增加月份
  void MonthUp_actionPerformed(ActionEvent e) {
    month++;
    if (month == 12) {
      month = 0;
      year++;
      showYear();
    }
    showMonth();
    showDate();
    showDays();
  }

  //初始化年月日
  void iniCalender() {
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH);
    day = cal.get(Calendar.DAY_OF_MONTH);
  }

  //刷新月份
  void showMonth() {
    Month.setText(Integer.toString(month + 1) + "月");
  }

  //刷新年份
  void showYear() {
    Year.setText(Integer.toString(year) + "年");
  }

  //刷新日期
  void showDate() {
    Out.setText(Integer.toString(year) + "-" + Integer.toString(month + 1)
                + "-" + Integer.toString(day));
  }

  //重画天数选择面板
  void showDays() {
    cal.set(year, month, 1);
    int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    int n = mm[month];
    if (cal.isLeapYear(year) && month == 1)
      n++;
    int i = 0;
    for (; i < firstDayOfWeek - 1; i++) {
      days[i].setEnabled(false);
      days[i].setSelected(false);
      days[i].setText("");
    }
    int d = 1;
    for (; d <= n; d++) {
      days[i].setText(Integer.toString(d));
      days[i].setEnabled(true);
      if (d == day)
        days[i].setSelected(true);
      else
        days[i].setSelected(false);
      ;
      i++;
    }
    for (; i < 42; i++) {
      days[i].setEnabled(false);
      days[i].setSelected(false);
      days[i].setText("");
    }
  }

  //单击年份面板选择整个年份字符串
  void SelectionYear() {
    Year.setSelectionStart(0);
    Year.setSelectionEnd(Year.getText().length());
  }

  //单击月份面板选择整个月份字符串
  void SelectionMonth() {
    Month.setSelectionStart(0);
    Month.setSelectionEnd(Month.getText().length());
  }

  //月份面板响应鼠标单击事件
  void Month_mouseClicked(MouseEvent e) {
    //SelectionMonth();
    inputMonth();
  }

  //检验输入的月份
  void inputMonth() {
    String s;
    if (Month.getText().endsWith("月")) {
      s = Month.getText().substring(0, Month.getText().length() - 1);
    } else
      s = Month.getText();
    month = Integer.parseInt(s) - 1;
    this.showMe();
  }

  //月份面板键盘敲击事件响应
  void Month_keyPressed(KeyEvent e) {
    if (e.getKeyChar() == 10)
      inputMonth();
  }

  //年份面板响应鼠标单击事件
  void Year_mouseClicked(MouseEvent e) {
    //SelectionYear();
    inputYear();
  }

  //年份键盘敲击事件响应
  void Year_keyPressed(KeyEvent e) {
    //System.out.print(new Integer(e.getKeyChar()).byteValue());
    if (e.getKeyChar() == 10)
      inputYear();
  }

  //检验输入的年份字符串
  void inputYear() {
    String s;
    if (Year.getText().endsWith("年")) {
      s = Year.getText().substring(0, Year.getText().length() - 1);
    } else
      s = Year.getText();
    year = Integer.parseInt(s);
    this.showMe();
  }

  //以字符串形式返回日期,yyyy-mm-dd
  public String getDate() {
    return Out.getText();
  }

  //以字符串形式输入日期,yyyy-mm-dd
  public void setDate(String date) {
    if (date != null) {
      StringTokenizer f = new StringTokenizer(date, "-");
      if (f.hasMoreTokens())
        year = Integer.parseInt(f.nextToken());
      if (f.hasMoreTokens())
        month = Integer.parseInt(f.nextToken());
      if (f.hasMoreTokens())
        day = Integer.parseInt(f.nextToken());
      cal.set(year, month, day);
    }
    this.showMe();
  }

  //以日期对象形式输入日期
  public void setTime(Date date) {
    cal.setTime(date);
    this.iniCalender();
    this.showMe();
  }

  //返回日期对象
  public Date getTime() {
    return cal.getTime();
  }

  //返回当前的日
  public int getDay() {
    return day;
  }

  //设置当前的日
  public void setDay(int day) {
    this.day = day;
    cal.set(this.year, this.month, this.day);
    this.showMe();
  }

  //设置当前的年
  public void setYear(int year) {
    this.year = year;
    cal.set(this.year, this.month, this.day);
    this.showMe();
  }

  //返回当前的年
  public int getYear() {
    return year;
  }

  //返回当前的月
  public int getMonth() {
    return month;
  }

  //设置当前的月
  public void setMonth(int month) {
    this.month = month;
    cal.set(this.year, this.month, this.day);
    this.showMe();
  }

  //刷新
  public void showMe() {
    this.showDays();
    this.showMonth();
    this.showYear();
    this.showDate();
  }

  //测试用
  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setContentPane(new JCalendar());
    f.pack();
    //f.setResizable(false);
    //f.show();
    f.setVisible(true);
  }
}