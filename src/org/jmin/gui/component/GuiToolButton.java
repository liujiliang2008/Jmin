package org.jmin.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.AbstractBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class GuiToolButton extends JButton implements MouseListener {

  private static Border margin = BorderFactory.createEmptyBorder(1, 5, 1, 5);

  private static Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);

  private static Border raised = new OneLineBevelBorder(BevelBorder.RAISED);

  private static Border lowered = new OneLineBevelBorder(BevelBorder.LOWERED);

  private static Border normal = BorderFactory.createCompoundBorder(empty,
      margin);

  private static Border entered = BorderFactory.createCompoundBorder(raised,
      margin);

  private static Border pressed = BorderFactory.createCompoundBorder(lowered,
      margin);

  public GuiToolButton(String text) {
    this.setText(text);
    this.setBackground(null);
    this.setContentAreaFilled(false);
    this.setFocusPainted(false);
    this.addMouseListener(this);
    this.setBorder(normal);
  }

  public void updateUI() {
    super.updateUI();
    this.setNormalBorder();
  }

  void setNormalBorder() {
    this.setBorder(normal);
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
    if (this.isEnabled()) {
      setBorder(entered);
    }
  }

  public void mouseExited(MouseEvent e) {
    if (this.isEnabled()) {
      setBorder(normal);
    }
  }

  public void mousePressed(MouseEvent e) {
    if (this.isEnabled()) {
      setBorder(pressed);
    }
  }

  public void mouseReleased(MouseEvent e) {
    if (this.isEnabled() && getBorder() == pressed)
      setBorder(entered);
  }
}

class OneLineBevelBorder extends AbstractBorder {

  /** Raised bevel type. */
  public static final int RAISED = 0;

  /** Lowered bevel type. */
  public static final int LOWERED = 1;

  protected int bevelType;

  protected Color highlight;

  protected Color shadow;

  /**
   * Creates a bevel border with the specified type and whose
   * colors will be derived from the background color of the
   * component passed into the paintBorder method.
   * @param bevelType the type of bevel for the border
   */
  public OneLineBevelBorder(int bevelType) {
    this.bevelType = bevelType;
  }

  /**
   * Creates a bevel border with the specified type, highlight and
   * shadow colors.
   * @param bevelType the type of bevel for the border
   * @param highlight the color to use for the bevel highlight
   * @param shadow    the color to use for the bevel shadow
   */
  public OneLineBevelBorder(int bevelType, Color highlight, Color shadow) {
    this(bevelType);
    this.highlight = highlight;
    this.shadow = shadow;
  }

  /**
   * Paints the border for the specified component with the specified
   * position and size.
   * @param c the component for which this border is being painted
   * @param g the paint graphics
   * @param x the x position of the painted border
   * @param y the y position of the painted border
   * @param width the width of the painted border
   * @param height the height of the painted border
   */
  public void paintBorder(Component c, Graphics g, int x, int y, int width,
                          int height) {
    if (bevelType == RAISED) {
      paintRaisedBevel(c, g, x, y, width, height);
    } else if (bevelType == LOWERED) {
      paintLoweredBevel(c, g, x, y, width, height);
    }
  }

  /**
   * Returns the insets of the border.
   * @param c the component for which this border insets value applies
   */
  public Insets getBorderInsets(Component c) {
    return new Insets(1, 1, 1, 1);
  }

  /**
   * Reinitialize the insets parameter with this Border's current Insets.
   * @param c the component for which this border insets value applies
   * @param insets the object to be reinitialized
   */
  public Insets getBorderInsets(Component c, Insets insets) {
    insets.left = insets.top = insets.right = insets.bottom = 1;
    return insets;
  }

  /**
   * Returns the highlight color of the bevel border
   * when rendered on the specified component.  If no highlight
   * color was specified at instantiation, the highlight color
   * is derived from the specified component's background color.
   * @param c the component for which the highlight may be derived
   */
  public Color getHighlightColor(Component c) {
    Color highlight = getHighlightColor();
    return highlight != null ? highlight : c.getBackground().brighter();
  }

  /**
   * Returns the shadow color of the bevel border
   * when rendered on the specified component.  If no shadow
   * color was specified at instantiation, the shadow color
   * is derived from the specified component's background color.
   * @param c the component for which the shadow may be derived
   */
  public Color getShadowColor(Component c) {
    Color shadow = getShadowColor();
    return shadow != null ? shadow : c.getBackground().darker();
  }

  /**
   * Returns the highlight color of the bevel border.
   * Will return null if no highlight color was specified
   * at instantiation.
   */
  public Color getHighlightColor() {
    return highlight;
  }

  /**
   * Returns the shadow color of the bevel border.
   * Will return null if no shadow color was specified
   * at instantiation.
   */
  public Color getShadowColor() {
    return shadow;
  }

  /**
   * Returns the type of the bevel border.
   */
  public int getBevelType() {
    return bevelType;
  }

  /**
   * Returns whether or not the border is opaque.
   */
  public boolean isBorderOpaque() {
    return true;
  }

  protected void paintRaisedBevel(Component c, Graphics g, int x, int y,
                                  int width, int height) {
    Color oldColor = g.getColor();
    int h = height;
    int w = width;

    g.translate(x, y);

    g.setColor(getHighlightColor(c));
    g.drawLine(0, 0, 0, h - 2);
    g.drawLine(0, 0, w - 2, 0);

    g.setColor(getShadowColor(c));
    g.drawLine(0, h - 1, w - 1, h - 1);
    g.drawLine(w - 1, 0, w - 1, h - 2);

    g.translate(-x, -y);
    g.setColor(oldColor);
  }

  protected void paintLoweredBevel(Component c, Graphics g, int x, int y,
                                   int width, int height) {

    Color oldColor = g.getColor();
    int h = height;
    int w = width;

    g.translate(x, y);
    g.setColor(getShadowColor(c));
    g.drawLine(0, 0, 0, h - 1);
    g.drawLine(1, 0, w - 1, 0);

    g.setColor(getHighlightColor(c));
    g.drawLine(1, h - 1, w - 1, h - 1);
    g.drawLine(w - 1, 1, w - 1, h - 2);

    g.translate(-x, -y);
    g.setColor(oldColor);
  }

}