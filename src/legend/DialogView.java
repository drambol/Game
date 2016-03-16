package legend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.WindowStore;

public class DialogView extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 3L;
	private JPanel messagePane = new JPanel();
	private JPanel buttonPane = new JPanel();
	private JButton buttonLeft = new JButton("Left");
	private JButton buttonRight = new JButton("Right");
	private boolean flag = false;
	private String itemType = "";
	private int itemPosition = -1;
	private String detailProperty = "";
	public final LegendView legendView = WindowStore.legendViewTL.get();
	public final WarehouseView warehouseView = WindowStore.warehouseViewTL.get();
	
	public DialogView(JFrame parent, String item, int position, String property) {
		super(parent, "Legend", true);
		this.addWindowListener(new CloseHandler());
		itemType = item;
		itemPosition = position;
		detailProperty = property;
		String message = "Which " + item + " would you like to replace?";
		messagePane.add(new JLabel(message));
		getContentPane().add(messagePane);
		buttonPane.add(buttonLeft, BorderLayout.WEST);
		buttonPane.add(buttonRight, BorderLayout.EAST);
		buttonLeft.addActionListener(this);
		buttonRight.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		if (actionevent.getSource() == buttonLeft) {
			flag =  true;
		}
		this.dispose();
		legendView.reactFromDialog(flag, itemType, itemPosition, detailProperty);
		if (legendView.fromMonster) {
			legendView.setEnabled(true);
			legendView.requestFocus();
		} else {
			warehouseView.setEnabled(true);
			legendView.requestFocus();
			warehouseView.requestFocus();
		}
	}
	
	private static class CloseHandler extends WindowAdapter {
		public void windowClosing(final WindowEvent event) {
			LegendView legendView = WindowStore.legendViewTL.get();
			legendView.setEnabled(true);
			legendView.requestFocus();
		}
	}

}
