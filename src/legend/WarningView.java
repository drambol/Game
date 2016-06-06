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

public class WarningView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 4L;
	private JPanel messagePane = new JPanel();
	private JPanel buttonPane = new JPanel();
	private JButton button = new JButton("OK");
	private int quitFlag;
	public final LegendView legendView = WindowStore.legendViewTL.get();
	public final WarehouseView warehouseView = WindowStore.warehouseViewTL.get();
	public final ShopView shopView = WindowStore.shopViewTL.get();
	
	public WarningView(JFrame parent, String message, int quitFlag) {
		super(parent, message, true);
		this.quitFlag = quitFlag;
		this.addWindowListener(new CloseHandler());
		messagePane.add(new JLabel(message));
		getContentPane().add(messagePane);
		buttonPane.add(button);
		button.addActionListener(this);
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
		if (actionevent.getSource() == button) {
			setVisible(false);
			dispose();
			switch (quitFlag) {
			case 0:
				System.exit(0);
				break;
			case 1:
				if (legendView.fromMonster) {
					legendView.setEnabled(true);
					legendView.requestFocus();
				} else {
					warehouseView.setEnabled(true);
					legendView.requestFocus();
					warehouseView.requestFocus();
				}
				break;
			case 2:
				shopView.setEnabled(true);
				legendView.setEnabled(true);
				legendView.requestFocus();
				legendView.freezeWindow();
				shopView.requestFocus();
				break;
			}
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
