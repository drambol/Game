package jinyong;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class JuXianZhuang extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 2L;
	private JScrollPane sp;
	private JPanel panel[] = new JPanel[199];
	private JLabel label[] = new JLabel[199];
	private JButton buttonEquip[] = new JButton[200];
	private Box box0 = Box.createVerticalBox();
	private Box box1 = Box.createVerticalBox();
	XmlParser xmlParser;
	public final HeroRank heroRank = WindowStore.heroRankTL.get();
//	int itemPosition = -1;
	
	public JuXianZhuang(String s) {
		super(s);
		WindowStore.juXianZhuangTL.set(this);
		this.addWindowListener(new CloseHandler());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		
		sp = new JScrollPane(box1);
		sp.setPreferredSize(new Dimension(320, 300));
		for (int i = 0; i < 99; i++) {
			panel[i*2] = new JPanel();
			panel[i*2].setPreferredSize(new Dimension(300, 24));
			label[i*2] = new JLabel();
			label[i*2].setPreferredSize(new Dimension(80, 22));
			label[i*2+1] = new JLabel();
			label[i*2+1].setPreferredSize(new Dimension(80, 22));
			buttonEquip[i*2] = new JButton();
			buttonEquip[i*2].setPreferredSize(new Dimension(60, 22));
			buttonEquip[i*2+1] = new JButton();
			buttonEquip[i*2+1].setPreferredSize(new Dimension(60, 22));
			panel[i*2].add(label[i*2], BorderLayout.WEST);
			panel[i*2].add(buttonEquip[i*2]);
			panel[i*2].add(label[i*2+1]);
			panel[i*2].add(buttonEquip[i*2+1], BorderLayout.EAST);
			box1.add(panel[i*2]);
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
		for (int i = 0; i < 200; i++) {
			if (actionevent.getSource() == buttonEquip[i]) {
				equip = i;
				break;
			}
		}
		if (equip >= 0) {
//			itemPosition = equip;
			xmlParser = new XmlParser("runSuite\\JinyongHero.xml");
			heroRank.heroLabel[heroRank.position].setText(xmlParser.getNodeValues("item").get(equip));
			xmlParser.getNodesByName("lead").item(heroRank.position - 1).setTextContent(xmlParser.getNodeValues("item").get(equip));
			xmlParser.save();
			return;
		}
	}
	
	private void refreshWarehouse() {
		xmlParser = new XmlParser("runSuite\\JinyongHero.xml");
		int itemCount = xmlParser.getNodeValues("item").size();
		for (int i = 0; i < itemCount; i++) {
			if (!xmlParser.getNodeValues("item").get(i).isEmpty()) {
				label[i].setText(xmlParser.getNodeValues("item").get(i));
				buttonEquip[i].setText("上场");
				buttonEquip[i].addActionListener(this);
			}
		}
	}
	
	public static void main(String[] args) {
		JuXianZhuang test = new JuXianZhuang("");
		test.setVisible(true);
		test.setResizable(false);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static class CloseHandler extends WindowAdapter {
		public void windowClosing(final WindowEvent event) {
			HeroRank heroRank = WindowStore.heroRankTL.get();
			heroRank.setEnabled(true);
			heroRank.requestFocus();
		}
	}
	
	public void freezeWindow() {
		WindowStore.juXianZhuangTL.set(this);
		setEnabled(false);		
	}

}
