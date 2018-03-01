package legend;

import java.text.DecimalFormat;

import clazz.Monsters;
import utility.calc.Algorithm;
import utility.file.XmlParser;

public class LegendFunction {
	
	XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	private int exp = 0;
	private int reqExp = 0;
	private int currExp = 0;
	private int charLevel = 0;
	private String career = "";
	private int hp = 0;
	private int mp = 0;
	private int hpGrow = 0;
	private int mpGrow = 0;
	private String dc = "";
	private String sc = "";
	private String mc = "";
	private String ac = "";
	private String mac = "";
	public boolean levelUp = false;
	String monsterName;
	private int extraAttack;
	private int extraDaoAttack;
	private int extraMagicAttack;
	private int extraDefence;
	private int extraMagicDefence;
	
	public LegendFunction(String monster) {
		this.monsterName = monster;
		currExp = Integer.parseInt(xmlParser.getNodeByName("exp").getTextContent());
		charLevel = Integer.parseInt(xmlParser.getNodeByName("level").getTextContent());
		switch (monster) {
		case LegendConstant.Monster01:
			exp = 400;
			break;
		case LegendConstant.Monster02:
			exp = 1000;
			break;
		case LegendConstant.Monster03:
			exp = 1600;
			break;
		case LegendConstant.Monster04:
			exp = 2000;
			break;
		case LegendConstant.Monster05:
			exp = 3200;
			break;
		case LegendConstant.Monster06:
			exp = 3200;
			break;
		case LegendConstant.Monster07:
			exp = 6000;
			break;
		case LegendConstant.Monster08:
			exp = 5000;
			break;
		case LegendConstant.Monster09:
			exp = 9000;
			break;
		case LegendConstant.Monster10:
			exp = 10000;
			break;
		case LegendConstant.Monster11:
			exp = 10000;
			break;
		case LegendConstant.Monster12:
			exp = 10000;
			break;
		case LegendConstant.Monster13:
			exp = 14400;
			break;
		case LegendConstant.Monster14:
			exp = 18000;
			break;
		case LegendConstant.Monster15:
			exp = 12000;
			break;
		case LegendConstant.Monster16:
			exp = 20000;
			break;
		case LegendConstant.Monster17:
			exp = 30000;
			break;
		case LegendConstant.Monster18:
			exp = 30000;
			break;
		case LegendConstant.Monster19:
			exp = 40000;
			break;
		case LegendConstant.Monster20:
			exp = 60000;
			break;
		}
	}
	
	public int addExp() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		int n = 1;
		String str = xmlParser.getNodeByName("medal").getTextContent().split("~")[0];
		switch (str) {
		case "x00":
			n = 2;
			break;
		case "x01":
		case "x10":
		case "x11":
			n = 3;
			break;
		}
		reqExp = (int) (Math.pow(1.25, charLevel - 1) * 100);
		int realExp = exp * n;
		if (exp * n + currExp >= reqExp) {
			charLevel = charLevel + 1;
			currExp = realExp + currExp - reqExp;
			xmlParser.getNodeByName("level").setTextContent(String.valueOf(charLevel));
			levelUp = true;
			attributeGrow();
		} else {
			currExp = exp * n + currExp;
		}
		xmlParser.getNodeByName("exp").setTextContent(String.valueOf(currExp));
		xmlParser.save();
		return realExp;
	}
	
	private void attributeGrow() {
		career = xmlParser.getNodeByName("career").getTextContent();
		hp = Integer.parseInt(xmlParser.getNodeByName("hp").getTextContent());
		mp = Integer.parseInt(xmlParser.getNodeByName("mp").getTextContent());
		dc = xmlParser.getNodeByName("dc").getTextContent();
		sc = xmlParser.getNodeByName("sc").getTextContent();
		mc = xmlParser.getNodeByName("mc").getTextContent();
		ac = xmlParser.getNodeByName("ac").getTextContent();
		mac = xmlParser.getNodeByName("mac").getTextContent();
		int dcMin = Integer.parseInt(dc.split("-")[0]);
		int dcMax = Integer.parseInt(dc.split("-")[1]);
		int scMin = Integer.parseInt(sc.split("-")[0]);
		int scMax = Integer.parseInt(sc.split("-")[1]);
		int mcMin = Integer.parseInt(mc.split("-")[0]);
		int mcMax = Integer.parseInt(mc.split("-")[1]);
		int acMin = Integer.parseInt(ac.split("-")[0]);
		int acMax = Integer.parseInt(ac.split("-")[1]);
		int macMin = Integer.parseInt(mac.split("-")[0]);
		int macMax = Integer.parseInt(mac.split("-")[1]);
		switch (career) {
		case LegendConstant.Warrior:
			hpGrow = (int) (0.6 * charLevel + 5);
			mpGrow = (int) (0.1 * charLevel + 3);
			if (charLevel%5 == 0) {
				if (dcMin < dcMax) {
					dcMin = dcMin + 1;
				}
				dcMax = dcMax + 1;
			}
			if (charLevel%7 == 0) {
				if (acMin < acMax) {
					acMin = acMin + 1;
				}
				acMax = acMax + 1;
			}
			break;
		case LegendConstant.Taoist:
			hpGrow = (int) (charLevel/3 + 3);
			mpGrow = (int) (charLevel/2 + 4);
			if (charLevel%7 == 0) {
				if (scMin < scMax) {
					scMin = scMin + 1;
					dcMin = dcMin + 1;
					macMin = macMin + 1;
				}
				scMax = scMax + 1;
				dcMax = dcMax + 1;
				macMax = macMax + 1;
			}
			break;
		case LegendConstant.Mage:
			hpGrow = (int) (charLevel/6 + 2);
			mpGrow = (int) (0.8 * charLevel + 7);
			if (charLevel%7 == 0) {
				if (mcMin < mcMax) {
					mcMin = mcMin + 1;
					dcMin = dcMin + 1;
				}
				mcMax = mcMax + 1;
				dcMax = dcMax + 1;
			}
			break;
		}
		xmlParser.getNodeByName("hp").setTextContent(String.valueOf(hp + hpGrow));
		xmlParser.getNodeByName("mp").setTextContent(String.valueOf(mp + mpGrow));
		xmlParser.getNodeByName("dc").setTextContent(String.valueOf(dcMin + "-" + dcMax));
		xmlParser.getNodeByName("sc").setTextContent(String.valueOf(scMin + "-" + scMax));
		xmlParser.getNodeByName("mc").setTextContent(String.valueOf(mcMin + "-" + mcMax));
		xmlParser.getNodeByName("ac").setTextContent(String.valueOf(acMin + "-" + acMax));
		xmlParser.getNodeByName("mac").setTextContent(String.valueOf(macMin + "-" + macMax));
		xmlParser.save();
	}

	public boolean eligibleChallenge(PropertyView propertyView) {
		getSpecialRing();
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		Monsters monster = new Monsters(monsterName);
		if (Integer.parseInt(xmlParser.getNodeByName("level").getTextContent()) < monster.reqLevel) {
			return false;
		}
		switch (xmlParser.getNodeByName("career").getTextContent()) {
		case LegendConstant.Warrior:
			return propertyView.maxAttack + extraAttack >= monster.reqAttack ? true : false;
		case LegendConstant.Taoist:
			return propertyView.maxDaoAttack + extraDaoAttack >= monster.reqDA ? true : false;
		case LegendConstant.Mage:
			return propertyView.maxMagicAttack + extraMagicAttack >= monster.reqMA ? true : false;
		}
		return false;
	}
	
	public boolean survival(PropertyView propertyView) {
		getSpecialRing();
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		Monsters monster = new Monsters(monsterName);
		double attackIndex = 0;
		double defenceIndex = 0;
		double safetyIndex;
		double deathIndex;
		int monsterAttack = monster.attack;
		int charDefence = 0;
//		System.out.println(extraAttack);
//		System.out.println(extraDaoAttack);
//		System.out.println(extraMagicAttack);
//		System.out.println(extraDefence);
//		System.out.println(extraMagicDefence);
		DecimalFormat df = new DecimalFormat("######0.00");
		switch (xmlParser.getNodeByName("career").getTextContent()) {
		case LegendConstant.Warrior:
			attackIndex = propertyView.minAttack + propertyView.maxAttack + extraAttack - monster.reqAttack;
			break;
		case LegendConstant.Taoist:
			attackIndex = (propertyView.minDaoAttack + propertyView.maxDaoAttack + extraDaoAttack) * 1.1 - monster.reqDA;
			charDefence = 5;
			break;
		case LegendConstant.Mage:
			attackIndex = (propertyView.minMagicAttack + propertyView.maxMagicAttack + extraMagicAttack) * 1.2 - monster.reqMA;
			charDefence = 10;
			break;
		}
		if (propertyView.accuracy - monster.dodge <= -4) {
			attackIndex = (double) (attackIndex * 0.8);
		} else if (propertyView.accuracy - monster.dodge == -3) {
			attackIndex = (double) (attackIndex * 0.85);
		} else if (propertyView.accuracy - monster.dodge == -2) {
			attackIndex = (double) (attackIndex * 0.9);
		} else if (propertyView.accuracy - monster.dodge == -1) {
			attackIndex = (double) (attackIndex * 0.95);
		}else if (propertyView.accuracy - monster.dodge == 1) {
			attackIndex = (double) (attackIndex * 1.05);
		} else if (propertyView.accuracy - monster.dodge == 2) {
			attackIndex = (double) (attackIndex * 1.1);
		} else if (propertyView.accuracy - monster.dodge == 3) {
			attackIndex = (double) (attackIndex * 1.15);
		} else if (propertyView.accuracy - monster.dodge >= 4) {
			attackIndex = (double) (attackIndex * 1.2);
		}
		if (monsterAttack == 0) {
			monsterAttack = monster.magicAttack;
			charDefence = charDefence + propertyView.minMagicDefence + propertyView.maxMagicDefence + extraMagicDefence;
		} else {
			charDefence = charDefence + propertyView.minDefence + propertyView.maxDefence + extraDefence;
		}
		defenceIndex = charDefence - monsterAttack + (propertyView.dodge - monster.accuracy + 5) * 2;
		safetyIndex = attackIndex + defenceIndex >= 0 ? attackIndex + defenceIndex : 0;
		safetyIndex = safetyIndex * monster.safetyFactor;
		safetyIndex = 30 + safetyIndex * 0.7;
		if (safetyIndex < 100) {
			deathIndex = Algorithm.getRandomDouble(0, 100);
			System.out.println(safetyIndex >= deathIndex ? df.format(safetyIndex) + " safe" : df.format(safetyIndex) + " dead");
			return safetyIndex >= deathIndex ? true : false;
		}
		return true;
	}
	
	public void deathEvent() {
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		int exp = Integer.parseInt(xmlParser.getNodeByName("exp").getTextContent());
		exp = (int) (exp * 0.8);
		xmlParser.getNodeByName("exp").setTextContent(String.valueOf(exp));
		int randomNumber = 0;
		for (int i = 0; i < 3; i++) {
			randomNumber = Algorithm.getRandomInt(0, 255);
			switch (randomNumber) {
			case 1:
				if (!xmlParser.getNodeByName("upgradeCount").getTextContent().equals("7"))
					xmlParser.getNodeByName("weapon").setTextContent("");
				break;
			case 2:
				xmlParser.getNodeByName("armor").setTextContent("");
				break;
			case 3:
				xmlParser.getNodeByName("helmet").setTextContent("");
				break;
			case 4:
				xmlParser.getNodeByName("amulet").setTextContent("");
				break;
			case 5:
				if (!xmlParser.getNodeByName("medal").getTextContent().contains("x1"))
					xmlParser.getNodeByName("medal").setTextContent("");
				break;
			case 6:
				xmlParser.getNodeByName("leftBracelet").setTextContent("");
				break;
			case 7:
				xmlParser.getNodeByName("rightBracelet").setTextContent("");
				break;
			case 8:
				xmlParser.getNodeByName("leftRing").setTextContent("");
				break;
			case 9:
				xmlParser.getNodeByName("rightRing").setTextContent("");
				break;
			case 10:
				xmlParser.getNodeByName("belt").setTextContent("");
				break;
			case 11:
				xmlParser.getNodeByName("boots").setTextContent("");
				break;
			case 12:
				xmlParser.getNodeByName("gem").setTextContent("");
				break;
			}
		}
		xmlParser.save();
	}
	
	public void getSpecialRing() {
		extraAttack = 0;
		extraDaoAttack = 0;
		extraMagicAttack = 0;
		extraDefence = 0;
		extraMagicDefence = 0;
		String leftRing = xmlParser.getNodeByName("leftRing").getTextContent();
		String rightRing = xmlParser.getNodeByName("rightRing").getTextContent();
		String ring = leftRing + rightRing;
		if (ring.contains("r36")) {
			extraDefence = extraDefence + 10;
			extraMagicDefence = extraMagicDefence + 10;
		}
		if (ring.contains("r37")) {
			extraAttack = extraAttack + 10;
			extraDaoAttack = extraDaoAttack + 10;
			extraMagicAttack = extraMagicAttack + 10;
		}
		if (ring.contains("r38")) {
			extraDaoAttack = extraDaoAttack + 8;
			extraDefence = extraDefence + 8;
			extraMagicDefence = extraMagicDefence + 8;
		}
		if (ring.contains("r39")) {
			extraMagicAttack = extraMagicAttack + 12;
		}
		if (ring.contains("r40")) {
			extraAttack = extraAttack + 15;
		}
		if (ring.contains("r41")) {
			extraDefence = extraDefence + 12;
			extraMagicDefence = extraMagicDefence + 12;
		}
		if (ring.contains("r42")) {
			if (xmlParser.getNodeByName("career").getTextContent().equals(LegendConstant.Mage)) {
				extraDefence = extraDefence + 13;
				extraMagicDefence = extraMagicDefence + 13;
			}
		}
	}
}
