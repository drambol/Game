package controls;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JRadioButton;

import runSuite.IConstants;

public class MyRadioButton extends JRadioButton {
	
	private static final long serialVersionUID = 1L;

	public MyRadioButton(String buttonName) {
		super(buttonName);
		this.setPreferredSize(new Dimension(120, 20));
		this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
	}
	
	public MyRadioButton(String buttonName, int width) {
		super(buttonName);
		this.setPreferredSize(new Dimension(width, 20));
		this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
	}

}
