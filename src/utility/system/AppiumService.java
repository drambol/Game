package utility.system;

import java.io.File;
import java.io.IOException;

public class AppiumService {
	
	public static void start() {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File fileDir = new File(classpathRoot, "src\\scripts");
		File scriptFile = new File(fileDir,"Start_Appium.bat");
		System.out.println(scriptFile.getAbsolutePath());
		try {
			Runtime.getRuntime().exec(scriptFile.getAbsolutePath());
			Thread.sleep(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void restart(int i) {
		if (WindowsProcess.findProcess("conhost.exe")) {
			if (i > 0) {
				WindowsProcess.killProcess("conhost.exe");
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		start();
	}

}
