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
	private MyRadioButton radioButton[] = new MyRadioButton[10];
	private MyButtonGroup buttonGroup1 = new MyButtonGroup();
	private MyButtonGroup buttonGroup2 = new MyButtonGroup();
	private MyButtonGroup buttonGroup3 = new MyButtonGroup();
	private MyButton button = new MyButton("Upgrade");
	private Box box0 = Box.createVerticalBox();
	XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	private String weaponName;
	private String weaponProperty;
	private String upgradeCount;
	
	public WeaponUpgradeView(String s) {
		super(s);
		WindowStore.weaponUpgradeViewTL.set(this);
		this.addWindowListener(new CloseHandler());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		
		label[0] = new MyLabel(weaponName + upgradeCount, 90);
		label[1] = new MyLabel(weaponProperty, 480);
		label[0].setHorizontalAlignment(SwingConstants.LEFT);
		label[1].setHorizontalAlignment(SwingConstants.LEFT);
		weaponName = xmlParser.getNodeByName("weapon").getTextContent().split("~")[1].split("  ")[0] + "+";
		upgradeCount = xmlParser.getNodeByName("upgradeCount").getTextContent();
		weaponProperty = xmlParser.getNodeByName("weapon").getTextContent().substring(xmlParser.getNodeByName("weapon").getTextContent().indexOf("  "));
		initRadioButton();
		if (!xmlParser.getNodeByName("weapon").getTextContent().contains("~")) {
			return;
		}
		weaponPanel.add(label[0]);
		weaponPanel.add(label[1]);
		label[2] = new MyLabel(LegendConstant.UpgradeType, 90);
		typePanel.add(label[2]);
		typePanel.add(radioButton[0]);
		typePanel.add(radioButton[1]);
		typePanel.add(radioButton[2]);
		label[3] = new MyLabel(LegendConstant.SelectOre, 90);
		orePanel.add(label[3]);
		orePanel.add(radioButton[3]);
		orePanel.add(radioButton[4]);
		orePanel.add(radioButton[5]);
		label[4] = new MyLabel(LegendConstant.SelectCharm, 90);
		charmPanel.add(label[4]);
		charmPanel.add(radioButton[6]);
		charmPanel.add(radioButton[7]);
		charmPanel.add(radioButton[8]);
		charmPanel.add(radioButton[9]);
		button.addActionListener(this);
		buttonPanel.add(button, BorderLayout.CENTER);
		box0.add(weaponPanel);
		box0.add(typePanel);
		box0.add(orePanel);
		box0.add(charmPanel);
		box0.add(buttonPanel);
		add(box0);
		
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Weapon Upgrade");
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new WeaponUpgradeView("");
	}

	public void actionPerformed(ActionEvent actionevent) {
		int successFactor = 0;
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		upgradeCount = xmlParser.getNodeByName("upgradeCount").getTextContent();
		int ore1 = Integer.parseInt(xmlParser.getNodeByName("ore1").getTextContent());
		int ore2 = Integer.parseInt(xmlParser.getNodeByName("ore2").getTextContent());
		int charm1 = Integer.parseInt(xmlParser.getNodeByName("charm1").getTextContent());
		int charm2 = Integer.parseInt(xmlParser.getNodeByName("charm2").getTextContent());
		int charm3 = Integer.parseInt(xmlParser.getNodeByName("charm3").getTextContent());
		int gold = Integer.parseInt(xmlParser.getNodeByName("money").getTextContent());
		if (actionevent.getSource() == button) {
			if (gold < 10000) {
				new WarningView(this, "Not enough gold!", 3);
				return;
			}
			if (!upgradeCount.equals("7")) {
				switch (upgradeCount) {
				case "0":
					successFactor = successFactor + 70;
					break;
				case "1":
					successFactor = successFactor + 60;
					break;
				case "2":
					successFactor = successFactor + 50;
					break;
				case "3":
					successFactor = successFactor + 40;
					break;
				case "4":
					successFactor = successFactor + 30;
					break;
				case "5":
					successFactor = successFactor + 20;
					break;
				case "6":
					successFactor = successFactor + 10;
					break;
				}
			} else {
				new WarningView(this, "Your weapon met the maximun limitation from upgrade!", 3);
				return;
			}
			if (radioButton[4].isSelected()) {
				if (ore1 >= 2) {
					ore1 = ore1 - 2;
					gold = Integer.parseInt(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("ore1").setTextContent(String.valueOf(ore1));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			} else if (radioButton[5].isSelected()) {
				if (ore2 >= 2) {
					successFactor = successFactor + 5;
					ore2 = ore2 - 2;
					gold = Integer.parseInt(xmlParser.getNodeByName("money").getTextContent()) - 10000;
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
			if (radioButton[7].isSelected()) {
				if (charm1 >= 1) {
					successFactor = successFactor + 5;
					charm1 = charm1 - 1;
					gold = Integer.parseInt(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("charm1").setTextContent(String.valueOf(charm1));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			} else if (radioButton[8].isSelected()) {
				if (charm2 >= 1) {
					charm2 = charm2 - 1;
					gold = Integer.parseInt(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("charm2").setTextContent(String.valueOf(charm2));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			} else if (radioButton[9].isSelected()) {
				if (charm3 >= 1) {
					charm3 = charm3 - 1;
					gold = Integer.parseInt(xmlParser.getNodeByName("money").getTextContent()) - 10000;
					xmlParser.getNodeByName("money").setTextContent(String.valueOf(gold));
					xmlParser.getNodeByName("charm3").setTextContent(String.valueOf(charm3));
					xmlParser.save();
				} else {
					new WarningView(this, "Material not enough!", 3);
					return;
				}
			}
			if (Algorithm.getDraw(successFactor)) {
				upgradeWeapon();
			} else {
				xmlParser.getNodeByName("weapon").setTextContent("");
				xmlParser.getNodeByName("upgradeCount").setTextContent("0");
				xmlParser.save();
				label[0].setText("");
				label[1].setText("");
				button.removeActionListener(this);
				LegendView legendView = WindowStore.legendViewTL.get();
				legendView.lbEquipment[1].setText("");
				refreshRadioButton();
				new WarningView(this, "Weapon upgrade fail, you weapon is broken!", 3);
			}
		}
	}
	
	private void initRadioButton() {
		for (int i = 0; i < 3; i++) {
			radioButton[i] = new MyRadioButton("", 162);
		}
		for (int i = 3; i < 6; i++) {
			radioButton[i] = new MyRadioButton("", 162);
		}
		for (int i = 6; i < 10; i++) {
			radioButton[i] = new MyRadioButton("", 120);
		}
		radioButton[0].setText(LegendConstant.Attack);
		radioButton[1].setText(LegendConstant.DaoAttack);
		radioButton[2].setText(LegendConstant.MagicAttack);
		radioButton[3].setText(LegendConstant.NoOre);
		radioButton[6].setText(LegendConstant.NoCharm);
		label[0].setText(weaponName + upgradeCount);
		label[1].setText(weaponProperty.substring(weaponProperty.indexOf("  ")));
		refreshRadioButton();
		buttonGroup1.add(radioButton[0]);
		buttonGroup1.add(radioButton[1]);
		buttonGroup1.add(radioButton[2]);
		buttonGroup2.add(radioButton[3]);
		buttonGroup2.add(radioButton[4]);
		buttonGroup2.add(radioButton[5]);
		buttonGroup3.add(radioButton[6]);
		buttonGroup3.add(radioButton[7]);
		buttonGroup3.add(radioButton[8]);
		buttonGroup3.add(radioButton[9]);
		radioButton[0].setSelected(true);
		radioButton[3].setSelected(true);
		radioButton[6].setSelected(true);
	}
	
	private void refreshRadioButton() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		radioButton[4].setText(LegendConstant.Ore1 + "(" + xmlParser.getNodeByName("ore1").getTextContent() + ")");
		radioButton[5].setText(LegendConstant.Ore2 + "(" + xmlParser.getNodeByName("ore2").getTextContent() + ")");
		radioButton[7].setText(LegendConstant.Charm1 + "(" + xmlParser.getNodeByName("charm1").getTextContent() + ")");
		radioButton[8].setText(LegendConstant.Charm2 + "(" + xmlParser.getNodeByName("charm2").getTextContent() + ")");
		radioButton[9].setText(LegendConstant.Charm3 + "(" + xmlParser.getNodeByName("charm3").getTextContent() + ")");
		if (Integer.parseInt(xmlParser.getNodeByName("ore1").getTextContent()) < 2)
			radioButton[4].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("ore2").getTextContent()) < 2)
			radioButton[5].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("charm1").getTextContent()) < 1)
			radioButton[7].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("charm2").getTextContent()) < 1)
			radioButton[8].setEnabled(false);
		if (Integer.parseInt(xmlParser.getNodeByName("charm3").getTextContent()) < 1)
			radioButton[9].setEnabled(false);
	}
	
	private void upgradeWeapon() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String weaponProperty = xmlParser.getNodeByName("weapon").getTextContent();
		int p = (Algorithm.getDraw(10)) ? 2 : 1;
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
		label[0].setText(weaponName + upgradeCount);
		label[1].setText(weaponProperty.substring(weaponProperty.indexOf("  ")));
		refreshRadioButton();
		new WarningView(this, "Weapon Upgrade Success!", 3);
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
