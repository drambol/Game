package jinyong;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import base.WindowStore;
import controls.MyButton;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import runSuite.IConstants;
import utility.file.XmlParser;

public class HeroRank extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	protected XmlParser xmlParser = new XmlParser("runSuite\\JinyongHero.xml");
	protected static ArrayList<String> strs = new ArrayList<String>();
	protected static File file = new File(System.getProperty("user.dir") + "\\test-data\\Hero_Ranking.xls");
	protected static Workbook book;
	protected static Sheet sheet;
	protected static ArrayList<String> classes = new ArrayList<String>();
	protected String heroClass;
	protected String heroName;
	
	public JLabel heroLabel[] = new JLabel[8];
	private JPanel heroPanel[] = new JPanel[5];
	private JLabel drawLabel;
	private JPanel drawPanel = new JPanel();
	private JPanel buttonPanel1 = new JPanel();
	private JPanel buttonPanel2 = new JPanel();
	private JPanel buttonPanel3 = new JPanel();
	private JPanel buttonPanel4 = new JPanel();
	private JPanel radioPanel = new JPanel();
	private JRadioButton radioButton[] = new JRadioButton[4];
	private MyButtonGroup buttonGroup = new MyButtonGroup();
	private MyButton drawHero = new MyButton("Draw Hero");
	private MyButton saveTeam = new MyButton("Save Team");
	private MyButton clearData = new MyButton("Clear Data");
	private MyButton loadData = new MyButton("Load Data");
	
	private Box boxLv1_Vertical_1 = Box.createVerticalBox();
	private Box boxLv1_Vertical_2 = Box.createVerticalBox();
	private Box boxLv1_Vertical_3 = Box.createVerticalBox();
	private Box boxLv1_Vertical_4 = Box.createVerticalBox();
	private Box boxLv2_Horizontal = Box.createHorizontalBox();
	private Box boxLv3_Vertical = Box.createVerticalBox();
	private Box boxLv4_Horizontal = Box.createHorizontalBox();
	private int width = 800;
	private int height = 200;
	protected int p1;
	protected int p2;
	protected int p3;
	protected int p4;
	
	public int position = 0;
	
	public HeroRank(String s) {
		super(s);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		xmlParser = new XmlParser("runSuite\\JinyongHero.xml");
		p1 = Integer.parseInt(xmlParser.getNodeValue("p1"));
		p2 = Integer.parseInt(xmlParser.getNodeValue("p2"));
		p3 = Integer.parseInt(xmlParser.getNodeValue("p3"));
		p4 = Integer.parseInt(xmlParser.getNodeValue("p4"));
		for(int i = 0; i < 5; i++) {
			heroPanel[i] = new JPanel();
		}
		for (int i = 0; i < 8; i++) {
			heroLabel[i] = new JLabel("", JLabel.CENTER);
			heroLabel[i].setPreferredSize(new Dimension(100, 20));
			heroLabel[i].setFont(new Font(IConstants.FONT, Font.PLAIN, 14));
			heroLabel[i].setText(IConstants.EmptySeat1);
			if (i > 0) {
				changeHero(heroLabel[i]);
				if (!xmlParser.getNodeValues("lead").get(i - 1).isEmpty()) {
					heroLabel[i].setText(xmlParser.getNodeValues("lead").get(i - 1));
				}
			}
		}
		heroLabel[0].setText(IConstants.MyTeam);
		heroLabel[0].setPreferredSize(new Dimension(200, 40));
		heroLabel[0].setFont(new Font(IConstants.FONT, Font.PLAIN, 24));
		heroPanel[0].add(heroLabel[0]);
		heroPanel[1].add(heroLabel[1]);
		heroPanel[2].add(heroLabel[2]);
		heroPanel[2].add(heroLabel[3]);
		heroPanel[3].add(heroLabel[4]);
		heroPanel[3].add(heroLabel[5]);
		heroPanel[4].add(heroLabel[6]);
		heroPanel[4].add(heroLabel[7]);
		boxLv1_Vertical_1.add(heroPanel[0]);
		for (int i = 1; i < 5; i++) {
			boxLv1_Vertical_2.add(heroPanel[i]);
		}
		
		drawLabel = new JLabel(IConstants.EmptySeat2, JLabel.CENTER);
		drawLabel.setPreferredSize(new Dimension(180, 60));
		drawLabel.setFont(new Font(IConstants.FONT, Font.PLAIN, 24));
		drawPanel.add(drawLabel);
		drawHero.addActionListener(this);
		clearData.addActionListener(this);
		loadData.addActionListener(this);
		saveTeam.addActionListener(this);
		buttonPanel1.add(drawHero);
		buttonPanel2.add(saveTeam);
		buttonPanel3.add(clearData);
		buttonPanel4.add(loadData);
		for (int i = 0; i < 4; i++) {
			radioButton[i] = new JRadioButton();
			radioButton[i].setPreferredSize(new Dimension(100, 25));
			radioButton[i].setFont(new Font(IConstants.FONT, Font.PLAIN, 12));
			buttonGroup.add(radioButton[i]);
		}
		radioButton[0].setText(IConstants.Card1 + p1);
		radioButton[0].setSelected(true);
		radioButton[1].setText(IConstants.Card2 + p2);
		radioButton[2].setText(IConstants.Card3 + p3);
		radioButton[3].setText(IConstants.Card4 + p4);
		radioPanel.add(radioButton[0]);
		radioPanel.add(radioButton[1]);
		radioPanel.add(radioButton[2]);
		radioPanel.add(radioButton[3]);
		boxLv1_Vertical_3.add(drawPanel);
		boxLv1_Vertical_3.add(radioPanel);
		boxLv1_Vertical_4.add(buttonPanel1);
		boxLv1_Vertical_4.add(buttonPanel2);
		boxLv1_Vertical_4.add(buttonPanel3);
		boxLv1_Vertical_4.add(buttonPanel4);
		boxLv2_Horizontal.add(boxLv1_Vertical_2);
		boxLv2_Horizontal.add(boxLv1_Vertical_3);
		boxLv3_Vertical.add(boxLv1_Vertical_1);
		boxLv3_Vertical.add(boxLv2_Horizontal);
		boxLv4_Horizontal.add(boxLv3_Vertical);
		boxLv4_Horizontal.add(boxLv1_Vertical_4);
		add(boxLv4_Horizontal);
		pack();
		setBounds((screenSize.width - width)/2, (screenSize.height - height)/2, width, height);
		setTitle("Jin Yong's Hero World");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		try {
			book = Workbook.getWorkbook(file);
			sheet = book.getSheet("Rank_Heroes");
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void to_be_named() {
		for (int i = 0; i < 100; i++) {
			heroClass = getHeroClass(2);
			heroName = getHero(sheet, heroClass);
			System.out.println(heroName + "【" + heroClass + "】");
		}
		book.close();
	}
	
	public static void main(String[] args) {
		new HeroRank("");
	}

	@Override
	public void actionPerformed(ActionEvent actionevent) {
		// TODO Auto-generated method stub
		xmlParser = new XmlParser("runSuite\\JinyongHero.xml");
		if(actionevent.getSource() == drawHero) {
			String drawType = buttonGroup.getSelectedButtonText().split(" ")[0] + " ";
			switch (drawType) {
			case IConstants.Card1:
				if (p1 > 0) {
					heroClass = getHeroClass(4);
					p1 = p1 - 1;
					radioButton[0].setText(IConstants.Card1 + p1);
					xmlParser.getNodeByName("p1").setTextContent(String.valueOf(p1));
				} else {
					freezeWindow();
					new MessageView(this, IConstants.NotEnoughCard);
					return;
				}
				break;
			case IConstants.Card2:
				if (p2 > 0) {
					heroClass = getHeroClass(3);
					p2 = p2 - 1;
					radioButton[1].setText(IConstants.Card2 + p2);
					xmlParser.getNodeByName("p2").setTextContent(String.valueOf(p2));
				} else {
					freezeWindow();
					new MessageView(this, IConstants.NotEnoughCard);
					return;
				}
				break;
			case IConstants.Card3:
				if (p3 > 0) {
					heroClass = getHeroClass(2);
					p3 = p3 - 1;
					xmlParser.getNodeByName("p3").setTextContent(String.valueOf(p3));
					radioButton[2].setText(IConstants.Card3 + p3);
				} else {
					freezeWindow();
					new MessageView(this, IConstants.NotEnoughCard);
					return;
				}
				break;
			case IConstants.Card4:
				if (p4 > 0) {
					heroClass = getHeroClass(1);
					p4 = p4 - 1;
					xmlParser.getNodeByName("p4").setTextContent(String.valueOf(p4));
					radioButton[3].setText(IConstants.Card4 + p4);
				} else {
					freezeWindow();
					new MessageView(this, IConstants.NotEnoughCard);
					return;
				}
				break;
			}
			heroName = getHero(sheet, heroClass);
			drawLabel.setText(wrapColor(heroName, heroClass));
			int n = 0;
			final String plus = Pattern.quote("+");
			for (int i = 0; i < 199; i++) {
				if (xmlParser.getNodeValues("item").get(i).contains(heroName)) {
					n = 1;
					if (xmlParser.getNodeValues("item").get(i).contains("+")) {
						n = Integer.parseInt(xmlParser.getNodeValues("item").get(i).split(plus)[1].substring(0, 1)) + 1;
					}
					heroName = heroName + "+" + n;
					xmlParser.getNodeByName("item", i).setTextContent(wrapColor(heroName, heroClass));
					xmlParser.save();
					break;
				}
			}
			if (n == 0) {
				for (int i = 0; i < 199; i++) {
					if (xmlParser.getNodeValues("item").get(i).isEmpty()) {
						xmlParser.getNodeByName("item", i).setTextContent(wrapColor(heroName, heroClass));
						xmlParser.save();
						break;
					}
				}
			}
		} else if (actionevent.getSource() == clearData) {
			newGame();
		} else if (actionevent.getSource() == saveTeam) {
			if (heroLabel[1].getText().contains(IConstants.EmptySeat1)) {
				freezeWindow();
				new MessageView(this, IConstants.WarningMessage);
				return;
			} else {
				heroName = heroLabel[1].getText().split(">")[2].split("<")[0];
				File targetFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\JinyongSave\\" + heroName + ".xml");
				if (targetFile.exists()) {
					targetFile.delete();
				}
				File sourceFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\JinyongHero.xml");
				sourceFile.renameTo(targetFile);
				xmlParser.save();
				freezeWindow();
				new MessageView(this, IConstants.SaveMessage);
				return;
			}
		} else if (actionevent.getSource() == loadData) {
			this.dispose();
			new SelectTeamView("");
		}
	}
	
	public String getHero(Sheet sheet, String classValue) {
		strs.clear();
		int maxRow = sheet.getRows();
		int maxCol = sheet.getColumns();
		for (int i = 3; i < maxRow; i++) {
			if (sheet.getCell(0, i).getContents().equalsIgnoreCase(classValue)) {
				for (int j = 1; j < maxCol; j++) {
					if (!sheet.getCell(j, i).getContents().equalsIgnoreCase("")) {
						strs.add(sheet.getCell(j, i).getContents());
					}
				}
				for (int n = 1; n < 4; n++) {
					if (i + n < maxRow) {
						if (sheet.getCell(0, i + n).getContents().equals("")) {
							for (int j = 1; j < maxCol; j++) {
								if (!sheet.getCell(j, i + n).getContents().equalsIgnoreCase("")) {
									strs.add(sheet.getCell(j, i + n).getContents());
								}
							}
						} else {
							n = 4;
						}
					}
				}
			}
		}
		Collections.shuffle(strs);
		int index = (int) (Math.random() * strs.size());
		return strs.get(index);
	}
	
	public String getHeroClass(int clazz) {
		classes.clear();
		switch (clazz) {
		case 1:
			classes.add("9");
			for (int i = 0; i < 3; i++) { classes.add("8A"); }
			for (int i = 0; i < 5; i++) { classes.add("8B"); }
			for (int i = 0; i < 8; i++) { classes.add("8C"); }
			for (int i = 0; i < 12; i++) { classes.add("8D"); }
			for (int i = 0; i < 20; i++) { classes.add("7A"); }
			for (int i = 0; i < 30; i++) { classes.add("7B"); }
			for (int i = 0; i < 40; i++) { classes.add("7C"); }
			for (int i = 0; i < 50; i++) { classes.add("7D"); }
			for (int i = 0; i < 60; i++) { classes.add("6A"); }
			for (int i = 0; i < 70; i++) { classes.add("6B"); }
			for (int i = 0; i < 80; i++) { classes.add("6C"); }
			for (int i = 0; i < 90; i++) { classes.add("6D"); }
			break;
		case 2:
			for (int i = 0; i < 3; i++) { classes.add("8A"); }
			for (int i = 0; i < 5; i++) { classes.add("8B"); }
			for (int i = 0; i < 8; i++) { classes.add("8C"); }
			for (int i = 0; i < 12; i++) { classes.add("8D"); }
			for (int i = 0; i < 20; i++) { classes.add("7A"); }
			for (int i = 0; i < 30; i++) { classes.add("7B"); }
			for (int i = 0; i < 40; i++) { classes.add("7C"); }
			for (int i = 0; i < 50; i++) { classes.add("7D"); }
			for (int i = 0; i < 60; i++) { classes.add("6A"); }
			for (int i = 0; i < 70; i++) { classes.add("6B"); }
			for (int i = 0; i < 80; i++) { classes.add("6C"); }
			for (int i = 0; i < 90; i++) { classes.add("6D"); }
			for (int i = 0; i < 100; i++) { classes.add("5A"); }
			for (int i = 0; i < 110; i++) { classes.add("5B"); }
			for (int i = 0; i < 120; i++) { classes.add("5C"); }
			for (int i = 0; i < 130; i++) { classes.add("5D"); }
			break;
		case 3:
			for (int i = 0; i < 20; i++) { classes.add("7A"); }
			for (int i = 0; i < 30; i++) { classes.add("7B"); }
			for (int i = 0; i < 40; i++) { classes.add("7C"); }
			for (int i = 0; i < 50; i++) { classes.add("7D"); }
			for (int i = 0; i < 60; i++) { classes.add("6A"); }
			for (int i = 0; i < 70; i++) { classes.add("6B"); }
			for (int i = 0; i < 80; i++) { classes.add("6C"); }
			for (int i = 0; i < 90; i++) { classes.add("6D"); }
			for (int i = 0; i < 100; i++) { classes.add("5A"); }
			for (int i = 0; i < 110; i++) { classes.add("5B"); }
			for (int i = 0; i < 120; i++) { classes.add("5C"); }
			for (int i = 0; i < 130; i++) { classes.add("5D"); }
			for (int i = 0; i < 140; i++) { classes.add("4A"); classes.add("4B"); classes.add("4C"); classes.add("4D"); }
			break;
		default:
			classes.add("9");
			for (int i = 0; i < 3; i++) { classes.add("8A"); }
			for (int i = 0; i < 5; i++) { classes.add("8B"); }
			for (int i = 0; i < 8; i++) { classes.add("8C"); }
			for (int i = 0; i < 12; i++) { classes.add("8D"); }
			for (int i = 0; i < 20; i++) { classes.add("7A"); }
			for (int i = 0; i < 30; i++) { classes.add("7B"); }
			for (int i = 0; i < 40; i++) { classes.add("7C"); }
			for (int i = 0; i < 50; i++) { classes.add("7D"); }
			for (int i = 0; i < 60; i++) { classes.add("6A"); }
			for (int i = 0; i < 70; i++) { classes.add("6B"); }
			for (int i = 0; i < 80; i++) { classes.add("6C"); }
			for (int i = 0; i < 90; i++) { classes.add("6D"); }
			for (int i = 0; i < 100; i++) { classes.add("5A"); }
			for (int i = 0; i < 110; i++) { classes.add("5B"); }
			for (int i = 0; i < 120; i++) { classes.add("5C"); }
			for (int i = 0; i < 130; i++) { classes.add("5D"); }
			for (int i = 0; i < 140; i++) { classes.add("4A"); classes.add("4B"); classes.add("4C"); classes.add("4D"); }
			for (int i = 0; i < 150; i++) {
				classes.add("3A");
				classes.add("3B");
				classes.add("3C");
				classes.add("3D");
				classes.add("2A");
				classes.add("2B");
				classes.add("2C");
				classes.add("2D");
				classes.add("1");
			}
			break;
		}
		Collections.shuffle(classes);
		int index = (int) (Math.random() * classes.size());
		heroClass = classes.get(index);
		return heroClass;
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
	
	private String wrapColor(String name, String clazz) {
		String color = "";
		switch (clazz) {
		case "9":
		case "8A":
			color = "#FF00FF";
			break;
		case "8B":
		case "8C":
		case "8D":
			color = "red";
			break;
		case "7A":
		case "7B":
		case "7C":
		case "7D":
			color = "orange";
			break;
		case "6A":
		case "6B":
		case "6C":
		case "6D":
			color = "purple";
			break;
		case "5A":
		case "5B":
		case "5C":
		case "5D":
			color = "blue";
			break;
		case "4A":
		case "4B":
		case "4C":
		case "4D":
			color = "green";
			break;
		default:
			color = "#808080";	
		}
		return "<html><font color=" + color + ">" + name + "</font></html>";
	}
	
	private void changeHero(final JLabel label) {
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				for (int i = 1; i < 8; i++) {
					if (label == heroLabel[i]) {
						position = i;
						break;
					}
				}
				freezeWindow();
				new JuXianZhuang("");
			}
		});
	}
	
	public void freezeWindow() {
		WindowStore.heroRankTL.set(this);
		setEnabled(false);
	}
	
	public void newGame() {
		for (int i = 0; i < 199; i++) {
			xmlParser.getNodeByName("item", i).setTextContent("");
		}
		for (int i = 0; i < 7; i++) {
			xmlParser.getNodeByName("lead", i).setTextContent("");
		}
		p1 = 100;
		p2 = 30;
		p3 = 10;
		p4 = 3;
		xmlParser.getNodeByName("p1").setTextContent(String.valueOf(p1));
		xmlParser.getNodeByName("p2").setTextContent(String.valueOf(p2));
		xmlParser.getNodeByName("p3").setTextContent(String.valueOf(p3));
		xmlParser.getNodeByName("p4").setTextContent(String.valueOf(p4));
		radioButton[0].setText(IConstants.Card1 + p1);
		radioButton[1].setText(IConstants.Card2 + p2);
		radioButton[2].setText(IConstants.Card3 + p3);
		radioButton[3].setText(IConstants.Card4 + p4);
		xmlParser.save();
		for (int i = 1; i < 8; i++) {
			heroLabel[i].setText(IConstants.EmptySeat1);
		}
		drawLabel.setText(IConstants.EmptySeat2);
	}

}
