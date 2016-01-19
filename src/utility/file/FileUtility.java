package utility.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtility {
	
	public File file;
	
	public FileUtility(String fileName) {
		file = new File(System.getProperty("user.dir") + "\\src\\" + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static File generateFile(String fileName, String location) {
		File file = new File(System.getProperty("user.dir") + "\\src\\xml\\" + fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			URL url = new URL(location);
			BufferedReader inputFile = new BufferedReader(new InputStreamReader(url.openStream()));
			OutputStreamWriter outputFile = new OutputStreamWriter(new FileOutputStream(file, true));
			String str = null;
			while ((str = inputFile.readLine()) != null) {
				outputFile.write(str);
			}
			outputFile.flush();
			outputFile.close();
			inputFile.close();
			return file;
		} catch (MalformedURLException x) {
			System.out.println("Wrong Address!");
		} catch (IOException xx) {
			System.out.println("IO Exception!");
			xx.printStackTrace();
		}
		return null;
	}
	
	public static File writeFile(String fileName, String fileContent) {
		File file = new File(System.getProperty("user.dir") + "\\src\\xml\\" + fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			OutputStreamWriter outputFile = new OutputStreamWriter(new FileOutputStream(file, true));
			outputFile.write(fileContent);
			outputFile.flush();
			outputFile.close();
			return file;
		} catch (MalformedURLException x) {
			System.out.println("Wrong Address!");
		} catch (IOException xx) {
			System.out.println("IO Exception!");
			xx.printStackTrace();
		}
		return null;
	}
	
	public void reset() {
		if (file.exists())
			file.delete();
	}
	
	public void open() {
		try {
			java.awt.Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public long getContentLength() {
		return file.length();
	}
	
	public void append(String content) {
		FileWriter writer = null;
		 try {     
			writer = new FileWriter(file.getAbsoluteFile(), true);
			writer.write(content + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeLine() {
		FileWriter writer = null;
		 try {     
			writer = new FileWriter(file.getAbsoluteFile(), true);
			writer.write("<hr>");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
