package legend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import base.WindowStore;
import controls.MyButton;
import controls.MyButtonGroup;
import controls.MyLabel;
import controls.MyRadioButton;
import utility.calc.Algorithm;
import utility.file.XmlParser;

public class WeaponUpgradeView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 10L;
	private JPanel weaponPanel = new JPanel();
	private JPanel typePanel = new JPanel();
	private JPanel orePanel = new JPanel();
	private JPanel charmPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private MyLabel label[] = new MyLabel[5];
	private MyRadioButton radioButton[] = new MyRadioButton[12];
	private MyButtonGroup buttonGroup1 = new MyButtonGroup();
	private MyButtonGroup buttonGroup2 = new MyButtonGroup();
	private MyButtonGroup buttonGroup3 = new MyButtonGroup();
	private MyButton button = new MyButton("Upgrade");
	private Box box0 = Box.createVerticalBox();
	XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	private String weaponName;
	private String weaponProperty;
	private String upgradeCount;
	private int weaponLuck = 0;
	
	public WeaponUpgradeView(String s) {
		super(s);
		WindowStore.weaponUpgradeViewTL.set(this);
		this.addWindowListener(new CloseHandler());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		
		label[0] = new MyLabel(weaponName + upgradeCount, 80);
		label[1] = new MyLabel(weaponProperty, 500);
		label[0].setHorizontalAlignment(SwingConstants.LEFT);
		label[1].setHorizontalAlignment(SwingConstants.LEFT);
		weaponName = xmlParser.getNodeByName("weapon").getTextContent().split("~")[1].split("  ")[0];
		upgradeCount = xmlParser.getNodeByName("upgradeCount").getTextContent();
		weaponProperty = xmlParser.getNodeByName("weapon").getTextContent().substring(xmlParser.getNodeByName("weapon").getTextContent().indexOf("  "));
		initRadioButton();
		if (!xmlParser.getNodeByName("weapon").getTextContent().contains("~")) {
			return;
		}
		weaponPanel.add(label[0]);
		weaponPanel.add(label[1]);
		label[2] = new MyLabel(LegendConstant.UpgradeType, 90);
		for (int i = 0; i < 5; i++) {
			radioButton[i].setPreferredSize(new Dimension(95, 22));
		}
		typePanel.add(label[2]);
		typePanel.add(radioButton[0]);
		typePanel.add(radioButton[1]);
		typePanel.add(radioButton[2]);
		typePanel.add(radioButton[3]);
		typePanel.add(radioButton[4]);
		label[3] = new MyLabel(LegendConstant.SelectOre, 90);
		for (int i = 5; i < 9; i++) {
			radioButton[i].setPreferredSize(new Dimension(120, 22));
		}
		charmPanel.add(label[3]);
		charmPanel.add(radioButton[5]);
		charmPanel.add(radioButton[6]);
		charmPanel.add(radioButton[7]);
		charmPanel.add(radioButton[8]);
		label[4] = new MyLabel(LegendConstant.SelectCharm, 90);
		for (int i = 9; i < 12; i++) {
			radioButton[i].setPreferredSize(new Dimension(162, 22));
		}
		orePanel.add(label[4]);
		orePanel.add(radioButton[9]);
		orePanel.add(radioButton[10]);
		orePanel.add(radioButton[11]);
		button.addActionListener(this);
		buttonPanel.add(button, BorderLayout.CENTER);
		box0.add(weaponPanel);
		box0.add(typePanel);
		box0.add(charmPanel);
		box0.add(orePanel);
		box0.add(buttonPanel);
		add(box0);
		
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Weapon Upgrade");
		setVisible(true);
		
		for (int i = 0; i < 3; i++) {
			radioButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg) {
					refreshRadioButton(false);
				}
			});
		}
		radioButton[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				for (int i = 5; i < 12; i++) {
					radioButton[i].setEnabled(false);
				}
			}
		});
		radioButton[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				for (int i = 5; i < 12; i++) {
					radioButton[i].setEnabled(false);
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new WeaponUpgradeView("");
	}

	public void actionPerformed(ActionEvent actionevent) {
		int successFactor = 0;
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		upgradeCount = xmlParser.getNodeByName("upgradeCount").getTextContent();
		int bless = Integer.parseInt(xmlParser.getNodeByName("bless").getTextContent());
		int superOil = Integer.parseInt(xmlParser.getNodeByName("superOil").getTextContent());
		int charm1 = Integer.parseInt(xmlParser.getNodeByName("charm1").getTextContent());
		int charm2 = Integer.parseInt(xmlParser.getNodeByName("charm2").getTextContent());
		int charm3 = Integer.parseInt(xmlParser.getNodeByName("charm3").getTextContent());
		int ore1 = Integer.parseInt(xmlParser.getNodeByName("ore1").getTextContent());
		int ore2 = Integer.parseInt(xmlParser.getNodeByName("ore2").getTextContent());
		long gold = Long.parseLong(xmlParser.getNodeByName("money").getTextContent());
		if (actionevent.getSource() == button) {
			if (radioButton[3].isSelected()) {
				if (bless > 0) {
					if (xmlParser.getNodeByName("weapon").getTextContent().contains(LegendConstant.Luck)) {
						String str = LegendConstant.Luck;
						weaponLuck = Integer.parseInt(xmlParser.getNodeByName("weapon").getTextContent().split(str)[1].substring(2, 3));
					} else {
						weaponLuck = 0;
					}
					blessWeapon(weaponLuck);
					bless = bless - 1;
					xmlParser.getNodeByName("bless").setTextContent(String.valueOf(bless));
					xmlParser.save();
					refreshRadioButton(true);
				} else {
					new WarningView(this, "Material not enough!", 3);
				}
				return;
			} else if (radioButton[4].isSelected()) {
				if (superOil > 0) {
					blessWeaponSuper();
					superOil = superOil - 1;
					xmlParser.getNodeByName("superOil").setTextContent(String.valueOf(superOil));
					xmlParser.save();
					refreshRadioButton(true);
				} else {
					new WarningView(this, "Material not enough!", 3);
				}
				return;
			}
			if (gold < 10000) {
				new WarningView(this, "Not enough gold!", 3);
				return;
			}
			if (!upgradeCount.equals("7") && !upgradeCount.equals("N/A")) {
				switch (upgradeCount) {
				case "0":
					successFactor = successFactor + 95;
					break;
				case "1":
					successFactor = successFactor + 85;
					break;
				case "2":
					successFactor = successFactor + 75;
					break;
				case "3":
					successFactor = successFactor + 65;
					break;
				case "4":
					successFactor = successFactor + 55;
					break;
				case "5":
					successFactor = successFactor + 45;
					break;
				case "6":
					successFactor = successFactor + 35;
					break;
				}
			} else {
				new WarningView(this, "Your weapon cannot be enhanced!", 3);
				return;
			}
			if (radioButton[6].isSelected()) {
				if (charm1 >= 1) {
					successFactor = successFactor + 5;
					charm1 = charm1 - 1;
					gold = Long.parseLong(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("charm1").setTextContent(String.valueOf(charm1));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			} else if (radioButton[7].isSelected()) {
				if (charm2 >= 1) {
					successFactor = successFactor + 10;
					charm2 = charm2 - 1;
					gold = Long.parseLong(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("charm2").setTextContent(String.valueOf(charm2));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			} else if (radioButton[8].isSelected()) {
				if (charm3 >= 1) {
					successFactor = successFactor + 15;
					charm3 = charm3 - 1;
					gold = Long.parseLong(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("charm3").setTextContent(String.valueOf(charm3));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			}
			if (radioButton[10].isSelected()) {
				if (ore1 >= 2) {
					ore1 = ore1 - 2;
					gold = Long.parseLong(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("ore1").setTextContent(String.valueOf(ore1));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			} else if (radioButton[11].isSelected()) {
				if (ore2 >= 2) {
					successFactor = successFactor + 5;
					ore2 = ore2 - 2;
					gold = Long.parseLong(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("ore2").setTextContent(String.valueOf(ore2));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			} else {
				successFactor = successFactor - 5;
			}
			if (Algorithm.getDraw(successFactor)) {
				upgradeWeapon();
			} else {
				String weaponProperty = xmlParser.getNodeByName("weapon").getTextContent();
				String str1 = weaponProperty.split("~")[1].split("  ")[0];
				String str2 = weaponProperty.split("~")[1].split("  ")[0] + LegendConstant.Destroyed;
				weaponProperty = upgradeCount.equals("0") ? weaponProperty.replaceFirst(str1, str2) : weaponProperty.replaceFirst("[+]" + upgradeCount, LegendConstant.Destroyed);
				xmlParser.getNodeByName("upgradeCount").setTextContent("N/A");
				xmlParser.getNodeByName("weapon").setTextContent(weaponProperty);
				xmlParser.save();
				label[0].setText(weaponProperty.split("~")[1].split("  ")[0]);
				refreshRadioButton(false);
				new WarningView(this, "Weapon upgrade fail, you weapon is broken!", 3);
			}
		}
	}
	
	private void initRadioButton() {
		for (int i = 0; i < 5; i++) {
			radioButton[i] = new MyRadioButton("", 100);
		}
		for (int i = 5; i < 8; i++) {
			radioButton[i] = new MyRadioButton("", 162);
		}
		for (int i = 8; i < 12; i++) {
			radioButton[i] = new MyRadioButton("", 120);
		}
		radioButton[0].setText(LegendConstant.Attack);
		radioButton[1].setText(LegendConstant.DaoAttack);
		radioButton[2].setText(LegendConstant.MagicAttack);
		radioButton[3].setText(LegendConstant.BlessOil);
		radioButton[4].setText(LegendConstant.SuperOil);
		radioButton[5].setText(LegendConstant.NoCharm);
		radioButton[9].setText(LegendConstant.NoOre);
		label[0].setText(weaponName);
		label[1].setText(weaponProperty.substring(weaponProperty.indexOf("  ")));
		refreshRadioButton(false);
		buttonGroup1.add(radioButton[0]);
		buttonGroup1.add(radioButton[1]);
		buttonGroup1.add(radioButton[2]);
		buttonGroup1.add(radioButton[3]);
		buttonGroup1.add(radioButton[4]);
		buttonGroup2.add(radioButton[5]);
		buttonGroup2.add(radioButton[6]);
		buttonGroup2.add(radioButton[7]);
		buttonGroup2.add(radioButton[8]);
		buttonGroup3.add(radioButton[9]);
		buttonGroup3.add(radioButton[10]);
		buttonGroup3.add(radioButton[11]);
		radioButton[0].setSelected(true);
		radioButton[5].setSelected(true);
		radioButton[9].setSelected(true);
	}
	
	private void refreshRadioButton(boolean disableOthers) {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		radioButton[3].setText(LegendConstant.BlessOil + "(" + xmlParser.getNodeByName("bless").getTextContent() + ")");
		radioButton[4].setText(LegendConstant.SuperOil + "(" + xmlParser.getNodeByName("superOil").getTextContent() + ")");
		radioButton[6].setText(LegendConstant.Charm1 + "(" + xmlParser.getNodeByName("charm1").getTextContent() + ")");
		radioButton[7].setText(LegendConstant.Charm2 + "(" + xmlParser.getNodeByName("charm2").getTextContent() + ")");
		radioButton[8].setText(LegendConstant.Charm3 + "(" + xmlParser.getNodeByName("charm3").getTextContent() + ")");
		radioButton[10].setText(LegendConstant.Ore1 + "(" + xmlParser.getNodeByName("ore1").getTextContent() + ")");
		radioButton[11].setText(LegendConstant.Ore2 + "(" + xmlParser.getNodeByName("ore2").getTextContent() + ")");
		for (int i = 5; i < 12; i++) {
			radioButton[i].setEnabled(true);
		}
		if (Integer.parseInt(xmlParser.getNodeByName("bless").getTextContent()) < 1)
			radioButton[3].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("superOil").getTextContent()) < 1)
			radioButton[4].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("charm1").getTextContent()) < 2)
			radioButton[6].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("charm2").getTextContent()) < 2)
			radioButton[7].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("charm3").getTextContent()) < 1)
			radioButton[8].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("ore1").getTextContent()) < 1)
			radioButton[10].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("ore2").getTextContent()) < 1)
			radioButton[11].setEnabled(false);
		if (disableOthers) {
			for (int i = 5; i < 12; i++) {
				radioButton[i].setEnabled(false);
			}
		}
	}
	
	private void upgradeWeapon() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String weaponProperty = xmlParser.getNodeByName("weapon").getTextContent();
		if (!"0".equals(upgradeCount)) {
			int count = Integer.parseInt(upgradeCount) + 1;
			weaponProperty = weaponProperty.replaceFirst("[+]" + upgradeCount, "+" + count);
		} else {
			String str1 = weaponProperty.split("~")[1].split("  ")[0];
			String str2 = weaponProperty.split("~")[1].split("  ")[0] + "+1";
			weaponProperty = weaponProperty.replaceFirst(str1, str2);
		}
		int p = (Algorithm.getDraw(10)) ? 2 : 1;
		if (upgradeCount.equals("6")) {
			p = 2;
		}
		String minAttack = "";
		String maxAttack = "";
		String newAttack = "";
		switch (buttonGroup1.getSelectedButtonText()) {
		case LegendConstant.Attack:
			minAttack = weaponProperty.split(LegendConstant.Attack + ": ")[1].split("-")[0];
			maxAttack = weaponProperty.split(LegendConstant.Attack + ": ")[1].split("-")[1].split("  ")[0];
			newAttack = String.valueOf(Integer.parseInt(maxAttack) + p);
			weaponProperty = weaponProperty.replace(LegendConstant.Attack + ": " + minAttack + "-" + maxAttack, LegendConstant.Attack + ": " + minAttack + "-" + newAttack);
			xmlParser.getNodeByName("weapon").setTextContent(weaponProperty);
			break;
		case LegendConstant.DaoAttack:
			if (weaponProperty.contains(LegendConstant.DaoAttack)) {
				minAttack = weaponProperty.split(LegendConstant.DaoAttack + ": ")[1].split("-")[0];
				maxAttack = weaponProperty.split(LegendConstant.DaoAttack + ": ")[1].split("-")[1].split("  ")[0];
				newAttack = String.valueOf(Integer.parseInt(maxAttack) + p);
				weaponProperty = weaponProperty.replace(LegendConstant.DaoAttack + ": " + minAttack + "-" + maxAttack, LegendConstant.DaoAttack + ": " + minAttack + "-" + newAttack);
			} else {
				newAttack = LegendConstant.DaoAttack + ": 0-" + p + "  " + LegendConstant.Weight;
				weaponProperty = weaponProperty.replace(LegendConstant.Weight, newAttack);
			}
			xmlParser.getNodeByName("weapon").setTextContent(weaponProperty);
			break;
		case LegendConstant.MagicAttack:
			if (weaponProperty.contains(LegendConstant.MagicAttack)) {
				minAttack = weaponProperty.split(LegendConstant.MagicAttack + ": ")[1].split("-")[0];
				maxAttack = weaponProperty.split(LegendConstant.MagicAttack + ": ")[1].split("-")[1].split("  ")[0];
				newAttack = String.valueOf(Integer.parseInt(maxAttack) + p);
				weaponProperty = weaponProperty.replace(LegendConstant.MagicAttack + ": " + minAttack + "-" + maxAttack, LegendConstant.MagicAttack + ": " + minAttack + "-" + newAttack);
			} else {
				newAttack = LegendConstant.MagicAttack + ": 0-" + p + "  " + LegendConstant.Weight;
				weaponProperty = weaponProperty.replace(LegendConstant.Weight, newAttack);
			}
			xmlParser.getNodeByName("weapon").setTextContent(weaponProperty);
			break;
		}
		upgradeCount = String.valueOf(Integer.parseInt(upgradeCount) + 1);
		xmlParser.getNodeByName("upgradeCount").setTextContent(upgradeCount);
		xmlParser.save();
		label[0].setText(weaponProperty.split("~")[1].split("  ")[0]);
		label[1].setText(weaponProperty.substring(weaponProperty.indexOf("  ")));
		refreshRadioButton(false);
		new WarningView(this, "Weapon Upgrade Success!", 3);
	}
	
	public void blessWeapon(int luck) {
		String weaponProperty = xmlParser.getNodeByName("weapon").getTextContent();
		String newLuckString = "";
		String oldLuckString = LegendConstant.Luck + " +" + luck;
		int goodChance = 100;
		int normalChance = 100;
		double chance = Algorithm.getRandomDouble(0, 100);
		switch (luck) {
		case 0:
			goodChance = 100;
			normalChance = 100;
			break;
		case 1:
			goodChance = 70;
			normalChance = 95;
			break;
		case 2:
			goodChance = 40;
			normalChance = 94;
			break;
		case 3:
			goodChance = 10;
			normalChance = 93;
			break;
		case 4:
			goodChance = 9;
			normalChance = 92;
			break;
		case 5:
			goodChance = 8;
			normalChance = 91;
			break;
		case 6:
			goodChance = 7;
			normalChance = 90;
			break;
		default:
			goodChance = 0;
			normalChance = 100;
			break;
		}
		if (luck == 0) {
			newLuckString = LegendConstant.Luck + " +1  " + LegendConstant.Weight;
			weaponProperty = weaponProperty.replace(LegendConstant.Weight, newLuckString);
			System.out.println("Your weapon is blessed!");
		} else {
			if (chance < goodChance) {
				luck = luck + 1;
				newLuckString = LegendConstant.Luck + " +" + luck;
				weaponProperty = weaponProperty.replace(oldLuckString, newLuckString);
				System.out.println("Your weapon is blessed!");
			} else if (chance < normalChance) {
				System.out.println("Your weapon is not blessed!");
			} else {
				luck = luck - 1;
				if (luck > 0) {
					newLuckString = LegendConstant.Luck + " +" + luck;
					weaponProperty = weaponProperty.replace(oldLuckString, newLuckString);
				} else {
					weaponProperty = weaponProperty.replace(oldLuckString + "  ", "");
				}
				System.out.println("Your weapon is cursed!");
			}
		}
		xmlParser.getNodeByName("weapon").setTextContent(weaponProperty);
		xmlParser.save();
		label[0].setText(weaponProperty.split("~")[1].split("  ")[0]);
		label[1].setText(weaponProperty.substring(weaponProperty.indexOf("  ")));
		refreshRadioButton(false);
	}
	
	/**
	 * This function is to be implemented in future.
	 */
	public void blessWeaponSuper() {
		String weaponProperty = xmlParser.getNodeByName("weapon").getTextContent();
		String str = LegendConstant.Luck;
		String newLuckString;
		String oldLuckString;
		if (weaponProperty.contains(str)) {
			weaponLuck = Integer.parseInt(xmlParser.getNodeByName("weapon").getTextContent().split(str)[1].substring(2, 3));
			if (weaponLuck < 7) {
				oldLuckString = LegendConstant.Luck + " +" + weaponLuck;
				weaponLuck = weaponLuck + 1;
				newLuckString = LegendConstant.Luck + " +" + weaponLuck;
				weaponProperty = weaponProperty.replace(oldLuckString, newLuckString);
				System.out.println("Your weapon is blessed!");
			} else {
				System.out.println("Your weapon is not blessed!");
			}
		} else {
			newLuckString = LegendConstant.Luck + " +1  " + LegendConstant.Weight;
			weaponProperty = weaponProperty.replace(LegendConstant.Weight, newLuckString);
		}
		xmlParser.getNodeByName("weapon").setTextContent(weaponProperty);
		xmlParser.save();
		label[0].setText(weaponProperty.split("~")[1].split("  ")[0]);
		label[1].setText(weaponProperty.substring(weaponProperty.indexOf("  ")));
		refreshRadioButton(false);
	}
	
	private static class CloseHandler extends WindowAdapter {
		public void windowClosing(final WindowEvent event) {
			LegendView legendView = WindowStore.legendViewTL.get();
			legendView.setEnabled(true);
			legendView.requestFocus();
			legendView.launchCharProperty();
		}
	}

}
