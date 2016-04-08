package controls;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import runSuite.IConstants;

public class MyLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;

	public MyLabel(String labelName) {
		super(labelName);
		this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
	}
	
	public MyLabel(String labelName, int width) {
		super(labelName);
		this.setPreferredSize(new Dimension(width, 22));
		this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
	}
	
	public void reset() {
		this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
	}

}
