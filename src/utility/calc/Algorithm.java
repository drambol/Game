package utility.calc;

import java.util.Random;

import utility.dateTime.DateTime;

public class Algorithm {
	
	public static long getMaximumNumber(long[] array) {
		int arrayCount = array.length;
		long max = 0;
		for (int i = 0; i < arrayCount; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
	
	public static int getMaximumOrder(long[] array) {
		int arrayCount = array.length;
		long max = 0;
		int order = 0;
		for (int i = 0; i < arrayCount; i++) {
			if (array[i] > max) {
				max = array[i];
				order = i;
			}
		}
		return order;
	}
	
	public static long getMinimumNumber(long[] array) {
		int arrayCount = array.length;
		long min = 0;
		for (int i = 0; i < arrayCount; i++) {
			if (array[i] < min) {
				min = array[i];
			}
		}
		return min;
	}
	
	public static int getMinimumOrder(long[] array) {
		int arrayCount = array.length;
		long min = 0;
		int order = 0;
		for (int i = 0; i < arrayCount; i++) {
			if (array[i] < min) {
				min = array[i];
				order = i;
			}
		}
		return order;
	}
	
	@SuppressWarnings("null")
	public static int getRandomInt(int min, int max) {
		DateTime.sleep(getRandomSleepTime());
		if (min > max) {
			return (Integer) null;
		}
		Random rnd = new Random();
		int num = 0;
		for (int p = 0; p < 1; p++) {
			num = rnd.nextInt(max + 1);
			if (num < min)
				p--;
		}
		return num;
	}
	
	@SuppressWarnings("null")
	public static double getRandomDouble(int min, int max) {
		if (min > max) {
			return (Integer) null;
		} else if (max > 327) {
			System.out.println("The input index is out of range!");
			return (Integer) null;
		}
		Random rnd = new Random();
		double num = 0;
		for (int p = 0; p < 1; p++) {
			DateTime.sleep(10);
			num = rnd.nextInt(max * 100 + 1);
			if (num < min * 100)
				p--;
		}
		return num / 100;
	}
	
	public static int[] sort(int[] input, boolean flag) {
		int count = input.length;
		int stack = 0;
		if (flag) {
			for (int trigger = 0; trigger < 1; trigger++) {
				for (int i = 0; i < count - 1; i++) {
					if (input[i] > input[i + 1]) {
						trigger--;
						stack = input[i];
						input[i] = input[i + 1];
						input[i + 1] = stack;
					}
				}
			}
		} else {
			for (int trigger = 0; trigger < 1; trigger++) {
				for (int i = 0; i < count - 1; i++) {
					if (input[i] < input[i + 1]) {
						trigger--;
						stack = input[i];
						input[i] = input[i + 1];
						input[i + 1] = stack;
					}
				}
			}
		}
		return input;
	}
	
	public static double[] sort(double[] input, boolean flag) {
		int count = input.length;
		double stack = 0;
		if (flag) {
			for (int trigger = 0; trigger < 1; trigger++) {
				for (int i = 0; i < count - 1; i++) {
					if (input[i] > input[i + 1]) {
						trigger--;
						stack = input[i];
						input[i] = input[i + 1];
						input[i + 1] = stack;
					}
				}
			}
		} else {
			for (int trigger = 0; trigger < 1; trigger++) {
				for (int i = 0; i < count - 1; i++) {
					if (input[i] < input[i + 1]) {
						trigger--;
						stack = input[i];
						input[i] = input[i + 1];
						input[i + 1] = stack;
					}
				}
			}
		}
		return input;
	}
	
	public static boolean contains(String[] array, String str) {
		int count = array.length;
		for (int i = 0; i < count; i++) {
			if (str == array[i]) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean contains(int[] array, int num) {
		int count = array.length;
		for (int i = 0; i < count; i++) {
			if (num == array[i]) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean getDraw(int chance) {
		if (chance < 1 || chance > 99) {
			return true;
		}
		int randomKey = getRandomInt(1, 60000)%100;
		if (randomKey < chance)
			return true;
		return false;
	}
	
	public static int getDrawNum() {
		int drawNum = getRandomInt(1, 60000)%100;
		return drawNum;
	}
	
	private static int getRandomSleepTime() {
		Random rnd = new Random();
		int num = 0;
		for (int p = 0; p < 1; p++) {
			num = rnd.nextInt(300 + 1);
			if (num < 100)
				p--;
		}
		return num/500;
	}

}
