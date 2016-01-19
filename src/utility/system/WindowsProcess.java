package utility.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WindowsProcess {
	
	public static List<String> listProcess() {
		List<String> args = new ArrayList<String>();
		BufferedReader br = null;
		try {
			Process proc = Runtime.getRuntime().exec("tasklist");
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			@SuppressWarnings("unused")
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(br.readLine());
				args.add(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return args;
	}
	
	public static boolean findProcess(String processName) {
		BufferedReader br = null;
		try {
			Process proc = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + "\"");
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains(processName)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ex) {
					
				}
			}
		}
	}
	
	public static void killProcess(String processName) {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM " + processName + " /T");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void runProcess(String commandName, int seconds) {
		try {
			Runtime.getRuntime().exec(commandName);
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
