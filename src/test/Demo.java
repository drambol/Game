package test;

import java.io.File;
import java.io.IOException;

import clazz.Heroes;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Demo {
	
	public static void main(String args[]) {
		File file = new File(System.getProperty("user.dir") + "\\test-data\\Shuihu108.xls");
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet("Sheet2");
			Heroes heroes = new Heroes(sheet);
			for(int i = 0; i < heroes.heroList.size(); i++) {
				System.out.println(heroes.heroList.get(i).name + "  " + heroes.heroList.get(i).value);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}