package org.jmin.gui.util;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import javax.swing.UIManager;

/**
 * Application class for the current framework.
 *
 * @author chris
 */

public class GuiUtil {
  
  public static int[] getKeys(String value) {
    int keyPairs[] = null;

    String[] keys = GuiUtil.split(value,"[+]");
    if (keys.length > 3 || keys.length < 1)
      throw new java.lang.IllegalArgumentException(
          "Invalid shortcut configeruation");
    if (keys.length == 1) {
      keyPairs = new int[1];
      keyPairs[0] = getCharacterKey(keys[0]);

    }else if (keys.length == 2) {
      keyPairs = new int[2];
      keyPairs[0] = getControlKey(keys[0]);
      keyPairs[1] = getCharacterKey(keys[1]);
    } else if (keys.length == 3) {
      keyPairs = new int[2];
      keyPairs[0] = getControlKey(keys[0]);
      keyPairs[0] |= getControlKey(keys[1]);
      keyPairs[1] = getCharacterKey(keys[2]);
    }

    return keyPairs;
  }
  
	/**
	 * 将字符串分割成多个子串
	 */
	public static String[] split(String source,String sub) {
		int i=0;
		StringTokenizer token = new StringTokenizer(source,sub);
		String[] subStrings= new String[token.countTokens()];
		while(token.hasMoreElements()){
			subStrings[i++] =(String)token.nextElement();
		}
		return subStrings;
	}
  

  private static int getControlKey(String value) {
    if (value.trim().equalsIgnoreCase("alt")) {
      return KeyEvent.ALT_MASK;
    } else if (value.trim().equalsIgnoreCase("ctrl")) {
      return KeyEvent.CTRL_MASK;
    } else if (value.trim().equalsIgnoreCase("shift")) {
      return KeyEvent.SHIFT_MASK;
    } else {
      throw new java.lang.IllegalArgumentException("Invalid control key");
    }
  }

  private static int getCharacterKey(String value) {
    int result = -1;

    if ("A".equals(value)) {
      result = KeyEvent.VK_A;
    } else if ("B".equals(value)) {
      result = KeyEvent.VK_B;
    } else if ("C".equals(value)) {
      result = KeyEvent.VK_C;
    } else if ("D".equals(value)) {
      result = KeyEvent.VK_D;
    } else if ("E".equals(value)) {
      result = KeyEvent.VK_E;
    } else if ("F".equals(value)) {
      result = KeyEvent.VK_F;
    } else if ("G".equals(value)) {
      result = KeyEvent.VK_G;
    } else if ("H".equals(value)) {
      result = KeyEvent.VK_H;
    } else if ("I".equals(value)) {
      result = KeyEvent.VK_I;
    } else if ("J".equals(value)) {
      result = KeyEvent.VK_J;
    } else if ("K".equals(value)) {
      result = KeyEvent.VK_K;
    } else if ("L".equals(value)) {
      result = KeyEvent.VK_L;
    } else if ("M".equals(value)) {
      result = KeyEvent.VK_M;
    } else if ("N".equals(value)) {
      result = KeyEvent.VK_N;
    } else if ("O".equals(value)) {
      result = KeyEvent.VK_O;
    } else if ("P".equals(value)) {
      result = KeyEvent.VK_P;
    } else if ("Q".equals(value)) {
      result = KeyEvent.VK_Q;
    } else if ("R".equals(value)) {
      result = KeyEvent.VK_R;
    } else if ("S".equals(value)) {
      result = KeyEvent.VK_S;
    } else if ("T".equals(value)) {
      result = KeyEvent.VK_T;
    } else if ("U".equals(value)) {
      result = KeyEvent.VK_U;
    } else if ("V".equals(value)) {
      result = KeyEvent.VK_V;
    } else if ("W".equals(value)) {
      result = KeyEvent.VK_W;
    } else if ("X".equals(value)) {
      result = KeyEvent.VK_X;
    } else if ("Y".equals(value)) {
      result = KeyEvent.VK_Y;
    } else if ("Z".equals(value)) {
      result = KeyEvent.VK_Z;
    } else if ("F1".equals(value)) {
      result = KeyEvent.VK_F1;
    } else if ("F2".equals(value)) {
      result = KeyEvent.VK_F2;
    } else if ("F3".equals(value)) {
      result = KeyEvent.VK_F3;
    } else if ("F4".equals(value)) {
      result = KeyEvent.VK_F4;
    } else if ("F5".equals(value)) {
      result = KeyEvent.VK_F5;
    } else if ("F6".equals(value)) {
      result = KeyEvent.VK_F6;
    } else if ("F7".equals(value)) {
      result = KeyEvent.VK_F7;
    } else if ("F8".equals(value)) {
      result = KeyEvent.VK_F8;
    } else if ("F9".equals(value)) {
      result = KeyEvent.VK_F9;
    } else if ("F10".equals(value)) {
      result = KeyEvent.VK_F10;
    } else if ("F11".equals(value)) {
      result = KeyEvent.VK_F11;
    } else if ("F12".equals(value)) {
      result = KeyEvent.VK_F12;
    }

    if (result == -1)
      throw new java.lang.IllegalArgumentException(
          "Invalid shortcut configeruation");
    else
      return result;
  }
  
  /**
   * 初始化GUI字体
   */
  public static void initGuiFont(){
    Font font = new Font("Dialog", Font.PLAIN, 12);
    UIManager.put("ToolTip.font", font);
    UIManager.put("Table.font", font);
    UIManager.put("TableHeader.font", font);
    UIManager.put("TextField.font", font);
    UIManager.put("ComboBox.font", font);
    UIManager.put("TextField.font", font);
    UIManager.put("PasswordField.font", font);
    UIManager.put("TextArea.font", font);
    UIManager.put("TextPane.font", font);
    UIManager.put("EditorPane.font", font);
    UIManager.put("FormattedTextField.font", font);
    UIManager.put("Button.font", font);
    UIManager.put("CheckBox.font", font);
    UIManager.put("RadioButton.font", font);
    UIManager.put("ToggleButton.font", font);
    UIManager.put("ProgressBar.font", font);
    UIManager.put("DesktopIcon.font", font);
    UIManager.put("TitledBorder.font", font);
    UIManager.put("Label.font", font);
    UIManager.put("List.font", font);
    UIManager.put("TabbedPane.font", font);
    UIManager.put("MenuBar.font", font);
    UIManager.put("Menu.font", font);
    UIManager.put("MenuItem.font", font);
    UIManager.put("PopupMenu.font", font);
    UIManager.put("CheckBoxMenuItem.font", font);
    UIManager.put("RadioButtonMenuItem.font", font);
    UIManager.put("Spinner.font", font);
    UIManager.put("Tree.font", font);
    UIManager.put("ToolBar.font", font);
    UIManager.put("OptionPane.messageFont", font);
    UIManager.put("OptionPane.buttonFont", font);
  }
}
