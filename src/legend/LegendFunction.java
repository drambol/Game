package legend;

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
	
	public LegendFunction(String monster) {
		this.monsterName = monster;
		currExp = Integer.parseInt(xmlParser.getNodeByName("exp").getTextContent());
		charLevel = Integer.parseInt(xmlParser.getNodeByName("level").getTextContent());
		switch (monster) {
		case LegendConstant.Monster01:
			exp = 200;
			break;
		case LegendConstant.Monster02:
			exp = 500;
			break;
		case LegendConstant.Monster03:
			exp = 800;
			break;
		case LegendConstant.Monster04:
			exp = 1000;
			break;
		case LegendConstant.Monster05:
			exp = 1600;
			break;
		case LegendConstant.Monster06:
			exp = 1600;
			break;
		case LegendConstant.Monster07:
			exp = 2000;
			break;
		case LegendConstant.Monster08:
			exp = 2500;
			break;
		case LegendConstant.Monster09:
			exp = 3600;
			break;
		case LegendConstant.Monster10:
			exp = 5000;
			break;
		case LegendConstant.Monster11:
			exp = 5000;
			break;
		case LegendConstant.Monster12:
			exp = 5000;
			break;
		case LegendConstant.Monster13:
			exp = 3600;
			break;
		case LegendConstant.Monster14:
			exp = 3000;
			break;
		case LegendConstant.Monster15:
			exp = 5000;
			break;
		case LegendConstant.Monster16:
			exp = 8000;
			break;
		case LegendConstant.Monster17:
			exp = 10000;
			break;
		case LegendConstant.Monster18:
			exp = 12000;
			break;
		case LegendConstant.Monster19:
			exp = 12000;
			break;
		case LegendConstant.Monster20:
			exp = 20000;
			break;
		}
	}
	
	public void addExp() {
		reqExp = (int) (Math.pow(1.25, charLevel - 1) * 100);
		if (currExp + exp >= reqExp) {
			charLevel = charLevel + 1;
			currExp = currExp + exp - reqExp;
			xmlParser.getNodeByName("level").setTextContent(String.valueOf(charLevel));
			levelUp = true;
			attributeGrow();
		} else {
			currExp = currExp + exp;
		}
		xmlParser.getNodeByName("exp").setTextContent(String.valueOf(currExp));
		xmlParser.save();
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
				dcMax = dcMax + 1;
				if (dcMin < dcMax) {
					dcMin = dcMin + 1;
				}
			}
			if (charLevel%7 == 0) {
				acMax = acMax + 1;
				if (acMin < acMax) {
					acMin = acMin + 1;
				}
			}
			break;
		case LegendConstant.Taoist:
			hpGrow = (int) (charLevel/3 + 3);
			mpGrow = (int) (charLevel/2 + 4);
			if (charLevel%7 == 0) {
				scMax = scMax + 1;
				dcMax = dcMax + 1;
				macMax = macMax + 1;
				if (scMin < scMax) {
					scMin = scMin + 1;
					dcMin = dcMin + 1;
					macMin = macMin + 1;
				}
			}
			break;
		case LegendConstant.Mage:
			hpGrow = (int) (charLevel/6 + 2);
			mpGrow = (int) (0.8 * charLevel + 7);
			if (charLevel%7 == 0) {
				mcMax = mcMax + 1;
				dcMax = dcMax + 1;
				if (mcMin < mcMax) {
					mcMin = mcMin + 1;
					dcMin = dcMin + 1;
				}
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
		xmlParser = new XmlParser("runSuite\\LegendHero.xml");
		Monsters monster = new Monsters(monsterName);
		propertyView.loadPorpertyFromXML();
		if (Integer.parseInt(xmlParser.getNodeByName("level").getTextContent()) < monster.reqLevel) {
			return false;
		}
		switch (xmlParser.getNodeByName("career").getTextContent()) {
		case LegendConstant.Warrior:
			return propertyView.attack >= monster.reqAttack ? true : false;
		case LegendConstant.Taoist:
			return propertyView.daoAttack >= monster.reqDA ? true : false;
		case LegendConstant.Mage:
			return propertyView.magicAttack >= monster.reqMA ? true : false;
		}
		return false;
	}
}
