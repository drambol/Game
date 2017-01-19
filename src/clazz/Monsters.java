package clazz;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import legend.LegendConstant;

public class Monsters {
	
	File file = new File(System.getProperty("user.dir") + "\\test-data\\Legend.xls");
	public double safetyFactor;
	public int reqAttack;
	public int reqDA;
	public int reqMA;
	public int reqLevel;
	public int attack = 0;
	public int magicAttack = 0;
	public int accuracy;
	public int dodge;
	
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
			safetyFactor = Double.parseDouble(sheet.getCell(1, rowCount).getContents());
			reqLevel = Integer.parseInt(sheet.getCell(2, rowCount).getContents());
			reqAttack = Integer.parseInt(sheet.getCell(3, rowCount).getContents());
			reqDA = Integer.parseInt(sheet.getCell(4, rowCount).getContents());
			reqMA = Integer.parseInt(sheet.getCell(5, rowCount).getContents());
			if (!sheet.getCell(6, rowCount).getContents().isEmpty())
				attack = Integer.parseInt(sheet.getCell(6, rowCount).getContents());
			if (!sheet.getCell(7, rowCount).getContents().isEmpty())
				magicAttack = Integer.parseInt(sheet.getCell(7, rowCount).getContents());
			accuracy = Integer.parseInt(sheet.getCell(8, rowCount).getContents());
			dodge = Integer.parseInt(sheet.getCell(9, rowCount).getContents());
			book.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
