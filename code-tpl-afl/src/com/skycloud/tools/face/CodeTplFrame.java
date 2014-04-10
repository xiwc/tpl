/*
 * Created by JFormDesigner on Fri Jan 03 13:37:27 CST 2014
 */

package com.skycloud.tools.face;

import java.awt.*;
import javax.swing.*;

/**
 * @author xiweicheng
 */
public class CodeTplFrame extends JFrame {
	/** serialVersionUID [long] */
	private static final long serialVersionUID = -8881588298000112643L;

	public CodeTplFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		codeTplPanel1 = new CodeTplPanel();

		// ======== this ========
		setTitle("\u4ee3\u7801\u751f\u6210\u5de5\u5177-xiweicheng@yeah.net");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(codeTplPanel1, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private CodeTplPanel codeTplPanel1;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
