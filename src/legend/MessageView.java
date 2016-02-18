package legend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.WindowStore;
import legend.LegendView;

public class MessageView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JLabel label = new JLabel();
	private JButton button1 = new JButton();
	private JButton button2 = new JButton();
	private Box box = Box.createVerticalBox();
	public boolean flag = false;
	public final LegendView legendView = WindowStore.legendViewTL.get();
	String item = "";
	int position = 0;
	int count = 0;
	
	public MessageView(String s, int a, int b) {
		super(s);
		item = s;
		position = a;
		count = b;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		String str = "Which " + s + " would you like to replace?";
		label.setText(str);
		button1.setText("Left");
		button2.setText("Right");
		button1.addActionListener(this);
		button2.addActionListener(this);
		panel1.add(label);
		panel2.add(button1, BorderLayout.EAST);
		panel2.add(button2, BorderLayout.WEST);
		box.add(panel1);
		box.add(panel2);
		add(box);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Legend");
		setVisible(true);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getSource() == button1) {
			flag =  true;
		}
		this.dispose();
		legendView.reactFromMessage(flag, item, position, count);
		legendView.setEnabled(true);
		legendView.requestFocus();
	}

}
