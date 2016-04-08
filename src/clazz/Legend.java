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
				} else if (code.substring(0, 1).contentEquals("m")) {
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
	
	private float parseIncrease() {
		XmlParser heroXml = new XmlParser("runSuite\\LegendHero.xml");
		String increase = heroXml.getNodeByName("medal").getTextContent().split("~")[0];
		switch (increase) {
		case "x4":
			return 1.3f;
		case "x5":
			return 1.5f;
		case "x6":
			return 2f;
		}
		return 1f;
	}
	
	private void addMoney(int amount) {
		XmlParser heroXml = new XmlParser("runSuite\\LegendHero.xml");
		int money = Integer.parseInt(heroXml.getNodeByName("money").getTextContent()) + amount;
		heroXml.getNodeByName("money").setTextContent(String.valueOf(money));
		heroXml.save();
		heroXml = null;
	}

}
