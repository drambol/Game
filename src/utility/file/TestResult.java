package utility.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import utility.dateTime.DateTime;

public class TestResult {

	public static void storeTestResult() throws IOException {
		String fileName = DateTime.getCurrentTime();
		String sourceFile = System.getProperty("user.dir") + "\\test-output\\Suite\\MyTest.html";
		String targetFile = System.getProperty("user.dir") + "\\history\\" + fileName + ".html";
		
		File file = new File(System.getProperty("user.dir") + "\\history\\");
		file.mkdir();

		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;

		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

}
