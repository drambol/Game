package legend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.WindowStore;
import controls.MyButton;

public class ConfirmView extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 7L;
	private JPanel textPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JLabel text = new JLabel();
	private MyButton buttonOk = new MyButton("OK", 75);
	private MyButton buttonCancel = new MyButton("Cancel", 75);
	private int source;
	
	public ConfirmView(JFrame parent, String message, int source) {
		super(parent, message, true);
		this.source = source;
		text.setText(message);
		buttonOk.addActionListener(this);
		buttonCancel.addActionListener(this);
		textPanel.add(text);
		buttonPanel.add(buttonOk, BorderLayout.WEST);
		buttonPanel.add(buttonCancel, BorderLayout.EAST);
		getContentPane().add(textPanel);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Dimension dialogSize = this.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 2 - dialogSize.width / 2, p.y + parentSize.height / 2 - dialogSize.height / 2);
		}
		setTitle("Legend");
		setVisible(true);
	}

	public void actionPerformed(ActionEvent actionevent) {
		LegendView legendView = WindowStore.legendViewTL.get();
		if (actionevent.getSource() == buttonOk) {
			this.dispose();
			legendView.confirmFlag = true;
			legendView.reactFromConfirm(source);
		}
		if (actionevent.getSource() == buttonCancel) {
			this.dispose();
			legendView.confirmFlag = false;
			legendView.reactFromConfirm(source);
		}
	}

}
