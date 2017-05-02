package controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import runSuite.IConstants;

public class MyLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private String blueItem1 = "w25w30w31w32w33w34w35r25r26r27r28r29r30i25i26i27i28i29i30a24a25a26a27a28a29h08h09h10";
	private String blueItem2 = "c19c20c21c22c23c24c25c26c27c28c29c30b05b06b07o05o06o07g05g10g15g20g25g30x01x03";
	private String redItem = "w36w37w38r31r32r33r35r36r37r38r39r40r41r42i31i32i33a30a31a32h11h12h13c31c32x10x11";

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
	
	public void setColoredText(String text) {
		String blueItem = blueItem1 + blueItem2;
		String itemType = text.split("~")[0];
		if (itemType.contains("|") && redItem.contains(itemType.substring(0, 3))) {
			this.setForeground(new Color(255, 0, 255));
		} else if (itemType.contains("|RED|") && blueItem.contains(itemType.substring(0, 3))) {
			this.setForeground(new Color(255, 0, 255));
		} else if (redItem.contains(itemType.substring(0, 3))) {
			this.setForeground(new Color(255, 140, 0));
		} else if (itemType.contains("|") && blueItem.contains(itemType.substring(0, 3))) {
			this.setForeground(new Color(24, 116, 203));
		} else if (itemType.contains("|RED|")) {
			this.setForeground(new Color(24, 116, 203));//59, 179, 255
		} else if (blueItem.contains(itemType)) {
			this.setForeground(new Color(0, 203, 0));
		} else if (itemType.contains("|BLUE|")) {
			this.setForeground(new Color(0, 203, 0));
		} else {
			this.setForeground(new Color(0, 0, 0));
//			this.setFont(new Font(IConstants.FONT, Font.BOLD, 12));
		}
		this.setText(text.split("~")[1]);
	}
	
	public void setDefaultText(String text) {
		this.setForeground(Color.black);
		this.setText(text);
	}

}
