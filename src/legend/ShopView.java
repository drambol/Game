package legend;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import base.WindowStore;
import clazz.Legend;
import clazz.LegendItem;
import utility.file.XmlParser;

public class ShopView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 9L;
	private JPanel panel[] = new JPanel[10];
	private JLabel labelItem[] = new JLabel[10];
	private JLabel labelPrice[] = new JLabel[10];
	private JLabel labelMinor[] = new JLabel[10];
	private JLabel labelPlus[] = new JLabel[10];
	private JTextField text[] = new JTextField[10];
	private JButton buttonBuy[] = new JButton[10];
	private Box box0 = Box.createVerticalBox();
	private Box box1 = Box.createVerticalBox();
	private Box boxH = Box.createVerticalBox();
	private JPanel moneyPanel = new JPanel();
	private JLabel moneyLabel = new JLabel("");
	protected XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	
	public ShopView(String s) {
		super(s);
		this.addWindowListener(new CloseHandler());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		
		for (int i = 0; i < 10; i++) {
			panel[i] = new JPanel();
			labelItem[i] = new JLabel();
			labelMinor[i] = new JLabel();
			labelPrice[i] = new JLabel();
			labelPlus[i] = new JLabel();
			text[i] = new JTextField();
			buttonBuy[i] = new JButton();
			labelItem[i].setPreferredSize(new Dimension(100, 22));
			labelPrice[i].setPreferredSize(new Dimension(55, 22));
			labelMinor[i].setPreferredSize(new Dimension(10, 20));
			labelPlus[i].setPreferredSize(new Dimension(15, 20));
			text[i].setPreferredSize(new Dimension(28, 20));
			buttonBuy[i].setPreferredSize(new Dimension(60, 22));
			buttonBuy[i].setText("Buy");
			buttonBuy[i].addActionListener(this);
			panel[i].add(labelItem[i]);
			panel[i].add(labelPrice[i]);
			panel[i].add(labelMinor[i]);
			labelMinor[i].setText("-");
			minorQuantity(labelMinor[i], i);
			panel[i].add(text[i]);
			if (i <= 4) {
				text[i].setText("1");
			} else {
				text[i].setText("10");
			}
			text[i].setHorizontalAlignment(JTextField.CENTER);
			text[i].setEnabled(false);
			panel[i].add(labelPlus[i]);
			labelPlus[i].setText("+");
			plusQuantity(labelPlus[i], i);
			panel[i].add(buttonBuy[i]);
			box0.add(panel[i]);
		}
		labelItem[0].setText(LegendConstant.Medal1);
		labelItem[1].setText(LegendConstant.Medal2);
		labelItem[2].setText(LegendConstant.Medal3);
		labelItem[3].setText(LegendConstant.Medal4);
		labelItem[4].setText(LegendConstant.Medal5);
		labelItem[5].setText(LegendConstant.Ore1);
		labelItem[6].setText(LegendConstant.Ore2);
		labelItem[7].setText(LegendConstant.Charm1);
		labelItem[8].setText(LegendConstant.Charm2);
		labelItem[9].setText(LegendConstant.Charm3);
		labelPrice[0].setText("100" + LegendConstant.CurrencyUnit);
		labelPrice[1].setText("1000" + LegendConstant.CurrencyUnit);
		labelPrice[2].setText("30" + LegendConstant.CurrencyUnit);
		labelPrice[3].setText("200" + LegendConstant.CurrencyUnit);
		labelPrice[4].setText("1000" + LegendConstant.CurrencyUnit);
		labelPrice[5].setText("10" + LegendConstant.CurrencyUnit);
		labelPrice[6].setText("20" + LegendConstant.CurrencyUnit);
		labelPrice[7].setText("100" + LegendConstant.CurrencyUnit);
		labelPrice[8].setText("200" + LegendConstant.CurrencyUnit);
		labelPrice[9].setText("400" + LegendConstant.CurrencyUnit);
		
		moneyPanel.setPreferredSize(new Dimension(290, 28));
		moneyLabel.setPreferredSize(new Dimension(284, 22));
		moneyLabel.setText(LegendConstant.RemainingGold + xmlParser.getNodeByName("money").getTextContent());
		moneyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		moneyPanel.add(moneyLabel);
		box1.add(moneyPanel);
		
		boxH.add(box0);
		boxH.add(box1);
		add(boxH);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Legend Shop");
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ShopView("");
	}

	public void actionPerformed(ActionEvent actionevent) {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		int serial = -1;
		long price = 0;
		long money = Integer.parseInt(xmlParser.getNodeByName("money").getTextContent());
		WindowStore.shopViewTL.set(this);
		freezeWindow();
		for (int i = 0; i < 10; i++) {
			if (actionevent.getSource() == buttonBuy[i]) {
				serial = i;
				break;
			}
		}
		int count = Integer.parseInt(text[serial].getText());
		if (count > 0) {
			price = Integer.parseInt(labelPrice[serial].getText().split(LegendConstant.CurrencyUnit)[0]);
			if (money > price * count * 10000) {
				money = money - price * count * 10000;
				xmlParser.getNodeByName("money").setTextContent("" + money);
				moneyLabel.setText(LegendConstant.RemainingGold + money);
				if (serial <= 4) {
					int code = serial + 2;
					for (int i = 0; i < 99; i++) {
						if (xmlParser.getNodeValues("item").get(i).isEmpty()) {
							Legend legend = new Legend();
							LegendItem legendItem = new LegendItem(legend.getItemByCode("x" + code));
							xmlParser.getNodeByName("item", i).setTextContent(legendItem.printItem());
							break;
						}
					}
				} else {
					int itemCount = 0;
					switch (serial) {
					case 5:
						itemCount = Integer.parseInt(xmlParser.getNodeByName("ore1").getTextContent()) + count;
						xmlParser.getNodeByName("ore1").setTextContent("" + itemCount);
						break;
					case 6:
						itemCount = Integer.parseInt(xmlParser.getNodeByName("ore2").getTextContent()) + count;
						xmlParser.getNodeByName("ore2").setTextContent("" + itemCount);
						break;
					case 7:
						itemCount = Integer.parseInt(xmlParser.getNodeByName("charm1").getTextContent()) + count;
						xmlParser.getNodeByName("charm1").setTextContent("" + itemCount);
						break;
					case 8:
						itemCount = Integer.parseInt(xmlParser.getNodeByName("charm2").getTextContent()) + count;
						xmlParser.getNodeByName("charm2").setTextContent("" + itemCount);
						break;
					case 9:
						itemCount = Integer.parseInt(xmlParser.getNodeByName("charm3").getTextContent()) + count;
						xmlParser.getNodeByName("charm3").setTextContent("" + itemCount);
						break;
					}
				}
				xmlParser.save();
				new WarningView(this, "Purchase Success!", 2);
			} else {
				new WarningView(this, "Not Enough Gold!", 2);
			}
		} else {
			new WarningView(this, "Item count can not be 0!", 2);
		}
	}
	
	private void minorQuantity(final JLabel label, final int count) {
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int quantity = Integer.parseInt(text[count].getText());
				if (count <= 4) {
					if (quantity == 1) {
						text[count].setText("0");
					}
				} else {
					if (quantity >= 1) {
						quantity = quantity - 10;
						text[count].setText("" + quantity);
					}
				}
			}
		});
	}
	
	private void plusQuantity(final JLabel label, final int count) {
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int quantity = Integer.parseInt(text[count].getText());
				if (count <= 4) {
					if (quantity == 0) {
						text[count].setText("1");
					}
				} else {
					if (quantity <= 90) {
						quantity = quantity + 10;
						text[count].setText("" + quantity);
					}
				}
			}
		});
	}
	
	public void freezeWindow() {
		WindowStore.shopViewTL.set(this);
		setEnabled(false);
	}
	
	private static class CloseHandler extends WindowAdapter {
		public void windowClosing(final WindowEvent event) {
			LegendView legendView = WindowStore.legendViewTL.get();
			legendView.setEnabled(true);
			legendView.requestFocus();
		}
	}

}
