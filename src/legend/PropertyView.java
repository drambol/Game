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
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.WindowStore;
import utility.file.XmlParser;

public class PropertyView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 5L;
	private JPanel headerPanel = new JPanel();
	private JLabel headerLabel = new JLabel("", JLabel.CENTER);
	private JPanel panel[] = new JPanel[3];
	private JLabel label[] = new JLabel[18];
	private JLabel buttomMargin = new JLabel();
	private Box box0 = Box.createVerticalBox();
	XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	public String headerInfo = "";
	public final LegendView legendView = WindowStore.legendViewTL.get();
	private String weaponProperty;
	private String armorProperty;
	private String helmetProperty;
	private String amuletProperty;
	private String medalProperty;
	private String leftBraceletProperty;
	private String rightBraceletProperty;
	private String leftRingProperty;
	private String rightRingProperty;
	private String beltProperty;
	private String bootsProperty;
	private String gemProperty;
	public int attack;
	public int daoAttack;
	public int magicAttack;
	public int level = 0;
	
	public PropertyView(String s, boolean visibility) {
		super(s);
		this.addWindowListener(new CloseHandler());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		headerPanel.setPreferredSize(new Dimension(200, 24));
		headerLabel.setPreferredSize(new Dimension(180, 22));
		headerPanel.add(headerLabel, BorderLayout.CENTER);
		box0.add(headerPanel);
		for (int i = 0; i < 9; i++) {
			label[i*2] = new JLabel("", JLabel.LEFT);
			label[i*2+1] = new JLabel("", JLabel.LEFT);
			label[i*2].setPreferredSize(new Dimension(30, 22));
			label[i*2+1].setPreferredSize(new Dimension(50, 22));
		}
		for (int i = 0; i < 3; i++) {
			panel[i] = new JPanel();
			panel[i].setPreferredSize(new Dimension(200, 24));
			for (int j = 0; j < 6; j++) {
				panel[i].add(label[i*6+j]);
			}
			box0.add(panel[i]);
		}
		headerInfo = s;
		loadPorpertyFromXML();
		buttomMargin.setPreferredSize(new Dimension(200, 10));
		box0.add(buttomMargin);
		add(box0);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Properties - " + s.split(" ")[0]);
		setVisible(visibility);
	}
	
	public void loadPorpertyFromXML() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		weaponProperty = xmlParser.getNodeByName("weapon").getTextContent();
		armorProperty = xmlParser.getNodeByName("armor").getTextContent();
		helmetProperty = xmlParser.getNodeByName("helmet").getTextContent();
		amuletProperty = xmlParser.getNodeByName("amulet").getTextContent();
		medalProperty = xmlParser.getNodeByName("medal").getTextContent();
		leftBraceletProperty = xmlParser.getNodeByName("leftBracelet").getTextContent();
		rightBraceletProperty = xmlParser.getNodeByName("rightBracelet").getTextContent();
		leftRingProperty = xmlParser.getNodeByName("leftRing").getTextContent();
		rightRingProperty = xmlParser.getNodeByName("rightRing").getTextContent();
		beltProperty = xmlParser.getNodeByName("belt").getTextContent();
		bootsProperty = xmlParser.getNodeByName("boots").getTextContent();
		gemProperty = xmlParser.getNodeByName("gem").getTextContent();
		if (headerInfo.length() > 2) {
			String charLevel = xmlParser.getNodeByName("level").getTextContent();
			headerInfo = headerInfo.split("Lv")[0] + "Lv " + charLevel + ")";
			headerLabel.setText(headerInfo);
		}
		label[0].setText(LegendConstant.Attack);
		label[1].setText(calculate(LegendConstant.Attack));
		label[2].setText(LegendConstant.DaoAttack);
		label[3].setText(calculate(LegendConstant.DaoAttack));
		label[4].setText(LegendConstant.MagicAttack);
		label[5].setText(calculate(LegendConstant.MagicAttack));
		label[6].setText(LegendConstant.Defence);
		label[7].setText(calculate(LegendConstant.Defence));
		label[8].setText(LegendConstant.MagicDefence);
		label[9].setText(calculate(LegendConstant.MagicDefence));
		label[10].setText("HP");
		label[11].setText(xmlParser.getNodeByName("hp").getTextContent());
		label[12].setText("MP");
		label[13].setText(xmlParser.getNodeByName("mp").getTextContent());
		label[14].setText(LegendConstant.Hit);
		label[15].setText("" + getOther(LegendConstant.Hit));
		label[16].setText(LegendConstant.Dexterity);
		label[17].setText("" + getOther(LegendConstant.Dexterity));
		attack = Integer.parseInt(calculate(LegendConstant.Attack).split("-")[1]);
		daoAttack = Integer.parseInt(calculate(LegendConstant.DaoAttack).split("-")[1]);
		magicAttack = Integer.parseInt(calculate(LegendConstant.MagicAttack).split("-")[1]);
		level = Integer.parseInt(xmlParser.getNodeByName("level").getTextContent());
	}
	
	private String calculate(String str) {
		int min = getMin(str);
		int max = getMax(str);
		String result = min + "-" + max;
		return result;
	}
	
	private int getMin(String str) {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String string = str + ": ";
		int min = 0;
		switch (str) {
		case LegendConstant.Attack:
			min = Integer.parseInt(xmlParser.getNodeByName("dc").getTextContent().split("-")[0]);
			break;
		case LegendConstant.DaoAttack:
			min = Integer.parseInt(xmlParser.getNodeByName("sc").getTextContent().split("-")[0]);
			break;
		case LegendConstant.MagicAttack:
			min = Integer.parseInt(xmlParser.getNodeByName("mc").getTextContent().split("-")[0]);
			break;
		case LegendConstant.Defence:
			min = Integer.parseInt(xmlParser.getNodeByName("ac").getTextContent().split("-")[0]);
			break;
		case LegendConstant.MagicDefence:
			min = Integer.parseInt(xmlParser.getNodeByName("mac").getTextContent().split("-")[0]);
			break;
		}
		if (weaponProperty.contains(string)) {
			min = min + Integer.parseInt(weaponProperty.split(string)[1].split("-")[0]);
		}
		if (armorProperty.contains(string)) {
			min = min + Integer.parseInt(armorProperty.split(string)[1].split("-")[0]);
		}
		if (helmetProperty.contains(string)) {
			min = min + Integer.parseInt(helmetProperty.split(string)[1].split("-")[0]);
		}
		if (amuletProperty.contains(string)) {
			min = min + Integer.parseInt(amuletProperty.split(string)[1].split("-")[0]);
		}
		if (medalProperty.contains(string)) {
			min = min + Integer.parseInt(medalProperty.split(string)[1].split("-")[0]);
		}
		if (leftBraceletProperty.contains(string)) {
			min = min + Integer.parseInt(leftBraceletProperty.split(string)[1].split("-")[0]);
		}
		if (rightBraceletProperty.contains(string)) {
			min = min + Integer.parseInt(rightBraceletProperty.split(string)[1].split("-")[0]);
		}
		if (leftRingProperty.contains(string)) {
			min = min + Integer.parseInt(leftRingProperty.split(string)[1].split("-")[0]);
		}
		if (rightRingProperty.contains(string)) {
			min = min + Integer.parseInt(rightRingProperty.split(string)[1].split("-")[0]);
		}
		if (beltProperty.contains(string)) {
			min = min + Integer.parseInt(beltProperty.split(string)[1].split("-")[0]);
		}
		if (bootsProperty.contains(string)) {
			min = min + Integer.parseInt(bootsProperty.split(string)[1].split("-")[0]);
		}
		if (gemProperty.contains(string)) {
			min = min + Integer.parseInt(gemProperty.split(string)[1].split("-")[0]);
		}
		return min;
	}
	
	private int getMax(String str) {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String string = str + ": ";
		int max = 0;
		switch (str) {
		case LegendConstant.Attack:
			max = Integer.parseInt(xmlParser.getNodeByName("dc").getTextContent().split("-")[1]);
			break;
		case LegendConstant.DaoAttack:
			max = Integer.parseInt(xmlParser.getNodeByName("sc").getTextContent().split("-")[1]);
			break;
		case LegendConstant.MagicAttack:
			max = Integer.parseInt(xmlParser.getNodeByName("mc").getTextContent().split("-")[1]);
			break;
		case LegendConstant.Defence:
			max = Integer.parseInt(xmlParser.getNodeByName("ac").getTextContent().split("-")[1]);
			break;
		case LegendConstant.MagicDefence:
			max = Integer.parseInt(xmlParser.getNodeByName("mac").getTextContent().split("-")[1]);
			break;
		}
		if (weaponProperty.contains(string)) {
			max = max + Integer.parseInt(weaponProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (armorProperty.contains(string)) {
			max = max + Integer.parseInt(armorProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (helmetProperty.contains(string)) {
			max = max + Integer.parseInt(helmetProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (amuletProperty.contains(string)) {
			max = max + Integer.parseInt(amuletProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (medalProperty.contains(string)) {
			max = max + Integer.parseInt(medalProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (leftBraceletProperty.contains(string)) {
			max = max + Integer.parseInt(leftBraceletProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (rightBraceletProperty.contains(string)) {
			max = max + Integer.parseInt(rightBraceletProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (leftRingProperty.contains(string)) {
			max = max + Integer.parseInt(leftRingProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (rightRingProperty.contains(string)) {
			max = max + Integer.parseInt(rightRingProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (beltProperty.contains(string)) {
			max = max + Integer.parseInt(beltProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (bootsProperty.contains(string)) {
			max = max + Integer.parseInt(bootsProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		if (gemProperty.contains(string)) {
			max = max + Integer.parseInt(gemProperty.split(string)[1].split("-")[1].split("  ")[0]);
		}
		return max;
	}
	
	private int getOther(String str) {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String string = str + " +";
		int result = 0;
		switch (str) {
		case LegendConstant.Hit:
			result = Integer.parseInt(xmlParser.getNodeByName("hit").getTextContent());
			break;
		case LegendConstant.Dexterity:
			result = Integer.parseInt(xmlParser.getNodeByName("dex").getTextContent());
			break;
		}
		if (weaponProperty.contains(string)) {
			result = result + Integer.parseInt(weaponProperty.split(string)[1].split("  ")[0]);
		}
		if (armorProperty.contains(string)) {
			result = result + Integer.parseInt(armorProperty.split(string)[1].split("  ")[0]);
		}
		if (helmetProperty.contains(string)) {
			result = result + Integer.parseInt(helmetProperty.split(string)[1].split("  ")[0]);
		}
		if (amuletProperty.contains(string)) {
			result = result + Integer.parseInt(amuletProperty.split(string)[1].split("  ")[0]);
		}
		if (medalProperty.contains(string)) {
			result = result + Integer.parseInt(medalProperty.split(string)[1].split("  ")[0]);
		}
		if (leftBraceletProperty.contains(string)) {
			result = result + Integer.parseInt(leftBraceletProperty.split(string)[1].split("  ")[0]);
		}
		if (rightBraceletProperty.contains(string)) {
			result = result + Integer.parseInt(rightBraceletProperty.split(string)[1].split("  ")[0]);
		}
		if (leftRingProperty.contains(string)) {
			result = result + Integer.parseInt(leftRingProperty.split(string)[1].split("  ")[0]);
		}
		if (rightRingProperty.contains(string)) {
			result = result + Integer.parseInt(rightRingProperty.split(string)[1].split("  ")[0]);
		}
		if (beltProperty.contains(string)) {
			result = result + Integer.parseInt(beltProperty.split(string)[1].split("  ")[0]);
		}
		if (bootsProperty.contains(string)) {
			result = result + Integer.parseInt(bootsProperty.split(string)[1].split("  ")[0]);
		}
		if (gemProperty.contains(string)) {
			result = result + Integer.parseInt(gemProperty.split(string)[1].split("  ")[0]);
		}
		return result;
	}
	
	private static class CloseHandler extends WindowAdapter {
		public void windowClosing(final WindowEvent event) {
			LegendView legendView = WindowStore.legendViewTL.get();
			legendView.setEnabled(true);
			legendView.requestFocus();
		}
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		// TODO Auto-generated method stub
		
	}

}
