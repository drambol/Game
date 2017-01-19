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
	public int maxAttack;
	public int maxDaoAttack;
	public int maxMagicAttack;
	public int minAttack;
	public int minDaoAttack;
	public int minMagicAttack;
	public int maxDefence;
	public int maxMagicDefence;
	public int minDefence;
	public int minMagicDefence;
	public int accuracy;
	public int dodge;
	public int luck;
	public int attackSpeed;
	public int poisonAvoid;
	public int magicAvoid;
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
		label[15].setText("" + getHitDexterity(LegendConstant.Hit));
		label[16].setText(LegendConstant.Dexterity);
		label[17].setText("" + getHitDexterity(LegendConstant.Dexterity));
		maxAttack = Integer.parseInt(calculate(LegendConstant.Attack).split("-")[1]);
		maxDaoAttack = Integer.parseInt(calculate(LegendConstant.DaoAttack).split("-")[1]);
		maxMagicAttack = Integer.parseInt(calculate(LegendConstant.MagicAttack).split("-")[1]);
		minAttack = Integer.parseInt(calculate(LegendConstant.Attack).split("-")[0]);
		minDaoAttack = Integer.parseInt(calculate(LegendConstant.DaoAttack).split("-")[0]);
		minMagicAttack = Integer.parseInt(calculate(LegendConstant.MagicAttack).split("-")[0]);
		level = Integer.parseInt(xmlParser.getNodeByName("level").getTextContent());
		maxDefence = Integer.parseInt(calculate(LegendConstant.Defence).split("-")[1]);
		minDefence = Integer.parseInt(calculate(LegendConstant.Defence).split("-")[0]);
		maxMagicDefence = Integer.parseInt(calculate(LegendConstant.MagicDefence).split("-")[1]);
		minMagicDefence = Integer.parseInt(calculate(LegendConstant.MagicDefence).split("-")[0]);
		accuracy = getHitDexterity(LegendConstant.Hit);
		dodge = getHitDexterity(LegendConstant.Dexterity);
		luck = getLuck();
		attackSpeed = getAttackSpeed();
		poisonAvoid = getPoisonAvoid();
		magicAvoid = getMagicAvoid();
		if (luck > 0) {
			minAttack = (int) ((maxAttack - minAttack) * luck / 20d + minAttack);
			minDaoAttack = (int) ((maxDaoAttack - minDaoAttack) * luck / 20d + minDaoAttack);
			minMagicAttack = (int) ((maxMagicAttack - minMagicAttack) * luck / 20d + minMagicAttack);
			minDefence = (int) ((maxDefence - minDefence) * luck / 20d + minDefence);
			minMagicDefence = (int) ((maxMagicDefence - minMagicDefence) * luck / 20d + minMagicDefence);
		}
		if (attackSpeed != 0) {
			maxAttack = (int) (maxAttack * (1 + attackSpeed / 20d));
			minAttack = (int) (minAttack * (1 + attackSpeed / 20d));
		}
		if (poisonAvoid > 0) {
			maxDefence = (int) (maxDefence * (1 + poisonAvoid / 10d));
			minDefence = (int) (minDefence * (1 + poisonAvoid / 10d));
			maxMagicDefence = (int) (maxMagicDefence * (1 + poisonAvoid / 10d));
			minMagicDefence = (int) (minMagicDefence * (1 + poisonAvoid / 10d));
		}
		if (magicAvoid > 0) {
			maxDefence = (int) (maxDefence * (1 + magicAvoid / 10d));
			minDefence = (int) (minDefence * (1 + magicAvoid / 10d));
			maxMagicDefence = (int) (maxMagicDefence * (1 + magicAvoid / 10d));
			minMagicDefence = (int) (minMagicDefence * (1 + magicAvoid / 10d));
		}
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
	
	private int getHitDexterity(String str) {
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
		if (amuletProperty.contains(string)) {
			result = result + Integer.parseInt(amuletProperty.split(string)[1].split("  ")[0]);
		}
		if (leftBraceletProperty.contains(string)) {
			result = result + Integer.parseInt(leftBraceletProperty.split(string)[1].split("  ")[0]);
		}
		if (rightBraceletProperty.contains(string)) {
			result = result + Integer.parseInt(rightBraceletProperty.split(string)[1].split("  ")[0]);
		}
		return result;
	}
	
	private int getLuck() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String str = LegendConstant.Luck + " +";
		int result = 0;
		if (weaponProperty.contains(str)) {
			result = result + Integer.parseInt(weaponProperty.split(str)[1].split("  ")[0]);
		}
		if (amuletProperty.contains(str)) {
			result = result + Integer.parseInt(amuletProperty.split(str)[1].split("  ")[0]);
		}
		result = result > 9 ? 9 : result;
		return result;
	}
	
	private int getAttackSpeed() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String str = LegendConstant.AttackSpeed + " ";
		int result = 0;
		if (weaponProperty.contains(str)) {
			if (weaponProperty.split(str)[1].substring(0, 1).equals("+")) {
				result = result + Integer.parseInt(weaponProperty.split(str)[1].split("  ")[0].substring(1));
			} else {
				result = result - Integer.parseInt(weaponProperty.split(str)[1].split("  ")[0].substring(1));
			}
		}
		if (amuletProperty.contains(str)) {
			result = result + Integer.parseInt(amuletProperty.split(str)[1].split("  ")[0]);
		}
		if (leftRingProperty.contains(str)) {
			result = result + Integer.parseInt(leftRingProperty.split(str)[1].split("  ")[0]);
		}
		if (rightRingProperty.contains(str)) {
			result = result + Integer.parseInt(rightRingProperty.split(str)[1].split("  ")[0]);
		}
		result = result > 4 ? 4 : result;
		return result;
	}
	
	private int getPoisonAvoid() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String str = LegendConstant.PoisonAvoid;
		int result = 0;
		if (amuletProperty.contains(str)) {
			result = result + Integer.parseInt(amuletProperty.split(str)[1].substring(2, 3));
		}
		if (leftRingProperty.contains(str)) {
			result = result + Integer.parseInt(leftRingProperty.split(str)[1].substring(2, 3));
		}
		if (rightRingProperty.contains(str)) {
			result = result + Integer.parseInt(rightRingProperty.split(str)[1].substring(2, 3));
		}
		if (gemProperty.contains(str)) {
			result = result + Integer.parseInt(gemProperty.split(str)[1].substring(2, 3));
		}
		return result;
	}
	
	private int getMagicAvoid() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		String str = LegendConstant.MagicAvoid;
		int result = 0;
		if (amuletProperty.contains(str)) {
			result = result + Integer.parseInt(amuletProperty.split(str)[1].substring(2, 3));
		}
		if (leftRingProperty.contains(str)) {
			result = result + Integer.parseInt(leftRingProperty.split(str)[1].substring(2, 3));
		}
		if (rightRingProperty.contains(str)) {
			result = result + Integer.parseInt(rightRingProperty.split(str)[1].substring(2, 3));
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
