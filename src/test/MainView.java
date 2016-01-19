package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import base.WindowStore;
import runSuite.IConstants;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utility.calc.Algorithm;
import utility.file.FileUtility;
import utility.file.XmlParser;

public class MainView extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyLabel label[] = new MyLabel[10];
	private JLabel spent = new JLabel("");
	private MyLabel singleHero = new MyLabel("", 0);
	private JLabel msg = new JLabel("");
	private JLabel wfmd = new JLabel("");
	private MyButton button1 = new MyButton("1 Draw (280$)");
	private MyButton button2 = new MyButton("1 Draw (free)");
	private MyButton button3 = new MyButton("10 Draws (2520$)");
	private MyButton button4 = new MyButton("Reset");
	private MyButton button5 = new MyButton("View Log");
	private MyButton button6 = new MyButton("Test");
	private JPanel panel[] = new JPanel[20];
	MyRadioButton radioButton[] = new MyRadioButton[15];
	MyButtonGroup buttonGroup = new MyButtonGroup();
	private Box box0 = Box.createVerticalBox();
	private Box box1 = Box.createVerticalBox();
	private Box box2 = Box.createVerticalBox();
	private Box box3 = Box.createVerticalBox();
	private Box boxH = Box.createHorizontalBox();
	private int width = 880;
	private int height = 240;
	private String spentInfo = wrapColor("Spent: ", "#3B16F4");
	private String dollarSymbol = wrapColor("$", "#3B16F4");
	private String moneyColor = "red";
	private String messageColor = "#842B00";
	private String lightColor = "#828285";
	private String deepColor = "#FFA62F";
	private int yinpiao = 0;
	public MainView mainView;

	public MainView(String s) {
		super(s);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
		int money = Integer.parseInt(xmlParser.getNodeValue("Note"));
		for(int i = 0; i < 20; i++) {
			panel[i] = new JPanel();
		}
		spent.setText(wrapHtml(spentInfo + wrapColor(String.valueOf(money), moneyColor) + dollarSymbol));
		spent.setPreferredSize(new Dimension(88, 25));
		wfmd.setFont(new Font(IConstants.FONT, Font.PLAIN, 18));
		wfmd.setPreferredSize(new Dimension(20, 190));
		wfmd.setText(wrapColor(Integer.parseInt(xmlParser.getNodeValue("WFMD"))));
		msg.setForeground(Color.decode(messageColor));
		msg.setFont(new Font(IConstants.FONT, Font.PLAIN, 14));
		msg.setText(this.getPurpleInfo(Integer.parseInt(xmlParser.getNodeValue("Purple"))));
		singleHero.setPreferredSize(new Dimension(410, 95));
		singleHero.setFont(new Font(IConstants.FONT, Font.PLAIN, 32));
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button3.setPreferredSize(new Dimension(140, 25));
		button4.addActionListener(this);
		button4.setPreferredSize(new Dimension(88, 25));
		button5.addActionListener(this);
		button5.setPreferredSize(new Dimension(88, 25));
		button6.addActionListener(this);
		button6.setPreferredSize(new Dimension(88, 25));
		for (int i = 0; i < 5; i++) {
			label[i] = new MyLabel("", JLabel.CENTER);
			panel[1].add(label[i]);
		}
		for (int i = 5; i < 10; i++) {
			label[i] = new MyLabel("", JLabel.CENTER);
			panel[2].add(label[i]);
		}
		panel[3].add(button1);
		panel[3].add(button2);
		panel[3].add(button3);
		panel[4].add(msg);
		initRadioButton();
		for (int i = 0; i < 3; i++) {
			panel[5].add(radioButton[i]);
		}
		for (int i = 3; i < 6; i++) {
			panel[6].add(radioButton[i]);
		}
		for (int i = 6; i < 9; i++) {
			panel[7].add(radioButton[i]);
		}
		for (int i = 9; i < 12; i++) {
			panel[8].add(radioButton[i]);
		}
		for (int i = 12; i < 15; i++) {
			panel[9].add(radioButton[i]);
		}
		panel[11].add(spent);
		panel[12].add(button4);
		panel[13].add(button5);
		panel[14].add(button6);
		panel[15].add(wfmd);
		box0.add(panel[15]);
		for(int i = 0; i < 5; i++) {
			box1.add(panel[i]);
		}
		for(int i = 5; i < 10; i++) {
			box2.add(panel[i]);
		}
		for(int i = 10; i < 15; i++) {
			box3.add(panel[i]);
		}
		boxH.add(box0);
		boxH.add(Box.createHorizontalStrut(-20));
		boxH.add(box1);
		boxH.add(Box.createHorizontalStrut(-20));
		boxH.add(box2);
		boxH.add(Box.createHorizontalStrut(-10));
		boxH.add(box3);
		boxH.add(Box.createHorizontalStrut(8));
		add(boxH);
		setBounds((screenSize.width - width)/2, (screenSize.height - height)/2, width, height);
		setTitle("More Fun Studio");
	}

	public static void main(String[] args) {
		MainView test1 = new MainView("");
		test1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test1.setVisible(true);
		test1.setResizable(false);
	}

	public void actionPerformed(ActionEvent actionevent) {
		QuanMinShuiHu qmsh = new QuanMinShuiHu();
		if(actionevent.getSource() == button1) {
			String str = qmsh.singleDraw(false);
			panel[0].setVisible(false);
			panel[2].setVisible(false);
			panel[1].removeAll();
			panel[1].add(singleHero);
			singleHero.setText(str);
			qmsh.logFile.writeLine();
			XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
			int money = Integer.parseInt(xmlParser.getNodeValue("Note"));
			spent.setText(wrapHtml(spentInfo + wrapColor(String.valueOf(money), moneyColor) + dollarSymbol));
			msg.setText(this.getPurpleInfo(qmsh.purple));
		}
		if(actionevent.getSource() == button2) {
			String str = qmsh.singleDraw(true);
			panel[0].setVisible(false);
			panel[2].setVisible(false);
			panel[1].removeAll();
			panel[1].add(singleHero);
			singleHero.setText(str);
			qmsh.logFile.writeLine();
			msg.setText(this.getPurpleInfo(qmsh.purple));
		}
		if(actionevent.getSource() == button3) {
			panel[0].setVisible(true);
			panel[2].setVisible(true);
			panel[1].removeAll();
			String[] str = qmsh.tenDraw();
			for (int i = 0; i < 5; i++) {
				label[i] = new MyLabel("", JLabel.CENTER);
				panel[1].add(label[i]);
			}
			for(int i = 0; i < 10; i++){
				label[i].reset();
				label[i].setText(str[i]);
			}
			qmsh.logFile.writeLine();
			XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
			int money = Integer.parseInt(xmlParser.getNodeValue("Note"));
			spent.setText(wrapHtml(spentInfo + wrapColor(String.valueOf(money), moneyColor) + dollarSymbol));
			msg.setText(this.getPurpleInfo(qmsh.purple));
		}
		if(actionevent.getSource() == button4) {
			for(int i = 0; i < 10; i++){
				label[i].setText("");
			}
			singleHero.setText("");
			spent.setText(wrapHtml(spentInfo + wrapColor("0", moneyColor) + dollarSymbol));
			msg.setText(this.getPurpleInfo(9));
			qmsh.initialData();
		}
		if(actionevent.getSource() == button5) {
			qmsh.logFile.open();
		}
		if(actionevent.getSource() == button6) {
			panel[0].setVisible(true);
			panel[2].setVisible(true);
			panel[1].removeAll();
			String[] str = qmsh.conspiracyDraw();
			for (int i = 0; i < 5; i++) {
				label[i] = new MyLabel("", JLabel.CENTER);
				panel[1].add(label[i]);
			}
			for(int i = 0; i < 10; i++){
				label[i].reset();
				label[i].setText(str[i]);
			}
			qmsh.logFile.writeLine();
			XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
			int money = Integer.parseInt(xmlParser.getNodeValue("Note"));
			spent.setText(wrapHtml(spentInfo + wrapColor(String.valueOf(money), moneyColor) + dollarSymbol));
//			hideWindow();
//			new MessageView("Test");
		}
	}
	
	private void hideWindow() {
		setVisible(false);
		WindowStore.mainViewTL.set(this);
	}
	
	private String getPurpleInfo(int i) {
		String str = "";
		if(i > 0)
			str = IConstants.Estimate_Purple1 + i + IConstants.Estimate_Purple2;
		else
			str = IConstants.Promise_Purple;
		return str;
	}
	
	private void initRadioButton() {
		File file = new File(System.getProperty("user.dir") + "\\test-data\\Shuihu108.xls");
		String superHero = "";
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet("Sheet3");
			for (int i = 0; i < 15; i++) {
				superHero = sheet.getCell(0, i + 1).getContents();
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
	
	private String wrapHtml(String str) {
		return "<html>" + str + "</html>";
	}
	
	private String wrapColor(String str, String color) {
		return "<font color=" + color + ">" + str + "</font>";
	}
	
	private String wrapColor(int color) {
		String str1 = IConstants.Wan;
		String str2 = IConstants.Fu;
		String str3 = IConstants.Mo;
		String str4 = IConstants.Dang;
		switch (color) {
		case 0:
			str1 = wrapColor(str1, lightColor);
			str2 = wrapColor(str2, lightColor);
			str3 = wrapColor(str3, lightColor);
			str4 = wrapColor(str4, lightColor);
			break;
		case 1:
			str1 = wrapColor(str1, deepColor);
			str2 = wrapColor(str2, lightColor);
			str3 = wrapColor(str3, lightColor);
			str4 = wrapColor(str4, lightColor);
			break;
		case 2:
			str1 = wrapColor(str1, deepColor);
			str2 = wrapColor(str2, deepColor);
			str3 = wrapColor(str3, lightColor);
			str4 = wrapColor(str4, lightColor);
			break;
		case 3:
			str1 = wrapColor(str1, deepColor);
			str2 = wrapColor(str2, deepColor);
			str3 = wrapColor(str3, deepColor);
			str4 = wrapColor(str4, lightColor);
			break;
		case 4:
			str1 = wrapColor(str1, deepColor);
			str2 = wrapColor(str2, deepColor);
			str3 = wrapColor(str3, deepColor);
			str4 = wrapColor(str4, deepColor);
			break;
		default:
			str1 = wrapColor(str1, lightColor);
			str2 = wrapColor(str2, lightColor);
			str3 = wrapColor(str3, lightColor);
			str4 = wrapColor(str4, lightColor);
		}
		return wrapHtml(str1 + str2 + str3 + str4);
	}
	
	class MyButton extends JButton {
		private static final long serialVersionUID = 1L;

		MyButton(String buttonName) {
			super(buttonName);
			this.setPreferredSize(new Dimension(115, 25));
		}
	}

	class MyLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		MyLabel(String labelName) {
			super(labelName);
			this.setPreferredSize(new Dimension(78, 25));
			this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
			this.setAlignmentX(CENTER);
		}
		
		MyLabel(String labelName, int i) {
			super(labelName, i);
			this.setPreferredSize(new Dimension(78, 25));
		}
		
		void reset() {
			this.setPreferredSize(new Dimension(78, 25));
			this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
		}
	}

	class MyRadioButton extends JRadioButton {
		private static final long serialVersionUID = 1L;

		MyRadioButton(String buttonName) {
			super(buttonName);
			this.setPreferredSize(new Dimension(80, 25));
			this.setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
		}
	}
	
	class MyButtonGroup extends ButtonGroup {
		private static final long serialVersionUID = 1L;

		MyButtonGroup() {
			super();
		}
		
		public String getSelectedButtonText() {
	        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	            AbstractButton button = buttons.nextElement();
	            if (button.isSelected()) {
	                return button.getText();
	            }
	        }
	        return null;
	    }
	}

	class QuanMinShuiHu {
		int baseRow = 1;
		int purpleCol = 1;
		int blueCol = 2;
		int purpleRow = 85;
		int blueRow = 178;
		int purple = 0;
		FileUtility logFile = new FileUtility("log.html");

		public String singleDraw(boolean free) {
			XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
			int identifier = Integer.parseInt(xmlParser.getNodeValue("WFMD"));
			purple = Integer.parseInt(xmlParser.getNodeValue("Purple"));
			int note = Integer.parseInt(xmlParser.getNodeValue("Note"));
			String str = "Blue";
			String heroInfo = "";
			boolean flag = false;
			if(logFile.getContentLength() == 0) {
				logFile.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");//UTF-8, gb2312
				logFile.append("<html><body><h3 align=center>" + IConstants.TITLE + "</h3></body></html>");
			}
			if (identifier == 4) {
				if (purple == 0) {
					purple = 9;
				} else {
					purple = purple - 1;
				}
				heroInfo = getSuperHero() + "</html>";
				logFile.append(heroInfo);
				identifier = 0;
			} else {
				boolean randomKey = Algorithm.getDraw(10);// 10% chance
				boolean randomColor = Algorithm.getDraw(15);// 15% chance
				if (randomKey) {
					identifier = identifier + 1;
					flag = true;
				}
				if (purple == 0) {
					str = "Purple";
					purple = 9;
					heroInfo = getHero(str) + getChineseString(identifier, flag) + "</html>";
					logFile.append(heroInfo);//purple
				} else if (randomColor) {
					str = "Purple";
					purple = purple - 1;
					heroInfo = getHero(str) + getChineseString(identifier, flag) + "</html>";
					logFile.append(heroInfo);//purple
				} else {
					purple = purple - 1;
					heroInfo = getHero(str) + getChineseString(identifier, flag) + "</html>";
					logFile.append(heroInfo);//blue
				}
			}
			wfmd.setText(wrapColor(identifier));
			xmlParser.getNodeByName("Purple").setTextContent(String.valueOf(purple));
			xmlParser.getNodeByName("WFMD").setTextContent(String.valueOf(identifier));
			if(!free)
				xmlParser.getNodeByName("Note").setTextContent(String.valueOf(note + 280));
			xmlParser.save();
			return heroInfo;
		}
		
		public String[] tenDraw() {
			XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
			int identifier = Integer.parseInt(xmlParser.getNodeValue("WFMD"));
			purple = Integer.parseInt(xmlParser.getNodeValue("Purple"));
			int note = Integer.parseInt(xmlParser.getNodeValue("Note"));
			String str = "";
			String heroInfo = "";
			String[] heroes = new String[]{"", "", "", "", "", "", "", "", "", ""};
			for(int i = 0; i < 10; i++) {
				str = "Blue";
				boolean flag = false;
				if(logFile.getContentLength() == 0) {
					logFile.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n");
					logFile.append("<html><body><h3 align=center>" + IConstants.TITLE + "</h3></body></html>");
				}
				if (identifier == 4) {
					if (purple == 0) {
						purple = 9;
					} else {
						purple = purple - 1;
					}
					heroInfo = getSuperHero() + "</html>";
					if(i < 9) {
						logFile.append(heroInfo + " | ");
					} else {
						logFile.append(heroInfo);
					}
					identifier = 0;
					heroes[i] = heroInfo;
				} else {
					boolean randomKey = Algorithm.getDraw(8);// 8% chance
					boolean randomColor = Algorithm.getDraw(12);// 12% chance
					if (randomKey) {
						identifier = identifier + 1;
						flag = true;
					}
					if (purple == 0) { // Must be a purple hero
						str = "Purple";
						purple = 9;
						heroInfo = getHero(str) + getChineseString(identifier, flag) + "</html>";
						if(i < 9) {
							logFile.append(heroInfo + " | ");
						} else {
							logFile.append(heroInfo);
						}
					} else if (randomColor) { // Lucky to be a purple hero
						str = "Purple";
						purple = purple - 1;
						heroInfo = getHero(str) + getChineseString(identifier, flag) + "</html>";
						if(i < 9) {
							logFile.append(heroInfo + " | ");
						} else {
							logFile.append(heroInfo);
						}
					} else { // Blue hero
						purple = purple - 1;
						heroInfo = getHero(str) + getChineseString(identifier, flag) + "</html>";
						if(i < 9) {
							logFile.append(heroInfo + " | ");
						} else {
							logFile.append(heroInfo);
						}
					}
					heroes[i] = heroInfo;
				}
			}
			wfmd.setText(wrapColor(identifier));
			xmlParser.getNodeByName("WFMD").setTextContent(String.valueOf(identifier));
			xmlParser.getNodeByName("Note").setTextContent(String.valueOf(note + 2520));
			xmlParser.save();
			return heroes;
		}
		
		public String[] conspiracyDraw() {
			XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
			int note = Integer.parseInt(xmlParser.getNodeValue("Note"));
			String[] conspiracies = new String[]{"", "", "", "", "", "", "", "", "", ""};
			int luckyNum = Algorithm.getRandomInt(0, 9);
			int deduct = 0;
			for(int i = 0; i < 10; i++) {
				if(i == luckyNum) {
					conspiracies[i] = getQimoMiaoce();
				} else {
					int num = Algorithm.getDrawNum();
					if (num < 10) {
						conspiracies[i] = getYinpiao();
						deduct = deduct + yinpiao;
					} else if (num < 11) {
						conspiracies[i] = getQimoMiaoce();
					} else {
						conspiracies[i] = getWentaoWulue();
					}
				}
			}
			xmlParser.getNodeByName("Note").setTextContent(String.valueOf(note + 400 -  deduct));
			xmlParser.save();
			return conspiracies;
		}

		public void initialData() {
			logFile.reset();
			wfmd.setText(wrapColor(0));
			XmlParser xmlParser = new XmlParser("runSuite\\NewFile.xml");
			xmlParser.getNodeByName("WFMD").setTextContent("0");
			xmlParser.getNodeByName("Purple").setTextContent("9");
			xmlParser.getNodeByName("Note").setTextContent("0");
			xmlParser.save();
		}

		protected String getHero(String color) {
			File file = new File(System.getProperty("user.dir") + "\\test-data\\Shuihu108.xls");
			String name = "";
			try {
				Workbook book = Workbook.getWorkbook(file);
				Sheet sheet = book.getSheet("Sheet3");
				if (color.equalsIgnoreCase("purple")) {
					int randomRow = Algorithm.getRandomInt(baseRow, purpleRow);
					try {
						Cell cell = sheet.getCell(purpleCol, randomRow);
						name = "<html><font color=#C34B98>" + cell.getContents() + "</font>";
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				} else {
					int randomRow = Algorithm.getRandomInt(baseRow, blueRow);
					try {
						Cell cell = sheet.getCell(blueCol, randomRow);
						name = "<html><font color=blue>" + cell.getContents() + "</font>";
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
				book.close();
				return name;
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected String getSuperHero() {
			String heroName = buttonGroup.getSelectedButtonText();
			String name = "<html><font color=red>" + heroName + "</font>";
			return name;
		}
		
		protected String getYinpiao() {
			int num = Algorithm.getDrawNum();
			String str = "";
			if (num < 25) {
				str = "<html><font color=#736F6E>108票</font>";
				yinpiao = 108;
			} else if (num < 60) {
				str = "<html><font color=#736F6E>72票</font>";
				yinpiao = 72;
			} else {
				str = "<html><font color=#736F6E>36票</font>";
				yinpiao = 36;
			}
			return str;
		}
		
		protected String getQimoMiaoce() {
			File file = new File(System.getProperty("user.dir") + "\\test-data\\Shuihu108.xls");
			String str = "";
			boolean randomKey = Algorithm.getDraw(20);
			try {
				Workbook book = Workbook.getWorkbook(file);
				Sheet sheet = book.getSheet("Sheet6");
				int num = Algorithm.getDrawNum();
				if(randomKey) {
					if (num < 4) {
						int randomRow = Algorithm.getRandomInt(1, 6);
						try {
							Cell cell = sheet.getCell(0, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=#F88017>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else if (num < 16) {
						int randomRow = Algorithm.getRandomInt(7, 12);
						try {
							Cell cell = sheet.getCell(0, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=#C34B98>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else if (num < 54) {
						int randomRow = Algorithm.getRandomInt(13, 18);
						try {
							Cell cell = sheet.getCell(0, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=blue>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else {
						int randomRow = Algorithm.getRandomInt(19, 24);
						try {
							Cell cell = sheet.getCell(0, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=green>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (num < 4) {
						int randomRow = Algorithm.getRandomInt(1, 9);
						try {
							Cell cell = sheet.getCell(1, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=#F88017>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else if (num < 16) {
						int randomRow = Algorithm.getRandomInt(10, 15);
						try {
							Cell cell = sheet.getCell(1, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=#C34B98>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else if (num < 54) {
						int randomRow = Algorithm.getRandomInt(16, 17);
						try {
							Cell cell = sheet.getCell(1, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=blue>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else {
						int randomRow = Algorithm.getRandomInt(18, 19);
						try {
							Cell cell = sheet.getCell(1, randomRow);
							str = "<html><span style=\"text-decoration:underline\"><font color=green>" + cell.getContents() + "</font></span>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}
				}
				book.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
		
		protected String getWentaoWulue() {
			File file = new File(System.getProperty("user.dir") + "\\test-data\\Shuihu108.xls");
			String str = "";
			boolean randomKey = Algorithm.getDraw(50);
			boolean newKey = Algorithm.getDraw(10);
			try {
				Workbook book = Workbook.getWorkbook(file);
				Sheet sheet = book.getSheet("Sheet6");
				if (randomKey) {
					if (newKey) {
						int randomRow = Algorithm.getRandomInt(1, 3);
						try {
							Cell cell = sheet.getCell(2, randomRow);
							str = "<html><font color=#FFA62F>" + cell.getContents() + "</font>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else {
						int randomRow = Algorithm.getRandomInt(4, 22);
						String color = "#827B60";
						if(randomRow < 7) {
							color = "#800000";
						} else if (randomRow < 12) {
							color = "#728FCE";
						}
						try {
							Cell cell = sheet.getCell(2, randomRow);
							str = "<html><font color=" + color + ">" + cell.getContents() + "</font>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (newKey) {
						int randomRow = Algorithm.getRandomInt(1, 16);
						try {
							Cell cell = sheet.getCell(3, randomRow);
							str = "<html><font color=#FFA62F>" + cell.getContents() + "</font>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					} else {
						int randomRow = Algorithm.getRandomInt(17, 102);
						try {
							Cell cell = sheet.getCell(3, randomRow);
							str = "<html><font color=#808000>" + cell.getContents() + "</font>";
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}
				}
				book.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

		protected String getChineseString(int i, boolean bool) {
			if (bool) {
				switch (i) {
				case 1:
					return wrapColor("[" + IConstants.Wan + "]", deepColor);
				case 2:
					return wrapColor("[" + IConstants.Fu + "]", deepColor);
				case 3:
					return wrapColor("[" + IConstants.Mo + "]", deepColor);
				case 4:
					return wrapColor("[" + IConstants.Dang + "]", deepColor);
				default:
					return "";
				}
			}
			return "";
		}

	}
}
