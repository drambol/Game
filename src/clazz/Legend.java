package clazz;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.NodeList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utility.calc.Algorithm;
import utility.file.XmlParser;

public class Legend {
	
	public String[] legendItems;
	public String[] itemCode;
	
	public String[] getItemFromMonster(String monsterCode) {
		legendItems = new String[10];
		itemCode = new String[10];
		XmlParser xmlParser = new XmlParser("runSuite\\LegendMonster.xml");
		NodeList nodeList = xmlParser.getNodeByName(monsterCode).getChildNodes();
		float increase = parseIncrease();
		int count = 0;
		int itemCount = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (count < 10) {
				String code = nodeList.item(i).getNodeName();
				if (!code.equals("#text") && !code.substring(0, 1).contentEquals("m")) {
					int probability = Integer.parseInt(nodeList.item(i).getTextContent());
					if (Algorithm.thousandPercent(probability * increase)) {
						LegendItem legendItem = new LegendItem(getItemByCode(code));
						legendItems[count] = legendItem.printItem();
						itemCode[count] = legendItem.code;
						count = count + 1;
					}
				} else if (code.substring(0, 3).contentEquals("mon")) {
					int a = Integer.parseInt(nodeList.item(i).getTextContent().split("-")[0]);
					int b = Integer.parseInt(nodeList.item(i).getTextContent().split("-")[1]);
					LegendItem legendItem = new LegendItem(getItemByCode(code));
					itemCount = Algorithm.getRandomInt(a, b);
					legendItems[count] = "*" + legendItem.printMedical(itemCount);
					itemCode[count] = legendItem.code;
					count = count + 1;
					if (code.equals("money")) {
						addMoney(itemCount);
					}
				} else if (code.substring(0, 2).contentEquals("mg")) {
					int probability = Integer.parseInt(nodeList.item(i).getTextContent());
					if (Algorithm.thousandPercent(probability * increase)) {
						LegendItem legendItem = new LegendItem(getItemByCode(code));
						legendItems[count] = "*" + legendItem.printMedical(1);
						itemCode[count] = legendItem.code;
						count = count + 1;
						addMoney(code);
					}
				} else if (code.contentEquals("misc1")) {
					int probability = Integer.parseInt(nodeList.item(i).getTextContent());
					if (Algorithm.thousandPercent(probability * increase)) {
						LegendItem legendItem = new LegendItem(getItemByCode(code));
						legendItems[count] = "*" + legendItem.printMedical(1);
						itemCode[count] = legendItem.code;
						count = count + 1;
						XmlParser heroXml = new XmlParser("runSuite\\LegendHero.xml");
						int oilCount = Integer.parseInt(heroXml.getNodeByName("bless").getTextContent()) + 1;
						heroXml.getNodeByName("bless").setTextContent(String.valueOf(oilCount));
						heroXml.save();
						heroXml = null;
					}
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
		return 1;//Return default value if code not found.
	}
	
	private float parseIncrease() {
		XmlParser heroXml = new XmlParser("runSuite\\LegendHero.xml");
		String increase = heroXml.getNodeByName("medal").getTextContent().split("~")[0];
		switch (increase) {
		case "x02":
			return 2f;
		case "x03":
		case "x10":
		case "x11":
			return 3f;
		}
		return 1f;
	}
	
	private void addMoney(int amount) {
		XmlParser heroXml = new XmlParser("runSuite\\LegendHero.xml");
		long money = Long.parseLong(heroXml.getNodeByName("money").getTextContent()) + amount;
		heroXml.getNodeByName("money").setTextContent(String.valueOf(money));
		heroXml.save();
		heroXml = null;
	}
	
	private void addMoney(String str) {
		int amount = 0;
		switch (str) {
		case "mg1":
			amount = 1000000;
			break;
		case "mg2":
			amount = 5000000;
			break;
		case "mg3":
			amount = 10000000;
			break;
		}
		addMoney(amount);
	}

}
