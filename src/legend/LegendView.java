package legend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.NodeList;

import base.WindowStore;
import clazz.LegendItem;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import runSuite.IConstants;
import utility.calc.Algorithm;
import utility.file.XmlParser;

public class LegendView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private MyLabel lbEquipment[] = new MyLabel[24];
	private JPanel jpEquipment[] = new JPanel[6];
	private MyLabel label[] = new MyLabel[10];
	private JPanel panel[] = new JPanel[11];
	private MyButton buttonKill = new MyButton("Kill Monster");
	private MyButton button[] = new MyButton[10];
	private Box box0 = Box.createVerticalBox();
	private Box box1 = Box.createVerticalBox();
	private Box boxH = Box.createVerticalBox();
	private JLabel lHeader = new JLabel();
	private JPanel pHeader = new JPanel();
	private JTextField tText1 = new JTextField();
	private JTextField tText2 = new JTextField();
	private JPanel pText = new JPanel();
	XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	Legend legend = new Legend();
	
	public LegendView(String s) {
		super(s);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		buttonKill.addActionListener(this);
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
			label[i].setPreferredSize(new Dimension(550, 22));
			panel[i].add(label[i], BorderLayout.WEST);
			button[i] = new MyButton("", new Dimension(80, 20));
			panel[i].add(button[i], BorderLayout.EAST);
		}
		tText1.setPreferredSize(new Dimension(85, 25));
		tText1.setHorizontalAlignment(JTextField.CENTER);
		tText1.setEditable(false);
		tText2.setPreferredSize(new Dimension(530, 25));
		tText2.setEditable(false);
		pText.setPreferredSize(new Dimension(650, 30));
		pText.add(tText1, BorderLayout.AFTER_LAST_LINE);
		pText.add(tText2, BorderLayout.AFTER_LAST_LINE);
		this.launchCharProperty(xmlParser);
		panel[10] = new JPanel();
		panel[10].add(buttonKill);
		pHeader.add(lHeader, BorderLayout.CENTER);
		box0.add(pHeader, BorderLayout.AFTER_LAST_LINE);
		for(int i = 0; i < 6; i++) {
			box0.add(jpEquipment[i], BorderLayout.AFTER_LAST_LINE);
		}
		box0.add(pText, BorderLayout.AFTER_LAST_LINE);
		for(int i = 0; i < 10; i++) {
			box0.add(panel[i], BorderLayout.AFTER_LAST_LINE);
		}
		box1.add(panel[10]);
		boxH.add(box0);
		boxH.add(box1);
		add(boxH);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Legend");
	}
	
	class MyButton extends JButton {
		private static final long serialVersionUID = 1L;

		MyButton(String buttonName) {
			super(buttonName);
			this.setPreferredSize(new Dimension(108, 22));
		}
		
		MyButton(String buttonName, Dimension dimension) {
			super(buttonName);
			this.setPreferredSize(dimension);
		}
	}

	class MyLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		MyLabel(String labelName) {
			super(labelName);
			this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
		}
		
		MyLabel(String labelName, int i) {
			super(labelName, i);
			this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
		}
		
		void reset() {
			this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
		}
	}
	
	class Legend {
		
		String[] legendItems;
		String[] itemCode;
		
		public String[] getItemFromMonster(String monsterCode) {
			legendItems = new String[10];
			itemCode = new String[10];
			XmlParser xmlParser = new XmlParser("runSuite\\LegendMonster.xml");
			NodeList nodeList = xmlParser.getNodeByName(monsterCode).getChildNodes();
			int count = 0;
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (count < 10) {
					String code = nodeList.item(i).getNodeName();
					if (!code.equals("#text") && !code.substring(0, 1).contentEquals("m")) {
						double probability = Double.parseDouble(nodeList.item(i).getTextContent());
						if (Algorithm.getDraw(probability)) {
							LegendItem legendItem = new LegendItem(getItemByCode(code));
							legendItems[count] = legendItem.printItem();
							itemCode[count] = legendItem.code;
							count = count + 1;
						}
					} else if (code.substring(0, 1).contentEquals("m")) {
						int a = Integer.parseInt(nodeList.item(i).getTextContent().split("-")[0]);
						int b = Integer.parseInt(nodeList.item(i).getTextContent().split("-")[1]);
						LegendItem legendItem = new LegendItem(getItemByCode(code));
						legendItems[count] = "*" + legendItem.printMedical(Algorithm.getRandomInt(a, b));
						itemCode[count] = legendItem.code;
						count = count + 1;
					}
				}
			}
			return legendItems;
		}
		
		public int getItemByCode(String str) {
			File file = new File(System.getProperty("user.dir") + "\\test-data\\Legend.xls");
			try {
				Workbook book = Workbook.getWorkbook(file);
				Sheet sheet = book.getSheet("Sheet1");
				int bound = sheet.getRows();
				for (int i = 1; i < bound; i++) {
					if (str.equalsIgnoreCase(sheet.getCell(1, i).getContents())) {
						return i;
					}
				}
				book.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 1;
		}
	}
	
	public static void main(String[] args) {
		LegendView test1 = new LegendView("");
		test1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test1.setVisible(true);
		test1.setResizable(false);
	}

	public void actionPerformed(ActionEvent actionevent) {
		int number = -1;
		if (actionevent.getSource() == buttonKill) {
			for(int i = 0; i < 10; i++) {
				label[i].setText("");
				button[i].setText("");
				button[i].removeActionListener(this);
			}
			String[] str = legend.getItemFromMonster("白野猪");
			for(int i = 0; i < 10; i++) {
				if (str[i] != null && !str[i].substring(0, 1).equals("*")) {
					label[i].setText(str[i]);
					button[i].setText("Equip It");
					button[i].addActionListener(this);
				} else if (str[i] != null && str[i].substring(0, 1).equals("*")) {
					label[i].setText(str[i].substring(1));
				} else {
					label[i].setText("");
					button[i].setText("");
					button[i].removeActionListener(this);
				}
			}
			return;
		}
		for (int i = 0; i < 10; i++) {
			if (actionevent.getSource() == button[i]) {
				number = i;
				break;
			}
		}
		if (number >= 0) {
			switch (legend.itemCode[number].substring(0, 1)) {
			case "w":
				lbEquipment[1].setText(label[number].getText().split(" ")[0]);
				xmlParser.getNodeByName("weapon").setTextContent(label[number].getText());
				xmlParser.save();
				launchCharProperty(xmlParser);
				break;
			case "r":
			case "t":
				freezeWindow();
				new MessageView("Ring", 15, number);
				break;
			case "i":
				freezeWindow();
				new MessageView("Bracelet", 11, number);
				break;
			case "a":
			case "s":
				lbEquipment[7].setText(label[number].getText().split(" ")[0]);
				xmlParser.getNodeByName("amulet").setTextContent(label[number].getText());
				xmlParser.save();
				launchCharProperty(xmlParser);
				break;
			case "h":
				lbEquipment[5].setText(label[number].getText().split(" ")[0]);
				xmlParser.getNodeByName("helmet").setTextContent(label[number].getText());
				xmlParser.save();
				launchCharProperty(xmlParser);
				break;
			case "c":
				lbEquipment[3].setText(label[number].getText().split(" ")[0]);
				xmlParser.getNodeByName("armor").setTextContent(label[number].getText());
				xmlParser.save();
				launchCharProperty(xmlParser);
				break;
			case "b":
				lbEquipment[19].setText(label[number].getText().split(" ")[0]);
				xmlParser.getNodeByName("belt").setTextContent(label[number].getText());
				xmlParser.save();
				launchCharProperty(xmlParser);
				break;
			case "o":
				lbEquipment[21].setText(label[number].getText().split(" ")[0]);
				xmlParser.getNodeByName("boots").setTextContent(label[number].getText());
				xmlParser.save();
				launchCharProperty(xmlParser);
				break;
			}
		}
	}
	
	public void reactFromMessage(boolean flag, String str, int a, int b) {
		if (flag) {
			lbEquipment[a].setText(label[b].getText().split(" ")[0]);
			xmlParser.getNodeByName("left" + str).setTextContent(label[b].getText());
		} else {
			lbEquipment[a+2].setText(label[b].getText().split(" ")[0]);
			xmlParser.getNodeByName("right" + str).setTextContent(label[b].getText());
		}
		xmlParser.save();
		launchCharProperty(xmlParser);
	}
	
	private void freezeWindow() {
		setEnabled(false);
		WindowStore.legendViewTL.set(this);
	}
	
	protected void launchCharProperty(XmlParser xmlParser) {
		String charName = xmlParser.getNodeByName("name").getTextContent();
		String charLevel = xmlParser.getNodeByName("level").getTextContent();
		String charCareer = xmlParser.getNodeByName("career").getTextContent();
		String headerInfo = charName + " (" + charCareer + " Lv " + charLevel + ")";
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
		lHeader.setText(headerInfo);
		lbEquipment[0].setText("武器");
		replaceItem(lbEquipment[1], weapon);
		lbEquipment[2].setText("盔甲");
		replaceItem(lbEquipment[3], armor);
		lbEquipment[4].setText("头盔");
		replaceItem(lbEquipment[5], helmet);
		lbEquipment[6].setText("项链");
		replaceItem(lbEquipment[7], amulet);
		lbEquipment[8].setText("勋章");
		replaceItem(lbEquipment[9], medal);
		lbEquipment[10].setText("左手镯");
		replaceItem(lbEquipment[11], leftBracelet);
		lbEquipment[12].setText("右手镯");
		replaceItem(lbEquipment[13], rightBracelet);
		lbEquipment[14].setText("左戒指");
		replaceItem(lbEquipment[15], leftRing);
		lbEquipment[16].setText("右戒指");
		replaceItem(lbEquipment[17], rightRing);
		lbEquipment[18].setText("腰带");
		replaceItem(lbEquipment[19], belt);
		lbEquipment[20].setText("靴子");
		replaceItem(lbEquipment[21], boots);
		lbEquipment[22].setText("宝石");
		replaceItem(lbEquipment[23], gem);
	}
	
	private void replaceItem(final MyLabel label, final String str) {
		label.setText(str.split(" ")[0]);
		label.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (str.length() > 0) {
					tText1.setText(str.split(" ")[0]);
					tText2.setText(str.substring(str.indexOf(" ")));
				} else {
					tText1.setText("");
					tText2.setText("");
				}
			}
		});
	}

}
