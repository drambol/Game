package clazz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utility.calc.Algorithm;
import jxl.Sheet;

public class Heroes {
	
	public int maxRow;
	public List<Hero> heroList = new ArrayList<Hero>();
	public List<Hero> filterList = new ArrayList<Hero>();
	
	public Heroes(Sheet sheet) {
		maxRow = sheet.getRows();
		for (int i = 1; i < maxRow; i++) {
			String heroName = sheet.getCell(0, i).getContents();
			String heroValue = sheet.getCell(1, i).getContents();
			Hero hero = new Hero(heroName, heroValue);
			heroList.add(hero);
		}
		Collections.shuffle(heroList);
	}
	
	public Heroes groupHero(String groupId) {
		for (int i = 0; i < heroList.size(); i++) {
			if (!groupId.equalsIgnoreCase(heroList.get(i).value)) {
				filterList.add(heroList.get(i));
			}
		}
		heroList = filterList;
		return this;
	}
	
	public Hero getHero(String name) {
		for (int i = 0; i < heroList.size(); i++) {
			if (name.equalsIgnoreCase(heroList.get(i).name)) {
				return heroList.get(i);
			}
		}
		return new Hero("Null", "Null");
	}
	
	public Hero getRandomHero() {
		int row = Algorithm.getRandomInt(0, heroList.size() - 1);
		return heroList.get(row);
	}
	
	public String getHeroValue(String name) {
		for (int i = 0; i < heroList.size(); i++) {
			if (name.equalsIgnoreCase(heroList.get(i).name)) {
				return heroList.get(i).value;
			}
		}
		return "Null";
	}
	
	public class Hero {
		
		public String name;
		public String value;
		
		public Hero(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}

}
