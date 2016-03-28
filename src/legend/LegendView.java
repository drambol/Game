package legend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import base.WindowStore;
import clazz.Legend;
import controls.MyButton;
import controls.MyButtonGroup;
import controls.MyLabel;
import controls.MyRadioButton;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utility.file.XmlParser;

public class LegendView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private MyLabel lbEquipment[] = new MyLabel[24];
	private JPanel jpEquipment[] = new JPanel[6];
	private MyLabel label[] = new MyLabel[10];
	private JPanel panel[] = new JPanel[11];
	private JPanel radioPanel[] = new JPanel[5];
	private MyButton buttonKill = new MyButton("Kill Monster");
	private MyButton buttonWH = new MyButton("Warehouse");
	private MyButton buttonSave = new MyButton("Save Char");
	private MyButton buttonEquip[] = new MyButton[10];
	private MyButton buttonStore[] = new MyButton[10];
	private Box box0 = Box.createVerticalBox();
	private Box box1 = Box.createVerticalBox();
	private Box boxH = Box.createVerticalBox();
	private JPanel pHeader = new JPanel();
	private JLabel labelCharInfo = new JLabel();
	private JLabel labelDelete = new JLabel();
	private JTextField tText1 = new JTextField();
	private JTextField tText2 = new JTextField();
	private JPanel pText = new JPanel();
	private MyRadioButton radioButton[] = new MyRadioButton[20];
	private MyButtonGroup buttonGroup = new MyButtonGroup();
	private String tempWarehouse = "";
	public int tempPosition = -1;
	public boolean fromMonster = true;
	public boolean confirmFlag = false;
	protected XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	private String charName = xmlParser.getNodeByName("name").getTextContent();
	Legend legend = new Legend();
	PropertyView propertyView;
	
	public LegendView(String s) {
		super(s);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		buttonKill.addActionListener(this);
		buttonWH.addActionListener(this);
		buttonSave.addActionListener(this);
		for (int i = 0; i < 12; i++) {
			lbEquipment[i*2] = new MyLabel("");
			lbEquipment[i*2+1] = new MyLabel("");
			lbEquipment[i*2].setPreferredSize(new Dimension(80, 22));
			lbEquipment[i*2+1].setPreferredSize(new Dimension(220, 22));
		}
		for (int i = 0; i < 6; i++) {
			jpEquipment[i] = new JPanel();
			jpEquipment[i].setPreferredSize(new Dimension(650, 25));
			jpEquipment[i].add(lbEquipment[i*4]);
			jpEquipment[i].add(lbEquipment[i*4+1]);
			jpEquipment[i].add(lbEquipment[i*4+2]);
			jpEquipment[i].add(lbEquipment[i*4+3]);
		}
		for (int i = 0; i < 10; i++) {
			panel[i] = new JPanel();
			panel[i].setPreferredSize(new Dimension(650, 25));
			label[i] = new MyLabel("");
			label[i].setPreferredSize(new Dimension(450, 22));
			panel[i].add(label[i], BorderLayout.WEST);
			buttonEquip[i] = new MyButton("", new Dimension(80, 20));
			buttonStore[i] = new MyButton("", new Dimension(80, 20));
			panel[i].add(buttonEquip[i]);
			panel[i].add(buttonStore[i], BorderLayout.EAST);
		}
		tText1.setPreferredSize(new Dimension(85, 25));
		tText1.setHorizontalAlignment(JTextField.CENTER);
		tText1.setEditable(false);
		tText2.setPreferredSize(new Dimension(530, 25));
		tText2.setEditable(false);
		pText.setPreferredSize(new Dimension(650, 30));
		pText.add(tText1, BorderLayout.AFTER_LAST_LINE);
		pText.add(tText2, BorderLayout.AFTER_LAST_LINE);
		launchCharProperty();
		launchCharInfo();
		panel[10] = new JPanel();
		panel[10].add(buttonKill);
		panel[10].add(buttonWH);
		panel[10].add(buttonSave);
		pHeader.add(labelCharInfo, BorderLayout.CENTER);
		pHeader.add(labelDelete, BorderLayout.CENTER);
		box0.add(pHeader, BorderLayout.AFTER_LAST_LINE);
		for(int i = 0; i < 6; i++) {
			box0.add(jpEquipment[i], BorderLayout.AFTER_LAST_LINE);
		}
		initRadioButton();
		for(int i = 0; i < 4; i++) {
			radioPanel[i] = new JPanel();
			radioPanel[i].add(radioButton[i*5]);
			radioPanel[i].add(radioButton[i*5+1]);
			radioPanel[i].add(radioButton[i*5+2]);
			radioPanel[i].add(radioButton[i*5+3]);
			radioPanel[i].add(radioButton[i*5+4]);
		}
		box0.add(pText, BorderLayout.AFTER_LAST_LINE);
		for(int i = 0; i < 10; i++) {
			box0.add(panel[i], BorderLayout.AFTER_LAST_LINE);
		}
		for(int i = 0; i < 4; i++) {
			box1.add(radioPanel[i]);
		}
		box1.add(panel[10]);
		boxH.add(box0);
		boxH.add(box1);
		add(boxH);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Legend");
		setVisible(true);
		setResizable(false);
		propertyView = new PropertyView("", false);
		this.addWindowListener(new CloseHandler(this));
	}
	
	public static void main(String[] args) {
		XmlParser sourceXml = new XmlParser("runSuite\\LegendHero.xml");
		if (sourceXml.getNodeByName("name").getTextContent().isEmpty()) {
			new RegisterView("");
		} else {
			new LegendView("");
		}
	}

	public void actionPerformed(ActionEvent actionevent) {
		int equip = -1;
		int store = -1;
		if (actionevent.getSource() == buttonKill) {
			LegendFunction lengendFunction = new LegendFunction(buttonGroup.getSelectedButtonText());
			if (lengendFunction.eligibleChallenge(propertyView)) {
				for(int i = 0; i < 10; i++) {
					label[i].setText("");
					buttonEquip[i].setText("");
					buttonStore[i].setText("");
					buttonEquip[i].removeActionListener(this);
					buttonStore[i].removeActionListener(this);
				}
				lengendFunction.addExp();
				if (lengendFunction.levelUp) {
					reactForLevelUp();
				}
				String[] str = legend.getItemFromMonster(buttonGroup.getSelectedButtonText());
				for(int i = 0; i < 10; i++) {
					if (str[i] != null && !str[i].substring(0, 1).equals("*")) {
						label[i].setText(str[i].split("~")[1]);
						buttonEquip[i].setText("Equip It");
						buttonStore[i].setText("Store It");
						buttonEquip[i].addActionListener(this);
						buttonStore[i].addActionListener(this);
					} else if (str[i] != null && str[i].substring(0, 1).equals("*")) {
						label[i].setText(str[i].substring(1));
					} else {
						label[i].setText("");
						buttonEquip[i].setText("");
						buttonStore[i].setText("");
						buttonEquip[i].removeActionListener(this);
						buttonStore[i].removeActionListener(this);
					}
				}
			} else {
				WindowStore.legendViewTL.set(this);
				new WarningView(this, "Not eligible to kill this monster!", false);
			}
			return;
		}
		if (actionevent.getSource() == buttonWH) {
			freezeWindow();
			new WarehouseView("");
			return;
		}
		for (int i = 0; i < 10; i++) {
			if (actionevent.getSource() == buttonEquip[i]) {
				equip = i;
				break;
			}
		}
		if (equip >= 0) {
			tempPosition = equip;
			fromMonster = true;
			equipItem(legend.legendItems[equip]);
			return;
		}
		for (int i = 0; i < 10; i++) {
			if (actionevent.getSource() == buttonStore[i]) {
				store = i;
				break;
			}
		}
		if (store >= 0) {
			xmlParser = new XmlParser("runSuite\\LegendHero.xml");
			for (int i = 0; i < 99; i++) {
				if (xmlParser.getNodeValues("item").get(i).isEmpty()) {
					xmlParser.getNodeByName("item", i).setTextContent(legend.itemCode[store] + "~" + label[store].getText());
					xmlParser.save();
					break;
				}
			}
			legend.legendItems[store] = "";
			label[store].setText("");
			buttonEquip[store].setText("");
			buttonStore[store].setText("");
			buttonEquip[store].removeActionListener(this);
			buttonStore[store].removeActionListener(this);
			return;
		}
		if (actionevent.getSource() == buttonSave) {
//			String charName = xmlParser.getNodeByName("name").getTextContent();
			File targetFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\save\\" + charName + ".xml");
			if (targetFile.exists()) {
				targetFile.delete();
			}
			File sourceFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\LegendHero.xml");
			sourceFile.renameTo(targetFile);
			xmlParser.save();
			WindowStore.legendViewTL.set(this);
			new WarningView(this, "Your character is successfully saved!", false);
			return;
		}
	}
	
	public void reactFromDialog(boolean flag, String str, int i, String detail) {
		if (flag) {
			lbEquipment[i].setText(detail.split("~")[1].split(" ")[0]);
			tempWarehouse = xmlParser.getNodeByName("left" + str).getTextContent();
			xmlParser.getNodeByName("left" + str).setTextContent(detail);
		} else {
			lbEquipment[i+2].setText(detail.split("~")[1].split(" ")[0]);
			tempWarehouse = xmlParser.getNodeByName("right" + str).getTextContent();
			xmlParser.getNodeByName("right" + str).setTextContent(detail);
		}
		xmlParser.save();
		launchCharProperty();
		replaceItem(fromMonster);
	}
	
	public void reactFromWarehouse(String str) {
		fromMonster = false;
		equipItem(str);
		WarehouseView warehouseView = WindowStore.warehouseViewTL.get();
		warehouseView.requestFocus();
	}
	
	public void reactForLevelUp() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String charName = xmlParser.getNodeByName("name").getTextContent();
		String charLevel = xmlParser.getNodeByName("level").getTextContent();
		String charCareer = xmlParser.getNodeByName("career").getTextContent();
		String headerInfo = charName + " (" + charCareer + " Lv " + charLevel + ")";
		labelCharInfo.setText(headerInfo);
		propertyView.loadPorpertyFromXML();
	}
	
	public void reactFromConfirm(int source) {
		switch (source) {
		case 1:
			if (confirmFlag) {
				xmlParser = new XmlParser("runSuite\\LegendHero.xml");
				String deleteChar = xmlParser.getNodeByName("name").getTextContent();
				xmlParser.getNodeByName("name").setTextContent("");
				xmlParser.save();
				dispose();
				File file = new File(System.getProperty("user.dir") + "\\src\\runSuite\\save\\" + deleteChar + ".xml");
				file.delete();
				SelectCharView.main(null);
			} else {
				this.setEnabled(true);
				this.requestFocus();
			}
			break;
		case 2:
			if (confirmFlag) {
				xmlParser = new XmlParser("runSuite\\LegendHero.xml");
				String charName = xmlParser.getNodeByName("name").getTextContent();
				File targetFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\save\\" + charName + ".xml");
				if (targetFile.exists()) {
					targetFile.delete();
				}
				File sourceFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\LegendHero.xml");
				sourceFile.renameTo(targetFile);
				xmlParser.save();
				WindowStore.legendViewTL.set(this);
				new WarningView(this, "Your character is successfully saved!", true);
			} else {
				System.exit(0);
			}
			break;
		}
	}
	
	public void freezeWindow() {
		WindowStore.legendViewTL.set(this);
		setEnabled(false);
	}
	
	protected void launchCharProperty() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String weapon = xmlParser.getNodeByName("weapon").getTextContent();
		String armor = xmlParser.getNodeByName("armor").getTextContent();
		String helmet = xmlParser.getNodeByName("helmet").getTextContent();
		String amulet = xmlParser.getNodeByName("amulet").getTextContent();
		String medal = xmlParser.getNodeByName("medal").getTextContent();
		String leftBracelet = xmlParser.getNodeByName("leftBracelet").getTextContent();
		String rightBracelet = xmlParser.getNodeByName("rightBracelet").getTextContent();
		String leftRing = xmlParser.getNodeByName("leftRing").getTextContent();
		String rightRing = xmlParser.getNodeByName("rightRing").getTextContent();
		String belt = xmlParser.getNodeByName("belt").getTextContent();
		String boots = xmlParser.getNodeByName("boots").getTextContent();
		String gem = xmlParser.getNodeByName("gem").getTextContent();
		
		labelDelete.setForeground(Color.RED);
		labelDelete.setText("Delete");
		lbEquipment[0].setText(LegendConstant.Weapon);
		showDetail(lbEquipment[1], weapon);
		lbEquipment[2].setText(LegendConstant.Armor);
		showDetail(lbEquipment[3], armor);
		lbEquipment[4].setText(LegendConstant.Helmet);
		showDetail(lbEquipment[5], helmet);
		lbEquipment[6].setText(LegendConstant.Amulet);
		showDetail(lbEquipment[7], amulet);
		lbEquipment[8].setText(LegendConstant.Medal);
		showDetail(lbEquipment[9], medal);
		lbEquipment[10].setText(LegendConstant.LeftBracelet);
		showDetail(lbEquipment[11], leftBracelet);
		lbEquipment[12].setText(LegendConstant.RightBracelet);
		showDetail(lbEquipment[13], rightBracelet);
		lbEquipment[14].setText(LegendConstant.LeftRing);
		showDetail(lbEquipment[15], leftRing);
		lbEquipment[16].setText(LegendConstant.RightRing);
		showDetail(lbEquipment[17], rightRing);
		lbEquipment[18].setText(LegendConstant.Belt);
		showDetail(lbEquipment[19], belt);
		lbEquipment[20].setText(LegendConstant.Boots);
		showDetail(lbEquipment[21], boots);
		lbEquipment[22].setText(LegendConstant.Gem);
		showDetail(lbEquipment[23], gem);
	}
	
	private void launchCharInfo() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String charName = xmlParser.getNodeByName("name").getTextContent();
		String charLevel = xmlParser.getNodeByName("level").getTextContent();
		String charCareer = xmlParser.getNodeByName("career").getTextContent();
		String headerInfo = charName + " (" + charCareer + " Lv " + charLevel + ")";
		labelCharInfo.setText(headerInfo);
		addClickEvent(headerInfo);
	}
	
	private void showDetail(final MyLabel label, final String str) {
		if (!str.isEmpty()) {
			label.setText(str.split("~")[1].split("  ")[0]);
		}
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (str.length() > 0) {
					tText1.setText(str.split("~")[1].split("  ")[0]);
					tText2.setText(str.substring(str.indexOf("  ")));
				} else {
					tText1.setText("");
					tText2.setText("");
				}
			}
		});
	}
	
	private void addClickEvent(final String str) {
		labelCharInfo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				freezeWindow();
				propertyView = new PropertyView(str, true);
			}
		});
		labelDelete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				freezeWindow();
				new ConfirmView(LegendView.this, "Are you sure to delete this char?", 1);
			}
		});
	}
	
	private String equipItem(String itemDetail) {
		WindowStore.legendViewTL.set(this);
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		JFrame jf = fromMonster ? this : WindowStore.warehouseViewTL.get();
		String warning = "Can not equip!";
		switch (itemDetail.substring(0, 1)) {
		case "w":
			if (meetRequirement(itemDetail)) {
				tempWarehouse = xmlParser.getNodeByName("weapon").getTextContent();
				xmlParser.getNodeByName("weapon").setTextContent(itemDetail);
				xmlParser.save();
				launchCharProperty();
				replaceItem(fromMonster);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "r":
		case "t":
			freezeWindow();
			if (meetRequirement(itemDetail)) {
				new DialogView(jf, "Ring", 15, itemDetail);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "i":
			freezeWindow();
			if (meetRequirement(itemDetail)) {
				new DialogView(jf, "Bracelet", 11, itemDetail);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "a":
		case "s":
			if (meetRequirement(itemDetail)) {
				tempWarehouse = xmlParser.getNodeByName("amulet").getTextContent();
				xmlParser.getNodeByName("amulet").setTextContent(itemDetail);
				xmlParser.save();
				launchCharProperty();
				replaceItem(fromMonster);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "h":
			if (meetRequirement(itemDetail)) {
				tempWarehouse = xmlParser.getNodeByName("helmet").getTextContent();
				xmlParser.getNodeByName("helmet").setTextContent(itemDetail);
				xmlParser.save();
				launchCharProperty();
				replaceItem(fromMonster);
			} else {
				new WarningView(jf, warning, false);
			}
			
			break;
		case "c":
			if (meetRequirement(itemDetail)) {
				String gender = xmlParser.getNodeByName("gender").getTextContent();
				int val = gender.equals(LegendConstant.Male) ? 1 : 0;
				int code = Integer.parseInt(itemDetail.split("~")[0].substring(1));
				if (code%2 == val) {
					tempWarehouse = xmlParser.getNodeByName("armor").getTextContent();
					xmlParser.getNodeByName("armor").setTextContent(itemDetail);
					xmlParser.save();
					launchCharProperty();
					replaceItem(fromMonster);
				} else {
					freezeWindow();
					new WarningView(jf, "Gender Not Correct!", false);
				}
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "b":
			if (meetRequirement(itemDetail)) {
				tempWarehouse = xmlParser.getNodeByName("belt").getTextContent();
				xmlParser.getNodeByName("belt").setTextContent(itemDetail);
				xmlParser.save();
				launchCharProperty();
				replaceItem(fromMonster);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "o":
			if (meetRequirement(itemDetail)) {
				tempWarehouse = xmlParser.getNodeByName("boots").getTextContent();
				xmlParser.getNodeByName("boots").setTextContent(itemDetail);
				xmlParser.save();
				launchCharProperty();
				replaceItem(fromMonster);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "g":
			if (meetRequirement(itemDetail)) {
				tempWarehouse = xmlParser.getNodeByName("gem").getTextContent();
				xmlParser.getNodeByName("gem").setTextContent(itemDetail);
				xmlParser.save();
				launchCharProperty();
				replaceItem(fromMonster);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		case "x":
			if (meetRequirement(itemDetail)) {
				tempWarehouse = xmlParser.getNodeByName("medal").getTextContent();
				xmlParser.getNodeByName("medal").setTextContent(itemDetail);
				xmlParser.save();
				launchCharProperty();
				replaceItem(fromMonster);
			} else {
				new WarningView(jf, warning, false);
			}
			break;
		}
		
		return tempWarehouse;
	}
	
	private void replaceItem(boolean monster) {
		if (monster) {
			if (!tempWarehouse.isEmpty()) {
				label[tempPosition].setText(tempWarehouse.split("~")[1]);
				legend.legendItems[tempPosition] = tempWarehouse;
			} else {
				label[tempPosition].setText("");
				buttonEquip[tempPosition].setText("");
				buttonStore[tempPosition].setText("");
				legend.legendItems[tempPosition] = "";
				buttonEquip[tempPosition].removeActionListener(this);
				buttonStore[tempPosition].removeActionListener(this);
			}
		} else {
			xmlParser = new XmlParser("runSuite\\LegendHero.xml");
			xmlParser.getNodeByName("item", tempPosition).setTextContent(tempWarehouse);
			xmlParser.save();
		}
	}
	
	private boolean meetRequirement(String itemDetail) {
		propertyView.loadPorpertyFromXML();
		int actualValue = -2;
		int requiredValue = -1;
		String str = LegendConstant.Require + itemDetail.split(LegendConstant.Require)[1].substring(0, 2);
		switch (str) {
		case LegendConstant.RequireLevel:
			actualValue = propertyView.level;
			requiredValue = Integer.parseInt(itemDetail.split(LegendConstant.RequireLevel)[1]);
			break;
		case LegendConstant.RequireAttack:
			actualValue = propertyView.attack;
			requiredValue = Integer.parseInt(itemDetail.split(LegendConstant.RequireAttack)[1]);
			break;
		case LegendConstant.RequireDao:
			actualValue = propertyView.daoAttack;
			requiredValue = Integer.parseInt(itemDetail.split(LegendConstant.RequireDao)[1]);
			break;
		case LegendConstant.RequireMagic:
			actualValue = propertyView.magicAttack;
			requiredValue = Integer.parseInt(itemDetail.split(LegendConstant.RequireMagic)[1]);
			break;
		}
		return actualValue >= requiredValue ? true : false;
	}
	
	private void initRadioButton() {
		File file = new File(System.getProperty("user.dir") + "\\test-data\\Legend.xls");
		String superHero = "";
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet("Sheet2");
			for (int i = 0; i < 20; i++) {
				superHero = sheet.getCell(0, i+1).getContents();
				radioButton[i] = new MyRadioButton(superHero);
				buttonGroup.add(radioButton[i]);
			}
			book.close();
			radioButton[0].setSelected(true);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class CloseHandler extends WindowAdapter {
		LegendView view;
		File targetFile;
		File sourceFile;
		boolean compareFlag = false;
		public CloseHandler(LegendView view) {
			this.view = view;
			targetFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\save\\" + view.charName + ".xml");
			sourceFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\LegendHero.xml");
		}
		public void windowClosing(final WindowEvent event) {
			try {
				compareFlag = FileUtils.contentEquals(targetFile, sourceFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (compareFlag) {
				System.exit(0);
			} else {
				WindowStore.legendViewTL.set(view);
				new ConfirmView(view, "Do you want to save this char?", 2);
			}
		}
	}
}
