package controls;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

import runSuite.IConstants;

public class MyButton extends JButton {
	
	private static final long serialVersionUID = 1L;

	public MyButton(String buttonName) {
		super(buttonName);
		this.setPreferredSize(new Dimension(108, 22));
		this.setFont(new Font("Arial", Font.PLAIN, 12));
	}
	
	public MyButton(String buttonName, int width) {
		super(buttonName);
		this.setPreferredSize(new Dimension(width, 22));
	}
	
	public MyButton(String buttonName, Dimension dimension) {
		super(buttonName);
		this.setPreferredSize(dimension);
	}

}
