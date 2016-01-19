package utility.file;

import java.io.File;

public class EnvironmentVariable {
	
	public static String getBrowserType() {
		XmlParser xmlParser = new XmlParser("runSuite\\Config.xml");
		return xmlParser.getNodeValue("Browser");
	}
	
	public static String getChromeLocation() {
		XmlParser xmlParser = new XmlParser("runSuite\\Config.xml");
		return xmlParser.getNodeValue("Chrome");
	}
	
	public static String getUrl(String url) {
		XmlParser xmlParser = new XmlParser("runSuite\\Config.xml");
		return xmlParser.getNodeValue(url);
	}
	
	public static String getApkLocation() {
		XmlParser xmlParser = new XmlParser("runSuite\\MobileConfig.xml");
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, xmlParser.getNodeValue("ApkLocation"));
		File app = new File(appDir, xmlParser.getNodeValue("ApkName"));
		return app.getAbsolutePath();
	}

}
