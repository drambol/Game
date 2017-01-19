package clazz;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utility.calc.Algorithm;

public class LegendItem {
	
	public String name;
	public String code;
	public int[] defence = {0, 0};
	public int[] magicDefence = {0, 0};
	public int[] attack = {0, 0};
	public int[] sprint = {0, 0};
	public int[] magic = {0, 0};
	public int accurate = 0;
	public int dexterity = 0;
	public int luck = 0;
	public int attackSpeed = 0;
	public int poisonAvoid = 0;
	public int magicAvoid = 0;
	public int weight = 0;
	public int durability = 0;
	public String requirement;
	
	public LegendItem(int line) {
		File file = new File(System.getProperty("user.dir") + "\\test-data\\Legend.xls");
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet("Sheet1");
			name = sheet.getCell(0, line).getContents();
			code = sheet.getCell(1, line).getContents();
			String realDefence = sheet.getCell(3, line).getContents();
			String realMagicDefence = sheet.getCell(4, line).getContents();
			String realAttack = sheet.getCell(5, line).getContents();
			String realSprint = sheet.getCell(6, line).getContents();
			String realMagic = sheet.getCell(7, line).getContents();
			String realAccurate = sheet.getCell(8, line).getContents();
			String realDexterity = sheet.getCell(9, line).getContents();
			String realLuck = sheet.getCell(10, line).getContents();
			String realAttackSpeed = sheet.getCell(11, line).getContents();
			String realPoisonAvoid = sheet.getCell(12, line).getContents();
			String realMagicAvoid = sheet.getCell(13, line).getContents();
			String realWeight = sheet.getCell(14, line).getContents();
			String realDurability = sheet.getCell(15, line).getContents();
			String realRequirement = sheet.getCell(16, line).getContents();
			if (!realDefence.isEmpty()) {
				defence[0] = Integer.parseInt(realDefence.split("-")[0]);
				defence[1] = Integer.parseInt(realDefence.split("-")[1]);
			}
			if (!realMagicDefence.isEmpty()) {
				magicDefence[0] = Integer.parseInt(realMagicDefence.split("-")[0]);
				magicDefence[1] = Integer.parseInt(realMagicDefence.split("-")[1]);
			}
			if (!realAttack.isEmpty()) {
				attack[0] = Integer.parseInt(realAttack.split("-")[0]);
				attack[1] = Integer.parseInt(realAttack.split("-")[1]);
			}
			if (!realSprint.isEmpty()) {
				sprint[0] = Integer.parseInt(realSprint.split("-")[0]);
				sprint[1] = Integer.parseInt(realSprint.split("-")[1]);
			}
			if (!realMagic.isEmpty()) {
				magic[0] = Integer.parseInt(realMagic.split("-")[0]);
				magic[1] = Integer.parseInt(realMagic.split("-")[1]);
			}
			if (!realAccurate.isEmpty()) {
				accurate = Integer.parseInt(realAccurate);
			}
			if (!realDexterity.isEmpty()) {
				dexterity = Integer.parseInt(realDexterity);
			}
			if (!realLuck.isEmpty()) {
				luck = Integer.parseInt(realLuck);
			}
			if (!realAttackSpeed.isEmpty()) {
				attackSpeed = Integer.parseInt(realAttackSpeed);
			}
			if (!realPoisonAvoid.isEmpty()) {
				poisonAvoid = Integer.parseInt(realPoisonAvoid.substring(0, 1));
			}
			if (!realMagicAvoid.isEmpty()) {
				magicAvoid = Integer.parseInt(realMagicAvoid.substring(0, 1));
			}
			if (!realWeight.isEmpty()) {
				weight = Integer.parseInt(realWeight);
			}
			if (!realDurability.isEmpty()) {
				durability = Integer.parseInt(realDurability);
			}
			if (!realRequirement.isEmpty()) {
				requirement = realRequirement;
			}
			book.close();
			getDurability(getExcellence());
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String printItem() {
		String str = code + "~" + name;
		if (defence[0] != 0 || defence[1] != 0) {
			str = str + "  防御: " + defence[0] + "-" + defence[1];
		}
		if (magicDefence[0] != 0 || magicDefence[1] != 0) {
			str = str + "  魔御: " + magicDefence[0] + "-" + magicDefence[1];
		}
		if (attack[0] != 0 || attack[1] != 0) {
			str = str + "  攻击: " + attack[0] + "-" + attack[1];
		}
		if (sprint[0] != 0 || sprint[1] != 0) {
			str = str + "  道术: " + sprint[0] + "-" + sprint[1];
		}
		if (magic[0] != 0 || magic[1] != 0) {
			str = str + "  魔法: " + magic[0] + "-" + magic[1];
		}
		if (accurate != 0) {
			str = str + "  准确 +" + accurate;
		}
		if (dexterity != 0) {
			str = str + "  敏捷 +" + dexterity;
		}
		if (luck != 0) {
			str = str + "  幸运 +" + luck;
		}
		if (attackSpeed > 0) {
			str = str + "  攻击速度 +" + attackSpeed;
		} else if (attackSpeed == -1) {
			str = str + "  攻击速度 -1";
		}
		if (poisonAvoid != 0) {
			int value = poisonAvoid * 10;
			str = str + "  毒物躲避 +" + value + "%";
		}
		if (magicAvoid != 0) {
			int value = magicAvoid * 10;
			str = str + "  魔法躲避 +" + value + "%";
		}
		if (weight > 0) {
			str = str + "  重量: " + weight;
		}
		if (durability > 0) {
			str = str + "  持久: " + durability;
		}
		if (requirement != null) {
			str = str + "  需" + requirement;
		}
		return str;
	}
	
	public String printMedical(int amount) {
		String str = name;
		if (amount > 99) {
			str = str + " " + amount;
		} else if (amount > 0) {
			str = str + " x" + amount;
		}
		return str;
	}
	
	private boolean getExcellence() {
		int chance = Algorithm.getRandomInt(1, 1000);
		boolean excellence = false;
		int p = 0;
		if (chance == 1) {
			p = 10;
		} else if (chance <= 3) {
			p = 9;
		} else if (chance <= 6) {
			p = 8;
		} else if (chance <= 10) {
			p = 7;
		} else if (chance <= 15) {
			p = 6;
		} else if (chance <= 25) {
			p = 5;
		} else if (chance <= 40) {
			p = 4;
		} else if (chance <= 60) {
			p = 3;
		} else if (chance <= 100) {
			p = 2;
		} else if (chance <= 200) {
			p = 1;
		}
		if (p > 0) {
			excellence = true;
			switch (code.substring(0, 1)) {
			case "w":
				for (int i = 0; i < p; i++) {
					int ran = Algorithm.getRandomInt(1, 100);
					if (ran <= 29) {
						attack[1] = attack[1] + 1;
					} else if (ran <= 58) {
						sprint[1] = sprint[1] + 1;
					} else if (ran <= 87) {
						magic[1] = magic[1] + 1;
					} else if (ran <= 96) {
						accurate = accurate + 1;
					} else {
						attackSpeed = attackSpeed + 1;
					}
				}
				break;
			case "r":
				for (int i = 0; i < p; i++) {
					int ran = Algorithm.getRandomInt(1, 99);
					if (ran <= 33) {
						attack[1] = attack[1] + 1;
					} else if (ran <= 66) {
						sprint[1] = sprint[1] + 1;
					} else {
						magic[1] = magic[1] + 1;
					}
				}
				break;
			case "R":
				for (int i = 0; i < p; i++) {
					int ran = Algorithm.getRandomInt(1, 100);
					if (ran <= 28) {
						attack[1] = attack[1] + 1;
					} else if (ran <= 56) {
						sprint[1] = sprint[1] + 1;
					} else if (ran <= 84) {
						magic[1] = magic[1] + 1;
					} else {
						poisonAvoid = poisonAvoid + 1;
					}
				}
				break;
			case "i":
			case "h":
			case "c":
			case "b":
			case "o":
			case "g":
				for (int i = 0; i < p; i++) {
					int ran = Algorithm.getRandomInt(1, 100);
					if (ran <= 20) {
						defence[1] = defence[1] + 1;
					} else if (ran <= 40) {
						magicDefence[1] = magicDefence[1] + 1;
					} else if (ran <= 60) {
						attack[1] = attack[1] + 1;
					} else if (ran <= 80) {
						sprint[1] = sprint[1] + 1;
					} else {
						magic[1] = magic[1] + 1;
					}
				}
				break;
			case "a":
				for (int i = 0; i < p; i++) {
					int ran = Algorithm.getRandomInt(1, 100);
					if (ran <= 30) {
						attack[1] = attack[1] + 1;
					} else if (ran <= 60) {
						sprint[1] = sprint[1] + 1;
					} else if (ran <= 90) {
						magic[1] = magic[1] + 1;
					} else if (ran <= 95) {
						accurate = accurate + 1;
					} else {
						dexterity = dexterity + 1;
					}
				}
				break;
			case "A":
				for (int i = 0; i < p; i++) {
					int ran = Algorithm.getRandomInt(1, 100);
					if (ran <= 25) {
						attack[1] = attack[1] + 1;
					} else if (ran <= 50) {
						sprint[1] = sprint[1] + 1;
					} else if (ran <= 75) {
						magic[1] = magic[1] + 1;
					} else if (ran <= 85) {
						luck = luck + 1;
					} else {
						magicAvoid = magicAvoid + 1;
					}
				}
				break;
			}
		}
		return excellence;
	}
	
	private void getDurability(boolean flag) {
		int chance = Algorithm.getRandomInt(1, 1000);
		int p = 0;
		int a, b, c;
		if (flag) {
			a = 950; b = 850; c = 650;
		} else {
			a = 980; b = 940; c = 860;
		}
		if (chance >= a) {
			p = 3;
		} else if (chance >= b) {
			p = 2;
		} else if (chance >= c) {
			p = 1;
		}
		if (p > 0) {
			switch (code.substring(0, 1)) {
			case "w":
			case "c":
				durability = durability + p * 2;
				break;
			case "r":
			case "i":
			case "a":
			case "h":
			case "b":
			case "o":
			case "g":
				durability = durability + p;
				break;
			}
		}
	}

}
