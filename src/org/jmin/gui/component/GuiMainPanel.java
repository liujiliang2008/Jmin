package org.jmin.gui.component;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GuiMainPanel extends JPanel implements ComponentListener {
	JPanel leftPane = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JSplitPane lefSplitPane = null;
	JSplitPane bottomSplitPane = null;

	public GuiMainPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		bottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPanel,bottomPanel);
		lefSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane,bottomSplitPane);
		this.setLayout(new BorderLayout());

		this.add(lefSplitPane, BorderLayout.CENTER);
		bottomSplitPane.setDividerSize(5);
		lefSplitPane.setDividerSize(5);
		this.addComponentListener(this);
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentResized(java.awt.event.ComponentEvent e) {
		lefSplitPane.setDividerLocation(0.2);
		bottomSplitPane.setDividerLocation(0.9);
	}

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("GuiMainPanel");
		GuiMainPanel panel = new GuiMainPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(800, 800);
		frame.setVisible(true);
	}
}
