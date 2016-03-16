package legend;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Monsters {
	
	File file = new File(System.getProperty("user.dir") + "\\test-data\\Legend.xls");
	int reqAttack;
	int reqDA;
	int reqMA;
	int reqLevel;
	
	public Monsters(String monsterName) {
		int rowCount = 0;
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet("Sheet2");
			switch (monsterName) {
			case LegendConstant.Monster01:
				rowCount = 1;
				break;
			case LegendConstant.Monster02:
				rowCount = 2;
				break;
			case LegendConstant.Monster03:
				rowCount = 3;
				break;
			case LegendConstant.Monster04:
				rowCount = 4;
				break;
			case LegendConstant.Monster05:
				rowCount = 5;
				break;
			case LegendConstant.Monster06:
				rowCount = 6;
				break;
			case LegendConstant.Monster07:
				rowCount = 7;
				break;
			case LegendConstant.Monster08:
				rowCount = 8;
				break;
			case LegendConstant.Monster09:
				rowCount = 9;
				break;
			case LegendConstant.Monster10:
				rowCount = 10;
				break;
			case LegendConstant.Monster11:
				rowCount = 11;
				break;
			case LegendConstant.Monster12:
				rowCount = 12;
				break;
			case LegendConstant.Monster13:
				rowCount = 13;
				break;
			case LegendConstant.Monster14:
				rowCount = 14;
				break;
			case LegendConstant.Monster15:
				rowCount = 15;
				break;
			case LegendConstant.Monster16:
				rowCount = 16;
				break;
			case LegendConstant.Monster17:
				rowCount = 17;
				break;
			case LegendConstant.Monster18:
				rowCount = 18;
				break;
			case LegendConstant.Monster19:
				rowCount = 19;
				break;
			case LegendConstant.Monster20:
				rowCount = 20;
				break;
			}
			reqLevel = Integer.parseInt(sheet.getCell(1, rowCount).getContents());
			reqAttack = Integer.parseInt(sheet.getCell(2, rowCount).getContents());
			reqDA = Integer.parseInt(sheet.getCell(3, rowCount).getContents());
			reqMA = Integer.parseInt(sheet.getCell(4, rowCount).getContents());
			book.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
