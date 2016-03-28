package controls;

import java.awt.Dimension;

import javax.swing.JButton;

public class MyButton extends JButton {
	
	private static final long serialVersionUID = 1L;

	public MyButton(String buttonName) {
		super(buttonName);
		this.setPreferredSize(new Dimension(108, 22));
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
