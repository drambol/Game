package legend;

public class TestClass {
	
	public static void main(String[] args) {
		for (int n = 1; n <= 60; n++) {
			int exp = (int) (Math.pow(1.25, n - 1) * 100);
			System.out.println(n + ": " + exp);
		}
		
	}

}
