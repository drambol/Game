package legend;

import utility.file.XmlParser;

public class TestClass {
	
	public static void main(String[] args) {
		XmlParser xmlParser = new XmlParser("runSuite\\save\\大吨弟.xml");
		xmlParser.save();
	}

}
