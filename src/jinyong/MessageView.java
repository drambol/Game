package jinyong;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.WindowStore;
import runSuite.IConstants;

public class MessageView extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 3L;

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JLabel label = new JLabel();
	private JButton button = new JButton("OK");
	private Box box = Box.createVerticalBox();
	public final HeroRank heroRank = WindowStore.heroRankTL.get();
	
	public MessageView(JFrame parent, String message) {
		super(parent, message, true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		button.addActionListener(this);
		label.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
		label.setText(message);
		panel1.add(label);
		panel2.add(button);
		
		box.add(panel1);
		box.add(panel2);
		add(box);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Message");
		setVisible(true);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getSource() == button) {
			heroRank.setEnabled(true);
		}
		this.dispose();
	}

}
