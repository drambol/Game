package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import base.WindowStore;
import runSuite.IConstants;

public class MessageView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width = 380;
	private int height = 180;
	private JTextArea msg = new JTextArea("");
	private int textWidth = 300;
	private int textHeight;
	private JButton button1 = new JButton("Back");
	private JPanel panel[] = new JPanel[5];
	private Box box = Box.createVerticalBox();
	public final MainView mainView = WindowStore.mainViewTL.get();
	
	public MessageView(String s) {
		super(s);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((screenSize.width - width)/2, (screenSize.height - height)/2, width, height);
		msg.setFont(new Font(IConstants.FONT, Font.PLAIN, 14));
		msg.setText("Could be dangerous, Drambol now is walking on the earth!");
		msg.setEditable(false);
		msg.setLineWrap(true);
		msg.setWrapStyleWord(true);
		textHeight = (msg.getText().length() / 42 + 1) * 16;
		System.out.println(textHeight);
		msg.setPreferredSize(new Dimension(textWidth, textHeight));
		msg.setForeground(Color.decode("#842B00"));
		msg.setOpaque(true);
		msg.setBackground(Color.GREEN);
		button1.setPreferredSize(new Dimension(115, 25));
		button1.addActionListener(this);
		panel[0] = new JPanel(new GridBagLayout());
		panel[1] = new JPanel();
		panel[0].add(msg);
		panel[0].setOpaque(true);
		panel[0].setBackground(Color.PINK);
//		panel[0].setAlignmentX(CENTER_ALIGNMENT);
		panel[1].add(button1);
		box.add(panel[0]);
		box.add(panel[1]);
		add(box);
		setVisible(true);
		
		//===================================================================================================
//		setSize(300, 300);
//		GridBagLayout layout = new GridBagLayout();
//		setLayout(layout);
//		MyPanel section1 = new MyPanel();
//		MyPanel section2 = new MyPanel();
//		MyPanel section3 = new MyPanel();
//		MyPanel section4 = new MyPanel();
//		MyPanel section5 = new MyPanel();
//		MyPanel section6 = new MyPanel();
//		MyPanel section7 = new MyPanel();
//		MyPanel section8 = new MyPanel();
//		JLabel label = new JLabel("Test Label");
//		JButton button = new JButton("Button");
//		add(section1);
//		add(section2);
//		add(label);
//		add(section3);
//		add(section4);
//		add(section5);
//		add(button);
//		add(section6);
//		add(section7);
//		add(section8);
//		GridBagConstraints bag = new GridBagConstraints();
//		bag.fill = GridBagConstraints.BOTH;
//		bag.gridwidth = 0;
//		bag.gridheight = 1;
//		bag.weightx = 1;
//		bag.weighty = 0;
//		layout.setConstraints(section1, bag);
//		bag.gridwidth = 1;
//		bag.gridheight = 2;
//		bag.weightx = 0;
//		bag.weighty = 0;
//		layout.setConstraints(section2, bag);
//		bag.gridwidth = 5;
//		bag.gridheight = 2;
//		bag.weightx = 1;
//		bag.weighty = 0;
//		layout.setConstraints(label, bag);
//		bag.gridwidth = 0;
//		bag.gridheight = 2;
//		bag.weightx = 0;
//		bag.weighty = 0;
//		layout.setConstraints(section3, bag);
//		bag.gridwidth = 0;
//		bag.gridheight = 1;
//		bag.weightx = 1;
//		bag.weighty = 0;
//		layout.setConstraints(section4, bag);
//		bag.gridwidth = 3;
//		bag.gridheight = 1;
//		bag.weightx = 0.3;
//		bag.weighty = 0;
//		layout.setConstraints(section5, bag);
//		bag.gridwidth = 1;
//		bag.gridheight = 1;
//		bag.weightx = 0;
//		bag.weighty = 0;
//		layout.setConstraints(button, bag);
//		bag.gridwidth = 3;
//		bag.gridheight = 1;
//		bag.weightx = 0.7;
//		bag.weighty = 0;
//		layout.setConstraints(section6, bag);
//		bag.gridwidth = 0;
//		bag.gridheight = 1;
//		bag.weightx = 0;
//		bag.weighty = 0;
//		layout.setConstraints(section7, bag);
//		bag.gridwidth = 0;
//		bag.gridheight = 1;
//		bag.weightx = 1;
//		bag.weighty = 1;
//		layout.setConstraints(section8, bag);
	}
	
	public static void main(String[] args) {
		MessageView test1 = new MessageView("");
		test1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test1.setVisible(true);
		test1.setResizable(false);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if(actionevent.getSource() == button1) {
			this.dispose();
			mainView.setVisible(true);
		}
	}
	
//	class MyPanel extends JPanel {
//		private static final long serialVersionUID = 1L;
//
//		MyPanel() {
//			super();
////			this.setPreferredSize(new Dimension(60, 25));
//		}
//	}

}
