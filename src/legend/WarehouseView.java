package legend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import base.WindowStore;
import utility.file.XmlParser;

public class WarehouseView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 2L;
	private JScrollPane sp;
	private JPanel detailPanel = new JPanel();
	private JLabel detailLabel = new JLabel();
	private JPanel panel[] = new JPanel[99];
	private JLabel label[] = new JLabel[99];
	private JButton buttonEquip[] = new JButton[99];
	private JButton buttonDiscard[] = new JButton[99];
	private Box box0 = Box.createVerticalBox();
	private Box box1 = Box.createVerticalBox();
	XmlParser xmlParser;
	public final LegendView legendView = WindowStore.legendViewTL.get();
	int itemPosition = -1;
	
	public WarehouseView(String s) {
		super(s);
		WindowStore.warehouseViewTL.set(this);
		this.addWindowListener(new CloseHandler());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		
		detailPanel.setPreferredSize(new Dimension(380, 24));
		detailLabel.setPreferredSize(new Dimension(360, 22));
		detailPanel.add(detailLabel);
		box0.add(detailPanel);
		sp = new JScrollPane(box1);
		sp.setPreferredSize(new Dimension(400, 300));
		for (int i = 0; i < 99; i++) {
			panel[i] = new JPanel();
			panel[i].setPreferredSize(new Dimension(380, 24));
			label[i] = new JLabel();
			label[i].setPreferredSize(new Dimension(200, 22));
			showDetail(label[i], i);
			buttonEquip[i] = new JButton();
			buttonEquip[i].setPreferredSize(new Dimension(80, 22));
			buttonDiscard[i] = new JButton();
			buttonDiscard[i].setPreferredSize(new Dimension(80, 22));
			panel[i].add(label[i], BorderLayout.WEST);
			panel[i].add(buttonEquip[i]);
			panel[i].add(buttonDiscard[i], BorderLayout.EAST);
			box1.add(panel[i]);
		}
		refreshWarehouse();
		box0.add(sp);
		add(box0);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("WareHouse");
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent actionevent) {
		int equip = -1;
		int discard = -1;
		for (int i = 0; i < 99; i++) {
			if (actionevent.getSource() == buttonEquip[i]) {
				equip = i;
				break;
			}
		}
		if (equip >= 0) {
			itemPosition = equip;
			legendView.tempPosition = equip;
			xmlParser = new XmlParser("runSuite\\LegendHero.xml");
			legendView.reactFromWarehouse(xmlParser.getNodeValues("item").get(equip));
			xmlParser = new XmlParser("runSuite\\LegendHero.xml");
			if (!xmlParser.getNodeValues("item").get(equip).isEmpty()) {
				label[equip].setText(xmlParser.getNodeValues("item").get(equip).split("~")[1].split("  ")[0]);
			} else {
				label[equip].setText("");
				buttonEquip[equip].setText("");
				buttonDiscard[equip].setText("");
				buttonEquip[equip].removeActionListener(this);
				buttonDiscard[equip].removeActionListener(this);
			}
			return;
		}
		for (int i = 0; i < 99; i++) {
			if (actionevent.getSource() == buttonDiscard[i]) {
				discard = i;
				break;
			}
		}
		if (discard >= 0) {
			xmlParser = new XmlParser("runSuite\\LegendHero.xml");
			xmlParser.getNodeByName("item", discard).setTextContent("");
			xmlParser.save();
			label[discard].setText("");
			buttonEquip[discard].setText("");
			buttonDiscard[discard].setText("");
			buttonEquip[discard].removeActionListener(this);
			buttonDiscard[discard].removeActionListener(this);
			return;
		}
	}
	
	private void showDetail(final JLabel label, final int i) {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!xmlParser.getNodeValues("item").get(i).isEmpty()) {
					detailLabel.setText(xmlParser.getNodeValues("item").get(i).split("~")[1]);
				} else {
					detailLabel.setText("");
				}
			}
		});
	}
	
	private void refreshWarehouse() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		int itemCount = xmlParser.getNodeValues("item").size();
		for (int i = 0; i < itemCount; i++) {
			if (!xmlParser.getNodeValues("item").get(i).isEmpty()) {
				label[i].setText(xmlParser.getNodeValues("item").get(i).split("~")[1].split("  ")[0]);
				buttonEquip[i].setText("Equip");
				buttonDiscard[i].setText("Discard");
				buttonEquip[i].addActionListener(this);
				buttonDiscard[i].addActionListener(this);
			}
		}
	}
	
	public void reactFromDialog() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		label[itemPosition].setText(xmlParser.getNodeValues("item").get(itemPosition).split("~")[1].split("  ")[0]);
	}
	
	public static void main(String[] args) {
		WarehouseView test = new WarehouseView("");
		test.setVisible(true);
		test.setResizable(false);
	}
	
	private static class CloseHandler extends WindowAdapter {
		public void windowClosing(final WindowEvent event) {
			LegendView legendView = WindowStore.legendViewTL.get();
			legendView.setEnabled(true);
			legendView.requestFocus();
		}
	}
	
	public void freezeWindow() {
		WindowStore.warehouseViewTL.set(this);
		setEnabled(false);		
	}

}
