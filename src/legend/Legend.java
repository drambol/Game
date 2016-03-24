package legend;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.NodeList;

import clazz.LegendItem;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utility.calc.Algorithm;
import utility.file.XmlParser;

public class Legend {
	
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
					int probability = Integer.parseInt(nodeList.item(i).getTextContent());
					if (Algorithm.thousandPercent(probability)) {
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
